import java.util.*;

class Joueur {

	private String nom;
	private ArrayList<Carte> main;
	private int argent;


	Joueur(String nom) {

		this.nom = nom;
		main =  new ArrayList<Carte>();
		main.addAll(InitialiserPioche.listMonument());
		main.add(new Etablissement("Champ de blé", 1, "Agriculture", 1));
		main.add(new Etablissement("Boulangerie", 1, "Magasin", 2, 3));
		this.argent = 20;

	}

	String getNom() {return this.nom;}

	ArrayList<Carte> getMain() {
		return new ArrayList<>(main);
	}

	ArrayList<String> getNomMonumentsConstruits() {
		ArrayList<String> nomMonumentsConstruits = new ArrayList<>();

		for(Carte c : main) {
			if(c instanceof Monument && ((Monument)c).estConstruit())
				nomMonumentsConstruits.add(c.getNom());
		}

		return nomMonumentsConstruits;
	}

	int getArgent() {
		return argent;
	}

	void ajouterArgent(int argentAAjouter) {
		this.argent += argentAAjouter;
	}

	void ajouteMain(Carte c) { this.main.add(c); }
}
