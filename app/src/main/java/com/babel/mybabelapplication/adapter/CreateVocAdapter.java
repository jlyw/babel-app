package com.babel.mybabelapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babel.mybabelapplication.R;
import com.babel.mybabelapplication.model.Voc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
public class CreateVocAdapter {
    protected class VocViewHolder {

        @BindView(R.id.row_voc_name_textview)
        protected TextView textViewNameVoc;

        @BindView(R.id.row_voc_organic_textview)
        protected TextView textViewOrganicVoc;

        public VocViewHolder(View vocRowView) {
            ButterKnife.bind(this, vocRowView);
        }

        public void setVoc(Voc voc) {

            textViewNameVoc.setText(voc.getFrench());
            textViewOrganicVoc.setText(voc.getEnglish());
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Voc> vocs;   //null

    public CreateVocAdapter(Context context) {
        vocs = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void refresh(Voc[] vocs) {
        if(vocs==null) {
            refresh(new ArrayList<Voc>());
        } else {
            refresh(Arrays.asList(vocs));
        }
    }

    public void refresh(List<Voc> vocList) {
        this.vocs.clear();
        this.vocs.addAll(vocList);

        //Refresh UI
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vocs.size();
    }

    @Override
    public Object getItem(int i) {
        return vocs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View viewFromCache, ViewGroup viewGroup) {

        View rowView = viewFromCache;

        //Utilisation d'un ViewHolder
        // https://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
        VocViewHolder vocViewHolder;

        if(rowView==null) {
            rowView = layoutInflater.inflate(R.layout.row_voc, viewGroup, false);
            vocViewHolder = new VocViewHolder(rowView);

            rowView.setTag(vocViewHolder);
        } else {
            vocViewHolder = (VocViewHolder)rowView.getTag();
        }

        Voc voc = vocs.get(position);

        vocViewHolder.setVoc(voc);

        return rowView;
    }
}
*/