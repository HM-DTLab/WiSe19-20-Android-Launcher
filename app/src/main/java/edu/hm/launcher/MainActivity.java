package edu.hm.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import edu.hm.launcher.tutorial.FirstTutorialPage;

public class MainActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Intent intent = new Intent(this, FirstTutorialPage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                }
        }
        return true;
    }
}
