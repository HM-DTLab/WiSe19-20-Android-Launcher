package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.hm.launcher.R;
import edu.hm.launcher.config.container.ConfigurationSingleTutorialContainer;
import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.SingleTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;
import edu.hm.launcher.config.parser.ConfigParseException;
import edu.hm.launcher.config.parser.XmlParserV2;
import edu.hm.launcher.config.parser.XmlParserV3;

public class Tutorials extends AppCompatActivity {

    ListView listView;
    List<SingleTutorialContainer> tutorialContainerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();

        String fileName = bundle.getString("file");

        try {
            tutorialContainerList = getTutorialContainer("tutorials" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConfigParseException e) {
            e.printStackTrace();
        }

        ArrayList<String> descriptionList = new ArrayList<>();

        for (int index = 0; index < tutorialContainerList.size(); index++)  {
            descriptionList.add(tutorialContainerList.get(index).getDescription());
        }

        final String[] description = new String[descriptionList.size()];

        for (int index = 0; index < descriptionList.size(); index++){
            description[index] = descriptionList.get(index);
        }


        ArrayList<String> imageList = new ArrayList<>();

        for (int index = 0; index < tutorialContainerList.size(); index++)  {
            imageList.add(tutorialContainerList.get(index).getImages());
        }

        final String[] images = new String[imageList.size()];

        for (int index = 0; index < imageList.size(); index++){
            images[index] = imageList.get(index);
        }

        int[] imageAsInt = null;

        try {
            imageAsInt = imageAsInt(images);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_tutorials);

        listView = findViewById(R.id.tutorial_listView);
        MyAdapterTutorials adapter = new MyAdapterTutorials(this, description, imageAsInt);
        listView.setAdapter(adapter);
    }

    private List<SingleTutorialContainer> getTutorialContainer(String fileName) throws IOException, ConfigParseException {
        InputStream xmlStream = getAssets().open(fileName);
        XmlParserV3 xmlParserV3 = new XmlParserV3();
        ConfigurationSingleTutorialContainer tutorialContainer = xmlParserV3.parseConfig(xmlStream);
        return tutorialContainer.getTutorials();
    }

    private int[] imageAsInt(String[] images) throws IOException {

        Log.d("Imagepath", "tutorials" + images[0]);


        int[] imagesAsInt = new int[images.length];

        for (int index = 0; index < images.length; index++) {
            imagesAsInt[index] = getAssets().open("tutorials" + images[index]).read();
            Log.d("imageRead", "" + imagesAsInt[index]);
        }
        return imagesAsInt;
    }

}
