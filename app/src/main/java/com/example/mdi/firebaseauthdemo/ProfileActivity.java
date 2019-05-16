package com.example.mdi.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUserEmail;
    private Button Logout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText editTextName,editTextAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth =FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName =(EditText) findViewById(R.id.editTextName);
        editTextAddress =(EditText) findViewById(R.id.editTextAddress);
        buttonSave =(Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        textViewUserEmail =(TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+ user.getEmail());
        Logout=(Button) findViewById(R.id.buttonLogout);

        Logout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation(){
        String name= editTextName.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();

        UserInformation userInformation= new UserInformation(name,address);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved ..." ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if(view==Logout)
        {
            firebaseAuth.signOut();
            finish();

            startActivity(new Intent(this,LoginActivity.class));
        }
if(view==buttonSave)
{
    saveUserInformation();
}
    }
}
