package com.alsalil.web.vote;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alsalil.web.vote.Questions.Question;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.Gift.Gifts;
import com.alsalil.web.vote.Profile.Profile;
import com.alsalil.web.vote.Requests.LogIn;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private String category_url = "http://vote4favorite.com/vote/api/category";
    private String winner_url = "http://vote4favorite.com/vote/api/winner";
    public static String user_id;
    private ArrayList<MainModel> data_list = new ArrayList<>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    ImageView winnerImage_View ;
    TextView  winner_name_txt, gift_name_txt ;
    LayoutAnimationController controller ;
    // Navigation header Data .....
    NavigationView navigationView;
    ImageView headerImage;
    TextView userName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        winnerImage_View = (ImageView) findViewById(R.id.winner_image);
        winner_name_txt = (TextView) findViewById(R.id.winner_name);
        gift_name_txt = (TextView) findViewById(R.id.gift_name);

        // get data of the Winner ....
        winner_Data();

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        String email = intent.getStringExtra("email");
        String user_name = intent.getStringExtra("userName");
        String user_image = intent.getStringExtra("imageName");

        // Recycler View .....
       // AnimationUtils.
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        StringRequest request = new StringRequest(Request.Method.POST, category_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    dataparsing(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Internet")
                        .setMessage("Error Connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);

        ///////////// ......
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        headerImage = (ImageView) hView.findViewById(R.id.imageView);

        // Navigation header Main text ...
        userName = (TextView) hView.findViewById(R.id.Name);
        userName.setTypeface(custom_font);
        userName.setText(user_name);

        // Navigation header email text ...
        userEmail = (TextView) hView.findViewById(R.id.Email_txt);
        userEmail.setTypeface(custom_font);
        userEmail.setText(email);
        Picasso.with(MainActivity.this).load("http://vote4favorite.com/vote/storage/app/images/" + user_image).into(headerImage);

        // hide gift in free membership ...
//        if (LogIn.type == 1) {
//            Menu nav_menu = navigationView.getMenu();
//            nav_menu.findItem(R.id.gifts).setVisible(false);
//        }


    }

    private void winner_Data() {

        StringRequest winner_request = new StringRequest(Request.Method.POST, winner_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject main_obj = new JSONObject(response);
                            boolean winner_status = main_obj.getBoolean("status");

                            if (winner_status){
                                JSONObject winner_obj = main_obj.getJSONObject("winner");
                                String winner_name = winner_obj.getString("userName");
                                String winner_img = winner_obj.getString("imageName");
                                JSONObject gift_obj = main_obj.getJSONObject("gift");
                                String details = gift_obj.getString("details");

                                Picasso.with(MainActivity.this).load("http://vote4favorite.com/vote/storage/app/images/" +winner_img).into(winnerImage_View);
                                winner_name_txt.setText(winner_name);
                                gift_name_txt.setText(details);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(winner_request);
    }

    // Main Activity data parsing method ...
    private void dataparsing(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        int success = jsonObject.getInt("status");

        if (success == 1) {

            JSONArray jsonArray = jsonObject.getJSONArray("category");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject data_obj = jsonArray.getJSONObject(i);
                String name = data_obj.getString("name");
                String icon = data_obj.getString("icon");
                String icon_image = data_obj.getString("iconImage");
                String cat_Id = data_obj.getString("id");


                data_list.add(new MainModel(icon_image, name, cat_Id));
            }
            controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_fall_down);
            adapter = new MainAdapter(MainActivity.this, data_list);
            recyclerView.setAdapter(adapter);

            // set Animation to recycler view ...
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Logout of the App")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //super.onBackPressed();
                            pref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                            editor = pref.edit();
                            editor.clear();
                            editor.apply();
                            finish();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // put searchable items in ArrayList ...
                newText = newText.toLowerCase();

                ArrayList<MainModel> newlist = new ArrayList<>();
                for (MainModel item : data_list) {
                    if (item.getTitle().toLowerCase().contains(newText)) {
                        newlist.add(item);
                    }
                }

                adapter = new MainAdapter(MainActivity.this, newlist);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });
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

        if (id == R.id.profile) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, Profile.class));
        } else if (id == R.id.gifts) {

            startActivity(new Intent(MainActivity.this, Gifts.class));

        } else if (id == R.id.questions) {

            startActivity(new Intent(MainActivity.this, Question.class));

        } else if (id == R.id.share_app) {

            int applicationNameId = this.getApplicationInfo().labelRes;
            final String appPackageName = this.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
            String text = "Install this cool application: ";
            String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
            i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
            startActivity(Intent.createChooser(i, "Share link:"));

        } else if (id == R.id.rate_app) {

//            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
//            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//            // To count with Play market backstack, After pressing back button,
//            // to taken back to our application, we need to add following flags to intent.
//            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
//                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            try {
//                startActivity(goToMarket);
//            } catch (ActivityNotFoundException e) {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
//            }

        }  else if (id == R.id.contact_us) {
           startActivity(new Intent(MainActivity.this, Contact.class));
        }  else if (id == R.id.about_app) {
            startActivity(new Intent(MainActivity.this, About.class));
        } else if (id == R.id.logout) {
            pref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
            editor = pref.edit();
            editor.clear();
            editor.apply();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
