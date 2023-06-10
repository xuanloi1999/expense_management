package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText edtEmail, edtPassword;
    TextView txtError;
    Account account;
    SharedPreferences sharedPreferences;
    String url = "https://expense-managementap.up.railway.app/account/username?username=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtError = findViewById(R.id.txtError);

        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);

        //lay gia tri sharepreference
        edtEmail.setText(sharedPreferences.getString("username",""));
        edtPassword.setText(sharedPreferences.getString("password",""));


        //Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        if(username != null && password != null){
            edtEmail.setText(username);
            edtPassword.setText(password);
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData(url + edtEmail.getText().toString(), new Callback<Account>() {
                    @Override
                    public void onSuccess(Account result) {

                        String password = edtPassword.getText().toString();
                        String username = edtEmail.getText().toString();
                        Log.d("Account", password + " " + username);
                        if(password.isEmpty() || username.isEmpty()){
                            txtError.setVisibility(View.VISIBLE);
                        }
                        if(result.getPasword().equals(password) && result.getUsername().equals(username)){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", result.getUsername());
                            editor.putString("password", result.getPasword());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("account", result);
                            startActivity(intent);
                        }else{
                            txtError.setVisibility(View.VISIBLE);
                            txtError.setText("Username or password not correct");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        txtError.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

    }

    private void GetData(String url, final Callback<Account> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            Account account = new Account(object.getString("_id"), object.getString("email"), object.getString("password"), object.getString("username"), Double.parseDouble(object.getString("money")));
                            callback.onSuccess(account);
                        } catch (JSONException e) {
                            callback.onError(e + "");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error + "");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}