package com.cleberschuster.roomdatabase.jetpack.ui.subscriber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cleberschuster.roomdatabase.jetpack.R
import com.cleberschuster.roomdatabase.jetpack.databinding.SubscriberFragmentBinding
import com.cleberschuster.roomdatabase.jetpack.extension.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriberFragment : Fragment() {

    // atribua a variável _binding inicialmente como nula e
    // também quando a visualização for destruída novamente,
    // ela deve ser definida como nula no onDestroyView()
    private var _binding: SubscriberFragmentBinding? = null
    // com a backing property do kotlin, extraímos
    // o valor não nulo de _binding
    private val binding get() = _binding!!

    private val viewModel: SubscriberViewModel by viewModel()
    private val args: SubscriberFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflar o layout e vincular-se ao _binding
        _binding = SubscriberFragmentBinding.inflate(layoutInflater, container, false)
        // Inflar o layout para este fragmento
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.subscriber?.let { subscriber ->
            binding.buttonSubscriber.text = getString(R.string.subscriber_button_update)
            binding.inputName.setText(subscriber.name)
            binding.inputEmail.setText(subscriber.email)

            binding.buttonDelete.visibility = View.VISIBLE
        }

        observeEvents()
        setListeners()
    }

    //também quando a visualização for destruída novamente,
    // ela deve ser definida como nula no onDestroyView()
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted,
                is SubscriberViewModel.SubscriberState.Updated,
                is SubscriberViewModel.SubscriberState.Deleted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()

                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.inputName.text?.clear()
        binding.inputEmail.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        binding.buttonSubscriber.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()

            viewModel.addOrUpdateSubscriber(name, email, args.subscriber?.id ?: 0)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.removeSubscriber(args.subscriber?.id ?: 0)
        }
    }
}