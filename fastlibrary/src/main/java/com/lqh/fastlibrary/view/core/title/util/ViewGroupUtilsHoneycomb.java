package com.lqh.fastlibrary.view.core.title.util;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @Author: AriesHoo on 2018/7/19 10:39
 * @E-Mail: AriesHoo@126.com
 * Function: 
 * Description: 
 */
@TargetApi(11)
public class ViewGroupUtilsHoneycomb {

    private static final ThreadLocal<Matrix> S_MATRIX = new ThreadLocal<>();
    private static final ThreadLocal<RectF> S_RECTF = new ThreadLocal<>();

    public static void offsetDescendantRect(ViewGroup group, View child, Rect rect) {
        Matrix m = S_MATRIX.get();
        if (m == null) {
            m = new Matrix();
            S_MATRIX.set(m);
        } else {
            m.reset();
        }

        offsetDescendantMatrix(group, child, m);

        RectF rectF = S_RECTF.get();
        if (rectF == null) {
            rectF = new RectF();
            S_RECTF.set(rectF);
        }
        rectF.set(rect);
        m.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f),
                (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
        final ViewParent parent = view.getParent();
        if (parent instanceof View && parent != target) {
            final View vp = (View) parent;
            offsetDescendantMatrix(target, vp, m);
            m.preTranslate(-vp.getScrollX(), -vp.getScrollY());
        }

        m.preTranslate(view.getLeft(), view.getTop());

        if (!view.getMatrix().isIdentity()) {
            m.preConcat(view.getMatrix());
        }
    }
}
