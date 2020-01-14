package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;

import edu.hm.launcher.R;

public class SecondTutorialPage extends AppCompatActivity {

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tutorial_page);

        Intent intent = getIntent();
        String[] files = intent.getStringArrayExtra("files");
        String[] titles = intent.getStringArrayExtra("titles");
        String drawable = intent.getStringExtra("drawable");

        Drawable[] drawables = new Drawable[titles.length];

        for (int index = 0; index < titles.length; index++) {
            try {
                drawables[index] = imageAsDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listView = findViewById(R.id.listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, titles, drawables);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Tutorials.class);

                intent.putExtra("file", files[position]);
                startActivity(intent);

            }
        });
    }

    private Drawable imageAsDrawable(String image) throws IOException {
        InputStream inputStream = getAssets().open("tutorials" + image);
        return Drawable.createFromStream(inputStream, null);

    }
}
