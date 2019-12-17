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

import edu.hm.launcher.R;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    String rTitle[][];
    String description[][];
    int image[][];


    MyAdapter(Context c, String title[][],String description[][], int image[][])   {
        super(c, R.layout.row, R.id.titleView, title[1]);
        this.context = c;
        this.rTitle = title;
        this.description = description;
        this.image = image;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTutorialTitle = row.findViewById(R.id.titleView);

        images.setImageResource(image[position][0]);
        myTutorialTitle.setText(rTitle[position][0]);

        return row;
    }
}
