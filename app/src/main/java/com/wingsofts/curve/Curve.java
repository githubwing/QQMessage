package com.wingsofts.curve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wing on 16/1/10.
 */
public class Curve extends View {

    //移动的圆的Y轴
    private float mMoveCircleY = 200;

    private float mCircleY = 200;
    private float mCircleX = 300;
    private float mCircleRadius = 30;
    private float mMoveCircleRadius = mCircleRadius;
    private float mSupX;
    private float mSupY = mCircleY;

    private float lastY;
    private boolean isUp;
    private float mPaintStrokeWidth = 3;

    //控制是否消失
    private boolean isCanDraw = true;


    public Curve(Context context) {
        super(context);
    }

    public Curve(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Curve(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(mPaintStrokeWidth);

        if(isCanDraw) {
            if ((mMoveCircleY-mCircleY)<mMoveCircleRadius*4) {
                Log.e("wing",mSupY-mCircleY+"");
                Path path = new Path();
                //左边的线
                path.moveTo(mCircleX - mCircleRadius + mPaintStrokeWidth / 2, mCircleY);
                path.quadTo(mCircleX, mSupY, mCircleX - mMoveCircleRadius + mPaintStrokeWidth / 2, mMoveCircleY);
                path.lineTo(mCircleX + mMoveCircleRadius, mMoveCircleY);
                path.quadTo(mCircleX, mSupY, mCircleX + mCircleRadius, mCircleY);
                path.lineTo(mCircleX - mCircleRadius, mCircleY);
                path.close();
                canvas.drawPath(path, p);

                //右边的线
//                path.moveTo(mCircleX + mCircleRadius - mPaintStrokeWidth / 2, mCircleY);
//                path.quadTo(mCircleX, mSupY, mCircleX + mMoveCircleRadius - mPaintStrokeWidth / 2, mMoveCircleY);
//                canvas.drawPath(path, p);

                p.setStyle(Paint.Style.FILL);
//                canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, p);

                if (isUp) {

                    canvas.drawCircle(mCircleX, mCircleY, mCircleRadius--, p);
                    canvas.drawCircle(mCircleX, mMoveCircleY, mMoveCircleRadius, p);
                } else {

                    canvas.drawCircle(mCircleX, mCircleY, mCircleRadius++, p);
                    canvas.drawCircle(mCircleX, mMoveCircleY, mMoveCircleRadius, p);
                }
//                canvas.drawPoint(mSupX, mSupY, p);
            } else {

                p.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCircleX, mMoveCircleY, mMoveCircleRadius, p);
//                canvas.drawCircle(mCircleX, mMoveCircleY, mMoveCircleRadius, p);
            }
        }
//        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mMoveCircleY = event.getY();

//                mSupY = mCircleY;
                if (mMoveCircleY < lastY) {
                    isUp = false;
                } else {
                    isUp = true;
                }
                lastY = mMoveCircleY;
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                if((mMoveCircleY-mCircleY)>mMoveCircleRadius*4){

                    Log.e("wing","超过");
                    isCanDraw = false;
                    invalidate();

                }else {

                    Log.e("wing","没超过");
                }
        }

        return true;
    }
}
