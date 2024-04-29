package com.soradesona.shiba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.soradesona.shiba.R
import com.soradesona.shiba.databinding.FragmentSettingsBinding
import com.soradesona.shiba.viewmodel.ShibaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: ShibaViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {

        setDownloadType()

        binding.textViewSeekBarValue.text = viewModel.imageCount

        binding.seekBar.progress = viewModel.imageCount.toInt()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, newValue: Int, fromUser: Boolean) {
                binding.textViewSeekBarValue.text = newValue.toString()
                viewModel.setImagesCountToLoad(newValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })



        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDownloadType() {

        getDownloadTypeAndSetVisibility()

        binding.downloadTypeByCount.setOnClickListener {
            viewModel.setCurrentDownloadType(false)
            binding.downloadTypeByCount.setBackgroundResource(R.drawable.button_pressed_background)
            binding.downloadTypeEndless.setBackgroundResource(R.drawable.ripple_effect_background)
            binding.seekBar.isEnabled = true
            binding.changeCountOfImagesToDownloadTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.textViewSeekBarValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))


        }

        binding.downloadTypeEndless.setOnClickListener {
            viewModel.setCurrentDownloadType(true)
            binding.downloadTypeEndless.setBackgroundResource(R.drawable.button_pressed_background)
            binding.downloadTypeByCount.setBackgroundResource(R.drawable.ripple_effect_background)
            binding.seekBar.isEnabled = false
            binding.changeCountOfImagesToDownloadTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            binding.textViewSeekBarValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        }

    }

    private fun getDownloadTypeAndSetVisibility(){
        when(viewModel.getCurrentDownloadType()){
            false ->{
                binding.downloadTypeByCount.setBackgroundResource(R.drawable.button_pressed_background)
            }
            true ->{
                binding.downloadTypeEndless.setBackgroundResource(R.drawable.button_pressed_background)
                binding.seekBar.isEnabled = false
                binding.changeCountOfImagesToDownloadTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.textViewSeekBarValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
