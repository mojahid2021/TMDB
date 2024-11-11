package com.horizonhunters.tmdb;

// VoteBarView.java
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.animation.ValueAnimator;

public class VoteBarView extends View {

    private Paint paint;
    private float percentage = 0f;

    public VoteBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoteBarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() * (percentage / 10);
        canvas.drawRect(0, 0, width, getHeight(), paint);
    }

    public void setPercentage(float targetPercentage) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, targetPercentage);
        animator.setDuration(1500); // Duration in milliseconds
        animator.addUpdateListener(animation -> {
            percentage = (float) animation.getAnimatedValue();
            invalidate(); // Redraw the view
        });
        animator.start();
    }
}
