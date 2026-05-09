import environment.Environment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import environment.Variable;
import gen.SymNoteBaseVisitor;
import gen.SymNoteParser;
import midi.SymNoteTimeline;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

public class SymNoteInterpreter extends SymNoteBaseVisitor<Object> {
    private static final int TICKS_PER_BEAT = 480;

    public Environment env = new Environment();
    public float bpm = 120;
    public String currentSynthName = "piano";
    public long currentTick = 0;
    public OutputCollector output;

    private final Set<String> declaredVariables;
    private final SymNoteTimeline timeline = new SymNoteTimeline(TICKS_PER_BEAT);
    private final FlowExecutor flowExecutor;
    private final GridExecutor gridExecutor;

    public Map<String, SymNoteParser.RoutineDeclContext> routines = new HashMap<>();

    public SymNoteInterpreter() {
        this(new HashSet<>(), new StdoutCollector());
    }

    public SymNoteInterpreter(Set<String> declaredVariables) {
        this(declaredVariables, new StdoutCollector());
    }

    public SymNoteInterpreter(Set<String> declaredVariables, OutputCollector output) {
        this.declaredVariables = new HashSet<>(declaredVariables);
        this.output = output;
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
    public Object visitBlockTrack(SymNoteParser.BlockTrackContext ctx) {
        Environment previousEnv = env;
        try {
            env = new Environment(previousEnv);
            return super.visitBlockTrack(ctx);
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
public void checkType(String type, Object value, String name, int line) {
        if (type.equals("int")) {
            if (!(value instanceof Integer))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("float")) {
            if (!(value instanceof Integer || value instanceof Float || value instanceof Double))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("string")) {
            if (!(value instanceof String))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("bool")) {
            if (!(value instanceof Boolean))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        }
    }

    public void checkTypeStrict(String type, Object value, String name, int line) {
        if (type.equals("int")) {
            if (!(value instanceof Integer))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("float")) {
            if (!(value instanceof Float || value instanceof Double))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("string")) {
            if (!(value instanceof String))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        } else if (type.equals("bool")) {
            if (!(value instanceof Boolean))
                throw new RuntimeException("Type mismatch for '" + name + "' at line " + line);
        }
    }

    @Override
    public Object visitDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx) {
        String name = ctx.ID().getText();
        String type = ctx.type().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        if (ctx.expression() != null && value == null) {
            throw new RuntimeException("Cannot assign void value to variable '" + name + "' at line " + ctx.getStart().getLine());
        }
        if (value != null) {
            int line = ctx.getStart().getLine();
            checkType(type, value, name, line);
            if (type.equals("int")) {
                env.define(name, new Variable(type, ((Number) value).intValue()));
                return null;
            }
            if (type.equals("float")) {
                env.define(name, new Variable(type, ((Number) value).floatValue()));
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
        if (value == null) {
            throw new RuntimeException("Cannot assign void value to variable '" + ctx.ID().getText() + "' at line " + ctx.getStart().getLine());
        }
        String type = env.get(ctx.ID().getText()).type;
        String name = ctx.ID().getText();
        checkType(type, value, name, ctx.getStart().getLine());

        if (type.equals("int")) {
            env.assign(name, ((Number) value).intValue());
            return null;
        }
        if (type.equals("float")) {
            env.assign(name, ((Number) value).floatValue());
            return null;
        }
        env.assign(name, value);
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
    public Object visitAtomBool(SymNoteParser.AtomBoolContext ctx) {
        if (ctx.BOOL().getText().equals("true"))
            return true;
        else if (ctx.BOOL().getText().equals("false"))
            return false;
        throw new RuntimeException("Invalid boolean value at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitAtomId(SymNoteParser.AtomIdContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());

        Object val = env.get(name).value;
        if(val == null){
            throw new RuntimeException("Variable '" + name + "' is used before being initialized at line " + ctx.getStart().getLine());
        }
        return val;
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
    public Object visitRoutineDecl(SymNoteParser.RoutineDeclContext ctx) {
        routines.put(ctx.ID().getText(), ctx);
        return null;
    }

    @Override
    public Object visitReturnRoutineStmt(SymNoteParser.ReturnRoutineStmtContext ctx) {
        Object value = null;
        if (ctx.expression() != null) {
            value = visit(ctx.expression());
        }
        throw new ReturnException(value);
    }

    @Override
    public Object visitReturnIterationRoutineStmt(SymNoteParser.ReturnIterationRoutineStmtContext ctx) {
        Object value = null;
        if (ctx.expression() != null) {
            value = visit(ctx.expression());
        }
        throw new ReturnException(value);
    }

    @Override
    public Object visitTrackDecl(SymNoteParser.TrackDeclContext ctx) {
        env.defineTrack(ctx.ID().getText(), ctx);
        return null;
    }

    @Override
    public Object visitCallExpr(SymNoteParser.CallExprContext ctx) {
        return flowExecutor.executeCall(ctx);
    }


    // --- Control Flow ---
    Object executeBlockIteration(ParserRuleContext ctx) {
        for (var child : ctx.children) {
            Object result = visit(child);
            if (result != null && (result.equals("BREAK") || result.equals("CONTINUE"))) {
                return result;
            }
        }
        return null;
    }

    @Override
    public Object visitBlockIterationLVL1(SymNoteParser.BlockIterationLVL1Context ctx) {
        return executeBlockIteration(ctx);
    }

    @Override
    public Object visitBlockIterationRoutine(SymNoteParser.BlockIterationRoutineContext ctx) {
        return executeBlockIteration(ctx);
    }

    @Override
    public Object visitBlockIterationTrack(SymNoteParser.BlockIterationTrackContext ctx) {
        return executeBlockIteration(ctx);
    }


    // - If -
    Object executeIfStmt(SymNoteParser.ExpressionContext expression, ParserRuleContext trueBranch,
                         ParserRuleContext falseBranch, Integer line) {
        Object condition = visit(expression);

        if (!(condition instanceof Boolean)) {
            throw new RuntimeException(
                    "Condition in 'if' statement must evaluate to a boolean at line " + line);
        }

        Object result = null;
        if ((Boolean) condition) {
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                result = visit(trueBranch);
            } finally {
                env = previousEnv;
            }
        } else if (falseBranch != null) {
            Environment previousEnv = env;
            try {
                env = new Environment(previousEnv);
                result = visit(falseBranch);
            } finally {
                env = previousEnv;
            }
        }
        return (result != null && (result.equals("BREAK") || result.equals("CONTINUE"))) ? result : null;
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.statementLVL1(0), ctx.statementLVL1(1), ctx.getStart().getLine());
    }

    @Override
    public Object visitIfIterationStmt(SymNoteParser.IfIterationStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.iterationStatementLVL1(0), ctx.iterationStatementLVL1(1), ctx.getStart().getLine());
    }

    @Override
    public Object visitIfRoutineStmt(SymNoteParser.IfRoutineStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.statementRoutine(0), ctx.statementRoutine(1), ctx.getStart().getLine());
    }

    @Override
    public Object visitIfIterationRoutineStmt(SymNoteParser.IfIterationRoutineStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.iterationStatementRoutine(0), ctx.iterationStatementRoutine(1), ctx.getStart().getLine());
    }

    @Override
    public Object visitIfTrackStmt(SymNoteParser.IfTrackStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.statementTrack(0), ctx.statementTrack(1), ctx.getStart().getLine());
    }

    @Override
    public Object visitIfIterationTrackStmt(SymNoteParser.IfIterationTrackStmtContext ctx) {
        return executeIfStmt(ctx.expression(), ctx.iterationStatementTrack(0), ctx.iterationStatementTrack(1), ctx.getStart().getLine());
    }


    // - While -
    Object executeWhileStmt(SymNoteParser.ExpressionContext expression, ParserRuleContext body, Integer line){
        while (true) {
            Object condition = visit(expression);
            if (!(condition instanceof Boolean))
                throw new RuntimeException("Condition must be boolean at line " + line);
            if (!(Boolean) condition) break;

            Environment prev = env;

            try {
                env = new Environment(prev);
                Object result = visit(body);

                if (result != null && result.toString().equals("BREAK")) {
                    break;
                }
            } finally {
                env = prev;
            }
        }
        return null;
    }

    @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementLVL1(), ctx.getStart().getLine());
    }

    @Override
    public Object visitWhileIterationStmt(SymNoteParser.WhileIterationStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementLVL1(), ctx.getStart().getLine());
    }

    @Override
    public Object visitWhileRoutineStmt(SymNoteParser.WhileRoutineStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementRoutine(), ctx.getStart().getLine());
    }

    @Override
    public Object visitWhileIterationRoutineStmt(SymNoteParser.WhileIterationRoutineStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementRoutine(), ctx.getStart().getLine());
    }

    @Override
    public Object visitWhileTrackStmt(SymNoteParser.WhileTrackStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementTrack(), ctx.getStart().getLine());
    }

    @Override
    public Object visitWhileIterationTrackStmt(SymNoteParser.WhileIterationTrackStmtContext ctx) {
        return executeWhileStmt(ctx.expression(), ctx.iterationStatementTrack(), ctx.getStart().getLine());
    }

    // - Loop -
    Object executeLoopStmt(String varName, ParserRuleContext e1, ParserRuleContext e2, ParserRuleContext body,  Integer line){
        Object from = visit(e1);
        Object to = visit(e2);

        if (!(from instanceof Integer)) throw new RuntimeException("Loop start value must be an integer at line " + line);
        if (!(to instanceof Integer)) throw new RuntimeException("Loop end value must be an integer at line " + line);

        int fromVal = (Integer) from;
        int toVal = (Integer) to;

        for (int i = fromVal; i <= toVal; i++) {
            Environment previousEnv = env;

            try {
                env = new Environment(previousEnv);
                env.define(varName, new Variable("int", i));

                Object result = visit(body);

                // Update loop variable in case it was modified inside the loop
                i = (Integer) env.get(varName).value;

                // Re-evaluate loop end condition in case it was modified inside the loop
                to = visit(e2);
                if (!(to instanceof Integer))
                    throw new RuntimeException("Loop end value must be an integer at line " + line);
                toVal = (Integer) to;

                if (result != null && result.toString().equals("BREAK")) {
                    break;
                }
            } finally {
                env = previousEnv;
            }
        }
        return null;
    }

    @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementLVL1(), ctx.getStart().getLine());
    }

    @Override
    public Object visitLoopIterationStmt(SymNoteParser.LoopIterationStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementLVL1(), ctx.getStart().getLine());
    }

    @Override
    public Object visitLoopRoutineStmt(SymNoteParser.LoopRoutineStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementRoutine(), ctx.getStart().getLine());
    }

    @Override
    public Object visitLoopIterationRoutineStmt(SymNoteParser.LoopIterationRoutineStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementRoutine(), ctx.getStart().getLine());
    }

    @Override
    public Object visitLoopTrackStmt(SymNoteParser.LoopTrackStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementTrack(), ctx.getStart().getLine());
    }

    @Override
    public Object visitLoopIterationTrackStmt(SymNoteParser.LoopIterationTrackStmtContext ctx) {
        return executeLoopStmt(ctx.ID().getText(), ctx.e1, ctx.e2, ctx.iterationStatementTrack(), ctx.getStart().getLine());
    }

    // --- Break & Continue ---
    @Override
    public Object visitBreakStmt(SymNoteParser.BreakStmtContext ctx) {
        return "BREAK";
    }

    @Override
    public Object visitContinueStmt(SymNoteParser.ContinueStmtContext ctx) {
        return "CONTINUE";
    }

    // --- Parallel ---
    @Override
    public Object visitParallelStmt(SymNoteParser.ParallelStmtContext ctx) {
        return flowExecutor.executeParallel(ctx);
    }

    @Override
    public Object visitParallelIterationStmt(SymNoteParser.ParallelIterationStmtContext ctx) {
        return flowExecutor.executeParallel(ctx);
    }

    // --- Grid / Time Execution ---
    @Override
    public Object visitGridTrackStmt(SymNoteParser.GridTrackStmtContext ctx) {
        return gridExecutor.executeGridStmt(ctx.gridStmt());
    }

    public Object visitGridIterationTrackStmt(SymNoteParser.GridIterationTrackStmtContext ctx) {
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
    public Object visitOpOr(SymNoteParser.OpOrContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        
        if (!(expr1 instanceof Boolean)) {
            throw new RuntimeException("Invalid operands for logical OR at line " + ctx.getStart().getLine());
        }
        
        if ((Boolean) expr1) {
            return true;
        }

        Object expr2 = visit(ctx.expression(1));
        if (!(expr2 instanceof Boolean)) {
            throw new RuntimeException("Invalid operands for logical OR at line " + ctx.getStart().getLine());
        }
        
        return expr2;
    }

    @Override
    public Object visitOpAnd(SymNoteParser.OpAndContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        
        if (!(expr1 instanceof Boolean)) {
            throw new RuntimeException("Invalid operands for logical AND at line " + ctx.getStart().getLine());
        }
        
        if (!(Boolean) expr1) {
            return false;
        }

        Object expr2 = visit(ctx.expression(1));
        if (!(expr2 instanceof Boolean)) {
            throw new RuntimeException("Invalid operands for logical AND at line " + ctx.getStart().getLine());
        }
        
        return expr2;
    }

    @Override
    public Object visitOpNot(SymNoteParser.OpNotContext ctx) {
        Object expr = visit(ctx.expression());
        if (expr instanceof Boolean)
            return !(Boolean) expr;

        throw new RuntimeException("Invalid operand for logical NOT at line " + ctx.getStart().getLine());
    }

    // Numer operations

    @Override
    public Object visitOpAddSub(SymNoteParser.OpAddSubContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if (expr1 instanceof String || expr2 instanceof String) {
            if (ctx.ADD() == null) {
                throw new RuntimeException("Cannot subtract strings at line " + ctx.getStart().getLine());
            }
            return String.valueOf(expr1) + String.valueOf(expr2);
        }

        if (expr1 instanceof Number && expr2 instanceof Number) {
            if (expr1 instanceof Integer && expr2 instanceof Integer) {
                int val1 = (Integer) expr1;
                int val2 = (Integer) expr2;
                return ctx.ADD() != null ? val1 + val2 : val1 - val2;
            }
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

        // Modulus operation
        if (ctx.MOD() != null) {
            if (expr1 instanceof Integer && expr2 instanceof Integer) {
                int val1 = (Integer) expr1;
                int val2 = (Integer) expr2;
                if (val2 == 0)
                    throw new ArithmeticException("Division by zero in modulus operation at line " + ctx.getStart().getLine());
                return val1 % val2;
            }
            throw new RuntimeException("Invalid operands for modulus at line " + ctx.getStart().getLine());
        }

        // Integer multiplication/division
        if (expr1 instanceof Integer && expr2 instanceof Integer) {
            int val1 = (Integer) expr1;
            int val2 = (Integer) expr2;
            if (ctx.DIV() != null) {
                if (val2 == 0)
                    throw new ArithmeticException("Division by zero at line " + ctx.getStart().getLine());
                return val1 / val2;
            }
            return val1 * val2;
        }

        // Floating point multiplication/division
        if (expr1 instanceof Number && expr2 instanceof Number) {
            float val1 = ((Number) expr1).floatValue();
            float val2 = ((Number) expr2).floatValue();
            if (ctx.DIV() != null) {
                if (val2 == 0)
                    throw new ArithmeticException("Division by zero at line " + ctx.getStart().getLine());
                return val1 / val2;
            }
            return val1 * val2;
        }

        // String repetition
        if (expr1 instanceof String && expr2 instanceof Integer && ctx.MUL() != null) {
            StringBuilder sb = new StringBuilder();
            sb.repeat(String.valueOf(expr1), Math.abs((Integer) expr2));

            if ((Integer) expr2 < 0) return sb.reverse().toString();
            return sb.toString();
        }
        if (expr2 instanceof String && expr1 instanceof Integer && ctx.MUL() != null) {
            StringBuilder sb = new StringBuilder();
            sb.repeat(String.valueOf(expr2), Math.abs((Integer) expr1));

            if ((Integer) expr1 < 0) return sb.reverse().toString();
            return sb.toString();
        }

        throw new RuntimeException("Invalid operands for multiplication/division at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpUnaryMinusPlus(SymNoteParser.OpUnaryMinusPlusContext ctx) {
        Object expr = visit(ctx.expression());
        if (expr instanceof Integer) {
            int val = (Integer) expr;
            if (ctx.SUB() != null)
                return -val;
            if (ctx.ADD() != null)
                return val;
        }
        if (expr instanceof Number) {
            float val = ((Number) expr).floatValue();
            if (ctx.SUB() != null)
                return -val;
            if (ctx.ADD() != null)
                return val;
        }
        throw new RuntimeException("Invalid operand for unary minus at line " + ctx.getStart().getLine());
    }

    @Override
    public Object visitOpCompare(SymNoteParser.OpCompareContext ctx) {
        Object expr1 = visit(ctx.expression(0));
        Object expr2 = visit(ctx.expression(1));

        if (expr1 instanceof Number && expr2 instanceof Number) {
            float val1 = ((Number) expr1).floatValue();
            float val2 = ((Number) expr2).floatValue();
            if (ctx.EQ() != null)
                return val1 == val2;
            else if (ctx.NE() != null)
                return val1 != val2;
            else if (ctx.LT() != null)
                return val1 < val2;
            else if (ctx.LE() != null)
                return val1 <= val2;
            else if (ctx.GT() != null)
                return val1 > val2;
            else if (ctx.GE() != null)
                return val1 >= val2;
        }

        if (expr1 instanceof String && expr2 instanceof String) {
            int cmp = ((String) expr1).compareTo((String) expr2);
            if (ctx.EQ() != null)
                return cmp == 0;
            else if (ctx.NE() != null)
                return cmp != 0;
            else if (ctx.LT() != null)
                return cmp < 0;
            else if (ctx.LE() != null)
                return cmp <= 0;
            else if (ctx.GT() != null)
                return cmp > 0;
            else if (ctx.GE() != null)
                return cmp >= 0;
        }

        if (expr1 instanceof Boolean && expr2 instanceof Boolean) {
            boolean val1 = (Boolean) expr1;
            boolean val2 = (Boolean) expr2;
            if (ctx.EQ() != null)
                return val1 == val2;
            else if (ctx.NE() != null)
                return val1 != val2;
        }

        throw new RuntimeException("Invalid operands for comparison at line " + ctx.getStart().getLine());
    }

    // increment and decrement
    @Override
    public Object visitPostDec(SymNoteParser.PostDecContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());

        if (!env.get(name).type.equals("int"))
            throw new RuntimeException(
                    "Post-decrement can only be applied to integers at line " + ctx.getStart().getLine());
        if (env.get(name).value == null)
            throw new RuntimeException("Cannot apply post-decrement to uninitialized variable '" + name + "' at line "
                    + ctx.getStart().getLine());

        int value = ((Number) env.get(name).value).intValue();
        env.assign(name, value - 1);
        return value;
    }

    @Override
    public Object visitPreDec(SymNoteParser.PreDecContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());

        if (!env.get(name).type.equals("int"))
            throw new RuntimeException(
                    "Pre-decrement can only be applied to integers at line " + ctx.getStart().getLine());
        if (env.get(name).value == null)
            throw new RuntimeException("Cannot apply pre-decrement to uninitialized variable '" + name + "' at line "
                    + ctx.getStart().getLine());

        int value = ((Number) env.get(name).value).intValue();
        env.assign(name, value - 1);
        return value - 1;
    }

    @Override
    public Object visitPostInc(SymNoteParser.PostIncContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());

        if (!env.get(name).type.equals("int"))
            throw new RuntimeException(
                    "Post-increment can only be applied to integers at line " + ctx.getStart().getLine());
        if (env.get(name).value == null)
            throw new RuntimeException("Cannot apply post-increment to uninitialized variable '" + name + "' at line "
                    + ctx.getStart().getLine());

        int value = ((Number) env.get(name).value).intValue();
        env.assign(name, value + 1);
        return value;
    }

    @Override
    public Object visitPreInc(SymNoteParser.PreIncContext ctx) {
        String name = ctx.ID().getText();
        validateVariableDeclared(name, ctx.getStart().getLine());

        if (!env.get(name).type.equals("int"))
            throw new RuntimeException(
                    "Pre-increment can only be applied to integers at line " + ctx.getStart().getLine());
        if (env.get(name).value == null)
            throw new RuntimeException("Cannot apply pre-increment to uninitialized variable '" + name + "' at line "
                    + ctx.getStart().getLine());

        int value = ((Number) env.get(name).value).intValue();
        env.assign(name, value + 1);
        return value + 1;
    }

    @Override
    public Object visitStandaloneExpr(SymNoteParser.StandaloneExprContext ctx) {
        return visit(ctx.expression());
    }

    // Empty Statements (Allows double semicolons)
    @Override
    public Object visitEmptyStmt(SymNoteParser.EmptyStmtContext ctx) { return null; }


}
