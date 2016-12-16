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
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 16/12/2016.
 */

public class VerbAdapter extends BaseAdapter {
    private final TextToSpeech ttobjAdapter;
    private TextToSpeech ttobj;
    private VerbDAO verbDAO;
    private VerbListDAO verbListDao;

    protected class VerbViewHolder {
        @BindView(R.id.voc_french_word)
        protected TextView textViewFrennchWord;

        @BindView(R.id.voc_english_word)
        protected TextView textViewEnglishWord;

        @BindView(R.id.row_image_view)
        protected ImageView imageViewWordProgress;

        @BindView(R.id.voc_sound_icon)
        protected ImageView imageViewListenWord;

        public VerbViewHolder(View verbListRowView) {
            ButterKnife.bind(this, verbListRowView);
        }

        public void setVerb(final Verb verb) {
            verbDAO = new VerbDAO();
            verbListDao = new VerbListDAO();

            List<Verb> allVerbs = verbDAO.getAllVerbs();

            // Maitrise du mot
            int grade = R.drawable.grade_0;
            switch(verb.getGrade()) {
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
            textViewFrennchWord.setText(verb.getFrench());
            textViewEnglishWord.setText(verb.getInfinitive());

            imageViewListenWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ttobjAdapter.speak(verb.getInfinitive() + ", " + verb.getSimplePast() + ", " + verb.getPastParticiple(), TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Verb> verbs; //null

    public VerbAdapter(Context context, TextToSpeech ttobj) {
        verbs = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);

        ttobjAdapter = ttobj;
    }

    public void refresh(Verb[] verb) {
        if(verb == null) {
            refresh(new ArrayList<Verb>());
        } else {
            refresh(Arrays.asList(verb));
        }
    }

    public void refresh(List<Verb> verbList) {
        this.verbs.clear();
        this.verbs.addAll(verbList);

        // Refresh UI
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return verbs.size();
    }

    @Override
    public Object getItem(int position) { return verbs.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Utilisation d'un viewHolder
        VerbAdapter.VerbViewHolder verbViewHolder;

        if(rowView == null) {
            rowView = layoutInflater.inflate(R.layout.row_verb, parent, false);
            verbViewHolder = new VerbAdapter.VerbViewHolder(rowView);

            rowView.setTag(verbViewHolder);

            rowView.setTag(verbViewHolder);
        } else {
            verbViewHolder = (VerbAdapter.VerbViewHolder) rowView.getTag();
        }

        Verb verb = verbs.get(position);

        verbViewHolder.setVerb(verb);

        return rowView;
    }
}
