
public class Monument extends Carte{

	boolean construit;
	String effet;

	Monument(String nom, int coutConstruction, String effet) {
		super(nom, coutConstruction);
		this.effet = effet;
		construit = false;
	}

	boolean estConstruit() {
		return construit;
	}

	@Override
	public String toString() {
		String retour = "";
		if(estConstruit()) {
			     retour += String.format("%20s", getNom()) + " | (  construit  )";
		} else { retour += String.format("%20s", getNom()) + " | (non construit)"; }
		return retour;
	}

	String getEffet() {
		return effet;
	}

	void setConstruit(boolean construit) {
		this.construit = construit;
	}
}
