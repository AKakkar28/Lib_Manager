package org.team4.view.user.search.results;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.team4.controller.results.MagazineController;
import org.team4.maintaindb.MaintainDatabase;
import org.team4.maintaindb.MaintainMagazine;
import org.team4.model.items.Magazine;
import org.team4.model.user.User;
import org.team4.view.user.search.info.MagazineItemPanel;
import org.team4.view.user.search.models.MagazineTableModel;


public class MagazineResultsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private String query;
	private JFrame window;
	private User user;


	public MagazineResultsPanel() {
		setBounds(100, 100, 788, 520);
		setLayout(null);

	}

	public MagazineResultsPanel(String query, JFrame window, User user) {

		this.window = window;
		this.query = query;
		this.user = user;

		setBounds(100, 100, 788, 520);
		setLayout(new BorderLayout());
		JLabel label = new JLabel("Magazine Search Results for Title: " + query);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label.setBounds(324, 5, 239, 26);
		add(label, BorderLayout.PAGE_START);

	}


	public void addSearchResults() {
		ArrayList<Magazine> results = MaintainDatabase.getInstance().getMagazineDatabase().searchMagazines(query);
		MagazineTableModel model = new MagazineTableModel(results);

		if(results.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No results found.", "Search Error", JOptionPane.ERROR_MESSAGE);
			window.dispose();
			return;
		}
		else {
			table = new JTable(model);
			table.setDefaultEditor(Object.class, null);
			add(new Panel().add(new JScrollPane(table)));

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						int row = table.rowAtPoint(e.getPoint());
						int col = table.columnAtPoint(e.getPoint());
						if (col == 0) {
							Magazine magazine = MaintainMagazine.getInstance().searchExactMagazineByISBN(table.getValueAt(row, 4).toString());
							JFrame itemInfoFrame = new JFrame(magazine.getTitle());
							System.out.println(magazine);

							MagazineItemPanel magazinePanel = new MagazineItemPanel(itemInfoFrame, magazine, user);
							new MagazineController(magazinePanel, user);

							magazinePanel.showItemInfo();
							itemInfoFrame.setContentPane(new JScrollPane(magazinePanel));
							itemInfoFrame.setSize(300, 250);
							itemInfoFrame.setVisible(true);
						}
					}
				}
			});


			window.setContentPane(new JScrollPane(this));
			window.setSize(1200, 500);
			window.setVisible(true);
		}

	}

	public JFrame getWindow() {
		return window;
	}

}
