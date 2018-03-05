
/*
 * Fleet_Remote.java
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

import javax.swing.JFrame;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public interface Fleet_Remote extends Remote {
	public boolean addShip(String s, Ocean_Remote o, int r, int c, int length, boolean h, JFrame mainframe)
			throws Exception;

	public void Fleet_Init(Ocean_Remote o, IOHandler_Remote io, JFrame mainframe, Button[][] b) throws Exception;

	boolean getShipPosition(String s, int r, int c, int length, boolean h, JFrame mainframe) throws Exception;
}
