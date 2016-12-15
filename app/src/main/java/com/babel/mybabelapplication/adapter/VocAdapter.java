package com.babel.mybabelapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.babel.mybabelapplication.R;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 15/12/2016.
 */

public class VocAdapter extends BaseAdapter {

    private VocDAO vocDAO;
    private VocListDAO vocListDao;

    protected class VocViewHolder {
        @BindView(R.id.textView)
        protected TextView textViewVocListName;

        @BindView(R.id.textView2)
        protected TextView textViewVocListNbWord;

        @BindView(R.id.row_image_view)
        protected ImageView imageViewVocListImage;

        public VocViewHolder(View vocListRowView) {
            ButterKnife.bind(this, vocListRowView);
        }

        public void setVoc(Voc voc) {
            vocDAO = new VocDAO();
            vocListDao = new VocListDAO();

            List<Voc> allVocs = vocDAO.getAllVocs();

            String ImageUri = "@drawable/image-chouette";


            //List<Voc> allVocOfAList = vocDAO.getAllVocsOffOneList(allVocLists.get(2).getId());
            imageViewVocListImage.setImageResource(R.drawable.image_chouette);
            textViewVocListName.setText(voc.getFrench());
            textViewVocListNbWord.setText(voc.getEnglish());
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Voc> vocs; //null

    public VocAdapter(Context context) {
        vocs = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void refresh(Voc[] voc) {
        if(voc == null) {
            refresh(new ArrayList<Voc>());
        } else {
            refresh(Arrays.asList(voc));
        }
    }

    public void refresh(List<Voc> vocList) {
        this.vocs.clear();
        this.vocs.addAll(vocList);

        // Refresh UI
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vocs.size();
    }

    @Override
    public Object getItem(int position) { return vocs.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Utilisation d'un viewHolder
        VocViewHolder vocViewHolder;

        if(rowView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = layoutInflater.inflate(R.layout.row_voc, parent, false);
            vocViewHolder = new VocViewHolder(rowView);

            rowView.setTag(vocViewHolder);

            rowView.setTag(vocViewHolder);
        } else {
            vocViewHolder = (VocViewHolder) rowView.getTag();
        }

        Voc voc = vocs.get(position);

        vocViewHolder.setVoc(voc);

        return rowView;
    }
}