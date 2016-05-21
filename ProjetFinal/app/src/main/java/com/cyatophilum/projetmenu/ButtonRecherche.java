package com.cyatophilum.projetmenu;
/**
 * Cette classe permet d'effectuer des recherches et d'afficher le résultat dans une ListView
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;
import android.widget.ListView;

public class ButtonRecherche extends AppCompatActivity implements View.OnClickListener {


    private ImageButton retour;
    private ImageButton recherche;
    private ListView lvProduct;
    private ListPlaceAdapter adapter;
    private List<Place> mPlaceList;
    private DatabaseHelper mDBHelper;
    private static Spinner spinnerCategorie;
    private static Spinner spinnerVille;
    private static EditText choixnom;
    private static String ville, categorie, nom;
    private static int position_item_click;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recherche);

        lvProduct = (ListView) findViewById(R.id.list_item);
        mDBHelper = new DatabaseHelper(this);

        recherche = (ImageButton) findViewById(R.id.recherchons);
        recherche.setOnClickListener(this);

        spinnerCategorie = (Spinner) findViewById(R.id.spinner1);
        spinnerVille = (Spinner) findViewById(R.id.spinner2);
        List<String> categorie = new ArrayList<>();
        List<String> commune;

        commune = mDBHelper.getAllProvinces();//récupération de toutes les villes avec une requête SQL

        //Saisie manuelle des différentes catégories car beaucoup plus court qu'avec les communes (communes)
        categorie.add("Categorie");
        categorie.add("Commerce et service");
        categorie.add("Dégustation");
        categorie.add("Equipement");
        categorie.add("Hébergement collectif");
        categorie.add("Hébergement locatif");
        categorie.add("Hôtellerie");
        categorie.add("Hôtellerie plein air");
        categorie.add("Patrimoine culturel");
        categorie.add("Patrimoine naturel");
        categorie.add("Restauration");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, commune);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVille.setAdapter(adapter2);
        spinnerCategorie.setAdapter(adapter1);

        retour = (ImageButton) findViewById(R.id.retour);
        retour.setOnClickListener(this);

        choixnom = (EditText) findViewById(R.id.choixx);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Permet d'afficher le détail d'un lieu lorsqu'on clique sur celui-ci dans la list
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPosition_item_click(position);
                Intent intent = new Intent(ButtonRecherche.this, RechercheAvancee.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Gestion du click sur les bouttons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retour:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.recherchons:
                setNom();
                setCategorie();
                setVille();
                mPlaceList = mDBHelper.getListPlace();
                adapter = new ListPlaceAdapter(this, mPlaceList);
                lvProduct.setAdapter(adapter);
                break;
        }
    }

    /**
     * Met à jour la position de l'item sélectionné
     *
     * @param v
     */
    public void setPosition_item_click(int v) {
        position_item_click = v;
    }

    /**
     * Renvoie la position de l'item de la list sélectionné
     *
     * @return position_item_click
     */
    public static int getPosition_item_click() {
        return position_item_click;
    }

    /**
     * Renvoie la saisie du nom du lieu
     *
     * @return nom
     */
    public static String getNom() {
        return nom;
    }

    /**
     * Met à jour le nom du lieu
     */
    public static void setNom() {
        ButtonRecherche.nom = ButtonRecherche.choixnom.getText().toString();
    }

    /**
     * Renvoie la catégorie sélectionnée
     *
     * @return categorie
     */
    public static String getCategorie() {
        return categorie;
    }

    /**
     * Met à jour la catégorie sélectionnée
     */
    public static void setCategorie() {
        ButtonRecherche.categorie = ButtonRecherche.spinnerCategorie.getSelectedItem().toString();
    }

    /**
     * Renvoie la ville sélectionnée
     *
     * @return ville
     */
    public static String getVille() {
        return ville;
    }

    /**
     * Met à jour la ville sélectionnée
     */
    public static void setVille() {
        ButtonRecherche.ville = ButtonRecherche.spinnerVille.getSelectedItem().toString();
    }

}



