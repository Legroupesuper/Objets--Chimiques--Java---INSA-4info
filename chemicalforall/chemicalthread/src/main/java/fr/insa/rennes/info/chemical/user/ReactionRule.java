/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/


package fr.insa.rennes.info.chemical.user;




/**
 * This interface describes a ReactionRule which can be added and react in a solution.
 * A ReactionRule is mainly composed of a selection rule ({@link #computeSelect() computeSelect}), a reaction process
 * ({@link #computeResult() computeResult()}, and reagents which are the attributes of the implementation class.<br/>
 * The different reagents of the reaction can be represented by creating attributes in the implementation class. 
 * They can be any kind of Java Objects (primitive or created ones). For each attribute X created, two methods should be present,
 * where X represents the attribute name and typeX its type (eg. Integer, String...):<br/>
 * It takes two reagents :<br />
 * <ul>
 * 	<li><code>public void setX(typeX x)</code> </li>
 *  <li><code>public typeX getX()</code></li>
 *  </ul>
 *  <br/>
 *  These are classic setters and getters, and should be implemented as usual. Refer to usecases for examples.</br>
 *  The attributes can then be used in the ({@link #computeResult() computeResult()} method for the reaction process and the 
 *  ({@link #computeSelect() computeSelect}) method fot the selection process.
 *  During the execution, random reagents will be choosen in the solution to fit with the attributes present in the 
 *  ReactionRule implementation. If a reagent should not be instanciated during execution, the annotation <code>@Dontreact</code> can be used
 *  before declaration of this reagent.
 *
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public interface ReactionRule {

	/** 
	 * Enumerates the different possible multiplicities for a reaction.<br/>
	 * <p> <code>ONE_SHOT</code> indicates that the reaction will happen only once, 
	 * the ReactionRule will then be removed from the solution.</p>
	 *  <p><code>INFINITY_SHOT</code> indicates that the reaction will always happen if reagents are present,
	 * until solution inertia. 
	 * Contrary to OneShot, the ReactionRule will not be removed from the solution after reaction.</p>
	 */  
	public static enum Multiplicity{
		/**
		 * Indicates that the reaction will happen only once, 
		 * the ReactionRule will then be removed from the solution.
		 */
		ONE_SHOT, 
		/**
		 * Indicates that the reaction will always happen if reagents are present,
		 * until solution inertia. 
		 * Contrary to OneShot, the ReactionRule will not be removed from the solution after reaction.
		 */
		INFINITY_SHOT};

	/**
	 * This method describes the reaction process. If reagents are needed, attributes can be created in 
	 * the ReactionRule implementation and used there.<br/>
	 * Any kind of operations with (or without) reagents can be done in this method. However, it must
	 * returns a Java <code>Object</code> array containing the results of the reaction. For example, if a 
	 * reaction processes a choice between two reagents in order to keep only one, this one should be returned
	 * in an Object array at the end. A reaction that simply consumes its reagents should return an empty Object array.<br/>
	 * At the end of the reaction process during the execution, every reagent is consumed and then disappear from the solution,
	 * unless it is returned in the result array.
	 * Refer to usecases for examples.
	 * @return an Object array containing what should be reinjected in the solution at the end of the reaction.
	 */
	public Object[] computeResult();
	/**
	 * This method describes conditions of reagents selection. During execution, the attributes of the ReactionRule are
	 * instanciated with matching-type reagents from the solution. This method is then called to check if the instanciated
	 * reagents respect particular conditions.<br/>
	 * This method should therefore contains every needed verification on the reagents. For example if the reaction should only
	 * react with integers higher than 0, it must be checked there.<br/>
	 * In case no selection is necessary, the annotation <code>@Dontuse</code> can be used before declaration. It is then equivalent
	 * to simply returning true, but treated differently in order to speed up the execution.
	 * Refer to usecases for examples.  
	 * @return <code>true</code> if the present reagents are compatible with the reaction.
	 */
	public boolean computeSelect();

	/**
	 * This method must return the multiplicity of the reaction. Two of them exist:<br/>
	 * <p> <code>ONE_SHOT</code> indicates that the reaction will happen only once, 
	 * the ReactionRule will then be removed from the solution.</p>
	 *  <p><code>INFINITY_SHOT</code> indicates that the reaction will always happen if reagents are present,
	 * until solution inertia. 
	 * Contrary to OneShot, the ReactionRule will not be removed from the solution after reaction.</p>
	 * @return the multiplicity of the reaction (<code>ONE_SHOT</code> or <code>INFINITY_SHOT</code>). 
	 */
	public Multiplicity getMultiplicity();
}