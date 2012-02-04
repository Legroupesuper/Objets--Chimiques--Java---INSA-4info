package org.chemicalmozart.model.interfaces;


import java.util.HashSet;

import controler.Observable;


/**
 *  Description of the interface Chord.
 *
 *
 */
public interface Chord extends Observable {
    /**
     *  Description of the method getDegrees.
     *
     *
     * @return null
     */
    public HashSet<Degree> getDegrees() ;

}