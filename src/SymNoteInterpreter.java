import environment.Environment;
import java.util.HashSet;
import java.util.Set;

import environment.Variable;
import gen.SymNoteBaseVisitor;
import gen.SymNoteParser;
import midi.SymNoteTimeline;

public class SymNoteInterpreter extends SymNoteBaseVisitor<Object> {
    private static final int TICKS_PER_BEAT = 480;

    public Environment env = new Environment();
    public float bpm = 120;
    public String currentSynthName = "piano";
    public long currentTick = 0;

    private final Set<String> declaredVariables;
    private final SymNoteTimeline timeline = new SymNoteTimeline(TICKS_PER_BEAT);
    private final FlowExecutor flowExecutor;
    private final GridExecutor gridExecutor;

    public SymNoteInterpreter() {
        this(new HashSet<>());
    }

    public SymNoteInterpreter(Set<String> declaredVariables) {
        this.declaredVariables = new HashSet<>(declaredVariables);
        this.flowExecutor = new FlowExecutor(this);
        this.gridExecutor = new GridExecutor(this);
    }

    public SymNoteTimeline getTimeline() {
        return timeline;
    }

    public float getBpm() {
        return bpm;
    }

    int getTicksPerBeat() {
        return TICKS_PER_BEAT;
    }

    @Override
    public Object visitProgram(SymNoteParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    // --- Block Scopes ---
    @Override
    public Object visitBlockLVL1(SymNoteParser.BlockLVL1Context ctx) {
        Environment previousEnv = env;
        try {
            env = new Environment(previousEnv);
            return super.visitBlockLVL1(ctx);
        } finally {
            env = previousEnv;
        }
    }

    @Override
    public Object visitBlockLVL2(SymNoteParser.BlockLVL2Context ctx) {
        Environment previousEnv = env;
        try {
            env = new Environment(previousEnv);
            return super.visitBlockLVL2(ctx);
        } finally {
            env = previousEnv;
        }
    }

    @Override
    public Object visitBlockRoutine(SymNoteParser.BlockRoutineContext ctx) {
        Environment previousEnv = env;
        try {
            env = new Environment(previousEnv);
            return super.visitBlockRoutine(ctx);
        } finally {
            env = previousEnv;
        }
    }

    // --- State & Variables ---

    private void checkType(String type, Object value, String name, int line) {
        boolean error = false;
        if (type.equals("int") && !(value instanceof Integer || value instanceof Float || value instanceof Double)) error = true;
        else if (type.equals("float") && !(value instanceof Float || value instanceof Double)) error = true;
        else if (type.equals("string") && !(value instanceof String)) error = true;
        else if (type.equals("bool") && !(value instanceof Boolean)) error = true;

        if (error) {
            throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        }
    }

    @Override
    public Object visitDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx) {
        String name = ctx.ID().getText();
        String type = ctx.type().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        if(value != null) {
            int line = ctx.getStart().getLine();
            checkType(type, value, name, line);
            if (type.equals("int")) {
                env.define(name, new Variable(type, ((Number) value).intValue()));
                return null;
            }
        }
        env.define(name, new Variable(type, value));
        return null;
    }

    @Override
    public Object visitAssignStmt(SymNoteParser.AssignStmtContext ctx) {
        validateVariableDeclared(ctx.ID().getText(), ctx.getStart().getLine());
        Object value = visit(ctx.expression());
        String type = env.get(ctx.ID().getText()).type;
        String name = ctx.ID().getText();
        checkType(type, value, name, ctx.getStart().getLine());

        if (type.equals("int")) {
            env.assign(name, ((Number) value).intValue());
            return null;
        }

        env.assign(name, visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitDeclAssignStmtLVL2(SymNoteParser.DeclAssignStmtLVL2Context ctx) {
        String name = ctx.ID().getText();
        String type = ctx.type().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        if(value != null) {
            int line = ctx.getStart().getLine();
            checkType(type, value, name, line);
        }
        env.define(name, new Variable(type, value));
        return null;
    }

    @Override
    public Object visitAssignStmtLVL2(SymNoteParser.AssignStmtLVL2Context ctx) {
        validateVariableDeclared(ctx.ID().getText(), ctx.getStart().getLine());
        Object value = visit(ctx.expression());
        String type = env.get(ctx.ID().getText()).type;
        String name = ctx.ID().getText();
        checkType(type, value, name, ctx.getStart().getLine());

        if (type.equals("int")) {
            env.assign(name, ((Number) value).intValue());
            return null;
        }

        env.assign(name, visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitAtomInt(SymNoteParser.AtomIntContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    @Override
    public Object visitAtomFloat(SymNoteParser.AtomFloatContext ctx) {
        return Float.parseFloat(ctx.FLOAT().getText());
    }

    @Override
    public Object visitAtomString(SymNoteParser.AtomStringContext ctx) {
        String str = ctx.STRING().getText();
        return str.substring(1, str.length() - 1);
    }

    @Override
    public Object visitAtomId(SymNoteParser.AtomIdContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());
        return env.get(name).value;
    }

    @Override
    public Object visitParenExpr(SymNoteParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitFuncCallExpr(SymNoteParser.FuncCallExprContext ctx) {
        return visit(ctx.callExpr());
    }

    // --- Function & Track Execution ---
    @Override
    public Object visitExprStmt(SymNoteParser.ExprStmtContext ctx) {
        return visit(ctx.callStmt().callExpr());
    }

    @Override
    public Object visitExprStmtLVL2(SymNoteParser.ExprStmtLVL2Context ctx) {
        return visit(ctx.callStmt().callExpr());
    }

    @Override
    public Object visitTrackDecl(SymNoteParser.TrackDeclContext ctx) {
        env.defineTrack(ctx.ID().getText(), ctx);
        return null;
    }

    @Override
    public Object visitRoutineDecl(SymNoteParser.RoutineDeclContext ctx) {
        throw new RuntimeException("routine is not implemented yet");
    }

    @Override
    public Object visitCallExpr(SymNoteParser.CallExprContext ctx) {
        return flowExecutor.executeCall(ctx);
    }

    // --- Control Flow ---
    @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        String varName = ctx.ID().getText();
        int from = toNumber(visit(ctx.e1), ctx.getStart().getLine()).intValue();
        int to = toNumber(visit(ctx.e2), ctx.getStart().getLine()).intValue();
        for (int i = from; i <= to; i++) {
            Environment previousEnv = env;
            env = new Environment(previousEnv);
            env.define(varName, new Variable("int", i));
            visit(ctx.statementLVL1());
            env = previousEnv;
        }
        return null;
    }

    @Override
    public Object visitLoopStmtLVL2(SymNoteParser.LoopStmtLVL2Context ctx) {
        String varName = ctx.ID().getText();
        int from = toNumber(visit(ctx.e1), ctx.getStart().getLine()).intValue();
        int to = toNumber(visit(ctx.e2), ctx.getStart().getLine()).intValue();

        for (int i = from; i <= to; i++) {
            Environment previousEnv = env;
            env = new Environment(previousEnv);
            env.define(varName, new Variable("int", i));
            visit(ctx.statementLVL2());
            env = previousEnv;
        }
        return null;
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        Object condition = visit(ctx.expression());
        
        if(!(condition instanceof Boolean)){
            throw new RuntimeException("Condition in 'if' statement must evaluate to a boolean at line " + ctx.getStart().getLine());
        }

        if((Boolean) condition){
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL1(0));
            } finally {
                env = previousEnv;
            }
        } else if (ctx.statementLVL1(1) != null){
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL1(1));
            } finally {
                env = previousEnv;
            }
        }
        return null;
    }

    @Override
    public Object visitIfStmtLVL2(SymNoteParser.IfStmtLVL2Context ctx) {
        Object condition = visit(ctx.expression());
        
        if(!(condition instanceof Boolean)){
            throw new RuntimeException("Condition in 'if' statement must evaluate to a boolean at line " + ctx.getStart().getLine());
        }

        if((Boolean) condition){
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL2(0));
            } finally {
                env = previousEnv;
            }
        } else if (ctx.statementLVL2(1) != null){
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL2(1));
            } finally {
                env = previousEnv;
            }
        }
        return null;
    }

    @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        while (true) {
            Object condition = visit(ctx.expression());
            
            if(!(condition instanceof Boolean)){
                throw new RuntimeException("Condition in 'while' statement must evaluate to a boolean at line " + ctx.getStart().getLine());
            }
            
            if(!(Boolean) condition){
                break;
            }

            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL1());
            } finally {
                env = previousEnv;
            }
        }
        return null;
    }

    @Override
    public Object visitWhileStmtLVL2(SymNoteParser.WhileStmtLVL2Context ctx) {
        while(true){
            Object condition = visit(ctx.expression());
            
            if(!(condition instanceof Boolean)){
                throw new RuntimeException("Condition in 'while' statement must evaluate to a boolean at line " + ctx.getStart().getLine());
            }
            
            if(!(Boolean) condition){
                break;
            }

            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                visit(ctx.statementLVL2());
            } finally {
                env = previousEnv;
            }
        }
        return null;
    }

    @Override
    public Object visitParallelStmt(SymNoteParser.ParallelStmtContext ctx) {
        return flowExecutor.executeParallel(ctx);
    }

    // --- Grid / Time Execution ---
    @Override
    public Object visitGridStmtLVL2(SymNoteParser.GridStmtLVL2Context ctx) {
        return gridExecutor.executeGridStmt(ctx.gridStmt());
    }

    @Override
    public Object visitGridStmt(SymNoteParser.GridStmtContext ctx) {
        return gridExecutor.executeGridStmt(ctx);
    }

    void validateVariableDeclared(String variableName, int line) {
        if (!declaredVariables.contains(variableName)) {
            throw new RuntimeException("Undefined variable '" + variableName + "' at line " + line);
        }
    }

    Number toNumber(Object value, int line) {
        if (value instanceof Integer || value instanceof Float) {
            return (Number) value;
        }
        throw new RuntimeException("Expected numeric value at line " + line);
    }

    // Logical operations

    @Override
    public Object visitAtomBool(SymNoteParser.AtomBoolContext ctx) {
        if(ctx.BOOL().getText().equals("true")) return true;
        else if(ctx.BOOL().getText().equals("false")) return false;
        throw new RuntimeException("Invalid boolean value at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpOr(SymNoteParser.OpOrContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if(expr1 instanceof Boolean && expr2 instanceof Boolean)
            return (Boolean) expr1 || (Boolean) expr2;

        throw new RuntimeException("Invalid operands for logical OR at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpAnd(SymNoteParser.OpAndContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if(expr1 instanceof Boolean && expr2 instanceof Boolean)
            return (Boolean) expr1 && (Boolean) expr2;

        throw new RuntimeException("Invalid operands for logical AND at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpNot(SymNoteParser.OpNotContext ctx) {
        Object expr = visit(ctx.expression());
        if(expr instanceof Boolean)
            return !(Boolean) expr;

        throw new RuntimeException("Invalid operand for logical NOT at line " + ctx.getStart().getLine());
    }


    // Numer operations

    @Override
    public Object visitOpAddSub(SymNoteParser.OpAddSubContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if(expr1 instanceof String || expr2 instanceof String){
            if (ctx.ADD() == null)
                throw new RuntimeException("Cannot subtract strings at line " + ctx.getStart().getLine());
            return expr1.toString() + expr2.toString();
        }

        if(expr1 instanceof Number && expr2 instanceof Number) {
            float val1 = ((Number) expr1).floatValue();
            float val2 = ((Number) expr2).floatValue();
            return ctx.ADD() != null ? val1 + val2 : val1 - val2;
        }

        throw new RuntimeException("Invalid operands for addition/subtraction at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpMulDivMod(SymNoteParser.OpMulDivModContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if (ctx.MOD() != null) {
            if (expr1 instanceof Integer && expr2 instanceof Integer) {
                int val1 = (Integer) expr1;
                int val2 = (Integer) expr2;
                return val1 % val2;
            }
            throw new RuntimeException("Invalid operands for modulus at line " + ctx.getStart().getLine());
        }

        if (expr1 instanceof Number && expr2 instanceof Number) {
            float val1 = ((Number) expr1).floatValue();
            float val2 = ((Number) expr2).floatValue();
            return ctx.MUL() != null ? val1 * val2 : val1 / val2;
        }

        if(expr1 instanceof String && expr2 instanceof Integer){
            if (ctx.MUL() == null)
                throw new RuntimeException("Cannot divide strings at line " + ctx.getStart().getLine());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < (Integer) expr2; i++){
                sb.append(expr1);
            }
            return sb.toString();
        }

        throw new RuntimeException("Invalid operands for multiplication/division at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpUnaryMinus(SymNoteParser.OpUnaryMinusContext ctx) {
        Object expr = visit(ctx.expression());
        if(expr instanceof Number) {
            float val = ((Number) expr).floatValue();
            return -val;
        }
        throw new RuntimeException("Invalid operand for unary minus at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpCompare(SymNoteParser.OpCompareContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if(expr1 instanceof Number && expr2 instanceof Number) {
            float val1 = ((Number) expr1).floatValue();
            float val2 = ((Number) expr2).floatValue();
            if (ctx.EQ() != null) return val1 == val2;
            else if (ctx.NE() != null) return val1 != val2;
            else if (ctx.LT() != null) return val1 < val2;
            else if (ctx.LE() != null) return val1 <= val2;
            else if (ctx.GT() != null) return val1 > val2;
            else if (ctx.GE() != null) return val1 >= val2;
        }

        if(expr1 instanceof String && expr2 instanceof String) {
            int cmp = ((String) expr1).compareTo((String) expr2);
            if (ctx.EQ() != null) return cmp == 0;
            else if (ctx.NE() != null) return cmp != 0;
            else if (ctx.LT() != null) return cmp < 0;
            else if (ctx.LE() != null) return cmp <= 0;
            else if (ctx.GT() != null) return cmp > 0;
            else if (ctx.GE() != null) return cmp >= 0;
        }

        if(expr1 instanceof Boolean && expr2 instanceof Boolean) {
            boolean val1 = (Boolean) expr1;
            boolean val2 = (Boolean) expr2;
            if (ctx.EQ() != null) return val1 == val2;
            else if (ctx.NE() != null) return val1 != val2;
        }

        throw new RuntimeException("Invalid operands for comparison at line " + ctx.getStart().getLine());
    }

}
