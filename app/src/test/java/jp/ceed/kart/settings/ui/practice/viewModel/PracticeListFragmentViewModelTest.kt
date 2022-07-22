package jp.ceed.kart.settings.ui.practice.viewModel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.initMainLooper
import jp.ceed.kart.settings.model.entity.PracticeTrack
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object PracticeListFragmentViewModelTest : Spek({

    lateinit var viewModel: PracticeListFragmentViewModel

    lateinit var itemViewModel: PracticeListItemViewModel

    initMainLooper()

    val practiceRepository: PracticeRepository = mockk(relaxed = true) {
        coEvery {
            findAll()
        } returns listOf(
            PracticeTrack(
                id = 4,
                trackName = "オートパラダイス御殿場",
                startDate = "2022-03-27",
                description = "This is description."
            )
        )
    }

    describe("初期化処理") {
        beforeEachTest {
            viewModel = PracticeListFragmentViewModel(
                practiceRepository = practiceRepository
            )
        }
        it("読み込んだ値がフィールドにセットされている") {
            val list = viewModel.practiceViewModelList.value
            assertThat(list).hasSize(1)
            assertThat(list?.get(0)?.id).isEqualTo(4)
            assertThat(list?.get(0)?.trackName).isEqualTo("オートパラダイス御殿場")
            assertThat(list?.get(0)?.description).isEqualTo("This is description.")
            assertThat(list?.get(0)?.startDate).isEqualTo("2022-03-27")
        }
    }

    describe("クリック時の処理") {
        beforeEachTest {
            viewModel = PracticeListFragmentViewModel(
                practiceRepository = practiceRepository
            )
            itemViewModel = viewModel.practiceViewModelList.value?.get(0) ?: throw AssertionError()
        }

        it("編集ボタンが押されると編集モードになる") {
            assertThat(viewModel.editEvent.value).isNull()
            viewModel.onClick(R.id.editButton, itemViewModel)
            assertThat(viewModel.editEvent.value?.getContentIfNotHandled()).isEqualTo(itemViewModel.id)
        }

        it("削除ボタンが押されると削除の処理が実行される") {
            assertThat(viewModel.deleteButtonEvent.value).isNull()
            viewModel.onClick(R.id.deleteButton, itemViewModel)
            assertThat(viewModel.deleteButtonEvent.value?.getContentIfNotHandled())
                .isInstanceOf(PracticeListItemViewModel::class.java)
        }

        it("リストがタップされると詳細に遷移する") {
            assertThat(viewModel.itemClickEvent.value).isNull()
            viewModel.onClick(R.id.practiceListItemLayout, itemViewModel)
            assertThat(viewModel.itemClickEvent.value?.getContentIfNotHandled())
                .isInstanceOf(PracticeListItemViewModel::class.java)
        }

        it("FABがクリックされると新規作成処理が実行される") {
            assertThat(viewModel.editEvent.value).isNull()
            viewModel.onClickFab()
            assertThat(viewModel.editEvent.value?.getContentIfNotHandled()).isEqualTo(0)
        }

        describe("削除実行時の処理") {
            beforeEachTest {
                viewModel = PracticeListFragmentViewModel(
                    practiceRepository = practiceRepository
                )
            }

            it("リポジトリの削除処理がが実行される") {
                viewModel.deletePractice(2)
                coVerify {
                    practiceRepository.deleteById(2)
                }
            }
        }
    }
})