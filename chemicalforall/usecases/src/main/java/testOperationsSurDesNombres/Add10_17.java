package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;
/*
 * Cette règle de réaction prend en paramètre 2 entiers
 * (qui doivent être égaux à 10 et à 17) et une autre règle
 * de réaction de type Mult2RR
 */
public class Add10_17 implements ReactionRule{

	private Integer _attr17;
	private SubSolution<SubSolutionElements> _subSolution;

	public Add10_17(){
		super();
		this._subSolution = new SubSolution<SubSolutionElements>();
		this._subSolution.addType(Integer.class);
		this._subSolution.addType(Mult2RR.class);
	}

	public Object[] computeResult() {
		Integer i = (Integer)this._subSolution.getElements().get(0) + this._attr17;
		String s = "Cédric a "+i+" ans.";
		return new Object[] {s};
	}
	public boolean computeSelect() {
		boolean bool = ((Integer)this._subSolution.getElements().get(0)).equals(10);
		return bool && this._attr17.equals(17);
	}

	public Integer get_attr17(){
		return this._attr17;
	}

	public SubSolution<SubSolutionElements> get_subSolution(){
		return this._subSolution;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	public void set_attr17(Integer i){
		this._attr17 = i;
	}

	public void set_subSolution(SubSolution<SubSolutionElements> s){
		this._subSolution = s;
	}
}
