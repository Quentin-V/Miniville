import java.util.*;

class Etablissement extends Carte {

	private ArrayList<Integer> numerosActivation;
	private String symbole;


	Etablissement(String nom, int coutConstruction, String symbole, int numActivation1, int numActivation2) {
		super(nom, coutConstruction);
		this.numerosActivation = new ArrayList<>();
		this.numerosActivation.add(numActivation1);
		this.numerosActivation.add(numActivation2);
		this.symbole = symbole;
	}

	Etablissement(String nom, int coutConstruction, String symbole, int numeroActivation) {
		super(nom, coutConstruction);
		this.numerosActivation = new ArrayList<>();
		this.numerosActivation.add(numeroActivation);
		this.symbole = symbole;
	}

	String getNumerosActivation() {

		String retour;

		if(numerosActivation.size() == 2) {
			retour = numerosActivation.get(0).toString() + " et " + numerosActivation.get(1).toString();
		} else { retour = numerosActivation.get(0).toString(); }

		return retour;

	}

	String getSymbole() {
		return symbole;
	}

}
