package org.chemicalmozart.viewV2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.ImageIcon;

public class MainView extends JFrame {
	private JTextField tfTempo;
	private JTextField tfBarNumber;
	private JTextField tfScale;
	private JTextField textField;
	public MainView() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildComponents();
		pack();	
	}
	
	public void buildComponents(){
		JPanel panelInfos = new JPanel();
		JLabel lblChemicalMusicGenerator = new JLabel("Chemical Music Generator");
		lblChemicalMusicGenerator.setFont(new Font("Verdana", Font.BOLD, 15));
		GroupLayout gl_panelInfos = new GroupLayout(panelInfos);
		gl_panelInfos.setHorizontalGroup(
			gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
					.addGap(93)
					.addComponent(lblChemicalMusicGenerator)
					.addContainerGap(99, Short.MAX_VALUE))
		);
		gl_panelInfos.setVerticalGroup(
			gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChemicalMusicGenerator)
					.addContainerGap(381, Short.MAX_VALUE))
		);
		panelInfos.setLayout(gl_panelInfos);
		JPanel panelParameters = new JPanel();
		JLabel lblParameters = new JLabel("Parameters");
		lblParameters.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblTempo = new JLabel("Tempo :");
		JLabel lblBarNumber = new JLabel("Bar number :");
		JLabel lblScale = new JLabel("Scale :");
		JLabel lblMidiOutput = new JLabel("Midi Output :");
		JButton btnStartReaction = new JButton("Start Reaction");
		tfTempo = new JTextField();
		tfTempo.setColumns(10);
		tfBarNumber = new JTextField();
		tfBarNumber.setText("");
		tfBarNumber.setColumns(10);
		tfScale = new JTextField();
		tfScale.setText("");
		tfScale.setColumns(10);
		textField = new JTextField();
		textField.setColumns(10);
		JButton btnBrowse = new JButton("Browse");
		
		JLabel lblImageParameters = new JLabel("");
		lblImageParameters.setIcon(new ImageIcon(MainView.class.getResource("/img/workflow.png")));
		
		JLabel lblImageMusic = new JLabel("");
		lblImageMusic.setIcon(new ImageIcon(MainView.class.getResource("/img/music.png")));
		
		JLabel lblImageFolder = new JLabel("");
		lblImageFolder.setIcon(new ImageIcon(MainView.class.getResource("/img/folder_full.png")));
		GroupLayout gl_panelParameters = new GroupLayout(panelParameters);
		gl_panelParameters.setHorizontalGroup(
			gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImageMusic)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblImageParameters)
							.addGap(35)
							.addComponent(lblParameters)))
					.addContainerGap(93, Short.MAX_VALUE))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(129)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBarNumber)
						.addComponent(lblScale)
						.addComponent(lblTempo))
					.addGap(28)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tfScale, 0, 0, Short.MAX_VALUE)
						.addComponent(tfBarNumber, 0, 0, Short.MAX_VALUE)
						.addComponent(tfTempo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelParameters.createSequentialGroup()
					.addContainerGap(46, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panelParameters.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addComponent(lblImageFolder)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(4)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMidiOutput)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panelParameters.createSequentialGroup()
					.addContainerGap(84, Short.MAX_VALUE)
					.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
					.addGap(66))
		);
		gl_panelParameters.setVerticalGroup(
			gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblImageParameters))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(32)
							.addComponent(lblParameters)))
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(79)
							.addComponent(lblImageMusic))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(65)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTempo)
								.addComponent(tfTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBarNumber)
								.addComponent(tfBarNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblScale)
								.addComponent(tfScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(42)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblMidiOutput)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowse)))
						.addComponent(lblImageFolder))
					.addGap(50)
					.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(84, Short.MAX_VALUE))
		);
		panelParameters.setLayout(gl_panelParameters);
		JPanel panelTitle = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 716, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelInfos, 0, 0, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
					.addGap(69))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public static void main(String[] args){
		MainView view = new MainView();	
		view.setVisible(true);
	}
}
