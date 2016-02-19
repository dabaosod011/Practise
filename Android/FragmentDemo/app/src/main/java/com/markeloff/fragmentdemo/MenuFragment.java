package com.markeloff.fragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = MenuFragment.class.getSimpleName();

    private ListView menuList;
    private ArrayAdapter<String> adapter;
    private String[] menuItems = {"Sound", "Display"};
    private boolean isTwoPane;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menuItems);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        menuList = (ListView) view.findViewById(R.id.menu_list);
        menuList.setAdapter(adapter);
        menuList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.details_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
        Log.d(TAG, "onItemClick: ");
        if (isTwoPane) {
            Fragment fragment = null;
            if (index == 0) {
                fragment = new SoundFragment();
            } else if (index == 1) {
                fragment = new DisplayFragment();
            }
            getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
        } else {
            Intent intent = null;
            if (index == 0) {
                intent = new Intent(getActivity(), SoundActivity.class);
            } else if (index == 1) {
                intent = new Intent(getActivity(), DisplayActivity.class);
            }
            startActivity(intent);
        }
    }
}
