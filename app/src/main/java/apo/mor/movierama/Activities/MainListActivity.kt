package apo.mor.movierama.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import apo.mor.movierama.Adapters.MoviesListAdapter
import apo.mor.movierama.Listeners.MovieListListener
import apo.mor.movierama.Utils.GeneralUtils
import apo.mor.movierama.Models.MovieModel
import apo.mor.movierama.Models.ResultModel
import apo.mor.movierama.Services.ServiceCalls
import apo.mor.movierama.databinding.ActivityMainListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_main_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainListActivity : AppCompatActivity(), MovieListListener {

    private var binding: ActivityMainListBinding? = null
    private var popularList: ArrayList<MovieModel> = ArrayList()
    private var searchList: ArrayList<MovieModel> = ArrayList()
    private var listAdapter: MoviesListAdapter? = null
    private var isLoading: Boolean = false
    private var isSearchList: Boolean = false
    private var searchText: String = ""
    private var movieSelectedPosition: Int? = 0

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.headerText?.text = GeneralUtils.getHeaderText(this@MainListActivity)
        popularList = Gson().fromJson(
            intent.getStringExtra("list"),
            object : TypeToken<ArrayList<MovieModel>>() {}.type
        )
        setUpList(page = 1)
        setUpSearch()
        setUpSwipeToRefresh()
    }

    private fun setUpList(page: Int) {
        val layoutManager = LinearLayoutManager(this@MainListActivity)
        binding?.mainList?.layoutManager = layoutManager
        listAdapter = MoviesListAdapter(this@MainListActivity, popularList, this@MainListActivity)
        binding?.mainList?.adapter = listAdapter
        addOnScrollListener(page = page)
    }

    private fun setUpSearch() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            var handler: Handler = Handler()
            var workRunnable: Runnable? = null
            override fun afterTextChanged(s: Editable?) {
                workRunnable?.let { handler.removeCallbacks(it) }
                workRunnable = Runnable { checkTextChange(s) }
                workRunnable?.let { handler.postDelayed(it, 400) }
            }
        }
        binding?.searchText?.addTextChangedListener(textWatcher)
        binding?.searchText?.maxLines = 1

        binding?.searchLayout?.setOnClickListener {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            binding?.searchText?.requestFocus()
        }
    }

    private fun checkTextChange(s: Editable?) {
        if (s?.isNotEmpty() == true) {
            isSearchList = true
            searchText = s.toString()
            addOnScrollListener(page = 1)
            getNextPage(page = 1)
        } else {
            isSearchList = false
            binding?.popularTextLayout?.visibility = View.VISIBLE
            getNextPage(page = 1)
        }
    }

    private fun setUpSwipeToRefresh() {
        binding?.swipeLayout?.setDistanceToTriggerSync(150)
        binding?.swipeLayout?.setOnRefreshListener {
            if (!isLoading) {
                binding?.swipeLayout?.isRefreshing = true
                isSearchList = false
                binding?.popularTextLayout?.visibility = View.VISIBLE
                if (binding?.searchText?.text?.isNotEmpty() == true) {
                    binding?.searchText?.text?.clear()
                } else {
                    getNextPage(page = 1)
                }
            }
        }
    }

    private fun addOnScrollListener(page: Int) {
        binding?.listScrollview?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v != null) {
                if (scrollY == (v.getChildAt(0)?.measuredHeight?.minus(v.measuredHeight))) {
                    if (!isLoading) {
                        val nextPage = page + 1
                        getNextPage(page = nextPage)
                    }
                }
            }
        })
    }

    private fun getNextPage(page: Int) {
        isLoading = true
        binding?.progressBar?.visibility = View.VISIBLE
        if (!isSearchList) {
            callPopularList(page = page)
        } else {
            callSearchList(page = page)
        }
        if (binding?.swipeLayout?.isRefreshing == true) {
            binding?.swipeLayout?.isRefreshing = false
        }
    }

    private fun callPopularList(page: Int) {
        ServiceCalls.getPopularMovieList(page = page, callback = object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if (page == 1) {
                    popularList.clear()
                    popularList.addAll(response.body()?.results as ArrayList<MovieModel>)
                    listAdapter?.notifyDataSetChanged()
                    binding?.listScrollview?.fullScroll(View.FOCUS_UP)
                } else {
                    popularList.addAll(response.body()?.results as ArrayList<MovieModel>)
                    listAdapter?.notifyItemRangeInserted((page - 1) * 20, page * 20)
                    addOnScrollListener(page = page)
                }
                binding?.progressBar?.visibility = View.GONE
                isLoading = false
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                binding?.progressBar?.visibility = View.GONE
                isLoading = false
            }
        })
    }

    private fun callSearchList(page: Int) {
        ServiceCalls.getSearchResults(
            page = page,
            searchText = searchText,
            callback = object : Callback<ResultModel> {
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    if (page <= response.body()?.total_pages ?: 1) {
                        searchList.clear()
                        searchList.addAll(response.body()?.results as ArrayList<MovieModel>)
                        if (page == 1) {
                            listAdapter?.replaceList(searchList)
                            binding?.listScrollview?.fullScroll(View.FOCUS_UP)
                        } else {
                            listAdapter?.notifyItemRangeInserted((page - 1) * 20, page * 20)
                            addOnScrollListener(page = page)
                        }
                    }
                    binding?.progressBar?.visibility = View.GONE
                    binding?.popularTextLayout?.visibility = View.GONE
                    isLoading = false
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                    binding?.progressBar?.visibility = View.GONE
                    isLoading = false
                }
            })
    }

    override fun onMovieSelected(id: String, position: Int) {
        movieSelectedPosition = position
        val mainListIntent = Intent(this@MainListActivity, MovieDetailsActivity::class.java)
        mainListIntent.putExtra("id", id)
        startActivityForResult(mainListIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            movieSelectedPosition?.let { listAdapter?.notifyItemChanged(it) }
        }
    }
}
