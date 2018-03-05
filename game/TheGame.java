
/*
 * TheGame.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

class TheGame extends UnicastRemoteObject {
	static int minColumn = -1, minRow = -1, maxColumn = -1, maxRow = -1;
	static IOHandler_Remote io = null;
	static boolean flag = true;
	static Button b[][];
	static String ship_names[] = { "Carrier", "Battleship", "Cruiser", "Destroyer" };
	static int stage = 0;
	static Registry rmiRegistry;
	static int ind = 0;
	static String name = "";
	static boolean play = true;
	static String[] p = { "A", "B" };
	static int pMeID = 0, pVsID = 0;

	public TheGame() throws Exception {
		super();
	}

	public static void main(String[] args) throws Exception {
		try {
			rmiRegistry = LocateRegistry.getRegistry("localhost", 5050);
			try {
				rmiRegistry.lookup("p");
				pMeID = 1;
				pVsID = 0;
				io = new IOHandler();
				rmiRegistry.rebind("ioB", io);

				JFrame mainframe = new JFrame();
				JPanel ocean = new JPanel();
				JButton Bconn = new JButton("Connect");
				JButton BdisConn = new JButton("Disconnect");
				Ocean_Remote aOcean = (Ocean_Remote) rmiRegistry.lookup("oceanA");
				Ocean_Remote bOcean = (Ocean_Remote) rmiRegistry.lookup("oceanB");
				int[] co = aOcean.getCoordinates();

				name = JOptionPane.showInputDialog("Enter your name:");
				mainframe.setTitle(name + "'s Interface");
				Fleet_Remote f = (Fleet_Remote) rmiRegistry.lookup("fleetB");
				initializeFrame(mainframe, ocean, Bconn, BdisConn, bOcean, f, co[1], co[3]);

				bOcean.Ocean_Init(minRow, minColumn, maxRow, maxColumn, io);

				Bconn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							rmiRegistry.lookup("ioA");
							JOptionPane.showMessageDialog(mainframe, "Connected!");
						} catch (Exception E) {
							JOptionPane.showMessageDialog(mainframe, "Player 1 not running!");
						}
					}
				});

				BdisConn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(mainframe, name + " Disconnected");
						mainframe.setVisible(false);
					}
				});

				Player_Remote playerB = (Player_Remote) rmiRegistry.lookup("playerB");
				playerB.setName(name);
				playerB.setJOcean(ocean);
				playerB.Player_Init(bOcean, "Player B", 'B', 'b', io);
				playerB.createTheFleet(mainframe, b);
				flag = false;
				playerB.setOnline();

			} catch (Exception E) {
				pMeID = 0;
				pVsID = 1;
				io = new IOHandler();
				rmiRegistry.rebind("p", io);
				rmiRegistry.rebind("ioA", io);

				JFrame mainframe = new JFrame();
				JPanel ocean = new JPanel();
				JButton Bconn = new JButton("Connect");
				JButton BdisConn = new JButton("Disconnect");
				Ocean_Remote aOcean = (Ocean_Remote) rmiRegistry.lookup("oceanA");

				Fleet_Remote f = (Fleet_Remote) rmiRegistry.lookup("fleetA");

				name = JOptionPane.showInputDialog("Enter your name:");
				String input_minrow = JOptionPane.showInputDialog("Enter the number of rows :");
				String input_mincol = JOptionPane.showInputDialog("Enter the number of colums :");
				mainframe.setTitle(name + "'s Interface");

				int row = Integer.parseInt(input_minrow);
				int col = Integer.parseInt(input_mincol);

				initializeFrame(mainframe, ocean, Bconn, BdisConn, aOcean, f, row, col);

				aOcean.Ocean_Init(minRow, minColumn, maxRow, maxColumn, io);
				Bconn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							rmiRegistry.lookup("ioB");
							JOptionPane.showMessageDialog(mainframe, "Connected!");
						} catch (Exception E) {
							JOptionPane.showMessageDialog(mainframe, "Player 2 not running!");
						}
					}
				});

				BdisConn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(mainframe, name + " Disconnected");
						mainframe.setVisible(false);
					}
				});

				Player_Remote playerA = (Player_Remote) rmiRegistry.lookup("playerA");
				playerA.setName(name);
				playerA.setJOcean(ocean);
				playerA.Player_Init(aOcean, "Player A", 'A', 'a', io);
				playerA.createTheFleet(mainframe, b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void initializeFrame(JFrame mainframe, JPanel ocean, JButton BConn, JButton BDisConn, Ocean_Remote o,
			Fleet_Remote f, int row, int col) throws Exception {

		mainframe.setLayout(new BorderLayout());
		mainframe.setVisible(true);
		mainframe.setSize(500, 500);
		mainframe.isResizable();
		getOceanCoordinates(mainframe, row, col);

		ocean.setLayout(new GridLayout(maxRow, maxColumn));

		b = new Button[maxRow][maxColumn];

		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				b[i][j] = new Button((i + 1) + "," + (j + 1));
				ocean.add(b[i][j]);
			}
		}

		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				Button btmp = b[i][j];
				b[i][j].setBackground(Color.CYAN);
				btmp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						try {
							if (stage == 0) {

								String[] splits = btmp.getLabel().split(",");
								Boolean h = false;
								String hstr = JOptionPane.showInputDialog("Place it horizontally? (Y/N):");
								if (hstr.equals("Y") || hstr.equals("y"))
									h = true;
								boolean t = f.addShip(ship_names[ind], o, Integer.parseInt(splits[0]),
										Integer.parseInt(splits[1]), 5 - ind, h, mainframe);

								if (t) {
									if (!h) {
										for (int z = 0; z < 5 - ind; z++) {
											b[Integer.parseInt(splits[0]) - 1 + z][Integer.parseInt(splits[1]) - 1]
													.setBackground(Color.green);
										}
									} else {
										for (int z = 0; z < 5 - ind; z++) {
											b[Integer.parseInt(splits[0]) - 1][Integer.parseInt(splits[1]) - 1 + z]
													.setBackground(Color.green);
										}
									}
									ind++;
									if (ind == 4) {
										stage = 1;
										Player_Remote pMe = (Player_Remote) rmiRegistry.lookup("player" + p[pMeID]);
										Player_Remote pVs = (Player_Remote) rmiRegistry.lookup("player" + p[pVsID]);

										refreshBoard(b, pVs.getOcean().getOceanMatrix());
									}
								}
							} else {
								if (play) {

									Player_Remote pMe = (Player_Remote) rmiRegistry.lookup("player" + p[pMeID]);
									Player_Remote pVs = (Player_Remote) rmiRegistry.lookup("player" + p[pVsID]);
									String[] splits = btmp.getLabel().split(",");
									if ((pMe.getPi() == 0 && pMeID == 0) || (pMe.getPi() == 1 && pMeID == 1)) {
										exerciseTurn(pMe, pVs, mainframe, Integer.parseInt(splits[0]),
												Integer.parseInt(splits[1]), b);

										char[][] dispOcean = pMe.getOcean().getOceanMatrix();

									} else {
										JOptionPane.showMessageDialog(mainframe, "It's " + pVs.getName() + "'s Turn!");
									}

								} else {

								}

							}
						} catch (Exception E) {
							E.printStackTrace();
						}
					}
				});
			}
		}

		JPanel control = new JPanel();
		control.add(BConn);
		control.add(BDisConn);

		mainframe.add(ocean, BorderLayout.NORTH);
		mainframe.add(control, BorderLayout.SOUTH);
	}

	private static void refreshBoard(Button[][] b, char[][] dispOcean) throws Exception {
		io.println(dispOcean[0].length + " " + dispOcean.length);
		for (int i = 0; i < dispOcean[0].length; i++) {
			for (int j = 0; j < dispOcean.length; j++) {
				if (dispOcean[i][j] == 'O')
					b[i][j].setBackground(Color.RED);
				else if (dispOcean[i][j] == 'X')
					b[i][j].setBackground(Color.DARK_GRAY);
				else
					b[i][j].setBackground(Color.CYAN);
			}
		}
	}

	static public void exerciseTurn(Player_Remote p, Player_Remote vs, JFrame mainframe, int r, int c, Button[][] b)
			throws Exception {

		if (p.getOcean().markHit(r, c, vs.getOcean())) { // Mark hit on ocean
			// matrix
			JOptionPane.showMessageDialog(mainframe, "Hit!");
			b[r - 1][c - 1].setBackground(Color.RED);
			if (p.checkWin()) { // Check if player won
				JOptionPane.showMessageDialog(mainframe, name + " Wins!");
				System.exit(0);
			}
		} else {
			b[r - 1][c - 1].setBackground(Color.LIGHT_GRAY);
			JOptionPane.showMessageDialog(mainframe, "Miss!");
			p.switchTurn(); // Flag is changed only if its a miss
		}

		p.getOcean().displayOcean();
	}

	static void getOceanCoordinates(JFrame mainframe, int row, int col) throws Exception {

		while (row < 0 || col < 0) {

			JOptionPane.showMessageDialog(mainframe, "Invalid Input");
			String input_minrow = JOptionPane.showInputDialog("Enter the number of rows :");
			String input_mincol = JOptionPane.showInputDialog("Enter the number of colums :");
			row = Integer.parseInt(input_minrow);
			col = Integer.parseInt(input_mincol);

		}
		// Get top right
		minRow = 1;
		minColumn = 1;
		maxRow = row;
		maxColumn = col;

	}

}
