package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * <p>
 * An interface specifying a builder for a {@link SubIndexProviderSolution}. <br />
 * In order to follow the <em>builder design pattern</em> and for code reusability,
 * an interface is used so that the implementation can be easily changed.
 * </p>
 * <p>
 *  The only use of this builder is supposed to be in the {@link BuilderIndexProvider}. 
 * Indeed, an index provider possesses several {@link SubIndexProvider}, 
 * themselves recursively containing {@link SubIndexProvider}.The recursion ends on a 
 * {@link SubIndexProviderElement}. See {@link IndexProvider} documentation for more.<br />
 * </p>
 * <p>
 * The different parameters of the builder are the solution, the reaction rule, the reaction rule's field, 
 * the parameterized type, and the {@link SubSolution} field. See the associated setters for a detailed descrition.<br />
 * The only mandatory parameters are the solution and the reaction rule. You then have the choice between
 * giving either the reaction rule's fields (see {@link #setReactionRuleFields(Field[])}), 
 * or the parameterized type ({@link #setParamType(ParameterizedType)} and the {@link SubSolution} 
 * field ({@link #setSubSolutionField(Field)}). The first case is used to build the root {@link SubIndexProviderSolution} of 
 * a {@link IndexProvider}, and the second one is used to recursively build {@link SubIndexProviderSolution}.
 * </p>
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see SubIndexProviderSolution
 */
public interface BuilderSubIndexProviderSolution extends Builder<SubIndexProviderSolution> {
	/**
	 * Returns the built SubIndexProviderSolution, following the parameters given.
	 * The different parameters are the solution, the reaction rule, the reaction rule's field, 
	 * the parameterized type, and the {@link SubSolution} field. See the associated setters 
	 * for a detailed description.
	 * @see #setParamType(ParameterizedType)
	 * @see #setReactionRule(ReactionRule)
	 * @see #setReactionRuleFields(Field[])
	 * @see #setSolution(Solution)
	 * @see #setSubSolutionField(Field)
	 */
	public SubIndexProviderSolution getProduct() throws ChemicalException;
	/**
	 * Sets the reaction rule parameter to build the sub index provider.
	 * The reaction rule parameter specifies which reaction rule needs reactives.
	 * @param rr The reaction rule that needs the index provider.
	 */
	public void setReactionRule(ReactionRule rr);
	/**
	 * Sets the reaction rule's fields parameter to build the sub index provider.
	 * These fields are the reactives needed by the reaction rule. Basically, these are the attributes 
	 * of the reaction rule class, without the ones annotated {@link Dontreact}.
	 * @param rrFields The reaction rule's fields
	 * @see Dontreact
	 */
	public void setReactionRuleFields(Field[] rrFields);
	/**
	 * Sets the parameterized type parameter to build the sub index provider.
	 * This type is the parameterized type of a {@link SubSolution} object. This {@link SubSolution}
	 * object is an attribute of the reaction rule given to the builder. This class is used
	 * to go down in the inner solutions of the reaction rule's main solution. See {@link SubSolutionReactivesAccessor}.
	 * @param paramType The parameterized type of a {@link SubSolution} object attribute of the specified reaction rule.
	 * @see SubSolution
	 * @see SubSolutionReactivesAccessor
	 */
	public void setParamType(ParameterizedType paramType);
	/**
	 * Sets the solution parameter to build the sub index provider.
	 * The specified solution is the solution in which the sub index provider has to rely to provide 
	 * indexes on the reactives asked by the reaction rule parameter. 
	 * @param sol The solution on which the sub index provider has to iterate.
	 */
	public void setSolution(Solution sol);
	/**
	 * Sets the {@link SubSolution} field parameter to build the sub index provider.
	 * This parameter is actually a field of the reaction rule parameter. 
	 * See {@link SubSolutionReactivesAccessor}.
	 * @param subSolField The {@link SubSolution} field of the reaction rule.
	 * @see #setParamType(ParameterizedType)
	 * @see SubSolution
	 * @see SubSolutionReactivesAccessor
	 */
	public void setSubSolutionField(Field subSolField);
}
