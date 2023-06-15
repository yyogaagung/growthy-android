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

class DiseaseSearchActivity : AppCompatActivity() {
    private lateinit var rvPenyakit: RecyclerView
    private val list = ArrayList<Penyakit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_seach)

        rvPenyakit = findViewById(R.id.rv_penyakit)
        rvPenyakit.setHasFixedSize(true)
        list.addAll(getListPenyakit())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvPenyakit.layoutManager = LinearLayoutManager(this)
        val listHewanAdapter = ListPenyakitAdapter(list)
        rvPenyakit.adapter = listHewanAdapter
        listHewanAdapter.setOnItemClickCallback(object : ListPenyakitAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Penyakit) {
                val intentToDetail = Intent(this@DiseaseSearchActivity, DiseaseInfoActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }

        })
    }

    private fun getListPenyakit(): ArrayList<Penyakit> {
        val dataNamaTradisional = resources.getStringArray(R.array.data_nama_tradisional)
        val dataNamaLocal = resources.getStringArray(R.array.data_nama_local)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listPenyakit = ArrayList<Penyakit>()
        for (i in dataNamaTradisional.indices) {
            val penyakit = Penyakit(dataNamaTradisional[i], dataNamaLocal[i], dataPhoto.getResourceId(i, -1))
            listPenyakit.add(penyakit)
        }
        return listPenyakit
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