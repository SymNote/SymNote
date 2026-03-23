public class MySymNoteVisitor extends SymNoteBaseVisitor<Object> {
    @Override
    public Object visitProgram(SymNoteParser.ProgramContext ctx) {
        System.out.println("Visiting program");
        return super.visitProgram(ctx);
    }

    @Override
    public Object visitIfStmt(SymNoteParser.IfStmtContext ctx) {
        System.out.println("Visiting if statement");
        return super.visitIfStmt(ctx);
    }

     @Override
    public Object visitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        System.out.println("Visiting while statement");
        return super.visitWhileStmt(ctx);
    }

     @Override
    public Object visitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        System.out.println("Visiting loop statement");
        return super.visitLoopStmt(ctx);
    }

     @Override
    public Object visitParallelStmt(SymNoteParser.ParallelStmtContext ctx) {
        System.out.println("Visiting parallel statement");
        return super.visitParallelStmt(ctx);
    }
}
