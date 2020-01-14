package edu.hm.launcher.tutorial;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.hm.launcher.R;

public class MyAdapterTutorials extends ArrayAdapter<String> {

    Context context;
    String description[];
    Drawable[] image;


    MyAdapterTutorials(Context c, String description[], Drawable[] image)   {
        super(c, R.layout.row, R.id.titleView, description);
        this.context = c;
        this.description = description;
        this.image = image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutorial_row = layoutInflater.inflate(R.layout.tutorial_row, parent, false);
        ImageView imageView = tutorial_row.findViewById(R.id.tutorial_imageView);
        TextView myDescription = tutorial_row.findViewById(R.id.tutorial_descriptionView);

        imageView.setImageDrawable(image[position]);
        myDescription.setText(description[position]);

        return tutorial_row;
    }
}
