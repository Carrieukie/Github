package com.example.github.ui.fragments.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.UserAndFollow
import com.example.domain.utils.Resource
import com.example.github.R
import com.example.github.databinding.FragmentHomeBinding
import com.example.github.utils.loadCircular
import com.example.github.utils.snackBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val repositoriesAdapter = RepositoriesAdapter(onclick = { onClick(it) })

    private lateinit var userAndFollow: UserAndFollow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        setUpRepositoryAdapter()
        setUpBindings()
    }

    private fun setUpBindings() {
        binding.apply {
            textViewFolowerss.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_usersFragment,
                bundleOf("followers" to Gson().toJson(userAndFollow.users))) }
            textViewNumFollowers.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_usersFragment,
                bundleOf("followers" to Gson().toJson(userAndFollow.users))) }
            textViewFolowerss.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_usersFragment,
                bundleOf("followers" to Gson().toJson(userAndFollow.users))) }
            textViewFollowingText.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_usersFragment,
                bundleOf("followers" to Gson().toJson(userAndFollow.users))) }

        }
    }

    private fun setUpRepositoryAdapter() {
        binding.recyclerViewRepositories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.layoutEmpty.isVisible = false
                }
                is Resource.Empty -> {
                    handleEmptyState()
                }
                is Resource.Error -> {
                    handleSuccess(state)
                    handleErrorState(state.error)
                    when (state.data) {
                        is SearchResult -> {
                            binding.textViewUsername.text = (state.data as SearchResult).login
                            binding.textViewBio.text = (state.data as SearchResult).bio
                            binding.imageViewProfile.loadCircular((state.data as SearchResult).avatarUrl ?: "")
                            lifecycleScope.launch {
                                viewModel.setState(HomeIntents.SearchUserRespositories((state.data as SearchResult).login ?: ""))
                                viewModel.setState(HomeIntents.SearchFollowers((state.data as SearchResult).login ?: ""))
                            }
                        }
                    }                }
                is Resource.Success -> {
                    handleSuccess(state.data)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.setState(HomeIntents.SearchUser("github"))
        }

    }

    private fun handleEmptyState() {
//        binding.animationView.setAnimation(R.raw.search)
        binding.progressBar.isVisible = false
    }

    private fun handleSuccess(data: Any?) {
        binding.apply {
            progressBar.isVisible = false
            layoutEmpty.isVisible = false
            layoutContent.isVisible = true
            when (data) {
                is SearchResult -> {
                    textViewUsername.text = data.login
                    textViewBio.text = data.bio
                    imageViewProfile.loadCircular(data.avatarUrl ?: "")
                    lifecycleScope.launch {
                        viewModel.setState(HomeIntents.SearchUserRespositories(data.login ?: ""))
                        viewModel.setState(HomeIntents.SearchFollowers(data.login ?: ""))
                    }
                }

                is List<*> -> {
                    val repository = data.filterIsInstance<Repository>()
                    repositoriesAdapter.submitList(repository)
                }

                is UserAndFollow -> {
                    userAndFollow = data
                    textViewFollowing.text = "${userAndFollow.users.size}"
                    textViewNumFollowers.text = "${userAndFollow.users.size}"
                    Timber.tag(TAG).i("user and follow $data")
                }

                (data == null) ->{

                }

                else -> {
                    Timber.tag(TAG).e(data.toString())
                    handleErrorState(Throwable("Something went wrong"))
                }
            }

        }

    }

    private fun handleErrorState(error: Throwable?) {
        binding.progressBar.isVisible = false
        binding.layoutContent.isVisible = false
        val errorMessage = error?.localizedMessage ?: "Something went wrong"
        snackBar(errorMessage)
        when {
            errorMessage.contains("hostname") -> {
                binding.animationView.setAnimation(R.raw.no_internet)
            }
            else -> {
                binding.animationView.apply {
                }
            }
        }
    }

    private fun onClick(repository: Repository) {
        findNavController().navigate(
            R.id.action_homeFragment_to_repositoryDetailsFragment,
            bundleOf("repo" to Gson().toJson(repository))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("onQueryTextChange", newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("onQueryTextSubmit", query ?: "")
                    viewModel.setState(HomeIntents.SearchUser((query?: "github").toLowerCase().trim()))
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->                 // Not implemented here
                return false
            else -> {}
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}