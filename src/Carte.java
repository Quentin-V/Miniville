class Carte {

	private String nom;
	private int coutConstruction;


	Carte(String nom, int coutConstruction) {

		this.nom = nom;
		this.coutConstruction = coutConstruction;

	}

	String getNom() { return nom; }

	int getCoutConstruction() {
		return coutConstruction;
	}

	public String toString() {
		return getNom();
	}

	boolean equals(Carte carte) {
		return getNom().equals(carte.getNom());
	}
}
