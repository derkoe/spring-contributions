package org.springframework.contributions.impl;

public class Egg {

	private final Hen byHen;

	public Egg(Hen byHen) {
		super();
		
		this.byHen = byHen;
	}

	public Hen getByHen() {
		return byHen;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Egg of " + byHen.getName();
	}
	
}
