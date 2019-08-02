package io.github.omisie11.dosecalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_about.*

class AboutBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_about, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        chip_mamaistetoskop.setOnClickListener { openWebUrl(getString(R.string.url_mama_i_stetoskop)) }
        chip_m_wozniak_github.setOnClickListener { openWebUrl(getString(R.string.url_mateusz_wozniak_github)) }

        chip_dev_github.setOnClickListener { openWebUrl(getString(R.string.github_url_omisie11)) }
        chip_dev_twitter.setOnClickListener { openWebUrl(getString(R.string.twitter_url_omisie11)) }
        chip_dev_website.setOnClickListener { openWebUrl(getString(R.string.url_omisie11_website)) }
    }

    private fun openWebUrl(urlAddress: String) {
        if (urlAddress.isNotEmpty()) startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress)))
    }
}