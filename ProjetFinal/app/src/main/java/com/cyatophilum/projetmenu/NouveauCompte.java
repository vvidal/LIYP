package com.cyatophilum.projetmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Cette classe permet de créer un compte
 *
 * @author Karim Oubah on 15/05/2016.
 */

public class NouveauCompte extends Activity {

    EditText UsernameEt, PasswordEt;
    private static String username;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_compte);
        UsernameEt = (EditText) findViewById(R.id.s_pseudo);
        PasswordEt = (EditText) findViewById(R.id.s_password);

    }

    /**
     * Méthode appelée si on appuie sur le bouton définie dans le fichier xml activity_nouveau_compte, elle crée compte
     * @param view
     */
    public void OnCreat(View view) {
        setUsername(UsernameEt.getText().toString());
        String password = PasswordEt.getText().toString();
        String type = "new_account";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, getUsername(), password);
        if (ButtonConnexion.getUser_id() != "") {
            Toast.makeText(NouveauCompte.this, "Bonjour " + getUsername(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     *
     * @return username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Modifie la variable static username
     * @param s
     */
    public static void setUsername(String s) {
        username = s;
    }


}
