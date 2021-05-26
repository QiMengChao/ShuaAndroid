package com.qmc.shuaandroid.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.qmc.shuaandroid.R
import com.qmc.shuaandroid.adapter.FragmentAdapter
import com.qmc.shuaandroid.databinding.ActivityMainBinding
import com.qmc.shuaandroid.ui.fragment.*
import com.qmc.shuaandroid.views.AnimationRadioView
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val lottieMap = mapOf(
        "首页" to "lottie_card_3.json",
        "导航" to "lottie_message_3.json",
        "项目" to "lottie_contract_3.json",
        "我的" to "lottie_my_3.json"
    )
    private val fragments =
        listOf(IndexFragment(), NavigationFragment(), EventFragment(), MineFragment())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mainBinding.root)
        initViewPager()
    }

    private fun initViewPager() {
        mainBinding.mainVp.offscreenPageLimit = 3
        mainBinding.mainVp.adapter =
            FragmentAdapter(supportFragmentManager, fragments,lottieMap.keys.toList())

        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = lottieMap.size

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)
                val lottieLayout =
                    LayoutInflater.from(context).inflate(R.layout.lottie_tab, null, false)
                val tabText = lottieLayout.findViewById<TextView>(R.id.tabText)
                val lottieView = lottieLayout.findViewById<AnimationRadioView>(R.id.tabLottie)
                lottieView.setAnimation(lottieMap.values.elementAt(index))
                tabText.text = lottieMap.keys.elementAt(index)
                commonPagerTitleView.setContentView(lottieLayout)
                commonPagerTitleView.onPagerTitleChangeListener =
                    object : CommonPagerTitleView.OnPagerTitleChangeListener {
                        override fun onSelected(index: Int, totalCount: Int) {
                            lottieView.isChecked = true
                            tabText.typeface = Typeface.DEFAULT_BOLD
                        }

                        override fun onDeselected(index: Int, totalCount: Int) {
                            lottieView.isChecked = false
                            tabText.typeface = Typeface.DEFAULT
                        }

                        override fun onLeave(
                            index: Int,
                            totalCount: Int,
                            leavePercent: Float,
                            leftToRight: Boolean
                        ) {
                        }

                        override fun onEnter(
                            index: Int,
                            totalCount: Int,
                            enterPercent: Float,
                            leftToRight: Boolean
                        ) {
                        }
                    }
                commonPagerTitleView.setOnClickListener { mainBinding.mainVp.currentItem = index }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        mainBinding.mainTab.navigator = commonNavigator
        ViewPagerHelper.bind(mainBinding.mainTab, mainBinding.mainVp)
    }

}