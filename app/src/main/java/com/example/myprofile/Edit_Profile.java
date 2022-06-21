package com.example.myprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_Profile extends AppCompatActivity {
    EditText etFirst, etLast, etPhone, etEmail;
    Button btnCancel, btnSave;

    SharedPreferences sharePreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Link From Java to XML
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etPhone = findViewById(R.id.etPhone);
        etEmail =findViewById(R.id.etEmail);
        btnCancel =findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        sharePreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences.Editor editor = sharePreferences.edit();
                editor.clear();
                editor.commit();
                finish();;
                Toast.makeText(Edit_Profile.this, "Cancel", Toast.LENGTH_SHORT).show();
                finish();
            }

        });


        //button Click Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "First Name: " + etFirst.getText());
                Log.d("msg", "Last Name: " + etLast.getText());
                Log.d("msg", "Phone: " + etPhone.getText());
                Log.d("msg", "Email: " + etEmail.getText());

                SharedPreferences.Editor editor = sharePreferences.edit();
                editor.putString(KEY_FIRST,etFirst.getText().toString());
                editor.putString(KEY_LAST,etLast.getText().toString());
                editor.putString(KEY_PHONE,etPhone.getText().toString());
                editor.putString(KEY_EMAIL,etEmail.getText().toString());
                editor.apply();

                Intent intent=new Intent(Edit_Profile.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(Edit_Profile.this, "Save", Toast.LENGTH_SHORT).show();
            }
        });

    }
}