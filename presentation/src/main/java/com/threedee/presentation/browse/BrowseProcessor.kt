package com.threedee.presentation.browse

import com.threedee.domain.interactor.browse.GetBufferoos
import com.threedee.presentation.base.BaseActionProcessor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class BrowseProcessor @Inject constructor(private val getBufferoos: GetBufferoos) :
    BaseActionProcessor<BrowseAction, BrowseResult>() {

    override fun getActionProcessors(shared: Observable<BrowseAction>): List<Observable<BrowseResult>> {
        return listOf(shared.connect(getBufferoosProcessor))
    }

    val getBufferoosProcessor: ObservableTransformer<BrowseAction.LoadBufferoosAction, BrowseResult> =
        ObservableTransformer {
            it.switchMap {
                getBufferoos.execute()
                    .map {
                        BrowseResult.Success(it) as BrowseResult
                    }
                    .onErrorReturn {
                        BrowseResult.Error(it.localizedMessage)
                    }
                    .toObservable()
                    .startWith(BrowseResult.Loading)
            }
        }

//    var actionProcessor: ObservableTransformer<BrowseAction, BrowseResult>
//
//    init {
//        actionProcessor = ObservableTransformer {
//            it.publish {
//                it.ofType(BrowseAction.LoadBufferoosAction::class.java)
//                    .compose(getBufferoosProcessor)
//                    .mergeWith(it.filter { it !is BrowseAction.LoadBufferoosAction }
//                        .flatMap {
//                            Observable.error<BrowseResult>(
//                                IllegalArgumentException("Unknown Action type")
//                            )
//                        })
//            }
//        }
//    }
}