package io.leapingwolf.transitapp.adapters

import android.content.Context
import android.graphics.Color
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import io.leapingwolf.transitapp.Helper
import io.leapingwolf.transitapp.databinding.FragmentSegmentDetailBinding
import io.leapingwolf.transitapp.models.Segment
import io.leapingwolf.transitapp.models.Stop
import io.leapingwolf.transitapp.viewmodels.SegmentViewModel
import java.util.*

/**
 * parent of expandable recycler view.
 */
class SegmentViewHolder(val viewDataBinding: FragmentSegmentDetailBinding, val ctx: Context) : ParentViewHolder<Segment, Stop>(viewDataBinding.root) {

    init {
        this.viewDataBinding.executePendingBindings()
    }

    fun bind(segment: Segment) {
        viewDataBinding.setVariable(io.leapingwolf.transitapp.BR.handler, SegmentViewModel(segment,this.ctx))
    }

    override fun onExpansionToggled(expanded: Boolean) {
        super.onExpansionToggled(expanded)
    }

}
