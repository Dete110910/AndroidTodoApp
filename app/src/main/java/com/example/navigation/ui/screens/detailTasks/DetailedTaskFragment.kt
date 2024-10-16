package com.example.navigation.ui.screens.detailTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentDetailTaskBinding


class DetailedTaskFragment : Fragment() {

    private lateinit var binding : FragmentDetailTaskBinding
    private val taskViewModel : TaskViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTaskBinding.inflate(layoutInflater)
        return binding.root
    }


}