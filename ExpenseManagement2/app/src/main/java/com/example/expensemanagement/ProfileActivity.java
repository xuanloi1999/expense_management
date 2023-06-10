package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.expensemanagement.Adapter.MenuAdapter;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.ItemMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    ArrayList<ItemMenu> itemMenus;
    MenuAdapter adapter;

    EditText edtEmail, edtPassword, edtUsername, edtMoney;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.navListView);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        edtMoney = findViewById(R.id.edtMoney);
        btnLogout = findViewById(R.id.btnLogout);

        //Intent
        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        if(account!= null){
            edtEmail.setText(account.getEmail());
            edtPassword.setText(account.getPasword());
            edtUsername.setText(account.getUsername());
            edtMoney.setText(account.getMoney()+"");
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.commit();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        actionToolBar();
        actionMenu();
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
}