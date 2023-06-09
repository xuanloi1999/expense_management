package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.expensemanagement.Adapter.MenuAdapter;
import com.example.expensemanagement.Adapter.TransactionAdapter;
import com.example.expensemanagement.Model.ItemMenu;
import com.example.expensemanagement.Model.Transaction;
import com.example.expensemanagement.Model.TypeTransaction;
import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    ArrayList<ItemMenu> itemMenus;
    ArrayList<Transaction> transactions;
    MenuAdapter adapter;
    TransactionAdapter transactionAdapter;
    PieChart pieChart;

    Button btnExpense, btnIncome, btnDay, btnMonth, btnYear, btnLogin;
    ListView listViewTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.navListView);
        pieChart = findViewById(R.id.pieChart);
        btnExpense = findViewById(R.id.btnExpense);
        btnIncome = findViewById(R.id.btnIncome);
        btnDay = findViewById(R.id.btnDay);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);
        listViewTransaction = findViewById(R.id.listViewTransaction);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        actionMenu();
        transactionList();
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

    private  void actionMenu(){
        itemMenus = new ArrayList<>();
        itemMenus.add(new ItemMenu("Import",R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("Gallery",R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("SlideShow",R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("Tools",R.drawable.ic_action_menu));

        adapter = new MenuAdapter(this,R.layout.dong_item,itemMenus);
        listView.setAdapter(adapter);
    }

    private void transactionList(){
        transactions = new ArrayList<>();
        transactions.add(new Transaction((double)20000, "Qua Tang", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)30000, "Qua Tang 2", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));
        transactions.add(new Transaction((double)40000, "Qua Tang 3", Calendar.getInstance().getTime(),"Note", TypeTransaction.EXPENSE, "aaa", "aaa" ));

        transactionAdapter = new TransactionAdapter(this,R.layout.transaction_item, transactions);
        listViewTransaction.setAdapter(transactionAdapter);
    }

    private void addToPieChart(){
        pieChart.addPieSlice(new PieModel("Integer 1", 20, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Integer 2", 30, Color.parseColor("#66bb6a")));
        pieChart.addPieSlice(new PieModel("Integer 3", 60, Color.parseColor("#aa6bb3")));
        pieChart.addPieSlice(new PieModel("Integer 4", 5, Color.parseColor("#12bb7a")));

        pieChart.startAnimation();
    }
}