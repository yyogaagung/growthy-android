package com.yyogadev.growthyapplication

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.yyogadev.growthyapplication.databinding.ActivityPlantSearchBinding

class PlantSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@PlantSearchActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }
        })
        return true
    }
}