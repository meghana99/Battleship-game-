
/*
 * IOHandler_Remote.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public interface IOHandler_Remote extends Remote {
	public void println(String s) throws RemoteException;

	public void print(String s) throws RemoteException;

	public String readLine() throws RemoteException;
}
