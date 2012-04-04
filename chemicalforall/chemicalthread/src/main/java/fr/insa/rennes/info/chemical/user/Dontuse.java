package fr.insa.rennes.info.chemical.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation permits to indicate that the computeSelect() method will not be used in the reaction.
 * It is equivalent to returning true in a computeSelect() method where no particular preconditions are 
 * required for the reaction to process.<br/>
 * The main goal of this annotation is to speed up the execution of the reaction by skipping the selection process.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Dontuse {

}