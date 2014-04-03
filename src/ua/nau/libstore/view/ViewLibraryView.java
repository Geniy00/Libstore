package ua.nau.libstore.view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import ua.nau.libstore.controller.DataController;
import ua.nau.libstore.controller.Manager;
import ua.nau.libstore.database.bean.ImageBean;
import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.resource.Resource;
import ua.nau.libstore.util.Javadoc;

import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ViewLibraryView extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtVersion;
	private JTextField txtLanguage;
	private JTextField txtTestRaiting;
	private JTextField txtTags;
	
	private JTextPane txtpnShortDescription;
	private JTextPane txtpnFullDescription;
	private JTextPane txtpnComment;
	private JTextPane txtpnRequire;
	private JLabel lblDateValue;
	private JLabel lblRatingValue;
	private JLabel lblImage;
	
	private LibraryBean libraryBean;
	private byte[] image;
	private String tags;
	
	private LibraryBean[] otherVersionLibrary;
	private LibraryBean[] relatedLibrary;
	private JTable tblVersions;
	private JTable tblRelated;
	
	private String downloadPath; 
	private String extractedFolder;
	
	
	public ViewLibraryView(LibraryBean libraryBean, byte[] image, String tags){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewLibraryView.class.getResource("/ua/nau/libstore/resource/icon/library_show_16.png")));		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
		try {
			//ViewLibraryView frame = new ViewLibraryView();
			createComponents();
			this.libraryBean = libraryBean;
			this.image = image;
			this.tags = tags;
			
			this.fillFields();
			this.setVisible(true);
			//frame.fillFields();
			//frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillFields(){ 
		this.setTitle(libraryBean.getName());
		
		lblDateValue.setText(new SimpleDateFormat("dd.MM.yyyy").format(libraryBean.getDate()));
		txtName.setText(libraryBean.getName());
		txtpnShortDescription.setText(libraryBean.getShortDescription());
		txtpnFullDescription.setText(libraryBean.getFullDescription());
		txtVersion.setText(libraryBean.getVersion());
		txtLanguage.setText(libraryBean.getLanguage().name());
		txtTestRaiting.setText(new Integer(libraryBean.getTestRaiting()).toString());
		txtpnComment.setText(libraryBean.getComment());
		txtpnRequire.setText(libraryBean.getRequire());
		txtTags.setText(tags);
		lblRatingValue.setText(String.format("%.1f", libraryBean.getRaiting()));
		if(image != null){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new ByteArrayInputStream(image));
				lblImage.setIcon(new ImageIcon(myPicture.getScaledInstance(150, 150, 1)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * Create the frame.
	 */
	private void createComponents() {
		setMinimumSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 654, 581);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Verdana", Font.PLAIN, 11));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{5, 604, 5, 0};
		gbl_contentPane.rowHeights = new int[]{10, 514, 10, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane)evt.getSource();
				int item = pane.getSelectedIndex();
				
				Manager manager = Manager.getInstance();
				if(item == 1){ //Version tab
					if(otherVersionLibrary == null){
						otherVersionLibrary = manager.Search.searchOtherVersion(libraryBean.getName(), libraryBean.getLanguage());
					}
					
					tblVersions.setModel(new DefaultTableModel(){
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
					DataController.fillLibraryBeanToModel((DefaultTableModel)tblVersions.getModel(), otherVersionLibrary);
				} else if(item == 2) { //ralated tab
					relatedLibrary = manager.Search.searchRelated(tags);
					tblRelated.setModel(new DefaultTableModel(){
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
					DataController.fillLibraryBeanToModel((DefaultTableModel)tblRelated.getModel(), relatedLibrary);
				}
			}
		});
		tabbedPane.setDoubleBuffered(true);
		tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 14));
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		JPanel pnlInfo = new JPanel();
		tabbedPane.addTab("Info", null, pnlInfo, null);
		GridBagLayout gbl_pnlInfo = new GridBagLayout();
		gbl_pnlInfo.columnWidths = new int[]{10, 85, 52, 30, 61, 110, 125, 8, 0};
		gbl_pnlInfo.rowHeights = new int[]{5, 15, 27, 65, 66, 20, 20, 20, 56, 56, 21, 5, 0};
		gbl_pnlInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 10.0, 10.0, 0.0, Double.MIN_VALUE};
		gbl_pnlInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlInfo.setLayout(gbl_pnlInfo);
		
		JLabel lblDate = new JLabel(Messages.getString("ViewLibraryView.lblDate.text", "Date:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDate.setForeground(Color.LIGHT_GRAY);
		lblDate.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 4;
		gbc_lblDate.gridy = 1;
		pnlInfo.add(lblDate, gbc_lblDate);
		
		lblDateValue = new JLabel("20.05.2012");
		lblDateValue.setForeground(Color.LIGHT_GRAY);
		lblDateValue.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblDateValue = new GridBagConstraints();
		gbc_lblDateValue.anchor = GridBagConstraints.WEST;
		gbc_lblDateValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateValue.gridx = 5;
		gbc_lblDateValue.gridy = 1;
		pnlInfo.add(lblDateValue, gbc_lblDateValue);
		
		JLabel lblName = new JLabel(Messages.getString("ViewLibraryView.lblName.text", "Name:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblName.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 4;
		gbc_lblName.gridy = 2;
		pnlInfo.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtName.setEditable(false);
		txtName.setColumns(10);
		txtName.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtName.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.gridwidth = 2;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 5;
		gbc_txtName.gridy = 2;
		pnlInfo.add(txtName, gbc_txtName);
		
		lblImage = new JLabel(Messages.getString("ViewLibraryView.lblImage.text", "Image")); //$NON-NLS-1$ //$NON-NLS-2$
		lblImage.setSize(new Dimension(150, 150));
		lblImage.setPreferredSize(new Dimension(150, 150));
		lblImage.setMinimumSize(new Dimension(150, 150));
		lblImage.setMaximumSize(new Dimension(150, 150));
		lblImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage.setBackground(SystemColor.inactiveCaption);
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.gridheight = 3;
		gbc_lblImage.gridwidth = 2;
		gbc_lblImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblImage.gridx = 1;
		gbc_lblImage.gridy = 3;
		pnlInfo.add(lblImage, gbc_lblImage);
		
		JLabel lblShortDescription = new JLabel(Messages.getString("ViewLibraryView.lblShortDescription.text", "Short Description:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblShortDescription.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblShortDescription = new GridBagConstraints();
		gbc_lblShortDescription.anchor = GridBagConstraints.WEST;
		gbc_lblShortDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblShortDescription.gridx = 4;
		gbc_lblShortDescription.gridy = 3;
		pnlInfo.add(lblShortDescription, gbc_lblShortDescription);
		
		JScrollPane scrllpnlShortDescription = new JScrollPane();
		GridBagConstraints gbc_scrllpnlShortDescription = new GridBagConstraints();
		gbc_scrllpnlShortDescription.fill = GridBagConstraints.BOTH;
		gbc_scrllpnlShortDescription.gridwidth = 2;
		gbc_scrllpnlShortDescription.insets = new Insets(0, 0, 5, 5);
		gbc_scrllpnlShortDescription.gridx = 5;
		gbc_scrllpnlShortDescription.gridy = 3;
		pnlInfo.add(scrllpnlShortDescription, gbc_scrllpnlShortDescription);
		
		txtpnShortDescription = new JTextPane();
		txtpnShortDescription.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtpnShortDescription.setBackground(SystemColor.inactiveCaptionBorder);
		txtpnShortDescription.setText("short description");
		scrllpnlShortDescription.setViewportView(txtpnShortDescription);
		
		JLabel lblFullDescription = new JLabel(Messages.getString("ViewLibraryView.lblFullDescription.text", "Full Description:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFullDescription.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblFullDescription = new GridBagConstraints();
		gbc_lblFullDescription.fill = GridBagConstraints.VERTICAL;
		gbc_lblFullDescription.anchor = GridBagConstraints.WEST;
		gbc_lblFullDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblFullDescription.gridx = 4;
		gbc_lblFullDescription.gridy = 4;
		pnlInfo.add(lblFullDescription, gbc_lblFullDescription);
		
		JScrollPane scrllpnlFullDescription = new JScrollPane();
		GridBagConstraints gbc_scrllpnlFullDescription = new GridBagConstraints();
		gbc_scrllpnlFullDescription.fill = GridBagConstraints.BOTH;
		gbc_scrllpnlFullDescription.gridwidth = 2;
		gbc_scrllpnlFullDescription.insets = new Insets(0, 0, 5, 5);
		gbc_scrllpnlFullDescription.gridx = 5;
		gbc_scrllpnlFullDescription.gridy = 4;
		pnlInfo.add(scrllpnlFullDescription, gbc_scrllpnlFullDescription);
		
		txtpnFullDescription = new JTextPane();
		txtpnFullDescription.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtpnFullDescription.setBackground(SystemColor.inactiveCaptionBorder);
		txtpnFullDescription.setText("full description");
		scrllpnlFullDescription.setViewportView(txtpnFullDescription);
		
		JLabel lblVersion = new JLabel(Messages.getString("ViewLibraryView.lblVersion.text", "Version:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblVersion.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.fill = GridBagConstraints.BOTH;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 4;
		gbc_lblVersion.gridy = 5;
		pnlInfo.add(lblVersion, gbc_lblVersion);
		
		txtVersion = new JTextField();
		txtVersion.setText("Version");
		txtVersion.setHorizontalAlignment(SwingConstants.CENTER);
		txtVersion.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtVersion.setEditable(false);
		txtVersion.setColumns(10);
		txtVersion.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_txtVersion = new GridBagConstraints();
		gbc_txtVersion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVersion.anchor = GridBagConstraints.NORTH;
		gbc_txtVersion.insets = new Insets(0, 0, 5, 5);
		gbc_txtVersion.gridx = 5;
		gbc_txtVersion.gridy = 5;
		pnlInfo.add(txtVersion, gbc_txtVersion);
		
		JLabel lblRaiting = new JLabel(Messages.getString("ViewLibraryView.lblRaiting.text", "Rating:")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblRaiting = new GridBagConstraints();
		gbc_lblRaiting.anchor = GridBagConstraints.EAST;
		gbc_lblRaiting.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaiting.gridx = 1;
		gbc_lblRaiting.gridy = 6;
		pnlInfo.add(lblRaiting, gbc_lblRaiting);
		
		lblRatingValue = new JLabel("Value");
		GridBagConstraints gbc_lblRatingValue = new GridBagConstraints();
		gbc_lblRatingValue.anchor = GridBagConstraints.WEST;
		gbc_lblRatingValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblRatingValue.gridx = 2;
		gbc_lblRatingValue.gridy = 6;
		pnlInfo.add(lblRatingValue, gbc_lblRatingValue);
		
		JLabel LblLanguage = new JLabel(Messages.getString("ViewLibraryView.LblLanguage.text", "Language:")); //$NON-NLS-1$ //$NON-NLS-2$
		LblLanguage.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_LblLanguage = new GridBagConstraints();
		gbc_LblLanguage.fill = GridBagConstraints.BOTH;
		gbc_LblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_LblLanguage.gridx = 4;
		gbc_LblLanguage.gridy = 6;
		pnlInfo.add(LblLanguage, gbc_LblLanguage);
		
		txtLanguage = new JTextField();
		txtLanguage.setText("Language");
		txtLanguage.setHorizontalAlignment(SwingConstants.CENTER);
		txtLanguage.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtLanguage.setEditable(false);
		txtLanguage.setColumns(10);
		txtLanguage.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_txtLanguage = new GridBagConstraints();
		gbc_txtLanguage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLanguage.anchor = GridBagConstraints.NORTH;
		gbc_txtLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_txtLanguage.gridx = 5;
		gbc_txtLanguage.gridy = 6;
		pnlInfo.add(txtLanguage, gbc_txtLanguage);
		
		JLabel lblTestRaiting = new JLabel(Messages.getString("ViewLibraryView.lblTestRaiting.text", "Test quality:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblTestRaiting.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblTestRaiting = new GridBagConstraints();
		gbc_lblTestRaiting.fill = GridBagConstraints.BOTH;
		gbc_lblTestRaiting.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestRaiting.gridx = 4;
		gbc_lblTestRaiting.gridy = 7;
		pnlInfo.add(lblTestRaiting, gbc_lblTestRaiting);
		
		txtTestRaiting = new JTextField();
		txtTestRaiting.setText("test raiting");
		txtTestRaiting.setHorizontalAlignment(SwingConstants.CENTER);
		txtTestRaiting.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtTestRaiting.setEditable(false);
		txtTestRaiting.setColumns(10);
		txtTestRaiting.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_txtTestRaiting = new GridBagConstraints();
		gbc_txtTestRaiting.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTestRaiting.anchor = GridBagConstraints.NORTH;
		gbc_txtTestRaiting.insets = new Insets(0, 0, 5, 5);
		gbc_txtTestRaiting.gridx = 5;
		gbc_txtTestRaiting.gridy = 7;
		pnlInfo.add(txtTestRaiting, gbc_txtTestRaiting);
		
		JPanel pnlActions = new JPanel();
		pnlActions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlActions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("ViewLibraryView.pnlActions.borderTitle", "Library actions"), TitledBorder.CENTER, TitledBorder.TOP, null, null)); //$NON-NLS-2$ //$NON-NLS-3$
		GridBagConstraints gbc_pnlActions = new GridBagConstraints();
		gbc_pnlActions.fill = GridBagConstraints.BOTH;
		gbc_pnlActions.gridheight = 3;
		gbc_pnlActions.gridwidth = 2;
		gbc_pnlActions.insets = new Insets(0, 0, 5, 5);
		gbc_pnlActions.gridx = 1;
		gbc_pnlActions.gridy = 8;
		pnlInfo.add(pnlActions, gbc_pnlActions);
		GridBagLayout gbl_pnlActions = new GridBagLayout();
		gbl_pnlActions.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlActions.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlActions.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlActions.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlActions.setLayout(gbl_pnlActions);
		
		JButton btnDownload = new JButton(Messages.getString("ViewLibraryView.btnDownload.text", "Download")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveChooser = new JFileChooser(downloadPath);
				saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = saveChooser.showSaveDialog(ViewLibraryView.this);//Dialog(ViewLibraryView.this, "Save");
				
				
				if(returnValue == JFileChooser.APPROVE_OPTION){
					File file = saveChooser.getSelectedFile();
					downloadPath = file.getAbsolutePath();
					
					Manager manager = Manager.getInstance();
					boolean isSaved = manager.File.saveFile(libraryBean.getId(), downloadPath);
					if(isSaved == false){
						JOptionPane.showMessageDialog(ViewLibraryView.this, "The library wasn't saved! Try save again in other folder",
								"Library not saved", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnDownload.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnDownload.setIcon(new ImageIcon(ViewLibraryView.class.getResource("/ua/nau/libstore/resource/icon/library_download_16.png")));
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDownload.insets = new Insets(0, 0, 5, 5);
		gbc_btnDownload.gridx = 1;
		gbc_btnDownload.gridy = 0;
		pnlActions.add(btnDownload, gbc_btnDownload);
		
		JButton btnOpenFolder = new JButton(Messages.getString("ViewLibraryView.btnOpenFolder.text", "Open folder")); //$NON-NLS-1$ //$NON-NLS-2$
		btnOpenFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveChooser = new JFileChooser(extractedFolder);
				saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = saveChooser.showSaveDialog(ViewLibraryView.this);
				
				if(returnValue == JFileChooser.APPROVE_OPTION){
					File file = saveChooser.getSelectedFile();
					extractedFolder = file.getAbsolutePath();
					
					Manager manager = Manager.getInstance();
					boolean isSaved = manager.File.extractFile(libraryBean.getId(), extractedFolder);
					if(isSaved == true) {
						Desktop desktop = null;
						if(Desktop.isDesktopSupported()){
							desktop = Desktop.getDesktop();
						}
						try {
							desktop.open(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(ViewLibraryView.this, "The library wasn't saved and can be opened! Try save again in other folder",
								"Library not saved", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnOpenFolder.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_btnOpenFolder = new GridBagConstraints();
		gbc_btnOpenFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpenFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpenFolder.gridx = 1;
		gbc_btnOpenFolder.gridy = 1;
		pnlActions.add(btnOpenFolder, gbc_btnOpenFolder);
		
		JButton btnViewJavadoc = new JButton(Messages.getString("ViewLibraryView.btnViewJavadoc.text", "View javadoc")); //$NON-NLS-1$ //$NON-NLS-2$
		btnViewJavadoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manager manager = Manager.getInstance();
				String path = Resource.getString("path.temp.unzip") + libraryBean.getId() + "/";
				boolean isExtracted = manager.File.extractFile(libraryBean.getId(), path);
				if(isExtracted == true) {
					boolean isGenerated = Javadoc.generateJavadoc(path);	
					if(isGenerated == false){
						JOptionPane.showMessageDialog(ViewLibraryView.this, "Javadoc documentation cann't be generated for this path!",
								"Javadoc error generation", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(ViewLibraryView.this, "The library wasn't saved and can be opened! Try save again in other folder",
							"Library not saved", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnViewJavadoc.setFont(new Font("Verdana", Font.PLAIN, 12));
		GridBagConstraints gbc_btnViewJavadoc = new GridBagConstraints();
		gbc_btnViewJavadoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewJavadoc.insets = new Insets(0, 0, 0, 5);
		gbc_btnViewJavadoc.gridx = 1;
		gbc_btnViewJavadoc.gridy = 2;
		pnlActions.add(btnViewJavadoc, gbc_btnViewJavadoc);
		
		JLabel lblComment = new JLabel(Messages.getString("ViewLibraryView.lblComment.text", "Comment:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblComment.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblComment = new GridBagConstraints();
		gbc_lblComment.anchor = GridBagConstraints.WEST;
		gbc_lblComment.insets = new Insets(0, 0, 5, 5);
		gbc_lblComment.gridx = 4;
		gbc_lblComment.gridy = 8;
		pnlInfo.add(lblComment, gbc_lblComment);
		
		JScrollPane scrllpnlComment = new JScrollPane();
		GridBagConstraints gbc_scrllpnlComment = new GridBagConstraints();
		gbc_scrllpnlComment.fill = GridBagConstraints.BOTH;
		gbc_scrllpnlComment.gridwidth = 2;
		gbc_scrllpnlComment.insets = new Insets(0, 0, 5, 5);
		gbc_scrllpnlComment.gridx = 5;
		gbc_scrllpnlComment.gridy = 8;
		pnlInfo.add(scrllpnlComment, gbc_scrllpnlComment);
		
		txtpnComment = new JTextPane();
		txtpnComment.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtpnComment.setBackground(SystemColor.inactiveCaptionBorder);
		txtpnComment.setText("comment");
		scrllpnlComment.setViewportView(txtpnComment);
		
		JLabel lblRequire = new JLabel(Messages.getString("ViewLibraryView.lblRequire.text", "Require:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRequire.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblRequire = new GridBagConstraints();
		gbc_lblRequire.fill = GridBagConstraints.VERTICAL;
		gbc_lblRequire.anchor = GridBagConstraints.WEST;
		gbc_lblRequire.insets = new Insets(0, 0, 5, 5);
		gbc_lblRequire.gridx = 4;
		gbc_lblRequire.gridy = 9;
		pnlInfo.add(lblRequire, gbc_lblRequire);
		
		JScrollPane scrllpnlRequire = new JScrollPane();
		GridBagConstraints gbc_scrllpnlRequire = new GridBagConstraints();
		gbc_scrllpnlRequire.fill = GridBagConstraints.BOTH;
		gbc_scrllpnlRequire.gridwidth = 2;
		gbc_scrllpnlRequire.insets = new Insets(0, 0, 5, 5);
		gbc_scrllpnlRequire.gridx = 5;
		gbc_scrllpnlRequire.gridy = 9;
		pnlInfo.add(scrllpnlRequire, gbc_scrllpnlRequire);
		
		txtpnRequire = new JTextPane();
		txtpnRequire.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtpnRequire.setBackground(SystemColor.inactiveCaptionBorder);
		txtpnRequire.setText("require");
		scrllpnlRequire.setViewportView(txtpnRequire);
		
		JLabel lblTags = new JLabel(Messages.getString("ViewLibraryView.lblTags.text", "Tags:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblTags.setFont(new Font("Verdana", Font.PLAIN, 11));
		GridBagConstraints gbc_lblTags = new GridBagConstraints();
		gbc_lblTags.fill = GridBagConstraints.BOTH;
		gbc_lblTags.insets = new Insets(0, 0, 5, 5);
		gbc_lblTags.gridx = 4;
		gbc_lblTags.gridy = 10;
		pnlInfo.add(lblTags, gbc_lblTags);
		
		txtTags = new JTextField();
		txtTags.setText("Tags");
		txtTags.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtTags.setEditable(false);
		txtTags.setColumns(10);
		txtTags.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_txtTags = new GridBagConstraints();
		gbc_txtTags.insets = new Insets(0, 0, 5, 5);
		gbc_txtTags.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTags.anchor = GridBagConstraints.NORTH;
		gbc_txtTags.gridwidth = 2;
		gbc_txtTags.gridx = 5;
		gbc_txtTags.gridy = 10;
		pnlInfo.add(txtTags, gbc_txtTags);
		
		JPanel pnlVersions = new JPanel();
		tabbedPane.addTab("Versions", null, pnlVersions, null);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_pnlVersions = new GroupLayout(pnlVersions);
		gl_pnlVersions.setHorizontalGroup(
			gl_pnlVersions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVersions.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlVersions.setVerticalGroup(
			gl_pnlVersions.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlVersions.createSequentialGroup()
					.addContainerGap(51, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tblVersions = new JTable();
		tblVersions.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					Point p = e.getPoint();
					int rowIndex = tblVersions.rowAtPoint(p);
					tblVersions.convertRowIndexToModel(rowIndex);					
					LibraryBean libraryBean = DataController.getLibraryBeanByModel(tblVersions.getModel(), rowIndex);
					
					Manager manager = Manager.getInstance();
					ImageBean imageBean = manager.Image.getImageBean(libraryBean.getId());
					byte[] image = null;
					if(imageBean != null){
						 image = imageBean.getData();
					}
					
					String tags = manager.TagList.getTagsByLibraryFK(libraryBean.getId());
					if(libraryBean != null && tags.length() > 3){
						new ViewLibraryView(libraryBean, image, tags);
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		tblVersions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblVersions.setFont(new Font("Verdana", Font.PLAIN, 12));
		tblVersions.setFillsViewportHeight(true);
		tblVersions.setDoubleBuffered(true);
		tblVersions.setBackground(SystemColor.inactiveCaptionBorder);
		scrollPane.setViewportView(tblVersions);
		pnlVersions.setLayout(gl_pnlVersions);
		
		JPanel pnlRelated = new JPanel();
		tabbedPane.addTab("Related", null, pnlRelated, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_pnlRelated = new GroupLayout(pnlRelated);
		gl_pnlRelated.setHorizontalGroup(
			gl_pnlRelated.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRelated.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlRelated.setVerticalGroup(
			gl_pnlRelated.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlRelated.createSequentialGroup()
					.addContainerGap(51, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tblRelated = new JTable();
		tblRelated.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					Point p = e.getPoint();
					int rowIndex = tblRelated.rowAtPoint(p);
					tblRelated.convertRowIndexToModel(rowIndex);					
					LibraryBean libraryBean = DataController.getLibraryBeanByModel(tblRelated.getModel(), rowIndex);
					
					Manager manager = Manager.getInstance();
					ImageBean imageBean = manager.Image.getImageBean(libraryBean.getId());
					byte[] image = null;
					if(imageBean != null){
						 image = imageBean.getData();
					}
					
					String tags = manager.TagList.getTagsByLibraryFK(libraryBean.getId());
					if(libraryBean != null && tags.length() > 3){
						new ViewLibraryView(libraryBean, image, tags);
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		tblRelated.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRelated.setFont(new Font("Verdana", Font.PLAIN, 12));
		tblRelated.setFillsViewportHeight(true);
		tblRelated.setDoubleBuffered(true);
		tblRelated.setBackground(SystemColor.inactiveCaptionBorder);
		scrollPane_1.setViewportView(tblRelated);
		pnlRelated.setLayout(gl_pnlRelated);
	}
}
