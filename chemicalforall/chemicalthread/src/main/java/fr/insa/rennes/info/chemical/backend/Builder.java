package fr.insa.rennes.info.chemical.backend;

/**
 * A basic interface describing the <em>builder design pattern</em> used within the library.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 * @param <T> The type of the product to be built by this builder.
 */
public interface Builder<T> {
	/**
	 * This function must build the product of the <em>builder design pattern</em>.
	 * This function may throw a ChemicalException if the builder needs more parameters before 
	 * processing to the effective building. These parameters depend on the product built, and the builder
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
