package id.muhammadfaisal.moviedb.bottomsheet

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.api.model.response.GenresItem
import id.muhammadfaisal.moviedb.databinding.FragmentFilterBottomSheetDialogBinding
import id.muhammadfaisal.moviedb.listener.BottomSheetListener
import id.muhammadfaisal.moviedb.vm.MovieViewModel
import java.lang.StringBuilder

@AndroidEntryPoint
class FilterBottomSheetDialogFragment(val listener: BottomSheetListener) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomSheetDialogBinding

    private lateinit var movieViewModel: MovieViewModel
    private var selectedIds = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentFilterBottomSheetDialogBinding.inflate(this.layoutInflater)
        this.binding.lifecycleOwner = this
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.setupViewModel()
        this.data()
        this.init()
    }

    private fun init() {
        this.binding.let {
            it.buttonSave.setOnClickListener {
                this.saveConfig()
            }

            it.imageClose.setOnClickListener {
                dismiss()
            }
        }

    }

    private fun saveConfig() {
        val ids = this.binding.chipGroup.checkedChipIds

        val sb = StringBuilder()
        for (id in ids) {
            sb.append("$id,")
        }

        this.movieViewModel.setSelectedGenre(sb.toString().dropLast(1))
        this.listener.onDismissed()

        dismiss()
    }

    private fun setupViewModel() {
        this.movieViewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
        this.binding.movieVM = this.movieViewModel
        this.binding.lifecycleOwner = this
    }

    private fun data() {

        this.movieViewModel.getGenres()
        this.movieViewModel.genres.observe(this) {
            val genres = it.genres

            if (genres != null) {
                if (binding.chipGroup.childCount == 0) {

                    for (genre in genres) {
                        this.createChip(genre)
                    }
                } else {
                    val selectedGenre = this.movieViewModel.getSelectedGenre()
                    if (selectedGenre != "") {
                        val selectedGenres = selectedGenre.split(",")

                        for (selected in selectedGenres) {
                            for (view in binding.chipGroup.children) {
                                if (view.id == selected.toInt()) {
                                    //Assume, has been selected before
                                    (view as Chip).isChecked = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createChip(genre: GenresItem) {
        val drawable = ChipDrawable.createFromAttributes(
            requireContext(),
            null,
            0,
            R.style.MovieDB_MaterialComponents_Chip_Choice
        )

        val chip = Chip(requireContext())
        chip.width = LayoutParams.WRAP_CONTENT
        chip.height = LayoutParams.WRAP_CONTENT
        chip.text = genre.name
        chip.id = genre.id

        chip.setChipDrawable(drawable)
        chip.setTextAppearance(R.style.MovieDB_MaterialComponents_Chip_Choice)

        this.binding.chipGroup.addView(chip)
    }
}