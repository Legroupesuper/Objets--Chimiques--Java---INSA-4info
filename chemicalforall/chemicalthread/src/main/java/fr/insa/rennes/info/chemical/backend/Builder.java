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
package fr.insa.rennes.info.chemical.backend;

/**
 * A basic interface describing the <em>builder design pattern</em> used within the library.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 * @param <T> The type of the product to be built by this builder.
 */
interface Builder<T> {
	/**
	 * This function must build the product of the <em>builder design pattern</em>.
	 * This function may throw a ChemicalException if the builder needs more parameters before 
	 * effectively building the product or if these parameters are incorrect. 
	 * These parameters depend on the product built, and the builder
	 * implementation class have to provide the director a setter for each parameter.
	 * @throws ChemicalException
	 */
	public void build() throws ChemicalException;
	
	/**
	 * Returns the built product.
	 * Throws a {@link ChemicalException} if the product isn't built yet. 
	 * A prior call to {@link #build()} is necessary.
	 * @return The built product.
	 * @throws ChemicalException
	 */
	public T getProduct() throws ChemicalException;
}