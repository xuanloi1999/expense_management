package com.example.expensemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtConfirmPassword, edtUsername, edtMoney;
    Button btnRegister;
    TextView txtError;
    private String url = "https://expense-managementap.up.railway.app/account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtUsername = findViewById(R.id.edtUsername);
        edtMoney = findViewById(R.id.edtMoney);
        btnRegister = findViewById(R.id.btnRegister);
        txtError = findViewById(R.id.txtError);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate(edtEmail.getText().toString())){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Email field input is not email format");
                }
                else if(!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Password and Confirm Password not match");
                }
                else if(edtUsername.getText().toString().isEmpty()){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Username cannot empty");
                }
                else if (isNumeric(edtUsername.getText().toString().charAt(0) + "")){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Username cannot start with numberic");
                    Log.d("Test",edtUsername.getText().toString());
                }
                else if(edtMoney.getText().toString().isEmpty()){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Money cannot empty");
                }
                else if(!isNumeric(edtMoney.getText().toString())){
                    Log.d("Test",edtMoney.getText().toString());
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Money field contain character");
                }
                else{
                    PostData(url);
                }
            }
        });
    }
    private void PostData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("username", edtUsername.getText().toString());
                intent.putExtra("password", edtPassword.getText().toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtError.setVisibility(View.VISIBLE);
                txtError.setText("Username or email is exits");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", edtEmail.getText().toString());
                params.put("password", edtPassword.getText().toString());
                params.put("username", edtUsername.getText().toString());
                params.put("money", edtMoney.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}