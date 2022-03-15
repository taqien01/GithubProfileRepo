package id.reza.profilegithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.reza.profilegithub.R
import id.reza.profilegithub.databinding.ActivityMainBinding
import id.reza.profilegithub.model.OneData
import id.reza.profilegithub.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainListAdapter.Interface {

    val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1

    private lateinit var binding: ActivityMainBinding
    val vm : MainViewModel by viewModel()

    var pageStr = "1"
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: MainListAdapter
    private lateinit var listAll: MutableList<OneData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        initObserver()
        initListener()
    }

    private fun initView(){
        binding.swipeRefresh.isRefreshing = true
//        binding.progressBar.visibility = View.VISIBLE
        mLayoutManager = GridLayoutManager(this, 1)
        vm.getData(pageStr)

        listAll = ArrayList()

        binding.rvList.layoutManager = mLayoutManager
        adapter = MainListAdapter(listAll)

        adapter = MainListAdapter(listAll)

        binding.rvList.adapter = adapter
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            pageStr = "1"
            listAll.clear()
            vm.getData(pageStr)
        }

        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as GridLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                vm.getData(pageStr)
            }
        })

        binding.rvList.addOnScrollListener(scrollListener)
    }

    private fun initObserver() {
        vm.loadingEvent.observe(this){
            binding.swipeRefresh.isRefreshing = it
        }

        vm.pageEvent.observe(this){
            pageStr = it
        }

        vm.errorEvent.observe(this){
            if (pageStr == "1"){
                binding.rvList.visibility = View.GONE
                binding.rlTrouble.visibility = View.VISIBLE

                binding.textView.text = "$it"
            }else{
                Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
            }
        }

        vm.listEvent.observe(this){
            setData(it)
        }

        vm.detailEvent.observe(this){
            Toast.makeText(this, "${it.name}, ${it.email}, ${it.created_at}", Toast.LENGTH_LONG).show()
        }

    }

    fun setData(list: List<OneData>){
        if (list.isNullOrEmpty()){
            binding.rvList.visibility = View.GONE
            binding.rlTrouble.visibility = View.VISIBLE
        }else{
            binding.rvList.visibility = View.VISIBLE
            binding.rlTrouble.visibility = View.GONE

            var listTemp = list.toMutableList()
            listAll.addAll(listTemp)


            adapter.notifyDataSetChanged()
            adapter.setInterface(this)

            scrollListener.setLoaded()

        }

    }

    override fun onClickDetail(item: OneData, position: Int) {
        vm.getOneUser(item.login)
    }
}