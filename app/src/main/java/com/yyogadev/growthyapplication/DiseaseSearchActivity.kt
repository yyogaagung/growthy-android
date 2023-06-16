package com.yyogadev.growthyapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yyogadev.growthyapplication.databinding.ActivityDiseaseSeachBinding

class DiseaseSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiseaseSeachBinding
    private lateinit var penyakitViewModel: PenyakitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseSeachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvPenyakit.layoutManager = layoutManager

        penyakitViewModel = PenyakitViewModelFactory().create(PenyakitViewModel::class.java)

        penyakitViewModel.penyakits.observe(this) {
            items -> setStoriesData(items)
        }
    }

    private fun setStoriesData(diseaseList: List<DiseaseItem>)  {
        val adapter = PenyakitAdapter(diseaseList)
        binding.rvPenyakit.adapter = adapter
        adapter.setOnItemClickCallback(object : PenyakitAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DiseaseItem) {
                val moveActivityWithObjectIntent = Intent(this@DiseaseSearchActivity, DiseaseInfoActivity::class.java)
                moveActivityWithObjectIntent.putExtra(DiseaseInfoActivity.ID, data.id)

                startActivity(moveActivityWithObjectIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint2)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@DiseaseSearchActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }
        })
        return true
    }
}