import java.util.ArrayList;
import java.util.Scanner;

abstract class EffetCarte {

	static void activerEffet(Controleur ctrl, int[] resultatDe) {

		int resultat = resultatDe[0] + resultatDe[1];

		switch(resultat) {
			case 1:
				champDeBle(ctrl);
				break;
			case 2:
				ferme(ctrl);
				boulangerie(ctrl);
				break;
			case 3:
				cafe(ctrl); // Ordre important car on paye avant de recevoir l'argent
				boulangerie(ctrl);
				break;
			case 4:
				superette(ctrl);
				break;
			case 5:
				foret(ctrl);
				break;
			case 6:
				stade(ctrl);
				chaineDeTele(ctrl);
				centreDaffaires(ctrl);
				break;
			case 7:
				fromagerie(ctrl);
				break;
			case 8:
				fabriqueDeMeubles(ctrl);
				break;
			case 9:
				restaurant(ctrl); // Ordre important car on paye avant de recevoir l'argent
				mine(ctrl);
				break;
			case 10:
				restaurant(ctrl); // Ordre important car on paye avant de recevoir l'argent
				verger(ctrl);
				break;
			case 11:
			case 12:
				marcheDeFruitsEtLegumes(ctrl);
		}

	}



	private static void champDeBle(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Champ de blé") ) {
					j.ajouterArgent(1);
				}
			}
		}
	}

	private static void ferme(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Ferme") ) {
					j.ajouterArgent(1);
				}
			}
		}
	}

	private static void boulangerie(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Boulangerie") ) {
					j.ajouterArgent(1);
				}
			}
		}
	}

	private static void cafe(Controleur ctrl) {
		ArrayList<Joueur> listeJoueurs = ctrl.getListeJoueurs();
		Joueur joueurActif = ctrl.getJoueurActif();

		for(int i = listeJoueurs.indexOf(joueurActif) + 1; i < listeJoueurs.size(); i++) { // On va chercher les joueurs dans l'ordre
			Joueur j = listeJoueurs.get(i);
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Café") && joueurActif.getArgent() > 0 ) {
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
				}
			}
		}
		for(int i = 0; i < listeJoueurs.indexOf(joueurActif); i++) { // On repars du début de la liste jusqu'au joueur
			Joueur j = listeJoueurs.get(i);
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Café") && joueurActif.getArgent() > 0 ) {
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
				}
			}
		}
	}

	private static void superette(Controleur ctrl) {
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Supérette")) {
				ctrl.getJoueurActif().ajouterArgent(3);
			}
		}
	}

	private static void foret(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Forêt") ) {
					j.ajouterArgent(1);
				}
			}
		}
	}

	private static void stade(Controleur ctrl) {
		for( Carte c : ctrl.getJoueurActif().getMain() ) {
			if( c.getNom().equals("Stade") ) {
				for( Joueur j : ctrl.getListeJoueurs() ) {
					if(j != ctrl.getJoueurActif()) {
						if( j.getArgent() >= 2 ) {
							j.ajouterArgent(-2);
							ctrl.getJoueurActif().ajouterArgent(2);
						} else if( j.getArgent() >= 1 ) {
							j.ajouterArgent(-1);
							ctrl.getJoueurActif().ajouterArgent(1);
						}
					}
				}
			}
		}
	}

	private static void chaineDeTele(Controleur ctrl) {

		for( Carte c : ctrl.getJoueurActif().getMain() ) {
			if( c.getNom().equals("Chaîne de télévision") ) {
				System.out.println("Saisissez le nom du joueur à qui vous voulez voler 5 pièces :");
				for(Joueur j : ctrl.getListeJoueurs()) {
					if( j != ctrl.getJoueurActif() ) {
						String nomJoueur = String.format("%20s", j.getNom());
						String nbPieces = String.format("%2d", j.getArgent());
						System.out.println("\t" + nomJoueur + " | " + nbPieces + " pièces");
						while(true) {
							try {
								Scanner sc = new Scanner(System.in);
								String choix = sc.next();
								for( Joueur j2 : ctrl.getListeJoueurs() ) {
									if( j2.getNom().equals(choix) ) {
										if(j2.getArgent() >= 5) {
											j2.ajouterArgent(-5);
											ctrl.getJoueurActif().ajouterArgent(5);
										}else if(j2.getArgent() >= 4) {
											j2.ajouterArgent(-4);
											ctrl.getJoueurActif().ajouterArgent(4);
										}else if(j2.getArgent() >= 3) {
											j2.ajouterArgent(-3);
											ctrl.getJoueurActif().ajouterArgent(3);
										}else if(j2.getArgent() >= 2) {
											j2.ajouterArgent(-2);
											ctrl.getJoueurActif().ajouterArgent(2);
										}else if(j2.getArgent() >= 1) {
											j2.ajouterArgent(-1);
											ctrl.getJoueurActif().ajouterArgent(1);
										}
										break;
									} else {System.out.println("Le nom saisi ne correspond pas à un nom de joueur, veuillez réessayer.");}
								}
								sc.close();
							} catch(Exception e) {e.printStackTrace();}
						}
					}
				}
			}
		}
	}

	private static void centreDaffaires(Controleur ctrl) {

	} // A COMPLETER ICI

	private static void fromagerie(Controleur ctrl) {
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Fromagerie")) {
				int nbElevage = 0;
				for(Carte c2 : ctrl.getJoueurActif().getMain()) {
					if(c2 instanceof Etablissement && ((Etablissement) c2).getSymbole().equals("Elevage")) {
						nbElevage++;
					}
				}
				ctrl.getJoueurActif().ajouterArgent(nbElevage*3);
			}
		}
	}

	private static void fabriqueDeMeubles(Controleur ctrl) {
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Fabrique de meubles")) {
				int nbRessources = 0;
				for(Carte c2 : ctrl.getJoueurActif().getMain()) {
					if(c2 instanceof Etablissement && ((Etablissement) c2).getSymbole().equals("Ressources")) {
						nbRessources++;
					}
				}
				ctrl.getJoueurActif().ajouterArgent(nbRessources*3);
			}
		}
	}

	private static void mine(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Mine") ) {
					j.ajouterArgent(5);
				}
			}
		}
	}

	private static void restaurant(Controleur ctrl) {
		ArrayList<Joueur> listeJoueurs = ctrl.getListeJoueurs();
		Joueur joueurActif = ctrl.getJoueurActif();

		for(int i = listeJoueurs.indexOf(joueurActif) + 1; i < listeJoueurs.size(); i++) { // On va chercher les joueurs dans l'ordre
			Joueur j = listeJoueurs.get(i);
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Restaurant") && joueurActif.getArgent() >= 2 ) {
					joueurActif.ajouterArgent(-2);
					j.ajouterArgent(2);
				} else if(c.getNom().equals("Restaurant") && joueurActif.getArgent() >= 1) {
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
				}
			}
		}
		for(int i = 0; i < listeJoueurs.indexOf(joueurActif); i++) { // On repars du début de la liste jusqu'au joueur
			Joueur j = listeJoueurs.get(i);
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Restaurant") && joueurActif.getArgent() > 0 ) {
					joueurActif.ajouterArgent(-2);
					j.ajouterArgent(2);
				}
			}
		}
	}

	private static void verger(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Verger") ) {
					j.ajouterArgent(3);
				}
			}
		}
	}

	private static void marcheDeFruitsEtLegumes(Controleur ctrl) {
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Marché de fruits et légumes")) {
				int nbAgriculture = 0;
				for(Carte c2 : ctrl.getJoueurActif().getMain()) {
					if(c2 instanceof Etablissement && ((Etablissement) c2).getSymbole().equals("Agriculture")) {
						nbAgriculture++;
					}
				}
				ctrl.getJoueurActif().ajouterArgent(nbAgriculture*2);
			}
		}
	}

}
