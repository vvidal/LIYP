package com.cyatophilum.projetmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Cette classe est la classe de départ,
 * elle implémente le layout de l'activité principale.
 * C'est à partir de celle-ci que l'on peut effectuer diverses actions
 *
 * @author Karim Oubah on 15/05/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton b1, b2, b3, b4; /* buttons pour se déplacer vers une autre activité*/
    private Button b5; /* boutton pour se connecter / se deconnecter / créer un compte */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); /* choix du layout main_activity */

        /* récupération des favoris, de l'historique ainsi que du séjour le plus rapide possible
            du fait de la lenteur de réponse du serveur
        */
        BackgroundWorker backgroundWorker1 = new BackgroundWorker(this);
        BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
        BackgroundWorker backgroundWorker3 = new BackgroundWorker(this);

        if (!ButtonConnexion.getUser_id().equals("")) {
            backgroundWorker1.execute("sejour", ButtonConnexion.getUser_id());
            backgroundWorker2.execute("historique", ButtonConnexion.getUser_id());
            backgroundWorker3.execute("favoris", ButtonConnexion.getUser_id());
        }

        /*Chaque boutton appelle la méthode setOnClickListener grâce à l'interface View.OnClickListener pour savoir si on le touche*/

        b1 = (ImageButton) findViewById(R.id.buttonrecherche); // boutton pour lancer la recherche
        b1.setOnClickListener(this);
        b2 = (ImageButton) findViewById(R.id.buttonsejour); // boutton pour acceder au séjour
        b2.setOnClickListener(this);
        b3 = (ImageButton) findViewById(R.id.buttonhistorique); // boutton pour acceder à l'historique
        b3.setOnClickListener(this);
        b4 = (ImageButton) findViewById(R.id.buttonfavoris); // boutton pour acceder aux favoris
        b4.setOnClickListener(this);
        b5 = (Button) findViewById(R.id.connect); // boutton pour lancer la gestion du compte utilisateur
        b5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //selon le boutton cliqué, une action sera menée
            case R.id.buttonrecherche:
                Intent intent = new Intent(this, ButtonRecherche.class); // on crée l'activité
                startActivity(intent);// on la lance
                break;
            case R.id.buttonsejour:
                intent = new Intent(this, ButtonSejour.class);
                startActivity(intent);
                break;
            case R.id.buttonhistorique:
                intent = new Intent(this, ButtonHistorique.class);
                startActivity(intent);
                break;
            case R.id.buttonfavoris:
                intent = new Intent(this, ButtonFavoris.class);
                startActivity(intent);
                break;
            case R.id.connect:
                if (ButtonConnexion.getUser_id() == "")
                    intent = new Intent(this, ButtonConnexion.class); // si l'utilisateur n'est pas connecté, alors il arrivera sur l'accueil de connexion
                else intent = new Intent(this, ButtonDeconnection.class); // sinon il arrivera sur l'accueil de deconnexion
                startActivity(intent);
                break;

        }

    }
}
