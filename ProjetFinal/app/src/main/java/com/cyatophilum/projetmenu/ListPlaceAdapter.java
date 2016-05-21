package com.cyatophilum.projetmenu;

/**
 * Cette classe permet de personnaliser la ListView
 *
 * @author Karim Oubah on 15/05/2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListPlaceAdapter extends BaseAdapter {
    private Context mContext;
    private List<Place> mPlaceList;

    /**
     * Constructeur de l'adapter
     * @param mContext
     * @param mPlaceList
     */
    public ListPlaceAdapter(Context mContext, List<Place> mPlaceList) {
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
     * Définie les paramètres de chaque item de la ListView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.item_listview, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_product_name);
        TextView tvDescription = (TextView)v.findViewById(R.id.tv_product_description);
        ImageView img = (ImageView) v.findViewById(R.id.imagedelacategorie);
        tvName.setText(mPlaceList.get(position).getNom());
        tvDescription.setText(mPlaceList.get(position).getType());

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
