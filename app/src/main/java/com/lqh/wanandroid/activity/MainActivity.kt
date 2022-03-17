package com.lqh.wanandroid.activity

import android.os.Bundle
import com.lqh.fastlibrary.entity.FastTabEntity
import com.lqh.fastlibrary.modle.activity.FastMainActivity
import com.lqh.fastlibrary.view.tablayout.CommonTabLayout
import com.lqh.wanandroid.R
import com.lqh.wanandroid.fragment.*
import java.util.*

class MainActivity : FastMainActivity() {

    private lateinit var fastTabEntityList: ArrayList<FastTabEntity>

    override fun isSwipeEnable(): Boolean {
        return false
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    override fun initView(savedInstanceState: Bundle?) {

    }

    /**
     * 用于添加Tab属性(文字-图标)
     *
     * @return 主页tab数组
     */
    override fun getTabList(): MutableList<FastTabEntity> {

        fastTabEntityList = ArrayList<FastTabEntity>()
        fastTabEntityList.add(FastTabEntity(R.string.home,
            R.drawable.ic_home_normal,
            R.drawable.ic_home_selected,
            HomeFragment()))
        fastTabEntityList.add(FastTabEntity(R.string.question,
            R.drawable.ic_question_normal,
            R.drawable.ic_question_selected,
            QuestionFragment()))
        fastTabEntityList.add(FastTabEntity(R.string.knowledge,
            R.drawable.ic_knowledge_normal,
            R.drawable.ic_knowledge_selected,
            KnowledgeFragment()))
//        fastTabEntityList.add(FastTabEntity(R.string.project,
//            R.drawable.ic_home_normal,
//            R.drawable.ic_home_selected,
//            QuestionFragment()))
        fastTabEntityList.add(FastTabEntity(R.string.official_accounts,
            R.drawable.ic_official_accounts_normal,
            R.drawable.ic_official_accounts_selected,
            WechatPublicAccountFragment()))

        fastTabEntityList.add(FastTabEntity(R.string.mine,
            R.drawable.ic_mine_normal,
            R.drawable.ic_mine_selected,
            FunctionFragment()))

        return fastTabEntityList
    }

    /**
     * 返回 CommonTabLayout  对象用于自定义设置
     *
     * @param tabLayout CommonTabLayout 对象用于单独属性调节
     */
    override fun setTabLayout(tabLayout: CommonTabLayout?) {
    }

}