/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.lqh.fastlibrary.utils.androidanimations.easing;


public enum  Skill {

    BackEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.back.BackEaseIn.class),
    BackEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.back.BackEaseOut.class),
    BackEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.back.BackEaseInOut.class),

    BounceEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.bounce.BounceEaseIn.class),
    BounceEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.bounce.BounceEaseOut.class),
    BounceEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.bounce.BounceEaseInOut.class),

    CircEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.circ.CircEaseIn.class),
    CircEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.circ.CircEaseOut.class),
    CircEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.circ.CircEaseInOut.class),

    CubicEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.cubic.CubicEaseIn.class),
    CubicEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.cubic.CubicEaseOut.class),
    CubicEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.cubic.CubicEaseInOut.class),

    ElasticEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.elastic.ElasticEaseIn.class),
    ElasticEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.elastic.ElasticEaseOut.class),

    ExpoEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.expo.ExpoEaseIn.class),
    ExpoEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.expo.ExpoEaseOut.class),
    ExpoEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.expo.ExpoEaseInOut.class),

    QuadEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.quad.QuadEaseIn.class),
    QuadEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.quad.QuadEaseOut.class),
    QuadEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.quad.QuadEaseInOut.class),

    QuintEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.quint.QuintEaseIn.class),
    QuintEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.quint.QuintEaseOut.class),
    QuintEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.quint.QuintEaseInOut.class),

    SineEaseIn(com.lqh.fastlibrary.utils.androidanimations.easing.sine.SineEaseIn.class),
    SineEaseOut(com.lqh.fastlibrary.utils.androidanimations.easing.sine.SineEaseOut.class),
    SineEaseInOut(com.lqh.fastlibrary.utils.androidanimations.easing.sine.SineEaseInOut.class),

    Linear(com.lqh.fastlibrary.utils.androidanimations.easing.linear.Linear.class);


    private Class easingMethod;

    private Skill(Class clazz) {
        easingMethod = clazz;
    }

    public BaseEasingMethod getMethod(float duration) {
        try {
            return (BaseEasingMethod)easingMethod.getConstructor(float.class).newInstance(duration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init easingMethod instance");
        }
    }
}
