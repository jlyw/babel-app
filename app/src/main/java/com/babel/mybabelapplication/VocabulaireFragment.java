package com.babel.mybabelapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.babel.mybabelapplication.adapter.VocListAdapter;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.VocList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VocabulaireFragment extends Fragment{
    Context context;
    Intent intent;

    //@BindView(R.id.VocListListView)
    protected ListView vocListListView;

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

        context = container.getContext();

        vocListAdapter = new VocListAdapter(getActivity());

        vocListListView.setAdapter(vocListAdapter);

        final List<VocList> allVocLists = vocListDao.getAllVocLists();

        vocListAdapter.refresh(allVocLists);

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
