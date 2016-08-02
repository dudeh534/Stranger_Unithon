package com.unithon.cafee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences setting;
    private RestClient restClient = new RestClient(this);
    RecyclerView recyclerView_Main;
    final List<Recycler_item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.main_bar_bg);
        toolbar.setBackground(drawable);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        setting = getSharedPreferences("setting", 0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Write_Intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(Write_Intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView_Main.setEnabled(false);
                items.clear();
                restClient.get("workgroup", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                Recycler_item recycler_item = new Recycler_item(object.getInt("max_user_count"), object.getInt("join_user_count"), object.getString("title"), object.getString("text"),
                                        object.getString("workplace_name"), object.getString("workgroup_type"), object.getString("created_at"), object.getDouble("latitude"), object.getDouble("longtitude"));
                                items.add(recycler_item);
                            }
                            DataStorage.setItem_array(items);

                        } catch (IndexOutOfBoundsException e) {

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView_Main.setAdapter(new RecyclerAdapter(MainActivity.this, items, R.layout.activity_main));
                        recyclerView_Main.setEnabled(true);
                    }
                });

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView nickname = (TextView) header.findViewById(R.id.nickname);
        if (!setting.getString("nick", "").isEmpty()) {
            nickname.setText(setting.getString("nick", ""));
        } else {
            nickname.setText("못받아와");
        }
        recyclerView_Main = (RecyclerView) findViewById(R.id.Recycler_Main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView_Main.setHasFixedSize(true);
        recyclerView_Main.setLayoutManager(layoutManager);
        recyclerView_Main.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView_Main.setPadding(3, 3, 0, 0);
        restClient.get("workgroup", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        Recycler_item recycler_item = new Recycler_item(object.getInt("max_user_count"), object.getInt("join_user_count"), object.getString("title"), object.getString("text"),
                                object.getString("workplace_name"), object.getString("workgroup_type"), object.getString("created_at"), object.getDouble("latitude"), object.getDouble("longtitude"));
                        items.add(recycler_item);
                    }
                    DataStorage.setItem_array(items);

                } catch (IndexOutOfBoundsException e) {

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView_Main.setAdapter(new RecyclerAdapter(MainActivity.this, items, R.layout.activity_main));
            }
        });


        recyclerView_Main.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView_Main, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detail_Intent = new Intent(MainActivity.this, NMapActivity.class);
                detail_Intent.putExtra("position", position);
                startActivity(detail_Intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        /*
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(),"카페목록");
        adapter.addFragment(new MainFragment(), "지도");
        viewPager.setAdapter(adapter);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tabs);
        tablayout.setTabTextColors(Color.GRAY, Color.BLACK);
        tablayout.setupWithViewPager(viewPager);
*/


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.list) {
            return true;
            // Handle the camera action
        } else if (id == R.id.map) {
            Intent intent = new Intent(MainActivity.this, NMapMenu.class);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
