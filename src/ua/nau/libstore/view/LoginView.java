package ua.nau.libstore.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ua.nau.libstore.controller.Manager;
import ua.nau.libstore.database.bean.UserBean;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField pwdPassword;

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
					LoginView frame = new LoginView();
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
	public LoginView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/ua/nau/libstore/resource/icon/library_32.png")));
		setFont(new Font("Verdana", Font.PLAIN, 12));
		setResizable(false);
		setTitle(Messages.getString("LoginView.this.title", "Login")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 270);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLogin = new JButton(Messages.getString("LoginView.btnLogin.text", "Login")); //$NON-NLS-1$ //$NON-NLS-2$
		btnLogin.setIcon(new ImageIcon(LoginView.class.getResource("/ua/nau/libstore/resource/icon/sign_in_24.png")));
		btnLogin.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				Manager manager = Manager.getInstance();
				UserBean userBean = manager.User.login(txtLogin.getText(), new String(pwdPassword.getPassword()));
				manager.setUserBean(userBean);
				
				if(userBean == null){
					JOptionPane.showMessageDialog(contentPane, "User/Password wasn't found in database", 
							"User/Password is incorect", JOptionPane.WARNING_MESSAGE);
					txtLogin.setText("");
					pwdPassword.setText("");
					return;
				} else{
					new MainView().setVisible(true);
					dispose();				
				}
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnExit = new JButton(Messages.getString("LoginView.btnExit.text", "Exit")); //$NON-NLS-1$ //$NON-NLS-2$
		btnExit.setIcon(new ImageIcon(LoginView.class.getResource("/ua/nau/libstore/resource/icon/sign-out_24.png")));
		btnExit.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnExit.setFocusable(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(27)
							.addComponent(btnExit)
							.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(34, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		
		JLabel lblLogin = new JLabel(Messages.getString("LoginView.lblLogin.text", "Login:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblLogin.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		JLabel lblPassword = new JLabel(Messages.getString("LoginView.lblPassword.text", "Password:")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtLogin.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLogin)
						.addComponent(lblPassword))
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(pwdPassword)
						.addComponent(txtLogin, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
					.addGap(35))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin)
						.addComponent(txtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(pwdPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
