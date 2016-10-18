// Generated from /Users/epaln/NetBeansProjects/nespros/src/main/java/com/imag/nespros/runtime/lang/ESCExpr.g4 by ANTLR 4.2.2
package com.imag.netah.core.lang;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ESCExprParser}.
 */
public interface ESCExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ESCExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull ESCExprParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull ESCExprParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#esc_expression}.
	 * @param ctx the parse tree
	 */
	void enterEsc_expression(@NotNull ESCExprParser.Esc_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#esc_expression}.
	 * @param ctx the parse tree
	 */
	void exitEsc_expression(@NotNull ESCExprParser.Esc_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void enterAggregate(@NotNull ESCExprParser.AggregateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void exitAggregate(@NotNull ESCExprParser.AggregateContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#count}.
	 * @param ctx the parse tree
	 */
	void enterCount(@NotNull ESCExprParser.CountContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#count}.
	 * @param ctx the parse tree
	 */
	void exitCount(@NotNull ESCExprParser.CountContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(@NotNull ESCExprParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(@NotNull ESCExprParser.ParamContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(@NotNull ESCExprParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(@NotNull ESCExprParser.PredicateContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#window_specification}.
	 * @param ctx the parse tree
	 */
	void enterWindow_specification(@NotNull ESCExprParser.Window_specificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#window_specification}.
	 * @param ctx the parse tree
	 */
	void exitWindow_specification(@NotNull ESCExprParser.Window_specificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#batch_n}.
	 * @param ctx the parse tree
	 */
	void enterBatch_n(@NotNull ESCExprParser.Batch_nContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#batch_n}.
	 * @param ctx the parse tree
	 */
	void exitBatch_n(@NotNull ESCExprParser.Batch_nContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#sum}.
	 * @param ctx the parse tree
	 */
	void enterSum(@NotNull ESCExprParser.SumContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#sum}.
	 * @param ctx the parse tree
	 */
	void exitSum(@NotNull ESCExprParser.SumContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#filterdef}.
	 * @param ctx the parse tree
	 */
	void enterFilterdef(@NotNull ESCExprParser.FilterdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#filterdef}.
	 * @param ctx the parse tree
	 */
	void exitFilterdef(@NotNull ESCExprParser.FilterdefContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#min}.
	 * @param ctx the parse tree
	 */
	void enterMin(@NotNull ESCExprParser.MinContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#min}.
	 * @param ctx the parse tree
	 */
	void exitMin(@NotNull ESCExprParser.MinContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#slidingwin}.
	 * @param ctx the parse tree
	 */
	void enterSlidingwin(@NotNull ESCExprParser.SlidingwinContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#slidingwin}.
	 * @param ctx the parse tree
	 */
	void exitSlidingwin(@NotNull ESCExprParser.SlidingwinContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#or_expression}.
	 * @param ctx the parse tree
	 */
	void enterOr_expression(@NotNull ESCExprParser.Or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#or_expression}.
	 * @param ctx the parse tree
	 */
	void exitOr_expression(@NotNull ESCExprParser.Or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#max}.
	 * @param ctx the parse tree
	 */
	void enterMax(@NotNull ESCExprParser.MaxContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#max}.
	 * @param ctx the parse tree
	 */
	void exitMax(@NotNull ESCExprParser.MaxContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#cpu_spec}.
	 * @param ctx the parse tree
	 */
	void enterCpu_spec(@NotNull ESCExprParser.Cpu_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#cpu_spec}.
	 * @param ctx the parse tree
	 */
	void exitCpu_spec(@NotNull ESCExprParser.Cpu_specContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#mbatch}.
	 * @param ctx the parse tree
	 */
	void enterMbatch(@NotNull ESCExprParser.MbatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#mbatch}.
	 * @param ctx the parse tree
	 */
	void exitMbatch(@NotNull ESCExprParser.MbatchContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(@NotNull ESCExprParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(@NotNull ESCExprParser.ParamsContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#operator_type}.
	 * @param ctx the parse tree
	 */
	void enterOperator_type(@NotNull ESCExprParser.Operator_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#operator_type}.
	 * @param ctx the parse tree
	 */
	void exitOperator_type(@NotNull ESCExprParser.Operator_typeContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#timefixed}.
	 * @param ctx the parse tree
	 */
	void enterTimefixed(@NotNull ESCExprParser.TimefixedContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#timefixed}.
	 * @param ctx the parse tree
	 */
	void exitTimefixed(@NotNull ESCExprParser.TimefixedContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#streamdef}.
	 * @param ctx the parse tree
	 */
	void enterStreamdef(@NotNull ESCExprParser.StreamdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#streamdef}.
	 * @param ctx the parse tree
	 */
	void exitStreamdef(@NotNull ESCExprParser.StreamdefContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#dimension}.
	 * @param ctx the parse tree
	 */
	void enterDimension(@NotNull ESCExprParser.DimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#dimension}.
	 * @param ctx the parse tree
	 */
	void exitDimension(@NotNull ESCExprParser.DimensionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#operator_def}.
	 * @param ctx the parse tree
	 */
	void enterOperator_def(@NotNull ESCExprParser.Operator_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#operator_def}.
	 * @param ctx the parse tree
	 */
	void exitOperator_def(@NotNull ESCExprParser.Operator_defContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expression(@NotNull ESCExprParser.And_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expression(@NotNull ESCExprParser.And_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(@NotNull ESCExprParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(@NotNull ESCExprParser.TermContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#selection_mode}.
	 * @param ctx the parse tree
	 */
	void enterSelection_mode(@NotNull ESCExprParser.Selection_modeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#selection_mode}.
	 * @param ctx the parse tree
	 */
	void exitSelection_mode(@NotNull ESCExprParser.Selection_modeContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#memory_spec}.
	 * @param ctx the parse tree
	 */
	void enterMemory_spec(@NotNull ESCExprParser.Memory_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#memory_spec}.
	 * @param ctx the parse tree
	 */
	void exitMemory_spec(@NotNull ESCExprParser.Memory_specContext ctx);

	/**
	 * Enter a parse tree produced by {@link ESCExprParser#average}.
	 * @param ctx the parse tree
	 */
	void enterAverage(@NotNull ESCExprParser.AverageContext ctx);
	/**
	 * Exit a parse tree produced by {@link ESCExprParser#average}.
	 * @param ctx the parse tree
	 */
	void exitAverage(@NotNull ESCExprParser.AverageContext ctx);
}