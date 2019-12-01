package com.agilya.syc.tabbedactivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_search extends Fragment {
    @BindView(R.id.search_begin_date) TextView searchBeginDate;
    @BindView(R.id.search_end_date) TextView searchEndDate;
    @BindView(R.id.search_valider) Button searchValider;

    //constructor empty !!
    public Fragment_search() {
        // Required empty public constructor
    }

    public static Fragment_search newInstance(String param1, String param2) {
        return new Fragment_search();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }

    @OnClick(R.id.search_valider)
    public void searchValiderCliked(){
        Log.d("AndroidClarified","Button One Clicked");
        //Toast.makeText(MainActivity.this, "Une erreur", Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity().getApplicationContext() ,"blabla", Toast.LENGTH_LONG).show();
   }



}
