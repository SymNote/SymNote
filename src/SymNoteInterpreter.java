import environment.Environment;
import java.util.HashSet;
import java.util.Set;
import midi.SymNoteTimeline;

public class SymNoteInterpreter extends SymNoteBaseVisitor<Object> {
    private static final int TICKS_PER_BEAT = 480;

    public Environment env = new Environment();
    public int bpm = 120;
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

    public int getBpm() {
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
    @Override
    public Object visitDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx) {
        String name = ctx.ID().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        env.define(name, value);
        return null;
    }

    @Override
    public Object visitAssignStmt(SymNoteParser.AssignStmtContext ctx) {
        validateVariableDeclared(ctx.ID().getText(), ctx.getStart().getLine());
        env.assign(ctx.ID().getText(), visit(ctx.expression()));
        return null;
    }

    @Override
    public Object visitDeclAssignStmtLVL2(SymNoteParser.DeclAssignStmtLVL2Context ctx) {
        String name = ctx.ID().getText();
        Object value = ctx.expression() != null ? visit(ctx.expression()) : null;
        env.define(name, value);
        return null;
    }

    @Override
    public Object visitAssignStmtLVL2(SymNoteParser.AssignStmtLVL2Context ctx) {
        validateVariableDeclared(ctx.ID().getText(), ctx.getStart().getLine());
        env.assign(ctx.ID().getText(), visit(ctx.expression()));
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
        return env.get(name);
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
            env.define(varName, i);
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
            env.define(varName, i);
            visit(ctx.statementLVL2());
            env = previousEnv;
        }
        return null;
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        throw new RuntimeException("if is not implemented yet");
    }

    @Override
    public Object visitIfStmtLVL2(SymNoteParser.IfStmtLVL2Context ctx) {
        throw new RuntimeException("if is not implemented yet");
    }

    @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        throw new RuntimeException("while is not implemented yet");
    }

    @Override
    public Object visitWhileStmtLVL2(SymNoteParser.WhileStmtLVL2Context ctx) {
        throw new RuntimeException("while is not implemented yet");
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

}
