// Generated from /Users/epaln/NetBeansProjects/nespros/src/main/java/com/imag/nespros/runtime/lang/ESCExpr.g4 by ANTLR 4.2.2
package com.imag.netah.core.lang;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ESCExprParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__19=1, T__18=2, T__17=3, T__16=4, T__15=5, T__14=6, T__13=7, T__12=8, 
		T__11=9, T__10=10, T__9=11, T__8=12, T__7=13, T__6=14, T__5=15, T__4=16, 
		T__3=17, T__2=18, T__1=19, T__0=20, SELECTIONMODE=21, OR=22, AND=23, FILTER=24, 
		FIRST=25, SEQ=26, LAST=27, STREAM=28, BATCH=29, MBATCH=30, WITHIN=31, 
		SLIDING=32, ID=33, STRING=34, TRUE=35, FALSE=36, LT=37, GT=38, EQ=39, 
		NEQ=40, LEQ=41, GEQ=42, INT=43, DOUBLE=44, WS=45;
	public static final String[] tokenNames = {
		"<INVALID>", "'memory'", "']'", "'avg'", "'max'", "')'", "','", "'min'", 
		"'['", "'@'", "':'", "'('", "'&&'", "'||'", "'{'", "'sum'", "'cpu_time'", 
		"'}'", "'parameters'", "'count'", "'selection_mode'", "SELECTIONMODE", 
		"OR", "AND", "FILTER", "'first'", "'seq'", "'last'", "STREAM", "'batch'", 
		"'mbatch'", "'within'", "'sliding'", "ID", "STRING", "'true'", "'false'", 
		"'<'", "'>'", "'='", "'!='", "'<='", "'>='", "INT", "DOUBLE", "WS"
	};
	public static final int
		RULE_expression = 0, RULE_esc_expression = 1, RULE_streamdef = 2, RULE_window_specification = 3, 
		RULE_batch_n = 4, RULE_mbatch = 5, RULE_timefixed = 6, RULE_slidingwin = 7, 
		RULE_operator_def = 8, RULE_operator_type = 9, RULE_filterdef = 10, RULE_predicate = 11, 
		RULE_or_expression = 12, RULE_and_expression = 13, RULE_term = 14, RULE_aggregate = 15, 
		RULE_sum = 16, RULE_average = 17, RULE_count = 18, RULE_min = 19, RULE_max = 20, 
		RULE_params = 21, RULE_param = 22, RULE_dimension = 23, RULE_memory_spec = 24, 
		RULE_cpu_spec = 25, RULE_selection_mode = 26;
	public static final String[] ruleNames = {
		"expression", "esc_expression", "streamdef", "window_specification", "batch_n", 
		"mbatch", "timefixed", "slidingwin", "operator_def", "operator_type", 
		"filterdef", "predicate", "or_expression", "and_expression", "term", "aggregate", 
		"sum", "average", "count", "min", "max", "params", "param", "dimension", 
		"memory_spec", "cpu_spec", "selection_mode"
	};

	@Override
	public String getGrammarFileName() { return "ESCExpr.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ESCExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExpressionContext extends ParserRuleContext {
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Esc_expressionContext esc_expression() {
			return getRuleContext(Esc_expressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); esc_expression();
			setState(60);
			_la = _input.LA(1);
			if (_la==18) {
				{
				setState(55); match(18);
				setState(56); match(14);
				setState(57); params();
				setState(58); match(17);
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

	public static class Esc_expressionContext extends ParserRuleContext {
		public Operator_defContext operator_def() {
			return getRuleContext(Operator_defContext.class,0);
		}
		public StreamdefContext streamdef() {
			return getRuleContext(StreamdefContext.class,0);
		}
		public Window_specificationContext window_specification() {
			return getRuleContext(Window_specificationContext.class,0);
		}
		public Esc_expressionContext esc_expression(int i) {
			return getRuleContext(Esc_expressionContext.class,i);
		}
		public List<Esc_expressionContext> esc_expression() {
			return getRuleContexts(Esc_expressionContext.class);
		}
		public Esc_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_esc_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterEsc_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitEsc_expression(this);
		}
	}

	public final Esc_expressionContext esc_expression() throws RecognitionException {
		Esc_expressionContext _localctx = new Esc_expressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_esc_expression);
		int _la;
		try {
			setState(78);
			switch (_input.LA(1)) {
			case STREAM:
				enterOuterAlt(_localctx, 1);
				{
				setState(62); streamdef();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(63); operator_def();
				setState(64); match(11);
				setState(65); esc_expression();
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==6) {
					{
					{
					setState(66); match(6);
					setState(67); esc_expression();
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73); match(5);
				setState(76);
				_la = _input.LA(1);
				if (_la==10) {
					{
					setState(74); match(10);
					setState(75); window_specification();
					}
				}

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

	public static class StreamdefContext extends ParserRuleContext {
		public TerminalNode STREAM() { return getToken(ESCExprParser.STREAM, 0); }
		public TerminalNode ID() { return getToken(ESCExprParser.ID, 0); }
		public StreamdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_streamdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterStreamdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitStreamdef(this);
		}
	}

	public final StreamdefContext streamdef() throws RecognitionException {
		StreamdefContext _localctx = new StreamdefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_streamdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80); match(STREAM);
			setState(81); match(11);
			setState(82); match(ID);
			setState(83); match(5);
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

	public static class Window_specificationContext extends ParserRuleContext {
		public SlidingwinContext slidingwin() {
			return getRuleContext(SlidingwinContext.class,0);
		}
		public Batch_nContext batch_n() {
			return getRuleContext(Batch_nContext.class,0);
		}
		public MbatchContext mbatch() {
			return getRuleContext(MbatchContext.class,0);
		}
		public TimefixedContext timefixed() {
			return getRuleContext(TimefixedContext.class,0);
		}
		public Window_specificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_window_specification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterWindow_specification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitWindow_specification(this);
		}
	}

	public final Window_specificationContext window_specification() throws RecognitionException {
		Window_specificationContext _localctx = new Window_specificationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_window_specification);
		try {
			setState(89);
			switch (_input.LA(1)) {
			case BATCH:
				enterOuterAlt(_localctx, 1);
				{
				setState(85); batch_n();
				}
				break;
			case MBATCH:
				enterOuterAlt(_localctx, 2);
				{
				setState(86); mbatch();
				}
				break;
			case WITHIN:
				enterOuterAlt(_localctx, 3);
				{
				setState(87); timefixed();
				}
				break;
			case SLIDING:
				enterOuterAlt(_localctx, 4);
				{
				setState(88); slidingwin();
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

	public static class Batch_nContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ESCExprParser.INT, 0); }
		public TerminalNode BATCH() { return getToken(ESCExprParser.BATCH, 0); }
		public Batch_nContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_batch_n; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterBatch_n(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitBatch_n(this);
		}
	}

	public final Batch_nContext batch_n() throws RecognitionException {
		Batch_nContext _localctx = new Batch_nContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_batch_n);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91); match(BATCH);
			setState(92); match(11);
			setState(93); match(INT);
			setState(94); match(5);
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

	public static class MbatchContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ESCExprParser.INT); }
		public TerminalNode MBATCH() { return getToken(ESCExprParser.MBATCH, 0); }
		public TerminalNode INT(int i) {
			return getToken(ESCExprParser.INT, i);
		}
		public MbatchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mbatch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterMbatch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitMbatch(this);
		}
	}

	public final MbatchContext mbatch() throws RecognitionException {
		MbatchContext _localctx = new MbatchContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mbatch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96); match(MBATCH);
			setState(97); match(11);
			setState(98); match(INT);
			setState(99); match(6);
			setState(100); match(INT);
			setState(101); match(5);
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

	public static class TimefixedContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ESCExprParser.INT); }
		public TerminalNode WITHIN() { return getToken(ESCExprParser.WITHIN, 0); }
		public TerminalNode INT(int i) {
			return getToken(ESCExprParser.INT, i);
		}
		public TimefixedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timefixed; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterTimefixed(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitTimefixed(this);
		}
	}

	public final TimefixedContext timefixed() throws RecognitionException {
		TimefixedContext _localctx = new TimefixedContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_timefixed);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); match(WITHIN);
			setState(104); match(11);
			setState(105); match(INT);
			setState(106); match(6);
			setState(107); match(INT);
			setState(108); match(5);
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

	public static class SlidingwinContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ESCExprParser.INT); }
		public TerminalNode SLIDING() { return getToken(ESCExprParser.SLIDING, 0); }
		public TerminalNode INT(int i) {
			return getToken(ESCExprParser.INT, i);
		}
		public SlidingwinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_slidingwin; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterSlidingwin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitSlidingwin(this);
		}
	}

	public final SlidingwinContext slidingwin() throws RecognitionException {
		SlidingwinContext _localctx = new SlidingwinContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_slidingwin);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110); match(SLIDING);
			setState(111); match(11);
			setState(112); match(INT);
			setState(113); match(6);
			setState(114); match(INT);
			setState(115); match(5);
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

	public static class Operator_defContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ESCExprParser.ID, 0); }
		public Operator_typeContext operator_type() {
			return getRuleContext(Operator_typeContext.class,0);
		}
		public Operator_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterOperator_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitOperator_def(this);
		}
	}

	public final Operator_defContext operator_def() throws RecognitionException {
		Operator_defContext _localctx = new Operator_defContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_operator_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117); match(ID);
			setState(118); match(9);
			setState(119); operator_type();
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

	public static class Operator_typeContext extends ParserRuleContext {
		public TerminalNode FIRST() { return getToken(ESCExprParser.FIRST, 0); }
		public FilterdefContext filterdef() {
			return getRuleContext(FilterdefContext.class,0);
		}
		public TerminalNode AND() { return getToken(ESCExprParser.AND, 0); }
		public TerminalNode SEQ() { return getToken(ESCExprParser.SEQ, 0); }
		public TerminalNode OR() { return getToken(ESCExprParser.OR, 0); }
		public AggregateContext aggregate() {
			return getRuleContext(AggregateContext.class,0);
		}
		public TerminalNode LAST() { return getToken(ESCExprParser.LAST, 0); }
		public Operator_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterOperator_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitOperator_type(this);
		}
	}

	public final Operator_typeContext operator_type() throws RecognitionException {
		Operator_typeContext _localctx = new Operator_typeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_operator_type);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case 3:
			case 4:
			case 7:
			case 15:
			case 19:
				enterOuterAlt(_localctx, 1);
				{
				setState(121); aggregate();
				}
				break;
			case FILTER:
				enterOuterAlt(_localctx, 2);
				{
				setState(122); filterdef();
				}
				break;
			case AND:
				enterOuterAlt(_localctx, 3);
				{
				setState(123); match(AND);
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 4);
				{
				setState(124); match(OR);
				}
				break;
			case SEQ:
				enterOuterAlt(_localctx, 5);
				{
				setState(125); match(SEQ);
				}
				break;
			case FIRST:
				enterOuterAlt(_localctx, 6);
				{
				setState(126); match(FIRST);
				}
				break;
			case LAST:
				enterOuterAlt(_localctx, 7);
				{
				setState(127); match(LAST);
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

	public static class FilterdefContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode FILTER() { return getToken(ESCExprParser.FILTER, 0); }
		public FilterdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterFilterdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitFilterdef(this);
		}
	}

	public final FilterdefContext filterdef() throws RecognitionException {
		FilterdefContext _localctx = new FilterdefContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_filterdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); match(FILTER);
			setState(131); match(8);
			setState(132); predicate();
			setState(133); match(2);
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

	public static class PredicateContext extends ParserRuleContext {
		public Or_expressionContext or_expression() {
			return getRuleContext(Or_expressionContext.class,0);
		}
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135); or_expression();
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

	public static class Or_expressionContext extends ParserRuleContext {
		public And_expressionContext and_expression(int i) {
			return getRuleContext(And_expressionContext.class,i);
		}
		public List<And_expressionContext> and_expression() {
			return getRuleContexts(And_expressionContext.class);
		}
		public Or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterOr_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitOr_expression(this);
		}
	}

	public final Or_expressionContext or_expression() throws RecognitionException {
		Or_expressionContext _localctx = new Or_expressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137); and_expression();
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==13) {
				{
				{
				setState(138); match(13);
				setState(139); and_expression();
				}
				}
				setState(144);
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

	public static class And_expressionContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public And_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterAnd_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitAnd_expression(this);
		}
	}

	public final And_expressionContext and_expression() throws RecognitionException {
		And_expressionContext _localctx = new And_expressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); term();
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==12) {
				{
				{
				setState(146); match(12);
				setState(147); term();
				}
				}
				setState(152);
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

	public static class TermContext extends ParserRuleContext {
		public Token sign;
		public Token val;
		public TerminalNode GEQ() { return getToken(ESCExprParser.GEQ, 0); }
		public TerminalNode INT() { return getToken(ESCExprParser.INT, 0); }
		public TerminalNode TRUE() { return getToken(ESCExprParser.TRUE, 0); }
		public TerminalNode ID() { return getToken(ESCExprParser.ID, 0); }
		public TerminalNode DOUBLE() { return getToken(ESCExprParser.DOUBLE, 0); }
		public TerminalNode EQ() { return getToken(ESCExprParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(ESCExprParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(ESCExprParser.LT, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode LEQ() { return getToken(ESCExprParser.LEQ, 0); }
		public TerminalNode GT() { return getToken(ESCExprParser.GT, 0); }
		public TerminalNode STRING() { return getToken(ESCExprParser.STRING, 0); }
		public TerminalNode FALSE() { return getToken(ESCExprParser.FALSE, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_term);
		int _la;
		try {
			setState(162);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(153); match(ID);
				setState(154);
				((TermContext)_localctx).sign = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << EQ) | (1L << NEQ) | (1L << LEQ) | (1L << GEQ))) != 0)) ) {
					((TermContext)_localctx).sign = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(155);
				((TermContext)_localctx).val = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INT) | (1L << DOUBLE))) != 0)) ) {
					((TermContext)_localctx).val = (Token)_errHandler.recoverInline(this);
				}
				consume();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 2);
				{
				setState(156); match(11);
				setState(157); predicate();
				setState(158); match(5);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(160); match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(161); match(FALSE);
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

	public static class AggregateContext extends ParserRuleContext {
		public MinContext min() {
			return getRuleContext(MinContext.class,0);
		}
		public MaxContext max() {
			return getRuleContext(MaxContext.class,0);
		}
		public CountContext count() {
			return getRuleContext(CountContext.class,0);
		}
		public SumContext sum() {
			return getRuleContext(SumContext.class,0);
		}
		public AverageContext average() {
			return getRuleContext(AverageContext.class,0);
		}
		public AggregateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterAggregate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitAggregate(this);
		}
	}

	public final AggregateContext aggregate() throws RecognitionException {
		AggregateContext _localctx = new AggregateContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_aggregate);
		try {
			setState(169);
			switch (_input.LA(1)) {
			case 3:
				enterOuterAlt(_localctx, 1);
				{
				setState(164); average();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 2);
				{
				setState(165); sum();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 3);
				{
				setState(166); count();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 4);
				{
				setState(167); min();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 5);
				{
				setState(168); max();
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

	public static class SumContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ESCExprParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ESCExprParser.ID, i);
		}
		public SumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitSum(this);
		}
	}

	public final SumContext sum() throws RecognitionException {
		SumContext _localctx = new SumContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_sum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171); match(15);
			setState(172); match(8);
			setState(173); match(ID);
			setState(174); match(6);
			setState(175); match(ID);
			setState(176); match(2);
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

	public static class AverageContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ESCExprParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ESCExprParser.ID, i);
		}
		public AverageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_average; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterAverage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitAverage(this);
		}
	}

	public final AverageContext average() throws RecognitionException {
		AverageContext _localctx = new AverageContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_average);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178); match(3);
			setState(179); match(8);
			setState(180); match(ID);
			setState(181); match(6);
			setState(182); match(ID);
			setState(183); match(2);
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

	public static class CountContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ESCExprParser.ID, 0); }
		public CountContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_count; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterCount(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitCount(this);
		}
	}

	public final CountContext count() throws RecognitionException {
		CountContext _localctx = new CountContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_count);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185); match(19);
			setState(186); match(8);
			setState(187); match(ID);
			setState(188); match(2);
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

	public static class MinContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ESCExprParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ESCExprParser.ID, i);
		}
		public MinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_min; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterMin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitMin(this);
		}
	}

	public final MinContext min() throws RecognitionException {
		MinContext _localctx = new MinContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_min);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190); match(7);
			setState(191); match(8);
			setState(192); match(ID);
			setState(193); match(6);
			setState(194); match(ID);
			setState(195); match(2);
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

	public static class MaxContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ESCExprParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ESCExprParser.ID, i);
		}
		public MaxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_max; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterMax(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitMax(this);
		}
	}

	public final MaxContext max() throws RecognitionException {
		MaxContext _localctx = new MaxContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_max);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); match(4);
			setState(198); match(8);
			setState(199); match(ID);
			setState(200); match(6);
			setState(201); match(ID);
			setState(202); match(2);
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

	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitParams(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204); param();
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==6) {
				{
				{
				setState(205); match(6);
				setState(206); param();
				}
				}
				setState(211);
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
		public DimensionContext dimension(int i) {
			return getRuleContext(DimensionContext.class,i);
		}
		public List<DimensionContext> dimension() {
			return getRuleContexts(DimensionContext.class);
		}
		public TerminalNode ID() { return getToken(ESCExprParser.ID, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_param);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212); match(ID);
			setState(213); match(8);
			setState(214); dimension();
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==6) {
				{
				{
				setState(215); match(6);
				setState(216); dimension();
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(222); match(2);
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

	public static class DimensionContext extends ParserRuleContext {
		public Memory_specContext memory_spec() {
			return getRuleContext(Memory_specContext.class,0);
		}
		public Selection_modeContext selection_mode() {
			return getRuleContext(Selection_modeContext.class,0);
		}
		public Cpu_specContext cpu_spec() {
			return getRuleContext(Cpu_specContext.class,0);
		}
		public DimensionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dimension; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterDimension(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitDimension(this);
		}
	}

	public final DimensionContext dimension() throws RecognitionException {
		DimensionContext _localctx = new DimensionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_dimension);
		try {
			setState(227);
			switch (_input.LA(1)) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(224); memory_spec();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 2);
				{
				setState(225); cpu_spec();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 3);
				{
				setState(226); selection_mode();
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

	public static class Memory_specContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ESCExprParser.INT, 0); }
		public Memory_specContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memory_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterMemory_spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitMemory_spec(this);
		}
	}

	public final Memory_specContext memory_spec() throws RecognitionException {
		Memory_specContext _localctx = new Memory_specContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_memory_spec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229); match(1);
			setState(230); match(EQ);
			setState(231); match(INT);
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

	public static class Cpu_specContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ESCExprParser.INT, 0); }
		public Cpu_specContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cpu_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterCpu_spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitCpu_spec(this);
		}
	}

	public final Cpu_specContext cpu_spec() throws RecognitionException {
		Cpu_specContext _localctx = new Cpu_specContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_cpu_spec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233); match(16);
			setState(234); match(EQ);
			setState(235); match(INT);
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

	public static class Selection_modeContext extends ParserRuleContext {
		public TerminalNode SELECTIONMODE() { return getToken(ESCExprParser.SELECTIONMODE, 0); }
		public Selection_modeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection_mode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).enterSelection_mode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ESCExprListener ) ((ESCExprListener)listener).exitSelection_mode(this);
		}
	}

	public final Selection_modeContext selection_mode() throws RecognitionException {
		Selection_modeContext _localctx = new Selection_modeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_selection_mode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237); match(20);
			setState(238); match(EQ);
			setState(239); match(SELECTIONMODE);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3/\u00f4\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\5\2?\n\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\7\3G\n\3\f\3\16\3J\13\3\3\3\3\3\3\3\5\3O\n\3\5\3Q\n"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\\\n\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0083"+
		"\n\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\7\16\u008f\n\16\f\16"+
		"\16\16\u0092\13\16\3\17\3\17\3\17\7\17\u0097\n\17\f\17\16\17\u009a\13"+
		"\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00a5\n\20\3\21"+
		"\3\21\3\21\3\21\3\21\5\21\u00ac\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\7\27\u00d2\n\27\f\27\16\27\u00d5\13\27\3\30\3\30\3\30\3\30\3\30"+
		"\7\30\u00dc\n\30\f\30\16\30\u00df\13\30\3\30\3\30\3\31\3\31\3\31\5\31"+
		"\u00e6\n\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34"+
		"\3\34\2\2\35\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64"+
		"\66\2\4\3\2\',\4\2$$-.\u00f2\28\3\2\2\2\4P\3\2\2\2\6R\3\2\2\2\b[\3\2\2"+
		"\2\n]\3\2\2\2\fb\3\2\2\2\16i\3\2\2\2\20p\3\2\2\2\22w\3\2\2\2\24\u0082"+
		"\3\2\2\2\26\u0084\3\2\2\2\30\u0089\3\2\2\2\32\u008b\3\2\2\2\34\u0093\3"+
		"\2\2\2\36\u00a4\3\2\2\2 \u00ab\3\2\2\2\"\u00ad\3\2\2\2$\u00b4\3\2\2\2"+
		"&\u00bb\3\2\2\2(\u00c0\3\2\2\2*\u00c7\3\2\2\2,\u00ce\3\2\2\2.\u00d6\3"+
		"\2\2\2\60\u00e5\3\2\2\2\62\u00e7\3\2\2\2\64\u00eb\3\2\2\2\66\u00ef\3\2"+
		"\2\28>\5\4\3\29:\7\24\2\2:;\7\20\2\2;<\5,\27\2<=\7\23\2\2=?\3\2\2\2>9"+
		"\3\2\2\2>?\3\2\2\2?\3\3\2\2\2@Q\5\6\4\2AB\5\22\n\2BC\7\r\2\2CH\5\4\3\2"+
		"DE\7\b\2\2EG\5\4\3\2FD\3\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2"+
		"JH\3\2\2\2KN\7\7\2\2LM\7\f\2\2MO\5\b\5\2NL\3\2\2\2NO\3\2\2\2OQ\3\2\2\2"+
		"P@\3\2\2\2PA\3\2\2\2Q\5\3\2\2\2RS\7\36\2\2ST\7\r\2\2TU\7#\2\2UV\7\7\2"+
		"\2V\7\3\2\2\2W\\\5\n\6\2X\\\5\f\7\2Y\\\5\16\b\2Z\\\5\20\t\2[W\3\2\2\2"+
		"[X\3\2\2\2[Y\3\2\2\2[Z\3\2\2\2\\\t\3\2\2\2]^\7\37\2\2^_\7\r\2\2_`\7-\2"+
		"\2`a\7\7\2\2a\13\3\2\2\2bc\7 \2\2cd\7\r\2\2de\7-\2\2ef\7\b\2\2fg\7-\2"+
		"\2gh\7\7\2\2h\r\3\2\2\2ij\7!\2\2jk\7\r\2\2kl\7-\2\2lm\7\b\2\2mn\7-\2\2"+
		"no\7\7\2\2o\17\3\2\2\2pq\7\"\2\2qr\7\r\2\2rs\7-\2\2st\7\b\2\2tu\7-\2\2"+
		"uv\7\7\2\2v\21\3\2\2\2wx\7#\2\2xy\7\13\2\2yz\5\24\13\2z\23\3\2\2\2{\u0083"+
		"\5 \21\2|\u0083\5\26\f\2}\u0083\7\31\2\2~\u0083\7\30\2\2\177\u0083\7\34"+
		"\2\2\u0080\u0083\7\33\2\2\u0081\u0083\7\35\2\2\u0082{\3\2\2\2\u0082|\3"+
		"\2\2\2\u0082}\3\2\2\2\u0082~\3\2\2\2\u0082\177\3\2\2\2\u0082\u0080\3\2"+
		"\2\2\u0082\u0081\3\2\2\2\u0083\25\3\2\2\2\u0084\u0085\7\32\2\2\u0085\u0086"+
		"\7\n\2\2\u0086\u0087\5\30\r\2\u0087\u0088\7\4\2\2\u0088\27\3\2\2\2\u0089"+
		"\u008a\5\32\16\2\u008a\31\3\2\2\2\u008b\u0090\5\34\17\2\u008c\u008d\7"+
		"\17\2\2\u008d\u008f\5\34\17\2\u008e\u008c\3\2\2\2\u008f\u0092\3\2\2\2"+
		"\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\33\3\2\2\2\u0092\u0090"+
		"\3\2\2\2\u0093\u0098\5\36\20\2\u0094\u0095\7\16\2\2\u0095\u0097\5\36\20"+
		"\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099"+
		"\3\2\2\2\u0099\35\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7#\2\2\u009c"+
		"\u009d\t\2\2\2\u009d\u00a5\t\3\2\2\u009e\u009f\7\r\2\2\u009f\u00a0\5\30"+
		"\r\2\u00a0\u00a1\7\7\2\2\u00a1\u00a5\3\2\2\2\u00a2\u00a5\7%\2\2\u00a3"+
		"\u00a5\7&\2\2\u00a4\u009b\3\2\2\2\u00a4\u009e\3\2\2\2\u00a4\u00a2\3\2"+
		"\2\2\u00a4\u00a3\3\2\2\2\u00a5\37\3\2\2\2\u00a6\u00ac\5$\23\2\u00a7\u00ac"+
		"\5\"\22\2\u00a8\u00ac\5&\24\2\u00a9\u00ac\5(\25\2\u00aa\u00ac\5*\26\2"+
		"\u00ab\u00a6\3\2\2\2\u00ab\u00a7\3\2\2\2\u00ab\u00a8\3\2\2\2\u00ab\u00a9"+
		"\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac!\3\2\2\2\u00ad\u00ae\7\21\2\2\u00ae"+
		"\u00af\7\n\2\2\u00af\u00b0\7#\2\2\u00b0\u00b1\7\b\2\2\u00b1\u00b2\7#\2"+
		"\2\u00b2\u00b3\7\4\2\2\u00b3#\3\2\2\2\u00b4\u00b5\7\5\2\2\u00b5\u00b6"+
		"\7\n\2\2\u00b6\u00b7\7#\2\2\u00b7\u00b8\7\b\2\2\u00b8\u00b9\7#\2\2\u00b9"+
		"\u00ba\7\4\2\2\u00ba%\3\2\2\2\u00bb\u00bc\7\25\2\2\u00bc\u00bd\7\n\2\2"+
		"\u00bd\u00be\7#\2\2\u00be\u00bf\7\4\2\2\u00bf\'\3\2\2\2\u00c0\u00c1\7"+
		"\t\2\2\u00c1\u00c2\7\n\2\2\u00c2\u00c3\7#\2\2\u00c3\u00c4\7\b\2\2\u00c4"+
		"\u00c5\7#\2\2\u00c5\u00c6\7\4\2\2\u00c6)\3\2\2\2\u00c7\u00c8\7\6\2\2\u00c8"+
		"\u00c9\7\n\2\2\u00c9\u00ca\7#\2\2\u00ca\u00cb\7\b\2\2\u00cb\u00cc\7#\2"+
		"\2\u00cc\u00cd\7\4\2\2\u00cd+\3\2\2\2\u00ce\u00d3\5.\30\2\u00cf\u00d0"+
		"\7\b\2\2\u00d0\u00d2\5.\30\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3"+
		"\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4-\3\2\2\2\u00d5\u00d3\3\2\2\2"+
		"\u00d6\u00d7\7#\2\2\u00d7\u00d8\7\n\2\2\u00d8\u00dd\5\60\31\2\u00d9\u00da"+
		"\7\b\2\2\u00da\u00dc\5\60\31\2\u00db\u00d9\3\2\2\2\u00dc\u00df\3\2\2\2"+
		"\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00dd"+
		"\3\2\2\2\u00e0\u00e1\7\4\2\2\u00e1/\3\2\2\2\u00e2\u00e6\5\62\32\2\u00e3"+
		"\u00e6\5\64\33\2\u00e4\u00e6\5\66\34\2\u00e5\u00e2\3\2\2\2\u00e5\u00e3"+
		"\3\2\2\2\u00e5\u00e4\3\2\2\2\u00e6\61\3\2\2\2\u00e7\u00e8\7\3\2\2\u00e8"+
		"\u00e9\7)\2\2\u00e9\u00ea\7-\2\2\u00ea\63\3\2\2\2\u00eb\u00ec\7\22\2\2"+
		"\u00ec\u00ed\7)\2\2\u00ed\u00ee\7-\2\2\u00ee\65\3\2\2\2\u00ef\u00f0\7"+
		"\26\2\2\u00f0\u00f1\7)\2\2\u00f1\u00f2\7\27\2\2\u00f2\67\3\2\2\2\17>H"+
		"NP[\u0082\u0090\u0098\u00a4\u00ab\u00d3\u00dd\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}