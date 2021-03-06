package site.budanitskaya.chemistryquiz.fine.ui.fragments

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import dagger.hilt.android.AndroidEntryPoint
import site.budanitskaya.chemistryquiz.fine.R
import site.budanitskaya.chemistryquiz.fine.utils.NotificationUtil
import site.budanitskaya.chemistryquiz.fine.utils.TimeUtils
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var preference: SharedPreferences

    companion object {
        const val NOTIFICATION_ON_PREFERENCE_KEY = "preference_notification"
        const val NOTIFICATION_TIME_PREFERENCE_KEY = "preference_notification_time"
        const val DEFAULT_NOTIFICATION_TIME = "21:0"
        const val SOUND_PREFERENCE_KEY = "sound"
    }

    private var notificationSwitchPreference: SwitchPreference? = null
    private var notificationTimePreference: Preference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val d = ResourcesCompat.getDrawable(resources, R.drawable.one, null)
        view.background = d


    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)
        init()
        setupClickListeners()
        notificationTimePreference?.summary =
            TimeUtils.formatTimeSavedInPreference(
                preference.getString(
                    NOTIFICATION_TIME_PREFERENCE_KEY,
                    DEFAULT_NOTIFICATION_TIME
                )
            )
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean =
        when (preference?.key) {
            NOTIFICATION_TIME_PREFERENCE_KEY -> {
                findNavController().navigate(R.id.action_navigation_notifications_to_timePickerFragment)
                true
            }
            else -> false
        }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, pref_key: String?) {
        when (pref_key) {
            NOTIFICATION_TIME_PREFERENCE_KEY -> {
                notificationTimePreference?.summary =
                    TimeUtils.formatTimeSavedInPreference(
                        prefs?.getString(
                            pref_key,
                            DEFAULT_NOTIFICATION_TIME
                        )
                    )
                val isNotificationOn =
                    prefs?.getBoolean(NOTIFICATION_ON_PREFERENCE_KEY, false) ?: false
                if (isNotificationOn) {
                    context?.let { NotificationUtil.scheduleAlarmToTriggerNotification(it) }
                }
            }
            NOTIFICATION_ON_PREFERENCE_KEY -> {
                val value = prefs?.getBoolean(pref_key, false) ?: false
                context?.let {
                    NotificationUtil.toggleNotificationRestartAfterBoot(it, value)
                    if (value) {
                        NotificationUtil.scheduleAlarmToTriggerNotification(it)
                    } else {
                        NotificationUtil.cancelNotification()
                    }
                }
            }

            SOUND_PREFERENCE_KEY -> {
            }
        }
    }

    private fun init() {
        notificationSwitchPreference = findPreference(NOTIFICATION_ON_PREFERENCE_KEY)
        notificationTimePreference = findPreference(NOTIFICATION_TIME_PREFERENCE_KEY)
    }

    private fun setupClickListeners() {
        notificationTimePreference?.onPreferenceClickListener = this
    }
}