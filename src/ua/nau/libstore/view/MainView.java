package ua.nau.libstore.view;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.DefaultComboBoxModel;

import ua.nau.libstore.controller.DataController;
import ua.nau.libstore.controller.Manager;
import ua.nau.libstore.database.bean.ImageBean;
import ua.nau.libstore.database.bean.LibraryBean;
import ua.nau.libstore.database.bean.UserBean;
import ua.nau.libstore.util.LanguageTypeEnum;
import ua.nau.libstore.util.UserTypeEnum;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JToolBar;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

@SuppressWarnings("serial")
public class MainView extends JFrame {

	private JPanel contentPane;
	private JTextField txtTagList;
	private JTable tblShowResult;
	private JComboBox<String> cbLanguage;
	private JTextField txtSearch;
	private JTable tblSearchResult;
	private JCheckBox chckbxStrictSearch;

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
					Manager.getInstance().setUserBean(new UserBean(-1, "Test", UserTypeEnum.ADMIN));
					MainView frame = new MainView();
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
	public MainView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_32.png")));
		setSize(new Dimension(711, 541));
		setName(Messages.getString("MainView.this.name", "")); //$NON-NLS-1$ //$NON-NLS-2$
		setMinimumSize(new Dimension(700, 400));
		setTitle(Messages.getString("MainView.this.title", "Libstore")); //$NON-NLS-1$ //$NON-NLS-2$
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 540);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Verdana", Font.PLAIN, 12));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu(Messages.getString("MainView.mnFile.text", "File")); //$NON-NLS-1$ //$NON-NLS-2$
		mnFile.setFont(new Font("Verdana", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem(Messages.getString("MainView.mntmExit.text", "Exit")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mntmExit.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnFile.add(mntmExit);
		
		JMenu mnAction = new JMenu(Messages.getString("MainView.mnAction.text", "Action")); //$NON-NLS-1$ //$NON-NLS-2$
		mnAction.setFont(new Font("Verdana", Font.PLAIN, 12));
		menuBar.add(mnAction);
		
		JMenuItem mntmSearch = new JMenuItem(Messages.getString("MainView.mntmSearch.text", "Search")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmSearch.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnAction.add(mntmSearch);
		
		JMenuItem mntmList = new JMenuItem(Messages.getString("MainView.mntmList.text", "List")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmList.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnAction.add(mntmList);
		
		JSeparator separator = new JSeparator();
		mnAction.add(separator);
		
		JMenuItem mntmManageUsers = new JMenuItem(Messages.getString("MainView.mntmManageUsers.text", "Manage users")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmManageUsers.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnAction.add(mntmManageUsers);
		
		JMenu mnTools = new JMenu(Messages.getString("MainView.mnOption.text", "Tools")); //$NON-NLS-1$ //$NON-NLS-2$
		mnTools.setFont(new Font("Verdana", Font.PLAIN, 12));
		menuBar.add(mnTools);
		
		JMenuItem mntmSettings = new JMenuItem(Messages.getString("MainView.mntmSettings.text", "Settings")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmSettings.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnTools.add(mntmSettings);
		
		JMenu mnHelp = new JMenu(Messages.getString("MainView.mnHelp.text", "Help")); //$NON-NLS-1$ //$NON-NLS-2$
		mnHelp.setFont(new Font("Verdana", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem(Messages.getString("MainView.mntmAbout.text", "About")); //$NON-NLS-1$ //$NON-NLS-2$
		mntmAbout.setFont(new Font("Verdana", Font.PLAIN, 12));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusable(false);
		tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("Verdana", Font.PLAIN, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 764, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 428, Short.MAX_VALUE))
		);
		
		JButton btnAdd = new JButton(Messages.getString("MainView.btnAdd.text_1", "Add")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAdd.setFocusable(false);
		btnAdd.setIcon(new ImageIcon(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_add_32.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Manager.getInstance().getUserBean().getPermission() == UserTypeEnum.ADMIN)
				{
					AddLibraryView addLibraryView = new AddLibraryView();
					addLibraryView.setModalityType(ModalityType.APPLICATION_MODAL);
					addLibraryView.setVisible(true);
				} else {					
					JOptionPane.showMessageDialog(contentPane, "Access denied", 
							"You have not permissions to add library!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAdd.setFont(new Font("Verdana", Font.PLAIN, 12));
		toolBar.add(btnAdd);
		
		
		JPanel pnlSearch = new JPanel();
		pnlSearch.setFocusTraversalKeysEnabled(false);
		pnlSearch.setFocusable(false);
		tabbedPane.addTab("Search", new ImageIcon(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_search_16.png")), pnlSearch, null);
		GridBagLayout gbl_pnlSearch = new GridBagLayout();
		gbl_pnlSearch.columnWidths = new int[]{20, 562, 10, 0, 20, 0};
		gbl_pnlSearch.rowHeights = new int[]{15, 30, 41, 304, 1, 0};
		gbl_pnlSearch.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlSearch.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlSearch.setLayout(gbl_pnlSearch);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Verdana", Font.PLAIN, 16));
		txtSearch.setText("");
		txtSearch.setColumns(10);
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.fill = GridBagConstraints.BOTH;
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.gridx = 1;
		gbc_txtSearch.gridy = 1;
		pnlSearch.add(txtSearch, gbc_txtSearch);
		
		JButton btnSearch = new JButton(Messages.getString("MainView.btnSearch.text", "Search"));
		btnSearch.setIcon(new ImageIcon(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_search_24.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Manager manager = Manager.getInstance();		
				LibraryBean[] beans = null;
				
				if(chckbxStrictSearch.isSelected()){
					beans = manager.Search.searchInNaturalMode(txtSearch.getText());
				} else if (chckbxStrictSearch.isSelected() == false){
					beans = manager.Search.searchInBooleanMode(txtSearch.getText());
				}

				tblSearchResult.setModel(new DefaultTableModel(){
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				});
				DataController.fillLibraryBeanToModel((DefaultTableModel)tblSearchResult.getModel(), beans);
				
			}
		});
		btnSearch.setFont(new Font("Verdana", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 1;
		pnlSearch.add(btnSearch, gbc_btnSearch);
		
		chckbxStrictSearch = new JCheckBox(Messages.getString("MainView.chckbxStrictSearch.text", "Strict search")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_chckbxStrictSearch = new GridBagConstraints();
		gbc_chckbxStrictSearch.anchor = GridBagConstraints.WEST;
		gbc_chckbxStrictSearch.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxStrictSearch.gridx = 1;
		gbc_chckbxStrictSearch.gridy = 2;
		pnlSearch.add(chckbxStrictSearch, gbc_chckbxStrictSearch);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.gridwidth = 5;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 3;
		pnlSearch.add(scrollPane_1, gbc_scrollPane_1);
		
		tblSearchResult = new JTable();
		tblSearchResult.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					Point p = e.getPoint();
					int rowIndex = tblSearchResult.rowAtPoint(p);
					tblSearchResult.convertRowIndexToModel(rowIndex);					
					LibraryBean libraryBean = DataController.getLibraryBeanByModel(tblSearchResult.getModel(), rowIndex);
					
					Manager manager = Manager.getInstance();
					ImageBean imageBean = manager.Image.getImageBean(libraryBean.getId());
					byte[] image = null;
					if(imageBean != null){
						 image = imageBean.getData();
					}
					
					String tags = manager.TagList.getTagsByLibraryFK(libraryBean.getId());
					if(libraryBean != null && tags.length() > 3){
						/*ViewLibraryView view =*/ new ViewLibraryView(libraryBean, image, tags);
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
		tblSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblSearchResult.setBackground(SystemColor.inactiveCaptionBorder);
		tblSearchResult.setFillsViewportHeight(true);
		tblSearchResult.setDoubleBuffered(true);
		tblSearchResult.setFont(new Font("Verdana", Font.PLAIN, 12));
		scrollPane_1.setViewportView(tblSearchResult);
		pnlSearch.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtSearch, chckbxStrictSearch, btnSearch, scrollPane_1, tblSearchResult}));
		
		JPanel pnlList = new JPanel();
		pnlList.setFocusable(false);
		pnlList.setFont(new Font("Verdana", Font.PLAIN, 13));
		pnlList.setName(Messages.getString("MainView.pnlList.name", "")); //$NON-NLS-1$ //$NON-NLS-2$
		tabbedPane.addTab("List", new ImageIcon(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_show_16.png")), pnlList, null);
		
		JButton btnShow = new JButton(Messages.getString("MainView.btnShow.text", "Show")); //$NON-NLS-1$ //$NON-NLS-2$
		btnShow.setIcon(new ImageIcon(MainView.class.getResource("/ua/nau/libstore/resource/icon/library_search_24.png")));
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Manager manager = Manager.getInstance();		
				LanguageTypeEnum language = LanguageTypeEnum.valueOf(cbLanguage.getSelectedItem().toString());
				String taglist = txtTagList.getText();
				LibraryBean[] beans = manager.Search.showByData(language, taglist);
				tblShowResult.setModel(new DefaultTableModel(){
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				});
				DataController.fillLibraryBeanToModel((DefaultTableModel)tblShowResult.getModel(), beans);
				
			}
		});
		btnShow.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JPanel pnlTags = new JPanel();
		pnlTags.setFont(new Font("Verdana", Font.PLAIN, 12));
		pnlTags.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Messages.getString("MainView.pnlTags.borderTitle_1", "Tag list"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-2$ //$NON-NLS-3$
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, Messages.getString("MainView.panel.borderTitle", ""), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$ //$NON-NLS-2$
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setDoubleBuffered(true);
		scrollPane.setSize(new Dimension(100, 100));
		scrollPane.setPreferredSize(new Dimension(100, 100));
		GroupLayout gl_pnlList = new GroupLayout(pnlList);
		gl_pnlList.setHorizontalGroup(
			gl_pnlList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlList.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(pnlTags, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnShow)
					.addContainerGap(114, Short.MAX_VALUE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
		);
		gl_pnlList.setVerticalGroup(
			gl_pnlList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlList.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlList.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlList.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(pnlTags, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnShow, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		
		tblShowResult = new JTable();
		tblShowResult.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					Point p = e.getPoint();
					int rowIndex = tblShowResult.rowAtPoint(p);
					tblShowResult.convertRowIndexToModel(rowIndex);					
					LibraryBean libraryBean = DataController.getLibraryBeanByModel(tblShowResult.getModel(), rowIndex);
					
					Manager manager = Manager.getInstance();
					ImageBean imageBean = manager.Image.getImageBean(libraryBean.getId());
					byte[] image = null;
					if(imageBean != null){
						 image = imageBean.getData();
					}
					
					String tags = manager.TagList.getTagsByLibraryFK(libraryBean.getId());
					if(libraryBean != null && tags.length() > 3){
						/*ViewLibraryView view =*/ new ViewLibraryView(libraryBean, image, tags);
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
		tblShowResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblShowResult.setBackground(SystemColor.inactiveCaptionBorder);
		tblShowResult.setFillsViewportHeight(true);
		tblShowResult.setDoubleBuffered(true);
		tblShowResult.setFont(new Font("Verdana", Font.PLAIN, 12));
		scrollPane.setViewportView(tblShowResult);
		
		JLabel lblLanguage = new JLabel(Messages.getString("MainView.lblLanguage.text", "Language:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblLanguage.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		cbLanguage = new JComboBox<String>();
		cbLanguage.setRequestFocusEnabled(false);
		cbLanguage.setName(Messages.getString("MainView.cbLanguage.name", "")); //$NON-NLS-1$ //$NON-NLS-2$
		cbLanguage.setModel(new DefaultComboBoxModel(LanguageTypeEnum.values()));
		
		cbLanguage.setFont(new Font("Verdana", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(cbLanguage, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLanguage))
					.addContainerGap(109, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(31, Short.MAX_VALUE)
					.addComponent(lblLanguage)
					.addGap(18)
					.addComponent(cbLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
		);
		panel.setLayout(gl_panel);
		
		final JComboBox cbTag = new JComboBox(Manager.getInstance().TagList.getAllTagNames());
		cbTag.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Manager manager = Manager.getInstance();
				String[] tags = manager.TagList.getAllTagNames();	
				if(cbTag.getItemCount() - 1 == tags.length) return;
				
				cbTag.removeAllItems();		
				cbTag.addItem("<select>");
				for (int i = 0; i < tags.length; i++) {
					cbTag.addItem(tags[i]);
				}
			}
		});
		cbTag.insertItemAt("<select>", 0);
		cbTag.setSelectedIndex(0);
		
		cbTag.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		JButton btnAddTag = new JButton(Messages.getString("MainView.btnAdd.text", "Add")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cbTag.getSelectedItem().equals("<select>")) return;
				
				String str = txtTagList.getText() + " " + cbTag.getSelectedItem();
				txtTagList.setText(DataController.formatTagsString(str));
			}
		});
		btnAddTag.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		txtTagList = new JTextField();
		txtTagList.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtTagList.setText(DataController.formatTagsString(txtTagList.getText()));
			}
		});
		txtTagList.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtTagList.setToolTipText(Messages.getString("MainView.textField.toolTipText", "Tags")); //$NON-NLS-1$ //$NON-NLS-2$
		txtTagList.setColumns(10);
		GroupLayout gl_pnlTags = new GroupLayout(pnlTags);
		gl_pnlTags.setHorizontalGroup(
			gl_pnlTags.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTags.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlTags.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_pnlTags.createSequentialGroup()
							.addComponent(cbTag, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
							.addComponent(btnAddTag, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtTagList, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlTags.setVerticalGroup(
			gl_pnlTags.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTags.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlTags.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbTag, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddTag))
					.addGap(18)
					.addComponent(txtTagList, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		pnlTags.setLayout(gl_pnlTags);
		pnlList.setLayout(gl_pnlList);
		contentPane.setLayout(gl_contentPane);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtSearch, chckbxStrictSearch, btnSearch, tabbedPane, btnAdd, tblSearchResult, pnlSearch, scrollPane_1, toolBar, pnlList, panel, cbLanguage, lblLanguage, pnlTags, cbTag, btnAddTag, txtTagList, btnShow, scrollPane, tblShowResult}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtSearch, chckbxStrictSearch, btnSearch, menuBar, mnFile, mntmExit, mnAction, mntmSearch, mntmList, separator, mntmManageUsers, mnTools, mntmSettings, mnHelp, mntmAbout, contentPane, toolBar, btnAdd, tabbedPane, pnlSearch, scrollPane_1, tblSearchResult, pnlList, panel, cbLanguage, lblLanguage, pnlTags, cbTag, btnAddTag, txtTagList, btnShow, scrollPane, tblShowResult}));
		
	}
}
