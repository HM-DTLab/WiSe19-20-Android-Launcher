package edu.hm.launcher;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Tutorial_Test extends AppCompatActivity {

    ImageView imageView;
    TextView title, descrpition;
    int positon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial__test);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)  {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        imageView = findViewById(R.id.tutorial_imageView);
        title = findViewById(R.id.titleText);
        descrpition = findViewById(R.id.description);

        if (positon == 0)   {
            Intent intent = getIntent();

            //Bundle bundle = this.getIntent().getExtras();
            //int pic = bundle.getInt("image");
            String aTitle = intent.getStringExtra("title");
            // Description

            //imageView.setImageResource(pic);
            //descrpition.setText();
            title.setText(aTitle);

            actionBar.setTitle(aTitle);

        }

    }
}
