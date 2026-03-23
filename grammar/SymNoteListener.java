// Generated from SymNote.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SymNoteParser}.
 */
public interface SymNoteListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SymNoteParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SymNoteParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#topLevelElement}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelElement(SymNoteParser.TopLevelElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#topLevelElement}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelElement(SymNoteParser.TopLevelElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(SymNoteParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(SymNoteParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(SymNoteParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(SymNoteParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(SymNoteParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(SymNoteParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStmt(SymNoteParser.LoopStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStmt(SymNoteParser.LoopStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parallelStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterParallelStmt(SymNoteParser.ParallelStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parallelStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitParallelStmt(SymNoteParser.ParallelStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declAssignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declAssignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitDeclAssignStmt(SymNoteParser.DeclAssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(SymNoteParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(SymNoteParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(SymNoteParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(SymNoteParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(SymNoteParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link SymNoteParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(SymNoteParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#routineDecl}.
	 * @param ctx the parse tree
	 */
	void enterRoutineDecl(SymNoteParser.RoutineDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#routineDecl}.
	 * @param ctx the parse tree
	 */
	void exitRoutineDecl(SymNoteParser.RoutineDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#trackDecl}.
	 * @param ctx the parse tree
	 */
	void enterTrackDecl(SymNoteParser.TrackDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#trackDecl}.
	 * @param ctx the parse tree
	 */
	void exitTrackDecl(SymNoteParser.TrackDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(SymNoteParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(SymNoteParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(SymNoteParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(SymNoteParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(SymNoteParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(SymNoteParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#blockLVL1}.
	 * @param ctx the parse tree
	 */
	void enterBlockLVL1(SymNoteParser.BlockLVL1Context ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#blockLVL1}.
	 * @param ctx the parse tree
	 */
	void exitBlockLVL1(SymNoteParser.BlockLVL1Context ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#blockLVL2}.
	 * @param ctx the parse tree
	 */
	void enterBlockLVL2(SymNoteParser.BlockLVL2Context ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#blockLVL2}.
	 * @param ctx the parse tree
	 */
	void exitBlockLVL2(SymNoteParser.BlockLVL2Context ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#gridStmt}.
	 * @param ctx the parse tree
	 */
	void enterGridStmt(SymNoteParser.GridStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#gridStmt}.
	 * @param ctx the parse tree
	 */
	void exitGridStmt(SymNoteParser.GridStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gridNote}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void enterGridNote(SymNoteParser.GridNoteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gridNote}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void exitGridNote(SymNoteParser.GridNoteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gridRest}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void enterGridRest(SymNoteParser.GridRestContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gridRest}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void exitGridRest(SymNoteParser.GridRestContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gridHold}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void enterGridHold(SymNoteParser.GridHoldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gridHold}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void exitGridHold(SymNoteParser.GridHoldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gridChord}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void enterGridChord(SymNoteParser.GridChordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gridChord}
	 * labeled alternative in {@link SymNoteParser#gridContent}.
	 * @param ctx the parse tree
	 */
	void exitGridChord(SymNoteParser.GridChordContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#noteElement}.
	 * @param ctx the parse tree
	 */
	void enterNoteElement(SymNoteParser.NoteElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#noteElement}.
	 * @param ctx the parse tree
	 */
	void exitNoteElement(SymNoteParser.NoteElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opNot}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpNot(SymNoteParser.OpNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opNot}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpNot(SymNoteParser.OpNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomInt}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomInt(SymNoteParser.AtomIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomInt}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomInt(SymNoteParser.AtomIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opMulDivMod}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpMulDivMod(SymNoteParser.OpMulDivModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opMulDivMod}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpMulDivMod(SymNoteParser.OpMulDivModContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opAnd}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpAnd(SymNoteParser.OpAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opAnd}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpAnd(SymNoteParser.OpAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(SymNoteParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(SymNoteParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opCompare}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpCompare(SymNoteParser.OpCompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opCompare}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpCompare(SymNoteParser.OpCompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(SymNoteParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(SymNoteParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomId}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomId(SymNoteParser.AtomIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomId}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomId(SymNoteParser.AtomIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opOr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpOr(SymNoteParser.OpOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opOr}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpOr(SymNoteParser.OpOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomBool}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomBool(SymNoteParser.AtomBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomBool}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomBool(SymNoteParser.AtomBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opAddSub}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpAddSub(SymNoteParser.OpAddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opAddSub}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpAddSub(SymNoteParser.OpAddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomString}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomString(SymNoteParser.AtomStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomString}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomString(SymNoteParser.AtomStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomFloat}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomFloat(SymNoteParser.AtomFloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomFloat}
	 * labeled alternative in {@link SymNoteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomFloat(SymNoteParser.AtomFloatContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymNoteParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SymNoteParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymNoteParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SymNoteParser.TypeContext ctx);
}