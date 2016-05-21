package com.cyatophilum.projetmenu;
/**
 * Cette classe permet d'effectuer d'afficher son séjour
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;
import android.widget.Toast;


public class ButtonSejour extends AppCompatActivity implements View.OnClickListener {

    AlertDialog alertDialog;
    private ListView lvProduct_favoris;
    private ListPlaceAdapter adapter;
    private List<Place> mPlaceList;
    private DatabaseHelper mDBHelper;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static String donnees_sejour;
    private double Longitude;
    private double Latitude;
    private static int position_item_click_sejour;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sejour);

        ImageButton b1 = (ImageButton) findViewById(R.id.ic_sharewithfacebook);
        b1.setOnClickListener(this);

        ImageButton b2 = (ImageButton) findViewById(R.id.ic_geoloc);
        b2.setOnClickListener(this);

        ImageButton b3 = (ImageButton) findViewById(R.id.ic_generer);
        b3.setOnClickListener(this);

        b3.setVisibility(View.GONE);

        lvProduct_favoris = (ListView) findViewById(R.id.list_favoris);
        mDBHelper = new DatabaseHelper(this);

        String username = ButtonConnexion.getUser_id();
        String type = "sejour";

        if (ButtonConnexion.getUser_id() == "") {
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Pour afficher votre séjour, il faut vous connecter");
            alertDialog.show();
        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
        setDonnees_sejour(BackgroundWorker.getResultat_sejour());
        //Toast.makeText(ButtonSejour.this, getDonnees_sejour(), Toast.LENGTH_LONG).show();

        mPlaceList = mDBHelper.sejour();
        adapter = new ListPlaceAdapter(this, mPlaceList);
        lvProduct_favoris.setAdapter(adapter);

        if (mPlaceList.isEmpty()) {
            b3.setVisibility(View.VISIBLE);
        }

        lvProduct_favoris.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPosition_item_click(position);
                Intent intent = new Intent(ButtonSejour.this, SejourAvance.class);
                startActivity(intent);
            }
        });

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

    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_sharewithfacebook:
                this.finish();
                break;

            case R.id.ic_geoloc:
                String res = mDBHelper.cheminSejour();
                String url = "https://www.google.fr/maps/dir/" + Latitude + "," + Longitude + "/" + res;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                break;

            case R.id.ic_generer:
                mPlaceList.clear();
                final Dialog dialog = new Dialog(ButtonSejour.this);
                dialog.setTitle("Séjour");
                dialog.setContentView(R.layout.message_facebook);
                dialog.show();

                final int[] val = new int[1];
                final EditText editText = (EditText) dialog.findViewById(R.id.message_fb);
                Button soumettre_sejour = (Button) dialog.findViewById(R.id.button_nbjour_sejour);
                soumettre_sejour.setOnClickListener(new View.OnClickListener() {
                    /**
                     *
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        if (!editText.getText().toString().equals("")) {
                            val[0] = Integer.parseInt(editText.getText().toString());
                            //Toast.makeText(ButtonSejour.this, "" + val[0], Toast.LENGTH_LONG).show();
                        } else {
                            val[0] = 0;
                            //Toast.makeText(ButtonSejour.this, "" + val[0], Toast.LENGTH_LONG).show();
                        }
                        if (val[0] != 0) {
                            //Toast.makeText(ButtonSejour.this, ""+val[0], Toast.LENGTH_LONG).show();
                            mPlaceList = mDBHelper.sejour_aleatoire(val[0]);
                            adapter = new ListPlaceAdapter(ButtonSejour.this, mPlaceList);
                            lvProduct_favoris.setAdapter(adapter);

                        }
                        dialog.cancel();

                        ArrayList<Integer> liste_id =DatabaseHelper.renvoie_id_sejour_alea();
                        for(int i=0;i<liste_id.size();i++){
                            BackgroundWorker backgroundWorker = new BackgroundWorker(ButtonSejour.this);
                            backgroundWorker.execute("insertion_sejour", ButtonConnexion.getUser_id(), "" + liste_id.get(i));
                            //Toast.makeText(ButtonSejour.this, "" + liste_id.get(i), Toast.LENGTH_LONG).show();
                        }

                        Toast.makeText(ButtonSejour.this, "SÉJOUR GÉNÉRÉ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ButtonSejour.this, MainActivity.class);
                        startActivity(intent);//retour à l'activité principale

                    }

                });


                break;

        }

    }

    /**
     *
     * @return
     */
    public List<Place> getmPlaceList() {
        return mPlaceList;
    }

    /**
     *
     * @return
     */
    public static String getDonnees_sejour() {
        return donnees_sejour;
    }

    /**
     *
     * @param s
     */
    public static void setDonnees_sejour(String s) {
        donnees_sejour = s;
    }

    /**
     *
     * @return
     */
    public static int getPosition_item_click() {
        return position_item_click_sejour;
    }

    /**
     *
     * @param v
     */
    public void setPosition_item_click(int v) {
        position_item_click_sejour = v;
    }

}