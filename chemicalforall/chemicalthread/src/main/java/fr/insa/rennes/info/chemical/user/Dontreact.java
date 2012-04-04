package fr.insa.rennes.info.chemical.user;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation permits to indicate that the following reactive should not react during the reaction process.
 * It can be used before any kind of reactive, including standard Java objects and ReactionRules.<br/>
 * By specifying @Dontreact before a reactive, the user is ensured that the following field will not be instantiated,
 * and therefore not be present in the solution. It can be useful in order to use variables in a ReactionRule implementation
 * that are not needed for the reaction.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dontreact {

}
