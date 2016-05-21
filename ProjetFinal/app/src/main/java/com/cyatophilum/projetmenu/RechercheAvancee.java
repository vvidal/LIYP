package com.cyatophilum.projetmenu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cyatophilum.projetmenu.mDataObject.MyPost;
import com.cyatophilum.projetmenu.mFaceBook.Login;
import com.cyatophilum.projetmenu.mFaceBook.MyConfiguration;
import com.sromku.simple.fb.SimpleFacebook;

import java.util.List;

/**
 * Classe permet d'afficher le détail d'un lieu de la recherche
 *
 * @author Karim Oubah on 15/05/2016.
 */

public class RechercheAvancee extends AppCompatActivity implements View.OnClickListener {

    private Detailproduit adapter;
    private List<Place> mPlaceList;
    private ListView lvProduct;
    private DatabaseHelper mDBHelper;
    public static TextView nommm;
    public static TextView adresse;
    public static TextView villee;
    AlertDialog alertDialog;
    public static TextView lien_facebook;
    public static TextView site_internet;
    String nom;
    SimpleFacebook fb;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private double Longitude;
    private double Latitude;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rechercheavancee);
        lvProduct = (ListView) findViewById(R.id.listdetail);
        mDBHelper = new DatabaseHelper(this);

        ImageButton b1 = (ImageButton) findViewById(R.id.ic_sharewithfacebook);
        b1.setOnClickListener(this);

        ImageButton b2 = (ImageButton) findViewById(R.id.ic_geoloc);
        b2.setOnClickListener(this);

        ImageButton b3 = (ImageButton) findViewById(R.id._mes_favoris);
        b3.setOnClickListener(this);

        ImageButton b4 = (ImageButton) findViewById(R.id.ic_ajouterr);
        b4.setOnClickListener(this);

        nommm = (TextView) findViewById(R.id.fiche_name);

        //Get product list in db when db exists
        mPlaceList = mDBHelper.lieuAvance();
        //Init adapter
        adapter = new Detailproduit(this, mPlaceList);
        //Set adapter for listview
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(rechercheListener);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            /**
             *
             * @param location
             */
            @Override
            public void onLocationChanged(Location location) {
                //Toast.makeText(getApplicationContext(),"\n " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
                Longitude = location.getLongitude();
                Latitude = location.getLatitude();
            }

            /**
             *
             * @param provider
             * @param status
             * @param extras
             */
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            /**
             *
             * @param provider
             */
            @Override
            public void onProviderEnabled(String provider) {

            }

            /**
             *
             * @param provider
             */
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);

        SimpleFacebook.setConfiguration(new MyConfiguration().getMyConfig());

        this.initialiseFB();
    }


    private AdapterView.OnItemClickListener rechercheListener = new AdapterView.OnItemClickListener() {
        /**
         *
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*Intent intent = new Intent(RechercheAvancee.this, MainActivity.class);
            startActivity(intent);*/
        }

    };

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ic_sharewithfacebook:
                MyPost myPost = new MyPost();
                nommm = (TextView) findViewById(R.id.fiche_name);
                lien_facebook = (TextView) findViewById(R.id.Facebook_fiche);
                site_internet = (TextView) findViewById(R.id.SiteWeb_fiche);
                String nom_lieu = RechercheAvancee.nommm.getText().toString();
                String lien_fb=RechercheAvancee.lien_facebook.getText().toString();
                lien_fb=lien_fb.substring(9, lien_fb.length());
                String website=RechercheAvancee.site_internet.getText().toString();
                website=website.substring(6,site_internet.length());
                //Toast.makeText(getApplicationContext(), nom_lieu+"\n\n"+lien_fb, Toast.LENGTH_LONG).show();
                if(!lien_fb.isEmpty()) myPost.setMessage(nom_lieu + "\n\n" + lien_fb);
                else myPost.setMessage(nom_lieu + "\n\n" + website);
                new Login(fb,RechercheAvancee.this,myPost).login();
                break;

            case R.id.ic_geoloc:

                nommm = (TextView) findViewById(R.id.fiche_name);
                nom = RechercheAvancee.nommm.getText().toString();

                adresse = (TextView) findViewById(R.id.adresse_fiche);
                String adresse = RechercheAvancee.adresse.getText().toString();

                villee = (TextView) findViewById(R.id.ville_fiche);
                String vill = RechercheAvancee.villee.getText().toString();

                String url = "https://www.google.fr/maps/dir/" + Latitude + "," + Longitude + "/" + adresse + " " + vill+"/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

                break;

            case R.id.ic_ajouterr:

                nommm = (TextView) findViewById(R.id.fiche_name);

                if (ButtonConnexion.getUser_id() == "") {
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Attention");
                    alertDialog.setMessage("Pour ajouter un lieu au séjour, il faut vous connecter");
                    alertDialog.show();
                } else {
                    nom = RechercheAvancee.nommm.getText().toString();
                    String id = mDBHelper.ajouterSejourFavorisHistorique(nom);
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute("insertion_sejour", ButtonConnexion.getUser_id(), id);
                    BackgroundWorker backgroundWorker1 = new BackgroundWorker(this);
                    backgroundWorker1.execute("sejour", ButtonConnexion.getUser_id());
                    //Toast.makeText(getApplicationContext(), "Lieu ajouté à votre séjour", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id._mes_favoris:

                nommm = (TextView) findViewById(R.id.fiche_name);

                if (ButtonConnexion.getUser_id() == "") {
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Attention");
                    alertDialog.setMessage("Pour ajouter un lieu à vos favoris, il faut vous connecter");
                    alertDialog.show();
                } else {
                    nom = RechercheAvancee.nommm.getText().toString();
                    String id = mDBHelper.ajouterSejourFavorisHistorique(nom);
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute("insertion_favoris", ButtonConnexion.getUser_id(), id);
                    BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
                    backgroundWorker2.execute("favoris", ButtonConnexion.getUser_id());
                    //Toast.makeText(getApplicationContext(), "Lieu ajouté à vos favoris", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        fb = SimpleFacebook.getInstance(this);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fb.onActivityResult(requestCode, resultCode, data);
    }

    private void initialiseFB() {
        SimpleFacebook.setConfiguration(new MyConfiguration().getMyConfig());
        fb = SimpleFacebook.getInstance(this);
    }

    /**
     *
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        fb = SimpleFacebook.getInstance(this);
    }
}
