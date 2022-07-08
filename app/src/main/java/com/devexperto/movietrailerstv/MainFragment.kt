package com.devexperto.movietrailerstv

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment

class MainFragment : BrowseSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)
    }
}