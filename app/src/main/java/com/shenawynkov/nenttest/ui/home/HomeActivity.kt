package com.shenawynkov.nenttest.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R
import com.shenawynkov.nenttest.databinding.ActivityMainBinding
import com.shenawynkov.nenttest.ui.fav.FavActivity
import com.shenawynkov.nenttest.ui.section.SectionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), SectionsAdapter.SectionsListener {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sectionsAdapter: SectionsAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()

    }

    override fun onResume() {
        super.onResume()
        viewModel.syncSections()
    }


    private fun setup() {

        //binding
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //init views
        binding.rvSections.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            sectionsAdapter = SectionsAdapter(ArrayList(), this@HomeActivity)
            adapter = sectionsAdapter
        }
        bt_fav.setOnClickListener {
            viewModel.backStackStatus = true
            startActivity(Intent(this, FavActivity::class.java))
        }
        //observers
        viewModel.sections.observe(this, Observer {
            if (it == null)
                return@Observer
            sectionsAdapter.setNewList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            if (it == null)
                return@Observer
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSectionSelected(section: Section) {
        viewModel.backStackStatus = true
        moveToSectionActivity(section)
    }

    override fun onFavChanged(fav: Boolean, id: String) {
        viewModel.updateFav(id, fav)

    }


    private fun moveToSectionActivity(section: Section) {
        val intent = Intent(this, SectionActivity::class.java)
        intent.putExtra(SectionActivity.SECTION, section)
        startActivity(intent)
    }
}