package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.initMainLooper
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object EditPracticeDialogFragmentViewModelTest : Spek({

    initMainLooper()

    lateinit var viewModel: EditPracticeDialogFragmentViewModel

    lateinit var savedStateHandle: SavedStateHandle

    val trackRepository: TrackRepository = mockk(relaxed = true) {
        coEvery {
            getTrackList()
        } returns listOf(
            Track(id = 1, name = "オートパラダイス御殿場"),
            Track(id = 2, name = "大井松田カートランド"),
        )
    }

    val practiceRepository: PracticeRepository = mockk(relaxed = true) {
        coEvery {
            findPracticeTrackByPracticeId(any())
        } returns PracticeTrack(
            id = 1,
            trackName = "サーキット秋ヶ瀬",
            startDate = "2022-02-02",
        )
    }

    describe("初期状態") {
        context("更新モードの時"){
            beforeEachTest {
                viewModel = EditPracticeDialogFragmentViewModel(
                    savedStateHandle = savedStateHandle,
                    practiceRepository = practiceRepository,
                    trackRepository = trackRepository,
                    uiUtil = mockk(relaxed = true),
                )
                savedStateHandle = mockk(relaxed = true) {
                    every {
                        get<Int>("practiceId")
                    } returns 3
                }
            }
            it("パラメータが正しく設定される") {
                assertThat(viewModel.practiceId).isEqualTo(3)
            }
            it("トラックリストが読み込まれる") {
                assertThat(viewModel.trackList).hasSize(2)
                assertThat(viewModel.trackList[0].id).isEqualTo(1)
                assertThat(viewModel.trackList[1].name).isEqualTo("大井松田カートランド")
            }
            it("ラベルリストが設定される") {
                assertThat(viewModel.labelList.value).hasSize(2)
                assertThat(viewModel.labelList.value?.get(0)).isEqualTo("オートパラダイス御殿場")
            }
        }
    }
})