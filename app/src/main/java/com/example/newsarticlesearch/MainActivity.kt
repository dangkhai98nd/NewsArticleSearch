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
import com.example.newsarticlesearch.presenter.InterfacePresenter
import com.example.newsarticlesearch.presenter.NewsPresenter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OptionsDialog.OnSendData , InterfacePresenter.View {

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

    var queryHashMapOptions: HashMap<String, String> = hashMapOf()
    var queryHashMapSearch: HashMap<String, String> = hashMapOf()


    var isLoading: Boolean = false

    var newsPresenter : InterfacePresenter.Presenter? = null

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

        toolbarTitle.setOnClickListener {
            if (queryHashMapOptions.size == 0 && queryHashMapSearch.size == 0) {
                rvMain.scrollToPosition(0)
            } else {
                queryHashMapOptions = hashMapOf()
                queryHashMapSearch = hashMapOf()
                initViews()
            }
        }
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
        newsPresenter = NewsPresenter(this)

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
                    if (!(isLoading)) {
                        this@MainActivity.page++
                        pbLoading.visibility = ProgressBar.VISIBLE
                        isLoading = true
                        val mapQuery = hashMapOf("page" to page.toString())
                        mapQuery.putAll(queryHashMapOptions)

                        mapQuery.putAll(queryHashMapSearch)
                        newsPresenter?.getDataNews(mapQuery)

                        scrollListener?.resetState()
                    }

                }

            }
        }
        rvMain.addOnScrollListener(scrollListener as RecyclerView.OnScrollListener)

        val mapQuery = hashMapOf("page" to page.toString())
        mapQuery.putAll(queryHashMapOptions)

        mapQuery.putAll(queryHashMapSearch)
        newsPresenter?.getDataNews(mapQuery)

    }

    private fun updateRecyclerView() {
        pd?.show()
        adapter?.clearList()
        page = 0

        val mapQuery = hashMapOf("page" to page.toString())
        mapQuery.putAll(queryHashMapOptions)

        mapQuery.putAll(queryHashMapSearch)
        newsPresenter?.getDataNews(mapQuery)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toobar, menu)
        val mSearch = menu.findItem(R.id.action_search)
        val mOptions = menu.findItem(R.id.options)

        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = "Search..."


        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                queryHashMapSearch = hashMapOf("q" to mSearchView.query.toString())
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                queryHashMapSearch = hashMapOf("q" to mSearchView.query.toString())
                updateRecyclerView()
                return false
            }

        })


        mOptions.setOnMenuItemClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val optionsDialog = OptionsDialog()

            optionsDialog.show(fragmentManager, null)


            val bundle = Bundle()
            bundle.putSerializable("queryHashMapOptions", queryHashMapOptions)
            optionsDialog.arguments = bundle

            false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun sendData(queryMap: HashMap<String, String>) {
        queryHashMapOptions = queryMap

        updateRecyclerView()
    }


    override fun onSuccess(newsList: List<News>?) {
        adapter?.addAll(newsList ?: return)
        if (page == 0) {
            if (srlMain.isRefreshing) {
                srlMain.isRefreshing = false
            }
            rvMain.layoutManager?.scrollToPosition(0)

            pd?.dismiss()
        }
        if (isLoading) {
            pbLoading.visibility = ProgressBar.GONE
            isLoading = false
        }

        Log.e("done load data page ", page.toString())
    }

}





