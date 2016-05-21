package com.cyatophilum.projetmenu;
/**
 * Classe permet d'obtenir les favoris de l'utilisateur
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ButtonFavoris extends AppCompatActivity {

    AlertDialog alertDialog;//si l'utilisateur n'est pas connecté
    private ListView lvProduct;//ListView ou sera stocké le contenue de mPlaceList
    private ListPlaceAdapter adapter;//
    private List<Place> mPlaceList;//liste des lieux
    private DatabaseHelper mDBHelper;//objet permettant d'effectuer les requêtes
    private static String donnees_favoris;//id des lieux présents dans la table favoris
    private static int position_item_click_favoris;//pour sélectionner l'item cliqué

    /**
     * Constructeur de l'activité
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris); // choix du layout activity_favoris

        lvProduct = (ListView) findViewById(R.id.list_favoris);
        mDBHelper = new DatabaseHelper(this);//instanciation de l'objet

        String username = ButtonConnexion.getUser_id();
        String type = "favoris";

        //si l'utilisateur n'est pas connecté
        if (ButtonConnexion.getUser_id() == "") {
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Pour afficher vos favoris, il faut vous connecter");
            alertDialog.show();
        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
        setDonnees_favoris(BackgroundWorker.getResultat_favoris());
        //Toast.makeText(ButtonFavoris.this, getDonnees_favoris(), Toast.LENGTH_LONG).show();

        //appel de la requete SQL et mis à jour de la ListView
        mPlaceList = mDBHelper.favoris();
        adapter = new ListPlaceAdapter(this, mPlaceList);
        lvProduct.setAdapter(adapter);


        /**Méthode permettant d'obtenir l'id de l'item cliqué en fonction de sa position dans la liste
         * @param AdapterView.OnItemClickListener()
         */
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPosition_item_click(position);
                Intent intent = new Intent(ButtonFavoris.this, FavorisAvance.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Renvoie les identifiants des favoris stockés sur MySQL
     *
     * @return donnees_favoris
     */
    public static String getDonnees_favoris() {
        return donnees_favoris;
    }

    /**
     * Modifie la variable static donnees_favoris en lui donnant les id des favoris
     *
     * @param s les données
     */
    public static void setDonnees_favoris(String s) {
        donnees_favoris = s;
    }

    /**
     * Renvoie la position de l'item cliqué
     *
     * @return position_item_click_favoris
     */
    public static int getPosition_item_click() {
        return position_item_click_favoris;
    }

    /**
     * Modifie la position de l'item cliqué
     *
     * @param v la position
     */
    public void setPosition_item_click(int v) {
        position_item_click_favoris = v;
    }
}

