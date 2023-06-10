package com.example.expensemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.expensemanagement.Model.Category;
import com.example.expensemanagement.Model.TypeTransaction;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {

    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    EditText edtDate;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    Account account;

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    EditText edtMoney,edtTransactionAt, edtNote;
    Button btnAdd, btnExpense, btnIncome;
    boolean isExpense = true;
    String transactionUrl = "https://expense-managementap.up.railway.app/transaction";
    String url = "https://expense-managementap.up.railway.app/category/typeCategory?typeCategory=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        edtDate = findViewById(R.id.edtTransactionAt);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        edtMoney = findViewById(R.id.edtMoney);
        edtTransactionAt = findViewById(R.id.edtTransactionAt);
        edtNote = findViewById(R.id.edtNote);
        btnAdd = findViewById(R.id.btnAdd);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);


        actionToolBar();
        categories = new ArrayList<>();
        categoryNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.category_list,categoryNames);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");

        autoCompleteTextView.setAdapter(adapter);

        GetData(url+"EXPENSE");
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(TransactionActivity.this, "Item" + item, Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostData(transactionUrl);
            }
        });
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpense = false;
                btnExpense.setBackgroundResource(R.drawable.button_unselect);
                btnIncome.setBackgroundResource(R.drawable.button_select);
                GetData(url+"INCOME");
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpense = true;
                btnIncome.setBackgroundResource(R.drawable.button_unselect);
                btnExpense.setBackgroundResource(R.drawable.button_select);
                GetData(url+"EXPENSE");
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDate();
            }
        });
    }
    private void PickDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(calendar.DATE);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void actionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionActivity.this, HomeActivity.class);
                intent.putExtra("account",account );
                startActivity(intent);
            }
        });
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        categories.clear();
                        categoryNames.clear();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                switch (object.getString("typeCategory")){
                                    case "EXPENSE":
                                        categoryNames.add(object.getString("name"));
                                        categories.add(new Category(object.getString("_id"),object.getString("name"),object.getString("icon"), TypeTransaction.EXPENSE));
                                        break;
                                    case "INCOME":
                                        categoryNames.add(object.getString("name"));
                                        categories.add(new Category(object.getString("_id"),object.getString("name"),object.getString("icon"),TypeTransaction.INCOME));
                                        break;
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void PostData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(TransactionActivity.this, HomeActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TransactionActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: " + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Category result = new Category();
                for(int i = 0; i < categories.size(); i++){
                    if(categories.get(i).getName().equals(autoCompleteTextView.getText().toString())){
                        result = categories.get(i);
                        break;
                    }
                }

                Map<String, String> params = new HashMap<>();
                params.put("expense", edtMoney.getText().toString());
                params.put("note", edtNote.getText().toString());
                params.put("transactionAt", edtTransactionAt.getText().toString());
                params.put("money", edtMoney.getText().toString());
                params.put("categoryID", result.get_id().toString());
                params.put("accountID", account.get_id().toString());
                params.put("typeTransaction", isExpense? TypeTransaction.EXPENSE.toString(): TypeTransaction.INCOME.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}