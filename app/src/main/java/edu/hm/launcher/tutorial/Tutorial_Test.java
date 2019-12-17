package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.hm.launcher.R;

public class Tutorial_Test extends AppCompatActivity {

    ListView listView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        int[] images = bundle.getIntArray("image");
        String[] title = intent.getStringArrayExtra("title");
        String[] description = intent.getStringArrayExtra("description");

        setContentView(R.layout.activity_tutorial__test);

        listView = findViewById(R.id.tutorial_listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, title, description, images);
        listView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)  {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        if (position == 0)   {

            int pic = bundle.getInt("image");
            String aTitle = intent.getStringExtra("title");

            for (int index = 0; index < description.length; index++)    {
                String[] firstDescription = intent.getStringArrayExtra("description");
            }
            // Description

            actionBar.setTitle(aTitle);

        }
        if (position == 1)   {

            int pic = bundle.getInt("image");
            String aTitle = intent.getStringExtra("title");

            for (int index = 0; index < description.length; index++)    {
                String[] firstDescription = intent.getStringArrayExtra("description");
            }
            // Description

            actionBar.setTitle(aTitle);

        }
    }
}
