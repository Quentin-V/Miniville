import java.util.*;

abstract class InitialiserPioche
{
	private static ArrayList<Etablissement> listEtab;
	private static ArrayList<Monument>      listMon;

	static ArrayList<Etablissement> listEtablissement()
	{
		listEtab = new ArrayList<>();

		listEtab.add(new Etablissement("Champ de blé",                1, "Agriculture",  1                ));
		listEtab.add(new Etablissement("Ferme",                       1, "Elevage",      2                ));
		listEtab.add(new Etablissement("Boulangerie",                 1, "Magasin",      2,   3));
		listEtab.add(new Etablissement("Café",                        1, "Restauration", 3                ));
		listEtab.add(new Etablissement("Supérette",                   2, "Magasin",      4                ));
		listEtab.add(new Etablissement("Forêt",                       3, "Ressources",   5                ));
		listEtab.add(new Etablissement("Stade",                       6, "Unique",       6                ));
		listEtab.add(new Etablissement("Chaîne de télévision",        7, "Unique",       6                ));
		listEtab.add(new Etablissement("Centre d'affaires",           8, "Unique",       6                ));
		listEtab.add(new Etablissement("Fromagerie",                  5, "Usine",        7                ));
		listEtab.add(new Etablissement("Fabrique de meubles",         3, "Usine",        8                ));
		listEtab.add(new Etablissement("Mine",                        6, "Ressources",   9                ));
		listEtab.add(new Etablissement("Restaurant",                  3, "Restauration", 9,  10));
		listEtab.add(new Etablissement("Verger",                      3, "Agriculture",  10               ));
		listEtab.add(new Etablissement("Marché de fruits et légumes", 2, "Marche",       11, 12));

		return listEtab;
	}

	static ArrayList<Monument> listMonument()
	{
		listMon = new ArrayList<>();

		listMon.add(new Monument("Gare",               4 ));
		listMon.add(new Monument("Centre commercial",  10));
		listMon.add(new Monument("Parc d'attractions", 16));
		listMon.add(new Monument("Tour radio",         22));

		return listMon;

	}
}
