package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.hm.launcher.MainActivity;
import edu.hm.launcher.R;

public class TutorialPage extends AppCompatActivity {

    ListView listView;
    TypedArray typedArrayTitles;
    TypedArray typedArrayDescriptions;
    TypedArray typedArrayImages;
    float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typedArrayTitles = getResources().obtainTypedArray(R.array.tutorial_titles);
        typedArrayDescriptions = getResources().obtainTypedArray(R.array.tutorial_descriptions);
        typedArrayImages = getResources().obtainTypedArray(R.array.tutorial_images);
        setContentView(R.layout.activity_tutorial_page);
        final String[][] descriptions = createArray(typedArrayDescriptions);
        final String[][] titles = createArray(typedArrayTitles);
        //needs to be done better
        final int[][] images = {
                {R.drawable.googlemaps, R.drawable.googlemaps},
                {R.drawable.note, R.drawable.note}
        };



        listView = findViewById(R.id.listView);
        MyAdapterTutorialPage adapter = new MyAdapterTutorialPage(this, titles, descriptions, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Tutorials.class);

                intent.putExtra("image",images[position]);
                intent.putExtra("title", titles[position]);
                intent.putExtra("position", ""+position);
                intent.putExtra("description", descriptions[position]);
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
                    Intent intent = new Intent(TutorialPage.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                }
        }
        return true;
    }



    private String[][] createArray(TypedArray typedArray)    {
        int length = typedArray.length();
        String[][] array = new String[length][];
        for (int index = 0; index < length; index++) {
            int id = typedArray.getResourceId(index, 0);
            if (id > 0) {
                array[index] = getResources().getStringArray(id);
            }
        }
        typedArray.recycle();
        return array;
    }

    private int[][] createArrayInt(TypedArray typedArray)    {
        int length = typedArray.length();
        int[][] array = new int[length][];
        for (int index = 0; index < length; index++) {
            int id = typedArray.getResourceId(index, 0);
            if (id > 0) {
                array[index] = getResources().getIntArray(id);
            }
        }
        typedArray.recycle();
        return array;
    }


}
