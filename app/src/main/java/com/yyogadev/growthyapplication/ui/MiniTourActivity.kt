package com.yyogadev.growthyapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.ViewPagerAdapter
import com.yyogadev.growthyapplication.databinding.ActivityMiniTourBinding
import com.yyogadev.growthyapplication.ui.home.deteksi.CameraActivity

class MiniTourActivity : AppCompatActivity() {

    private lateinit var mSlideViewPager: ViewPager
    private lateinit var mDotLayout: LinearLayout
    private lateinit var backBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var skipBtn: Button

    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mini_tour)

        backBtn = findViewById(R.id.btnBack)
        nextBtn = findViewById(R.id.btnNext)
        skipBtn = findViewById(R.id.btnSkip)

        backBtn.setOnClickListener {
            if (getItem(0) > 0) {
                mSlideViewPager.setCurrentItem(getItem(-1), true)
            }
        }

        nextBtn.setOnClickListener {
            if (getItem(0) < 3) {
                mSlideViewPager.setCurrentItem(getItem(1), true)
            } else {
                val intent = Intent(this@MiniTourActivity, CameraActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        skipBtn.setOnClickListener {
            val intent = Intent(this@MiniTourActivity, CameraActivity::class.java)
            startActivity(intent)
            finish()
        }

        mSlideViewPager = findViewById(R.id.miniTourPager)
        mDotLayout = findViewById(R.id.indicator_layout)

        viewPagerAdapter = ViewPagerAdapter(this)

        mSlideViewPager.adapter = viewPagerAdapter

        setUpIndicator(0)
        mSlideViewPager.addOnPageChangeListener(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(4) { TextView(this@MiniTourActivity) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this@MiniTourActivity)
            dots[i].text = Html.fromHtml("&#8226").toString()
            dots[i].textSize = 35F
            //dots[i].setTextColor(ContextCompat.getColor(applicationContext, R.color.inactive))
            mDotLayout.addView(dots[i])
        }

        //dots[position].setTextColor(ContextCompat.getColor(applicationContext, R.color.active))
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // Do something on page scrolled
        }

        override fun onPageSelected(position: Int) {
            setUpIndicator(position)

            if (position > 0) {
                backBtn.visibility = View.VISIBLE
            } else {
                backBtn.visibility = View.INVISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            // Do something when the scroll state changes
        }
    }

    private fun getItem(i: Int): Int {
        return mSlideViewPager.currentItem + i
    }
}