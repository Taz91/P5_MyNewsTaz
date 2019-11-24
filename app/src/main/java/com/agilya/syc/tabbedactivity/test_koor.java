package com.agilya.syc.tabbedactivity;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class test_koor extends Fragment {
    private EditText txtLogin;
    private EditText txtPwd;
    private Button btnValider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_koor, container,true);
        txtLogin = (EditText) rootView.findViewById(R.id.txtLogin);
        txtPwd = (EditText) rootView.findViewById(R.id.txtPwd);
        btnValider = (Button) rootView.findViewById(R.id.btnValider);

        return rootView;
    }

    private View.OnClickListener btnValiderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("DEBUG",txtLogin.getText() + " / " + txtPwd.getText() );
        }
    };


}
