package com.yeseul.belonging

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.yeseul.belonging.adapter.AlbumAdapter
import com.yeseul.belonging.adapter.ItemDecoration
import com.yeseul.belonging.vo.AlbumVO
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.Charset


class AlbumActivity : AppCompatActivity(), AlbumAdapter.OnItemClickListener {

    private var mContext: Context? = null


    private var rcc_album: RecyclerView? = null

    private var mAlbumAdapter: AlbumAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.yeseul.belonging.R.layout.activity_album)
        mContext = this
        init()
    }
    private fun init() {
        rcc_album = findViewById<RecyclerView>(com.yeseul.belonging.R.id.rcc_album)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(mContext, 2)
        if(rcc_album != null) {
            rcc_album!!.setLayoutManager(mLayoutManager)
            rcc_album!!.addItemDecoration(ItemDecoration(this))
        }

        mAlbumAdapter = AlbumAdapter(mContext, getAlbumList())
        mAlbumAdapter!!.setOnItemClickListener(this)
        rcc_album!!.setAdapter(mAlbumAdapter)
    }

    override fun onItemClick(view: View?, albumVO: AlbumVO?) {
        val intent = Intent(this@AlbumActivity, DetailActivity::class.java)

        intent.putExtra("albumVO", albumVO)


        val thumbView: View = view!!.findViewById(com.yeseul.belonging.R.id.img_thumb)

        val pair_thumb: Pair<View, String> =
            Pair.create(thumbView, thumbView.transitionName)

        val optionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this@AlbumActivity, pair_thumb)


        startActivity(intent, optionsCompat.toBundle())
    }



    private fun getAlbumList(): ArrayList<AlbumVO>? {
        val list_album: ArrayList<AlbumVO> = ArrayList()
        val gson = Gson()
        try {
            val `is`: InputStream = assets.open("album.json")
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            val json = String(buffer, Charset.forName("UTF-8"))
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.getJSONArray("album")
            var index = 0
            while (index < jsonArray.length()) {
                val albumVO: AlbumVO =
                    gson.fromJson<AlbumVO>(jsonArray[index].toString(), AlbumVO::class.java)
                list_album.add(albumVO)
                index++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list_album
    }
}
