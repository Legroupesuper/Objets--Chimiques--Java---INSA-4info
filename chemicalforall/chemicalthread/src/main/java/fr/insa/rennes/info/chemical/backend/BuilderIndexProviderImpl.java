package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * Standard implementation of {@link BuilderIndexProvider}. Another implementation can be created
 * by implementing {@link BuilderIndexProvider}.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public class BuilderIndexProviderImpl implements BuilderIndexProvider {
	/**
	 * The product built, here a {@link IndexProvider}
	 */
	private IndexProvider _ip;
	/**
	 * The solution parameter.
	 */
	private Solution _solution;
	/**
	 * The reaction rule parameter.
	 */
	private ReactionRule _rr;
	/**
	 * The reaction rule's fields parameter.
	 */
	private Field[] _rrFields;
	/**
	 * The strategy parameter.
	 */
	private Strategy _strategy;
	
	/**
	 * Indicates if the product's building is finished. 
	 * Allows a check before sending the product when the user requests for it as it is not built yet. 
	 */
	private boolean _complete;
	
	/**
	 * Constructs a standard builder for a {@link IndexProvider}.
	 * Initializes all the builder's parameters to <code>null</code> and the {@link #_complete} field
	 * to false.
	 */
	public BuilderIndexProviderImpl() {
		_complete = false;
		_solution = null;
		_rr = null;
		_rrFields = null;
		_strategy = null;
	}
	
	
	public IndexProvider getProduct() throws ChemicalException {
		if(!_complete)
			throw new ChemicalException("The index provider is not entirely built yet.");
		return _ip;
	}
	
	public void setSolution(Solution sol) {
		this._solution = sol;
	}

	public void setReactionRule(ReactionRule rr) {
		this._rr = rr;
	}

	public void setReactionRuleFields(Field[] rrFields) {
		this._rrFields = rrFields;
	}

	public void setStrategy(Strategy strategy) {
		this._strategy = strategy;
	}
	
	/**
	 * Processes to the building of the index provider.
	 * This functions first checks if all the parameters are instantiated, if not a ChemicalException
	 * explaining the issue is thrown.<br />
	 * The only operation is then to create a {@link SubIndexProviderSolution} builder 
	 * to build the root {@link SubIndexProviderSolution} (see {@link IndexProvider} structure). 
	 * If any error occurs during the building of the {@link SubIndexProviderSolution}, the error is 
	 * directly reported. At the end, the product is declared as complete 
	 * ({@link #_complete} is set to <code>true</code>).<br />
	 * Note: the implementation used to build the {@link IndexProvider} is the standard implementation.
	 * @see IndexProvider
	 * @see SubIndexProviderSolution
	 * @see BuilderSubIndexProviderSolution
	 */
	public void build() throws ChemicalException {
		if(_solution == null || _rr == null || _rrFields == null  || _strategy == null)
			throw new ChemicalException("All parameters need to be instanciated to build the IndexProvider.");
		
		//First, use a builder for the SubIndexProvider
		BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolutionImpl();
		sipSolBuilder.setSolution(_solution);
		sipSolBuilder.setReactionRule(_rr);
		sipSolBuilder.setReactionRuleFields(_rrFields);
		sipSolBuilder.build();
		
		//Then simply instanciate the IndexProvider object
		_ip = new IndexProvider(sipSolBuilder.getProduct(), _strategy); 
		
		_complete = true;
	}
}
