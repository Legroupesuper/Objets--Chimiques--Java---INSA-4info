
package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;
/*
 * Cette règle de réaction multiplie par 2 les entiers
 * égaux à l'attribut _b
 */
public class Mult2RR implements ReactionRule{
	Integer _a;
	@Dontreact
	Integer _b;

	public Mult2RR(Integer i){
		this._b = i;
	}

	public Object[] computeResult() {
		return new Object[] {this._a*2};
	}

	public boolean computeSelect() {
		return this._a.equals(this._b);
	}

	public Integer get_a(){
		return this._a;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_a(Integer i){
		this._a = i;
	}


}