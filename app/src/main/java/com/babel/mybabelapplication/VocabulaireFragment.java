package com.babel.mybabelapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.babel.mybabelapplication.adapter.VocListAdapter;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.VocList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VocabulaireFragment extends Fragment{
    //Context context;

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

        //context = container.getContext();

        vocListAdapter = new VocListAdapter(getActivity());

        vocListListView.setAdapter(vocListAdapter);

        List<VocList> allVocLists = vocListDao.getAllVocLists();

        vocListAdapter.refresh(allVocLists);
        //vocListAdapter.refresh(allVocLists);
        return rootView;
    }

}
