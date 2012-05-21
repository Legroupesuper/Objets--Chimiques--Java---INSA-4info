package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Result;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to put a bar into the result solution.
 * It takes the following reagents: (they must appear in this order)
 * <ul>
 * 	<li>SubSolution "barInCreaction"
 * 		<ul>
 * 			<li>BarInCreation : To identify the bar in creation</li>
 * 			<li>QuaterLeft : To test in the computeSelect if the bar is full.</li>
 * 		</ul>
 *  </li>
 *  <li>Subsolution "result"
 *  	<ul>
 *  		<li>Result : The object that identify the result solution.</li>
 *  	</ul>
 *  </li>
 * </ul>
 * It must put the first subsolution into the second one.
 */
public class MoveToResultRR implements ReactionRule{

	/**
	 * This subsolution represents the BarInCreation solution
	 */
	private SubSolution<SubSolutionElements> _subSolInCreation;
	/**
	 * This subsolution represents the result solution
	 */
	private SubSolution<SubSolutionElements> _subSolResult;

	/**
	 * The constructor is used by the library to instantiate _subSolInCreation and _subSolResult.
	 */
	public MoveToResultRR() {
		super();

		SubSolutionElements eltsSolInCreation = new SubSolutionElements();
		List<Class<? extends Object>> typeListSolInCreation = new ArrayList<Class<? extends Object>>();
		typeListSolInCreation.add(BarInCreation.class);
		eltsSolInCreation.setTypeList(typeListSolInCreation);
		_subSolInCreation = new SubSolution<SubSolutionElements>(eltsSolInCreation);

		SubSolutionElements eltsSolResult = new SubSolutionElements();
		List<Class<? extends Object>> typeListSolResult = new ArrayList<Class<? extends Object>>();
		typeListSolResult.add(Result.class);
		eltsSolResult.setTypeList(typeListSolResult);
		_subSolResult = new SubSolution<SubSolutionElements>(eltsSolResult);
	}

	/**
	 * The computeResult must add the BarInCreation solution into the Result solution.
	 * <br />
	 * It must put back the BarInCreation and the QuaterLeft in the BarInCreation solution and then add the BarInCreationSolution into
	 * the Result solution. It must also put back the Result object into the Result solution.
	 * @return The Result solution as described
	 */
	public Object[] computeResult() {
		Solution inCreationSolution = _subSolInCreation.getSolution();
		BarInCreation babar = new BarInCreation();
		QuaterLeft qLeft = new QuaterLeft(0);
		inCreationSolution.add(babar);
		inCreationSolution.add(qLeft);

		Solution resultSolution = _subSolResult.getSolution();
		Result resultID = new Result();
		resultSolution.add(resultID);
		resultSolution.add(inCreationSolution);

		return new Object[]{resultSolution};
	}

	/**
	 * The computeSelect has in charge to check that the configuration of the BarInCreation is correct.
	 * This means that it must contains a Quaterleft object with a value of 0. It must also contain at least
	 * one or several ChordImpl.
	 */
	public boolean computeSelect() {
		List<Object> subSolInCreationElements = _subSolInCreation.getElements();
		List<Object> subSolResultElements = _subSolResult.getElements();
		boolean subSolInCreation_ContainsBarInCreation = false;
		boolean subSolInCreation_ContainsQuaterLeft = false;
		boolean subSolInCreation_quaterLeftWithAValue0 = false;
		boolean subSolInCreation_containsAtLeastOneChodImpl = false;
		boolean subSolResult_containsResult = false;
		
		if(subSolInCreationElements != null && subSolResultElements != null){
			if(subSolInCreationElements.size()>=2){
				subSolInCreation_ContainsBarInCreation = subSolInCreationElements.get(0) instanceof BarInCreation;
				subSolInCreation_ContainsQuaterLeft = subSolInCreationElements.get(1) instanceof QuaterLeft;
				if(subSolInCreation_ContainsQuaterLeft){
					subSolInCreation_quaterLeftWithAValue0 = ((QuaterLeft)subSolInCreationElements.get(1)).getValue() == 0;
				}
				for (Object o : subSolInCreationElements){
					if(o instanceof ChordImpl){
						subSolInCreation_containsAtLeastOneChodImpl = true;
					}
				}

			}
			if(subSolResultElements.size()>=1){
				subSolResult_containsResult = subSolResultElements.get(0) instanceof Result;
			}
		}
			return subSolInCreation_ContainsBarInCreation && 
					subSolInCreation_quaterLeftWithAValue0 && 
					subSolInCreation_containsAtLeastOneChodImpl &&
					subSolResult_containsResult;
	}

	public SubSolution<SubSolutionElements> get_subSolInCreation() {
		return this._subSolInCreation;
	}
	public SubSolution<SubSolutionElements> get_subSolResult() {
		return this._subSolResult;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}
	public void set_subSolInCreation(SubSolution<SubSolutionElements> _subSolInCreation) {
		this._subSolInCreation = _subSolInCreation;
	}
	public void set_subSolResult(SubSolution<SubSolutionElements> _subSolResult) {
		this._subSolResult = _subSolResult;
	}
}
