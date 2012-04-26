package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This rule compute the degree of harmony that will be played after the current degree.
 * It creates a temporary solution in which all the possible degrees are present. The created solution is identified with a Temporary object.
 * It also creates a PickOneRR and a RythmeHRR.
 * <br />
 * Here is how it works :<br />
 * It takes two reagents :<br />
 * <ul>
 * 	<li>DegreeImpl _degree</li>
 *  <li>SubSolution _barInCreationSolution</li>
 *  </ul>
 * The computeSelect is used, see the computeSelect and computeResult documentation for more informations
 */
public class HarmonicRR2 implements ReactionRule{

	/**
	 * Represents the current Degree that we will use for the next chord.
	 */
	public DegreeImpl _degree;
	/**
	 * Represents the BarInCreation Solution. It must contain :
	 * <ul>
	 * 	<li>a <b>BarInCreation</b> object to identify the good solution</li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _barInCreationSolution;

	/**
	 * The constructor is used by the library to instantiate the _barInCreationSolution element.
	 */
	public HarmonicRR2() {
		super();
		SubSolutionElements elts = new SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		elts.setTypeList(l);
		_barInCreationSolution = new SubSolution<SubSolutionElements>(elts);
	}

	/**
	 * It musn't return _degree into the main solution !<br />
	 * After a 2nd degree, you can go :<br />
	 * <ul>
	 * <li> On a 1nd degree on 10%</li>
	 * <li> On a 2nd degree on 10%</li>
	 * <li> On a 3rd degree on 10%</li>
	 * <li> On a 4th degree on 25%</li>
	 * <li> On a 5th degree on 30%</li>
	 * <li> On a 6th degree on 15%</li>
	 *  </ul><br />
	 * <br />
	 * ! _degree must be added to _barInCreationSolution !<br />
	 * <br />
	 * The computResult add a temporary solution into the main solution. To identify this solution, you must add a
	 * Temporary object into the solution. This object will be used later.
	 * <br /><br />
	 * It also add the _degree in the solution identified by a BarInCreationObject. It must also put back the BarInCreation object into
	 *  _barInCreationSolution because, due to the reaction, it's no longer in _barInCreationSolution.
	 * <br /><br />
	 * @return a Solution identified by a HarmonicSol object with a proportional number of each possible degrees, <br />
	 * a PickOneRR and a RythmeHRR.<br />

	 */
	public Object[] computeResult() {
		//Temporary Solution which will be in the return statement
		Solution temporarySolution = new Solution();
		Temporary temporaryID = new Temporary();
		temporarySolution.add(temporaryID);
		temporarySolution.add(_degree);
		
		//Percentages
		int chancesOfGoingOnADegree1 = 10;
		int chancesOfGoingOnADegree2 = 10;
		int chancesOfGoingOnADegree3 = 10;
		int chancesOfGoingOnADegree4 = 25;
		int chancesOfGoingOnADegree5 = 30;
		int chancesOfGoingOnADegree6 = 15;
		
		//Modification of _barInCreationSolution
		Solution barInCreationSolution = _barInCreationSolution.getSolution();
		BarInCreation babar = new BarInCreation();
		babar.set_state(BarInCreationState.RYTHMEHRR);
		barInCreationSolution.add(babar);
		barInCreationSolution.add(_degree);
		
		//Creation of the harmonicSolution which will be in the return statement
		Solution harmonicSolution = new Solution();
		ArrayList<DegreeImpl> degreeList = new ArrayList<DegreeImpl>();
		for(int i = 0; i<chancesOfGoingOnADegree1;i++){
			degreeList.add(new DegreeImpl(1));	
		}
		for(int i = 0; i<chancesOfGoingOnADegree2;i++){
			degreeList.add(new DegreeImpl(2));
		}
		for(int i = 0; i<chancesOfGoingOnADegree3;i++){
			degreeList.add(new DegreeImpl(3));
		}
		for(int i = 0; i<chancesOfGoingOnADegree4;i++){
			degreeList.add(new DegreeImpl(4));
		}
		for(int i = 0; i<chancesOfGoingOnADegree5;i++){
			degreeList.add(new DegreeImpl(5));
		}
		for(int i = 0; i<chancesOfGoingOnADegree6;i++){
			degreeList.add(new DegreeImpl(6));
		}
		for (DegreeImpl deg : degreeList) {
			harmonicSolution.add(deg);
		}
		
		//Return statement
		return new Object[]{harmonicSolution,temporarySolution,new PickOneRR(),new RythmeHRR()};
	}

	/**
	 * This method must succeed if :<br />
	 *  _barInCreationSolution is a bar in creation solution <br />
	 *  && _degree.getValue() is equal to 2 (This is HarmonicRR2)
	 */
	public boolean computeSelect() {
		List<Object> barInCreationElements = _barInCreationSolution.getElements();
		boolean containsABarInCreationElement = false;
		boolean barInCreationInGoodState = false;
		if(barInCreationElements != null){
			if(barInCreationElements.size()>=1){
				containsABarInCreationElement = barInCreationElements.get(0) instanceof BarInCreation;
				if(containsABarInCreationElement){
					barInCreationInGoodState = 
					((BarInCreation)barInCreationElements.get(0)).get_state().equals(BarInCreation.BarInCreationState.HARMONICRR);
				}
			}
		}
		return barInCreationInGoodState && (_degree.get_value()==2);
	}

	/**
	 * @return the _barInCreationSolution
	 */
	public SubSolution<SubSolutionElements> get_barInCreationSolution() {
		return this._barInCreationSolution;
	}

	/**
	 * @return the _degree
	 */
	public DegreeImpl get_degree() {
		return this._degree;
	}

	/**
	 * This is an infinity-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	/**
	 * @param _barInCreationSolution the _barInCreationSolution to set
	 */
	public void set_barInCreationSolution(
			SubSolution<SubSolutionElements> _barInCreationSolution) {
		this._barInCreationSolution = _barInCreationSolution;
	}

	/**
	 * @param _degree the _degree to set
	 */
	public void set_degree(DegreeImpl _degree) {
		this._degree = _degree;
	}
}
