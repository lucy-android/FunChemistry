package site.budanitskaya.chemistryquiz.fine.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import site.budanitskaya.chemistryquiz.fine.R
import site.budanitskaya.chemistryquiz.fine.databinding.FragmentChipsOverBinding
import site.budanitskaya.chemistryquiz.fine.utils.colorWrapper

@AndroidEntryPoint
class ChipsOverFragment : Fragment() {

    private lateinit var binding: FragmentChipsOverBinding

    private lateinit var args: ChipsOverFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chips_over, container, false
        )
        args = ChipsOverFragmentArgs.fromBundle(
            requireArguments()
        )
        val sum = args.number.sum()
        binding.numberTxt.text = (sum / 1000).toString()

        viewLifecycleOwner.lifecycleScope.launch {
            var x = 0
            while (x < sum/1000) {
                binding.numberTxt.text = x.toString()
                x++
                delay(100)
            }
        }

        val pientri = mutableListOf<PieEntry>()

        for (i in args.number) {
            pientri.add(PieEntry(i.toFloat()))
        }

        val x = PieDataSet(pientri, "Label")
        x.setColors(colorWrapper(R.color.material_purple_a700, requireContext()), colorWrapper(R.color.yellow, requireContext()), colorWrapper(R.color.material_green_a200, requireContext()),colorWrapper(R.color.peach, requireContext()), colorWrapper(R.color.pink, requireContext()));
        with(binding.chart1) {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            data = PieData(x)
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
        }
        return binding.root
    }
}