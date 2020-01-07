package com.yeseul.belonging

import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeseul.belonging.vo.AlbumVO


class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null
    private var albumVO: AlbumVO? = null
    private var btn_back: ImageView? = null
    private var img_thumb: ImageView? = null
    private var txt_title: TextView? = null
    private var txt_artist: TextView? = null
    private var txt_date: TextView? = null
    private var txt_type: TextView? = null
    private var txt_introduce: TextView? = null
    private var rcc_song: RecyclerView? = null
    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        setInit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.yeseul.belonging.R.layout.activity_detail)
        mContext = this
        data
    }

    private val data: Unit
        private get() {
            val intent = intent
            albumVO = intent.getSerializableExtra("albumVO") as AlbumVO
        }

    private fun setInit() {
        btn_back = findViewById<View>(com.yeseul.belonging.R.id.btn_back) as ImageView
        btn_back!!.setOnClickListener(this)
        img_thumb = findViewById<View>(com.yeseul.belonging.R.id.img_thumb) as ImageView
        Glide.with(this!!.mContext!!)
            .load(albumVO!!.thumb)
            .into(img_thumb!!)
        txt_title = findViewById<View>(com.yeseul.belonging.R.id.txt_title) as TextView
        txt_title!!.text = albumVO!!.title
        txt_artist = findViewById<View>(com.yeseul.belonging.R.id.txt_artist) as TextView
        txt_artist!!.text = albumVO!!.artist
        txt_date = findViewById<View>(com.yeseul.belonging.R.id.txt_date) as TextView
        txt_date!!.text = albumVO!!.date
        txt_type = findViewById<View>(com.yeseul.belonging.R.id.txt_type) as TextView
        txt_type!!.text = albumVO!!.type
        txt_introduce = findViewById<View>(com.yeseul.belonging.R.id.txt_introduce) as TextView
        txt_introduce!!.text = albumVO!!.introduce
        rcc_song = findViewById<View>(com.yeseul.belonging.R.id.rcc_song) as RecyclerView
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rcc_song!!.layoutManager = mLayoutManager
        rcc_song!!.isNestedScrollingEnabled = false
        val mSongAdapter = SongAdapter(mContext, albumVO!!.song)
        rcc_song!!.adapter = mSongAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            com.yeseul.belonging.R.id.btn_back -> supportFinishAfterTransition()
        }
    }
}