package com.example.draw;

import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private double maxLetDist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.textF);
        text.setText("0");

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                maxLetDist = progressValue / 100.0;
                text.setText(String.valueOf(progressValue));
            }
        });

        MyDraw draw = findViewById(R.id.myDraw2);
        MyDraw draw1 = findViewById(R.id.myDraw3);


        findViewById(R.id.button).setOnClickListener(v -> {


            showToast(isSame(draw, draw1));
        });

        findViewById(R.id.clear).setOnClickListener(v -> {
            draw.clearLines();
            draw1.clearLines();
        });
    }

    private void showToast(boolean same) {
        Toast toast = new Toast(getApplicationContext());
        toast.setText(same ? "YES" : "NO");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean isSame(MyDraw draw, MyDraw draw1) {

        ArrayList<Point> points = resize(draw.getPoints());
        ArrayList<Point> points1 = resize(draw1.getPoints());

        double maxDist = Double.MIN_VALUE;

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            double minDist = Double.MAX_VALUE;
            for (int j = 0; j < points1.size(); j++) {
                Point point1 = points1.get(j);
                minDist = Math.min(minDist, getDistance(point, point1));
            }
            maxDist = Math.max(maxDist, minDist);
        }

        return maxDist < maxLetDist;
    }

    private double getDistance(Point point, Point point1) {
        return Math.sqrt(Math.pow(point.x - point1.x, 2) + Math.pow(point.y - point1.y, 2));
    }

    private ArrayList resize(ArrayList<Point> points) {
        ArrayList<Float> pointsX = new ArrayList<>();
        ArrayList<Float> pointsY = new ArrayList<>();

        points.forEach(point -> {
            pointsX.add((float) point.x);
            pointsY.add((float) point.y);
        });

        Collections.sort(pointsX);
        Collections.sort(pointsY);

        float minx = pointsX.get(0);
        float miny = pointsY.get(0);

        float maxx = pointsX.get(pointsX.size() - 1);
        float maxy = pointsY.get(pointsY.size() - 1);

        float S = Math.max(maxx - minx, maxy - miny);

        ArrayList<Float> tPointsX = (ArrayList<Float>) pointsX.clone();
        ArrayList<Float> tPointsY = (ArrayList<Float>) pointsY.clone();

        pointsX.clear();
        pointsY.clear();

        tPointsX.forEach(aFloat -> {
            pointsX.add((aFloat - minx) / S);
        });

        tPointsY.forEach(aFloat -> {
            pointsY.add((aFloat - miny) / S);
        });

        points.clear();

        for (int i = 0; i < pointsX.size(); i++) {
            //points.add(new Point(Integer.parseInt(String.valueOf(pointsX.get(i))), Integer.parseInt(String.valueOf(pointsY.get(i)))));
            points.add(new Point(pointsX.get(i), pointsY.get(i)));
        }

        return points;

    }
}