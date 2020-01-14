package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.hm.launcher.MainActivity;
import edu.hm.launcher.R;
import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;
import edu.hm.launcher.config.parser.ConfigParseException;
import edu.hm.launcher.config.parser.XmlParserV2;

public class FirstTutorialPage extends AppCompatActivity {

    ListView listView;
    List<TutorialContainer> tutorialContainerList;
    float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            tutorialContainerList = getTutorialContainer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConfigParseException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_tutorial_page);
        Log.d("Test","Just a Test");


        ArrayList<String> titleList = new ArrayList<>();

        Log.d("Size",""+tutorialContainerList.size());
        for (int index = 0; index < tutorialContainerList.size(); index++)  {
            titleList.add(tutorialContainerList.get(index).getTutorialAppTitle());

        }

        final String[] titles = new String[titleList.size()];

        for (int index = 0; index < titleList.size(); index++){
            titles[index] = titleList.get(index);
            Log.d("Title",titleList.get(index));
        }

        int[] images = {R.drawable.whatsapp,R.drawable.googlemaps};

        listView = findViewById(R.id.listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, titles, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), SecondTutorialPage.class);

                intent.putExtra("files", tutorialContainerList.get(position).getTutorialFiles());
                intent.putExtra("titles", tutorialContainerList.get(position).getTutorialTitle());
                startActivity(intent);

            }
        });
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 > x2){
                    Intent intent = new Intent(FirstTutorialPage.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                }
        }
        return true;
    }

    private List<TutorialContainer> getTutorialContainer() throws IOException, ConfigParseException {
        InputStream xmlStream = this.getAssets().open("tutorials/tutorials_root.xml");
        XmlParserV2 xmlParserV2 = new XmlParserV2();
        Log.d("In Methode", "HERE");
        ConfigurationTutorialContainer tutorialContainer = xmlParserV2.parseConfig(xmlStream);
        return tutorialContainer.getTutorials();
    }


}
