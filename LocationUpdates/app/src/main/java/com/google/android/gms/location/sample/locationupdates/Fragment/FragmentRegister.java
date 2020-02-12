package com.google.android.gms.location.sample.locationupdates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.sample.locationupdates.Interface.OnCreateUserRequestedListener;
import com.google.android.gms.location.sample.locationupdates.R;

public class FragmentRegister extends Fragment implements View.OnClickListener
{
    EditText editTxtUser;
    EditText editTxtPass;
    EditText editTxtUsername;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTxtUser = view.findViewById(R.id.editTxtUser);
        editTxtPass = view.findViewById(R.id.editTxtPass);
        editTxtUsername = view.findViewById(R.id.editTxtUsername);
        btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.btn)
        {
            ((OnCreateUserRequestedListener)getActivity()).onCreateUserRequested(editTxtUser.getText().toString(), editTxtPass.getText().toString(), editTxtUsername.getText().toString());
        }
    }
}
