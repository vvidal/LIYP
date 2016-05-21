package com.cyatophilum.projetmenu;

/**
 * Classe permet de se connecter
 *
 * @author Karim Oubah on 15/05/2016.
 */


import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ButtonConnexion extends Activity implements View.OnClickListener {

    EditText UsernameEt, PasswordEt; /* Champs contenant la saisie de l'utilisateur */
    private static String user_id = ""; //id de l'utilisateur, mis à jour à la connection ou l'inscription
    private static String username;//pseudo de l'utilisateur

    /**
     * Constructeur de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection); // choix du layout activity_connection
        UsernameEt = (EditText) findViewById(R.id.s_pseudo);
        PasswordEt = (EditText) findViewById(R.id.s_password);

        Button b1 = (Button) findViewById(R.id.button_creer_compte); //mise en relation du button dans le layout
        b1.setOnClickListener(this); //listener pour le boutton qui permet de créer un compte
    }

    /**
     * Affiche la boite en fonction des conditions
     * @param v le resultat de doInBackground
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //si l'utilisateur choisi creer un compte,
            //alors on lance l'activité
            //permettant la création
            case R.id.button_creer_compte:
                Intent intent = new Intent(this, NouveauCompte.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * Méthode définie dans le xml pour le boutton se connecter, tentative de connection
     * @param view l'écran actuel
     */
    public void OnLogin(View view) {
        setUsername(UsernameEt.getText().toString());
        String password = PasswordEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, getUsername(), password);//tentative de connection
        // si l'id de l'utilisateur est différent de "", la connection à réussie
        if (!ButtonConnexion.getUser_id().equals("")) {
            Toast.makeText(ButtonConnexion.this, "Bonjour " + getUsername(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);//retour à l'activité principale
        }
    }

    /**
     * Modifie la variable static contenant l'identifiant de l'utilisateur en cas de connection
     * @param s l'id de l'utilisateur stockée sur MySQL
     */
    public static void setUser_id(String s) {
        user_id = s;
    }

    /**
     * Renvoie l'id de l'utilisateur stocké sur MySQL
     * @return user_id l'id de l'utilisateur
     */
    public static String getUser_id() {
        return user_id;
    }

    /**
     * Renvoie le pseudo de l'utilisateur stocké sur MySQL
     * @return username le pseudo de l'utilisateur
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Modifie le pseudo de l'utilisateur
     * @param s le pseudo
     */
    public static void setUsername(String s) {
        username = s;
    }


}