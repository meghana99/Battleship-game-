package hw13;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleShipGUI {

	public static void main(String args[]) {
		String input_name = JOptionPane.showInputDialog("Enter your name:");

		String input_minrow = JOptionPane.showInputDialog("Enter the number of rows :");
		String input_mincol = JOptionPane.showInputDialog("Enter the number of colums :");

		int minrow = Integer.parseInt(input_minrow);
		int mincol = Integer.parseInt(input_mincol);

		JFrame mainframe = new JFrame();
		mainframe.setLayout(new BorderLayout());

		mainframe.setVisible(true);
		mainframe.setSize(500, 500);
		mainframe.isResizable();

		JPanel ocean = new JPanel();

		ocean.setLayout(new GridLayout(minrow, mincol));

		for (int i = 0; i < minrow; i++) {
			for (int j = 0; j < mincol; j++) {
				Button b = new Button("");
				ocean.add(b);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (b.getBackground() == Color.green)
							b.setBackground(Color.red);
						else
							b.setBackground(Color.green);
						
					}
				});
			}
		}

		JPanel control = new JPanel();
		control.add(new JButton("Connect"));
		control.add(new JButton("Disconnect"));

		mainframe.add(ocean, BorderLayout.NORTH);
		mainframe.add(control, BorderLayout.SOUTH);

	}

}
