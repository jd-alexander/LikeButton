package com.like;

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

import com.like.view.R;

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

    private Drawable likeDrawable;
    private Drawable unLikeDrawable;

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

    /**
     * Does all the initial setup of the button such as retrieving all the attributes that were
     * set in xml and inflating the like button's view and initial state.
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(getContext()).inflate(R.layout.likeview, this, true);
        icon = (ImageView) findViewById(R.id.icon);
        dotsView = (DotsView) findViewById(R.id.dots);
        circleView = (CircleView) findViewById(R.id.circle);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeButton, defStyle, 0);

        iconSize = array.getDimensionPixelSize(R.styleable.LikeButton_icon_size, -1);
        if (iconSize == -1)
            iconSize = 40;

        String iconType = array.getString(R.styleable.LikeButton_icon_type);

        likeDrawable = array.getDrawable(R.styleable.LikeButton_like_drawable);

        if(likeDrawable!=null)
            setLikeDrawable(likeDrawable);

        unLikeDrawable = array.getDrawable(R.styleable.LikeButton_unlike_drawable);

        if(unLikeDrawable !=null)
            setUnlikeDrawable(unLikeDrawable);

        if (iconType != null)
            if (!iconType.isEmpty())
                currentIcon = parseIconType(iconType);


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


        if (likeDrawable == null && unLikeDrawable == null) {
            if (currentIcon != null) {

                setLikeDrawableRes(currentIcon.getOnIconResourceId());
                setUnlikeDrawableRes(currentIcon.getOffIconResourceId());
            } else {
                currentIcon = parseIconType(IconType.Heart);
                setLikeDrawableRes(currentIcon.getOnIconResourceId());
                setUnlikeDrawableRes(currentIcon.getOffIconResourceId());

            }
        }

        Boolean status = array.getBoolean(R.styleable.LikeButton_liked,false);
        setLiked(status);
        setEffectsViewSize();
        setOnClickListener(this);
        array.recycle();
    }

    /**
     * This triggers the entire functionality of the button such as icon changes,
     * animations, listeners etc.
     * @param v
     */
    @Override
    public void onClick(View v) {
        isChecked = !isChecked;

        icon.setImageDrawable(isChecked ? likeDrawable : unLikeDrawable);

        if (likeListener != null) {
            if (isChecked) {
                likeListener.liked(this);
            } else {
                likeListener.unLiked(this);
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

    /**
     * Used to trigger the scale animation that takes places on the
     * icon when the button is touched.
     * @param event
     * @return
     */
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


    /**
     * This drawable is shown when the button is a liked state.
     * @param resId
     */
    public void setLikeDrawableRes(@DrawableRes int resId) {
        likeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }
    }

    /**
     * This drawable is shown when the button is in a liked state.
     * @param likeDrawable
     */
    public void setLikeDrawable(Drawable likeDrawable) {

        this.likeDrawable = likeDrawable;

        if (iconSize != 0) {
            this.likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }

    }

    /**
     * This drawable will be shown when the button is in on unLiked state.
     * @param resId
     */
    public void setUnlikeDrawableRes(@DrawableRes int resId) {
        unLikeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }
        icon.setImageDrawable(unLikeDrawable);
    }

    /**
     * This drawable will be shown when the button is in on unLiked state.
     * @param unLikeDrawable
     */
    public void setUnlikeDrawable(Drawable unLikeDrawable) {

        this.unLikeDrawable = unLikeDrawable;

        if (iconSize != 0) {
            this.unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }
        icon.setImageDrawable(unLikeDrawable);


    }

    /**
     * Sets one of the three icons that are bundled with the library.
     * @param currentIconType
     */
    public void setIcon(IconType currentIconType) {
        currentIcon = parseIconType(currentIconType);
        setLikeDrawableRes(currentIcon.getOnIconResourceId());
        setUnlikeDrawableRes(currentIcon.getOffIconResourceId());
    }

    /**
     * Sets the size of the drawable/icon that's being used. The views that generate
     * the like effect are also updated to reflect the size of the icon.
     * @param iconSize
     */

    public void setIconSizeDp(int iconSize)
    {
        setIconSizePx((int)Utils.dipToPixels(getContext(),(float)iconSize));
    }
    /**
     * Sets the size of the drawable/icon that's being used. The views that generate
     * the like effect are also updated to reflect the size of the icon.
     * @param iconSize
     */
    public void setIconSizePx(int iconSize) {
        this.iconSize = iconSize;
        setEffectsViewSize();
        this.unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        this.likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
    }

    /**
     * * Parses the specific icon based on string
     * version of its enum.
     * These icons are bundled with the library and
     * are accessed via objects that contain their
     * resource ids and an enum with their name.
     * @param iconType
     * @return Icon
     */
    private Icon parseIconType(String iconType) {
        List<Icon> icons = Utils.getIcons();

        for (Icon icon : icons) {
            if (icon.getIconType().name().toLowerCase().equals(iconType.toLowerCase())) {
                return icon;
            }
        }

        throw new IllegalArgumentException("Correct icon type not specified.");
    }

    /**
     * Parses the specific icon based on it's type.
     * These icons are bundled with the library and
     * are accessed via objects that contain their
     * resource ids and an enum with their name.
     * @param iconType
     * @return
     */
    private Icon parseIconType(IconType iconType) {
        List<Icon> icons = Utils.getIcons();

        for (Icon icon : icons) {
            if (icon.getIconType().equals(iconType)) {
                return icon;
            }
        }

        throw new IllegalArgumentException("Correct icon type not specified.");
    }

    /**
     * Listener that is triggered once the
     * button is in a liked or unLiked state
     * @param likeListener
     */
    public void setOnLikeListener(OnLikeListener likeListener) {
        this.likeListener = likeListener;
    }


    /**
     * This set sets the colours that are used for the little dots
     * that will be exploding once the like button is clicked.
     * @param primaryColor
     * @param secondaryColor
     */
    public void setExplodingDotColorsRes(@ColorRes int primaryColor, @ColorRes int secondaryColor) {
        dotsView.setColors(ContextCompat.getColor(getContext(),primaryColor), ContextCompat.getColor(getContext(),secondaryColor));
    }

    public void setCircleStartColorRes(@ColorRes int circleStartColor) {
        this.circleStartColor = circleStartColor;
        circleView.setStartColor(ContextCompat.getColor(getContext(),circleStartColor));
    }

    public void setCircleEndColorRes(@ColorRes int circleEndColor) {
        this.circleEndColor = circleEndColor;
        circleView.setEndColor(ContextCompat.getColor(getContext(),circleEndColor));
    }

    /**
     * This function updates the dots view and the circle
     * view with the respective sizes based on the size
     * of the icon being used.
     */
    public void setEffectsViewSize() {
        if (iconSize != 0) {
            dotsView.setSize(iconSize * 3, iconSize * 3);
            circleView.setSize(iconSize, iconSize);
        }
    }

    /**
     * Sets the initial state of the button to liked
     * or unliked.
     * @param status
     */
    public void setLiked(Boolean status)
    {
        if(status)
        {
            isChecked=true;
            icon.setImageDrawable(likeDrawable);
        }
        else
        {
            isChecked=false;
            icon.setImageDrawable(unLikeDrawable);
        }
    }
}
