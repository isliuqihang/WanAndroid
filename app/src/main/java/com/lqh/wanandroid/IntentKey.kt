package com.lqh.wanandroid

/**
 * <pre>
 * description:
 * Created by: Lqh
 * date: 20211117
 * update: 1117
 * version:1.0
</pre> *
 */
object IntentKey {
    /**
     * 作者id
     */
    private const val INTENT_KEY_IN_MAX_SELECT: String = "maxSelect"

    val AUTHOR_ID: String = "authorId"
    val CHAPTER_ID: String = "chapterId"

    /**
     * 导航分类名称
     */
    val NAVI_NAME: String = "naviName"

    /**
     *导航分类数据
     */
    const val NAVI_DATA: String = "naviData"

    /**
     * 导航分类二级分类点击的条目
     */
    const val NAVI_CURR_POS: String = "naviCurrPos"

    /**
     * 公众号id
     */
    const val PARENT_CHAPTER_ID: String = "parentChapterId"


}