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

    public Map<String, String> getGlobalVariableTypes() {
        return globalVariableTypes;
    }

    public Set<String> getDeclaredVariables() {
        return declaredVariables;
    }

    private void pushScope() {
        scopeStack.push(new HashMap<>());
    }

    private void popScope() {
        if (!scopeStack.isEmpty()) {
            scopeStack.pop();
        }
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


    @Override
    public void enterProgram(SymNoteParser.ProgramContext ctx) { pushScope(); }


    // Declarations
    @Override
    public void enterRoutineDecl(SymNoteParser.RoutineDeclContext ctx) {
        pushScope();
        registerParameters(ctx.parameters());
    }
    @Override
    public void exitRoutineDecl(SymNoteParser.RoutineDeclContext ctx) { popScope();}

    @Override
    public void enterTrackDecl(SymNoteParser.TrackDeclContext ctx) {
        pushScope();
        registerParameters(ctx.parameters());
    }
    @Override
    public void exitTrackDecl(SymNoteParser.TrackDeclContext ctx) { popScope();}


    @Override
    public void enterDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx) {
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }



    // Level 1
    @Override
    public void enterBlockLVL1(SymNoteParser.BlockLVL1Context ctx) { pushScope();}
    @Override
    public void exitBlockLVL1(SymNoteParser.BlockLVL1Context ctx) { popScope(); }

    @Override
    public void enterIfStmt(SymNoteParser.IfStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfStmt(SymNoteParser.IfStmtContext ctx) { popScope();}

    @Override
    public void enterWhileStmt(SymNoteParser.WhileStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileStmt(SymNoteParser.WhileStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopStmt(SymNoteParser.LoopStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopStmt(SymNoteParser.LoopStmtContext ctx) { popScope(); }


    // Level 1 + Iteration
    @Override
    public void enterBlockIterationLVL1(SymNoteParser.BlockIterationLVL1Context ctx) { pushScope(); }
    @Override
    public void exitBlockIterationLVL1(SymNoteParser.BlockIterationLVL1Context ctx) { popScope(); }

    @Override
    public void enterIfIterationStmt(SymNoteParser.IfIterationStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfIterationStmt(SymNoteParser.IfIterationStmtContext ctx) { popScope(); }

    @Override
    public void enterWhileIterationStmt(SymNoteParser.WhileIterationStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileIterationStmt(SymNoteParser.WhileIterationStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopIterationStmt(SymNoteParser.LoopIterationStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopIterationStmt(SymNoteParser.LoopIterationStmtContext ctx) { popScope(); }


    // Routine
    @Override
    public void enterBlockRoutine(SymNoteParser.BlockRoutineContext ctx) {
        boolean isTopLevel = ctx.getParent() instanceof SymNoteParser.RoutineDeclContext;
        if (!isTopLevel) pushScope(); 
    }

    @Override
    public void exitBlockRoutine(SymNoteParser.BlockRoutineContext ctx) {
        boolean isTopLevel = ctx.getParent() instanceof SymNoteParser.RoutineDeclContext;
        if (!isTopLevel) popScope(); 
    }

    @Override
    public void enterIfRoutineStmt(SymNoteParser.IfRoutineStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfRoutineStmt(SymNoteParser.IfRoutineStmtContext ctx) { popScope(); }

    @Override
    public void enterWhileRoutineStmt(SymNoteParser.WhileRoutineStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileRoutineStmt(SymNoteParser.WhileRoutineStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopRoutineStmt(SymNoteParser.LoopRoutineStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopRoutineStmt(SymNoteParser.LoopRoutineStmtContext ctx) { popScope();}


    // Routing + Iteration
    @Override
    public void enterBlockIterationRoutine(SymNoteParser.BlockIterationRoutineContext ctx) { pushScope(); }
    @Override
    public void exitBlockIterationRoutine(SymNoteParser.BlockIterationRoutineContext ctx) { popScope(); }

    @Override
    public void enterIfIterationRoutineStmt(SymNoteParser.IfIterationRoutineStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfIterationRoutineStmt(SymNoteParser.IfIterationRoutineStmtContext ctx) { popScope(); }

    @Override
    public void enterWhileIterationRoutineStmt(SymNoteParser.WhileIterationRoutineStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileIterationRoutineStmt(SymNoteParser.WhileIterationRoutineStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopIterationRoutineStmt(SymNoteParser.LoopIterationRoutineStmtContext ctx) {
        pushScope();
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopIterationRoutineStmt(SymNoteParser.LoopIterationRoutineStmtContext ctx) { popScope(); }


    // Track
    @Override
    public void enterBlockTrack(SymNoteParser.BlockTrackContext ctx) {
        boolean isTopLevel = ctx.getParent() instanceof SymNoteParser.TrackDeclContext;
        if (!isTopLevel) pushScope(); 
    }

    @Override
    public void exitBlockTrack(SymNoteParser.BlockTrackContext ctx) {
        boolean isTopLevel = ctx.getParent() instanceof SymNoteParser.TrackDeclContext;
        if (!isTopLevel) popScope(); 
    }

    @Override
    public void enterIfTrackStmt(SymNoteParser.IfTrackStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfTrackStmt(SymNoteParser.IfTrackStmtContext ctx) { popScope(); }

    @Override
    public void enterWhileTrackStmt(SymNoteParser.WhileTrackStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileTrackStmt(SymNoteParser.WhileTrackStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopTrackStmt(SymNoteParser.LoopTrackStmtContext ctx) {
        pushScope(); //?
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopTrackStmt(SymNoteParser.LoopTrackStmtContext ctx) { popScope(); }


    // Track + Iteration
    @Override
    public void enterBlockIterationTrack(SymNoteParser.BlockIterationTrackContext ctx) { pushScope(); }
    @Override
    public void exitBlockIterationTrack(SymNoteParser.BlockIterationTrackContext ctx) { popScope(); }

    @Override
    public void enterIfIterationTrackStmt(SymNoteParser.IfIterationTrackStmtContext ctx) { pushScope(); }
    @Override
    public void exitIfIterationTrackStmt(SymNoteParser.IfIterationTrackStmtContext ctx) { popScope(); }

    @Override
    public void enterWhileIterationTrackStmt(SymNoteParser.WhileIterationTrackStmtContext ctx) { pushScope(); }
    @Override
    public void exitWhileIterationTrackStmt(SymNoteParser.WhileIterationTrackStmtContext ctx) { popScope(); }

    @Override
    public void enterLoopIterationTrackStmt(SymNoteParser.LoopIterationTrackStmtContext ctx) {
        pushScope(); //?
        defineInCurrentScope(ctx.ID().getText(), ctx.type().getText(), ctx.ID().getSymbol());
    }
    @Override
    public void exitLoopIterationTrackStmt(SymNoteParser.LoopIterationTrackStmtContext ctx) { popScope(); }
}