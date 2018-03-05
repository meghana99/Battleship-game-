
/*
 * Ship.java
 *
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 *
 */

import java.io.Serializable;

/**
 * @author Nikita Tribhuvan
 * @author Meghana Sathish
 */

class Ship implements Serializable {
	int r, c, length;
	boolean horizontal;
	String name;

	public Ship(int r, int c, int length, boolean horizontal, String name) {
		this.r = r;
		this.c = c;
		this.horizontal = horizontal;
		this.length = length;
		this.name = name;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public int getLength() {
		return length;
	}

	public int getX() {
		return r;
	}

	public int getY() {
		return c;
	}
}
