package com.google.android.gms.location.sample.locationupdates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.sample.locationupdates.Interface.OnShowRegisterRequestedListener;
import com.google.android.gms.location.sample.locationupdates.Interface.OnSignInRequestedListener;
import com.google.android.gms.location.sample.locationupdates.R;
import com.google.android.gms.location.sample.locationupdates.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLogin extends Fragment implements View.OnClickListener
{
    private FirebaseAuth auth;
    private Button btn;
    private Button btnRegister;
    private EditText editTxtUser;
    private EditText editTxtPass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn = view.findViewById(R.id.btn);
        btnRegister = view.findViewById(R.id.btnRegister);
        editTxtPass = view.findViewById(R.id.editTxtPass);
        editTxtUser = view.findViewById(R.id.editTxtUser);

        btn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.btn)
        {
            if(!Utils.stringIsNullOrEmpty(editTxtUser.getText().toString()) && !Utils.stringIsNullOrEmpty(editTxtPass.getText().toString()))
            {
                ((OnSignInRequestedListener)getActivity()).onSignInRequested(editTxtUser.getText().toString(), editTxtPass.getText().toString());
            }
            else
            {
                Toast.makeText(getContext(), "El email y la contraseña no pueden estar vacios", Toast.LENGTH_LONG).show();
            }

        }
        else if (view.getId() == R.id.btnRegister)
        {
            //startActivity(new Intent(getContext(), Register.class));
            ((OnShowRegisterRequestedListener)getActivity()).onShowRegisterRequested();
        }
    }
}
