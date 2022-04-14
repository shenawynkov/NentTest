package com.shenawynkov.nenttest.ui.section

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R
import com.shenawynkov.nenttest.databinding.ActivitySectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionBinding
    private val viewModel: SectionViewModel by viewModels()

    companion object {
        const val SECTION = "section"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()


    }

    fun setup() {


        //binding
        binding = DataBindingUtil.setContentView<ActivitySectionBinding>(
            this,
            R.layout.activity_section
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //getData
        intent.getParcelableExtra<Section>(SECTION)?.let {
            if (viewModel.sectionDetail.value == null)
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