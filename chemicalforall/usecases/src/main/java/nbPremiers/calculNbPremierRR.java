
package nbPremiers;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class calculNbPremierRR implements ReactionRule {

	private Integer _a;
	private Integer _b;

	public Object[] computeResult() {
		return new Object[] {this._b};
	}

	public boolean computeSelect() {
		return (this._a%this._b == 0);
	}

	public Integer get_a(){
		return this._a;
	}
	public Integer get_b(){
		return this._b;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_a(Integer i){
		this._a = i;
	}

	public void set_b(Integer i){
		this._b = i;
	}

}