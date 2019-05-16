package com.example.mdi.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private  String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        firebaseAuth =FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignin =(TextView) findViewById(R.id.textViewSimple);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);

        progressDialog=new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);


    }


    public void registerUser()
    {
         email=editTextEmail.getText().toString();
         password=editTextPassword.getText().toString();




        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

            return;
        }

        progressDialog.setMessage("Registering User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //user is succesfully ragistered and log in
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            Toast.makeText(MainActivity.this,"Registered successful",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Could not register plz try again",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


/*
    @Override
    public void onClick(View view) {
        if(view==buttonRegister1)
        {
            registerUser();
        }
        if(view==textViewSignin)
        {
            //will open login activity here

        }


    }

    */
/*
public void show(View view)
{


    registerUser();

}
*/

    @Override
    public void onClick(View view) {

        if (view == buttonRegister) {
            registerUser();
        }
        if (view == textViewSignin) {
            //will open login activity here
            startActivity(new Intent(this,LoginActivity.class));

        }
    }


}
