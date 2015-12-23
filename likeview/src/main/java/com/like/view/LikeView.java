package com.like.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;


/**
 * Created by Miroslaw Stanek on 20.12.2015.
 */
public class LikeView extends FrameLayout implements View.OnClickListener {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private ImageView icon;
    private DotsView dotsView;
    private CircleView circleView;
    private Icon currentIcon;

    private boolean isChecked;
    private AnimatorSet animatorSet;

    private Drawable onDrawable;
    private Drawable offDrawable;

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (!isInEditMode()) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeView, defStyle, 0);

            String icontype = array.getString(R.styleable.LikeView_icon_type);

            if (icontype != null)
                if (icontype.isEmpty())
                    currentIcon = parseIconType(icontype);

        }


        LayoutInflater.from(getContext()).inflate(R.layout.likeview, this, true);
        icon = (ImageView) findViewById(R.id.icon);
        dotsView = (DotsView) findViewById(R.id.dots);
        circleView = (CircleView) findViewById(R.id.circle);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        isChecked = !isChecked;

        if(currentIcon!=null)
        {
            icon.setImageResource(isChecked ? currentIcon.getOnIconResourceId() : currentIcon.getOffIconResourceId());

        }
        else if(onDrawable!=null && offDrawable!=null)
        {
            icon.setImageDrawable(isChecked?onDrawable:offDrawable);
        }
        else
        {
            currentIcon=parseIconType(IconType.Heart);
            icon.setImageResource(isChecked ? currentIcon.getOnIconResourceId() : currentIcon.getOffIconResourceId());
        }

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        if (isChecked) {
            icon.animate().cancel();
            icon.setScaleX(0);
            icon.setScaleY(0);
            circleView.setInnerCircleRadiusProgress(0);
            circleView.setOuterCircleRadiusProgress(0);
            dotsView.setCurrentProgress(0);

            animatorSet = new AnimatorSet();

            ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);
            outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(icon, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(350);
            starScaleYAnimator.setStartDelay(250);
            starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(icon, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(350);
            starScaleXAnimator.setStartDelay(250);
            starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            animatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator,
                    starScaleYAnimator,
                    starScaleXAnimator,
                    dotsAnimator
            );

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    circleView.setInnerCircleRadiusProgress(0);
                    circleView.setOuterCircleRadiusProgress(0);
                    dotsView.setCurrentProgress(0);
                    icon.setScaleX(1);
                    icon.setScaleY(1);
                }
            });

            animatorSet.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                icon.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
                icon.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;
        }
        return true;
    }

    public void setOnDrawable(Drawable onDrawable) {
        currentIcon=null;
        this.onDrawable = onDrawable;
    }

    public void setOffDrawable(Drawable offDrawable) {
        currentIcon=null;
        this.offDrawable = offDrawable;
    }


    public void setIcon(IconType currentIconType) {
        currentIcon = parseIconType(currentIconType);
    }

    public Icon parseIconType(String iconType) {
        List<Icon> icons = Utils.getIcons();

        for (Icon icon : icons) {
            if (icon.getIconType().name().toLowerCase().equals(iconType.toLowerCase())) {
                return icon;
            }
        }

        throw new IllegalArgumentException("Correct icon type not specified.");
    }

    public Icon parseIconType(IconType iconType) {
        List<Icon> icons = Utils.getIcons();

        for (Icon icon : icons) {
            if (icon.getIconType().equals(iconType)) {
                return icon;
            }
        }

        throw new IllegalArgumentException("Correct icon type not specified.");
    }
}
