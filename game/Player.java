
/*
 * Player.java
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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

class Player extends UnicastRemoteObject implements Player_Remote {
	static int pi = 0;
	Ocean_Remote o = null;
	char ltr;
	Fleet_Remote f = null;
	boolean flag = true;
	int r, c;
	IOHandler_Remote io = null;
	boolean online = false;
	String name = "";
	JPanel jOcean;

	public Player() throws RemoteException {
		super();
	}

	public int getPi() throws RemoteException{
		return this.pi;
	}

	public void setPi(int pi) throws RemoteException{
		this.pi=pi;
	}

	public void setJOcean(JPanel jOcean) throws RemoteException {
		this.jOcean = jOcean;
	}

	public JPanel getJOcean() throws RemoteException {
		return this.jOcean;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public void Player_Init(Ocean_Remote o, String name, char ltr, char s_ltr, IOHandler_Remote io)
			throws RemoteException {
		this.o = o;
		this.name = name;
		this.io = io;
		this.ltr = ltr;
	}

	public boolean isOnline() throws RemoteException {
		return online;
	}

	public void setOnline() throws RemoteException {
		this.online = true;
	}

	public void createTheFleet(JFrame mainframe, Button[][] b) throws Exception {
		io.println("Place ships in the fleet of " + name);
		Registry rmiRegistry = LocateRegistry.getRegistry("localhost", 5050);
		f = (Fleet_Remote) rmiRegistry.lookup("fleet" + ltr);
		f.Fleet_Init(o, io, mainframe, b);
	}

	public String getName() throws RemoteException {
		return name;
	}

	public Ocean_Remote getOcean() throws RemoteException {
		return o;
	}

	public IOHandler_Remote getIO() throws RemoteException {
		return io;
	}

	public void exerciseTurn(Player_Remote vs) throws Exception {
		// Display ocean matrix for the player
		this.getOcean().displayOcean();
		do {
			this.getIO().println(this.getName() + " : Enter coordinates to hit");
			getLocation(this.getIO());
		} while (!checkValid(r, c)); // Repeat till valid coordinates are
										// entered
		if (this.getOcean().markHit(r, c, vs.getOcean())) { // Mark hit on ocean
															// matrix
			if (this.checkWin()) { // Check if player won
				this.getIO().println(this.getName() + " Wins!");
				this.getIO().println("Game over...");
				System.exit(0);
			}
		} else
			switchTurn(); // Flag is changed only if its a miss
	}

	// Gets coordinates for player to hit
	public void getLocation(IOHandler_Remote iox) throws Exception {
		iox.print("Row : ");
		r = Integer.parseInt(iox.readLine());
		iox.print("Column : ");
		c = Integer.parseInt(iox.readLine());
	}

	public void switchTurn() throws Exception {
		if(this.getPi()==0)
			this.setPi(1);
		else
			this.setPi(0);
	}

	// Check win using ocean matrix of the player
	public boolean checkWin() throws Exception {
		return getOcean().checkWin();
	}

	// Checks if row and column are within ocean boundaries
	public boolean checkValid(int r, int c) throws RemoteException {
		int[] co = o.getCoordinates();
		if (r < co[0] || r > co[1] || c < co[2] || c > co[3]) {
			io.println("Invalid coordinates");
			return false;
		}
		return true;
	}
}
