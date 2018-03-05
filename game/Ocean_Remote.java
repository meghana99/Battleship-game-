
/*
 * Ocean_Remote.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.awt.Button;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public interface Ocean_Remote extends Remote {
	public void Ocean_Init(int minRow, int minColumn, int maxRow, int maxColumn, IOHandler_Remote io)
			throws RemoteException;

	public void displayOcean() throws RemoteException;

	public void displayPlayerMatrix() throws RemoteException;

	public int[] getCoordinates() throws RemoteException;

	public char[][] getPlayerMatrix() throws RemoteException;

	public void placeShip(Ship s) throws RemoteException;

	public boolean markHit(int r, int c, Ocean_Remote o) throws RemoteException;

	public boolean checkWin() throws RemoteException;

	public boolean checkPosition(int r, int c, int l, boolean h) throws RemoteException;

	public void displayJOcean(Button[][] b) throws RemoteException;

	public void displayJPlayerMatrix(Button[][] b) throws RemoteException;

	public char[][] getOceanMatrix() throws RemoteException;
}
