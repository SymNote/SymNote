// Generated from SymNote.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SymNoteParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SymNoteVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SymNoteParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#topLevelElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopLevelElement(SymNoteParser.TopLevelElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(SymNoteParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(SymNoteParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(SymNoteParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStmt(SymNoteParser.LoopStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parallelStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParallelStmt(SymNoteParser.ParallelStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declAssignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(SymNoteParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(SymNoteParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(SymNoteParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#routineDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineDecl(SymNoteParser.RoutineDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#trackDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrackDecl(SymNoteParser.TrackDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(SymNoteParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(SymNoteParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(SymNoteParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#blockLVL1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockLVL1(SymNoteParser.BlockLVL1Context ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#blockLVL2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockLVL2(SymNoteParser.BlockLVL2Context ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#gridStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGridStmt(SymNoteParser.GridStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gridNote}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGridNote(SymNoteParser.GridNoteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gridRest}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGridRest(SymNoteParser.GridRestContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gridHold}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGridHold(SymNoteParser.GridHoldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gridChord}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGridChord(SymNoteParser.GridChordContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#noteElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoteElement(SymNoteParser.NoteElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opNot}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpNot(SymNoteParser.OpNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomInt}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomInt(SymNoteParser.AtomIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opMulDivMod}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpMulDivMod(SymNoteParser.OpMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opAnd}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpAnd(SymNoteParser.OpAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExpr(SymNoteParser.FuncCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opCompare}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpCompare(SymNoteParser.OpCompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(SymNoteParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomId}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomId(SymNoteParser.AtomIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opOr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpOr(SymNoteParser.OpOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomBool}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomBool(SymNoteParser.AtomBoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opAddSub}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpAddSub(SymNoteParser.OpAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomString}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomString(SymNoteParser.AtomStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomFloat}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomFloat(SymNoteParser.AtomFloatContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymNoteParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SymNoteParser.TypeContext ctx);
}