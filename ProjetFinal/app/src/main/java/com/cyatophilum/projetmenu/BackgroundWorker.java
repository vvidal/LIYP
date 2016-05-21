package com.cyatophilum.projetmenu;

/**
 * Cette classe permet de gérer les requêtes de l'utilisateurs
 * lorsqu'il veut voir ses favoris, son historique son séjour.
 * Un objet de cette classe est aussi instancié si l'utilisateur
 * veut ajouter/supprimer un lieu de ses favoris ou de son séjour.
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog; //Pour afficher le résultat de la requête
    private static String resultat_historique; //variable de classe permettant d'obtenir les lieux présents dans l'historique de l'utilisateur
    private static String resultat_favoris; //variable de classe permettant d'obtenir les lieux présents dans les favoris de l'utilisateur
    private static String resultat_sejour; //variable de classe permettant d'obtenir les lieux présents dans le séjour de l'utilisateur
    private static String type; // type de la requête : Ajout, suppression, création de compte, connexion...

    BackgroundWorker(Context ctx) {
        context = ctx;
    }
    /**
     * Renvoie un résultat en fonction données stockées sur MySQL
     * @return result chaine contenant le résultat de la requête SQL
     */
    @Override
    protected String doInBackground(String... params) {

        setType(params[0]);
        //l'url est utilisée selon le type de requête soumis, les scripts PHP sont stockés sur serveur.
        String login_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/login.php";
        String historique_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/historique.php";
        String favoris_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/favoris.php";
        String sejour_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/sejour.php";
        String ajout_favoris_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/insertion_favoris.php";
        String ajout_historique_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/insertion_historique.php";
        String ajout_sejour_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/insertion_sejour.php";
        String creer_compte_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/creer_compte.php";
        String supprimer_favoris_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/suppression_favoris.php";
        String supprimer_sejour_url = "http://iutdoua-webetu.univ-lyon1.fr/~p1504180/PHP/projet_tut/suppression_sejour.php";

        //Dans le cas d'une création de compte ou d'une connection, on se connecte à l'URL et la requête SQL est exécutée
        if (type.equals("login") || type.equals("new_account")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url;
                if (type.equals("login")) url = new URL(login_url);
                else url = new URL(creer_compte_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1")); //ouverture du résultat
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) { //lecture du résultat
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                if (result.contains("nvenue")) { //si le résultat contient bienvenue,
                    String res = result; //alors le résultat retourné est une chaine de caractère contenant le pseudo
                    res = res.substring(10, res.length());
                    ButtonConnexion.setUser_id(res);
                    result = result.substring(0, 9);
                    result = result + " " + params[1];
                }
                return result;
            //gestion des exceptions
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //dans le cas ou il faut réaliser un select dans la table sejour ou favoris ou historique
        if (type.equals("historique") || type.equals("favoris") || type.equals("sejour")) {
            try {
                String user_name = params[1];
                URL url;
                if (type.equals("historique")) url = new URL(historique_url);
                else if (type.equals("favoris")) url = new URL(favoris_url);
                else url = new URL(sejour_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                //on met à jour à jour la variable de classe
                // en fonction du type de la requete
                if (type.equals("historique")) setResultat_historique(result);

                else if (type.equals("favoris")) setResultat_favoris(result);

                else setResultat_sejour(result);

                return result;

            //gestion des exceptions
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // si l'utilisateur souhaite inséré une valeur dans son séjour/ses favoris
        //ou s'il supprime une valeur de son séjour, alors on appelle insertion_historique pour ce cas
        if (type.equals("insertion_sejour") || type.equals("insertion_favoris") || type.equals("insertion_historique") || type.equals("suppression_sejour") || type.equals("suppression_favoris")) {
            try {
                String id_user = params[1];
                String id_lieu = params[2];
                URL url;
                if (type.equals("insertion_sejour")) url = new URL(ajout_sejour_url);
                else if (type.equals("insertion_favoris")) url = new URL(ajout_favoris_url);
                else if (type.equals("insertion_historique")) url = new URL(ajout_historique_url);
                else if (type.equals("suppression_favoris")) url = new URL(supprimer_favoris_url);
                else url = new URL(supprimer_sejour_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(id_user, "UTF-8") + "&"
                        + URLEncoder.encode("id_lieu", "UTF-8") + "=" + URLEncoder.encode(id_lieu, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    /**
     * Prépare le boite à dialogue
     */
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }
    /**
     * Affiche la boite en fonction des conditions
     * @param result le resultat de doInBackground
     */
    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        if (ButtonConnexion.getUser_id().equals("") && (type.equals("login") || type.equals("new_account") )) {
            alertDialog.show();
        }
        if (!ButtonConnexion.getUser_id().equals("") && (type.equals("insertion_sejour") || type.equals("insertion_favoris"))){
            alertDialog.show();
        }

    }

    /**
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Modifie la variable resultat_historique avec les identifiants des lieux présents dans la table historique
     * @param res le résultat de la requete SQL recupérée par doInBackground
     */
    public static void setResultat_historique(String res) {

        resultat_historique = res.substring(1, res.length() - 1);
    }

    /**
     * Renvoie les identifiants des lieux visités par l'utilisateur
     * @return identifiants de la table historique
     */
    public static String getResultat_historique() {
        return resultat_historique;
    }

    /**
     * Modifie la variable resultat_favoris avec les identifiants des lieux présents dans la table favoris
     * @param res le résultat de la requete SQL recupérée par doInBackground
     */
    public static void setResultat_favoris(String res) {

        resultat_favoris = res.substring(1, res.length() - 1);
    }

    /**
     * Renvoie les identifiants des lieux favoris de l'utilisateur
     * @return identifiants de la table favoris
     */
    public static String getResultat_favoris() {
        return resultat_favoris;
    }

    /**
     * Modifie la variable resultat_sejour avec les identifiants des lieux présents dans la table séjours
     * @param res le résultat de la requete SQL recupérée par doInBackground
     */
    public static void setResultat_sejour(String res) {

        resultat_sejour = res.substring(1, res.length() - 1);
    }

    /**
     * Renvoie les identifiants des lieux du séjour de l'utilisateur
     * @return identifiants de la table séjour
     */
    public static String getResultat_sejour() {
        return resultat_sejour;
    }

    /**
     * Modifie la variable type pour appeler le bon script PHP
     * @param s le type de requete utilisé par l'utilisateur
     */
    public static void setType(String s) {
        type = s;
    }

}
