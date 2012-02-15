package org.chemicalmozart;

import javax.swing.JDialog;

import org.chemicalmozart.view.implementations.MainView;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	try {
			MainView dialog = new MainView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
