package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Adapter.CategoryAdapter;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.Callback;
import com.example.expensemanagement.Model.Category;
import com.example.expensemanagement.Model.TypeTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    GridView gridView;
    Button btnExpense, btnIncome, btnAddCategory;
    ArrayList<Category> categories;
    CategoryAdapter adapter;
    String url = "https://expense-managementap.up.railway.app/category/typeCategory?typeCategory=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gridView = findViewById(R.id.gridView);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        categories = new ArrayList<>();
        adapter = new CategoryAdapter(this,R.layout.category_item,categories);
        gridView.setAdapter(adapter);
        GetData(url+ "EXPENSE");
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData(url+ "EXPENSE");
                btnIncome.setBackgroundResource(R.drawable.button_unselect);
                btnExpense.setBackgroundResource(R.drawable.button_select);
            }
        });
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData(url+ "INCOME");
                btnExpense.setBackgroundResource(R.drawable.button_unselect);
                btnIncome.setBackgroundResource(R.drawable.button_select);
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
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                switch (object.getString("typeCategory")){
                                    case "EXPENSE":
                                        categories.add(new Category(object.getString("name"),object.getString("icon"),TypeTransaction.EXPENSE));
                                        break;
                                    case "INCOME":
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