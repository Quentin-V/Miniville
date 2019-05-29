import java.util.ArrayList;
import java.util.Scanner;

class CUI {

    private static Controleur ctrl;

    private Joueur joueurActif;

    CUI(Controleur ctrl) {
        CUI.ctrl = ctrl;
    }

    void start() {
        int nbJoueurs = 0;
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

        this.joueurActif = joueurActif;

        System.out.print("Tour de " + joueurActif.getNom() + " : \n\n");

        System.out.println("Votre main : ");

        afficherMain();
    }

    void afficherDe(int[] resultatDe) {
        if(resultatDe[1] == 0) {
            System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0]);
        }else {System.out.println(joueurActif.getNom() + ", vous avez fait : " + resultatDe[0] + " et " + resultatDe[1] );}
    }

    boolean veutJouer2Des() {
        if(joueurActif.getNomMonumentsConstruits().contains("Gare")) {
            System.out.print("Voulez vous lancer deux dés (O/N) : ");
            while(true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    char choix = sc.next().charAt(0);
                    sc.close();
                    if(Character.toLowerCase(choix) == 'o') {
                        return true;
                    }
                    break;
                } catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !");}
            }

            System.out.println();

        }
        return false;
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
        for(Etablissement etab : ctrl.getPioche()) {
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
                for(Etablissement etab : ctrl.getPioche()) {
                    if(etab.getNom().equals(proposes.get(choix))) {
                        joueurActif.ajouteMain(etab);
                        ctrl.removeFromPioche(etab);
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

    boolean veutRelancer() {
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
                        return true;
                    }
                    break;
                } catch(Exception e) {System.out.println("Erreur, veuillez saisir 'O' ou 'N' !"); e.printStackTrace();}
            }
        }
        return false;
    }

    void annoncerDouble() {
        System.out.println("Vous avez fait un double ! Vous allez rejouer à la fin de votre tour.");
    }

    void afficherMain() {
        ArrayList<Carte> cartesUniques = new ArrayList<>();



        for (Carte carteUnique: cartesUniques) {
            int nbCetteCarte = 0;
            for(Carte c : joueurActif.getMain()) {
                if(c.equals(carteUnique)) {
                    nbCetteCarte++;
                }
            }
            if(carteUnique instanceof Monument) {
                System.out.println(carteUnique);
            } else {System.out.println(nbCetteCarte + " | " + carteUnique.getNom());}
        }
    }

}
