package com.cyatophilum.projetmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Cette classe permet d'affecter à chaque balise xml le texte adéquat lorsque l'on clique
 * sur un lieu de la ListView
 *
 * @author Karim Oubah on 15/05/2016.
 */

public class Detailproduit  extends BaseAdapter {
    private Context mContext;
    private List<Place> mPlaceList;

    /**
     * Constructeur
     * @param mContext
     * @param mPlaceList
     */
    public Detailproduit(Context mContext, List<Place> mPlaceList) {
        this.mContext = mContext;
        this.mPlaceList = mPlaceList;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return mPlaceList.size();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mPlaceList.get(position);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return mPlaceList.get(position).getId();
    }

    /**
     * Remplie les différents champs du layout activity_recherche_avancee
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_recherche_avancee, null);
        TextView tvName = (TextView)v.findViewById(R.id.fiche_name);
        TextView tvDescription = (TextView)v.findViewById(R.id.type_fiche);
        ImageView img = (ImageView) v.findViewById(R.id.imagedetail);
        tvName.setText(mPlaceList.get(position).getNom());
        tvDescription.setText(mPlaceList.get(position).getType());

        TextView tvtypedetail = (TextView)v.findViewById(R.id.type_detail_fiche);
        tvtypedetail.setText(mPlaceList.get(position).getType_detail());

        TextView tvclassement = (TextView)v.findViewById(R.id.classement_fiche);
        tvclassement.setText("Classement: "+ mPlaceList.get(position).getClassement());

        TextView tvadresse = (TextView)v.findViewById(R.id.adresse_fiche);
        tvadresse.setText("Adresse: "+ mPlaceList.get(position).getAdresse());

        TextView tvville = (TextView)v.findViewById(R.id.ville_fiche);
        tvville.setText(mPlaceList.get(position).getCommune());

        TextView tvcp = (TextView)v.findViewById(R.id.CP_fiche);
        tvcp.setText(mPlaceList.get(position).getCP());

        TextView tvtarifenc = (TextView)v.findViewById(R.id.Tarifsenclair_fiche);
        tvtarifenc.setText("Tarifs: "+ mPlaceList.get(position).getTarifsencl());

        TextView tvmin = (TextView)v.findViewById(R.id.Tarifmin_fiche);
        tvmin.setText("Tarif minimum: "+ mPlaceList.get(position).getTarifsmin()+"€");

        TextView tvmax = (TextView)v.findViewById(R.id.Tarifmax_fiche);
        tvmax.setText("Tarif maximum: "+ mPlaceList.get(position).getTarifsmax()+"€");

        TextView tvouverture = (TextView)v.findViewById(R.id.ouverture_fiche);
        tvouverture.setText("Ouverture: "+ mPlaceList.get(position).getOuverture());

        TextView tvtel = (TextView)v.findViewById(R.id.Telephone_fiche);
        tvtel.setText("Téléphone: "+ mPlaceList.get(position).getTelephone());

        TextView tvmail = (TextView)v.findViewById(R.id.Email_fiche);
        tvmail.setText("Email: "+ mPlaceList.get(position).getEmail());

        TextView tvfacebook = (TextView)v.findViewById(R.id.Facebook_fiche);
        tvfacebook.setText("Facebook: "+ mPlaceList.get(position).getFacebook());

        TextView tvsiteweb = (TextView)v.findViewById(R.id.SiteWeb_fiche);
        tvsiteweb.setText("Site web: "+ mPlaceList.get(position).getSiteweb());


        String temp= mPlaceList.get(position).getType();
        if("Restauration".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907fdd36d3);
        }

        else if("Hôtellerie".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907e7ed99f);
        }

        else if("Commerce et service".equals(temp)) {
            img.setImageResource(R.drawable.ic_commerce);
        }

        else if("Dégustation".equals(temp)) {
            img.setImageResource(R.drawable.ic_commerce);
        }

        else if("Equipement".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907e24c753);
        }

        else if("Hébergement collectif".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907e7ed99f);
        }

        else if("Hébergement locatif".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907e7ed99f);
        }

        else if("Hôtellerie plein air".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907e7ed99f);
        }

        else if("Patrimoine culturel".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907f79aace);
        }

        else if("Patrimoine naturel".equals(temp)) {
            img.setImageResource(R.drawable.bloggif_569907ede63a2);
        }

        return v;
    }
}
