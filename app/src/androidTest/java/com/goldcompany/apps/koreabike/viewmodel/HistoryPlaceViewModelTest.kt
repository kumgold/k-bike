package com.goldcompany.apps.koreabike.viewmodel

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.goldcompany.apps.koreabike.MainCoroutineRule
import com.goldcompany.apps.koreabike.runBlockingTest
import com.goldcompany.apps.koreabike.ui.historyplace.HistoryPlaceViewModel
import com.goldcompany.koreabike.data.db.KBikeDatabase
import com.goldcompany.koreabike.domain.usecase.DeleteAddressUseCase
import com.goldcompany.koreabike.domain.usecase.GetAllHistoryAddressUseCase
import com.goldcompany.koreabike.domain.usecase.GetCurrentAddressUseCase
import com.goldcompany.koreabike.domain.usecase.InsertAddressUseCase
import com.goldcompany.koreabike.domain.usecase.UpdateCurrentAddressUnselectedUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject


@HiltAndroidTest
class HistoryPlaceViewModelTest {

    private lateinit var database: KBikeDatabase
    private lateinit var viewModel: HistoryPlaceViewModel

    private val hiltRule = HiltAndroidRule(this)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(coroutineRule)

    @Inject
    lateinit var getCurrentAddressUseCase: GetCurrentAddressUseCase
    @Inject
    lateinit var getAllHistoryAddressUseCase: GetAllHistoryAddressUseCase
    @Inject
    lateinit var updateCurrentAddressUnselectedUseCase: UpdateCurrentAddressUnselectedUseCase
    @Inject
    lateinit var insertAddressUseCase: InsertAddressUseCase
    @Inject
    lateinit var deleteAddressUseCase: DeleteAddressUseCase

    @Before
    fun setup() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, KBikeDatabase::class.java).build()
        viewModel = HistoryPlaceViewModel(
            getCurrentAddressUseCase = getCurrentAddressUseCase,
            getAllHistoryAddressUseCase = getAllHistoryAddressUseCase,
            updateCurrentAddressUnselectedUseCase = updateCurrentAddressUnselectedUseCase,
            insertAddressUseCase = insertAddressUseCase,
            deleteAddressUseCase = deleteAddressUseCase
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testDefaultValue() {
        coroutineRule.runBlockingTest {
            Assert.assertEquals(viewModel.uiState.value.items.isEmpty(), true)
        }
    }
}