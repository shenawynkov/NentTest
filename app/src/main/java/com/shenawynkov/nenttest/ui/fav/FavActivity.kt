package com.shenawynkov.nenttest.ui.fav

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R
import com.shenawynkov.nenttest.databinding.ActivityFavBinding
import com.shenawynkov.nenttest.databinding.ActivityMainBinding
import com.shenawynkov.nenttest.ui.home.HomeViewModel
import com.shenawynkov.nenttest.ui.home.SectionsAdapter
import com.shenawynkov.nenttest.ui.section.SectionActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavActivity : AppCompatActivity(), SectionsAdapter.SectionsListener {
    private  val viewModel: FavViewModel by viewModels()
    private lateinit var sectionsAdapter: SectionsAdapter
    private lateinit var binding: ActivityFavBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()

    }

    fun setup() {

        //binding
        binding = DataBindingUtil.setContentView<ActivityFavBinding>(
            this,
            R.layout.activity_fav
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //init views
        binding.rvSections.apply {
            layoutManager = LinearLayoutManager(this@FavActivity)
            sectionsAdapter = SectionsAdapter(ArrayList(), this@FavActivity)
            adapter = sectionsAdapter
        }
        //observers
        viewModel.sections.observe(this, Observer {
            if (it == null)
                return@Observer
            sectionsAdapter.setNewList(it)
        })

    }

    override fun onSectionSelected(section: Section) {
        val intent = Intent(this, SectionActivity::class.java)
        intent.putExtra(SectionActivity.SECTION, section)
        startActivity(intent)
    }

    override fun onFavChanged(fav: Boolean, id: String) {
        viewModel.updateFav(id, fav)

    }


}