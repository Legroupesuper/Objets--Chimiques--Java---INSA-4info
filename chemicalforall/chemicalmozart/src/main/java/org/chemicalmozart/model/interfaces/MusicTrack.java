package org.chemicalmozart.model.interfaces;

import org.chemicalmozart.controler.Observable;



/**
 *  Description of the interface MusicTrack.
 *
 *
 */
public interface MusicTrack extends Observable {


    /**
     *  Description of the method writeMidiFile.
     *
     *
     * @param fileName
     */
    public void writeMidiFile(String fileName) ;

}