package edu.hm.launcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Tutorial_page extends AppCompatActivity {

    ListView listView;
    String[] tutorialTitle = {"Maps", "Notizen"};
    String[][] descritption = {
            {"Route erstellen", "Maps öffnen", "route eingeben"},
            {"Notiz erstellen", "Notizen öffnen", "neue Notiz erstellen"}
    };
    int images[] = {R.drawable.googlemaps,R.drawable.note};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);


        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, tutorialTitle,descritption, images);
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
                    intent.putExtra("description", descritption[0]);
                    startActivity(intent);

                }
                else if (position == 1)  {
                    Intent intent = new Intent(getApplicationContext(), Tutorial_Test.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[1]);
                    intent.putExtras(bundle);

                    intent.putExtra("title", tutorialTitle[1]);
                    intent.putExtra("position", ""+1);
                    intent.putExtra("description", descritption[1]);
                    startActivity(intent);
                }

            }
        });



    }


}
