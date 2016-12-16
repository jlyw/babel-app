package com.babel.mybabelapplication.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 15/12/2016.
 */

public class VocAdapter extends BaseAdapter {

    private final TextToSpeech ttobjAdapter;
    private TextToSpeech ttobj;
    private VocDAO vocDAO;
    private VocListDAO vocListDao;

    protected class VocViewHolder {
        @BindView(R.id.voc_french_word)
        protected TextView textViewFrennchWord;

        @BindView(R.id.voc_english_word)
        protected TextView textViewEnglishWord;

        @BindView(R.id.row_image_view)
        protected ImageView imageViewWordProgress;

        @BindView(R.id.voc_sound_icon)
        protected ImageView imageViewListenWord;

        public VocViewHolder(View vocListRowView) {
            ButterKnife.bind(this, vocListRowView);
        }

        public void setVoc(final Voc voc) {
            vocDAO = new VocDAO();
            vocListDao = new VocListDAO();

            List<Voc> allVocs = vocDAO.getAllVocs();

            // Maitrise du mot
            int grade = R.drawable.grade_0;
            switch(voc.getGrade()) {
                case 0:
                    grade = R.drawable.grade_0;
                    break;
                case 1:
                    grade = R.drawable.grade_1;
                    break;
                case 2:
                    grade = R.drawable.grade_2;
                    break;
            }

            imageViewWordProgress.setImageResource(grade);
            imageViewListenWord.setImageResource(R.drawable.sound_icon);
            textViewFrennchWord.setText(voc.getFrench());
            textViewEnglishWord.setText(voc.getEnglish());

            imageViewListenWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ttobjAdapter.speak(voc.getEnglish(), TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Voc> vocs; //null

    public VocAdapter(Context context, TextToSpeech ttobj) {
        vocs = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);

        ttobjAdapter = ttobj;
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
