package com.lqh.fastlibrary.utils.androidanimations.specials.in;

import android.animation.ObjectAnimator;
import android.view.View;

import com.lqh.fastlibrary.utils.androidanimations.BaseViewAnimator;
import com.lqh.fastlibrary.utils.androidanimations.easing.Glider;
import com.lqh.fastlibrary.utils.androidanimations.easing.Skill;

public class DropOutAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
    }
}
