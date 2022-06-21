package com.soradesona.shiba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.soradesona.shiba.*
import com.soradesona.shiba.viewmodel.ShibaViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SettingsFragment : PreferenceFragmentCompat(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibilityOnViewCreated()
        initButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setVisibilityOnDestroyView()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_xml, rootKey)
    }

    private fun setVisibilityOnViewCreated() {
        requireActivity().settings_button.visibility = View.GONE
        requireActivity().main_toolbar.title = ""
        requireActivity().settings_back.visibility = View.VISIBLE
    }

    private fun setVisibilityOnDestroyView() {
        requireActivity().settings_button.visibility = View.VISIBLE
        requireActivity().main_toolbar.title = "ShibaInu"
        requireActivity().settings_back.visibility = View.GONE
    }

    private fun initButtons() {
        requireActivity().settings_back.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_listFragment)
        }
    }
}
