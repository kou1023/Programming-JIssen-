package com.example.puroguramingujissenii;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyView extends View {
    private final ArrayList<Integer> array_x;
    private final ArrayList<Integer> array_y;
    private final ArrayList<Boolean> array_status;

    public MyView(Context context) {
        super(context);
        array_x = new ArrayList<>();
        array_y = new ArrayList<>();
        array_status = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                array_x.add(x);
                array_y.add(y);
                array_status.add(false);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                array_x.add(x);
                array_y.add(y);
                array_status.add(true);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                array_x.add(x);
                array_y.add(y);
                array_status.add(true);
                invalidate();
                break;
        }
        return true;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), p);

        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);

        for (int i = 1; i < array_status.size(); i++) {
            if (array_status.get(i)) {
                canvas.drawLine(array_x.get(i - 1), array_y.get(i - 1),
                        array_x.get(i), array_y.get(i), p);
            }
        }
    }
}
