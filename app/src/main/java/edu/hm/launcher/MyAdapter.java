package edu.hm.launcher;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    String rTitle[];
    int image[];


    MyAdapter(Context c, String title[], int image[])   {
        super(c, R.layout.row, R.id.textView, title);
        this.context = c;
        this.rTitle = title;
        this.image = image;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        //ImageView images = row.findViewById(R.id.image)
        TextView tutorialTitle = row.findViewById(R.id.textView);

        //images.setImageResource(image[position]);
        tutorialTitle.setText(rTitle[position]);



        return row;
    }
}
