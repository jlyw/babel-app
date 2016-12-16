package com.babel.mybabelapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.babel.mybabelapplication.adapter.VerbListAdapter;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.VerbList;

import java.util.List;

public class VerbesIrreguliersFragment extends Fragment{
    Context context;
    Intent intent;

    protected ListView verbListListView;

    protected VerbListAdapter verbListAdapter;

    VerbListDAO verbListDao = new VerbListDAO();

    public VerbesIrreguliersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbes_irreguliers, container, false);

        verbListListView = (ListView) rootView.findViewById(R.id.VocListListView);

        context = container.getContext();

        verbListAdapter = new VerbListAdapter(getActivity());

        verbListListView.setAdapter(verbListAdapter);

        final List<VerbList> allVerbLists = verbListDao.getAllVerbLists();

        verbListAdapter.refresh(allVerbLists);

        verbListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VerbList verbList = (VerbList) verbListAdapter.getItem(position);

                intent = new Intent(context, ShowVerbListActivity.class);
                intent.putExtra("LIST_VOC_ID", verbList.getId());
                startActivity(intent);
            }
        });
        return rootView;
    }
}
