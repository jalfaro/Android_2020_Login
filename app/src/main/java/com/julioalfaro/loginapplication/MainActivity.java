package com.julioalfaro.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.julioalfaro.loginapplication.utilities.NetworkUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtUser, txtPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txt_user);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login :
                String user = txtUser.getText().toString();
                String password = NetworkUtility.md5(txtPassword.getText().toString());
                NetworkUtility.login(user, password, this);
                break;
            case R.id.btn_register :
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    public void login(boolean status, String message) {
        if (status) {
            //Abrir la pagina principal
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        txtUser.setText("");
        txtPassword.setText("");
    }
}
