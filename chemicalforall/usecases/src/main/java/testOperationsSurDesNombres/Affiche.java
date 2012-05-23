package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class Affiche implements ReactionRule {

	private String _s;

	public Object[] computeResult() {
		System.out.println(this._s);
		return new Object[]{};
	}

	@Dontuse
	public boolean computeSelect() {
		return true;
	}

	public String get_s(){
		return this._s;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_s(String str){
		this._s = str;
	}

}
