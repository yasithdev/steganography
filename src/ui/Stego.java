package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.FLVCrypto;
import core.IO;
import core.RC4;

public class Stego {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Stego window = new Stego();
					window.frameHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JFrame frameHome;
	private JTextField textFieldFilePath;
	private JTextField textFieldInputPath;
	private JTextField textFieldOutputPath;
	private JTextField txtDecryptPassword;
	private JTextField txtPassword;

	/**
	 * Create the application.
	 */
	public Stego() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameHome = new JFrame();
		frameHome.setTitle("Steganography");
		frameHome.setBounds(100, 100, 695, 366);
		frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frameHome.getContentPane().add(tabbedPane);

		JPanel PanelEmbed = new JPanel();
		tabbedPane.addTab("Embed", null, PanelEmbed, null);

		JPanel PanelEmbedStep2 = new JPanel();
		GridBagLayout gbl_PanelEmbedStep2 = new GridBagLayout();
		gbl_PanelEmbedStep2.columnWidths = new int[]{0, 0};
		gbl_PanelEmbedStep2.rowHeights = new int[]{50, 0, 29, 0, 0};
		gbl_PanelEmbedStep2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PanelEmbedStep2.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		PanelEmbedStep2.setLayout(gbl_PanelEmbedStep2);
				
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_panel_1.ipadx = 20;
				gbc_panel_1.insets = new Insets(5, 5, 20, 5);
				gbc_panel_1.gridx = 0;
				gbc_panel_1.gridy = 0;
				PanelEmbedStep2.add(panel_1, gbc_panel_1);
						GridBagLayout gbl_panel_1 = new GridBagLayout();
						gbl_panel_1.columnWidths = new int[]{164, 0};
						gbl_panel_1.rowHeights = new int[]{16, 0};
						gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
						gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
						panel_1.setLayout(gbl_panel_1);
						
								JLabel lblStep2Title = new JLabel("Step 2 : Select a FLV Video");
								lblStep2Title.setHorizontalAlignment(SwingConstants.CENTER);
								GridBagConstraints gbc_lblStep2Title = new GridBagConstraints();
								gbc_lblStep2Title.insets = new Insets(20, 20, 20, 20);
								gbc_lblStep2Title.gridx = 0;
								gbc_lblStep2Title.gridy = 0;
								panel_1.add(lblStep2Title, gbc_lblStep2Title);
								lblStep2Title.setLabelFor(PanelEmbedStep2);
								lblStep2Title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
				JButton btnInputPathBrowse = new JButton("Browse");
				btnInputPathBrowse.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						final JFileChooser fc = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter("FLV Files", "flv");
						fc.setFileFilter(filter);

						// Handle open button action.
						if (e.getSource() == btnInputPathBrowse) {
							int returnVal = fc.showOpenDialog(btnInputPathBrowse);

							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								textFieldInputPath.setText(file.getAbsolutePath());
							}
						}
					}
				});
				
						textFieldInputPath = new JTextField();
						textFieldInputPath.setHorizontalAlignment(SwingConstants.CENTER);
						textFieldInputPath.setColumns(1);
						textFieldInputPath.setText("/");
						GridBagConstraints gbc_textFieldInputPath = new GridBagConstraints();
						gbc_textFieldInputPath.fill = GridBagConstraints.HORIZONTAL;
						gbc_textFieldInputPath.insets = new Insets(5, 5, 5, 5);
						gbc_textFieldInputPath.gridx = 0;
						gbc_textFieldInputPath.gridy = 1;
						PanelEmbedStep2.add(textFieldInputPath, gbc_textFieldInputPath);
				btnInputPathBrowse.setAlignmentX(Component.CENTER_ALIGNMENT);
				GridBagConstraints gbc_btnInputPathBrowse = new GridBagConstraints();
				gbc_btnInputPathBrowse.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnInputPathBrowse.insets = new Insets(0, 0, 5, 0);
				gbc_btnInputPathBrowse.gridx = 0;
				gbc_btnInputPathBrowse.gridy = 2;
				PanelEmbedStep2.add(btnInputPathBrowse, gbc_btnInputPathBrowse);
		PanelEmbed.setLayout(new GridLayout(0, 3, 10, 5));

		JPanel PanelEmbedStep1 = new JPanel();
		GridBagLayout gbl_PanelEmbedStep1 = new GridBagLayout();
		gbl_PanelEmbedStep1.columnWeights = new double[]{1.0};
		gbl_PanelEmbedStep1.rowWeights = new double[]{0.0, 1.0};
		PanelEmbedStep1.setLayout(gbl_PanelEmbedStep1);
				
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.fill = GridBagConstraints.HORIZONTAL;
				gbc_panel.insets = new Insets(5, 5, 20, 5);
				gbc_panel.gridx = 0;
				gbc_panel.gridy = 0;
				PanelEmbedStep1.add(panel, gbc_panel);
						GridBagLayout gbl_panel = new GridBagLayout();
						gbl_panel.columnWidths = new int[]{183, 0};
						gbl_panel.rowHeights = new int[]{16, 0};
						gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
						gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
						panel.setLayout(gbl_panel);
						
								JLabel lblStep1Title = new JLabel("Step 1 : Enter Secret Message");
								GridBagConstraints gbc_lblStep1Title = new GridBagConstraints();
								gbc_lblStep1Title.insets = new Insets(20, 20, 20, 20);
								gbc_lblStep1Title.anchor = GridBagConstraints.NORTHWEST;
								gbc_lblStep1Title.gridx = 0;
								gbc_lblStep1Title.gridy = 0;
								panel.add(lblStep1Title, gbc_lblStep1Title);
								lblStep1Title.setLabelFor(PanelEmbedStep1);
		
				JTextArea textAreaSecretMsg = new JTextArea();
				textAreaSecretMsg.setRows(5);
				textAreaSecretMsg.setLineWrap(true);
				GridBagConstraints gbc_textAreaSecretMsg = new GridBagConstraints();
				gbc_textAreaSecretMsg.insets = new Insets(10, 10, 10, 10);
				gbc_textAreaSecretMsg.fill = GridBagConstraints.BOTH;
				gbc_textAreaSecretMsg.gridx = 0;
				gbc_textAreaSecretMsg.gridy = 1;
				PanelEmbedStep1.add(textAreaSecretMsg, gbc_textAreaSecretMsg);
		PanelEmbed.add(PanelEmbedStep1);
		PanelEmbed.add(PanelEmbedStep2);

		JPanel PanelEmbedStep3 = new JPanel();
		GridBagLayout gbl_PanelEmbedStep3 = new GridBagLayout();
		gbl_PanelEmbedStep3.columnWidths = new int[]{0, 0};
		gbl_PanelEmbedStep3.rowHeights = new int[]{16, 16, 26, 29, 0, 26, 29, 0};
		gbl_PanelEmbedStep3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PanelEmbedStep3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		PanelEmbedStep3.setLayout(gbl_PanelEmbedStep3);
		
				JButton btnEmbed = new JButton("Embed");
				btnEmbed.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Path inputFilePath = Paths.get(textFieldInputPath.getText().trim());
							Path outputFilePath = Paths.get(textFieldOutputPath.getText().trim());
							String message = textAreaSecretMsg.getText().trim();
							String key = txtPassword.getText().trim();
							RC4 rc4 = new RC4(key.getBytes());
							FLVCrypto flvcrypto = new FLVCrypto();

							byte[] inputFileBytes = IO.readFileBytes(inputFilePath);
							byte[] encryptedMsgBytes = rc4.encrypt(message.getBytes());
							byte[] outputFileBytes = flvcrypto.embed(inputFileBytes, encryptedMsgBytes);
							IO.writeFileBytes(outputFilePath, outputFileBytes);
							JOptionPane.showMessageDialog(frameHome, "Message embedded successfully");

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(frameHome, "An error occured. " + e1.getMessage());
							e1.printStackTrace();
						}
					}
				});
						
						JPanel panel_2 = new JPanel();
						panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
						GridBagConstraints gbc_panel_2 = new GridBagConstraints();
						gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
						gbc_panel_2.insets = new Insets(5, 5, 20, 5);
						gbc_panel_2.gridx = 0;
						gbc_panel_2.gridy = 0;
						PanelEmbedStep3.add(panel_2, gbc_panel_2);
						GridBagLayout gbl_panel_2 = new GridBagLayout();
						gbl_panel_2.columnWidths = new int[]{0, 0};
						gbl_panel_2.rowHeights = new int[]{16, 0};
						gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
						gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
						panel_2.setLayout(gbl_panel_2);
				
						JLabel lblStep3Title = new JLabel("Step 3: Embed Message");
						GridBagConstraints gbc_lblStep3Title = new GridBagConstraints();
						gbc_lblStep3Title.insets = new Insets(20, 20, 20, 20);
						gbc_lblStep3Title.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblStep3Title.gridx = 0;
						gbc_lblStep3Title.gridy = 0;
						panel_2.add(lblStep3Title, gbc_lblStep3Title);
						lblStep3Title.setLabelFor(PanelEmbedStep3);
						lblStep3Title.setAlignmentX(Component.CENTER_ALIGNMENT);
				
						JLabel lblOutputFolder = new JLabel("Output Folder :");
						lblOutputFolder.setAlignmentX(Component.RIGHT_ALIGNMENT);
						GridBagConstraints gbc_lblOutputFolder = new GridBagConstraints();
						gbc_lblOutputFolder.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblOutputFolder.anchor = GridBagConstraints.NORTH;
						gbc_lblOutputFolder.insets = new Insets(5, 5, 5, 5);
						gbc_lblOutputFolder.gridx = 0;
						gbc_lblOutputFolder.gridy = 1;
						PanelEmbedStep3.add(lblOutputFolder, gbc_lblOutputFolder);
				
						JButton btnOutputPathBrowse = new JButton("Browse");
						btnOutputPathBrowse.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								final JFileChooser fc = new JFileChooser();
								FileNameExtensionFilter filter = new FileNameExtensionFilter("FLV Files", "flv");
								fc.setFileFilter(filter);

								// Handle open button action.
								if (e.getSource() == btnOutputPathBrowse) {
									int returnVal = fc.showSaveDialog(btnOutputPathBrowse);

									if (returnVal == JFileChooser.APPROVE_OPTION) {
										File file = fc.getSelectedFile();
										textFieldOutputPath.setText(file.getAbsolutePath());
									}
								}
							}
						});
						lblOutputFolder.setLabelFor(textFieldOutputPath);
						
								textFieldOutputPath = new JTextField();
								textFieldOutputPath.setText("/");
								GridBagConstraints gbc_textFieldOutputPath = new GridBagConstraints();
								gbc_textFieldOutputPath.fill = GridBagConstraints.HORIZONTAL;
								gbc_textFieldOutputPath.anchor = GridBagConstraints.NORTH;
								gbc_textFieldOutputPath.insets = new Insets(0, 0, 5, 0);
								gbc_textFieldOutputPath.gridx = 0;
								gbc_textFieldOutputPath.gridy = 2;
								PanelEmbedStep3.add(textFieldOutputPath, gbc_textFieldOutputPath);
								textFieldOutputPath.setColumns(10);
						GridBagConstraints gbc_btnOutputPathBrowse = new GridBagConstraints();
						gbc_btnOutputPathBrowse.fill = GridBagConstraints.HORIZONTAL;
						gbc_btnOutputPathBrowse.anchor = GridBagConstraints.NORTH;
						gbc_btnOutputPathBrowse.insets = new Insets(0, 0, 5, 0);
						gbc_btnOutputPathBrowse.gridx = 0;
						gbc_btnOutputPathBrowse.gridy = 3;
						PanelEmbedStep3.add(btnOutputPathBrowse, gbc_btnOutputPathBrowse);
						
								JLabel lblPassword = new JLabel("Password : ");
								GridBagConstraints gbc_lblPassword = new GridBagConstraints();
								gbc_lblPassword.fill = GridBagConstraints.HORIZONTAL;
								gbc_lblPassword.insets = new Insets(20, 0, 5, 0);
								gbc_lblPassword.gridx = 0;
								gbc_lblPassword.gridy = 4;
								PanelEmbedStep3.add(lblPassword, gbc_lblPassword);
						lblPassword.setLabelFor(txtPassword);
						
								txtPassword = new JTextField();
								GridBagConstraints gbc_txtPassword = new GridBagConstraints();
								gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
								gbc_txtPassword.anchor = GridBagConstraints.NORTH;
								gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
								gbc_txtPassword.gridx = 0;
								gbc_txtPassword.gridy = 5;
								PanelEmbedStep3.add(txtPassword, gbc_txtPassword);
								txtPassword.setColumns(10);
				GridBagConstraints gbc_btnEmbed = new GridBagConstraints();
				gbc_btnEmbed.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnEmbed.anchor = GridBagConstraints.NORTH;
				gbc_btnEmbed.gridx = 0;
				gbc_btnEmbed.gridy = 6;
				PanelEmbedStep3.add(btnEmbed, gbc_btnEmbed);
				btnEmbed.setAlignmentX(Component.CENTER_ALIGNMENT);
		PanelEmbed.add(PanelEmbedStep3);

		JPanel PanelExtract = new JPanel();
		tabbedPane.addTab("Extract", null, PanelExtract, null);
		PanelExtract.setLayout(new GridLayout(1, 2, 10, 10));

		JPanel PanelExtractStep1 = new JPanel();
		PanelExtract.add(PanelExtractStep1);
		GridBagLayout gbl_PanelExtractStep1 = new GridBagLayout();
		gbl_PanelExtractStep1.columnWidths = new int[]{0, 0};
		gbl_PanelExtractStep1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_PanelExtractStep1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PanelExtractStep1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		PanelExtractStep1.setLayout(gbl_PanelExtractStep1);
						
						JPanel panel_3 = new JPanel();
						panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
						GridBagConstraints gbc_panel_3 = new GridBagConstraints();
						gbc_panel_3.fill = GridBagConstraints.BOTH;
						gbc_panel_3.insets = new Insets(5, 5, 20, 5);
						gbc_panel_3.gridx = 0;
						gbc_panel_3.gridy = 0;
						PanelExtractStep1.add(panel_3, gbc_panel_3);
						GridBagLayout gbl_panel_3 = new GridBagLayout();
						gbl_panel_3.columnWidths = new int[]{332, 0};
						gbl_panel_3.rowHeights = new int[]{0, 0};
						gbl_panel_3.columnWeights = new double[]{0.0, Double.MIN_VALUE};
						gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
						panel_3.setLayout(gbl_panel_3);
				
						JLabel lblStep = new JLabel("Step 1 : Select FLV File");
						GridBagConstraints gbc_lblStep = new GridBagConstraints();
						gbc_lblStep.insets = new Insets(20, 20, 20, 20);
						gbc_lblStep.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblStep.gridx = 0;
						gbc_lblStep.gridy = 0;
						panel_3.add(lblStep, gbc_lblStep);
						lblStep.setLabelFor(PanelExtractStep1);
		
				textFieldFilePath = new JTextField();
				GridBagConstraints gbc_textFieldFilePath = new GridBagConstraints();
				gbc_textFieldFilePath.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldFilePath.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldFilePath.gridx = 0;
				gbc_textFieldFilePath.gridy = 1;
				PanelExtractStep1.add(textFieldFilePath, gbc_textFieldFilePath);
				textFieldFilePath.setText("/");
				textFieldFilePath.setColumns(1);
		
				JButton btnFilePathBrowse = new JButton("Browse");
				btnFilePathBrowse.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						final JFileChooser fc = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter("FLV Files", "flv");
						fc.setFileFilter(filter);

						// Handle open button action.
						if (e.getSource() == btnFilePathBrowse) {
							int returnVal = fc.showOpenDialog(btnFilePathBrowse);

							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								textFieldFilePath.setText(file.getAbsolutePath());
							}
						}
					}
				});
				GridBagConstraints gbc_btnFilePathBrowse = new GridBagConstraints();
				gbc_btnFilePathBrowse.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnFilePathBrowse.gridx = 0;
				gbc_btnFilePathBrowse.gridy = 2;
				PanelExtractStep1.add(btnFilePathBrowse, gbc_btnFilePathBrowse);
				btnFilePathBrowse.setAlignmentX(0.5f);

		JPanel PanelExtractStep2 = new JPanel();
		PanelExtract.add(PanelExtractStep2);
		GridBagLayout gbl_PanelExtractStep2 = new GridBagLayout();
		gbl_PanelExtractStep2.columnWidths = new int[]{0, 0};
		gbl_PanelExtractStep2.rowHeights = new int[]{16, 0, 0, 0, 0, 0};
		gbl_PanelExtractStep2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PanelExtractStep2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		PanelExtractStep2.setLayout(gbl_PanelExtractStep2);
										
										JPanel panel_4 = new JPanel();
										panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
										GridBagConstraints gbc_panel_4 = new GridBagConstraints();
										gbc_panel_4.fill = GridBagConstraints.BOTH;
										gbc_panel_4.insets = new Insets(5, 5, 20, 0);
										gbc_panel_4.gridx = 0;
										gbc_panel_4.gridy = 0;
										PanelExtractStep2.add(panel_4, gbc_panel_4);
										GridBagLayout gbl_panel_4 = new GridBagLayout();
										gbl_panel_4.columnWidths = new int[]{130, 0};
										gbl_panel_4.rowHeights = new int[]{16, 0};
										gbl_panel_4.columnWeights = new double[]{0.0, Double.MIN_VALUE};
										gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
										panel_4.setLayout(gbl_panel_4);
								
										JLabel lblStepEnter = new JLabel("Step 2: Extract Message");
										GridBagConstraints gbc_lblStepEnter = new GridBagConstraints();
										gbc_lblStepEnter.insets = new Insets(20, 20, 20, 20);
										gbc_lblStepEnter.anchor = GridBagConstraints.NORTHEAST;
										gbc_lblStepEnter.gridx = 0;
										gbc_lblStepEnter.gridy = 0;
										panel_4.add(lblStepEnter, gbc_lblStepEnter);
										lblStepEnter.setLabelFor(PanelExtractStep2);
						
								JLabel lblDecryptPassword = new JLabel("Password : ");
								GridBagConstraints gbc_lblDecryptPassword = new GridBagConstraints();
								gbc_lblDecryptPassword.fill = GridBagConstraints.HORIZONTAL;
								gbc_lblDecryptPassword.insets = new Insets(0, 0, 5, 0);
								gbc_lblDecryptPassword.gridx = 0;
								gbc_lblDecryptPassword.gridy = 1;
								PanelExtractStep2.add(lblDecryptPassword, gbc_lblDecryptPassword);
						lblDecryptPassword.setLabelFor(txtDecryptPassword);
						
								txtDecryptPassword = new JTextField();
								GridBagConstraints gbc_txtDecryptPassword = new GridBagConstraints();
								gbc_txtDecryptPassword.fill = GridBagConstraints.HORIZONTAL;
								gbc_txtDecryptPassword.insets = new Insets(5, 5, 5, 0);
								gbc_txtDecryptPassword.gridx = 0;
								gbc_txtDecryptPassword.gridy = 2;
								PanelExtractStep2.add(txtDecryptPassword, gbc_txtDecryptPassword);
								txtDecryptPassword.setColumns(10);
								
								JTextArea textAreaOutput = new JTextArea();
								textAreaOutput.setLineWrap(true);
								textAreaOutput.setRows(3);
								GridBagConstraints gbc_textAreaOutput = new GridBagConstraints();
								gbc_textAreaOutput.fill = GridBagConstraints.BOTH;
								gbc_textAreaOutput.insets = new Insets(5, 5, 0, 0);
								gbc_textAreaOutput.gridx = 0;
								gbc_textAreaOutput.gridy = 4;
								PanelExtractStep2.add(textAreaOutput, gbc_textAreaOutput);
						
								JButton btnExtractMessage = new JButton("Extract Message");
								btnExtractMessage.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										Path filePath = Paths.get(textFieldFilePath.getText().trim());
										String key = txtDecryptPassword.getText().trim();
										RC4 rc4 = new RC4(key.getBytes());
										FLVCrypto flvcrypto = new FLVCrypto();

										byte[] inputFileBytes;
										try {
											inputFileBytes = IO.readFileBytes(filePath);
											byte[] encryptedBytes = flvcrypto.extract(inputFileBytes);
											String message = new String(rc4.decrypt(encryptedBytes));
											textAreaOutput.setText(message);
										} catch (Exception ex) {
											// TODO Auto-generated catch block
											JOptionPane.showMessageDialog(frameHome, "An error has occured. " + ex.getMessage());
											ex.printStackTrace();
										}
									}
								});
								GridBagConstraints gbc_btnExtractMessage = new GridBagConstraints();
								gbc_btnExtractMessage.insets = new Insets(0, 0, 5, 0);
								gbc_btnExtractMessage.fill = GridBagConstraints.HORIZONTAL;
								gbc_btnExtractMessage.gridx = 0;
								gbc_btnExtractMessage.gridy = 3;
								PanelExtractStep2.add(btnExtractMessage, gbc_btnExtractMessage);
	}
}
