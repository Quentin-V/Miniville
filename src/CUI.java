import java.util.ArrayList;
import java.util.Scanner;

class CUI {

    private static Controleur ctrl;

    private Joueur joueurActif;

    CUI(Controleur ctrl) {
        CUI.ctrl = ctrl;
    }

    void start() {
        int nbJoueurs;
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

        ctrl.setNbJoueurs(nbJoueurs);

        System.out.println("---------------------------------\n");


        ArrayList<Joueur> listeJoueurs = new ArrayList<>();

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
        ctrl.setListeJoueurs(listeJoueurs);
    }

    void lancerTour(Joueur joueurActif) {

        for(int i = 0; i < 100; i++) System.out.println();

        this.joueurActif = joueurActif;

        System.out.print("Tour de " + joueurActif.getNom() + " : \n\n");

        System.out.println("Votre main : ");

        afficherMain();
    }

    void afficherDe(int[] resultatDe) {
        if(resultatDe[1] == 0) {
            System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0]);
        }else {System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0] + " et " + resultatDe[1] + " (" + (resultatDe[0] + resultatDe[1]) + ")");}
    }

    boolean veutJouer2Des() {
        if(joueurActif.getNomMonumentsConstruits().contains("Gare")) {
            System.out.print("Voulez vous lancer deux dés (O/N) : ");
            while(true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    char choix = sc.next().charAt(0);
                    System.out.println();
                    return Character.toLowerCase(choix) == 'o';
                } catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !");}
            }
        }
        return false;
    }

    boolean veutConstruire() {
        if(joueurActif.getArgent() > 0) {
            System.out.print("Voulez vous construire (O/N) : ");
            while(true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    char choix = sc.next().charAt(0);
                    System.out.println();
                    return Character.toLowerCase(choix) == 'o';
                } catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !"); e.printStackTrace();}
            }
        }
        return false;
    }

    void construire() {

        int argent = joueurActif.getArgent();
        int indice = 1;
        ArrayList<Carte> proposes = new ArrayList<>();

        System.out.println("Vous avez " + argent + " pièces.");
        System.out.println("Liste des batiments que vous pouvez acheter : \n");
        for(Etablissement etab : ctrl.getPioche()) { // On affiche tous les établissements qui sont possible à acheter.
            if(etab.getCoutConstruction() <= argent && !etabEstEnregistre(etab, proposes)) {
                proposes.add(etab);
                String nomEtab    = String.format("%30s", etab.getNom());
                String coutEtab   = String.format("%9s",  etab.getCoutConstruction() + " pièces");
                String numActEtab = String.format("%.8s", etab.getNumerosActivation());
                String sIndice    = String.format("%2d",  indice);
                System.out.println("\t" + sIndice + " : " + nomEtab + " | " + coutEtab + " | Activé par : " + numActEtab);
                indice++;
            }
        }
        for(Carte c : joueurActif.getMain()) { // On affiche les monuments à la fin de la liste.
            if(c.getCoutConstruction() <= argent && c instanceof Monument && !((Monument) c).estConstruit()) {
                proposes.add(c);
                String nomEtab    = String.format("%30s", c.getNom());
                String coutEtab   = String.format("%9s" , c.getCoutConstruction() + " pièces");
                String effet      = String.format("%s"  , ((Monument) c).getEffet());
                String sIndice    = String.format("%2d" , indice);
                System.out.println("\t" + sIndice + " : " + nomEtab + " | " + coutEtab + " | Effet : " + effet);
                indice++;
            }
        }

        System.out.print("\nSaisissez le numéro du batiment que vous souhaitez acheter (-1 pour annuler) : ");
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

                Carte carteChoisie = proposes.get(choix);
                if(joueurActif.getArgent() >= carteChoisie.getCoutConstruction()) { // Vérification au cas où, même si ça a déjà été vérifié avant
                    if(carteChoisie instanceof Etablissement) {
                        joueurActif.ajouteMain(carteChoisie);
                        ctrl.removeFromPioche((Etablissement) carteChoisie);
                    } else {
                        ((Monument) carteChoisie).setConstruit(true);
                    }
                    ctrl.getJoueurActif().ajouterArgent(-1*carteChoisie.getCoutConstruction());
                    break;
                }
                System.out.println();
            }catch (Exception e) {
                System.out.print("Veuillez saisir un chiffre correspondant au batiment \n" +
                        "que vous voulez construire, -1 pour annuler : ");
            }
            if(choix >= 0 && choix < proposes.size()) break;
        }
        System.out.println();
    }

    boolean veutRelancer() {
        if(joueurActif.getNomMonumentsConstruits().contains("Tour radio")) {
            while(true) {
                System.out.print("Voulez vous relancer ces dés (O/N) : ");
                try {
                    Scanner sc = new Scanner(System.in);
                    char choixRelancer = sc.next().charAt(0);
                    System.out.println();
                    if(Character.toLowerCase(choixRelancer) == 'o') {
                        System.out.println("Vous relancez vos dés !");
                        return true;
                    }
                    return false;
                } catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !"); e.printStackTrace();}
            }
        }
        return false;
    }

    void annoncerDouble() {
        System.out.println("Vous avez fait un double ! Vous allez rejouer à la fin de votre tour.");
    }

    void afficherMain(Joueur j) {
        ArrayList<Carte> cartesUniques = new ArrayList<>();

        for(Carte c : j.getMain()) {
            if(!carteEstEnregistree(c,cartesUniques)) {
                cartesUniques.add(c);
            }
        }

        for (Carte carteUnique: cartesUniques) {
            int nbCetteCarte = 0;
            for(Carte c : j.getMain()) {
                if(c.equals(carteUnique)) {
                    nbCetteCarte++;
                }
            }
            if(carteUnique instanceof Monument) {
                System.out.println(carteUnique);
            } else if(carteUnique instanceof Etablissement) {
                String nomCarte = String.format("%-27s", carteUnique.getNom());
                System.out.println(nbCetteCarte + " | " + nomCarte + " | Activation : " + ((Etablissement) carteUnique).getNumerosActivation());
            }
        }
        System.out.println("\n\n");
    }

    private void afficherMain() {afficherMain(joueurActif);}

    void afficherVictoire() {
        for(int j = 0; j < 1000; j++) System.out.println();
        System.out.println("PARTIE FINIE ! C'est " + joueurActif.getNom() + " qui a gagné");
        System.out.println("Ecrivez WOW SUPER pour finir le programme et voir l'argent et la main de chaque joueur.");
        Scanner sc = new Scanner(System.in);
        sc.next();
        sc.close();
        for(Joueur j : ctrl.getListeJoueurs()) {
            System.out.println("\t" + j.getNom() + " : \n\tArgent : " +j.getArgent());
            afficherMain(j);
        }
    }

    void argentChange(Joueur j, String etab, int prevArgent) {
        int gain = j.getArgent() - prevArgent;
        System.out.println(j.getNom() + " a gagné " + gain + " pièce grâce à l'activation de " + etab);
    }

    void argentVole(Joueur joueurVole, Joueur joueurVoleur, String etab, int nbPieces) {
        System.out.println(joueurVoleur.getNom() + " a pris " + nbPieces + " pièce(s) à " + joueurVole.getNom() + " grâce à l'activation de " + etab);
    }

    private boolean carteEstEnregistree(Carte carte, ArrayList<Carte> lCartes) {
        for(Carte c : lCartes) {
            if(c.equals(carte)) {
                return true;
            }
        }
        return false;
    }

    private boolean etabEstEnregistre(Etablissement etab, ArrayList<Carte> lEtab) {
        for(Carte c : lEtab) {
            if(c.equals(etab)) {
                return true;
            }
        }
        return false;
    }

}
