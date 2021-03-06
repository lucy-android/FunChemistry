package site.budanitskaya.chemistryquiz.fine.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import site.budanitskaya.chemistryquiz.fine.ui.fragments.NotificationsFragment
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        setupInitialTime(c)
        return TimePickerDialog(
            activity, this, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], true
        )
    }

    private fun setupInitialTime(c: Calendar) {
        try {

            val (hour, minute) = sharedPreferences.getString(
                NotificationsFragment.NOTIFICATION_TIME_PREFERENCE_KEY,
                NotificationsFragment.DEFAULT_NOTIFICATION_TIME
            )!!.split(":")
            c.set(Calendar.HOUR_OF_DAY, hour.toInt())
            c.set(Calendar.MINUTE, minute.toInt())
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        sharedPreferences.edit()
            .putString(NotificationsFragment.NOTIFICATION_TIME_PREFERENCE_KEY, "$hourOfDay:$minute")
            .apply()
    }
}