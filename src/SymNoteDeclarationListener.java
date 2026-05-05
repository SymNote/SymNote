import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gen.SymNoteBaseListener;
import gen.SymNoteParser;
import org.antlr.v4.runtime.Token;

public class SymNoteDeclarationListener extends SymNoteBaseListener {
    private final Deque<Map<String, String>> scopeStack = new ArrayDeque<>();
    private final Map<String, String> globalVariableTypes = new HashMap<>();
    private final Set<String> declaredVariables = new HashSet<>();

    @Override
    public void enterProgram(SymNoteParser.ProgramContext ctx) {
        pushScope();
    }

    @Override
    public void enterTrackDecl(SymNoteParser.TrackDeclContext ctx) {
        pushScope();
        registerParameters(ctx.parameters());
    }

    @Override
    public void exitTrackDecl(SymNoteParser.TrackDeclContext ctx) {
        popScope();
    }

    @Override
    public void enterBlockLVL1(SymNoteParser.BlockLVL1Context ctx) {
        pushScope();
    }

    @Override
    public void exitBlockLVL1(SymNoteParser.BlockLVL1Context ctx) {
        popScope();
    }

    @Override
    public void enterBlockLVL2(SymNoteParser.BlockLVL2Context ctx) {
        pushScope();
    }

    @Override
    public void exitBlockLVL2(SymNoteParser.BlockLVL2Context ctx) {
        popScope();
    }

    public Map<String, String> getGlobalVariableTypes() {
        return globalVariableTypes;
    }

    public Set<String> getDeclaredVariables() {
        return declaredVariables;
    }

    @Override
    public void enterDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx) {
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    @Override
    public void enterDeclAssignStmtLVL2(SymNoteParser.DeclAssignStmtLVL2Context ctx) {
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    @Override
    public void enterLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    @Override
    public void exitLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        popScope();
    }

    @Override
    public void enterLoopStmtLVL2(SymNoteParser.LoopStmtLVL2Context ctx) {
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    private void registerParameters(SymNoteParser.ParametersContext parameters) {
        if (parameters == null) {
            return;
        }

        for (SymNoteParser.ParamContext paramCtx : parameters.param()) {
            defineInCurrentScope(paramCtx.ID().getText(), paramCtx.type().getText(), paramCtx.ID().getSymbol());
        }
    }

    private void defineInCurrentScope(String name, String type, Token token) {
        Map<String, String> currentScope = scopeStack.peek();
        if (currentScope == null) {
            throw new RuntimeException("Internal declaration listener error: no active scope.");
        }

        if (currentScope.containsKey(name)) {
            throw new VariableAlreadyDefinedException(name, token.getLine());
        }

        currentScope.put(name, type);
        declaredVariables.add(name);
        if (scopeStack.size() == 1) {
            globalVariableTypes.put(name, type);
        }
    }

    private void pushScope() {
        scopeStack.push(new HashMap<>());
    }

    private void popScope() {
        if (!scopeStack.isEmpty()) {
            scopeStack.pop();
        }
    }

    @Override
    public void enterRoutineDecl(SymNoteParser.RoutineDeclContext ctx) {
        pushScope();
        registerParameters(ctx.parameters());
    }

    @Override
    public void exitRoutineDecl(SymNoteParser.RoutineDeclContext ctx) {
        popScope();
    }

    @Override
    public void enterBlockRoutine(SymNoteParser.BlockRoutineContext ctx) {
        pushScope();
    }

    @Override
    public void exitBlockRoutine(SymNoteParser.BlockRoutineContext ctx) {
        popScope();
    }

    @Override
    public void enterDeclAssignRoutineStmt(SymNoteParser.DeclAssignRoutineStmtContext ctx) {
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    @Override
    public void enterLoopRoutineStmt(SymNoteParser.LoopRoutineStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }

    @Override
    public void exitLoopRoutineStmt(SymNoteParser.LoopRoutineStmtContext ctx) {
        popScope();
    }

    @Override
    public void enterIfStmt(SymNoteParser.IfStmtContext ctx) {
        pushScope();
    }
    @Override
    public void exitIfStmt(SymNoteParser.IfStmtContext ctx) {
        popScope();
    }
    @Override
    public void enterWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        pushScope();
    }
    @Override
    public void exitWhileStmt(SymNoteParser.WhileStmtContext ctx) {
        popScope();
    }

    @Override
    public void enterIfStmtLVL2(SymNoteParser.IfStmtLVL2Context ctx) {
        pushScope();
    }
    @Override
    public void exitIfStmtLVL2(SymNoteParser.IfStmtLVL2Context ctx) {
        popScope();
    }
    @Override
    public void enterWhileStmtLVL2(SymNoteParser.WhileStmtLVL2Context ctx) {
        pushScope();
    }
    @Override
    public void exitWhileStmtLVL2(SymNoteParser.WhileStmtLVL2Context ctx) {
        popScope();
    }
}