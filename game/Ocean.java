
/*
 * Ocean.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.awt.Button;
import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

class Ocean extends UnicastRemoteObject implements Ocean_Remote {
	int r1, r2, c1, c2;
	char[][] ocean_matrix = null;
	char[][] player_matrix = null;
	IOHandler_Remote io = null;

	public Ocean() throws RemoteException {
		super();
	}

	public void Ocean_Init(int minRow, int minColumn, int maxRow, int maxColumn, IOHandler_Remote io)
			throws RemoteException {
		c1 = minColumn;
		c2 = maxColumn;
		r1 = minRow;
		r2 = maxRow;
		this.io = io;
		// Create two matrices
		// Ocean matrix to play (used by opposition player)
		// Player matrix to position ships
		ocean_matrix = new char[r2 - r1 + 1][c2 - c1 + 1];
		player_matrix = new char[r2 - r1 + 1][c2 - c1 + 1];

		for (int i = 0; i < r2 - r1 + 1; i++)
			for (int j = 0; j < c2 - c1 + 1; j++) {
				ocean_matrix[i][j] = '.';
				player_matrix[i][j] = '_';
			}
	}

	// Displays ocean matrix for opposition
	public void displayOcean() throws RemoteException {
		io.print("  ");
		for (int i = 0; i < c2 - c1 + 1; i++)
			io.print(String.format("%-3s ", i + c1 + ""));
		io.println("");
		for (int i = 0; i < r2 - r1 + 1; i++) {
			io.print(String.format("%-3s ", i + r1 + ""));
			for (int j = 0; j < c2 - c1 + 1; j++)
				io.print(String.format("%-3s ", ocean_matrix[i][j]));
			io.println("");
		}
	}

	public void displayJOcean(Button[][] b) throws RemoteException {

		for (int i = 0; i < r2 - r1 + 1; i++) {
			// io.print(String.format("%-3s ", i + r1 + ""));
			for (int j = 0; j < c2 - c1 + 1; j++) {
				if (ocean_matrix[i][j] == 'o')
					b[i][j].setBackground(Color.RED);
				else if (ocean_matrix[i][j] == 'x')
					b[i][j].setBackground(Color.DARK_GRAY);
				else
					b[i][j].setBackground(Color.CYAN);
			}

		}
	}

	// Displays player matrix for positioning ships
	public void displayPlayerMatrix() throws RemoteException {
		io.print("  ");
		for (int i = 0; i < c2 - c1 + 1; i++)
			io.print(String.format("%-3s ", i + c1 + ""));
		io.println("");
		for (int i = 0; i < r2 - r1 + 1; i++) {
			io.print(String.format("%-3s ", i + r1 + ""));
			for (int j = 0; j < c2 - c1 + 1; j++)
				io.print(String.format("%-3s ", player_matrix[i][j]));
			io.println("");
		}
	}

	public void displayJPlayerMatrix(Button[][] b) throws RemoteException {

		for (int i = 0; i < r2 - r1 + 1; i++) {

			for (int j = 0; j < c2 - c1 + 1; j++) {
				if (player_matrix[i][j] == 'o')
					b[i][j].setBackground(Color.GREEN);
				else if (player_matrix[i][j] == 'x')
					b[i][j].setBackground(Color.RED);
				else
					b[i][j].setBackground(Color.CYAN);
			}

		}
	}

	// Returns ocean coordinates to caller
	public int[] getCoordinates() throws RemoteException {
		int[] co = { r1, r2, c1, c2 };
		return co;
	}

	public char[][] getPlayerMatrix() throws RemoteException {
		return player_matrix;
	}

	public char[][] getOceanMatrix() throws RemoteException {
		return ocean_matrix;
	}

	// Places ship in the player matrix
	public void placeShip(Ship s) throws RemoteException {
		int fixedIndex;
		int startIndex;
		if (s.isHorizontal()) {
			fixedIndex = s.getX() - r1;
			startIndex = s.getY() - c1;
			for (int i = startIndex; i < startIndex + s.getLength(); i++) {
				player_matrix[fixedIndex][i] = 'O';
				io.println("O at " + fixedIndex + "-" + i);
			}
		} else {
			fixedIndex = s.getY() - c1;
			startIndex = s.getX() - r1;
			for (int i = startIndex; i < startIndex + s.getLength(); i++) {
				player_matrix[i][fixedIndex] = 'O';
				io.println("O at " + fixedIndex + "-" + i);
			}
		}

	}

	// Marks a position as hit in the ocean matrix
	public boolean markHit(int r, int c, Ocean_Remote o) throws RemoteException {
		r = r - r1;
		c = c - c1;
		if ((o.getPlayerMatrix())[r][c] == 'O') {

			io.println("Ship hit! You get a free turn...");
			ocean_matrix[r][c] = 'O'; // Copy hit
										// part
										// of the
			// ship to ocean matrix
			return true; // Check if all ships are sunk i.e, player won
		}
		ocean_matrix[r][c] = 'X';
		io.println("Miss..");
		return false;
	}

	// Check if player has won the game
	public boolean checkWin() throws RemoteException {
		int count = 0;
		for (int i = 0; i < r2 - r1 + 1; i++)
			for (int j = 0; j < c2 - c1 + 1; j++) {
				if (ocean_matrix[i][j] == 'O')
					count++;
			}
		// Total count of O = 5+4+3+2 = 14
		if (count == 14)
			return true;
		return false;
	}

	// Checks if ship can be placed in the given position
	public boolean checkPosition(int r, int c, int l, boolean h) throws RemoteException {
		r = r - r1;
		c = c - c1;
		if (h) {
			l = l + c;
			// Check validity for 8 surrounding vertices of a point & for every
			// point of the ship
			while (c < l) {
				if (c - 1 >= 0 && player_matrix[r][c - 1] == 'O')
					return false;
				if (r - 1 >= 0 && c - 1 >= 0 && player_matrix[r - 1][c - 1] == 'O')
					return false;
				if (r - 1 >= 0 && player_matrix[r - 1][c] == 'O')
					return false;
				if (r - 1 >= 0 && c + 1 <= c2 && player_matrix[r - 1][c + 1] == 'O')
					return false;
				if (c + 1 <= c2 && player_matrix[r][c + 1] == 'O')
					return false;
				if (r + 1 <= r2 && c + 1 <= c2 && player_matrix[r + 1][c + 1] == 'O')
					return false;
				if (r + 1 <= r2 && player_matrix[r + 1][c] == 'O')
					return false;
				if (r + 1 <= r2 && c - 1 >= 0 && player_matrix[r + 1][c - 1] == 'O')
					return false;
				c++;
			}
		} else {
			l = l + r;
			while (r < l) {
				if (c - 1 >= 0 && player_matrix[r][c - 1] == 'O')
					return false;
				if (r - 1 >= 0 && c - 1 >= 0 && player_matrix[r - 1][c - 1] == 'O')
					return false;
				if (r - 1 >= 0 && player_matrix[r - 1][c] == 'O')
					return false;
				if (r - 1 >= 0 && c + 1 <= c2 && player_matrix[r - 1][c + 1] == 'O')
					return false;
				if (c + 1 <= c2 && player_matrix[r][c + 1] == 'O')
					return false;
				if (r + 1 <= r2 && c + 1 <= c2 && player_matrix[r + 1][c + 1] == 'O')
					return false;
				if (r + 1 <= r2 && player_matrix[r + 1][c] == 'O')
					return false;
				if (r + 1 <= r2 && c - 1 >= 0 && player_matrix[r + 1][c - 1] == 'O')
					return false;
				r++;
			}
		}
		return true;
	}
}
