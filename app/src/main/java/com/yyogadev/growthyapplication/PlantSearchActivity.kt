package com.yyogadev.growthyapplication

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyogadev.growthyapplication.databinding.ActivityPlantSearchBinding
import com.yyogadev.growthyapplication.retrofit.response.PlantResponse
import com.yyogadev.growthyapplication.retrofit.response.PlantResponseItem

class PlantSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantSearchBinding
    private lateinit var viewModel: PlantSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantSearchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PlantSearchViewModel::class.java)

        plantSearchViewModel.plant.observe(this, {PlantResponse ->
            setData(PlantResponse)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(this).get(PlantSearchViewModel::class.java)
        plantSearchViewModel.plant.observe(this){
            plants -> setData(plants)
        }

        plantSearchViewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.setPlant("sukulen")
        plantSearchViewModel.isLoading.observe(this,{
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar. visibility = View.GONE
        }
    }

    private fun setData(plantResponse: List<PlantResponseItem>) {
        val listReview = ArrayList<String>()
        for(PlantResponse in plantResponse){
            listReview.add(
                """
                ${PlantResponse.localName},
                ${PlantResponse.species},
                ${PlantResponse.plantImgNormal}
                """.trimIndent()
            )
        }
        val adapter = PlantReviewAdapter(listReview)
        binding.rvMain.adapter = adapter
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

            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@PlantSearchActivity, query, Toast.LENGTH_SHORT).show()
//                searchView.clearFocus()
                viewModel.setPlant(query)
                return true
            }
        })
        return true
    }
}