package fr.insa.rennes.info.chemical.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is use to tell that the next field is not a reactive of the reactionrule
 * @author Le groupe super
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dontputback {

}