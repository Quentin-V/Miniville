import java.util.*;

public class Joueur {

	private String nom;
	private ArrayList<Carte> main;
	private int argent;


	Joueur(String nom) {

		this.nom = nom;
		main =  new ArrayList<Carte>();
		for(Monument m : InitialiserPioche.listMonument()) main.add(m);
		main.add(new Etablissement("Champ de blé", 1, "Agriculture", 1));
		main.add(new Etablissement("Boulangerie", 1, "Magasin", 2, 3));
		this.argent = 20;

	}

	String getNom() {return this.nom;}

	ArrayList<Carte> getMain() {
		ArrayList<Carte> mainCopie = new ArrayList<>();

		for(Carte c : main) {
			mainCopie.add(c);
		}

		return mainCopie;
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
