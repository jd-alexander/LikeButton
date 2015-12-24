package com.like.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
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


public class LikeButton extends FrameLayout implements View.OnClickListener {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private ImageView icon;
    private DotsView dotsView;
    private CircleView circleView;
    private Icon currentIcon;
    private OnLikeListener likeListener;
    private int dotPrimaryColor;
    private int dotSecondaryColor;
    private int circleStartColor;
    private int circleEndColor;
    private int iconSize;

    private boolean isChecked;
    private AnimatorSet animatorSet;

    private Drawable onDrawable;
    private Drawable offDrawable;

    public LikeButton(Context context) {
        this(context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeButton, defStyle, 0);

        iconSize = array.getDimensionPixelSize(R.styleable.LikeButton_icon_size, -1);
        if (iconSize == -1)
            iconSize = 40;

        String iconType = array.getString(R.styleable.LikeButton_icon_type);

        onDrawable = array.getDrawable(R.styleable.LikeButton_on_drawable);
        offDrawable = array.getDrawable(R.styleable.LikeButton_off_drawable);

        circleStartColor = array.getColor(R.styleable.LikeButton_circle_start_color, 0);

        if (circleStartColor != 0)
            circleView.setStartColor(circleStartColor);

        circleEndColor = array.getColor(R.styleable.LikeButton_circle_end_color, 0);

        if (circleEndColor != 0)
            circleView.setEndColor(circleEndColor);

        dotPrimaryColor = array.getColor(R.styleable.LikeButton_dots_primary_color, 0);
        dotSecondaryColor = array.getColor(R.styleable.LikeButton_dots_secondary_color, 0);

        if (dotPrimaryColor != 0 && dotSecondaryColor != 0) {
            dotsView.setColors(dotPrimaryColor, dotSecondaryColor);
        }

        if (iconType != null)
            if (!iconType.isEmpty())
                currentIcon = parseIconType(iconType);

        array.recycle();


        LayoutInflater.from(getContext()).inflate(R.layout.likeview, this, true);
        icon = (ImageView) findViewById(R.id.icon);
        dotsView = (DotsView) findViewById(R.id.dots);
        circleView = (CircleView) findViewById(R.id.circle);


        if (onDrawable == null && offDrawable == null) {
            if (currentIcon != null) {

                setOnDrawableRes(currentIcon.getOnIconResourceId());
                setOffDrawableRes(currentIcon.getOffIconResourceId());
            } else {
                currentIcon = parseIconType(IconType.Heart);
                setOnDrawableRes(currentIcon.getOnIconResourceId());
                setOffDrawableRes(currentIcon.getOffIconResourceId());

            }
        }

        icon.setImageDrawable(offDrawable);
        setEffectsViewSize();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        isChecked = !isChecked;

        icon.setImageDrawable(isChecked ? onDrawable : offDrawable);

        if (likeListener != null) {
            if (isChecked) {
                likeListener.liked();
            } else {
                likeListener.unliked();
            }
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


    public void setOnDrawableRes(@DrawableRes int resId) {
        onDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            onDrawable = Utils.resizeDrawable(getContext(), onDrawable, iconSize, iconSize);
        }
    }

    public void setOnDrawable(Drawable onDrawable) {

        this.onDrawable = onDrawable;

        if (iconSize != 0) {
            this.onDrawable = Utils.resizeDrawable(getContext(), onDrawable, iconSize, iconSize);
        }

    }

    public void setOffDrawableRes(@DrawableRes int resId) {
        offDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            offDrawable = Utils.resizeDrawable(getContext(), offDrawable, iconSize, iconSize);
        }
    }

    public void setOffDrawable(Drawable offDrawable) {

        this.offDrawable = offDrawable;

        if (iconSize != 0) {
            this.offDrawable = Utils.resizeDrawable(getContext(), offDrawable, iconSize, iconSize);
        }

    }


    public void setIcon(IconType currentIconType) {
        currentIcon = parseIconType(currentIconType);
        setOnDrawableRes(currentIcon.getOnIconResourceId());
        setOffDrawableRes(currentIcon.getOffIconResourceId());
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        setEffectsViewSize();
        this.offDrawable = Utils.resizeDrawable(getContext(), offDrawable, iconSize, iconSize);
        this.onDrawable = Utils.resizeDrawable(getContext(), onDrawable, iconSize, iconSize);
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

    public void setOnLikeListener(OnLikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public void setExplodingDotColors(@ColorRes int primaryColor, @ColorRes int secondaryColor) {
        dotsView.setColors(primaryColor, secondaryColor);
    }

    public void setCircleStartColor(@ColorRes int circleStartColor) {
        this.circleStartColor = circleStartColor;
        circleView.setStartColor(ContextCompat.getColor(getContext(), circleStartColor));
    }

    public void setCircleEndColor(@ColorRes int circleEndColor) {
        this.circleEndColor = circleEndColor;
        circleView.setEndColor(ContextCompat.getColor(getContext(), circleEndColor));
    }


    public void setEffectsViewSize() {
        if (iconSize != 0) {
            dotsView.setSize(iconSize * 3, iconSize * 3);
            circleView.setSize(iconSize, iconSize);
        }
    }
}
