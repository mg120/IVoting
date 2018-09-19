package com.alsalil.web.vote.Section;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsalil.web.vote.Rate.Rate;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Section extends AppCompatActivity {

    private TextView section_title;
    private ImageView section_img;
    private RecyclerView section_recycler;
    private TextView back_txt;
    private LayoutAnimationController controller ;
    public static String cat_Id;
    public static String catName;
    private ArrayList<SectionModel> sectionModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        section_recycler = (RecyclerView) findViewById(R.id.section_recyclerV);
        section_title = (TextView) findViewById(R.id.title);
        section_title.setTypeface(custom_font);
        back_txt = (TextView) findViewById(R.id.back);
        //section_img = (ImageView) findViewById(R.id.section_image_view);

        Intent intent = getIntent();
        catName = intent.getExtras().getString("catName");
        cat_Id = intent.getExtras().getString("catId");

        section_title.setText(catName);
        back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int status = jsonObject.getInt("status");

                    if (status == 1) {
                        // Toast.makeText(Section.this , ""+ status , Toast.LENGTH_SHORT).show();
                        JSONArray sections_arr = jsonObject.getJSONArray("sections");
                        for (int i = 0; i < sections_arr.length(); i++) {
                            JSONObject section_data = sections_arr.getJSONObject(i);
                            String name = section_data.getString("name");
                            String image = section_data.getString("imageName");
                            String item_id = section_data.getString("id");

                            sectionModels.add(new SectionModel(image, name, item_id));
                        }
                    } else {
                        // Toast.makeText(Section.this, "error cat Name",Toast.LENGTH_SHORT).show();
                    }

                    if(sectionModels != null) {

                        controller = AnimationUtils.loadLayoutAnimation(Section.this, R.anim.layout_slide_from_bottom);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Section.this, 2);
                        section_recycler.setLayoutManager(layoutManager);
                        section_recycler.setHasFixedSize(true);
                        SectionAdapter adapter = new SectionAdapter(Section.this, sectionModels, catName);
                        section_recycler.setAdapter(adapter);

                        // set Animation to recycler view ...
                        section_recycler.setLayoutAnimation(controller);
                        section_recycler.getAdapter().notifyDataSetChanged();
                        section_recycler.scheduleLayoutAnimation();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Section.this);
                        builder.setMessage("No Internet ...")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        SectionRequest sectionRequest = new SectionRequest(catName, listener);
        RequestQueue queue = Volley.newRequestQueue(Section.this);
        queue.add(sectionRequest);
    }
}
