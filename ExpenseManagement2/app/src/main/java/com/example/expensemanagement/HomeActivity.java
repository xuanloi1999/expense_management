package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Adapter.MenuAdapter;
import com.example.expensemanagement.Adapter.TransactionAdapter;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.Category;
import com.example.expensemanagement.Model.ItemMenu;
import com.example.expensemanagement.Model.Transaction;
import com.example.expensemanagement.Model.TypeTransaction;
import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    ArrayList<ItemMenu> itemMenus;
    ArrayList<Transaction> transactions;
    TransactionAdapter transactionAdapter;
    PieChart pieChart;
    LinearLayout profile;
    Button btnExpense, btnIncome, btnDay, btnMonth, btnYear, btnLogin, btnLogout, btnAddTransaction;
    ListView listViewTransaction;
    SharedPreferences sharedPreferences;
    String url = "https://expense-managementap.up.railway.app/transaction?accountID=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        pieChart = findViewById(R.id.pieChart);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);
        btnDay = findViewById(R.id.btnDay);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);
        listViewTransaction = findViewById(R.id.listViewTransaction);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        profile = findViewById(R.id.profile);

        transactions = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(this,R.layout.transaction_item, transactions );
        listViewTransaction.setAdapter(transactionAdapter);

        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        if(account != null){
            btnLogin.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            GetTransactionData(url+ account.get_id() +"&typeTransaction=EXPENSE");
        }else{
            profile.setVisibility(View.INVISIBLE);
            btnAddTransaction.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomeActivity.this, TransactionActivity.class);
                startActivity(intent1);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.commit();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIncome.setBackgroundResource(R.drawable.button_unselect);
                btnExpense.setBackgroundResource(R.drawable.button_select);
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExpense.setBackgroundResource(R.drawable.button_unselect);
                btnIncome.setBackgroundResource(R.drawable.button_select);
                GetTransactionData(url+ account.get_id() +"&typeTransaction=INCOME");
            }
        });

        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDay.setBackgroundResource(R.drawable.button_select);
                btnMonth.setBackgroundResource(R.drawable.button_unselect);
                btnYear.setBackgroundResource(R.drawable.button_unselect);
            }
        });

        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMonth.setBackgroundResource(R.drawable.button_select);
                btnDay.setBackgroundResource(R.drawable.button_unselect);
                btnYear.setBackgroundResource(R.drawable.button_unselect);
            }
        });

        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnYear.setBackgroundResource(R.drawable.button_select);
                btnMonth.setBackgroundResource(R.drawable.button_unselect);
                btnDay.setBackgroundResource(R.drawable.button_unselect);
            }
        });

        addToPieChart();
        actionToolBar();

    }

    private void actionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void GetTransactionData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        transactions.clear();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                switch (object.getString("typeTransaction")){
                                    case "EXPENSE":
                                        transactions.add(new Transaction(object.getString("_id"),Double.parseDouble(object.getString("expense")),object.getString("categoryID"),object.getString("note"),TypeTransaction.EXPENSE, object.getString("accountID")));
                                        break;
                                    case "INCOME":
                                        transactions.add(new Transaction(object.getString("_id"),Double.parseDouble(object.getString("expense")),object.getString("categoryID"),object.getString("note"),TypeTransaction.INCOME, object.getString("accountID")));
                                        break;
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        transactionAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "onErrorResponse: " + error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void addToPieChart(){
        if(transactions.size() != 0){
            ArrayList<Transaction> result = SumTransaction();
            for(int i = 0; i< result.size(); i++){
                pieChart.addPieSlice(new PieModel(result.get(i).getCategory(), result.get(i).getExpense().floatValue(), Color.parseColor("#FFA72"+i)));
            }
        }
        else{
            pieChart.addPieSlice(new PieModel("Integer 1", 20, Color.parseColor("#FFA726")));
            pieChart.addPieSlice(new PieModel("Integer 2", 30, Color.parseColor("#66bb6a")));
            pieChart.addPieSlice(new PieModel("Integer 3", 60, Color.parseColor("#aa6bb3")));
            pieChart.addPieSlice(new PieModel("Integer 4", 5, Color.parseColor("#12bb7a")));
        }

//

        pieChart.startAnimation();
    }

    private ArrayList<Transaction> SumTransaction(){
        ArrayList<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(transactions.get(0));
        for (int j =0 ; j < transactions1.size(); j++){
            for(int i = 1; i< transactions.size(); i++){
                double sum = transactions1.get(j).getExpense();
                if(transactions.get(i).get_id().equals(transactions1.get(j).get_id())){
                    sum += transactions.get(i).getExpense();
                    transactions1.get(j).setExpense(sum);
                }
                else{
                    transactions1.add(transactions.get(i));
                }
            }
        }
        return transactions1;
    }
}