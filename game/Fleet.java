
/*
 * Fleet.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.awt.Button;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

class Fleet extends UnicastRemoteObject implements Fleet_Remote {
	Ship[] ships = new Ship[4];
	int r, c;
	int co[];
	boolean h;
	IOHandler_Remote io = null;

	public Fleet() throws RemoteException {
		super();
	}

	public boolean addShip(String s, Ocean_Remote o, int r, int c, int length, boolean h, JFrame mainframe)
			throws Exception {

		boolean added = false;
		co = o.getCoordinates();
		if ((getShipPosition(s, r, c, length, h, mainframe)) && ((o.checkPosition(r, c, length, h) == true))) {
			ships[0] = new Ship(r, c, length, h, s);
			o.placeShip(ships[0]);
			o.displayPlayerMatrix();
			o.displayOcean();
			added = true;
			// break;

		} else

		{
			JOptionPane.showMessageDialog(mainframe, s + " can't be placed!");

		}
		return added;

	}

	public void Fleet_Init(Ocean_Remote o, IOHandler_Remote io, JFrame mainframe, Button[][] b) throws Exception {
	}

	// Get ship position and also check if they are within ocean boundaries
	public boolean getShipPosition(String s, int r, int c, int l, boolean h, JFrame mainframe) throws Exception {

		if (h) {
			if (r < co[0] || r > co[1] || c < co[2] || c + l - 1 > co[3])
				return false;
		} else {
			if (r < co[0] || r + l - 1 > co[1] || c < co[2] || c > co[3])
				return false;
		}
		return true;
	}
}
