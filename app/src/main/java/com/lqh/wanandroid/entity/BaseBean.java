package com.lqh.wanandroid.entity;

import com.blankj.utilcode.util.JsonUtils;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211117
 *     update: 1117
 *     version:1.0
 * </pre>
 */

public class BaseBean implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toFormatJson() {
        return JsonUtils.formatJson((toJson()));
    }
}
 