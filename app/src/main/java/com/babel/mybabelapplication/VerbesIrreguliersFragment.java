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

import com.babel.mybabelapplication.adapter.VerbListAdapter;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.VerbList;

import java.util.List;

public class VerbesIrreguliersFragment extends Fragment{
    Context context;
    Intent intent;

    protected ListView verbListListView;

    protected RelativeLayout layoutNoData;
    protected Button ctaVerbNoData;

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

        layoutNoData = (RelativeLayout) rootView.findViewById(R.id.layout_no_data);
        ctaVerbNoData = (Button) rootView.findViewById(R.id.cta_create_verb_list);

        context = container.getContext();

        verbListAdapter = new VerbListAdapter(getActivity());

        verbListListView.setAdapter(verbListAdapter);

        final List<VerbList> allVerbLists = verbListDao.getAllVerbLists();

        verbListAdapter.refresh(allVerbLists);

        if(verbListAdapter.getCount() == 0) {
            layoutNoData.setVisibility(View.VISIBLE);
        }

        ctaVerbNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, CreateListVerbsActivity.class);
                startActivity(intent);
            }
        });

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
