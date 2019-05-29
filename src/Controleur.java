import java.util.*;

public class Controleur {

	private ArrayList<Joueur>        listeJoueurs;
	private int                      nbJoueurs;
	private Joueur                   joueurActif;
	private ArrayList<Etablissement> pioche;

	private CUI cui;

	private Controleur() {

		pioche = InitialiserPioche.listEtablissement();
		listeJoueurs = new ArrayList<>();
		joueurActif = null;

		initialiserPartie();

		do {
			jouer();
		} while(!partieFinie());

	}

	private void initialiserPartie() {

		cui = new CUI(this);
		cui.start();

	}

	void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	private boolean partieFinie() {
		for(Carte c : joueurActif.getMain()) {
			if(c instanceof Monument) {
				if( !((Monument)c).estConstruit() ){
					return false;
				}
			}
		}

		return true;
	}

	private void jouer() {

		for(int i = 0; i < listeJoueurs.size(); i++) {

			joueurActif = listeJoueurs.get(i);

			cui.lancerTour(joueurActif);

			int[] resultatDe = lancerDe();

			cui.afficherDe(resultatDe);

			if(cui.veutRelancer()) {
				lancerDe();
			} // Relance le dé

			if(joueurActif.getNomMonumentsConstruits().contains("Parc d'attractions") && resultatDe[0] == resultatDe[1]) {
				cui.annoncerDouble();
				i--;
			}

			activerEffets(resultatDe);

			if(veutConstruire()) {
				construire();
			}

			if(partieFinie()) break;

		}

	}

	private int[] lancerDe() {

		int[] resultatDe = new int[2];

		resultatDe[0]  = (int) (Math.random() * 6 + 1);

		if(cui.veutJouer2Des()) {
			resultatDe[1] = (int) (Math.random() * 6 + 1);
		}

		return resultatDe;
	}

	boolean veutConstruire() {
		return cui.veutConstruire();
	}

	void construire() {
		cui.construire();
	}

	ArrayList<Etablissement> getPioche() {
		return new ArrayList<>(pioche);
	}

	void removeFromPioche(Etablissement etab) {
		pioche.remove(etab);
	}

	void activerEffets(int[] resultatDe) {
		EffetCarte.activerEffet(this, resultatDe);
	}

	ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	Joueur getJoueurActif() {
		return joueurActif;
	}

	public static void main(String[] args) {
		new Controleur();
	}

}
