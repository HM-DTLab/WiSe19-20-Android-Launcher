package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import edu.hm.launcher.R;
import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;
import edu.hm.launcher.config.parser.ConfigParseException;
import edu.hm.launcher.config.parser.XmlParserV2;

public class SecondTutorialPage extends AppCompatActivity {

    ListView listView;
    List<TutorialContainer> tutorialContainerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tutorial_page);

        Intent intent = getIntent();
        String[] files = intent.getStringArrayExtra("files");
        String[] titles = intent.getStringArrayExtra("titles");


        int[] images = {R.drawable.whatsapp, R.drawable.whatsapp, R.drawable.whatsapp, R.drawable.whatsapp};

        listView = findViewById(R.id.listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, titles, images);
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
}
