package com.lqh.fastlibrary.utils.androidanimations.specials.in;

import android.animation.ObjectAnimator;
import android.view.View;

import com.lqh.fastlibrary.utils.androidanimations.BaseViewAnimator;
import com.lqh.fastlibrary.utils.androidanimations.easing.Glider;
import com.lqh.fastlibrary.utils.androidanimations.easing.Skill;

public class LandingAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 0, 1f))
        );
    }
}
