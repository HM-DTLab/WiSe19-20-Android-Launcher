package edu.hm.launcher.tutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.hm.launcher.R;

public class MyAdapterTutorialPage extends ArrayAdapter<String> {

    Context context;
    String rTitle[];
    String description[];
    int[] image;


    MyAdapterTutorialPage(Context c, String title[],String description[], int[] image)   {
        super(c, R.layout.row, R.id.titleView, description);
        this.context = c;
        this.rTitle = title;
        this.description = description;
        this.image = image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutorial_row = layoutInflater.inflate(R.layout.tutorial_row, parent, false);
        ImageView images = tutorial_row.findViewById(R.id.tutorial_imageView);
        TextView myTutorialTitle = tutorial_row.findViewById(R.id.tutorial_titleView);
        TextView myDescription = tutorial_row.findViewById(R.id.tutorial_descriptionView);

        images.setImageResource(image[position]);
        myTutorialTitle.setText(rTitle[position]);
        myDescription.setText(description[position]);

        return tutorial_row;
    }
}
