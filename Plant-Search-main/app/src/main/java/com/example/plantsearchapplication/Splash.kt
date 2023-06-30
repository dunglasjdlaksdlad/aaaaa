package com.example.plantsearchapplication

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.widget.LinearLayout

import androidx.viewpager2.widget.ViewPager2
import com.example.plantsearchapplication.databinding.SplashBinding
import com.google.android.gms.common.util.CollectionUtils.listOf

class Splash : AppCompatActivity() {

    private lateinit var binding: SplashBinding
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Identify Plants",
                "You can identify the plants you don't know through your camera",
                R.drawable.intro_a
            ),
            IntroSlide(
                "Learn Many Plants Species",
                "Let's learn about the many plant species that exist in this world",
                R.drawable.intro_b
            ),
            IntroSlide(
                "Read Many Articles About Plant",
                "Let's learn more about beautiful plants and read many articles about plants and gardening",
                R.drawable.intro_c
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kiểm tra trạng thái đã hiển thị màn hình Splash trước đó hay chưa
        val isFirstTime = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
            .getBoolean("isFirstTime", true)

        if (isFirstTime) {
            // Lần đầu tiên mở ứng dụng, hiển thị màn hình Splash
            binding = SplashBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            binding.introSliderViewPager.adapter = introSliderAdapter
            setupIndicators()
            setCurrentIndicator(0)
            binding.introSliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)

                    if (position == introSliderAdapter.itemCount - 1) {
                        binding.buttonNext.text = getString(R.string.sign_in)
                    } else {
                        binding.buttonNext.text = getString(R.string.next)
                    }
                }
            })

            binding.buttonNext.setOnClickListener {
                if (binding.introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                    binding.introSliderViewPager.currentItem += 1
                } else {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()

                }
            }

            binding.textSkipIntro.setOnClickListener {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()

            }

            // Lưu trạng thái đã hiển thị màn hình Splash
            getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit()
                .putBoolean("isFirstTime", false).apply()
        } else {
            // Không phải lần đầu tiên mở ứng dụng, chuyển trực tiếp sang màn hình Login
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
            return
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
            }

            binding.indicatorContainer.addView(indicators[i])
        }
    }


    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}
