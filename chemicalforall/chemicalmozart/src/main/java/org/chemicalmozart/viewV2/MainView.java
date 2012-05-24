package org.chemicalmozart.viewV2;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField tfTempo;
	private JTextField tfBarNumber;
	private JComboBox<String> tfScale;
	private JTextField tfMidiOutput;
	private File saveFile;
	private File loadFile;
	private JLabel lblLogInfos;
	private JLabel lblPlay;
	private JLabel lblOpenFile;
	JLabel lblStop;
	private Sequencer sequencer;
	JLabel lblPlayingfilename;
	JLabel lblLogParameters;
	
	public MainView() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		saveFile = null;
		loadFile = null;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildComponents();
		pack();	
		setLocationRelativeTo(null);
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
		
		lblLogInfos = new JLabel("");
		lblLogInfos.setForeground(new Color(139, 0, 0));
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
									.addComponent(lblNowPlaying))
								.addComponent(lblLogInfos, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelInfos.createSequentialGroup()
							.addGap(77)
							.addComponent(lblPlay)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStop)))
					.addContainerGap())
		);
		gl_panelInfos.setVerticalGroup(
			gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
					.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelInfos.createSequentialGroup()
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
							.addGap(18))
						.addGroup(Alignment.TRAILING, gl_panelInfos.createSequentialGroup()
							.addComponent(lblOpenFile)
							.addGap(68)))
					.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
					.addComponent(lblLogInfos, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
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
		btnStartReaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartReactionAction();
			}
		});
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
		tfMidiOutput = new JTextField();
		tfMidiOutput.setColumns(10);
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
		
		lblLogParameters = new JLabel("");
		lblLogParameters.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLogParameters.setForeground(new Color(128, 0, 0));
		GroupLayout gl_panelParameters = new GroupLayout(panelParameters);
		gl_panelParameters.setHorizontalGroup(
			gl_panelParameters.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addContainerGap(19, Short.MAX_VALUE)
					.addComponent(lblImageFolder)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMidiOutput))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(4)
							.addComponent(tfMidiOutput, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse)))
					.addContainerGap())
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addContainerGap(88, Short.MAX_VALUE)
					.addComponent(btnStartReaction)
					.addGap(77))
				.addGroup(Alignment.LEADING, gl_panelParameters.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblImageMusic)
							.addGap(27)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBarNumber)
								.addComponent(lblScale)
								.addComponent(lblTempo))
							.addGap(28)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfScale, 0, 0, Short.MAX_VALUE)
								.addComponent(tfBarNumber, 0, 0, Short.MAX_VALUE)
								.addComponent(tfTempo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblImageParameters)
							.addGap(35)
							.addComponent(lblParameters)))
					.addContainerGap(30, Short.MAX_VALUE))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(63)
					.addComponent(lblLogParameters, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
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
							.addGap(49)
							.addComponent(lblImageMusic))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(34)
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
					.addGap(28)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblMidiOutput)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfMidiOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowse)))
						.addComponent(lblImageFolder))
					.addGap(18)
					.addComponent(lblLogParameters, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		panelParameters.setLayout(gl_panelParameters);
		JPanel panelTitle = new JPanel();
		JLabel lblChemicalMusicGenerator = new JLabel("Chemical Music Generator");
		lblChemicalMusicGenerator.setForeground(new Color(105, 105, 105));
		lblChemicalMusicGenerator.setFont(new Font("Verdana", Font.PLAIN, 16));
		
		JLabel lblImageChemistry = new JLabel("");
		lblImageChemistry.setIcon(new ImageIcon(MainView.class.getResource("/img/chemistry.png")));
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAboutAction();
			}
		});
		btnAbout.setHorizontalAlignment(SwingConstants.LEFT);
		btnAbout.setIcon(new ImageIcon(MainView.class.getResource("/img/male_user_info.png")));
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
								.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(btnAbout)))
							.addGap(10)
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
							.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnAbout))
						.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)))
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
			tfMidiOutput.setText(saveFile.getAbsolutePath());
		}
		else{
			saveFile = null;
			tfMidiOutput.setText("");
		}
	 }
	 
	 public void lblOpenFileAction(){		 
		sequencer.stop();
		//sequencer.close();
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("chemicalSong.mid"));
		if (fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			loadFile = fc.getSelectedFile();
			// Create a sequencer for the sequence
	        try{
		        Sequence sequence = MidiSystem.getSequence(loadFile);
		        sequencer.setSequence(sequence);
		        lblPlayingfilename.setText(loadFile.getName());	
		        lblLogInfos.setText("");
		        lblPlayAction();
		        
	        }
	        catch(Exception e){
	        	lblLogInfos.setText("Unable to load selected file");
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
	        lblLogInfos.setText("");
	    } catch (Exception e){
	    	lblLogInfos.setText("Unable to play selected file");
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
	 
	 public void btnStartReactionAction(){
		 if (checkParameters()){
			 
		 }
	 }
	 
	 public boolean checkParameters(){
		 //tempo
		 try{
			 Integer.parseInt(tfTempo.getText());
		 }
		 catch(NumberFormatException e){
			 lblLogParameters.setText("Tempo field should be an integer");
			 return false;
		 }
		 //barNumber
		 try{
			 Integer.parseInt(tfBarNumber.getText());
		 }
		 catch(NumberFormatException e){
			 lblLogParameters.setText("Bar number field should be an integer");
			 return false;
		 }
		 if (tfMidiOutput.getText().isEmpty()){
			 lblLogParameters.setText("You should select a midi output file");
			 return false;
		 }
		 lblLogParameters.setText("");
		 return true;
	 }
	 
	 public void btnAboutAction(){
		  JDialog about = new About();
		  about.setLocationRelativeTo(null);
		  about.setVisible(true);
	 }
	
	public static void main(String[] args){
		setBestLookAndFeelAvailable();
		MainView view = new MainView();	
		view.setVisible(true);
	}
}
