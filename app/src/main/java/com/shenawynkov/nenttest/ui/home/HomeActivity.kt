package com.shenawynkov.nenttest.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R
import com.shenawynkov.nenttest.databinding.ActivityMainBinding
import com.shenawynkov.nenttest.databinding.ActivitySectionBinding
import com.shenawynkov.nenttest.ui.section.SectionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), SectionsAdapter.SectionsListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var sectionsAdapter: SectionsAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()

    }

    fun setup() {
        //viewmodel
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        viewModel.getSections()
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
        //observers
        viewModel.sections.observe(this, Observer {
            if (it == null)
                return@Observer
            sectionsAdapter.setNewList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            if(it==null)
                return@Observer
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSectionSelected(section: Section) {
        val intent = Intent(this, SectionActivity::class.java)
        intent.putExtra(SectionActivity.SECTION, section)
        startActivity(intent)
    }
}