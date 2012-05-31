/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.viewV2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.chemicalmozart.utils.MusicWriter;
import org.chemicalmozart.utils.MusicWriter.NoteValues;
import org.chemicalmozart.utils.SolutionWriterRR;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

/**
 * This class describes the main graphical user interface for the chemical music generator
 *
 */

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * The main function, to launch this application
	 * @param args not used
	 */
	public static void main(String[] args){
		setBestLookAndFeelAvailable();
		MainView view = new MainView();
		view.setVisible(true);
	}
	/**
	 * This fonction tries to adapt the LookAndFeel to the OS, for a better look
	 */
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
	private JAnimatedIcon animatedIcon;

	private JComboBox comboBoxAccompaniment;
	private JComboBox comboBoxLead;
	private JLabel lblLogInfos;
	private JLabel lblLogParameters;
	private JLabel lblOpenFile;
	private JLabel lblPlay;
	private JLabel lblPlayingfilename;
	private JLabel lblStop;
	/**
	 * The current loaded file for Now Playing music
	 */
	private File loadFile;
	/**
	 * The file that will be used to write the midi generated song
	 */
	private File saveFile;
	/**
	 * The Midi sequencer used to play sound
	 */
	private Sequencer sequencer;
	private JTextField tfBarNumber;
	private JTextField tfMidiOutput;

	private JComboBox tfScale;

	private JTextField tfTempo;

	/**
	 * Constructs the view based on a JFrame
	 */
	public MainView() {
		this.setTitle("Chemical Music Generator");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/img/chemistry_mini.png")));
		try {
			this.sequencer = MidiSystem.getSequencer();
			this.sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		this.loadFile = null;
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.buildComponents();
		this.saveFile = new File(this.tfMidiOutput.getText());
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Asks the user for the location of the midi file that will be generated
	 */
	public void browseButtonAction(){
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("chemicalSong.mid"));
		if (fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
			this.saveFile = fc.getSelectedFile();
			this.tfMidiOutput.setText(this.saveFile.getAbsolutePath());
		}
		else{
			this.saveFile = null;
			this.tfMidiOutput.setText("");
		}
	}

	/**
	 * Opens a new dialog containing informations about this application
	 */
	public void btnAboutAction(){
		JDialog about = new About();
		about.setLocationRelativeTo(null);
		about.setVisible(true);
	}

	/**
	 * Starts the reaction using specified parameters
	 */
	public void btnStartReactionAction(){

		this.animatedIcon.setImages(this.animatedIcon.readGifImages("src/main/java/img/minimozart_anim.gif"));
		this.animatedIcon.setPause(false);
		if (this.checkParameters()){
			this.lblStopAction();
			final int tempo = Integer.parseInt(this.tfTempo.getText());
			int barNumber = Integer.parseInt(this.tfBarNumber.getText());
			final ModelBuilder builder = new ModelBuilder(barNumber);

			builder.getMainSol().addInertEventListener(new InertEventListener() {

				public void isInert(InertEvent e) {
					Solution s = (Solution) e.getSource();
					try {
						final MusicWriter writer = new MusicWriter(tempo, MainView.this.indexToNoteValues(MainView.this.tfScale.getSelectedIndex()), MainView.this.saveFile.getAbsolutePath(), MainView.this.comboBoxLead.getSelectedIndex(), MainView.this.comboBoxAccompaniment.getSelectedIndex());
						s.add(writer);
						s.add(new SolutionWriterRR(MainView.this));
					} catch (ChemicalException e1) {
						e1.printStackTrace();
					} catch (InvalidMidiDataException e1) {
						e1.printStackTrace();
					}
				}
			});


			builder.launchReaction();
		}

	}

	/**
	 * Used to set up every tool in the main view (labels, comboboxes, textfields...).
	 * The main part of this fonction is automatically generated by Eclipse's WindowBuilder plugin.
	 */
	public void buildComponents(){
		JPanel panelInfos = new JPanel();
		panelInfos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel lblMidiFile = new JLabel("Midi File :");
		lblMidiFile.setFont(new Font("Verdana", Font.PLAIN, 11));

		this.lblPlayingfilename = new JLabel("");
		this.lblPlayingfilename.setFont(new Font("Verdana", Font.PLAIN, 11));

		this.lblPlay = new JLabel("");
		this.lblPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainView.this.lblPlayPressed();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				MainView.this.lblPlayAction();
			}
		});
		this.lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play.png")));

		this.lblStop = new JLabel("");
		this.lblStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainView.this.lblStopPressed();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				MainView.this.lblStopAction();
			}
		});
		this.lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop.png")));

		this.lblOpenFile = new JLabel("");
		this.lblOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MainView.this.lblOpenFilePressed();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				MainView.this.lblOpenFileAction();
			}
		});
		this.lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open.png")));

		JLabel lblNowPlaying = new JLabel("Now Playing");
		lblNowPlaying.setForeground(new Color(139, 0, 0));
		lblNowPlaying.setFont(new Font("Verdana", Font.PLAIN, 14));

		JLabel lblImageEqualizer = new JLabel("");
		lblImageEqualizer.setIcon(new ImageIcon(MainView.class.getResource("/img/equalizer.png")));

		this.lblLogInfos = new JLabel("");
		this.lblLogInfos.setForeground(new Color(139, 0, 0));

		this.animatedIcon = new JAnimatedIcon("src/main/java/img/minimozart_anim.gif",20);
		this.animatedIcon.setPause(true);
		GroupLayout gl_panelInfos = new GroupLayout(panelInfos);
		gl_panelInfos.setHorizontalGroup(
				gl_panelInfos.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelInfos.createSequentialGroup()
						.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelInfos.createSequentialGroup()
										.addGap(6)
										.addComponent(this.lblOpenFile)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelInfos.createSequentialGroup()
														.addComponent(lblMidiFile)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(this.lblPlayingfilename, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
														.addGroup(gl_panelInfos.createSequentialGroup()
																.addComponent(lblImageEqualizer)
																.addGap(30)
																.addComponent(lblNowPlaying))))
																.addGroup(gl_panelInfos.createSequentialGroup()
																		.addGap(77)
																		.addComponent(this.lblPlay)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(this.lblStop)))
																		.addContainerGap())
																		.addGroup(gl_panelInfos.createSequentialGroup()
																				.addContainerGap(94, Short.MAX_VALUE)
																				.addComponent(this.animatedIcon, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
																				.addGap(93))
																				.addGroup(gl_panelInfos.createSequentialGroup()
																						.addContainerGap(66, Short.MAX_VALUE)
																						.addComponent(this.lblLogInfos, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
																						.addContainerGap())
				);
		gl_panelInfos.setVerticalGroup(
				gl_panelInfos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfos.createSequentialGroup()
						.addGroup(gl_panelInfos.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelInfos.createSequentialGroup()
										.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
												.addComponent(lblImageEqualizer)
												.addGroup(gl_panelInfos.createSequentialGroup()
														.addGap(25)
														.addComponent(lblNowPlaying)))
														.addGap(60)
														.addGroup(gl_panelInfos.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblMidiFile)
																.addComponent(this.lblPlayingfilename, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
																.addGap(27)
																.addGroup(gl_panelInfos.createParallelGroup(Alignment.LEADING)
																		.addComponent(this.lblStop)
																		.addComponent(this.lblPlay))
																		.addGap(18))
																		.addGroup(gl_panelInfos.createSequentialGroup()
																				.addComponent(this.lblOpenFile)
																				.addGap(68)))
																				.addGap(18)
																				.addComponent(this.lblLogInfos, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
																				.addComponent(this.animatedIcon, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
																				.addGap(18))
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
				MainView.this.btnStartReactionAction();
			}
		});
		btnStartReaction.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnStartReaction.setHorizontalAlignment(SwingConstants.LEFT);
		btnStartReaction.setIcon(new ImageIcon(MainView.class.getResource("/img/rec_button.png")));
		this.tfTempo = new JTextField();
		this.tfTempo.setText("60");
		this.tfTempo.setColumns(10);
		this.tfBarNumber = new JTextField();
		this.tfBarNumber.setText("25");
		this.tfBarNumber.setColumns(10);
		this.tfScale = new JComboBox();
		this.tfScale.setMaximumRowCount(12);
		this.tfScale.setModel(new DefaultComboBoxModel(new String[] {"Do", "Do#", "Ré", "Ré#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si"}));
		this.tfScale.setSelectedIndex(0);
		this.tfMidiOutput = new JTextField();
		this.tfMidiOutput.setText("chemicalSong.mid");
		this.tfMidiOutput.setColumns(10);
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainView.this.browseButtonAction();
			}
		});
		btnBrowse.setFont(new Font("Verdana", Font.PLAIN, 11));

		JLabel lblImageParameters = new JLabel("");
		lblImageParameters.setIcon(new ImageIcon(MainView.class.getResource("/img/workflow.png")));

		JLabel lblImageMusic = new JLabel("");
		lblImageMusic.setIcon(new ImageIcon(MainView.class.getResource("/img/music.png")));

		JLabel lblImageFolder = new JLabel("");
		lblImageFolder.setIcon(new ImageIcon(MainView.class.getResource("/img/folder_full.png")));

		this.lblLogParameters = new JLabel("");
		this.lblLogParameters.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.lblLogParameters.setForeground(new Color(128, 0, 0));
		JLabel lblChemicalMusicGenerator = new JLabel("Chemical Music Generator");
		lblChemicalMusicGenerator.setForeground(new Color(105, 105, 105));
		lblChemicalMusicGenerator.setFont(new Font("Verdana", Font.PLAIN, 16));

		JLabel lblImageChemistry = new JLabel("");
		lblImageChemistry.setIcon(new ImageIcon(MainView.class.getResource("/img/chemistry.png")));

		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainView.this.btnAboutAction();
			}
		});
		btnAbout.setHorizontalAlignment(SwingConstants.LEFT);
		btnAbout.setIcon(new ImageIcon(MainView.class.getResource("/img/male_user_info.png")));

		this.comboBoxLead = new JComboBox();
		this.comboBoxLead.setMaximumRowCount(15);
		this.comboBoxLead.setModel(new DefaultComboBoxModel(new String[] {"O Acoustic Grand Piano", "1 Bright Acoustic Piano", "2 Electric grand Piano", "3 Honky Tonk Piano", "4 Eiectric Piano 1", "5 Electric Piano 2", "6 Harpsichord", "7 Clavinet", "8 Celesra", "9 Glockenspiel", "10 Music Box", "11 Vibraphone", "12 Marimba", "13 Xylophone", "14 Tubular bells", "15 Dulcimer", "16 Drawbar Organ", "17 Percussive Organ", "18 Rock Organ", "19 Church Organ", "20 Reed Organ", "21 Accordion", "22 Harmonica", "23 Tango Accordion", "24 Nylon Accustic Guitar", "25 Steel Acoustic Guitar", "26 Jazz Electric Guitar", "27 Ciean Electric Guitar", "28 Muted Electric Guitar", "29 Overdrive Guitar", "30 Distorted Guitar", "31 Guitar Harmonics", "32 Acoustic Bass", "33 Electric Fingered Bass", "34 Electric Picked Bass", "35 Fretless Bass", "36 Slap Bass 1", "37 Slap Bass 2", "38 Syn Bass 1", "39 Syn Bass 2", "40 Violin", "41 Viola", "42 Cello", "43 Contrabass", "44 Tremolo Strings", "45 Pizzicato Strings", "46 Orchestral Harp", "47 Timpani", "48 String Ensemble 1", "49 String Ensemble 2 (Slow)", "50 Syn Strings 1", "51 Syn Strings 2", "52 Choir Aahs", "53 Voice Oohs", "54 Syn Choir", "55 Orchestral Hit", "56 Trumpet", "57 Trombone", "58 Tuba", "59 Muted Trumpet", "60 French Horn", "61 Brass Section", "62 Syn Brass 1", "62 Syn Brass 2", "64 Soprano Sax", "65 Alto Sax", "66 Tenor Sax", "67 Baritone Sax", "68 Oboe", "69 English Horn", "7O Bassoon", "71 Clarinet", "72 Piccolo", "73 Flute", "74 Recorder", "75 Pan Flute", "76 Bottle Blow", "77 Shakuhachi", "78 Whistle", "79 Ocarina", "80 Syn Square Wave", "81 Syn Sawtooth Wave", "82 Syn Calliope", "83 Syn Chiff", "84 Syn Charang", "85 Syn Voice", "86 Syn Fifths Sawtooth Wave", "87 Syn Brass & Lead", "88 New Age Syn Pad", "89 Warm Syn Pad", "90 Polysynth Syn Pad", "91 Choir Syn Pad", "92 Bowed Syn Pad", "93 Metal Syn Pad", "94 Halo Syn Pad", "95 Sweep Syn Pad", "96 SFX Rain", "97 SFX Soundtrack", "98 SFX Crystal", "99 SFX Atmosphere", "100 SFX Brightness", "101 SFX Goblins", "102 SFX Echoes", "103 SFX Sci-fi", "104 Sitar", "105 Banjo", "106 Shamisen", "107 Koto", "108 Kalimba", "109 Bag Pipe", "110 Fiddle", "111 Shanai", "112 Tinkle Bell", "113 Agogo", "114 Steel Drums", "115 Woodblock", "116 Taiko Drum", "117 Melodic Tom", "118 Syn Drum", "119 Reverse Cymbal", "120 Guitar Fret Noise", "121 Breath Noise", "122 Seashore", "123 Bird Tweet", "125 Telephone Ring", "125 Helicopter", "126 Applause", "127 Gun Shot"}));
		this.comboBoxLead.setSelectedIndex(0);

		JLabel lblLead = new JLabel("Lead :");
		lblLead.setFont(new Font("Verdana", Font.PLAIN, 11));

		JLabel lblAccompaniment = new JLabel("Accompaniment  :");
		lblAccompaniment.setFont(new Font("Verdana", Font.PLAIN, 11));

		this.comboBoxAccompaniment = new JComboBox();
		this.comboBoxAccompaniment.setModel(new DefaultComboBoxModel(new String[] {"O Acoustic Grand Piano", "1 Bright Acoustic Piano", "2 Electric grand Piano", "3 Honky Tonk Piano", "4 Eiectric Piano 1", "5 Electric Piano 2", "6 Harpsichord", "7 Clavinet", "8 Celesra", "9 Glockenspiel", "10 Music Box", "11 Vibraphone", "12 Marimba", "13 Xylophone", "14 Tubular bells", "15 Dulcimer", "16 Drawbar Organ", "17 Percussive Organ", "18 Rock Organ", "19 Church Organ", "20 Reed Organ", "21 Accordion", "22 Harmonica", "23 Tango Accordion", "24 Nylon Accustic Guitar", "25 Steel Acoustic Guitar", "26 Jazz Electric Guitar", "27 Ciean Electric Guitar", "28 Muted Electric Guitar", "29 Overdrive Guitar", "30 Distorted Guitar", "31 Guitar Harmonics", "32 Acoustic Bass", "33 Electric Fingered Bass", "34 Electric Picked Bass", "35 Fretless Bass", "36 Slap Bass 1", "37 Slap Bass 2", "38 Syn Bass 1", "39 Syn Bass 2", "40 Violin", "41 Viola", "42 Cello", "43 Contrabass", "44 Tremolo Strings", "45 Pizzicato Strings", "46 Orchestral Harp", "47 Timpani", "48 String Ensemble 1", "49 String Ensemble 2 (Slow)", "50 Syn Strings 1", "51 Syn Strings 2", "52 Choir Aahs", "53 Voice Oohs", "54 Syn Choir", "55 Orchestral Hit", "56 Trumpet", "57 Trombone", "58 Tuba", "59 Muted Trumpet", "60 French Horn", "61 Brass Section", "62 Syn Brass 1", "62 Syn Brass 2", "64 Soprano Sax", "65 Alto Sax", "66 Tenor Sax", "67 Baritone Sax", "68 Oboe", "69 English Horn", "7O Bassoon", "71 Clarinet", "72 Piccolo", "73 Flute", "74 Recorder", "75 Pan Flute", "76 Bottle Blow", "77 Shakuhachi", "78 Whistle", "79 Ocarina", "80 Syn Square Wave", "81 Syn Sawtooth Wave", "82 Syn Calliope", "83 Syn Chiff", "84 Syn Charang", "85 Syn Voice", "86 Syn Fifths Sawtooth Wave", "87 Syn Brass & Lead", "88 New Age Syn Pad", "89 Warm Syn Pad", "90 Polysynth Syn Pad", "91 Choir Syn Pad", "92 Bowed Syn Pad", "93 Metal Syn Pad", "94 Halo Syn Pad", "95 Sweep Syn Pad", "96 SFX Rain", "97 SFX Soundtrack", "98 SFX Crystal", "99 SFX Atmosphere", "100 SFX Brightness", "101 SFX Goblins", "102 SFX Echoes", "103 SFX Sci-fi", "104 Sitar", "105 Banjo", "106 Shamisen", "107 Koto", "108 Kalimba", "109 Bag Pipe", "110 Fiddle", "111 Shanai", "112 Tinkle Bell", "113 Agogo", "114 Steel Drums", "115 Woodblock", "116 Taiko Drum", "117 Melodic Tom", "118 Syn Drum", "119 Reverse Cymbal", "120 Guitar Fret Noise", "121 Breath Noise", "122 Seashore", "123 Bird Tweet", "125 Telephone Ring", "125 Helicopter", "126 Applause", "127 Gun Shot"}));
		this.comboBoxAccompaniment.setSelectedIndex(0);
		this.comboBoxAccompaniment.setMaximumRowCount(15);
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
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
												.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addGap(20)
										.addComponent(panelParameters, GroupLayout.PREFERRED_SIZE, 535, Short.MAX_VALUE))
										.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
												.addGap(34)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblImageChemistry)
														.addGroup(groupLayout.createSequentialGroup()
																.addGap(11)
																.addComponent(lblChemicalMusicGenerator)))
																.addGap(26)
																.addComponent(panelInfos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addGap(6)
																.addComponent(btnAbout)))
																.addContainerGap())
				);
		GroupLayout gl_panelParameters = new GroupLayout(panelParameters);
		gl_panelParameters.setHorizontalGroup(
				gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
						.addContainerGap(67, Short.MAX_VALUE)
						.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelParameters.createSequentialGroup()
										.addComponent(lblImageParameters)
										.addGap(18)
										.addComponent(lblParameters))
										.addComponent(this.lblLogParameters, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panelParameters.createSequentialGroup()
												.addComponent(lblImageMusic)
												.addGap(57)
												.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
														.addComponent(lblTempo)
														.addComponent(lblBarNumber)
														.addComponent(lblScale))
														.addGap(41)
														.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
																.addComponent(this.tfTempo, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
																.addComponent(this.tfBarNumber, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
																.addComponent(this.tfScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
																.addGroup(gl_panelParameters.createSequentialGroup()
																		.addComponent(lblLead)
																		.addGap(85)
																		.addComponent(this.comboBoxLead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																		.addGroup(gl_panelParameters.createSequentialGroup()
																				.addComponent(lblAccompaniment, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
																				.addGap(6)
																				.addComponent(this.comboBoxAccompaniment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																				.addGroup(gl_panelParameters.createSequentialGroup()
																						.addComponent(lblImageFolder)
																						.addGap(10)
																						.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
																								.addComponent(lblMidiOutput)
																								.addComponent(this.tfMidiOutput, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
																								.addGap(6)
																								.addComponent(btnBrowse)))
																								.addGap(20))
																								.addGroup(gl_panelParameters.createSequentialGroup()
																										.addGap(145)
																										.addComponent(btnStartReaction)
																										.addContainerGap(158, Short.MAX_VALUE))
				);
		gl_panelParameters.setVerticalGroup(
				gl_panelParameters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameters.createSequentialGroup()
						.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
								.addComponent(lblImageParameters)
								.addGroup(gl_panelParameters.createSequentialGroup()
										.addGap(22)
										.addComponent(lblParameters)))
										.addGap(31)
										.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelParameters.createSequentialGroup()
														.addGap(14)
														.addComponent(lblImageMusic))
														.addGroup(gl_panelParameters.createSequentialGroup()
																.addGap(2)
																.addComponent(lblTempo))
																.addGroup(gl_panelParameters.createSequentialGroup()
																		.addComponent(this.tfTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
																				.addComponent(this.tfBarNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(lblBarNumber))
																				.addGap(18)
																				.addGroup(gl_panelParameters.createParallelGroup(Alignment.BASELINE)
																						.addComponent(this.tfScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																						.addComponent(lblScale))))
																						.addGap(36)
																						.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
																								.addGroup(gl_panelParameters.createSequentialGroup()
																										.addGap(3)
																										.addComponent(lblLead))
																										.addComponent(this.comboBoxLead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																										.addGap(11)
																										.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
																												.addGroup(gl_panelParameters.createSequentialGroup()
																														.addGap(5)
																														.addComponent(lblAccompaniment))
																														.addComponent(this.comboBoxAccompaniment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																														.addGap(38)
																														.addGroup(gl_panelParameters.createParallelGroup(Alignment.LEADING)
																																.addComponent(lblImageFolder)
																																.addGroup(gl_panelParameters.createSequentialGroup()
																																		.addComponent(lblMidiOutput)
																																		.addGap(14)
																																		.addComponent(this.tfMidiOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																		.addGroup(gl_panelParameters.createSequentialGroup()
																																				.addGap(27)
																																				.addComponent(btnBrowse)))
																																				.addGap(30)
																																				.addComponent(this.lblLogParameters, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
																																				.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
																																				.addComponent(btnStartReaction, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
				);
		panelParameters.setLayout(gl_panelParameters);
		this.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Checks if every parameter is correct
	 * @return true if the parameters are correct
	 */
	public boolean checkParameters(){
		//tempo
		try{
			Integer.parseInt(this.tfTempo.getText());
		}
		catch(NumberFormatException e){
			this.lblLogParameters.setText("Tempo field should be an integer");
			return false;
		}
		//barNumber
		try{
			Integer.parseInt(this.tfBarNumber.getText());
		}
		catch(NumberFormatException e){
			this.lblLogParameters.setText("Bar number field should be an integer");
			return false;
		}
		if (this.tfMidiOutput.getText().isEmpty()){
			this.lblLogParameters.setText("You should select a midi output file");
			return false;
		}
		this.lblLogParameters.setText("");
		return true;
	}

	/**
	 * Performs actions at the end of the reaction
	 * Plays the generated file and set animations
	 */
	public void endOfReaction() {
		this.animatedIcon.setPause(true);
		this.animatedIcon.setImages("src/main/java/img/minimozart_grill.gif");
		this.loadFile = this.saveFile;
		try{
			Sequence sequence = MidiSystem.getSequence(this.loadFile);
			this.sequencer.setSequence(sequence);
			this.lblPlayingfilename.setText(this.loadFile.getName());
			this.lblLogInfos.setText("");
			this.lblPlayAction();
		}
		catch(Exception ex){
			this.lblLogInfos.setText("Unable to load selected file");
			this.lblPlayingfilename.setText("");
		}

	}

	/**
	 * Converts an index to the corresponding Note
	 * @param n the integer index
	 * @return the noteValue corresponding to n
	 */
	private NoteValues indexToNoteValues(int n){
		switch(n){
		case 0:
			return NoteValues.DO;
		case 1:
			return NoteValues.DOD;
		case 2:
			return NoteValues.RE;
		case 3:
			return NoteValues.RED;
		case 4:
			return NoteValues.MI;
		case 5:
			return NoteValues.FA;
		case 6:
			return NoteValues.FAD;
		case 7:
			return NoteValues.SOL;
		case 8:
			return NoteValues.SOLD;
		case 9:
			return NoteValues.LA;
		case 10:
			return NoteValues.LAD;
		case 11:
			return NoteValues.SI;
		}
		return NoteValues.DO;
	}

	/**
	 * Asks the user for the location of the midi file he wants to play,
	 * and starts playing automatically if possible
	 */
	public void lblOpenFileAction(){
		this.sequencer.stop();
		//sequencer.close();
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("chemicalSong.mid"));
		if (fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			this.loadFile = fc.getSelectedFile();
			// Create a sequencer for the sequence
			try{
				Sequence sequence = MidiSystem.getSequence(this.loadFile);
				this.sequencer.setSequence(sequence);
				this.lblPlayingfilename.setText(this.loadFile.getName());
				this.lblLogInfos.setText("");
				this.lblPlayAction();
			}
			catch(Exception e){
				this.lblLogInfos.setText("Unable to load selected file");
				this.lblPlayingfilename.setText("");
			}
		}
		this.lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open.png")));
	}

	/**
	 * Changes the icon of the Open File label when pressed
	 */
	public void lblOpenFilePressed(){
		this.lblOpenFile.setIcon(new ImageIcon(MainView.class.getResource("/img/open_pressed.png")));
	}

	/**
	 * Starts playing the loaded midi file
	 */
	public void lblPlayAction(){
		try {
			// Start playing
			this.sequencer.start();
			this.lblLogInfos.setText("");
		} catch (Exception e){
			this.lblLogInfos.setText("Unable to play selected file");
		}
		this.lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play.png")));
	}

	/**
	 * Changes the icon of the Play label when pressed
	 */
	public void lblPlayPressed(){
		this.lblPlay.setIcon(new ImageIcon(MainView.class.getResource("/img/play_pressed.png")));
	}

	/**
	 * Stops (pause) the current playing sound
	 */
	public void lblStopAction(){
		this.sequencer.stop();
		this.lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop.png")));
	}

	/**
	 * Changes the icon of the Stop label when pressed
	 */
	public void lblStopPressed(){
		this.lblStop.setIcon(new ImageIcon(MainView.class.getResource("/img/stop_pressed.png")));
	}
}