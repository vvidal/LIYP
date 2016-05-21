package com.cyatophilum.projetmenu;
/**
 * Classe permet d'obtenir l'historique de l'utilisateur
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

public class ButtonHistorique extends AppCompatActivity {

    AlertDialog alertDialog; //si l'utilisateur n'est pas connecté
    private ListView lvProduct_historique; //ListView ou sera stocké le contenue de mPlaceList
    private ListPlaceAdapter adapter_historique;
    private List<Place> mPlaceList_historique; //liste des lieux
    private DatabaseHelper mDBHelper; //objet permettant d'effectuer les requêtes
    private static String donnees_historique; //id des lieux présents dans la table historique
    private static int position_item_click_historique; //pour sélectionner l'item cliqué

    /**
     * Constructeur de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique); // choix du layout activity_historique

        lvProduct_historique = (ListView)findViewById(R.id.list_historique);
        mDBHelper = new DatabaseHelper(this);

        String username=ButtonConnexion.getUser_id();
        String type = "historique";

        //si l'utilisateur n'est pas connecté
        if (ButtonConnexion.getUser_id() == ""){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Pour afficher votre historique, il faut vous connecter");
            alertDialog.show();
        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
        setDonnees_historique(BackgroundWorker.getResultat_historique());
        //Toast.makeText(ButtonHistorique.this, getDonnees_historique(), Toast.LENGTH_LONG).show();

        //appel de la requete SQL et mis à jour de la ListView
        mPlaceList_historique = mDBHelper.historique();
        adapter_historique = new ListPlaceAdapter(this, mPlaceList_historique);
        lvProduct_historique.setAdapter(adapter_historique);

        /**Méthode permettant d'obtenir l'id de l'item cliqué en fonction de sa position dans la liste
         * @param AdapterView.OnItemClickListener()
         */
        lvProduct_historique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPosition_item_click(position);
                Intent intent = new Intent(ButtonHistorique.this, HistoriqueAvance.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Renvoie les identifiants des lieux stockés dans l'historique sur MySQL
     *
     * @return donnees_historique
     */
    public static String getDonnees_historique() {
        return donnees_historique;
    }

    /**
     * Modifie la variable static donnees_historique en lui donnant les id des lieux de l'historique
     *
     * @param s les données
     */
    public static void setDonnees_historique(String s) {
        donnees_historique = s;
    }

    /**
     * Renvoie la position de l'item cliqué
     *
     * @return position_item_click_favoris
     */
    public static int getPosition_item_click(){return position_item_click_historique;}

    /**
     * Modifie la position de l'item cliqué
     *
     * @param v la position
     */
    public void setPosition_item_click(int v){ position_item_click_historique = v;}

}

