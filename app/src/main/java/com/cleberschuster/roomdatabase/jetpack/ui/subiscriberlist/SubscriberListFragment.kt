package com.cleberschuster.roomdatabase.jetpack.ui.subiscriberlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cleberschuster.roomdatabase.jetpack.R
import com.cleberschuster.roomdatabase.jetpack.databinding.SubscriberListFragmentBinding
import com.cleberschuster.roomdatabase.jetpack.extension.navigateWithAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriberListFragment : Fragment() {

    private var _binding: SubscriberListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriberListViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SubscriberListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun observeViewModelEvents() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            setHasOptionsMenu(allSubscribers.size > 1)

            val subscriberListAdapter = SubscriberListAdapter(allSubscribers) { subscriber ->
                val directions = SubscriberListFragmentDirections
                    .actionSubscriberListFragmentToSubscriberFragment(subscriber)

                findNavController().navigateWithAnimations(directions)
            }

            with(binding.recyclerSubscribers) {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }

        viewModel.deleteAllSubscribersEvent.observe(viewLifecycleOwner) {
            viewModel.getSubscribers()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscribers()
    }

    private fun configureViewListeners() {
        binding.fabAddSubscriber.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_subscriberListFragment_to_subscriberFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.subscriber_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete_subscribers) {
            viewModel.deleteAllSubscribers()
            true
        } else super.onOptionsItemSelected(item)
    }
}