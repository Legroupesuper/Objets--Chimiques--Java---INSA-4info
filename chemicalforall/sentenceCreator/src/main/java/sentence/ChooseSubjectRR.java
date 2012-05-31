

package sentence;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class ChooseSubjectRR implements ReactionRule{

	/**
	 * Take a subject from the subjects solution then put it in the solution.
	 * It takes the following reagents: (they must appear in this order)
	 * <ul>
	 * 	<li>SubSolution _subSol
	 * 		<ul>
	 * 			<li>SubjectType : To identify the solution</li>
	 * 			<li>Subject : a subject</li>
	 * 		</ul>
	 *  </li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _subSol;

	public ChooseSubjectRR() {
		super();
		this._subSol = new SubSolution<SubSolutionElements>();
		this._subSol.addType(SubjectType.class);
		this._subSol.addType(Subject.class);
	}

	public Object[] computeResult() {
		Subject s = (Subject)this._subSol.getElements().get(1);
		return new Object[]{s};
	}

	public boolean computeSelect() {
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
		return "ChooseSubjectRR";
	}


}