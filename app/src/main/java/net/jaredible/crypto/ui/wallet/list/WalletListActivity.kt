package net.jaredible.crypto.ui.wallet.list

import android.graphics.Canvas
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseActivity
import net.jaredible.crypto.ui.custom.SwipeController
import net.jaredible.crypto.ui.custom.SwipeControllerActions

class WalletListActivity : BaseActivity(), WalletListView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: WalletListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewModel: WalletListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = WalletListAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.vWallets).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onLeftClicked(position: Int) {}
            override fun onRightClicked(position: Int) {
                viewAdapter.wallets.removeAt(position)
                viewAdapter.notifyItemRemoved(position)
                viewAdapter.notifyItemRangeChanged(position, viewAdapter.itemCount)
            }
        })

        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })

        viewModel = ViewModelProvider(this).get(WalletListViewModel::class.java)
        viewModel.getWallets().observe(this, Observer {
            viewAdapter.setWallets(it)
        })
    }

}