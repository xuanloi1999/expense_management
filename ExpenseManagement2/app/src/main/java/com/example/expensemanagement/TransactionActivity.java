package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Model.Category;
import com.example.expensemanagement.Model.TypeTransaction;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TransactionActivity extends AppCompatActivity {

    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    EditText edtDate;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;


    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    EditText edtMoney,edtTransactionAt;
    String url = "https://expense-managementap.up.railway.app/category/typeCategory?typeCategory=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        edtDate = findViewById(R.id.transactionAt);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);


        actionToolBar();
        categories = new ArrayList<>();
        categoryNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.category_list,categoryNames);

        autoCompleteTextView.setAdapter(adapter);

        GetData(url+"EXPENSE");
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(TransactionActivity.this, "Item" + item, Toast.LENGTH_SHORT).show();
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
                                        categories.add(new Category(object.getString("name"),object.getString("icon"), TypeTransaction.EXPENSE));
                                        break;
                                    case "INCOME":
                                        categoryNames.add(object.getString("name"));
                                        categories.add(new Category(object.getString("name"),object.getString("icon"),TypeTransaction.INCOME));
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
}