package com.example.okcreditassignment.ui.news.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.okcreditassignment.R
import com.example.okcreditassignment.appData.Constants.Companion.API_DATE_FORMAT
import com.example.okcreditassignment.appData.Constants.Companion.END_USER_DATE_FORMAT
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.databinding.FragmentDetailBinding
import com.example.okcreditassignment.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailFragment : Fragment() {

    private lateinit var mBinding : FragmentDetailBinding
    private val mViewModel : NewsViewModel by activityViewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        mViewModel.selectedArticle.observe(viewLifecycleOwner) { article ->
            with(mBinding) {
                ivLogo.load(article.urlToImage)
                tvTitle.text = article.title
                if (article.description.isNullOrEmpty())
                    tvDescription.visibility = View.GONE
                else {
                    tvDescription.visibility = View.VISIBLE
                    tvDescription.text = article.description
                }
                if (article.author.isNullOrEmpty())
                    tvAuthor.visibility = View.GONE
                else {
                    tvAuthor.text = "Author - ${article.author}"
                    tvAuthor.visibility = View.VISIBLE
                }
                tvSource.text = "Source - ${article.source!!.name}"
                tvInfo.append(" ")
                val spannable: Spannable = SpannableString("here")
                spannable.setSpan(object : ClickableSpan() {
                    override fun onClick(view: View) {
                        Utils.redirectUserToUrl(requireActivity(), article.url)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(requireContext(), R.color.purple_500)
                        ds.isUnderlineText = true
                    }
                }, 0, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                tvInfo.append(spannable)
                tvInfo.append(".")
                tvInfo.movementMethod = LinkMovementMethod.getInstance()
                tvTime.text = Utils.convertToLocal(
                    article.publishedAt!!,
                    API_DATE_FORMAT,
                    END_USER_DATE_FORMAT
                )
            }
        }
        return mBinding.root
    }


}