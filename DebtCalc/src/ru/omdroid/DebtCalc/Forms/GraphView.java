package ru.omdroid.DebtCalc.Forms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.SeekBar;


class GraphView extends View {
    Context context;
    public GraphView(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.argb(-7, 231, 244, 235));
        canvas.drawPaint(paint);
        RectF rectF = new RectF();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        rectF.set(canvas.getWidth() - 200, 50, canvas.getWidth()-10, 80);
        canvas.drawRoundRect(rectF, 15, 15, paint);
        canvas.restore();

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        rectF.set(canvas.getWidth() - 200, 50, canvas.getWidth()-10, 80);
        canvas.drawRoundRect(rectF, 15, 15, paint);
        canvas.restore();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        rectF.set(10, 50, canvas.getWidth()-150, 80);
        canvas.drawRoundRect(rectF, 15, 15, paint);
        canvas.restore();

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        rectF.set(10, 50, canvas.getWidth()-150, 80);
        canvas.drawRoundRect(rectF, 15, 15, paint);
        canvas.restore();
    }
}
