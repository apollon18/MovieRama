package apo.mor.movierama.Activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apo.mor.movierama.GeneralUtils
import apo.mor.movierama.Models.MovieModel
import apo.mor.movierama.databinding.ActivityMainListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class MainListActivity : AppCompatActivity() {

    private var binding: ActivityMainListBinding? = null
    private var popularList: ArrayList<MovieModel> = ArrayList()
    private var searchList: ArrayList<MovieModel> = ArrayList()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.headerText?.text = GeneralUtils.getHeaderText(this@MainListActivity)
        popularList = Gson().fromJson(intent.getStringExtra("list"), object : TypeToken<ArrayList<MovieModel>>(){}.type)
    }
}