package com.babel.mybabelapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babel.mybabelapplication.adapter.VocListAdapter;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.VocList;

import java.util.List;

public class VocabulaireFragment extends Fragment{
    Context context;
    Intent intent;

    protected ListView vocListListView;

    protected RelativeLayout layoutNoData;
    protected Button ctaVocNoData;

    protected VocListAdapter vocListAdapter;

    VocListDAO vocListDao = new VocListDAO();

    public VocabulaireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vocabulaire, container, false);

        vocListListView = (ListView) rootView.findViewById(R.id.VocListListView);

        layoutNoData = (RelativeLayout) rootView.findViewById(R.id.layout_no_data);
        ctaVocNoData = (Button) rootView.findViewById(R.id.cta_create_voc_list);

        context = container.getContext();

        vocListAdapter = new VocListAdapter(getActivity());

        vocListListView.setAdapter(vocListAdapter);

        final List<VocList> allVocLists = vocListDao.getAllVocLists();

        vocListAdapter.refresh(allVocLists);

        if(vocListAdapter.getCount() == 0) {
            layoutNoData.setVisibility(View.VISIBLE);
        }

        ctaVocNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, CreateListActivity.class);
                startActivity(intent);
            }
        });

        vocListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VocList vocList = (VocList) vocListAdapter.getItem(position);

                intent = new Intent(context, ShowVocListActivity.class);
                intent.putExtra("LIST_VOC_ID", vocList.getId());
                startActivity(intent);
            }
        });
        return rootView;
    }
}
