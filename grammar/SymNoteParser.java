// Generated from SymNote.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SymNoteParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitProgram(this);
		}
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__13) | (1L << T__14) | (1L << T__16) | (1L << T__22) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterTopLevelElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitTopLevelElement(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterLoopStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitLoopStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitLoopStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockStmtContext extends StatementContext {
		public BlockLVL1Context blockLVL1() {
			return getRuleContext(BlockLVL1Context.class,0);
		}
		public BlockStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitBlockStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterDeclAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitDeclAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitDeclAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignStmtContext extends StatementContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParallelStmtContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ParallelStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterParallelStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitParallelStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParallelStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitReturnStmt(this);
		}
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
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__13) | (1L << T__22) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__22) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterRoutineDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitRoutineDecl(this);
		}
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterTrackDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitTrackDecl(this);
		}
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitParameters(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitParam(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitArguments(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterBlockLVL1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitBlockLVL1(this);
		}
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__13) | (1L << T__22) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterBlockLVL2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitBlockLVL2(this);
		}
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__13) | (1L << T__18) | (1L << T__22) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterGridStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitGridStmt(this);
		}
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__20) | (1L << SUB) | (1L << NOTE) | (1L << ID))) != 0)) {
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
	public static class GridChordContext extends GridContentContext {
		public List<NoteElementContext> noteElement() {
			return getRuleContexts(NoteElementContext.class);
		}
		public NoteElementContext noteElement(int i) {
			return getRuleContext(NoteElementContext.class,i);
		}
		public GridChordContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterGridChord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitGridChord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridChord(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GridNoteContext extends GridContentContext {
		public NoteElementContext noteElement() {
			return getRuleContext(NoteElementContext.class,0);
		}
		public GridNoteContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterGridNote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitGridNote(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridNote(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GridHoldContext extends GridContentContext {
		public GridHoldContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterGridHold(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitGridHold(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitGridHold(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GridRestContext extends GridContentContext {
		public TerminalNode SUB() { return getToken(SymNoteParser.SUB, 0); }
		public GridRestContext(GridContentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterGridRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitGridRest(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterNoteElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitNoteElement(this);
		}
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
	public static class OpNotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OpNotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomIntContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(SymNoteParser.INT, 0); }
		public AtomIntContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAtomInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAtomInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomInt(this);
			else return visitor.visitChildren(this);
		}
	}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OpAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OpAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncCallExprContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FuncCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterFuncCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitFuncCallExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitFuncCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpCompare(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomIdContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(SymNoteParser.ID, 0); }
		public AtomIdContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAtomId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAtomId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OpOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OpOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomBoolContext extends ExpressionContext {
		public TerminalNode BOOL() { return getToken(SymNoteParser.BOOL, 0); }
		public AtomBoolContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAtomBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAtomBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomBool(this);
			else return visitor.visitChildren(this);
		}
	}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterOpAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitOpAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitOpAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomStringContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(SymNoteParser.STRING, 0); }
		public AtomStringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAtomString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAtomString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SymNoteVisitor ) return ((SymNoteVisitor<? extends T>)visitor).visitAtomString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomFloatContext extends ExpressionContext {
		public TerminalNode FLOAT() { return getToken(SymNoteParser.FLOAT, 0); }
		public AtomFloatContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterAtomFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitAtomFloat(this);
		}
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
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__22) | (1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
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
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
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
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << GE) | (1L << LE) | (1L << EQ) | (1L << NE))) != 0)) ) {
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SymNoteListener ) ((SymNoteListener)listener).exitType(this);
		}
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
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\65\u00f1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\7\2#\n\2\f"+
		"\2\16\2&\13\2\3\2\3\2\3\3\3\3\5\3,\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\4\66\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\7\4L\n\4\f\4\16\4O\13\4\3\4\3\4\3\4\3\4\3\4\5\4"+
		"V\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4a\n\4\3\4\3\4\3\4\3\4\5\4"+
		"g\n\4\3\5\3\5\3\5\3\5\5\5m\n\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\5\6"+
		"x\n\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7\u0080\n\7\f\7\16\7\u0083\13\7\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\7\t\u008b\n\t\f\t\16\t\u008e\13\t\3\n\3\n\7\n\u0092"+
		"\n\n\f\n\16\n\u0095\13\n\3\n\3\n\3\13\3\13\3\13\7\13\u009c\n\13\f\13\16"+
		"\13\u009f\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00a9\n\f\f\f\16"+
		"\f\u00ac\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00b7\n\r\f\r\16"+
		"\r\u00ba\13\r\3\r\3\r\5\r\u00be\n\r\3\16\3\16\3\16\3\16\3\16\5\16\u00c5"+
		"\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00d1\n\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00d9\n\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u00ea\n\17\f\17"+
		"\16\17\u00ed\13\17\3\20\3\20\3\20\2\3\34\21\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36\2\7\4\2..\63\63\3\2#%\3\2&\'\3\2(-\3\2\34\"\2\u010a\2$\3"+
		"\2\2\2\4+\3\2\2\2\6f\3\2\2\2\bh\3\2\2\2\ns\3\2\2\2\f|\3\2\2\2\16\u0084"+
		"\3\2\2\2\20\u0087\3\2\2\2\22\u008f\3\2\2\2\24\u0098\3\2\2\2\26\u00a2\3"+
		"\2\2\2\30\u00bd\3\2\2\2\32\u00bf\3\2\2\2\34\u00d8\3\2\2\2\36\u00ee\3\2"+
		"\2\2 #\5\4\3\2!#\5\6\4\2\" \3\2\2\2\"!\3\2\2\2#&\3\2\2\2$\"\3\2\2\2$%"+
		"\3\2\2\2%\'\3\2\2\2&$\3\2\2\2\'(\7\2\2\3(\3\3\2\2\2),\5\b\5\2*,\5\n\6"+
		"\2+)\3\2\2\2+*\3\2\2\2,\5\3\2\2\2-g\5\22\n\2./\7\3\2\2/\60\7\4\2\2\60"+
		"\61\5\34\17\2\61\62\7\5\2\2\62\65\5\6\4\2\63\64\7\6\2\2\64\66\5\6\4\2"+
		"\65\63\3\2\2\2\65\66\3\2\2\2\66g\3\2\2\2\678\7\7\2\289\7\4\2\29:\5\34"+
		"\17\2:;\7\5\2\2;<\5\6\4\2<g\3\2\2\2=>\7\b\2\2>?\7\4\2\2?@\5\36\20\2@A"+
		"\7\63\2\2AB\7\t\2\2BC\5\34\17\2CD\7\n\2\2DE\5\34\17\2EF\7\5\2\2FG\5\6"+
		"\4\2Gg\3\2\2\2HI\7\13\2\2IM\7\f\2\2JL\5\6\4\2KJ\3\2\2\2LO\3\2\2\2MK\3"+
		"\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2Pg\7\r\2\2QR\5\36\20\2RU\7\63\2\2"+
		"ST\7\16\2\2TV\5\34\17\2US\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\7\17\2\2Xg\3\2"+
		"\2\2YZ\7\63\2\2Z[\7\16\2\2[\\\5\34\17\2\\]\7\17\2\2]g\3\2\2\2^`\7\20\2"+
		"\2_a\5\34\17\2`_\3\2\2\2`a\3\2\2\2ab\3\2\2\2bg\7\17\2\2cd\5\34\17\2de"+
		"\7\17\2\2eg\3\2\2\2f-\3\2\2\2f.\3\2\2\2f\67\3\2\2\2f=\3\2\2\2fH\3\2\2"+
		"\2fQ\3\2\2\2fY\3\2\2\2f^\3\2\2\2fc\3\2\2\2g\7\3\2\2\2hi\7\21\2\2ij\7\63"+
		"\2\2jl\7\4\2\2km\5\f\7\2lk\3\2\2\2lm\3\2\2\2mn\3\2\2\2no\7\5\2\2op\7\22"+
		"\2\2pq\5\36\20\2qr\5\22\n\2r\t\3\2\2\2st\7\23\2\2tu\7\63\2\2uw\7\4\2\2"+
		"vx\5\f\7\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\7\5\2\2z{\5\24\13\2{\13\3\2"+
		"\2\2|\u0081\5\16\b\2}~\7\24\2\2~\u0080\5\16\b\2\177}\3\2\2\2\u0080\u0083"+
		"\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\r\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0084\u0085\5\36\20\2\u0085\u0086\7\63\2\2\u0086\17\3\2\2\2\u0087"+
		"\u008c\5\34\17\2\u0088\u0089\7\24\2\2\u0089\u008b\5\34\17\2\u008a\u0088"+
		"\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\21\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0093\7\f\2\2\u0090\u0092\5\6\4"+
		"\2\u0091\u0090\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094"+
		"\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u0097\7\r\2\2\u0097"+
		"\23\3\2\2\2\u0098\u009d\7\f\2\2\u0099\u009c\5\6\4\2\u009a\u009c\5\26\f"+
		"\2\u009b\u0099\3\2\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a1\7\r\2\2\u00a1\25\3\2\2\2\u00a2\u00a3\7\25\2\2\u00a3\u00a4\7\4\2"+
		"\2\u00a4\u00a5\5\34\17\2\u00a5\u00a6\7\5\2\2\u00a6\u00aa\7\f\2\2\u00a7"+
		"\u00a9\5\30\r\2\u00a8\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3"+
		"\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad"+
		"\u00ae\7\r\2\2\u00ae\27\3\2\2\2\u00af\u00be\5\32\16\2\u00b0\u00be\7\'"+
		"\2\2\u00b1\u00be\7\26\2\2\u00b2\u00b3\7\27\2\2\u00b3\u00b8\5\32\16\2\u00b4"+
		"\u00b5\7\24\2\2\u00b5\u00b7\5\32\16\2\u00b6\u00b4\3\2\2\2\u00b7\u00ba"+
		"\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba"+
		"\u00b8\3\2\2\2\u00bb\u00bc\7\30\2\2\u00bc\u00be\3\2\2\2\u00bd\u00af\3"+
		"\2\2\2\u00bd\u00b0\3\2\2\2\u00bd\u00b1\3\2\2\2\u00bd\u00b2\3\2\2\2\u00be"+
		"\31\3\2\2\2\u00bf\u00c4\t\2\2\2\u00c0\u00c1\7\4\2\2\u00c1\u00c2\5\34\17"+
		"\2\u00c2\u00c3\7\5\2\2\u00c3\u00c5\3\2\2\2\u00c4\u00c0\3\2\2\2\u00c4\u00c5"+
		"\3\2\2\2\u00c5\33\3\2\2\2\u00c6\u00c7\b\17\1\2\u00c7\u00c8\7\4\2\2\u00c8"+
		"\u00c9\5\34\17\2\u00c9\u00ca\7\5\2\2\u00ca\u00d9\3\2\2\2\u00cb\u00cc\7"+
		"\31\2\2\u00cc\u00d9\5\34\17\16\u00cd\u00ce\7\63\2\2\u00ce\u00d0\7\4\2"+
		"\2\u00cf\u00d1\5\20\t\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d2\3\2\2\2\u00d2\u00d9\7\5\2\2\u00d3\u00d9\7\63\2\2\u00d4\u00d9\7"+
		"\60\2\2\u00d5\u00d9\7\61\2\2\u00d6\u00d9\7/\2\2\u00d7\u00d9\7\62\2\2\u00d8"+
		"\u00c6\3\2\2\2\u00d8\u00cb\3\2\2\2\u00d8\u00cd\3\2\2\2\u00d8\u00d3\3\2"+
		"\2\2\u00d8\u00d4\3\2\2\2\u00d8\u00d5\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8"+
		"\u00d7\3\2\2\2\u00d9\u00eb\3\2\2\2\u00da\u00db\f\f\2\2\u00db\u00dc\t\3"+
		"\2\2\u00dc\u00ea\5\34\17\r\u00dd\u00de\f\13\2\2\u00de\u00df\t\4\2\2\u00df"+
		"\u00ea\5\34\17\f\u00e0\u00e1\f\n\2\2\u00e1\u00e2\t\5\2\2\u00e2\u00ea\5"+
		"\34\17\13\u00e3\u00e4\f\t\2\2\u00e4\u00e5\7\32\2\2\u00e5\u00ea\5\34\17"+
		"\n\u00e6\u00e7\f\b\2\2\u00e7\u00e8\7\33\2\2\u00e8\u00ea\5\34\17\t\u00e9"+
		"\u00da\3\2\2\2\u00e9\u00dd\3\2\2\2\u00e9\u00e0\3\2\2\2\u00e9\u00e3\3\2"+
		"\2\2\u00e9\u00e6\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\35\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef\t\6\2"+
		"\2\u00ef\37\3\2\2\2\31\"$+\65MU`flw\u0081\u008c\u0093\u009b\u009d\u00aa"+
		"\u00b8\u00bd\u00c4\u00d0\u00d8\u00e9\u00eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}