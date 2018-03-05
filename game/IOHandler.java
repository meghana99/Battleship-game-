
/*
 * IOHandler.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public class IOHandler extends UnicastRemoteObject implements IOHandler_Remote {

	public IOHandler() throws RemoteException {
		super();
	}

	public void println(String s) throws RemoteException {
		System.out.println(s);
	}

	public void print(String s) throws RemoteException {
		System.out.print(s);
	}

	public String readLine() throws RemoteException {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
