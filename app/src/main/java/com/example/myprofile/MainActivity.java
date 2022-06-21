package com.example.myprofile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myprofile.Edit_Profile;
import com.example.myprofile.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView txtFull, txtFirst, txtLast, txtPhone, txtEmail;
    Button btnEdit, btnChp, btnLogOut;
    ProgressDialog progressDialog;

    SharedPreferences sharePreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_FULL ="full";
    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFull =findViewById(R.id.txtFull);
        txtFirst = findViewById(R.id.txtFirst);
        txtLast = findViewById(R.id.txtLast);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        btnEdit = findViewById(R.id.btnEdit);
        btnChp = findViewById(R.id.btnchp);
        btnLogOut = findViewById(R.id.btnLogOut);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharePreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String first = sharePreferences.getString(KEY_FIRST,null);
        String last = sharePreferences.getString(KEY_LAST,null);
        String phone = sharePreferences.getString(KEY_PHONE, null);
        String email= sharePreferences.getString(KEY_EMAIL,null);

        if(first !=null || last !=null || phone !=null || email !=null){
            txtFull.setText(first+" "+last);
            txtFirst.setText(" "+first);
            txtLast.setText(" "+last);
            txtPhone.setText(" "+phone);
            txtEmail.setText(" "+email);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Edit_Profile.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Edit My Profile", Toast.LENGTH_SHORT).show();
            }
        });
        btnChp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is second technic are adding layout to alert dilog
                View resetpasswordlayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_change_password,null);
                EditText et_oldpass = resetpasswordlayout.findViewById(R.id.et_oldpass);
                EditText et_newpass = resetpasswordlayout.findViewById(R.id.et_newpass);
                EditText et_conpass = resetpasswordlayout.findViewById(R.id.et_conpass);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("CHANGE PASSWORD");
                builder.setView(resetpasswordlayout);
                builder.setPositiveButton("CHANG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldpassword = et_oldpass.getText().toString().trim();
                        String newpassword = et_newpass.getText().toString().trim();
                        String confirmpssword = et_conpass.getText().toString().trim();

                        if(oldpassword.isEmpty() || newpassword.isEmpty() || confirmpssword.isEmpty()){
                            massage("some feild are enpty");
                        }else {
                            progressDialog.show();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.RESET_PASSWORD_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            massage(response);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    massage(error.getMessage());
                                }
                            }){
                                @Override
                                protected Map<String,String> getParams() throws AuthFailureError{
                                    Map<String,String> params = new HashMap<>();
                                    params.put("oldpassword", oldpassword);
                                    params.put("newpassword", newpassword);
                                    params.put("confirmpasswod",confirmpssword);
                                    params.put("eamil", email);
                                    return params;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(stringRequest);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences.Editor editor = sharePreferences.edit();
                editor.clear();
                editor.commit();
                finish();;
                Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }
    public void massage(String massage){
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }
}