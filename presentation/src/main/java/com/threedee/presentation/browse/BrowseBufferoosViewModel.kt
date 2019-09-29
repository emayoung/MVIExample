package com.threedee.presentation.browse

import androidx.lifecycle.ViewModel
import com.threedee.presentation.base.BaseIntent
import com.threedee.presentation.base.BaseViewModel
import com.threedee.presentation.base.model.TaskStatus
import com.threedee.presentation.browse.mapper.BufferooMapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class BrowseBufferoosViewModel @Inject internal constructor(
    private val browseProcessor: BrowseProcessor,
    private val bufferooMapper: BufferooMapper
) : ViewModel(), BaseViewModel<BrowseIntent, BrowseViewState> {

    private var intentsSubject: PublishSubject<BrowseIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<BrowseIntent, BrowseIntent> =
        ObservableTransformer<BrowseIntent, BrowseIntent> {
            it.publish {
                Observable.merge(it.ofType(BrowseIntent.InitialIntent::class.java).take(1),
                    it.filter { intent -> intent !is BrowseIntent.InitialIntent }
                )
            }
        }

    private val reducer: BiFunction<BrowseViewState, BrowseResult, BrowseViewState> =
        BiFunction<BrowseViewState, BrowseResult, BrowseViewState> { previousState, result ->
            println("previous state:$previousState, result: $result")
            when (result) {
                BrowseResult.Loading -> {
                    BrowseViewState.InProgress
                }
                is BrowseResult.Error -> {
                    BrowseViewState.Failed
                }
                is BrowseResult.Success -> {
                    BrowseViewState.Success(
                        result.data?.map { bufferooMapper.mapToView(it) })
                }
                is BrowseResult.Idle -> {
                    BrowseViewState.Idle()
                }
            }
        }
    private val statesSubject: Observable<BrowseViewState> = compose()

    override fun processIntents(intents: Observable<BrowseIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<BrowseViewState> {
        return statesSubject
    }

    private fun compose(): Observable<BrowseViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(browseProcessor)
            .scan<BrowseViewState>(BrowseViewState.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: BaseIntent): BrowseAction {
        return when (intent) {
            is BrowseIntent.LoadingIntent -> BrowseAction.LoadBufferoosAction
            is BrowseIntent.RefreshBufferoosIntent -> BrowseAction.LoadBufferoosAction
            is BrowseIntent.InitialIntent -> BrowseAction.LoadBufferoosAction
            else -> throw UnsupportedOperationException(
                "Oops, that looks like an unknown intent: $intent"
            )
        }
    }
}