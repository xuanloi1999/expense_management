package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.example.expensemanagement.Adapter.MenuAdapter;
import com.example.expensemanagement.Model.ItemMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    ArrayList<ItemMenu> itemMenus;
    MenuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.navListView);

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