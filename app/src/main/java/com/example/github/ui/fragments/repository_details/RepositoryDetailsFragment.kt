package com.example.github.ui.fragments.repository_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.models.Repository
import com.example.github.databinding.FragmentRepositoryDetailsBinding
import com.example.github.utils.toFormattedDate
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding : FragmentRepositoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRepositoryDetailsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        val repoJson = arguments?.getString("repo")
        val repository = Gson().fromJson(repoJson, Repository::class.java)
        binding.apply {
            textViewTitle.text = repository.fullName
            textViewDesc.text = if(repository.description == null) "No descption" else repository.description
            textViewForks.text = "${repository.fork} forks"
            textViewStars.text = "${repository.stargazersCount} stars"
            textViewUpdatedOn.text = "Last Updated ${repository.createdAt?.toFormattedDate()}"
            textViewCreatedOn.text = "Created on ${repository.createdAt?.toFormattedDate()}"
        }
    }

}