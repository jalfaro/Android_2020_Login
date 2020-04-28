package com.julioalfaro.loginapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.julioalfaro.loginapplication.data.Usuario;
import com.julioalfaro.loginapplication.utilities.NetworkUtility;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtNombre, txtUser, txtPassword;
    Button btnRegistrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        txtNombre = findViewById(R.id.txt_register_nombre);
        txtUser = findViewById(R.id.txt_register_user);
        txtPassword = findViewById(R.id.txt_register_password);
        btnRegistrar = findViewById(R.id.btn_new_register);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_new_register) {
            Usuario user = new Usuario();
            user.setEmail(txtUser.getText().toString());
            user.setNombre(txtNombre.getText().toString());
            user.setPassword(NetworkUtility.md5(txtPassword.getText().toString()));
            NetworkUtility.register(user, this);
        }
    }

    public void registroOperado(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
