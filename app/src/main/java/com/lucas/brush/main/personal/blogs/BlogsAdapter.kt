package com.lucas.brush.main.personal.blogs

import com.lucas.brush.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import javax.inject.Inject

/**
 * @package    BlogsAdapter.kt
 * @anthor     luan
 * @date       11:06 AM
 * @des
 */
class BlogsAdapter @Inject constructor(): BaseQuickAdapter<BlogsBean.DataBean, BaseViewHolder>(R.layout.item_blogs) {
    override fun convert(helper: BaseViewHolder, item: BlogsBean.DataBean) {
        helper.setText(R.id.v_text,"${helper.adapterPosition+1}. ${item.blogName}")
        helper.addOnClickListener(R.id.v_modify)
        helper.addOnClickListener(R.id.v_del)
    }
}
