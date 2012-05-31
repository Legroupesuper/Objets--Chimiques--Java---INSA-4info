
import java.util.Arrays;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;


public class TestIndexProvider {
	public static void main(String[] args) {
		Solution main = new Solution();
		Solution subsol = new Solution();
		int[] val = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
		String [] val2 = new String[]{"toto", "tata", "tutu", "tonton", "toutou", "titi"};
		subsol.addAll(Arrays.asList(val));
		subsol.addAll(Arrays.asList(val2));
		main.add(subsol);
		
		ReactionRule rr = new ReactionRule() {
			private SubSolution<SubSolutionElements> _subSol;

			@SuppressWarnings("unused")
			public SubSolution<SubSolutionElements> get_subSol() {
				return _subSol;
			}

			@SuppressWarnings("unused")
			public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
				this._subSol = _subSol;
			}

			public Multiplicity getMultiplicity() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean computeSelect() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Object[] computeResult() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		main.add(rr);
	}
}