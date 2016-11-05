package com.dosssik.andrey.savemyeye.wiget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dosssik.andrey.savemyeye.R;

import java.util.concurrent.TimeUnit;


public class CustomTimerView extends View {

    private static final int ARC_START_ANGLE = 270; // 12 o'clock
    private static final float THICKNESS_SCALE = 0.03f;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint circlePaint;
    private Paint eraserPaint;

    private RectF circleInnerBounds;
    private RectF circleOuterBounds;

    private float circleSweepAngle;

    private ValueAnimator timerAnimator;


    public CustomTimerView(Context context) {
        super(context);
    }

    public CustomTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPoints(context, attrs);
    }

    public CustomTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPoints(context, attrs);
    }

    private void initPoints(Context context, AttributeSet attrs) {
        int circleColor = Color.RED;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTimerView);
            if (typedArray != null) {
                circleColor = typedArray.getColor(R.styleable.CustomTimerView_circleColor, circleColor);
                typedArray.recycle();
            }
        }

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true); // TODO: 02.10.2016 Сглаживает края?
        circlePaint.setColor(circleColor);

        eraserPaint = new Paint();
        eraserPaint.setAntiAlias(true);
        eraserPaint.setColor(Color.TRANSPARENT);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)); // взаимоотношение слоев отрисовки https://softwyer.wordpress.com/2012/01/21/1009/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        if (circleSweepAngle > 0f) {
            this.canvas.drawArc(circleOuterBounds, ARC_START_ANGLE, circleSweepAngle, true, circlePaint);
            this.canvas.drawOval(circleInnerBounds, eraserPaint);
        }

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

//    @SuppressWarnings("SuspiciousNameCombination")
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        // TODO: Проверить. Должно влиять на границы, делать квадрат, назависимо от содержимого. КАК?
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            canvas = new Canvas(bitmap);
        }
        super.onSizeChanged(w, h, oldw, oldh);
        updateBounds();
    }

    private void updateBounds() {
        final float thickness = getWidth() * THICKNESS_SCALE;

        circleOuterBounds = new RectF(0, 0, getWidth(), getHeight());
        circleInnerBounds = new RectF(
                circleOuterBounds.left + thickness,
                circleOuterBounds.top + thickness,
                circleOuterBounds.right - thickness,
                circleOuterBounds.bottom - thickness
        );

        invalidate();
    }

    private void drawProgress(float progress) {
        circleSweepAngle = 360 * progress;

        invalidate();
    }

    public void startCustomAnim(int seconds) {
        timerAnimator = ValueAnimator.ofFloat(0f, 1f);
        timerAnimator.setDuration(TimeUnit.SECONDS.toMillis(seconds));
        timerAnimator.setInterpolator(new LinearInterpolator());
        timerAnimator.addUpdateListener(animation -> drawProgress((float) animation.getAnimatedValue()));
        timerAnimator.start();
    }

    public void startFromPersentPosition(int secondsLeft, float persentPosition) {
        timerAnimator = ValueAnimator.ofFloat(persentPosition, 1f);
        timerAnimator.setDuration(TimeUnit.SECONDS.toMillis(secondsLeft));
        timerAnimator.setInterpolator(new LinearInterpolator());
        timerAnimator.addUpdateListener(animation -> drawProgress((float) animation.getAnimatedValue()));
        timerAnimator.start();
    }

    public void stopCustomAnim() {
        if (timerAnimator != null && timerAnimator.isRunning()) {
            timerAnimator.cancel();
            timerAnimator = null;

            drawProgress(0f);
        }
    }
}
