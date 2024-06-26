package org.team4.view.login;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField pwdPassword;
	private JButton btnShowPassword;
	private JButton btnRegister;
	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JLabel txtpnAccountNotFound;
	private JLabel txtpnPasswordIncorrect;
	private JPanel loginPane;
	private JPanel registerPane;
	private JLabel txtpnSignUp;
	private JLabel txtpnWelcome;
	private JTextField txtRegisterEmail;
	private JPasswordField pwdRegisterPassword;
	private JButton btnShowPassword_1;
	private JButton btnAlreadyHaveAccount;
	private JTextField txtName;
	private JComboBox<String> comboBox;
	private JLabel txtpnSelectAccountType;
	
	private static final String SHOW_PASSWORD_PATH = "Assets/showPasswordIconReScaled.png";

	public void addPlaceholderStyle(JTextField textField) {
		Font font = textField.getFont();
		font = font.deriveFont(Font.ITALIC);
		textField.setFont(font);
		textField.setForeground(Color.gray);
	}

	public void removePlaceholderStyle(JTextField textField) {
		Font font = textField.getFont();
		font = font.deriveFont(Font.PLAIN);
		textField.setFont(font);
		textField.setForeground(Color.black);
	}

	public void showLoginPage() {
		registerPane.setVisible(false);
		loginPane.setVisible(true);
	}

	public void showRegistrationPage() {
		loginPane.setVisible(false);
		registerPane.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		initComponents();
		addPlaceholderStyle(txtEmail);
		addPlaceholderStyle(pwdPassword);
		addPlaceholderStyle(txtRegisterEmail);
		addPlaceholderStyle(pwdRegisterPassword);
		addPlaceholderStyle(txtName);
		showLoginPage();

	}

	/**
	 * Create frame components
	 */
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		loginPane = new JPanel();
		loginPane.setBounds(0, 0, 284, 361);
		loginPane.setLayout(null);

		registerPane = new JPanel();
		registerPane.setBounds(0, 0, 284, 361);
		registerPane.setLayout(null);
		contentPane.add(registerPane);

		txtpnSignUp = new JLabel("",SwingConstants.CENTER);
		txtpnSignUp.setText("Sign Up");
		txtpnSignUp.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnSignUp.setBackground(new Color(240, 240, 240));
		txtpnSignUp.setBounds(0, 5, 284, 30);
		registerPane.add(txtpnSignUp);

		txtRegisterEmail = new JTextField();
		txtRegisterEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtRegisterEmail.getText().equals("Email")) {
					txtRegisterEmail.setText(null);
					txtRegisterEmail.requestFocus();
					removePlaceholderStyle(txtRegisterEmail);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtRegisterEmail.getText().length() == 0) {
					addPlaceholderStyle(txtRegisterEmail);
					txtRegisterEmail.setText("Email");
				}
			}
		});
		txtRegisterEmail.setToolTipText("Email");
		txtRegisterEmail.setText("Email");
		txtRegisterEmail.setForeground(Color.GRAY);
		txtRegisterEmail.setFont(new Font("Tahoma", Font.ITALIC, 11));
		txtRegisterEmail.setColumns(10);
		txtRegisterEmail.setBounds(57, 90, 170, 30);
		registerPane.add(txtRegisterEmail);

		pwdRegisterPassword = new JPasswordField();
		pwdRegisterPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (Arrays.equals(pwdRegisterPassword.getPassword(),
						new char[] { 'P', 'a', 's', 's', 'w', 'o', 'r', 'd' })) {
					pwdRegisterPassword.setText(null);
					pwdRegisterPassword.requestFocus();
					pwdRegisterPassword.setEchoChar('\u2022');
					removePlaceholderStyle(pwdRegisterPassword);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (pwdRegisterPassword.getPassword().length == 0) {
					addPlaceholderStyle(pwdRegisterPassword);
					pwdRegisterPassword.setText("Password");
					pwdRegisterPassword.setEchoChar((char) 0);
				}
			}
		});
		pwdRegisterPassword.setToolTipText("Password");
		pwdRegisterPassword.setText("Password");
		pwdRegisterPassword.setEchoChar(' ');
		pwdRegisterPassword.setBounds(57, 140, 170, 30);
		registerPane.add(pwdRegisterPassword);

		btnShowPassword_1 = new JButton();
		btnShowPassword_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pwdRegisterPassword.getForeground() != Color.gray) {
					if (pwdRegisterPassword.getEchoChar() == '\u2022') {
						pwdRegisterPassword.setEchoChar((char) 0);
					} else {
						pwdRegisterPassword.setEchoChar('\u2022');
					}
				}
			}
		});
		btnShowPassword_1.setIcon(new ImageIcon(SHOW_PASSWORD_PATH));
		btnShowPassword_1.setBounds(226, 145, 21, 20);
		registerPane.add(btnShowPassword_1);
		contentPane.add(loginPane);

		btnCreateAccount = new JButton("Log In");
		btnCreateAccount.setText("Sign Up");
		btnCreateAccount.setBounds(42, 252, 200, 43);
		registerPane.add(btnCreateAccount);

		btnAlreadyHaveAccount = new JButton("Already have an account? Login");
		btnAlreadyHaveAccount.setBounds(10, 311, 264, 23);
		registerPane.add(btnAlreadyHaveAccount);

		txtName = new JTextField();
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtName.getText().equals("Name")) {
					txtName.setText(null);
					txtName.requestFocus();
					removePlaceholderStyle(txtName);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtName.getText().length() == 0) {
					addPlaceholderStyle(txtName);
					txtName.setText("Name");
				}
			}
		});
		txtName.setText("Name");
		txtName.setBounds(57, 40, 170, 30);
		registerPane.add(txtName);
		txtName.setColumns(10);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Student", "Faculty", "Non Faculty", "Visitor" }));
		comboBox.setBounds(57, 210, 170, 30);
		registerPane.add(comboBox);

		txtpnSelectAccountType = new JLabel();
		txtpnSelectAccountType.setBackground(new Color(240, 240, 240));
		txtpnSelectAccountType.setText("Select Account Type");
		txtpnSelectAccountType.setBounds(57, 185, 170, 20);
		registerPane.add(txtpnSelectAccountType);

		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtEmail.getText().equals("Email")) {
					txtEmail.setText(null);
					txtEmail.requestFocus();
					removePlaceholderStyle(txtEmail);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtEmail.getText().length() == 0) {
					addPlaceholderStyle(txtEmail);
					txtEmail.setText("Email");
				}
			}
		});

		txtpnWelcome = new JLabel("", SwingConstants.CENTER);
		txtpnWelcome.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnWelcome.setBackground(new Color(240, 240, 240));
		txtpnWelcome.setText("Welcome");
		txtpnWelcome.setBounds(0, 20, 284, 20);
		loginPane.add(txtpnWelcome);
		txtEmail.setText("Email");
		txtEmail.setToolTipText("Email");
		txtEmail.setBounds(57, 70, 170, 30);
		loginPane.add(txtEmail);
		txtEmail.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (Arrays.equals(pwdPassword.getPassword(), new char[] { 'P', 'a', 's', 's', 'w', 'o', 'r', 'd' })) {
					pwdPassword.setText(null);
					pwdPassword.requestFocus();
					pwdPassword.setEchoChar('\u2022');
					removePlaceholderStyle(pwdPassword);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (pwdPassword.getPassword().length == 0) {
					addPlaceholderStyle(pwdPassword);
					pwdPassword.setText("Password");
					pwdPassword.setEchoChar((char) 0);
				}
			}
		});
		pwdPassword.setText("Password");
		pwdPassword.setToolTipText("Password");
		pwdPassword.setBounds(57, 130, 170, 30);
		pwdPassword.setEchoChar((char) 0);
		loginPane.add(pwdPassword);

		btnShowPassword = new JButton();
		btnShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pwdPassword.getForeground() != Color.gray) {
					if (pwdPassword.getEchoChar() == '\u2022') {
						pwdPassword.setEchoChar((char) 0);
					} else {
						pwdPassword.setEchoChar('\u2022');
					}
				}
			}
		});
		btnShowPassword.setIcon(new ImageIcon(SHOW_PASSWORD_PATH));
		btnShowPassword.setBounds(226, 135, 21, 20);
		loginPane.add(btnShowPassword);

		btnLogin = new JButton("Log In");
		btnLogin.setBounds(42, 200, 200, 43);
		loginPane.add(btnLogin);

		btnRegister = new JButton("Don't have an account? Sign Up");
		btnRegister.setBounds(10, 311, 264, 23);
		loginPane.add(btnRegister);

		txtpnAccountNotFound = new JLabel();
		txtpnAccountNotFound.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtpnAccountNotFound.setForeground(new Color(255, 0, 0));
		txtpnAccountNotFound.setBackground(new Color(240, 240, 240));
		txtpnAccountNotFound.setText("Account not found");
		txtpnAccountNotFound.setBounds(57, 100, 170, 20);
		txtpnAccountNotFound.setVisible(false);
		loginPane.add(txtpnAccountNotFound);

		txtpnPasswordIncorrect = new JLabel();
		txtpnPasswordIncorrect.setText("Password Incorrect");
		txtpnPasswordIncorrect.setForeground(Color.RED);
		txtpnPasswordIncorrect.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtpnPasswordIncorrect.setBackground(UIManager.getColor("Button.background"));
		txtpnPasswordIncorrect.setBounds(57, 160, 170, 20);
		txtpnPasswordIncorrect.setVisible(false);
		loginPane.add(txtpnPasswordIncorrect);
	}

	/**
	 * Getters and Setters for login page
	 */
	public JButton getLoginButton() {
		return btnLogin;
	}

	public JButton getRegisterButton() {
		return btnRegister;
	}

	public String getEmailInput() {
		return txtEmail.getText();
	}

	public String getPasswordInput() {
		return new String(pwdPassword.getPassword());
	}

	public JLabel getEmailWarning() {
		return txtpnAccountNotFound;
	}

	public JLabel getPasswordWarning() {
		return txtpnPasswordIncorrect;
	}

	/**
	 * Getters and Setters for registration page
	 */
	public JButton getCreateAccountButton() {
		return btnCreateAccount;
	}

	public JButton getAlreadyHaveAccountButton() {
		return btnAlreadyHaveAccount;
	}

	public String getRegisterEmailInput() {
		return txtRegisterEmail.getText();
	}

	public String getUserNameInput() {
		return txtName.getText();
	}

	public String getRegisterPasswordInput() {
		return new String(pwdRegisterPassword.getPassword());
	}

	public String getRegisterAccountType() {
		return comboBox.getSelectedItem().toString().toUpperCase().replaceAll("\\s+", "");
	}

}
