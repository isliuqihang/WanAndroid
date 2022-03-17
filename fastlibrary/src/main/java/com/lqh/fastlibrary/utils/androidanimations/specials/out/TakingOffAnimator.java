package com.lqh.fastlibrary.utils.androidanimations.specials.out;

import android.animation.ObjectAnimator;
import android.view.View;

import com.lqh.fastlibrary.utils.androidanimations.BaseViewAnimator;
import com.lqh.fastlibrary.utils.androidanimations.easing.Glider;
import com.lqh.fastlibrary.utils.androidanimations.easing.Skill;

public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
        );
    }
}
