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
			int argentJoueur = j.getArgent();
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Champ de blé") ) {
					j.ajouterArgent(1);
				}
			}
			if(argentJoueur != j.getArgent()) { ctrl.argentChange(j,"Champ de blé",argentJoueur); }
		}
	} // Fait

	private static void ferme(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			int argentJoueur = j.getArgent();
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Ferme") ) {
					j.ajouterArgent(1);
				}
			}
			if(argentJoueur != j.getArgent()) { ctrl.argentChange(j,"Ferme",argentJoueur); }
		}
	} // Fait

	private static void boulangerie(Controleur ctrl) {
		int argentJoueur = ctrl.getJoueurActif().getArgent();
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Boulangerie")) {
				ctrl.getJoueurActif().ajouterArgent(1);
			}
		}
		if(argentJoueur != ctrl.getJoueurActif().getArgent()) { ctrl.argentChange(ctrl.getJoueurActif(),"Boulangerie",argentJoueur); }
		piecesSupplementaires(ctrl, ctrl.getJoueurActif(),"Boulangerie");
	} // Fait

	private static void cafe(Controleur ctrl) {
		ArrayList<Joueur> listeJoueurs = ctrl.getListeJoueurs();
		Joueur joueurActif = ctrl.getJoueurActif();

		for(int i = listeJoueurs.indexOf(joueurActif) + 1; i < listeJoueurs.size(); i++) { // On va chercher les joueurs dans l'ordre
			Joueur j = listeJoueurs.get(i);
			int nbCafes = 0;
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Café") && joueurActif.getArgent() > 0 ) {
					nbCafes++;
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
					if(joueurActif.getArgent() >= 1) piecesSupplementaires(ctrl, j, "Café");
				}
			}
			if(nbCafes > 0) ctrl.argentVole(joueurActif, j, "Café", nbCafes);
		}
		for(int i = 0; i < listeJoueurs.indexOf(joueurActif); i++) { // On repars du début de la liste jusqu'au joueur
			Joueur j = listeJoueurs.get(i);
			int nbPiecesVoleesParCafe = 0;
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Café") && joueurActif.getArgent() > 0 ) {
					nbPiecesVoleesParCafe++;
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
					if(joueurActif.getArgent() >= 1) piecesSupplementaires(ctrl, j, "Café");
				}
			}
			if(nbPiecesVoleesParCafe > 0) ctrl.argentVole(joueurActif, j, "Café", nbPiecesVoleesParCafe);
		}
	} // Pas terminé

	private static void superette(Controleur ctrl) {
		int argentJoueur = ctrl.getJoueurActif().getArgent();
		for(Carte c : ctrl.getJoueurActif().getMain()) {
			if( c.getNom().equals("Supérette")) {
				ctrl.getJoueurActif().ajouterArgent(3);
			}
		}
		if(argentJoueur != ctrl.getJoueurActif().getArgent()) { ctrl.argentChange(ctrl.getJoueurActif(),"Superette",argentJoueur); }
		piecesSupplementaires(ctrl, ctrl.getJoueurActif(),"Supérette");
	} // Fait

	private static void foret(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			int argentJoueur = j.getArgent();
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Forêt") ) {
					j.ajouterArgent(1);
				}
			}
			if(argentJoueur != j.getArgent()) { ctrl.argentChange(j,"Forêt",argentJoueur); }
		}
	} // Fait

	private static void stade(Controleur ctrl) {
		for( Carte c : ctrl.getJoueurActif().getMain() ) {
			if( c.getNom().equals("Stade") ) {
				for( Joueur j : ctrl.getListeJoueurs() ) {
					if(j != ctrl.getJoueurActif()) {
						if( j.getArgent() >= 2 ) {
							j.ajouterArgent(-2);
							ctrl.getJoueurActif().ajouterArgent(2);
							ctrl.argentVole(j, ctrl.getJoueurActif(), "Stade", 2);
						} else if( j.getArgent() >= 1 ) {
							j.ajouterArgent(-1);
							ctrl.getJoueurActif().ajouterArgent(1);
							ctrl.argentVole(j, ctrl.getJoueurActif(), "Stade", 1);
						}
					}
				}
			}
		}
	} // Pas terminé

	private static void chaineDeTele(Controleur ctrl) {

		for( Carte c : ctrl.getJoueurActif().getMain() ) {
			if( c.getNom().equals("Chaîne de télévision") ) {
				System.out.println("Saisissez le nom du joueur à qui vous voulez voler 5 pièces (-1 pour ne pas voler) : ");
				for(Joueur j : ctrl.getListeJoueurs()) {
					if( j != ctrl.getJoueurActif() ) {
						String nomJoueur = String.format("%20s", j.getNom());
						String nbPieces = String.format("%2d", j.getArgent());
						System.out.println("\t" + nomJoueur + " | " + nbPieces + " pièces");
						while(true) {
							try {
								Scanner sc = new Scanner(System.in);
								String choix = sc.next();
								if(choix.equals(ctrl.getJoueurActif().getNom())) throw new IllegalArgumentException();
								if(choix.equals("-1")) return;
								for( Joueur j2 : ctrl.getListeJoueurs() ) {
									if( j2.getNom().equals(choix) ) {
										if(j2.getArgent() >= 5) {
											j2.ajouterArgent(-5);
											ctrl.getJoueurActif().ajouterArgent(5);
											ctrl.argentVole(j2, ctrl.getJoueurActif(), "Chaine de télé", 5);
										}else if(j2.getArgent() >= 4) {
											j2.ajouterArgent(-4);
											ctrl.getJoueurActif().ajouterArgent(4);
											ctrl.argentVole(j2, ctrl.getJoueurActif(), "Chaine de télé", 4);
										}else if(j2.getArgent() >= 3) {
											j2.ajouterArgent(-3);
											ctrl.getJoueurActif().ajouterArgent(3);
											ctrl.argentVole(j2, ctrl.getJoueurActif(), "Chaine de télé", 3);
										}else if(j2.getArgent() >= 2) {
											j2.ajouterArgent(-2);
											ctrl.getJoueurActif().ajouterArgent(2);
											ctrl.argentVole(j2, ctrl.getJoueurActif(), "Chaine de télé", 2);
										}else if(j2.getArgent() >= 1) {
											j2.ajouterArgent(-1);
											ctrl.getJoueurActif().ajouterArgent(1);
											ctrl.argentVole(j2, ctrl.getJoueurActif(), "Chaine de télé", 1);
										}
										return;
									}
								}
								System.out.println("Le nom saisi ne correspond pas à un nom de joueur, veuillez réessayer.");
							}
							catch(IllegalArgumentException e) {System.out.println("Vous ne pouvez pas vous voler des pièces");}
							catch(Exception e) {e.printStackTrace();}
						}
					}
				}
			}
		}
	} // Pas terminé

	private static void centreDaffaires(Controleur ctrl) {

		// VERIFIER SI LE JOUEUR A LA CARTE AU MOINS

		/*
		while(true) {
			System.out.print("Voulez vous échanger une carte avec un autre joueur (O|N) : ");
			try {
				Scanner sc = new Scanner(System.in);
				char choixRelancer = sc.next().charAt(0);
				System.out.println();
				if(Character.toLowerCase(choixRelancer) != 'n' && Character.toLowerCase(choixRelancer) != 'o') {
					throw new Exception();
				} else if(Character.toLowerCase(choixRelancer) == 'n') {
					return;
				} else {break;}
			} catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !"); e.printStackTrace();}
		}

		System.out.println("Voici les mains de chaque joueur : ");
		for(Joueur j : ctrl.getListeJoueurs()) {
			System.out.println("\t" + j.getNom() + " : ");
			ctrl.afficherMain(j);
		}

		for(Joueur j : ctrl.getListeJoueurs()) {
			if(!j.equals(ctrl.getJoueurActif())) System.out.println("\t" + j.getNom() + " | " + j.getArgent());
		}
		System.out.print("Ecrivez le nom du joueur à qui vous voulez échanger une carte : ");

		while(true) {
			try {
				Scanner sc = new Scanner(System.in);
				String choix = sc.next();
				if(choix.equals(ctrl.getJoueurActif().getNom())) throw new IllegalArgumentException();
				for(Joueur j2 : ctrl.getListeJoueurs()) {
					if(j2.getNom().equals(choix)) {
						// ICI AFFICHER LA MAIN ETC POUR ECHANGER LA CARTE
						return;
					}
					System.out.println("Le nom saisi ne correspond pas à un nom de joueur, veuillez réessayer.");
				}
			} catch(IllegalArgumentException e) {System.out.println("Vous ne pouvez pas vous échanger une carte.");}
			  catch(Exception e)                {e.printStackTrace();}
		}
		*/



	} // RIEN DE FAIT ICI

	private static void fromagerie(Controleur ctrl) {
		int argentJoueur = ctrl.getJoueurActif().getArgent();
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
		if(argentJoueur != ctrl.getJoueurActif().getArgent()) { ctrl.argentChange(ctrl.getJoueurActif(),"Fromagerie",argentJoueur); }
	} // Fait

	private static void fabriqueDeMeubles(Controleur ctrl) {
		int argentJoueur = ctrl.getJoueurActif().getArgent();
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
		if(argentJoueur != ctrl.getJoueurActif().getArgent()) { ctrl.argentChange(ctrl.getJoueurActif(),"Fabrique de meubles",argentJoueur); }
	} // Fait

	private static void mine(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			int argentJoueur = j.getArgent();
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Mine") ) {
					j.ajouterArgent(5);
				}
			}
			if(argentJoueur != j.getArgent()) { ctrl.argentChange(j,"Mine",argentJoueur); }
		}
	} // Fait

	private static void restaurant(Controleur ctrl) {
		ArrayList<Joueur> listeJoueurs = ctrl.getListeJoueurs();
		Joueur joueurActif = ctrl.getJoueurActif();

		for(int i = listeJoueurs.indexOf(joueurActif) + 1; i < listeJoueurs.size(); i++) { // On va chercher les joueurs dans l'ordre
			Joueur j = listeJoueurs.get(i);
			int nbPiecesPrisesParRestaurant = 0;
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Restaurant") && joueurActif.getArgent() >= 2 ) {
					nbPiecesPrisesParRestaurant += 2;
					joueurActif.ajouterArgent(-2);
					j.ajouterArgent(2);
					if(joueurActif.getArgent() >= 1) piecesSupplementaires(ctrl, j, "Restaurant");
				} else if(c.getNom().equals("Restaurant") && joueurActif.getArgent() >= 1) {
					nbPiecesPrisesParRestaurant++;
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
				}
			}
			if(nbPiecesPrisesParRestaurant > 0) ctrl.argentVole(joueurActif, j, "Restaurant", nbPiecesPrisesParRestaurant);
		}
		for(int i = 0; i < listeJoueurs.indexOf(joueurActif); i++) { // On repars du début de la liste jusqu'au joueur
			Joueur j = listeJoueurs.get(i);
			int nbPiecesPrisesParRestaurant = 0;
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Restaurant") && joueurActif.getArgent() > 0 ) {
					nbPiecesPrisesParRestaurant += 2;
					joueurActif.ajouterArgent(-2);
					j.ajouterArgent(2);
					if(joueurActif.getArgent() >= 1) piecesSupplementaires(ctrl, j, "Restaurant");
				} else if(c.getNom().equals("Restaurant") && joueurActif.getArgent() >= 1) {
					nbPiecesPrisesParRestaurant++;
					joueurActif.ajouterArgent(-1);
					j.ajouterArgent(1);
				}
			}
			if(nbPiecesPrisesParRestaurant > 0) ctrl.argentVole(joueurActif, j, "Restaurant", nbPiecesPrisesParRestaurant);
		}
	} // Pas terminé

	private static void verger(Controleur ctrl) {
		for(Joueur j : ctrl.getListeJoueurs()) {
			int argentJoueur = j.getArgent();
			for(Carte c : j.getMain()) {
				if( c.getNom().equals("Verger") ) {
					j.ajouterArgent(3);
				}
			}
			if(argentJoueur != j.getArgent()) { ctrl.argentChange(j,"Verger",argentJoueur); }
		}
	} // Fait

	private static void marcheDeFruitsEtLegumes(Controleur ctrl) {
		int argentJoueur = ctrl.getJoueurActif().getArgent();
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
		if(argentJoueur != ctrl.getJoueurActif().getArgent()) { ctrl.argentChange(ctrl.getJoueurActif(),"Marché de fuits et légumes",argentJoueur); }
	} // Fait

	private static void piecesSupplementaires(Controleur ctrl, Joueur unJoueur,String carteActivee) {
		if(unJoueur.getNomMonumentsConstruits().contains("Centre commercial")) {
			int nbPiecesBonus = 0;
			for(Carte c : unJoueur.getMain()) {
				if(c.getNom().equals(carteActivee)) nbPiecesBonus++;
			}
			if(nbPiecesBonus > 0) {
				unJoueur.ajouterArgent(nbPiecesBonus);
				ctrl.argentChange(unJoueur, "Centre commercial", unJoueur.getArgent() - nbPiecesBonus);
			}
		}
	} // Fait (Effet du centre commercial)

}
