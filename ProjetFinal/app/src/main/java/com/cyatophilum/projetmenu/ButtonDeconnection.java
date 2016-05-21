package com.cyatophilum.projetmenu;

/**
 * Classe permet de se déconnecter
 *
 * @author Karim Oubah on 15/05/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by a on 26/04/2016.
 */
public class ButtonDeconnection  extends Activity{

    /**
     * Constructeur de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deconnection); // choix du layout activity_deconnection

    }
    /**
     * Deconnection de l'utilisateur et réinitialisation de la variable static user_id
     * @param view
     */
    public void Disconnect(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        ButtonConnexion.setUser_id("");
        startActivity(intent);
    }


}
