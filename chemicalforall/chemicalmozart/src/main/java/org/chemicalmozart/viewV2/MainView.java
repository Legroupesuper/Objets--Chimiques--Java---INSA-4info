package org.chemicalmozart.viewV2;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.IconUIResource;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame {
	private JTextField tfTempo;
	private JTextField tfBarNumber;
	private JComboBox<String> tfScale;
	private JTextField textField;
	private File saveFile;
	private File loadFile;
	private JLabel lblLog;
	private JLabel lblPlay;
	private JLabel lblOpenFile;
	JLabel lblStop;
	private Sequencer sequencer;
	JLabel lblPlayingfilename;
	
	public MainView() {
		saveFile = null;
		loadFile = null;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildComponents();
		pack();	
	}
	
	public void buildComponents(){
		JPanel panelInfos = new JPanel();
		panelInfos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblMidiFile = new JLabel("Midi File :");
		lblMidiFile.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		lblPlayingfilename = new JLabel("");
		lblPlayingfilename.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		lblPlay = new JLabel("");
		lblPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lblPlayAction();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblPlayPressed();
			}
		});
		lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play.png")));
		
		lblStop = new JLabel("");
		lblStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblStopAction();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblStopPressed();
			}
		});
		lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop.png")));
		
		lblOpenFile = new JLabel("");
		lblOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				lblOpenFileAction();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblOpenFilePressed();
			}
		});
		lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open.png")));
		
		JLabel lblNowPlaying = new JLabel("Now Playing");
		lblNowPlaying.setForeground(new Color(139, 0, 0));
		lblNowPlaying.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JLabel lblImageEqualizer = new JLabel("");
		lblImageEqualizer.setIcon(new ImageIcon(MainView.class.getResource("/img/equalizer.png")));
		
		lblLog = new JLabel("");
		lblLog.setForeground(new Color(139, 0, 0));
		GroupLayout gl_panelInfos = new GroupLayout(panelInfos);
		gl_panelInfos.setHorizontalGroup(
			gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
					.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelInfos.createSequentialGroup()
							.addGap(6)
							.addComponent(lblOpenFile)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelInfos.createSequentialGroup()
									.addComponent(lblMidiFile)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblPlayingfilename, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
								.addGroup(gl_panelInfos.createSequentialGroup()
									.addComponent(lblImageEqualizer)
									.addGap(30)
									.addComponent(lblNowPlaying))))
						.addGroup(gl_panelInfos.createSequentialGroup()
							.addGap(77)
							.addComponent(lblPlay)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStop))
						.addGroup(gl_panelInfos.createSequentialGroup()
							.addGap(25)
							.addComponent(lblLog)))
					.addContainerGap())
		);
		gl_panelInfos.setVerticalGroup(
			gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
					.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panelInfos.createSequentialGroup()
							.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
								.addComponent(lblImageEqualizer)
								.addGroup(gl_panelInfos.createSequentialGroup()
									.addGap(25)
									.addComponent(lblNowPlaying)))
							.addGap(60)
							.addGroup(gl_panelInfos.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMidiFile)
								.addComponent(lblPlayingfilename, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(27)
							.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStop)
								.addComponent(lblPlay))
							.addGap(24))
						.addGroup(Alignment.TRAILING, gl_panelInfos.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblOpenFile)
							.addGap(74)))
					.addComponent(lblLog)
					.addContainerGap(124, Short.MAX_VALUE))
		);
		panelInfos.setLayout(gl_panelInfos);
		JPanel panelParameters = new JPanel();
		panelParameters.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblParameters = new JLabel("Parameters");
		lblParameters.setForeground(new Color(128, 0, 0));
		lblParameters.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblTempo = new JLabel("Tempo :");
		lblTempo.setFont(new Font("Verdana", Font.PLAIN, 11));
		JLabel lblBarNumber = new JLabel("Bar number :");
		lblBarNumber.setFont(new Font("Verdana", Font.PLAIN, 11));
		JLabel lblScale = new JLabel("Scale :");
		lblScale.setFont(new Font("Verdana", Font.PLAIN, 11));
		JLabel lblMidiOutput = new JLabel("Midi Output :");
		lblMidiOutput.setFont(new Font("Verdana", Font.PLAIN, 11));
		JButton btnStartReaction = new JButton("  Start Reaction");
		btnStartReaction.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnStartReaction.setHorizontalAlignment(SwingConstants.LEFT);
		btnStartReaction.setIcon(new ImageIcon(MainView.class.getResource("/img/rec_button.png")));
		tfTempo = new JTextField();
		tfTempo.setColumns(10);
		tfBarNumber = new JTextField();
		tfBarNumber.setText("");
		tfBarNumber.setColumns(10);
		tfScale = new JComboBox<String>();
		tfScale.setMaximumRowCount(12);
		tfScale.setModel(new DefaultComboBoxModel<String>(new String[] {"Do", "Do #", "Ré", "Ré #", "Mi", "Fa", "Fa #", "Sol", "Sol #", "La", "La #", "Si"}));
		tfScale.setSelectedIndex(0);
		textField = new JTextField();
		textField.setColumns(10);
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseButtonAction();
			}
		});
		btnBrowse.setFont(new Font("Verdana", Font.PLAIN, 11));
		
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
				.addGroup(gl_panelParameters.createSequentialGroup()
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
					.addComponent(btnStartReaction)
					.addGap(81))
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
					.addGap(27)
					.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		panelParameters.setLayout(gl_panelParameters);
		JPanel panelTitle = new JPanel();
		JLabel lblChemicalMusicGenerator = new JLabel("Chemical Music Generator");
		lblChemicalMusicGenerator.setForeground(new Color(105, 105, 105));
		lblChemicalMusicGenerator.setFont(new Font("Verdana", Font.PLAIN, 16));
		
		JLabel lblImageChemistry = new JLabel("");
		lblImageChemistry.setIcon(new ImageIcon(MainView.class.getResource("/img/chemistry.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 649, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(lblImageChemistry)
									.addGap(18)
									.addComponent(lblChemicalMusicGenerator))
								.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblImageChemistry)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(11)
									.addComponent(lblChemicalMusicGenerator)))
							.addGap(26)
							.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
	
	 public static void setBestLookAndFeelAvailable(){
		   String system_lf = UIManager.getSystemLookAndFeelClassName().toLowerCase();
		   if(system_lf.contains("metal")){
		       try {
		           UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		       }catch (Exception e) {}
		   }else{
		       try {
		           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		       }catch (Exception e) {}
		   }
		 }
	 
	 public void browseButtonAction(){
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("chemicalSong.mid"));
		if (fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
			saveFile = fc.getSelectedFile();
		}
		else
			saveFile = null;
	 }
	 
	 public void lblOpenFileAction(){
		 JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("chemicalSong.mid"));
		if (fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			loadFile = fc.getSelectedFile();
			// Create a sequencer for the sequence
	        try{
				sequencer = MidiSystem.getSequencer();
		        sequencer.open();
		        Sequence sequence = MidiSystem.getSequence(loadFile);
		        sequencer.setSequence(sequence);
		        lblPlayingfilename.setText(loadFile.getName());	
		        lblLog.setText("");
		        lblPlayAction();
	        }
	        catch(Exception e){
	        	lblLog.setText("Unable to load selected file");
	        	lblPlayingfilename.setText("");
	        }
		}
		lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open.png")));
	 }
	 
	 public void lblOpenFilePressed(){
		 lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open_pressed.png")));
	 }
	 
	 public void lblPlayAction(){
		 try {
	        // Start playing
	        sequencer.start();
	        lblLog.setText("");
	    } catch (Exception e){
	    	lblLog.setText("Unable to play selected file");
	    }			
		 lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play.png")));
	 }
	 
	 public void lblPlayPressed(){
		 lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play_pressed.png")));
	 }
	 
	 public void lblStopAction(){
		 sequencer.stop();
		 lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop.png")));
	 }
	 
	 public void lblStopPressed(){
		 lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop_pressed.png")));
	 }
	
	public static void main(String[] args){
		setBestLookAndFeelAvailable();
		MainView view = new MainView();	
		view.setVisible(true);
	}
}
