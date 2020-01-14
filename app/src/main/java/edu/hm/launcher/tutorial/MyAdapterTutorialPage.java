package edu.hm.launcher.tutorial;

import android.content.Context;
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

public class MyAdapterTutorialPage extends ArrayAdapter<String> {

    Context context;
    String title[];
    int image[];


    MyAdapterTutorialPage(Context c, String title[], int image[])   {
        super(c, R.layout.row, R.id.titleView, title);
        this.context = c;
        this.title = title;
        this.image = image;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTutorialTitle = row.findViewById(R.id.titleView);

        images.setImageResource(image[position]);
        myTutorialTitle.setText(title[position]);

        return row;
    }
}
