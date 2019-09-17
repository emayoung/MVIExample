package com.threedee.presentation.browse

import com.threedee.domain.interactor.browse.GetBufferoos
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class BrowseProcessor @Inject constructor(private val getBufferoos: GetBufferoos) {

    private val getBufferoosProcessor: ObservableTransformer<BrowseAction.LoadBufferoosAction, BrowseResult> =
        ObservableTransformer {
            it.switchMap {
                getBufferoos.execute()
                    .map {
                        BrowseResult.LoadBufferoosTask.success(it)
                    }
                    .onErrorReturn {
                        BrowseResult.LoadBufferoosTask.failure()
                    }
                    .toObservable()
                    .startWith(BrowseResult.LoadBufferoosTask.inFlight())
            }
        }

    var actionProcessor: ObservableTransformer<BrowseAction, BrowseResult>

    init {
        actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(BrowseAction.LoadBufferoosAction::class.java)
                    .compose(getBufferoosProcessor)
                    .mergeWith(it.filter { it !is BrowseAction.LoadBufferoosAction }
                        .flatMap {
                            Observable.error<BrowseResult>(
                                IllegalArgumentException("Unknown Action type")
                            )
                        })
            }
        }
    }
}