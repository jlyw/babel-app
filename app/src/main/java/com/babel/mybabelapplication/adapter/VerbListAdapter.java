package com.babel.mybabelapplication.adapter;

import android.content.Context;
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
import com.babel.mybabelapplication.model.VerbList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 16/12/2016.
 */

public class VerbListAdapter extends BaseAdapter {
    Context context;

    private VerbDAO verbDAO;
    private VerbListDAO verbListDao;

    protected class VerbListViewHolder {
        @BindView(R.id.row_list_name)
        protected TextView textViewVerbListName;

        @BindView(R.id.row_number_word)
        protected TextView textViewVerbListNbWord;

        @BindView(R.id.row_grade)
        protected TextView textViewRowGrade;

        @BindView(R.id.row_image_view)
        protected ImageView imageViewVerbListImage;

        public VerbListViewHolder(View verbListRowView) {
            ButterKnife.bind(this, verbListRowView);
        }

        public void setVerbList(VerbList verbList) {
            verbDAO = new VerbDAO();
            verbListDao = new VerbListDAO();

            List<VerbList> allVerbLists = verbListDao.getAllVerbLists();

            String ImageUri = "@drawable/image-chouette";


            //List<Verb> allVerbOfAList = verbDAO.getAllVerbsOffOneList(allVerbLists.get(2).getId());
            int totalPoint = 0;
            for( Verb item : verbList.getVerbs()) {
                totalPoint += item.getGrade();
            }
            int resultPercent;
            if (verbList.getVerbs().size() != 0) {
                resultPercent = ((totalPoint * 100) / (verbList.getVerbs().size() * 2));
            } else {
                resultPercent = 0;
            }


            if(resultPercent <= 33) {
                imageViewVerbListImage.setImageResource(R.drawable.grade_0);
            } else if (resultPercent <= 66) {
                imageViewVerbListImage.setImageResource(R.drawable.grade_1);
            } else {
                imageViewVerbListImage.setImageResource(R.drawable.grade_2);
            }
            textViewRowGrade.setText("Liste maîtrisée à " + resultPercent + "%");
            textViewVerbListName.setText(verbList.getName());
            textViewVerbListNbWord.setText(verbList.getVerbs().size() + " verbes");
        }
    }

    private final LayoutInflater layoutInflater;
    private List<VerbList> verbLists; //null

    public VerbListAdapter(Context context) {
        this.context = context;
        verbLists = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void refresh(VerbList[] verbLists) {
        if(verbLists == null) {
            refresh(new ArrayList<VerbList>());
        } else {
            refresh(Arrays.asList(verbLists));
        }
    }

    public void refresh(List<VerbList> verbListList) {
        this.verbLists.clear();
        this.verbLists.addAll(verbListList);

        // Refresh UI
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return verbLists.size();
    }

    @Override
    public Object getItem(int position) { return verbLists.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // Utilisation d'un viewHolder
        VerbListAdapter.VerbListViewHolder verbListViewHolder;

        if(rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = layoutInflater.inflate(R.layout.row_verb_list, parent, false);
            verbListViewHolder = new VerbListAdapter.VerbListViewHolder(rowView);

            rowView.setTag(verbListViewHolder);

            rowView.setTag(verbListViewHolder);
        } else {
            verbListViewHolder = (VerbListAdapter.VerbListViewHolder) rowView.getTag();
        }

        VerbList verbList = verbLists.get(position);

        verbListViewHolder.setVerbList(verbList);

        return rowView;
    }
}
