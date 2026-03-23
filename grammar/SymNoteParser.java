// Generated from SymNote.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SymNoteParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, MUL=33, DIV=34, MOD=35, ADD=36, SUB=37, GT=38, LT=39, GE=40, 
		LE=41, EQ=42, NE=43, NOTE=44, BOOL=45, INT=46, FLOAT=47, STRING=48, ID=49, 
		WS=50, COMMENT=51;
	public static final int
		RULE_program = 0, RULE_topLevelElement = 1, RULE_statement = 2, RULE_routineDecl = 3, 
		RULE_trackDecl = 4, RULE_parameters = 5, RULE_param = 6, RULE_arguments = 7, 
		RULE_blockLVL1 = 8, RULE_blockLVL2 = 9, RULE_gridStmt = 10, RULE_gridContent = 11, 
		RULE_noteElement = 12, RULE_expression = 13, RULE_type = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "topLevelElement", "statement", "routineDecl", "trackDecl", 
			"parameters", "param", "arguments", "blockLVL1", "blockLVL2", "gridStmt", 
			"gridContent", "noteElement", "expression", "type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'('", "')'", "'else'", "'while'", "'loop'", "'from'", 
			"'to'", "'parallel'", "'{'", "'}'", "'='", "';'", "'return'", "'routine'", 
			"'returns'", "'track'", "','", "'grid'", "'~'", "'['", "']'", "'not'", 
			"'and'", "'or'", "'int'", "'float'", "'bool'", "'void'", "'note'", "'sample'", 
			"'synth'", "'*'", "'/'", "'%'", "'+'", "'-'", "'>'", "'<'", "'>='", "'<='", 
			"'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "MUL", "DIV", "MOD", 
			"ADD", "SUB", "GT", "LT", "GE", "LE", "EQ", "NE", "NOTE", "BOOL", "INT", 
			"FLOAT", "STRING", "ID", "WS", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SymNote.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SymNoteParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SymNoteParser.EOF, 0); }
		public List<TopLevelElementContext> topLevelElement() {
			return getRuleContexts(TopLevelElementContext.class);
		}
		public TopLevelElementContext topLevelElement(int i) {
			return getRuleContext(TopLevelElementContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090724066149990L) != 0)) {
				{
				setState(32);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__14:
				case T__16:
					{
					setState(30);
					topLevelElement();
					}
					break;
				case T__0:
				case T__1:
				case T__4:
				case T__5:
				case T__8:
				case T__9:
				case T__13:
				case T__22:
				case T__25:
				case T__26:
				case T__27:
				case T__28:
				case T__29:
				case T__30:
				case T__31:
				case BOOL:
				case INT:
				case FLOAT:
				case STRING:
				case ID:
					{
					setState(31);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TopLevelElementContext extends ParserRuleContext {
		public RoutineDeclContext routineDecl() {
			return getRuleContext(RoutineDeclContext.class,0);
		}
		public TrackDeclContext trackDecl() {
			return getRuleContext(TrackDeclContext.class,0);
		}
		public TopLevelElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topLevelElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitTopLevelElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TopLevelElementContext topLevelElement() throws RecognitionException {
		TopLevelElementContext _localctx = new TopLevelElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_topLevelElement);
		try {
			setState(41);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				routineDecl();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
				trackDecl();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LoopStmtContext extends StatementContext {
		public ExpressionContext e1;
		public ExpressionContext e2;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LoopStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitLoopStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockStmtContext extends StatementContext {
		public BlockLVL1Context blockLVL1() {
			return getRuleContext(BlockLVL1Context.class,0);
		}
		public BlockStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclAssignStmtContext extends StatementContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclAssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitDeclAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignStmtContext extends StatementContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParallelStmtContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ParallelStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParallelStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		int _la;
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				blockLVL1();
				}
				break;
			case 2:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(T__0);
				setState(45);
				match(T__1);
				setState(46);
				expression(0);
				setState(47);
				match(T__2);
				setState(48);
				statement();
				setState(51);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(49);
					match(T__3);
					setState(50);
					statement();
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(53);
				match(T__4);
				setState(54);
				match(T__1);
				setState(55);
				expression(0);
				setState(56);
				match(T__2);
				setState(57);
				statement();
				}
				break;
			case 4:
				_localctx = new LoopStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(59);
				match(T__5);
				setState(60);
				match(T__1);
				setState(61);
				type();
				setState(62);
				match(ID);
				setState(63);
				match(T__6);
				setState(64);
				((LoopStmtContext)_localctx).e1 = expression(0);
				setState(65);
				match(T__7);
				setState(66);
				((LoopStmtContext)_localctx).e2 = expression(0);
				setState(67);
				match(T__2);
				setState(68);
				statement();
				}
				break;
			case 5:
				_localctx = new ParallelStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(70);
				match(T__8);
				setState(71);
				match(T__9);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090724065986150L) != 0)) {
					{
					{
					setState(72);
					statement();
					}
					}
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(78);
				match(T__10);
				}
				break;
			case 6:
				_localctx = new DeclAssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(79);
				type();
				setState(80);
				match(ID);
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(81);
					match(T__11);
					setState(82);
					expression(0);
					}
				}

				setState(85);
				match(T__12);
				}
				break;
			case 7:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(87);
				match(ID);
				setState(88);
				match(T__11);
				setState(89);
				expression(0);
				setState(90);
				match(T__12);
				}
				break;
			case 8:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(92);
				match(T__13);
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090715543142404L) != 0)) {
					{
					setState(93);
					expression(0);
					}
				}

				setState(96);
				match(T__12);
				}
				break;
			case 9:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(97);
				expression(0);
				setState(98);
				match(T__12);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RoutineDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BlockLVL1Context blockLVL1() {
			return getRuleContext(BlockLVL1Context.class,0);
		}
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public RoutineDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_routineDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitRoutineDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoutineDeclContext routineDecl() throws RecognitionException {
		RoutineDeclContext _localctx = new RoutineDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_routineDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__14);
			setState(103);
			match(ID);
			setState(104);
			match(T__1);
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8522825728L) != 0)) {
				{
				setState(105);
				parameters();
				}
			}

			setState(108);
			match(T__2);
			setState(109);
			match(T__15);
			setState(110);
			type();
			setState(111);
			blockLVL1();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrackDeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public BlockLVL2Context blockLVL2() {
			return getRuleContext(BlockLVL2Context.class,0);
		}
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public TrackDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trackDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitTrackDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrackDeclContext trackDecl() throws RecognitionException {
		TrackDeclContext _localctx = new TrackDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_trackDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__16);
			setState(114);
			match(ID);
			setState(115);
			match(T__1);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8522825728L) != 0)) {
				{
				setState(116);
				parameters();
				}
			}

			setState(119);
			match(T__2);
			setState(120);
			blockLVL2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParametersContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			param();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(123);
				match(T__17);
				setState(124);
				param();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			type();
			setState(131);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			expression(0);
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(134);
				match(T__17);
				setState(135);
				expression(0);
				}
				}
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockLVL1Context extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockLVL1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockLVL1; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitBlockLVL1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockLVL1Context blockLVL1() throws RecognitionException {
		BlockLVL1Context _localctx = new BlockLVL1Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_blockLVL1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(T__9);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090724065986150L) != 0)) {
				{
				{
				setState(142);
				statement();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockLVL2Context extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<GridStmtContext> gridStmt() {
			return getRuleContexts(GridStmtContext.class);
		}
		public GridStmtContext gridStmt(int i) {
			return getRuleContext(GridStmtContext.class,i);
		}
		public BlockLVL2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockLVL2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitBlockLVL2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockLVL2Context blockLVL2() throws RecognitionException {
		BlockLVL2Context _localctx = new BlockLVL2Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_blockLVL2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(T__9);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090724066510438L) != 0)) {
				{
				setState(153);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__4:
				case T__5:
				case T__8:
				case T__9:
				case T__13:
				case T__22:
				case T__25:
				case T__26:
				case T__27:
				case T__28:
				case T__29:
				case T__30:
				case T__31:
				case BOOL:
				case INT:
				case FLOAT:
				case STRING:
				case ID:
					{
					setState(151);
					statement();
					}
					break;
				case T__18:
					{
					setState(152);
					gridStmt();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GridStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<GridContentContext> gridContent() {
			return getRuleContexts(GridContentContext.class);
		}
		public GridContentContext gridContent(int i) {
			return getRuleContext(GridContentContext.class,i);
		}
		public GridStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gridStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GridStmtContext gridStmt() throws RecognitionException {
		GridStmtContext _localctx = new GridStmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_gridStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(T__18);
			setState(161);
			match(T__1);
			setState(162);
			expression(0);
			setState(163);
			match(T__2);
			setState(164);
			match(T__9);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 580679581564928L) != 0)) {
				{
				{
				setState(165);
				gridContent();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GridContentContext extends ParserRuleContext {
		public GridContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gridContent; }
	 
		public GridContentContext() { }
		public void copyFrom(GridContentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GridChordContext extends GridContentContext {
		public List<NoteElementContext> noteElement() {
			return getRuleContexts(NoteElementContext.class);
		}
		public NoteElementContext noteElement(int i) {
			return getRuleContext(NoteElementContext.class,i);
		}
		public GridChordContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridChord(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GridNoteContext extends GridContentContext {
		public NoteElementContext noteElement() {
			return getRuleContext(NoteElementContext.class,0);
		}
		public GridNoteContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridNote(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GridHoldContext extends GridContentContext {
		public GridHoldContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridHold(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GridRestContext extends GridContentContext {
		public TerminalNode SUB() { return getToken(SymNoteParser.SUB, 0); }
		public GridRestContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GridContentContext gridContent() throws RecognitionException {
		GridContentContext _localctx = new GridContentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_gridContent);
		int _la;
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOTE:
			case ID:
				_localctx = new GridNoteContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				noteElement();
				}
				break;
			case SUB:
				_localctx = new GridRestContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				match(SUB);
				}
				break;
			case T__19:
				_localctx = new GridHoldContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				match(T__19);
				}
				break;
			case T__20:
				_localctx = new GridChordContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(176);
				match(T__20);
				setState(177);
				noteElement();
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(178);
					match(T__17);
					setState(179);
					noteElement();
					}
					}
					setState(184);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(185);
				match(T__21);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NoteElementContext extends ParserRuleContext {
		public TerminalNode NOTE() { return getToken(SymNoteParser.NOTE, 0); }
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NoteElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noteElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitNoteElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoteElementContext noteElement() throws RecognitionException {
		NoteElementContext _localctx = new NoteElementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_noteElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_la = _input.LA(1);
			if ( !(_la==NOTE || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(190);
				match(T__1);
				setState(191);
				expression(0);
				setState(192);
				match(T__2);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpNotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OpNotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomIntContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(SymNoteParser.INT, 0); }
		public AtomIntContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpMulDivModContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(SymNoteParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(SymNoteParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(SymNoteParser.MOD, 0); }
		public OpMulDivModContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OpAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallExprContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FuncCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitFuncCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpCompareContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GT() { return getToken(SymNoteParser.GT, 0); }
		public TerminalNode LT() { return getToken(SymNoteParser.LT, 0); }
		public TerminalNode GE() { return getToken(SymNoteParser.GE, 0); }
		public TerminalNode LE() { return getToken(SymNoteParser.LE, 0); }
		public TerminalNode EQ() { return getToken(SymNoteParser.EQ, 0); }
		public TerminalNode NE() { return getToken(SymNoteParser.NE, 0); }
		public OpCompareContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpCompare(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomIdContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public AtomIdContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OpOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomBoolContext extends ExpressionContext {
		public TerminalNode BOOL() { return getToken(SymNoteParser.BOOL, 0); }
		public AtomBoolContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomBool(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OpAddSubContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(SymNoteParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(SymNoteParser.SUB, 0); }
		public OpAddSubContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomStringContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(SymNoteParser.STRING, 0); }
		public AtomStringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomFloatContext extends ExpressionContext {
		public TerminalNode FLOAT() { return getToken(SymNoteParser.FLOAT, 0); }
		public AtomFloatContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomFloat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(197);
				match(T__1);
				setState(198);
				expression(0);
				setState(199);
				match(T__2);
				}
				break;
			case 2:
				{
				_localctx = new OpNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(201);
				match(T__22);
				setState(202);
				expression(12);
				}
				break;
			case 3:
				{
				_localctx = new FuncCallExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(203);
				match(ID);
				setState(204);
				match(T__1);
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1090715543142404L) != 0)) {
					{
					setState(205);
					arguments();
					}
				}

				setState(208);
				match(T__2);
				}
				break;
			case 4:
				{
				_localctx = new AtomIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				match(ID);
				}
				break;
			case 5:
				{
				_localctx = new AtomIntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(210);
				match(INT);
				}
				break;
			case 6:
				{
				_localctx = new AtomFloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(211);
				match(FLOAT);
				}
				break;
			case 7:
				{
				_localctx = new AtomBoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(212);
				match(BOOL);
				}
				break;
			case 8:
				{
				_localctx = new AtomStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(213);
				match(STRING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(233);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(231);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new OpMulDivModContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(216);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(217);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 60129542144L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(218);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new OpAddSubContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(219);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(220);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(221);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new OpCompareContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(222);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(223);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 17317308137472L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(224);
						expression(9);
						}
						break;
					case 4:
						{
						_localctx = new OpAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(225);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(226);
						match(T__23);
						setState(227);
						expression(8);
						}
						break;
					case 5:
						{
						_localctx = new OpOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(228);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(229);
						match(T__24);
						setState(230);
						expression(7);
						}
						break;
					}
					} 
				}
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8522825728L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 13:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00013\u00ef\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0005\u0000!\b\u0000\n\u0000\f\u0000$\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0003\u0001*\b\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u00024\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002J\b\u0002"+
		"\n\u0002\f\u0002M\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002T\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002_\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002e\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003k\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"v\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005~\b\u0005\n\u0005\f\u0005\u0081\t\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005"+
		"\u0007\u0089\b\u0007\n\u0007\f\u0007\u008c\t\u0007\u0001\b\u0001\b\u0005"+
		"\b\u0090\b\b\n\b\f\b\u0093\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t"+
		"\u0005\t\u009a\b\t\n\t\f\t\u009d\t\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0005\n\u00a7\b\n\n\n\f\n\u00aa\t\n\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0005\u000b\u00b5\b\u000b\n\u000b\f\u000b\u00b8\t\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u00bc\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0003\f\u00c3\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00cf\b\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00d7\b\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0005\r\u00e8\b\r\n\r\f\r\u00eb\t\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0000\u0001\u001a\u000f\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0005\u0002"+
		"\u0000,,11\u0001\u0000!#\u0001\u0000$%\u0001\u0000&+\u0001\u0000\u001a"+
		" \u0108\u0000\"\u0001\u0000\u0000\u0000\u0002)\u0001\u0000\u0000\u0000"+
		"\u0004d\u0001\u0000\u0000\u0000\u0006f\u0001\u0000\u0000\u0000\bq\u0001"+
		"\u0000\u0000\u0000\nz\u0001\u0000\u0000\u0000\f\u0082\u0001\u0000\u0000"+
		"\u0000\u000e\u0085\u0001\u0000\u0000\u0000\u0010\u008d\u0001\u0000\u0000"+
		"\u0000\u0012\u0096\u0001\u0000\u0000\u0000\u0014\u00a0\u0001\u0000\u0000"+
		"\u0000\u0016\u00bb\u0001\u0000\u0000\u0000\u0018\u00bd\u0001\u0000\u0000"+
		"\u0000\u001a\u00d6\u0001\u0000\u0000\u0000\u001c\u00ec\u0001\u0000\u0000"+
		"\u0000\u001e!\u0003\u0002\u0001\u0000\u001f!\u0003\u0004\u0002\u0000 "+
		"\u001e\u0001\u0000\u0000\u0000 \u001f\u0001\u0000\u0000\u0000!$\u0001"+
		"\u0000\u0000\u0000\" \u0001\u0000\u0000\u0000\"#\u0001\u0000\u0000\u0000"+
		"#%\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000%&\u0005\u0000\u0000"+
		"\u0001&\u0001\u0001\u0000\u0000\u0000\'*\u0003\u0006\u0003\u0000(*\u0003"+
		"\b\u0004\u0000)\'\u0001\u0000\u0000\u0000)(\u0001\u0000\u0000\u0000*\u0003"+
		"\u0001\u0000\u0000\u0000+e\u0003\u0010\b\u0000,-\u0005\u0001\u0000\u0000"+
		"-.\u0005\u0002\u0000\u0000./\u0003\u001a\r\u0000/0\u0005\u0003\u0000\u0000"+
		"03\u0003\u0004\u0002\u000012\u0005\u0004\u0000\u000024\u0003\u0004\u0002"+
		"\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u00004e\u0001\u0000"+
		"\u0000\u000056\u0005\u0005\u0000\u000067\u0005\u0002\u0000\u000078\u0003"+
		"\u001a\r\u000089\u0005\u0003\u0000\u00009:\u0003\u0004\u0002\u0000:e\u0001"+
		"\u0000\u0000\u0000;<\u0005\u0006\u0000\u0000<=\u0005\u0002\u0000\u0000"+
		"=>\u0003\u001c\u000e\u0000>?\u00051\u0000\u0000?@\u0005\u0007\u0000\u0000"+
		"@A\u0003\u001a\r\u0000AB\u0005\b\u0000\u0000BC\u0003\u001a\r\u0000CD\u0005"+
		"\u0003\u0000\u0000DE\u0003\u0004\u0002\u0000Ee\u0001\u0000\u0000\u0000"+
		"FG\u0005\t\u0000\u0000GK\u0005\n\u0000\u0000HJ\u0003\u0004\u0002\u0000"+
		"IH\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000"+
		"\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001\u0000"+
		"\u0000\u0000Ne\u0005\u000b\u0000\u0000OP\u0003\u001c\u000e\u0000PS\u0005"+
		"1\u0000\u0000QR\u0005\f\u0000\u0000RT\u0003\u001a\r\u0000SQ\u0001\u0000"+
		"\u0000\u0000ST\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UV\u0005"+
		"\r\u0000\u0000Ve\u0001\u0000\u0000\u0000WX\u00051\u0000\u0000XY\u0005"+
		"\f\u0000\u0000YZ\u0003\u001a\r\u0000Z[\u0005\r\u0000\u0000[e\u0001\u0000"+
		"\u0000\u0000\\^\u0005\u000e\u0000\u0000]_\u0003\u001a\r\u0000^]\u0001"+
		"\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000"+
		"`e\u0005\r\u0000\u0000ab\u0003\u001a\r\u0000bc\u0005\r\u0000\u0000ce\u0001"+
		"\u0000\u0000\u0000d+\u0001\u0000\u0000\u0000d,\u0001\u0000\u0000\u0000"+
		"d5\u0001\u0000\u0000\u0000d;\u0001\u0000\u0000\u0000dF\u0001\u0000\u0000"+
		"\u0000dO\u0001\u0000\u0000\u0000dW\u0001\u0000\u0000\u0000d\\\u0001\u0000"+
		"\u0000\u0000da\u0001\u0000\u0000\u0000e\u0005\u0001\u0000\u0000\u0000"+
		"fg\u0005\u000f\u0000\u0000gh\u00051\u0000\u0000hj\u0005\u0002\u0000\u0000"+
		"ik\u0003\n\u0005\u0000ji\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000"+
		"kl\u0001\u0000\u0000\u0000lm\u0005\u0003\u0000\u0000mn\u0005\u0010\u0000"+
		"\u0000no\u0003\u001c\u000e\u0000op\u0003\u0010\b\u0000p\u0007\u0001\u0000"+
		"\u0000\u0000qr\u0005\u0011\u0000\u0000rs\u00051\u0000\u0000su\u0005\u0002"+
		"\u0000\u0000tv\u0003\n\u0005\u0000ut\u0001\u0000\u0000\u0000uv\u0001\u0000"+
		"\u0000\u0000vw\u0001\u0000\u0000\u0000wx\u0005\u0003\u0000\u0000xy\u0003"+
		"\u0012\t\u0000y\t\u0001\u0000\u0000\u0000z\u007f\u0003\f\u0006\u0000{"+
		"|\u0005\u0012\u0000\u0000|~\u0003\f\u0006\u0000}{\u0001\u0000\u0000\u0000"+
		"~\u0081\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080"+
		"\u0001\u0000\u0000\u0000\u0080\u000b\u0001\u0000\u0000\u0000\u0081\u007f"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0003\u001c\u000e\u0000\u0083\u0084"+
		"\u00051\u0000\u0000\u0084\r\u0001\u0000\u0000\u0000\u0085\u008a\u0003"+
		"\u001a\r\u0000\u0086\u0087\u0005\u0012\u0000\u0000\u0087\u0089\u0003\u001a"+
		"\r\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0089\u008c\u0001\u0000\u0000"+
		"\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000"+
		"\u0000\u008b\u000f\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000"+
		"\u0000\u008d\u0091\u0005\n\u0000\u0000\u008e\u0090\u0003\u0004\u0002\u0000"+
		"\u008f\u008e\u0001\u0000\u0000\u0000\u0090\u0093\u0001\u0000\u0000\u0000"+
		"\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000"+
		"\u0092\u0094\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000"+
		"\u0094\u0095\u0005\u000b\u0000\u0000\u0095\u0011\u0001\u0000\u0000\u0000"+
		"\u0096\u009b\u0005\n\u0000\u0000\u0097\u009a\u0003\u0004\u0002\u0000\u0098"+
		"\u009a\u0003\u0014\n\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u0098"+
		"\u0001\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u0099"+
		"\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009e"+
		"\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0005\u000b\u0000\u0000\u009f\u0013\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0005\u0013\u0000\u0000\u00a1\u00a2\u0005\u0002\u0000\u0000\u00a2\u00a3"+
		"\u0003\u001a\r\u0000\u00a3\u00a4\u0005\u0003\u0000\u0000\u00a4\u00a8\u0005"+
		"\n\u0000\u0000\u00a5\u00a7\u0003\u0016\u000b\u0000\u00a6\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00ab\u0001\u0000"+
		"\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005\u000b"+
		"\u0000\u0000\u00ac\u0015\u0001\u0000\u0000\u0000\u00ad\u00bc\u0003\u0018"+
		"\f\u0000\u00ae\u00bc\u0005%\u0000\u0000\u00af\u00bc\u0005\u0014\u0000"+
		"\u0000\u00b0\u00b1\u0005\u0015\u0000\u0000\u00b1\u00b6\u0003\u0018\f\u0000"+
		"\u00b2\u00b3\u0005\u0012\u0000\u0000\u00b3\u00b5\u0003\u0018\f\u0000\u00b4"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b5\u00b8\u0001\u0000\u0000\u0000\u00b6"+
		"\u00b4\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7"+
		"\u00b9\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000\u0000\u00b9"+
		"\u00ba\u0005\u0016\u0000\u0000\u00ba\u00bc\u0001\u0000\u0000\u0000\u00bb"+
		"\u00ad\u0001\u0000\u0000\u0000\u00bb\u00ae\u0001\u0000\u0000\u0000\u00bb"+
		"\u00af\u0001\u0000\u0000\u0000\u00bb\u00b0\u0001\u0000\u0000\u0000\u00bc"+
		"\u0017\u0001\u0000\u0000\u0000\u00bd\u00c2\u0007\u0000\u0000\u0000\u00be"+
		"\u00bf\u0005\u0002\u0000\u0000\u00bf\u00c0\u0003\u001a\r\u0000\u00c0\u00c1"+
		"\u0005\u0003\u0000\u0000\u00c1\u00c3\u0001\u0000\u0000\u0000\u00c2\u00be"+
		"\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u0019"+
		"\u0001\u0000\u0000\u0000\u00c4\u00c5\u0006\r\uffff\uffff\u0000\u00c5\u00c6"+
		"\u0005\u0002\u0000\u0000\u00c6\u00c7\u0003\u001a\r\u0000\u00c7\u00c8\u0005"+
		"\u0003\u0000\u0000\u00c8\u00d7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0005"+
		"\u0017\u0000\u0000\u00ca\u00d7\u0003\u001a\r\f\u00cb\u00cc\u00051\u0000"+
		"\u0000\u00cc\u00ce\u0005\u0002\u0000\u0000\u00cd\u00cf\u0003\u000e\u0007"+
		"\u0000\u00ce\u00cd\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000"+
		"\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0\u00d7\u0005\u0003\u0000"+
		"\u0000\u00d1\u00d7\u00051\u0000\u0000\u00d2\u00d7\u0005.\u0000\u0000\u00d3"+
		"\u00d7\u0005/\u0000\u0000\u00d4\u00d7\u0005-\u0000\u0000\u00d5\u00d7\u0005"+
		"0\u0000\u0000\u00d6\u00c4\u0001\u0000\u0000\u0000\u00d6\u00c9\u0001\u0000"+
		"\u0000\u0000\u00d6\u00cb\u0001\u0000\u0000\u0000\u00d6\u00d1\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d2\u0001\u0000\u0000\u0000\u00d6\u00d3\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000\u00d6\u00d5\u0001\u0000"+
		"\u0000\u0000\u00d7\u00e9\u0001\u0000\u0000\u0000\u00d8\u00d9\n\n\u0000"+
		"\u0000\u00d9\u00da\u0007\u0001\u0000\u0000\u00da\u00e8\u0003\u001a\r\u000b"+
		"\u00db\u00dc\n\t\u0000\u0000\u00dc\u00dd\u0007\u0002\u0000\u0000\u00dd"+
		"\u00e8\u0003\u001a\r\n\u00de\u00df\n\b\u0000\u0000\u00df\u00e0\u0007\u0003"+
		"\u0000\u0000\u00e0\u00e8\u0003\u001a\r\t\u00e1\u00e2\n\u0007\u0000\u0000"+
		"\u00e2\u00e3\u0005\u0018\u0000\u0000\u00e3\u00e8\u0003\u001a\r\b\u00e4"+
		"\u00e5\n\u0006\u0000\u0000\u00e5\u00e6\u0005\u0019\u0000\u0000\u00e6\u00e8"+
		"\u0003\u001a\r\u0007\u00e7\u00d8\u0001\u0000\u0000\u0000\u00e7\u00db\u0001"+
		"\u0000\u0000\u0000\u00e7\u00de\u0001\u0000\u0000\u0000\u00e7\u00e1\u0001"+
		"\u0000\u0000\u0000\u00e7\u00e4\u0001\u0000\u0000\u0000\u00e8\u00eb\u0001"+
		"\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001"+
		"\u0000\u0000\u0000\u00ea\u001b\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001"+
		"\u0000\u0000\u0000\u00ec\u00ed\u0007\u0004\u0000\u0000\u00ed\u001d\u0001"+
		"\u0000\u0000\u0000\u0017 \")3KS^dju\u007f\u008a\u0091\u0099\u009b\u00a8"+
		"\u00b6\u00bb\u00c2\u00ce\u00d6\u00e7\u00e9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}