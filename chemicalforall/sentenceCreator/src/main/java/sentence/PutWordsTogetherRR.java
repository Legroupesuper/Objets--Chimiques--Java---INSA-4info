/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
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