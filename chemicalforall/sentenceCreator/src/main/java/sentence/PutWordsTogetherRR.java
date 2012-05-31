
package sentence;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class PutWordsTogetherRR  implements ReactionRule{
	
	private Subject subject;
	private Verb verb;
	private Complement complement;
	
	public Object[] computeResult() {
		String s = subject.toString();
		String v = verb.toString();
		String c = complement.toString();
		String res = s+" "+v+" "+c;
		return new Object[]{res};
	}
	
	public boolean computeSelect() {
		return true;
	}
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Verb getVerb() {
		return verb;
	}
	public void setVerb(Verb verb) {
		this.verb = verb;
	}
	public Complement getComplement() {
		return complement;
	}
	public void setComplement(Complement complement) {
		this.complement = complement;
	}
	@Override
	public String toString() {
		return "PutWordsTogetherRR";
	}
	
}