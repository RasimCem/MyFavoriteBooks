package com.example.myfavoritebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText mailText,passText,passText2;
    Button signUpBtn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mailText= findViewById(R.id.mailText);
        passText = findViewById(R.id.passText);
        passText2 = findViewById(R.id.passText2);
        signUpBtn = findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        tv = findViewById(R.id.signinView);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mailText.getText().toString();
                String pass = passText.getText().toString();
                String pass2=passText2.getText().toString();
                //System.out.println(mail+"  "+pass);
                if(pass.equalsIgnoreCase(pass2)){
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent= new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(SignUpActivity.this, "Passwords must be same!", Toast.LENGTH_SHORT).show();
                    System.out.println(pass+" "+pass2);
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
