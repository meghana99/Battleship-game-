
/*
 * Player_Remote.java
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

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public interface Player_Remote extends Remote {

	public void setName(String name) throws RemoteException;

	public void Player_Init(Ocean_Remote o, String name, char ltr, char s_ltr, IOHandler_Remote io)
			throws RemoteException;

	public void createTheFleet(JFrame mainframe, Button[][] b) throws Exception;

	public String getName() throws RemoteException;

	public Ocean_Remote getOcean() throws RemoteException;

	public boolean isOnline() throws RemoteException;

	public void setOnline() throws RemoteException;

	public IOHandler_Remote getIO() throws RemoteException;

	public void exerciseTurn(Player_Remote vs) throws Exception;

	public void getLocation(IOHandler_Remote iox) throws Exception;

	public void switchTurn() throws Exception;

	public boolean checkWin() throws Exception;

	boolean checkValid(int r, int c) throws RemoteException;

	public void setJOcean(JPanel jOcean) throws RemoteException;

	public JPanel getJOcean() throws RemoteException;

	public int getPi() throws RemoteException;

	public void setPi(int pi) throws RemoteException;
}
