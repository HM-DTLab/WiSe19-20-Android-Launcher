package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import edu.hm.launcher.R;

public class Tutorials extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        int[] images = bundle.getIntArray("image");
        String[] title = intent.getStringArrayExtra("title");
        String[] description = intent.getStringArrayExtra("description");

        setContentView(R.layout.activity_tutorials);

        listView = findViewById(R.id.tutorial_listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, title, description, images);
        listView.setAdapter(adapter);

    }
}
