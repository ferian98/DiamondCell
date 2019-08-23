package com.example.android.diamondcell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;

    private final String TAG_USERNAME = "USERNAME";
    private final String TAG_HAK_AKSES = "HAK_AKSES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        Button buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();

                if (isRegisteredUser(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(TAG_USERNAME, username);
                    intent.putExtra(TAG_HAK_AKSES, getHakAkses(username));
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "User tidak dikenal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO: Ubah metode agar mengecek jika username dan password sudah ada di DB
    private boolean isRegisteredUser(String username, String password) {
        return true;
    }

    //TODO: Ubah metode untuk mengembalikan hak akses dari user
    private String getHakAkses(String username) {
        return "MANAJER";
    }
}
