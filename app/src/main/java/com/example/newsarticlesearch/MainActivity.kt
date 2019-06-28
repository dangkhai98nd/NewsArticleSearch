package com.example.newsarticlesearch

import android.app.ProgressDialog
import android.content.ComponentName
import android.content.res.Configuration
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.newsarticlesearch.adapter.NewsAdapter
import com.example.newsarticlesearch.api.Client
import com.example.newsarticlesearch.api.Service
import com.example.newsarticlesearch.models.ArticleSearch
import com.example.newsarticlesearch.models.News
import com.example.newsarticlesearch.presenter.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"


    var mCustomTabsClient: CustomTabsClient? = null
    var mCustomTabsSession: CustomTabsSession? = null
    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
    private var mCustomTabsIntent: CustomTabsIntent? = null

    var adapter: NewsAdapter? = null
    var pd: ProgressDialog? = null
    var newsList: List<News>? = null
    var scrollListener: EndlessRecyclerViewScrollListener? = null
    var page: Int = 0


    var isLoading : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                componentName: ComponentName,
                customTabsClient: CustomTabsClient
            ) {
//                mCustomTabsClient = customTabsClient
                customTabsClient.warmup(0L)
                mCustomTabsSession = customTabsClient.newSession(null)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mCustomTabsClient = null
            }
        }

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection)

        mCustomTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
            .setShowTitle(true)
            .build()


        val toolbarTitle: TextView = toolbarTitle

        toolbarTitle.setOnClickListener { initViews() }
        initViews()

        srlMain.setColorSchemeResources(R.color.red)
        srlMain.setOnRefreshListener {
            initViews()
        }

    }

    private fun initViews() {

        page = 0
        newsList = null
        pd = ProgressDialog(this)
        pd?.setMessage("Load...")
        pd?.setCancelable(false)
        pd?.show()


        adapter = NewsAdapter(this, mCustomTabsIntent)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            rvMain.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            rvMain.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }

        rvMain.itemAnimator = DefaultItemAnimator()

        rvMain.adapter = adapter

        val layoutManager = rvMain.layoutManager
        if (layoutManager != null) {
            scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    if (!(isLoading))
                    {
                        this@MainActivity.page++
                        pbLoading.visibility = ProgressBar.VISIBLE
                        isLoading = true
                        loadJSON()
                        scrollListener?.resetState()
                    }

                }

            }
        }
        rvMain.addOnScrollListener(scrollListener as RecyclerView.OnScrollListener)

        loadJSON()

    }

    private fun loadJSON(mapQueryMore: HashMap<String, String>? = null) {
        try {
            val mapQuery = hashMapOf("page" to page.toString())
            if (mapQueryMore != null) {
                mapQuery.putAll(mapQueryMore)
            }
            val client = Client()
            val service: Service? = client.getClient()?.create(Service::class.java)
            val call: Call<ArticleSearch>? = service?.getApiNews(mapQuery)
            call?.run {
                enqueue(object : Callback<ArticleSearch> {
                    override fun onFailure(call: Call<ArticleSearch>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "error load data page $page",Toast.LENGTH_SHORT).show()
                        Log.e("error load data page ",page.toString())
                    }

                    override fun onResponse(call: Call<ArticleSearch>, response: Response<ArticleSearch>) {
                        newsList = response.body()?.response?.docs

                        adapter?.addAll(newsList ?: return)
                        if (page == 0) {
                            if (srlMain.isRefreshing) {
                                srlMain.isRefreshing = false
                            }
                            pd?.dismiss()


                        }
                        if (isLoading) {
                            pbLoading.visibility = ProgressBar.GONE
                            isLoading = false
                        }
                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toobar, menu)
        val mSearch = menu.findItem(R.id.action_search)
        val mOptions = menu.findItem(R.id.options)

        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = "Search..."


        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@MainActivity, mSearchView.query, Toast.LENGTH_SHORT).show()
                adapter?.clearList()
                page = 0

                loadJSON(hashMapOf("q" to mSearchView.query.toString()))
                return false
            }

        })


        mOptions.setOnMenuItemClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val optionsDialog = OptionsDialog()
            optionsDialog.show(fragmentManager, null)
            false
        }

        return super.onCreateOptionsMenu(menu)
    }



}





