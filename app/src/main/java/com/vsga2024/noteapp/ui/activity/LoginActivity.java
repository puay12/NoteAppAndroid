package com.vsga2024.noteapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vsga2024.noteapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private String existedUsername = "puay124";
    private String existedPassword = "Puay1244";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.usernameInput.getText().toString();
                String password = binding.passwordInput.getText().toString();
                userLogin(username, password);
            }
        });
    }

    private void userLogin(String username, String password) {
        if ((!username.isEmpty()) && (!password.isEmpty())) {
            if (checkUsername(username) && checkPassword(password)) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Maaf, username atau password salah", Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.usernameInput.setError("Username tidak boleh kosong");
            binding.passwordInput.setError("Password tidak boleh kosong");
        }
    }

    private boolean checkUsername(String username) {
        return username.equals(existedUsername);
    }

    private boolean checkPassword(String password) {
        return password.equals(existedPassword);
    }
}