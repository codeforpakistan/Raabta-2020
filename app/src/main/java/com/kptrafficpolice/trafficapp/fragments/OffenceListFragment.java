package com.kptrafficpolice.trafficapp.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import com.kptrafficpolice.trafficapp.Adapters.ExpandableListAdapter2;
import com.kptrafficpolice.trafficapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OffenceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffenceListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private List<String> parent_title;
    private HashMap<String, List<String>> child_title;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter2 expandableListAdapter2;

    private static String PARENT_TITLE_ONE="Parent One";
    private static String PARENT_TITLE_TWO="Parent Two";
    private static String PARENT_TITLE_THREE="Parent Three";
    private static String PARENT_TITLE_FOUR="Parent Four";

    private static String CHILD_TITLE_ONE="Child One";
    private static String CHILD_TITLE_TWO="Child Two";
    private static String CHILD_TITLE_THREE="Child Three";
    private static String CHILD_TITLE_FOUR="Child Four";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OffenceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OffenceList.
     */
    // TODO: Rename and change types and number of parameters
    public static OffenceListFragment newInstance(String param1, String param2) {
        OffenceListFragment fragment = new OffenceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.offence_list_new, container, false);

        return view;
    }


    private void getExpandableList() {
        parent_title.add(PARENT_TITLE_ONE);
        parent_title.add(PARENT_TITLE_TWO);
        parent_title.add(PARENT_TITLE_THREE);
        parent_title.add(PARENT_TITLE_FOUR);

        // parent one
        List<String> parent_one=new ArrayList<>();
        parent_one.add(CHILD_TITLE_ONE);
        parent_one.add(CHILD_TITLE_TWO);
        parent_one.add(CHILD_TITLE_THREE);
        parent_one.add(CHILD_TITLE_FOUR);

        // parent two
        List<String> parent_two=new ArrayList<>();
        parent_two.add(CHILD_TITLE_ONE);
        parent_two.add(CHILD_TITLE_TWO);
        parent_two.add(CHILD_TITLE_THREE);
        parent_two.add(CHILD_TITLE_FOUR);

        // parent three
        List<String> parent_three=new ArrayList<>();
        parent_three.add(CHILD_TITLE_ONE);
        parent_three.add(CHILD_TITLE_TWO);
        parent_three.add(CHILD_TITLE_THREE);
        parent_three.add(CHILD_TITLE_FOUR);

        // parent four
        List<String> parent_four=new ArrayList<>();
        parent_four.add(CHILD_TITLE_ONE);
        parent_four.add(CHILD_TITLE_TWO);
        parent_four.add(CHILD_TITLE_THREE);
        parent_four.add(CHILD_TITLE_FOUR);

        // adding all the child with the respective parent
        child_title.put(PARENT_TITLE_ONE,parent_one);
        child_title.put(PARENT_TITLE_TWO,parent_two);
        child_title.put(PARENT_TITLE_THREE,parent_three);
        child_title.put(PARENT_TITLE_FOUR,parent_four);
    }
}