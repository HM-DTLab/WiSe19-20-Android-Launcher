package edu.hm.launcher.tutorial;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.hm.launcher.R;
import edu.hm.launcher.config.container.ConfigurationSingleTutorialContainer;
import edu.hm.launcher.config.container.SingleTutorialContainer;
import edu.hm.launcher.config.parser.ConfigParseException;
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

        Drawable[] drawables = null;

        try {
            drawables = imageAsDrawable(images);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_tutorials);

        listView = findViewById(R.id.tutorial_listView);
        MyAdapterTutorials adapter = new MyAdapterTutorials(this, description, drawables);
        listView.setAdapter(adapter);
    }

    private List<SingleTutorialContainer> getTutorialContainer(String fileName) throws IOException, ConfigParseException {
        InputStream xmlStream = getAssets().open(fileName);
        XmlParserV3 xmlParserV3 = new XmlParserV3();
        ConfigurationSingleTutorialContainer tutorialContainer = xmlParserV3.parseConfig(xmlStream);
        return tutorialContainer.getTutorials();
    }

    private Drawable[] imageAsDrawable(String[] images) throws IOException {

        Drawable[] drawables = new Drawable[images.length];

        for (int index = 0; index < images.length; index++) {
            InputStream inputStream = getAssets().open("tutorials" + images[index]);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            drawables[index] = drawable;
        }
        return drawables;
    }

}
