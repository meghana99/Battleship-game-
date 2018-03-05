
/*
 * Server.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

public class Server {
	public static void main(String args[]) {
		try {
			Registry rmiRegistry = LocateRegistry.createRegistry(5050);

			Ocean o1 = new Ocean();
			Player p1 = new Player();
			Fleet f1 = new Fleet();

			Ocean o2 = new Ocean();
			Player p2 = new Player();
			Fleet f2 = new Fleet();

			rmiRegistry.rebind("oceanA", o1);
			rmiRegistry.rebind("playerA", p1);
			rmiRegistry.rebind("fleetA", f1);

			rmiRegistry.rebind("oceanB", o2);
			rmiRegistry.rebind("playerB", p2);
			rmiRegistry.rebind("fleetB", f2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
