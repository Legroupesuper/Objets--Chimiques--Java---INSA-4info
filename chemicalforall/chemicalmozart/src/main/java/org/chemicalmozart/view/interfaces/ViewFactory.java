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
package org.chemicalmozart.view.interfaces;

import org.chemicalmozart.view.implementations.BarNumberTextFieldView;
import org.chemicalmozart.view.implementations.CloseButtonView;
import org.chemicalmozart.view.implementations.MainView;
import org.chemicalmozart.view.implementations.PlayButtonView;
import org.chemicalmozart.view.implementations.RunButtonView;
import org.chemicalmozart.view.implementations.SaveButtonView;
import org.chemicalmozart.view.implementations.ScaleTextFieldView;
import org.chemicalmozart.view.implementations.SecondView;
import org.chemicalmozart.view.implementations.SpeedTextFieldView;


/**
 *  This interface is used to define the factory calls. 
 */
public interface ViewFactory {


    /**
     *  This method is used to build the MainView windows
     * @return a new MainWiew
     */
    public MainView buildMainView() ;

    /**
     * Description of the method buildSecondDialog.
     * @return a new SecondView
     */
    public SecondView buildView() ;

    /**
     * This method creates the CloseButtonView
     * @return a new CloseButtonView
     */
    public CloseButtonView buildCloseButton() ;

    /**
     * This method creates the RunButtonView
     * @return a new RunButtonView
     */
    public RunButtonView buildRunButton() ;

    /**
     * This method creates the PlayButtonView
     * @return a new PlayButtonView
     */
    public PlayButtonView buildPlayButton() ;

    /**
     * This method creates the SaveButtonView
     * @return a new SaveButtonView
     */
    public SaveButtonView buildSaveButton() ;

    /**
     * This method creates the SpeedTextFieldView
     * @return a new SpeedTextFieldView
     */
    public SpeedTextFieldView buildSpeedTextField() ;

    /**
     * This method creates the BarNumberTextFieldView
     * @return a new BarNumberTextFieldView
     */
    public BarNumberTextFieldView buildMeasureBarTextField() ;

    /**
     * This method creates the ScaleTextFieldView
     * @return a new ScaleTextFieldView
     */
    public ScaleTextFieldView buildScaleTextField() ;

}