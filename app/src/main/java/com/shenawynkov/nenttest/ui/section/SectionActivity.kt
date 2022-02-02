package com.shenawynkov.nenttest.ui.section

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R
import com.shenawynkov.nenttest.databinding.ActivitySectionBinding
import com.shenawynkov.nenttest.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionBinding
    private lateinit var viewModel: SectionViewModel

    @Inject
    lateinit var viewModelFactory: SectionViewModelFactory

    companion object {
        const val SECTION = "section"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()


    }

    fun setup() {
        // viewmodel
        viewModel =
            ViewModelProvider(this@SectionActivity, viewModelFactory)[SectionViewModel::class.java]

        //binding
        binding = DataBindingUtil.setContentView<ActivitySectionBinding>(
            this,
            R.layout.activity_section
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //getData
        intent.getParcelableExtra<Section>(SECTION)?.let {
            viewModel.getSection(it)
        }
        //observer
        viewModel.errorMessage.observe(this, Observer {
            if (it == null)
                return@Observer
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}