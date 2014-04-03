package ua.nau.libstore.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;

import ua.nau.libstore.controller.DataController;
import ua.nau.libstore.controller.Manager;
import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.util.LanguageTypeEnum;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.text.AttributeSet;

@SuppressWarnings("serial")
public class AddLibraryView extends JDialog {

	private JPanel contentPane;
	private String filePath = "";
	private String imagePath = "";
	private JTextField txtTagList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddLibraryView frame = new AddLibraryView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddLibraryView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddLibraryView.class.getResource("/ua/nau/libstore/resource/icon/library_add_32.png")));
		setMinimumSize(new Dimension(400, 600));
		setPreferredSize(new Dimension(600, 1000));
		setTitle("Add new library");
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 544, 727);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 94, 0, 163, 106, 30, 0 };
		gbl_contentPane.rowHeights = new int[] { 40, 30, 65, 65, 37, 37, 37, 65, 70, 37, 37, 50,
				44, 20, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0,
				0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblName = new JLabel(Messages.getString("AddLibraryView.lblName.text", "Name:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		lblName.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JTextField txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 2;
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 3;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		txtName.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtName.setColumns(10);

		JLabel lblShortDescription = new JLabel(Messages.getString("AddLibraryView.lblShortDescription.text", "Short description:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblShortDescription = new GridBagConstraints();
		gbc_lblShortDescription.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblShortDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblShortDescription.gridx = 1;
		gbc_lblShortDescription.gridy = 2;
		contentPane.add(lblShortDescription, gbc_lblShortDescription);
		lblShortDescription.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JLabel lblLimitSD = new JLabel("150");
		GridBagConstraints gbc_lblLimitSD = new GridBagConstraints();
		gbc_lblLimitSD.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblLimitSD.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimitSD.gridx = 2;
		gbc_lblLimitSD.gridy = 2;
		contentPane.add(lblLimitSD, gbc_lblLimitSD);
		lblLimitSD.setForeground(Color.LIGHT_GRAY);
		lblLimitSD.setFont(new Font("Verdana", Font.PLAIN, 11));

		JScrollPane scrollPane1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane1 = new GridBagConstraints();
		gbc_scrollPane1.gridwidth = 2;
		gbc_scrollPane1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane1.gridx = 3;
		gbc_scrollPane1.gridy = 2;
		contentPane.add(scrollPane1, gbc_scrollPane1);
		scrollPane1.setAlignmentY(Component.TOP_ALIGNMENT);

		final JTextPane txtpnShortDescription = new JTextPane(new DefaultStyledDocument(){
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if ((getLength() + str.length()) < 145) {
		            super.insertString(offs, str, a);
		        }
		        else {
		            Toolkit.getDefaultToolkit().beep();
		        }
		    }
		});
		
		scrollPane1.setViewportView(txtpnShortDescription);
		txtpnShortDescription.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {				
				Integer count = 150 - txtpnShortDescription.getText().length();				
				lblLimitSD.setText(count.toString());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				removeUpdate(e);
			}
		});
		txtpnShortDescription.setFont(new Font("Verdana", Font.PLAIN, 12));

		JLabel lblFullDescription = new JLabel(Messages.getString("AddLibraryView.lblFullDescription.text", "Full description:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblFullDescription = new GridBagConstraints();
		gbc_lblFullDescription.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFullDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblFullDescription.gridx = 1;
		gbc_lblFullDescription.gridy = 3;
		contentPane.add(lblFullDescription, gbc_lblFullDescription);
		lblFullDescription.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JLabel lblLimitFD = new JLabel("1500");
		GridBagConstraints gbc_lblLimitFD = new GridBagConstraints();
		gbc_lblLimitFD.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblLimitFD.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimitFD.gridx = 2;
		gbc_lblLimitFD.gridy = 3;
		contentPane.add(lblLimitFD, gbc_lblLimitFD);
		lblLimitFD.setForeground(Color.LIGHT_GRAY);
		lblLimitFD.setFont(new Font("Verdana", Font.PLAIN, 11));

		JScrollPane scrollPane2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridwidth = 2;
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane2.gridx = 3;
		gbc_scrollPane2.gridy = 3;
		contentPane.add(scrollPane2, gbc_scrollPane2);

		final JTextPane txtpnFullDescription = new JTextPane(new DefaultStyledDocument(){
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if ((getLength() + str.length()) < 1450) {
		            super.insertString(offs, str, a);
		        }
		        else {
		            Toolkit.getDefaultToolkit().beep();
		        }
		    }
		});
		txtpnFullDescription.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				Integer count = 1500 - txtpnFullDescription.getText().length();
				lblLimitFD.setText(count.toString());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				removeUpdate(e);
			}
		});
		scrollPane2.setViewportView(txtpnFullDescription);
		txtpnFullDescription.setFont(new Font("Verdana", Font.PLAIN, 12));

		JLabel lblVersion = new JLabel(Messages.getString("AddLibraryView.lblVersion.text", "Version:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.WEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 4;
		contentPane.add(lblVersion, gbc_lblVersion);
		lblVersion.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JFormattedTextField frmtdtxtfldVersion = new JFormattedTextField(
				createNewFormatter("#.#"));
		GridBagConstraints gbc_frmtdtxtfldVersion = new GridBagConstraints();
		gbc_frmtdtxtfldVersion.fill = GridBagConstraints.HORIZONTAL;
		gbc_frmtdtxtfldVersion.insets = new Insets(0, 0, 5, 5);
		gbc_frmtdtxtfldVersion.gridx = 3;
		gbc_frmtdtxtfldVersion.gridy = 4;
		contentPane.add(frmtdtxtfldVersion, gbc_frmtdtxtfldVersion);
		frmtdtxtfldVersion.setFont(new Font("Verdana", Font.PLAIN, 12));
		frmtdtxtfldVersion.setText("1.0");

		JLabel lblLanguage = new JLabel(Messages.getString("AddLibraryView.lblLanguage.text", "Language:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.WEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 1;
		gbc_lblLanguage.gridy = 5;
		contentPane.add(lblLanguage, gbc_lblLanguage);
		lblLanguage.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JComboBox cbLanguage = new JComboBox();
		GridBagConstraints gbc_cbLanguage = new GridBagConstraints();
		gbc_cbLanguage.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_cbLanguage.gridx = 3;
		gbc_cbLanguage.gridy = 5;
		contentPane.add(cbLanguage, gbc_cbLanguage);
		cbLanguage.setModel(new DefaultComboBoxModel(LanguageTypeEnum.values()));
		cbLanguage.setFont(new Font("Verdana", Font.PLAIN, 12));

		JLabel lblTestQuality = new JLabel(Messages.getString("AddLibraryView.lblTestQuality.text", "Test quality:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblTestQuality = new GridBagConstraints();
		gbc_lblTestQuality.anchor = GridBagConstraints.WEST;
		gbc_lblTestQuality.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestQuality.gridx = 1;
		gbc_lblTestQuality.gridy = 6;
		contentPane.add(lblTestQuality, gbc_lblTestQuality);
		lblTestQuality.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JComboBox cbTestRaiting = new JComboBox();
		GridBagConstraints gbc_cbTestRaiting = new GridBagConstraints();
		gbc_cbTestRaiting.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTestRaiting.insets = new Insets(0, 0, 5, 5);
		gbc_cbTestRaiting.gridx = 3;
		gbc_cbTestRaiting.gridy = 6;
		contentPane.add(cbTestRaiting, gbc_cbTestRaiting);
		cbTestRaiting.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10" }));
		cbTestRaiting.setFont(new Font("Verdana", Font.PLAIN, 12));

		JLabel lblComment = new JLabel(Messages.getString("AddLibraryView.lblComment.text", "Comment:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblComment = new GridBagConstraints();
		gbc_lblComment.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblComment.insets = new Insets(0, 0, 5, 5);
		gbc_lblComment.gridx = 1;
		gbc_lblComment.gridy = 7;
		contentPane.add(lblComment, gbc_lblComment);
		lblComment.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JLabel lblLimitComment = new JLabel("2000");
		GridBagConstraints gbc_lblLimitComment = new GridBagConstraints();
		gbc_lblLimitComment.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblLimitComment.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimitComment.gridx = 2;
		gbc_lblLimitComment.gridy = 7;
		contentPane.add(lblLimitComment, gbc_lblLimitComment);
		lblLimitComment.setForeground(Color.LIGHT_GRAY);
		lblLimitComment.setFont(new Font("Verdana", Font.PLAIN, 11));

		JScrollPane scrollPane3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane3 = new GridBagConstraints();
		gbc_scrollPane3.gridwidth = 2;
		gbc_scrollPane3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane3.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane3.gridx = 3;
		gbc_scrollPane3.gridy = 7;
		contentPane.add(scrollPane3, gbc_scrollPane3);

		final JTextPane txtpnComment = new JTextPane(new DefaultStyledDocument(){
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if ((getLength() + str.length()) < 1900) {
		            super.insertString(offs, str, a);
		        }
		        else {
		            Toolkit.getDefaultToolkit().beep();
		        }
		    }
		});
		txtpnComment.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				Integer count = 2000 - txtpnComment.getText().length();
				lblLimitComment.setText(count.toString());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				removeUpdate(e);
			}
		});
		scrollPane3.setViewportView(txtpnComment);
		txtpnComment.setFont(new Font("Verdana", Font.PLAIN, 12));

		JLabel lblRequire = new JLabel(Messages.getString("AddLibraryView.lblRequire.text", "Require:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblRequire = new GridBagConstraints();
		gbc_lblRequire.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblRequire.insets = new Insets(0, 0, 5, 5);
		gbc_lblRequire.gridx = 1;
		gbc_lblRequire.gridy = 8;
		contentPane.add(lblRequire, gbc_lblRequire);
		lblRequire.setFont(new Font("Verdana", Font.PLAIN, 12));

		final JLabel lblLimitRequire = new JLabel("200");
		GridBagConstraints gbc_lblLimitRequire = new GridBagConstraints();
		gbc_lblLimitRequire.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblLimitRequire.insets = new Insets(0, 0, 5, 5);
		gbc_lblLimitRequire.gridx = 2;
		gbc_lblLimitRequire.gridy = 8;
		contentPane.add(lblLimitRequire, gbc_lblLimitRequire);
		lblLimitRequire.setForeground(Color.LIGHT_GRAY);
		lblLimitRequire.setFont(new Font("Verdana", Font.PLAIN, 11));

		JScrollPane scrollPane4 = new JScrollPane();
		GridBagConstraints gbc_scrollPane4 = new GridBagConstraints();
		gbc_scrollPane4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane4.gridwidth = 2;
		gbc_scrollPane4.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane4.gridx = 3;
		gbc_scrollPane4.gridy = 8;
		contentPane.add(scrollPane4, gbc_scrollPane4);

		final JTextPane txtpnRequire = new JTextPane(new DefaultStyledDocument(){
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if ((getLength() + str.length()) < 199) {
		            super.insertString(offs, str, a);
		        }
		        else {
		            Toolkit.getDefaultToolkit().beep();
		        }
		    }
		});
		txtpnRequire.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				Integer count = 200 - txtpnRequire.getText().length();
				lblLimitRequire.setText(count.toString());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				removeUpdate(e);
			}
		});
		txtpnRequire.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtpnRequire.setText("Nothing");
		scrollPane4.setViewportView(txtpnRequire);

		JLabel lblFiledirectoryPath = new JLabel(Messages.getString("AddLibraryView.lblFiledirectoryPath.text", "File\\directory path:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFiledirectoryPath.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_lblFiledirectoryPath = new GridBagConstraints();
		gbc_lblFiledirectoryPath.anchor = GridBagConstraints.WEST;
		gbc_lblFiledirectoryPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiledirectoryPath.gridx = 1;
		gbc_lblFiledirectoryPath.gridy = 9;
		contentPane.add(lblFiledirectoryPath, gbc_lblFiledirectoryPath);

		final JLabel lblAttachFileResult = new JLabel("");
		GridBagConstraints gbc_lblAttachFileResult = new GridBagConstraints();
		gbc_lblAttachFileResult.insets = new Insets(0, 0, 5, 5);
		gbc_lblAttachFileResult.gridx = 4;
		gbc_lblAttachFileResult.gridy = 9;
		contentPane.add(lblAttachFileResult, gbc_lblAttachFileResult);

		JButton btnAttachFile = new JButton(Messages.getString("AddLibraryView.btnAttachFile.text", "File")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAttachFile.setIcon(new ImageIcon(AddLibraryView.class.getResource("/ua/nau/libstore/resource/icon/attach.png")));
		btnAttachFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(filePath);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = chooser.showDialog(AddLibraryView.this, "Attach");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					filePath = file.getPath();
					lblAttachFileResult.setForeground(Color.green);
					lblAttachFileResult.setText("Attached");
				} else {
					lblAttachFileResult.setForeground(Color.red);
					lblAttachFileResult.setText("Mistake!");
				}
			}
		});
		btnAttachFile.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_btnAttachFile = new GridBagConstraints();
		gbc_btnAttachFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAttachFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnAttachFile.gridx = 3;
		gbc_btnAttachFile.gridy = 9;
		contentPane.add(btnAttachFile, gbc_btnAttachFile);

		JLabel lblImagePath = new JLabel(Messages.getString("AddLibraryView.lblImagePath.text", "Image path:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblImagePath.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_lblImagePath = new GridBagConstraints();
		gbc_lblImagePath.anchor = GridBagConstraints.WEST;
		gbc_lblImagePath.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagePath.gridx = 1;
		gbc_lblImagePath.gridy = 10;
		contentPane.add(lblImagePath, gbc_lblImagePath);

		final JLabel lblAttachImageResult = new JLabel((String) null);
		GridBagConstraints gbc_lblAttachImageResult = new GridBagConstraints();
		gbc_lblAttachImageResult.insets = new Insets(0, 0, 5, 5);
		gbc_lblAttachImageResult.gridx = 4;
		gbc_lblAttachImageResult.gridy = 10;
		contentPane.add(lblAttachImageResult, gbc_lblAttachImageResult);

		JButton btnAttachImage = new JButton(Messages.getString("AddLibraryView.btnAttachImage.text", "Image")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAttachImage.setIcon(new ImageIcon(AddLibraryView.class.getResource("/ua/nau/libstore/resource/icon/attach.png")));
		btnAttachImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(imagePath);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showDialog(AddLibraryView.this, "Attach");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					imagePath = file.getPath();
					lblAttachImageResult.setForeground(Color.green);
					lblAttachImageResult.setText("Attached");
				} else {
					lblAttachImageResult.setForeground(Color.red);
					lblAttachImageResult.setText("Mistake!");
				}
			}
		});
		btnAttachImage.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_btnAttachImage = new GridBagConstraints();
		gbc_btnAttachImage.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAttachImage.insets = new Insets(0, 0, 5, 5);
		gbc_btnAttachImage.gridx = 3;
		gbc_btnAttachImage.gridy = 10;
		contentPane.add(btnAttachImage, gbc_btnAttachImage);

		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("AddLibraryView.panel_1.borderTitle", "Tag list"), //$NON-NLS-2$ //$NON-NLS-3$
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 11;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 110, 57, 250, 0 };
		gbl_panel_1.rowHeights = new int[] { 22, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		final JComboBox cbTag = new JComboBox(Manager.getInstance().TagList.getAllTagNames());
		cbTag.setSelectedIndex(0);
		cbTag.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_cbTag = new GridBagConstraints();
		gbc_cbTag.fill = GridBagConstraints.BOTH;
		gbc_cbTag.insets = new Insets(0, 0, 0, 5);
		gbc_cbTag.gridx = 0;
		gbc_cbTag.gridy = 0;
		panel_1.add(cbTag, gbc_cbTag);

		JButton btnAddTag = new JButton("+");
		btnAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = txtTagList.getText() + " " + cbTag.getSelectedItem();
				txtTagList.setText(DataController.formatTagsString(str));
			}
		});
		btnAddTag.setFont(new Font("Verdana", Font.BOLD, 12));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		panel_1.add(btnAddTag, gbc_button);

		txtTagList = new JTextField();
		txtTagList.setToolTipText(Messages.getString("AddLibraryView.txtTagList.toolTipText", "Tags")); //$NON-NLS-1$ //$NON-NLS-2$
		txtTagList.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtTagList.setColumns(10);
		GridBagConstraints gbc_txtTagList = new GridBagConstraints();
		gbc_txtTagList.fill = GridBagConstraints.BOTH;
		gbc_txtTagList.gridx = 2;
		gbc_txtTagList.gridy = 0;
		panel_1.add(txtTagList, gbc_txtTagList);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 12;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 50, 70, 50, 60, 50, 0 };
		gbl_panel.rowHeights = new int[] { 30, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnClose = new JButton(Messages.getString("AddLibraryView.btnClose.text", "Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnClose.setIcon(new ImageIcon(AddLibraryView.class.getResource("/ua/nau/libstore/resource/icon/cancel_24.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setMaximumSize(new Dimension(90, 23));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.BOTH;
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 3;
		gbc_btnClose.gridy = 0;
		panel.add(btnClose, gbc_btnClose);

		final JLabel lblMessage = new JLabel("");
		lblMessage.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.anchor = GridBagConstraints.NORTH;
		gbc_lblMessage.gridwidth = 4;
		gbc_lblMessage.insets = new Insets(0, 0, 0, 5);
		gbc_lblMessage.gridx = 1;
		gbc_lblMessage.gridy = 13;
		contentPane.add(lblMessage, gbc_lblMessage);

		JButton btnSubmit = new JButton(Messages.getString("AddLibraryView.btnSubmit.text", "Submit")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSubmit.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnSubmit.setIcon(new ImageIcon(AddLibraryView.class.getResource("/ua/nau/libstore/resource/icon/accept_24.png")));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String shortDescription = txtpnShortDescription.getText();
				String fullDescription = txtpnFullDescription.getText();
				String version = frmtdtxtfldVersion.getText();
				LanguageTypeEnum language = LanguageTypeEnum.valueOf(cbLanguage.getSelectedItem()
						.toString());
				int testRaiting = Integer.parseInt((String) cbTestRaiting.getSelectedItem());
				String comment = txtpnComment.getText();
				String require = txtpnRequire.getText();
				String tags = DataController.formatTagsString(txtTagList.getText());

				if (name.length() < 5 || shortDescription.length() < 10
						|| fullDescription.length() < 10 || version.length() < 3
						|| language == LanguageTypeEnum.UNDEFINED || filePath.length() < 8
						|| tags.length() < 3) {
					lblMessage.setForeground(Color.RED);
					lblMessage.setText("Library wasn't added! Check corect of filds.");
					return;
				}

				LibraryBean bean = new LibraryBean(-1, name, shortDescription, fullDescription,
						version, language, 3, testRaiting, comment, new Date(), require);
				Manager manager = Manager.getInstance();
				if (manager.Library.add(bean, filePath, imagePath, tags) == true) {
					lblMessage.setForeground(Color.GREEN);
					lblMessage.setText("Library was added successfully.");
					return;
				}

				lblMessage.setForeground(Color.RED);
				lblMessage.setText("Library wasn't added! It's database problem");
			}
		});
		btnSubmit.setMaximumSize(new Dimension(100, 23));
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.fill = GridBagConstraints.BOTH;
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 0;
		panel.add(btnSubmit, gbc_btnSubmit);

	}

	private MaskFormatter createNewFormatter(String str) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return formatter;
	}
}
