
public class Monument extends Carte{

	boolean construit;

	Monument(String nom, int coutConstruction) {
		super(nom, coutConstruction);
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

}
