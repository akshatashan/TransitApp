package io.door2door.transitapp.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import io.door2door.transitapp.R
import io.door2door.transitapp.databinding.ChildviewStopBinding
import io.door2door.transitapp.databinding.ExpandableRecyclerviewSegmentBinding
import io.door2door.transitapp.models.Segment
import io.door2door.transitapp.models.Stop

/**
 * adapter for the each page of the carousel view on the Segment Detail screen
 */
class SegmentExpandableRecyclerAdapter(private val mContext: Context, private val mSegmentList: List<Segment>) : ExpandableRecyclerAdapter<Segment, Stop, SegmentViewHolder, StopViewHolder>(mSegmentList) {
    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): SegmentViewHolder {
        val binding = DataBindingUtil.inflate<ExpandableRecyclerviewSegmentBinding>(LayoutInflater.from(parentViewGroup!!.context), R.layout.expandable_recyclerview_segment, parentViewGroup, false)
        return SegmentViewHolder(binding)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): StopViewHolder {
        val binding = DataBindingUtil.inflate<ChildviewStopBinding>(LayoutInflater.from(childViewGroup!!.context), R.layout.childview_stop, childViewGroup, false)
        return StopViewHolder(binding)
    }

    override fun onBindParentViewHolder(parentViewHolder: SegmentViewHolder, parentPosition: Int, parent: Segment) {
        parentViewHolder.bind(parent)
    }

    override fun onBindChildViewHolder(childViewHolder: StopViewHolder, parentPosition: Int, childPosition: Int, child: Stop) {
        //**** Don't bind first child for every segment. Bind last child only for last segment ******//
        //Parent element of expandable view is the first child(Stop 1 is header of the corresponding segment). Hence, don't bind the first child in the child view
        //Last element of child is the first element of next parent.Hence don't bind last child(Stop n is the header of the next corresponding segment)

        if(mSegmentList[parentPosition].num_stops > 0) {
            val lastChildPosition = mSegmentList[parentPosition].stops.size - 1
            if (childPosition > 0 && childPosition < lastChildPosition) {
                childViewHolder.bind(child)
            }
            //Last parent has to show the last element of child.Hence, bind last child only for last parent
            if (parentPosition == mSegmentList.size - 1) {
                //got the last parent
                if (childPosition == lastChildPosition) {
                    //got the last child
                    childViewHolder.bind(child)
                }
            }
        }
    }
}
