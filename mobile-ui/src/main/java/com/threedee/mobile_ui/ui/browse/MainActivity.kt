package com.threedee.mobile_ui.ui.browse

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.mapper.BufferooMapper
import com.threedee.mobile_ui.widget.empty.EmptyListener
import com.threedee.mobile_ui.widget.error.ErrorListener
import com.threedee.presentation.base.BaseView
import com.threedee.presentation.browse.BrowseBufferoosViewModel
import com.threedee.presentation.browse.BrowseIntent
import com.threedee.presentation.browse.BrowseViewState
import com.threedee.presentation.browse.model.BufferooView
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() ,
    BaseView<BrowseIntent, BrowseViewState> {

    private val loadConversationsIntentPublisher =
        BehaviorSubject.create<BrowseIntent.LoadingIntent>()
    private val refreshConversationsIntentPublisher =
        BehaviorSubject.create<BrowseIntent.RefreshBufferoosIntent>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: BufferooMapper
    private lateinit var browseBufferoosViewModel: BrowseBufferoosViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        browseBufferoosViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseBufferoosViewModel::class.java)

        setupBrowseRecycler()
        setupViewListeners()

        compositeDisposable.add(browseBufferoosViewModel.states().subscribe({ render(it) }))
        browseBufferoosViewModel.processIntents(intents())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<BrowseIntent> {
        return Observable.merge(initialIntent(), loadConversationsIntentPublisher,
            refreshConversationsIntentPublisher)
    }

    private fun initialIntent(): Observable<BrowseIntent.InitialIntent> {
        return Observable.just(BrowseIntent.InitialIntent)
    }

    override fun render(state: BrowseViewState) {
        when {
            state.inProgress -> {
                setupScreenForLoadingState()
            }
            state is BrowseViewState.Failed -> {
                setupScreenForError()
            }
            state is BrowseViewState.Success -> {
                setupScreenForSuccess(state.bufferoos)
            }
        }
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<BufferooView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(bufferoos: List<BufferooView>) {
        browseAdapter.bufferoos = bufferoos.map { mapper.mapToViewModel(it) }
        browseAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError() {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            refreshConversationsIntentPublisher.onNext(BrowseIntent.RefreshBufferoosIntent)
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            refreshConversationsIntentPublisher.onNext(BrowseIntent.RefreshBufferoosIntent)
        }
    }
}
