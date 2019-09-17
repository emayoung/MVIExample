package com.threedee.presentation.browse

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.threedee.domain.interactor.browse.GetBufferoos
import com.threedee.domain.model.Bufferoo
import com.threedee.presentation.browse.mapper.BufferooMapper
import com.threedee.presentation.browse.model.BufferooView
import com.threedee.presentation.test.factory.BufferooFactory
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowseBufferoosViewModelTest {

    @Mock
    lateinit var mockGetBufferoos: GetBufferoos
    @Mock
    lateinit var mockBufferooMapper: BufferooMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Bufferoo>>>

    private lateinit var bufferoosViewModel: BrowseBufferoosViewModel
    private lateinit var browseProcessor: BrowseProcessor

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<List<Bufferoo>>>()
        mockGetBufferoos = mock()
        mockBufferooMapper = mock()
        browseProcessor = BrowseProcessor(mockGetBufferoos)
        bufferoosViewModel = BrowseBufferoosViewModel(browseProcessor, mockBufferooMapper)
    }

    //<editor-fold desc="Load bufferoos">
    @Test
    fun loadBufferoosIntentReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Success })
    }

    @Test
    fun loadBufferoosIntentWhenSuccessIsNotInProgress() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { !it.inProgress })
    }

    @Test
    fun loadBufferoosIntentReturnsData() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { it.bufferoos == viewList })
    }

    @Test
    fun loadBufferoosIntentReturnsError() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Failed })
    }

    @Test
    fun loadBufferoosIntentWhenErrorIsNotInProgress() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { !it.inProgress })
    }

    @Test
    fun loadBufferoosIntentWhenErrorContainsNoData() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(2, { it.bufferoos == null })
    }

    @Test
    fun loadBufferoosIntentReturnsLoading() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(1, { it is BrowseViewState.InProgress })
    }

    @Test
    fun loadBufferoosIntentBeginsAsIdle() {
        stubGetBufferoos(Flowable.error(RuntimeException()))
        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.LoadingIntent))

        testObserver.assertValueAt(0, { it is BrowseViewState.Idle })
    }
    //</editor-fold>

    //<editor-fold desc="Refresh bufferoos">
    @Test
    fun refreshBufferoosIntentReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Success })
    }

    @Test
    fun refreshBufferoosIntentWhenSuccessIsNotInProgress() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { !it.inProgress })
    }

    @Test
    fun refreshBufferoosIntentReturnsData() {
        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])
        stubGetBufferoos(Flowable.just(list))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { it.bufferoos == viewList })
    }

    @Test
    fun refreshBufferoosIntentReturnsError() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Failed })
    }

    @Test
    fun refreshBufferoosIntentWhenErrorIsNotInProgress() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { !it.inProgress })
    }

    @Test
    fun refreshBufferoosIntentWhenErrorContainsNoData() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(2, { it.bufferoos == null })
    }

    @Test
    fun refreshBufferoosIntentReturnsLoading() {
        stubGetBufferoos(Flowable.error(RuntimeException()))

        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(1, { it is BrowseViewState.InProgress })
    }

    @Test
    fun refreshBufferoosIntentBeginsAsIdle() {
        stubGetBufferoos(Flowable.error(RuntimeException()))
        val testObserver = bufferoosViewModel.states().test()

        bufferoosViewModel.processIntents(Observable.just(BrowseIntent.RefreshBufferoosIntent))

        testObserver.assertValueAt(0, { it is BrowseViewState.Idle })
    }
    //</editor-fold>

    private fun stubBufferooMapperMapToView(
        bufferooView: BufferooView,
        bufferoo: Bufferoo
    ) {
        whenever(mockBufferooMapper.mapToView(bufferoo))
            .thenReturn(bufferooView)
    }

    private fun stubGetBufferoos(flowable: Flowable<List<Bufferoo>>) {
        whenever(mockGetBufferoos.execute(anyOrNull()))
            .thenReturn(flowable)
    }
}