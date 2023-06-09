package com.example.expensemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensemanagement.Adapter.MenuAdapter;
import com.example.expensemanagement.Model.Account;
import com.example.expensemanagement.Model.ItemMenu;
import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    ArrayList<ItemMenu> itemMenus;
    MenuAdapter adapter;

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.navListView);
        //PostData("https://expense-managementap.up.railway.app/account");

        actionToolBar();
        actionMenu();
    }

    private void GetData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);
                    account = new Account(object.getString("email"),object.getString("username"),object.getString("password"), Double.parseDouble(object.getString("money")));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(HistoryActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", "xuanloiabc");
                params.put("password", "123");
                params.put("username", "abc");
                params.put("money", "20000");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData("https://expense-managementap.up.railway.app/account/username?username=abc");
                Toast.makeText(HistoryActivity.this, account.toString(), Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void actionMenu() {
        itemMenus = new ArrayList<>();
        itemMenus.add(new ItemMenu("Import", R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("Gallery", R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("SlideShow", R.drawable.ic_action_menu));
        itemMenus.add(new ItemMenu("Tools", R.drawable.ic_action_menu));

        adapter = new MenuAdapter(this, R.layout.dong_item, itemMenus);
        listView.setAdapter(adapter);
    }
}