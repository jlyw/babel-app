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
 * Created by jeanl on 14/12/2016.
 */

public class VocListAdapter extends BaseAdapter {
    Context context;

    private VocDAO vocDAO;
    private VocListDAO vocListDao;

    protected class VocListViewHolder {
        @BindView(R.id.row_list_name)
        protected TextView textViewVocListName;

        @BindView(R.id.row_number_word)
        protected TextView textViewVocListNbWord;

        @BindView(R.id.row_image_view)
        protected ImageView imageViewVocListImage;

        public VocListViewHolder(View vocListRowView) {
            ButterKnife.bind(this, vocListRowView);
        }

        public void setVocList(VocList vocList) {
            vocDAO = new VocDAO();
            vocListDao = new VocListDAO();

            List<VocList> allVocLists = vocListDao.getAllVocLists();

            String ImageUri = "@drawable/image-chouette";


            //List<Voc> allVocOfAList = vocDAO.getAllVocsOffOneList(allVocLists.get(2).getId());
            imageViewVocListImage.setImageResource(R.drawable.image_chouette);
            textViewVocListName.setText(vocList.getName());
            textViewVocListNbWord.setText(vocList.getVocs().size() + " mots");
        }
    }

    private final LayoutInflater layoutInflater;
    private List<VocList> vocLists; //null

    public VocListAdapter(Context context) {
        this.context = context;
        vocLists = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void refresh(VocList[] vocLists) {
        if(vocLists == null) {
            refresh(new ArrayList<VocList>());
        } else {
            refresh(Arrays.asList(vocLists));
        }
    }

    public void refresh(List<VocList> vocListList) {
        this.vocLists.clear();
        this.vocLists.addAll(vocListList);

        // Refresh UI
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vocLists.size();
    }

    @Override
    public Object getItem(int position) { return vocLists.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Utilisation d'un viewHolder
        VocListViewHolder vocListViewHolder;

        if(rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = layoutInflater.inflate(R.layout.row_voc_list, parent, false);
            vocListViewHolder = new VocListViewHolder(rowView);

            rowView.setTag(vocListViewHolder);

            rowView.setTag(vocListViewHolder);
        } else {
            vocListViewHolder = (VocListViewHolder) rowView.getTag();
        }

        VocList vocList = vocLists.get(position);

        vocListViewHolder.setVocList(vocList);

        return rowView;
    }
}
