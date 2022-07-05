package com.example.github.ui.fragments.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.User
import com.example.github.R
import com.example.github.databinding.FragmentUsersBinding
import com.example.github.utils.snackBar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersFragment : Fragment() {

    private lateinit var binding : FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUsersBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.apply {
            val adapter = UsersList(onclick = {

            })

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val follower = arguments?.getString("followers")
            val following = arguments?.getString("following")

            if (follower != null){
                textView15.text = "Followers"
                val type  = object : TypeToken<List<User>>() {}.type
                val followers = Gson().fromJson<List<User>>(follower,type)
                adapter.submitList(followers.shuffled())
            }
            if (following != null){
                textView15.text = "Following"
                val type  = object : TypeToken<List<User>>() {}.type
                val followers = Gson().fromJson<List<User>>(following,type)
                adapter.submitList(followers.shuffled())
            }


        }
    }

}