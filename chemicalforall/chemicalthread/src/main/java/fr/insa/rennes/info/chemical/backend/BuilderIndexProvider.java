package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * <p>
 * An interface specifying an index provider builder.<br />
 * In order to follow the <em>builder design pattern</em> and for code reusability,
 * an interface is used so that the implementation can be easily changed.
 * </p>
 * <p>
 * The different parameters to give to the builder are the solution, the reaction rule, the reaction rule's fields, 
 * and the increment strategy of the index provider. See the associated setters for a detailed descrition.
 * </p>
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * 
 * @see IndexProvider
 * @see IncrementStrategy
 */
public interface BuilderIndexProvider extends Builder<IndexProvider> {
	/**
	 * Returns the built index provider, following the parameters given.
	 * The different parameters are the solution, the reaction rule, the reaction rule's fields, 
	 * and the increment strategy of the index provider. See the associated setters for a detailed description.
	 * @return The built index provider.
	 * @see #setReactionRule(ReactionRule)
	 * @see #setReactionRuleFields(Field[])
	 * @see #setSolution(Solution)
	 * @see #setStrategy(Solution.Strategy)
	 */
	public IndexProvider getProduct() throws ChemicalException;
	/**
	 * Sets the solution parameter to build the index provider.
	 * The specified solution is the solution on which the index provider has to base itself
	 * to provide indexes on the reactives asked by the reaction rule. 
	 * @param sol The solution on which the index provider has to iterate.
	 */
	public void setSolution(Solution sol);
	/**
	 * Sets the reaction rule parameter to build the index provider.
	 * The reaction rule parameter specifies what is the reaction rule that needs reactives.
	 * Indeed, an index provider is always created in order to find reactives for a 
	 * specific reaction rule.
	 * @param rr The reaction rule that needs reactives.
	 */
	public void setReactionRule(ReactionRule rr);
	/**
	 * Sets the reaction rule's fields parameter to build the index provider.
	 * These fields are the reactives needed by the reaction rule. Basically, these are the attributes 
	 * of the reaction rule class, without the ones annotated {@link Dontreact}.
	 * @param rrFields The reaction rule's fields
	 * @see Dontreact
	 */
	public void setReactionRuleFields(Field[] rrFields);
	/**
	 * Sets the iteration strategy parameter to build the index provider.
	 * The strategy determines in which order the reactives are given to the reaction rule.
	 * An ordered strategy will always give the same order of iteration and lead to the same execution result.
	 * A random strategy will allow indeterminism and possibly different results between two executions. 
	 * @param strategy The iteration's order/strategy.
	 * @see Solution.Strategy
	 */
	public void setStrategy(Strategy strategy);
}
