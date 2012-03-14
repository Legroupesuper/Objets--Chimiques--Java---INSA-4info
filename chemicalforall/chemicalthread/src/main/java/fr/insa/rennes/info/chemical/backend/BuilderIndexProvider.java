package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class BuilderIndexProvider {
	private IndexProvider _ip;
	private Solution _solution;
	private ReactionRule _rr;
	private Field[] _rrFields;
	private Strategy _strategy;
	private boolean _complete;
	
	public BuilderIndexProvider() {
		_complete = false;
		_solution = null;
		_rr = null;
		_rrFields = null;
		_strategy = null;
	}

	public IndexProvider getIndexProvider() throws ChemicalException {
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

	public void build() throws ChemicalException {
		if(_solution == null || _rr == null || _rrFields == null  || _strategy == null)
			throw new ChemicalException("All parameters need to be instanciated to build the IndexProvider.");
		
		//First, use a builder for the SubIndexProvider
		BuilderSubIndexProviderSolution sipSolBuilder = new BuilderSubIndexProviderSolution();
		sipSolBuilder.setSolution(_solution);
		sipSolBuilder.setReactionRule(_rr);
		sipSolBuilder.setReactionRuleFields(_rrFields);
		sipSolBuilder.build();
		
		//Then simply instanciate the IndexProvider object
		_ip = new IndexProvider(sipSolBuilder.getSubIndexProvider(), _strategy); 
		
		_complete = true;
	}
}
