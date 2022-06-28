package com.androiddevs.mvvmnewsapp.ui.fragment


import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.SettingFragmentBinding
import com.androiddevs.mvvmnewsapp.ui.activity.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.adapter.CountryAdapter
import com.androiddevs.mvvmnewsapp.ui.viewModel.SettingViewModel
import com.androiddevs.mvvmnewsapp.util.Constant.countryList
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.getLanguage
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.getSharedPreference
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.setLocale
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var binding: SettingFragmentBinding
    var selectedLanguageIndex = -1
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var rec: RecyclerView
    private lateinit var alertDialog: AlertDialog

    private val viewModel:SettingViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            tvPageName.text=getString(R.string.settings)
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            btnLanguage.setOnClickListener {
                showChangeLang()
            }
            btnCatagory.setOnClickListener {
                chooseCountry()
            }
        }

    }


       private fun chooseCountry() {
           val builder = AlertDialog.Builder(requireContext())
           val layoutView = layoutInflater.inflate(R.layout.country_dialog, null)
            rec=layoutView.findViewById(R.id.recCountry)
           builder.setView(layoutView)
           alertDialog=builder.create()
           setupRecyclerView(alertDialog)
           alertDialog.show()

       }

       private fun setupRecyclerView(dialog: AlertDialog) {
           countryAdapter = CountryAdapter(countryList(),getSharedPreference(requireContext()), dialog,activity)
           rec.apply {
               adapter = countryAdapter
               layoutManager = LinearLayoutManager(activity)
           }
       }

    private fun showChangeLang() {
        val listItems = arrayOf("English","عربي")
        val lang=getLanguage(requireContext())

        selectedLanguageIndex = if (lang=="ar"){
                                    1
                                }else{
                                    0
                                }

        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle(getString(R.string.chooselanguage))

        mBuilder.setSingleChoiceItems(listItems, selectedLanguageIndex) { dialog, index ->
            val lang =when(index){
                0->"en"
                1->"ar"
                else->"en"
            }
           setLocale(requireContext(),lang)
            dialog.dismiss()
            refresh()

        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    fun refresh(){
        val navController = requireActivity().findNavController(R.id.newsNavHostFragment)
        navController.run {
            popBackStack()
            navigate(R.id.settingFragment)
        }
        (activity as NewsActivity).recreate()
    }
}