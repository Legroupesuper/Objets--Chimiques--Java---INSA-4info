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
	private JComboBox tfScale;
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
		tfScale = new JComboBox();
		tfScale.setMaximumRowCount(12);
		tfScale.setModel(new DefaultComboBoxModel(new String[] {"Do", "Do #", "Ré", "Ré #", "Mi", "Fa", "Fa #", "Sol", "Sol #", "La", "La #", "Si"}));
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
		
		JComboBox comboBoxLead = new JComboBox();
		comboBoxLead.setMaximumRowCount(15);
		comboBoxLead.setModel(new DefaultComboBoxModel(new String[] {"O Acoustic Grand Piano", "1 Bright Acoustic Piano", "2 Electric grand Piano", "3 Honky Tonk Piano", "4 Eiectric Piano 1", "5 Electric Piano 2", "6 Harpsichord", "7 Clavinet", "8 Celesra", "9 Glockenspiel", "10 Music Box", "11 Vibraphone", "12 Marimba", "13 Xylophone", "14 Tubular bells", "15 Dulcimer", "16 Drawbar Organ", "17 Percussive Organ", "18 Rock Organ", "19 Church Organ", "20 Reed Organ", "21 Accordion", "22 Harmonica", "23 Tango Accordion", "24 Nylon Accustic Guitar", "25 Steel Acoustic Guitar", "26 Jazz Electric Guitar", "27 Ciean Electric Guitar", "28 Muted Electric Guitar", "29 Overdrive Guitar", "30 Distorted Guitar", "31 Guitar Harmonics", "32 Acoustic Bass", "33 Electric Fingered Bass", "34 Electric Picked Bass", "35 Fretless Bass", "36 Slap Bass 1", "37 Slap Bass 2", "38 Syn Bass 1", "39 Syn Bass 2", "40 Violin", "41 Viola", "42 Cello", "43 Contrabass", "44 Tremolo Strings", "45 Pizzicato Strings", "46 Orchestral Harp", "47 Timpani", "48 String Ensemble 1", "49 String Ensemble 2 (Slow)", "50 Syn Strings 1", "51 Syn Strings 2", "52 Choir Aahs", "53 Voice Oohs", "54 Syn Choir", "55 Orchestral Hit", "56 Trumpet", "57 Trombone", "58 Tuba", "59 Muted Trumpet", "60 French Horn", "61 Brass Section", "62 Syn Brass 1", "62 Syn Brass 2", "64 Soprano Sax", "65 Alto Sax", "66 Tenor Sax", "67 Baritone Sax", "68 Oboe", "69 English Horn", "7O Bassoon", "71 Clarinet", "72 Piccolo", "73 Flute", "74 Recorder", "75 Pan Flute", "76 Bottle Blow", "77 Shakuhachi", "78 Whistle", "79 Ocarina", "80 Syn Square Wave", "81 Syn Sawtooth Wave", "82 Syn Calliope", "83 Syn Chiff", "84 Syn Charang", "85 Syn Voice", "86 Syn Fifths Sawtooth Wave", "87 Syn Brass & Lead", "88 New Age Syn Pad", "89 Warm Syn Pad", "90 Polysynth Syn Pad", "91 Choir Syn Pad", "92 Bowed Syn Pad", "93 Metal Syn Pad", "94 Halo Syn Pad", "95 Sweep Syn Pad", "96 SFX Rain", "97 SFX Soundtrack", "98 SFX Crystal", "99 SFX Atmosphere", "100 SFX Brightness", "101 SFX Goblins", "102 SFX Echoes", "103 SFX Sci-fi", "104 Sitar", "105 Banjo", "106 Shamisen", "107 Koto", "108 Kalimba", "109 Bag Pipe", "110 Fiddle", "111 Shanai", "112 Tinkle Bell", "113 Agogo", "114 Steel Drums", "115 Woodblock", "116 Taiko Drum", "117 Melodic Tom", "118 Syn Drum", "119 Reverse Cymbal", "120 Guitar Fret Noise", "121 Breath Noise", "122 Seashore", "123 Bird Tweet", "125 Telephone Ring", "125 Helicopter", "126 Applause", "127 Gun Shot"}));
		comboBoxLead.setSelectedIndex(0);
		
		JLabel lblLead = new JLabel("Lead :");
		lblLead.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		JLabel lblAccompaniment = new JLabel("Accompaniment  :");
		lblAccompaniment.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		JComboBox comboBoxAccompaniment = new JComboBox();
		comboBoxAccompaniment.setModel(new DefaultComboBoxModel(new String[] {"O Acoustic Grand Piano", "1 Bright Acoustic Piano", "2 Electric grand Piano", "3 Honky Tonk Piano", "4 Eiectric Piano 1", "5 Electric Piano 2", "6 Harpsichord", "7 Clavinet", "8 Celesra", "9 Glockenspiel", "10 Music Box", "11 Vibraphone", "12 Marimba", "13 Xylophone", "14 Tubular bells", "15 Dulcimer", "16 Drawbar Organ", "17 Percussive Organ", "18 Rock Organ", "19 Church Organ", "20 Reed Organ", "21 Accordion", "22 Harmonica", "23 Tango Accordion", "24 Nylon Accustic Guitar", "25 Steel Acoustic Guitar", "26 Jazz Electric Guitar", "27 Ciean Electric Guitar", "28 Muted Electric Guitar", "29 Overdrive Guitar", "30 Distorted Guitar", "31 Guitar Harmonics", "32 Acoustic Bass", "33 Electric Fingered Bass", "34 Electric Picked Bass", "35 Fretless Bass", "36 Slap Bass 1", "37 Slap Bass 2", "38 Syn Bass 1", "39 Syn Bass 2", "40 Violin", "41 Viola", "42 Cello", "43 Contrabass", "44 Tremolo Strings", "45 Pizzicato Strings", "46 Orchestral Harp", "47 Timpani", "48 String Ensemble 1", "49 String Ensemble 2 (Slow)", "50 Syn Strings 1", "51 Syn Strings 2", "52 Choir Aahs", "53 Voice Oohs", "54 Syn Choir", "55 Orchestral Hit", "56 Trumpet", "57 Trombone", "58 Tuba", "59 Muted Trumpet", "60 French Horn", "61 Brass Section", "62 Syn Brass 1", "62 Syn Brass 2", "64 Soprano Sax", "65 Alto Sax", "66 Tenor Sax", "67 Baritone Sax", "68 Oboe", "69 English Horn", "7O Bassoon", "71 Clarinet", "72 Piccolo", "73 Flute", "74 Recorder", "75 Pan Flute", "76 Bottle Blow", "77 Shakuhachi", "78 Whistle", "79 Ocarina", "80 Syn Square Wave", "81 Syn Sawtooth Wave", "82 Syn Calliope", "83 Syn Chiff", "84 Syn Charang", "85 Syn Voice", "86 Syn Fifths Sawtooth Wave", "87 Syn Brass & Lead", "88 New Age Syn Pad", "89 Warm Syn Pad", "90 Polysynth Syn Pad", "91 Choir Syn Pad", "92 Bowed Syn Pad", "93 Metal Syn Pad", "94 Halo Syn Pad", "95 Sweep Syn Pad", "96 SFX Rain", "97 SFX Soundtrack", "98 SFX Crystal", "99 SFX Atmosphere", "100 SFX Brightness", "101 SFX Goblins", "102 SFX Echoes", "103 SFX Sci-fi", "104 Sitar", "105 Banjo", "106 Shamisen", "107 Koto", "108 Kalimba", "109 Bag Pipe", "110 Fiddle", "111 Shanai", "112 Tinkle Bell", "113 Agogo", "114 Steel Drums", "115 Woodblock", "116 Taiko Drum", "117 Melodic Tom", "118 Syn Drum", "119 Reverse Cymbal", "120 Guitar Fret Noise", "121 Breath Noise", "122 Seashore", "123 Bird Tweet", "125 Telephone Ring", "125 Helicopter", "126 Applause", "127 Gun Shot"}));
		comboBoxAccompaniment.setSelectedIndex(0);
		comboBoxAccompaniment.setMaximumRowCount(15);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
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
					.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblImageChemistry)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(11)
									.addComponent(lblChemicalMusicGenerator)))
							.addGap(26)
							.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnAbout))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		GroupLayout gl_panelParameters = new GroupLayout(panelParameters);
		gl_panelParameters.setHorizontalGroup(
			gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(19)
					.addComponent(lblImageMusic)
					.addGap(57)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTempo)
						.addComponent(lblBarNumber)
						.addComponent(lblScale))
					.addGap(41)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(tfTempo, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfBarNumber, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfScale, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(19)
					.addComponent(lblLead)
					.addGap(85)
					.addComponent(comboBoxLead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(19)
					.addComponent(lblAccompaniment, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(comboBoxAccompaniment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(19)
					.addComponent(lblImageFolder)
					.addGap(10)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMidiOutput)
						.addComponent(tfMidiOutput, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(btnBrowse))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(100)
					.addComponent(btnStartReaction))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(63)
					.addComponent(lblLogParameters, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGap(67)
					.addComponent(lblImageParameters)
					.addGap(18)
					.addComponent(lblParameters))
		);
		gl_panelParameters.setVerticalGroup(
			gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImageParameters)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(22)
							.addComponent(lblParameters)))
					.addGap(18)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(14)
							.addComponent(lblImageMusic))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(2)
							.addComponent(lblTempo)
							.addGap(21)
							.addComponent(lblBarNumber)
							.addGap(23)
							.addComponent(lblScale))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(tfTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(tfBarNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(tfScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(27)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(3)
							.addComponent(lblLead))
						.addComponent(comboBoxLead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(5)
							.addComponent(lblAccompaniment))
						.addComponent(comboBoxAccompaniment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImageFolder)
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addComponent(lblMidiOutput)
							.addGap(14)
							.addComponent(tfMidiOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelParameters.createSequentialGroup()
							.addGap(27)
							.addComponent(btnBrowse)))
					.addGap(18)
					.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(84)
					.addComponent(lblLogParameters, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE))
		);
		panelParameters.setLayout(gl_panelParameters);
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
