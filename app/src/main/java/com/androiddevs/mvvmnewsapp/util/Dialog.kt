/*
package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.androiddevs.mvvmnewsapp.R

object Dialog {

    private fun showChangeLang(context: Context) {

        val listItems = arrayOf("عربي", "English")

        */
/*  val sharedPreferences: SharedPreferences =
              requireContext().getSharedPreferences("language", 0)*//*


        selectedLanguageIndex = sharedPreferences.getInt(Constant.CHOOSE_LANGUAGE, 1)

        Log.i("setting language", "showChangeLang: $selectedLanguageIndex")


        val mBuilder = AlertDialog.Builder(context)

        mBuilder.setTitle(getString(R.string.chooselanguage))

        mBuilder.setSingleChoiceItems(listItems, selectedLanguageIndex) { dialog, index ->

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(Constant.CHOOSE_LANGUAGE, index)
            editor.apply()
            //  editor.commit()
            if (index == 0) {
                setLocate("ar")
                requireActivity().recreate()
            } else if (index == 1) {
                setLocate("en")
                requireActivity().recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()

    }
}*/
