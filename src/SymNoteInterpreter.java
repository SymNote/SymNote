import audio.AudioEngine;
import audio.Synthesizer;
import environment.Environment;

public class SymNoteInterpreter extends SymNoteBaseVisitor<Object> {
    public Environment env = new Environment();
    public AudioEngine audioEngine = new AudioEngine();
    public int bpm = 120;
    public Synthesizer currentSynth = null;

    private final GridExecutor gridExecutor;
    private final FlowExecutor flowExecutor;

    public SymNoteInterpreter() {
        this.gridExecutor = new GridExecutor(this);
        this.flowExecutor = new FlowExecutor(this);
    }

    @Override
    public Object visitProgram(SymNoteParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
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
        return env.get(ctx.ID().getText());
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
    public Object visitCallExpr(SymNoteParser.CallExprContext ctx) {
        return flowExecutor.executeCall(ctx);
    }

    // --- Control Flow ---
    @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        String varName = ctx.ID().getText();
        int from = (Integer) visit(ctx.e1);
        int to = (Integer) visit(ctx.e2);

        for (int i = from; i <= to; i++) {
            env.define(varName, i);
            visit(ctx.statementLVL1());
        }
        return null;
    }

    @Override
    public Object visitLoopStmtLVL2(SymNoteParser.LoopStmtLVL2Context ctx) {
        String varName = ctx.ID().getText();
        int from = (Integer) visit(ctx.e1);
        int to = (Integer) visit(ctx.e2);

        for (int i = from; i <= to; i++) {
            env.define(varName, i);
            visit(ctx.statementLVL2());
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
        return visit(ctx.gridStmt());
    }

    @Override
    public Object visitGridStmt(SymNoteParser.GridStmtContext ctx) {
        return gridExecutor.executeGridStmt(ctx);
    }
}
