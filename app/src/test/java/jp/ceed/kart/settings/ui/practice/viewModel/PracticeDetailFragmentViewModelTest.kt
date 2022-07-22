package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.kart.settings.domain.repository.SessionRepository
import jp.ceed.kart.settings.initMainLooper
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.model.entity.Session
import jp.ceed.kart.settings.ui.common.RowControlListener
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object PracticeDetailFragmentViewModelTest : Spek({

    lateinit var viewModel: PracticeDetailFragmentViewModel

    initMainLooper()

    val sessionRepository: SessionRepository = mockk(relaxed = true) {
        coEvery {
            getSessionList(any())
        } returns listOf(
            Session(id = 2, practiceId = 4, startTime = "09:00"),
            Session(id = 3, practiceId = 4, startTime = "10:00"),
        )
    }

    val savedStateHandle: SavedStateHandle = mockk(relaxed = true) {
        every {
            get<Int>("practiceId")
        } returns 4
    }

    describe("初期状態") {
        beforeEachTest {
            viewModel = PracticeDetailFragmentViewModel(
                savedStateHandle = savedStateHandle,
                sessionRepository = sessionRepository,
                resourceRepository = mockk(relaxed = true),
            )
        }
        it("初期値が正しく代入されている") {
            assertThat(viewModel.practiceId).isEqualTo(4)
        }
        it("表示リストの読み込みが正常に完了している") {
            assertThat(viewModel.practiceRowList.value).hasSize(25)
            assertThat(viewModel.practiceRowList.value?.get(0)).isInstanceOf(
                PracticeDetailAdapterItem.PracticeControlItem::class.java
            )
            val controlItem = viewModel.practiceRowList.value?.get(0) as PracticeDetailAdapterItem.PracticeControlItem
            assertThat(controlItem.controlItems).hasSize(2)
            assertThat(viewModel.practiceRowList.value?.get(1)).isInstanceOf(
                PracticeDetailAdapterItem.PracticeRowItem::class.java
            )
        }
    }

    describe("FABクリック時の処理") {
        beforeEachTest {
            viewModel = PracticeDetailFragmentViewModel(
                savedStateHandle = savedStateHandle,
                sessionRepository = sessionRepository,
                resourceRepository = mockk(relaxed = true),
            )
        }
        it("FABがクリックされると適切なイベントが発生する") {
            assertThat(viewModel.event.value).isNull()
            viewModel.onClickFab()
            val eventContent = viewModel.event.value?.getContentIfNotHandled() as PracticeDetailFragmentViewModel.EventContent
            assertThat(eventContent.value).isEqualTo(0)
        }
    }

    describe("制御ボタンクリック時の処理") {
        beforeEachTest {
            viewModel = PracticeDetailFragmentViewModel(
                savedStateHandle = savedStateHandle,
                sessionRepository = sessionRepository,
                resourceRepository = mockk(relaxed = true),
            )
        }
        it("削除ボタンがタップされると適切なイベントが発生する") {
            viewModel.onClickControl(RowControlListener.RowControlCommand.DELETE, 3)
            val eventContent = viewModel.event.value?.getContentIfNotHandled() as PracticeDetailFragmentViewModel.EventContent
            assertThat(eventContent.eventType).isEqualTo(PracticeDetailFragmentViewModel.EventType.CLICK_DELETE_DIALOG)
            assertThat(eventContent.value).isEqualTo(3)
        }
        it("編集ボタンがタップされると編集状態になり、保存ボタンがタップされると編集状態が解除される") {
            viewModel.onClickControl(RowControlListener.RowControlCommand.EDIT, 2)
            var firstRowItem = viewModel.practiceRowList.value?.get(1) as PracticeDetailAdapterItem.PracticeRowItem
            assertThat(firstRowItem.values[0].isEditable).isEqualTo(true)
            viewModel.onClickControl(RowControlListener.RowControlCommand.SAVE, 2)
            firstRowItem = viewModel.practiceRowList.value?.get(1) as PracticeDetailAdapterItem.PracticeRowItem
            assertThat(firstRowItem.values[0].isEditable).isEqualTo(false)
        }
    }

})