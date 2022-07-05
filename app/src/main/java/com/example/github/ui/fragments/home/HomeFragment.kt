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
import com.example.domain.models.UserAndFollow
import com.example.github.R
import com.example.github.databinding.FragmentHomeBinding
import com.example.github.utils.loadCircular
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val repositoriesAdapter = RepositoriesAdapter(onclick = { onClick(it) })

    private lateinit var userAndFollow: UserAndFollow

    private val jobs = mutableListOf<Deferred<Any>>()

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
            textViewFolowerss.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_usersFragment,
                    bundleOf("followers" to Gson().toJson(userAndFollow.users))
                )
            }
            textViewNumFollowers.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_usersFragment,
                    bundleOf("followers" to Gson().toJson(userAndFollow.users))
                )
            }
            textViewFollowing.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_usersFragment,
                    bundleOf("following" to Gson().toJson(userAndFollow.users))
                )
            }
            textViewFollowingText.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_usersFragment,
                    bundleOf("following" to Gson().toJson(userAndFollow.users))
                )
            }

        }
    }

    private fun setUpRepositoryAdapter() {
        binding.recyclerViewRepositories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
        }
    }

    private fun observeUiState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collectLatest { state ->
                binding.apply {

                    progressBar.isVisible = state.isLoading

                    state.error?.let { error ->
                        Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
                    }

                    //observe user search
                    state.searchResult?.let { searchResult ->
                        textViewUsername.text = searchResult.login
                        textViewBio.text = searchResult.bio
                        imageViewProfile.loadCircular(searchResult.avatarUrl ?: "")

                        //fetch data if repo is empty
                        if (state.repositories.isNullOrEmpty() && !state.isLoading) {
                            val job = lifecycleScope.async {
                                viewModel.setState(
                                    HomeIntents.SearchUserRespositories(
                                        searchResult.login ?: "github"
                                    )
                                )
                            }
                            jobs.add(job)
                        }

                        // fetch followers
                        if (state.userAndFollow == null && !state.isLoading) {
                            val job = lifecycleScope.async {
                                viewModel.setState(
                                    HomeIntents.SearchFollowers(
                                        searchResult.login ?: "github"
                                    )
                                )
                            }
                            jobs.add(job)
                        }
                        jobs.awaitAll()
                    }

                }

                //observe repositories search
                state.repositories?.let { repositories ->
                    repositoriesAdapter.submitList(repositories)
                }

                //observe user and followers
                state.userAndFollow?.let { userAndFollowers ->
                    userAndFollow = userAndFollowers
                }

                //observe empty state
                state.apply {
                    val isEmpty = !isLoading && searchResult == null && userAndFollow == null && repositories == null
                    binding.layoutContent.isVisible = !isEmpty
                    binding.layoutEmpty.isVisible = isEmpty
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
                lifecycleScope.launch {
                    viewModel.setState(
                        HomeIntents.SearchUser(
                            (query ?: "github").toLowerCase().trim()
                        )
                    )
                }
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
