import java.util.*;

public class Controleur {

	private ArrayList<Joueur>        listeJoueurs;
	private int                      nbJoueurs;
	private Joueur                   joueurActif;
	private ArrayList<Etablissement> pioche;

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

		while(true) {
			System.out.print("Nombre de joueurs : ");
			try {
				Scanner sc = new Scanner(System.in);
				nbJoueurs = sc.nextInt();
				System.out.println();
				if(nbJoueurs < 2) {
					System.out.println("Le nombre de joueurs doit être au moins égal  à 2 !");
				}else { break; }
			} catch(Exception e) {System.out.println("Erreur, veuillez saisir un entier représentant le nombre de joueurs.  "); }
		}

		System.out.println("---------------------------------\n");


		for(int i = 0; i < nbJoueurs; i++) {
			while(true) {
				System.out.print("Veuillez saisir le nom du joueur " + (i+1) +" :  ");
				try {
					Scanner sc = new Scanner(System.in);
					String nomJoueur = sc.next();
					listeJoueurs.add(new Joueur(nomJoueur));
					System.out.println();
					break;
				} catch(Exception e) {System.out.println("Erreur, on sait pas ce qui se passe désolé !"); e.printStackTrace();}
			}
		}
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

			System.out.print("Tour de " + joueurActif.getNom() + " : \n\n");

			System.out.println("Votre main : ");
			for(Carte c : joueurActif.getMain()) {
				System.out.println(c);
			}

			int[] resultatDe = lancerDe();
			if(resultatDe[1] == 0) {
				System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0]);
			}else {System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0] + " et " + resultatDe[1] );}

			if(joueurActif.getNomMonumentsConstruits().contains("Tour radio")) {
				while(true) {
					System.out.print("Voulez vous relancer ces dés (O/N) : ");
					try {
						Scanner sc = new Scanner(System.in);
						char choixRelancer = sc.next().charAt(0);
						sc.close();
						System.out.println();
						if(Character.toLowerCase(choixRelancer) == 'o') {
							System.out.println("Vous relancez vos dés !");
							lancerDe();
							if(resultatDe[1] == 0) {
								System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0]);
							}else {System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0] + " et " + resultatDe[1] );}
						}

						break;
					} catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !"); e.printStackTrace();}
				}
			}

			if(joueurActif.getNomMonumentsConstruits().contains("Parc d'attractions") && resultatDe[0] == resultatDe[1]) {
				System.out.println("Vous avez fait un double, vous allez donc rejouer.");
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
		if(joueurActif.getNomMonumentsConstruits().contains("Gare")) {
			System.out.print("Voulez vous lancer deux dés (O/N) : ");
			while(true) {
				try {
					Scanner sc = new Scanner(System.in);
					char choix = sc.next().charAt(0);
					sc.close();
					if(Character.toLowerCase(choix) == 'o') {
						resultatDe[1] = (int) (Math.random() * 6 + 1);
					}
					break;
				} catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !");}
			}

			System.out.println();

		}

		return resultatDe;
	}

	boolean veutConstruire() {
		if(joueurActif.getArgent() > 0) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Voulez vous construire (O/N) : ");
			while(true) {
				try {
					char choix = sc.next().charAt(0);
					if(Character.toLowerCase(choix) == 'o') {
						return true;
					}
					break;
				} catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !");}
			}
		}
		System.out.println();
		return false;
	}

	void construire() {

		int argent = joueurActif.getArgent();
		int indice = 1;
		ArrayList<String> proposes = new ArrayList<>();

		System.out.println("Vous avez " + argent + " pièces.");
		System.out.println("Liste des batiments que vous pouvez acheter : \n");
		for(Etablissement etab : pioche) {
			if(etab.getCoutConstruction() <= argent && !proposes.contains(etab.getNom())) {
				proposes.add(etab.getNom());
				String nomEtab    = String.format("%30s", etab.getNom());
				String coutEtab   = String.format("%9s",  etab.getCoutConstruction() + " pièces");
				String numActEtab = String.format("%.8s", etab.getNumerosActivation());
				String sIndice    = String.format("%2d",  indice);
				System.out.println("\t" + sIndice + " : " + nomEtab + " | " + coutEtab + " | Activé par : " + numActEtab);
			}
			indice++;
		}

		System.out.print("Saisissez le numéro du batiment que vous souhaitez acheter (-1 pour annuler) : ");
		while(true) {
			int choix = -1;
			try {
				Scanner sc = new Scanner(System.in);
				choix = sc.nextInt();
				if(choix > proposes.size()) {
					System.out.println("Le numéro saisi est trop grand.");
				} else if(choix < 0) {
					break;
				}
				choix--;
				for(Etablissement etab : pioche) {
					System.out.println("DEBUG");
					if(etab.getNom().equals(proposes.get(choix))) {
						joueurActif.ajouteMain(etab);
						pioche.remove(etab);
						break;
					}
				}
			}catch (Exception e) {
				System.out.print("Veuillez saisir un chiffre correspondant au batiment \n" +
						           "que vous voulez construire, -1 pour annuler.");
			}
			if(choix >= 0 && choix < proposes.size()) break;
		}
		System.out.println();
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
