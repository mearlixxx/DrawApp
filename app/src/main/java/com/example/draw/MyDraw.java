package com.example.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDraw extends View {

    private Paint paint;
    private ArrayList<Point> points = new ArrayList<>();

    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        points.forEach(point -> {
            canvas.drawCircle(point.x, point.y, 7, paint);
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
        }

        return true;
    }

    int mX = 0;
    int mY = 0;


    private void touchUp() {

    }

    private void touchMove(int x, int y) {
        points.add(new Point(x, y));

    }

    private void touchStart(int x, int y) {
        points.add(new Point(x, y));


    }

    public ArrayList<Point> getPoints() {
        return (ArrayList<Point>) points.clone();
    }

    public void clearLines() {
        points.clear();
        invalidate();
    }
}
