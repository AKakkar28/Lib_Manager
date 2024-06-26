package org.team4.view.user;

import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import org.team4.model.user.User;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class RequestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JLabel lblAuthor;
	private JFormattedTextField textFieldISBN;
	private JTextField textFieldEdition;
	private JComboBox<String> comboBoxBookType;
	private JButton submitButton; 
	private User user;
	
	public RequestPanel(User user) {
		setBounds(100, 100, 1160, 740);
		setLayout(null);
		
		JLabel lblRequestBook = new JLabel("Request Book");
		lblRequestBook.setBounds(500, 5, 160, 26);
		lblRequestBook.setHorizontalAlignment(SwingConstants.CENTER);
		lblRequestBook.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(lblRequestBook);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(372, 197, 66, 13);
		add(lblTitle);

		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(467, 193, 353, 19);
		add(textFieldTitle);
		textFieldTitle.setColumns(10);

		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(372, 312, 50, 13);
		lblISBN.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblISBN);

		textFieldAuthor = new JTextField();
		textFieldAuthor.setBounds(467, 247, 353, 19);
		textFieldAuthor.setColumns(10);
		add(textFieldAuthor);

		JLabel lblEdition = new JLabel("Edition:");
		lblEdition.setBounds(372, 368, 66, 13);
		add(lblEdition);

		lblAuthor = new JLabel("Author:");
		lblAuthor.setHorizontalAlignment(SwingConstants.LEFT);
		lblAuthor.setBounds(372, 248, 66, 19);
		add(lblAuthor);

		comboBoxBookType = new JComboBox<>();
		comboBoxBookType.setModel(new DefaultComboBoxModel<String>(new String[] {"TextBook", "General Book"}));
		comboBoxBookType.setBounds(467, 423, 139, 21);
		add(comboBoxBookType);

		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
		
	    textFieldISBN = new JFormattedTextField();
	    textFieldISBN.setColumns(10);
		textFieldISBN.setBounds(467, 308, 353, 19);
		add(textFieldISBN);

		textFieldEdition = new JTextField();
		textFieldEdition.setColumns(10);
		textFieldEdition.setBounds(467, 364, 139, 19);
		add(textFieldEdition);

		JLabel lblBookType = new JLabel("Book Type:");
		lblBookType.setBounds(372, 426, 78, 13);
		add(lblBookType);

		submitButton = new JButton("Submit");
		submitButton.setBounds(521, 511, 117, 29);
		add(submitButton);
	}

	public JTextField getTextFieldTitle() {
		return textFieldTitle;
	}

	public JTextField getTextFieldAuthor() {
		return textFieldAuthor;
	}

	public JTextField getTextFieldISBN() {
		return textFieldISBN;
	}

	public JTextField getTextFieldEdition() {
		return textFieldEdition;
	}
	
	public JComboBox<String> getComboBoxBookType() {
		return comboBoxBookType;
	}

	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public User getUser() {
		return user;
	}

}
