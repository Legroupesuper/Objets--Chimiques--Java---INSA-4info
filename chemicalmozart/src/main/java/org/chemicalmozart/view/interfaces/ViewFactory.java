package org.chemicalmozart.view.interfaces;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;


/**
 *  Description of the interface ViewFactory.
 *
 *
 */
public interface ViewFactory {


    /**
     *  Description of the method buildMainDialog.
     *
     *
     * @return null
     */
    public JDialog buildMainDialog() ;

    /**
     *  Description of the method buildSecondDialog.
     *
     *
     * @return null
     */
    public JDialog buildSecondDialog() ;

    /**
     *  Description of the method buildCloseButton.
     *
     *
     * @return null
     */
    public JButton buildCloseButton() ;

    /**
     *  Description of the method buildRunButton.
     *
     *
     * @return null
     */
    public JButton buildRunButton() ;

    /**
     *  Description of the method buildPlayButton.
     *
     *
     * @return null
     */
    public JButton buildPlayButton() ;

    /**
     *  Description of the method buildSaveButton.
     *
     *
     * @return null
     */
    public JButton buildSaveButton() ;

    /**
     *  Description of the method buildSpeedTextField.
     *
     *
     * @return null
     */
    public JTextField buildSpeedTextField() ;

    /**
     *  Description of the method buildMeasureNumberTextField.
     *
     *
     * @return null
     */
    public JTextField buildMeasureNumberTextField() ;

    /**
     *  Description of the method buildScaleTextField.
     *
     *
     * @return null
     */
    public JTextField buildScaleTextField() ;

}