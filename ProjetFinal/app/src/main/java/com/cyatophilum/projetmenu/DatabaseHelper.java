package com.cyatophilum.projetmenu;
/**
 * Classe permet de gérer toute la partie SQLite
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "basefinale.sqlite";
    private static final String DBLOCATION = "data/data/com.example.a.projetfinal/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static ArrayList<Integer> list_id = new ArrayList<>();

    /**
     * Constructeur de la classe
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    /**
     * @return DBNAME nom de la base de données
     */
    public static String getDbname() {
        return DBNAME;
    }

    /**
     * @return DBLOCATION chemin d'accès à la base de données
     */
    public static String getDBLOCATION() {
        return DBLOCATION;
    }

    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Cette méthode ouvre la base de données
     */
    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Cette méthode ferme la base de données
     */
    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    /**
     * Méthode permettant à l'utilisateur d'effectuer une recherche dans la base de données
     *
     * @return liste des lieux répondant aux critères
     */
    public List<Place> getListPlace() {

        String lechoix = "";
        String choixcategorie = ButtonRecherche.getCategorie();
        String choixville = ButtonRecherche.getVille();
        lechoix = ButtonRecherche.getNom();

        lechoix = requete(lechoix);
        choixcategorie = requete(choixcategorie);
        choixville = requete(choixville);

        Place place;
        List<Place> placeList = new ArrayList<>();
        openDatabase();

        Cursor cursor;

        if (!lechoix.isEmpty() && !choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and commune =" + "'" + choixville + "'" + " and type=" + "'" + choixcategorie + "'", null);
        } else if (lechoix.isEmpty() && !choixcategorie.equals("Categorie") && choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where type=" + "'" + choixcategorie + "'", null);
        } else if (lechoix.isEmpty() && choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where commune=" + "'" + choixville + "'", null);
        } else if (lechoix.isEmpty() && !choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where commune =" + "'" + choixville + "'" + " and type=" + "'" + choixcategorie + "'", null);
        } else if (!lechoix.isEmpty() && !choixcategorie.equals("Categorie") && choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and type=" + "'" + choixcategorie + "'", null);
        } else if (!lechoix.isEmpty() && choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and commune =" + "'" + choixville + "'", null);
        } else if (!lechoix.isEmpty() && choixcategorie.equals("Categorie") && choixville.equals("Ville"))
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%')", null);
        else
            cursor = mDatabase.rawQuery("select * from lieu_lyon", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
            placeList.add(place);
            cursor.moveToNext();
        }
        cursor.close();

        closeDatabase();
        return placeList;


    }

    /**
     * Cette méthode permet de selectionner le lieu sur lequel on a cliqué dans le résultat de la recherche
     * et dont on souhaite obtenir les informations détaillées
     *
     * @return
     */
    public List<Place> lieuAvance() {

        String lechoix = "";
        String choixcategorie = ButtonRecherche.getCategorie();
        String choixville = ButtonRecherche.getVille();
        lechoix = ButtonRecherche.getNom();

        lechoix = requete(lechoix);
        choixcategorie = requete(choixcategorie);
        choixville = requete(choixville);

        int i = 0;

        Place place;
        List<Place> placeList = new ArrayList<>();
        openDatabase();

        Cursor cursor;


        if (!lechoix.isEmpty() && !choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and commune =" + "'" + choixville + "'" + " and type=" + "'" + choixcategorie + "'", null);
        } else if (lechoix.isEmpty() && !choixcategorie.equals("Categorie") && choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where type=" + "'" + choixcategorie + "'", null);
        } else if (lechoix.isEmpty() && choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where commune=" + "'" + choixville + "'", null);
        } else if (lechoix.isEmpty() && !choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where commune =" + "'" + choixville + "'" + " and type=" + "'" + choixcategorie + "'", null);
        } else if (!lechoix.isEmpty() && !choixcategorie.equals("Categorie") && choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and type=" + "'" + choixcategorie + "'", null);
        } else if (!lechoix.isEmpty() && choixcategorie.equals("Categorie") && !choixville.equals("Ville")) {
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%') and commune =" + "'" + choixville + "'", null);
        } else if (!lechoix.isEmpty() && choixcategorie.equals("Categorie") && choixville.equals("Ville"))
            cursor = mDatabase.rawQuery("select * from lieu_lyon" + " where upper(nom) like upper('" + lechoix + "%')", null);
        else
            cursor = mDatabase.rawQuery("select * from lieu_lyon", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i != ButtonRecherche.getPosition_item_click()) {
            cursor.moveToNext();
            i++;
        }
        i = cursor.getInt(0);
        cursor.close();

        cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id=" + i, null);
        cursor.moveToFirst();
        place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
        placeList.add(place);
        cursor.close();


        closeDatabase();
        return placeList;


    }

    /**
     * Cette méthode permet de selectionner le lieu sur lequel on a cliqué dans le séjour
     * et dont on souhaite obtenir les informations détaillées
     *
     * @return placeList
     */
    public List<Place> productavance_sejour() {

        int i = 0;

        Place place;
        List<Place> placeList = new ArrayList<>();

        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonSejour.getDonnees_sejour() + ")", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i != ButtonSejour.getPosition_item_click()) {
            cursor.moveToNext();
            i++;
        }
        i = cursor.getInt(0);
        cursor.close();

        cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id=" + i, null);
        cursor.moveToFirst();
        place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
        placeList.add(place);

        cursor.close();
        closeDatabase();

        return placeList;


    }

    /**
     * Cette méthode permet de selectionner le lieu sur lequel on a cliqué dans l'historique
     * et dont on souhaite obtenir les informations détaillées
     *
     * @return placeList
     */
    public List<Place> productavance_historique() {

        int i = 0;

        Place place;
        List<Place> placeList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonHistorique.getDonnees_historique() + ")", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i != ButtonHistorique.getPosition_item_click()) {
            cursor.moveToNext();
            i++;
        }
        i = cursor.getInt(0);
        cursor.close();

        cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id=" + i, null);
        cursor.moveToFirst();
        place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
        placeList.add(place);

        cursor.close();
        closeDatabase();

        return placeList;


    }

    /**
     * Cette méthode permet de selectionner le lieu sur lequel on a cliqué dans les favoris
     * et dont on souhaite obtenir les informations détaillées
     *
     * @return placeList
     */
    public List<Place> productavance_favoris() {

        int i = 0;

        Place place;
        List<Place> placeList = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonFavoris.getDonnees_favoris() + ")", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i != ButtonFavoris.getPosition_item_click()) {
            cursor.moveToNext();
            i++;
        }
        i = cursor.getInt(0);
        cursor.close();

        cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id=" + i, null);
        cursor.moveToFirst();
        place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
        placeList.add(place);

        cursor.close();
        closeDatabase();

        return placeList;


    }

    /**
     * Cette méthode affiche le séjour aléatoire obtenue grâce à la méthode proposerSejour
     *
     * @param v
     * @return placeList
     */
    public List<Place> sejour_aleatoire(int v) {

        Place place;

        List<Place> placeList = new ArrayList<>();

        openDatabase();

        String donnees = proposerSejour(v);

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + donnees + ")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
            placeList.add(place);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return placeList;


    }

    /**
     * Cette méthode sélectionne les lieux présents dans les favoris de l'utilisateur
     * uniquement s'il est connecté
     *
     * @return placeList
     */
    public List<Place> favoris() {

        Place place;
        List<Place> placeList = new ArrayList<>();
        if (ButtonConnexion.getUser_id() != "") {
            openDatabase();

            Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonFavoris.getDonnees_favoris() + ")", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
                placeList.add(place);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();

        }
        return placeList;
    }

    /**
     * Cette méthode sélectionne les lieux présents dans l'historique de l'utilisateur
     * uniquement s'il est connecté
     *
     * @return placeList
     */
    public List<Place> historique() {


        Place place;
        List<Place> placeList = new ArrayList<>();

        if (ButtonConnexion.getUser_id() != "") {
            openDatabase();

            Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonHistorique.getDonnees_historique() + ")", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
                placeList.add(place);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();
        }


        return placeList;
    }

    /**
     * Cette méthode sélectionne les lieux présents dans le séjour de l'utilisateur
     * uniquement s'il est connecté
     *
     * @return placeList
     */
    public List<Place> sejour() {

        Place place;
        List<Place> placeList = new ArrayList<>();

        if (ButtonConnexion.getUser_id() != "") {
            openDatabase();

            Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + ButtonSejour.getDonnees_sejour() + ")", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                place = new Place(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15));
                placeList.add(place);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();

        }
        return placeList;
    }

    /**
     * Cette méthode permet de double les apostrophes et ainsi évite le programme de planter
     *
     * @param s
     * @return chaine
     */
    public static String requete(String s) {
        String chaine = "";

        if (!s.contains("'")) return s;
        else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '\'') chaine = chaine + "'";
                chaine = chaine + s.charAt(i);
            }
        }
        return chaine;
    }

    /**
     * Cette méthode permet d'obtenir les communes dans l'ordre décroissant de leur nombre total
     *
     * @return list
     */
    public ArrayList<String> getAllProvinces() {

        ArrayList<String> list = new ArrayList<>();
        // Ouverture de la base pour lecture
        SQLiteDatabase db = this.getReadableDatabase();
        // Début de la transaction
        db.beginTransaction();

        try {
            list.add("Ville");
            String selectQuery = "select commune, count(*) as co from lieu_lyon group by commune order by co desc;";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Ajout de la ville à la list
                    String pname = cursor.getString(cursor.getColumnIndex("commune"));
                    list.add(pname);
                }
            }
            db.setTransactionSuccessful();


        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return list;

    }

    /**
     * Cette méthode permet de récupérer l'id d'un lieu que l'on souhaite placer dans ses favoris ou son séjour ou dans l'historique
     *
     * @param nom
     * @return res
     */
    public String ajouterSejourFavorisHistorique(String nom) {
        openDatabase();
        String res = "";
        nom = requete(nom);
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where nom = '" + nom + "'", null);
        cursor.moveToFirst();
        res = "" + cursor.getInt(0);
        cursor.close();

        closeDatabase();
        return res;


    }

    /**
     * Cette méthode propose un séjour tant qu'il n'est pas valide
     *
     * @param v
     * @return
     */
    public String proposerSejour(int v) {

        String resultat = "";
        int nbr_lieux;


        do {
            Cursor cursor;
            list_id.clear();
            resultat = "";
            String donnees = genererNombre();
            nbr_lieux = 0;


            int alternateur = (int) (Math.random() * ((2 - 1) + 1) + 1);

            if (alternateur == 1)
                cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + donnees + ")", null);

            else
                cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + donnees + ") order by id desc", null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast() && nbr_lieux <= v * 2) {

                resultat = cursor.getInt(0) + ", " + resultat;
                list_id.add(cursor.getInt(0));
                nbr_lieux++;
                cursor.moveToNext();
            }

            resultat = resultat.substring(0, resultat.length() - 2);

            cursor.close();

        } while (verification_sejour(resultat, v) == false);

        return resultat;


    }

    /**
     * cette méthode permet de générer des nombres pour la méthode qui génère un voyage aléatoirement
     *
     * @return string contenant l'id des lieux
     */
    public String genererNombre() {

        String ch = "";
        int alternateur = (int) (Math.random() * (4 - 1) + 1);
        int i;

        if (alternateur == 1)
            for (i = 0; i < 500; i++) ch = (int) (Math.random() * ((1665 - 1) + 1) + 1) + ", " + ch;


        if (alternateur == 2)
            for (i = 0; i < 500; i++)
                ch = (int) (Math.random() * ((3329 - 1664) + 1) + 1664) + ", " + ch;


        if (alternateur == 3)
            for (i = 0; i < 500; i++)
                ch = (int) (Math.random() * ((4993 - 3329) + 1) + 3329) + ", " + ch;

        String resultat = ch.substring(0, ch.length() - 2);

        return resultat;
    }

    /**
     * Cette méthode vérifie si un séjour est valide au regard de certain critère
     *
     * @param data
     * @param duree
     * @return boolean
     */
    public boolean verification_sejour(String data, int duree) {

        int nb_restaurant = 0;
        int nb_hotel = 0;
        int nb_musee = 0;
        int nb_commerce = 0;
        int total = 0;

        Cursor cursor = mDatabase.rawQuery("SELECT count(*), type FROM lieu_lyon where id in (" + data + ") group by type", null);

        cursor.moveToFirst();
        total++;
        while (!cursor.isAfterLast()) {

            if ((cursor.getString(1).contains("tellerie") || cursor.getString(1).contains("bergement")))
                nb_hotel = cursor.getInt(0);

            else if (cursor.getString(1).contains("tauration"))
                nb_restaurant = cursor.getInt(0);

            else if (cursor.getString(1).contains("trimoine")) nb_musee = cursor.getInt(0);

            else if (cursor.getString(1).contains("mmerce")) nb_commerce = cursor.getInt(0);

            cursor.moveToNext();
            total++;
        }

        cursor.close();

        if (duree > 3) {
            if (nb_restaurant != duree || nb_hotel != 1 || nb_musee < duree / 2)
                return false;
        } else {
            if (nb_restaurant != duree | nb_hotel != 1)
                return false;
        }

        return true;


    }

    /**
     * res le résultat pour lancer la géolocalisation et google maps
     *
     * @return
     */
    public String cheminSejour() {

        String res = "";

        openDatabase();

        String donnees = BackgroundWorker.getResultat_sejour();

        Cursor cursor;

        cursor = mDatabase.rawQuery("SELECT * FROM lieu_lyon where id in (" + donnees + ")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res = res + cursor.getString(4) + " " + cursor.getString(6) + "/";
            cursor.moveToNext();
        }
        cursor.close();


        closeDatabase();

        return res;

    }

    /**
     * @return list_id
     */
    public static ArrayList<Integer> renvoie_id_sejour_alea() {
        return list_id;
    }

}
