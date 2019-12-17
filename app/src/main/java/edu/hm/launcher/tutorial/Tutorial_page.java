package edu.hm.launcher.tutorial;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.hm.launcher.MainActivity;
import edu.hm.launcher.R;

public class Tutorial_page extends AppCompatActivity {

    ListView listView;
    String[] tutorialTitle;
    TypedArray typedArrayDescriptions;
    int[] images = {R.drawable.googlemaps, R.drawable.note};
    float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tutorialTitle = getResources().getStringArray(R.array.tutorial_titel);
        typedArrayDescriptions = getResources().obtainTypedArray(R.array.tutorial_descriptions);
        setContentView(R.layout.activity_tutorial_page);
        final String[][] descriptions = createArray();


        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, tutorialTitle, descriptions, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)  {
                    Intent intent = new Intent(getApplicationContext(), Tutorial_Test.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[0]);
                    intent.putExtras(bundle);

                    intent.putExtra("title", tutorialTitle[0]);
                    intent.putExtra("position", ""+0);
                    intent.putExtra("description", descriptions[0]);
                    startActivity(intent);

                }
                else if (position == 1)  {
                    Intent intent = new Intent(getApplicationContext(), Tutorial_Test.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[1]);
                    intent.putExtras(bundle);

                    intent.putExtra("title", tutorialTitle[1]);
                    intent.putExtra("position", ""+1);
                    intent.putExtra("description", descriptions[1]);
                    startActivity(intent);
                }

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
                if(x1 < x2){
                    Intent i = new Intent(Tutorial_page.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                }
        }
        return true;
    }

    private String[][] createArray()    {
        Log.d("ARRAY","Swiped");
        int length = typedArrayDescriptions.length();
        String[][] array = new String[length][];
        for (int index = 0; index < length; index++) {
            int id = typedArrayDescriptions.getResourceId(index, 0);
            if (id > 0) {
                array[index] = getResources().getStringArray(id);
            }
        }
        typedArrayDescriptions.recycle();
        return array;
    }


}
