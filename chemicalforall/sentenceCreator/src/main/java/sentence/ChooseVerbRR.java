

package sentence;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class ChooseVerbRR implements ReactionRule{

	/**
	 * Take a verb from the verbs solution then put it in the solution.
	 * It takes the following reagents: (they must appear in this order)
	 * <ul>
	 * 	<li>SubSolution _subSol
	 * 		<ul>
	 * 			<li>SubjectType : To identify the solution</li>
	 * 			<li>Verb : a verb</li>
	 * 		</ul>
	 *  </li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _subSol;

	public ChooseVerbRR() {
		super();
		this._subSol = new SubSolution<SubSolutionElements>();
		this._subSol.addType(VerbType.class);
		this._subSol.addType(Verb.class);
	}

	public Object[] computeResult() {
		System.out.println("je suis dans computeResult de ChooseVerbRR");
		Verb v = (Verb)this._subSol.getElements().get(1);
		return new Object[]{v};
	}

	public boolean computeSelect() {
		System.out.println("je suis dans computeSelect de ChooseVerbRR");
		return true;
	}

	public SubSolution<SubSolutionElements> get_subSol() {
		return this._subSol;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
		this._subSol = _subSol;
	}

	@Override
	public String toString() {
		return "ChooseVerbRR";
	}

}