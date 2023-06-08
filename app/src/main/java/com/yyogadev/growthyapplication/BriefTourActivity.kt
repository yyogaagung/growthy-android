package com.yyogadev.growthyapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.ui.MainActivity
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class BriefTourActivity : AppCompatActivity() {

    private lateinit var tokenViewModel: TokenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brief_tour)
        supportActionBar?.hide()
        checkToken()

        val fragmentManager = supportFragmentManager
        val briefOneFragment = BriefOneFragment()
        val fragment = fragmentManager.findFragmentByTag(BriefOneFragment::class.java.simpleName)

        if (fragment !is BriefOneFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + BriefOneFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, briefOneFragment, BriefOneFragment::class.java.simpleName)
                .commit()
        }
    }

    fun checkToken(){
        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isNotEmpty()) {
                val i = Intent(this@BriefTourActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}