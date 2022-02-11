package com.example.parkingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;
    Button register;
    TextView registered;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_pm_logo_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        name = findViewById(R.id.editTxtName1);
        email = findViewById(R.id.editTxtEmail1);
        password = findViewById(R.id.editTxtPassowrd1);
        register = findViewById(R.id.btnregister);
        registered = findViewById(R.id.txtViewRegister);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = name.getText().toString().trim();
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(sname)) {
                    name.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(semail)) {
                    email.setError("Email is rewuired.");
                    return;
                }
                if (TextUtils.isEmpty(spassword)) {
                    password.setError("Password is rewuired.");
                    return;
                }
                if (spassword.length() < 6) {
                    password.setError("Password must be more than five characters.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registered Successfully.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("email", semail);
                            intent.putExtra("password", spassword);

                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public void registerToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
