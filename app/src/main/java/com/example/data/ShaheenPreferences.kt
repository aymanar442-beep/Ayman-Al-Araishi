package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.ShaheenConfig

class ShaheenPreferences(context: Context) {
  private val prefs: SharedPreferences =
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

  companion object {
    private const val PREFS_NAME = "shaheen_secure_settings"
    private const val KEY_USERNAME = "username"
    private const val KEY_LICENSE_KEY = "license_key"
    private const val KEY_API_KEY = "api_key"
    private const val KEY_DISCLAIMER_ACCEPTED = "disclaimer_accepted"
    private const val KEY_EXTRA_USERS = "extra_users_count"

    private const val DEFAULT_USERNAME = "ayman"
    private const val DEFAULT_LICENSE = "SH-9924-SEC-ALPHA-88X"
    private const val DEFAULT_API_KEY = "sh_live_k82f990141be297d09873a"
  }

  fun loadConfig(): ShaheenConfig {
    val username = prefs.getString(KEY_USERNAME, DEFAULT_USERNAME) ?: DEFAULT_USERNAME
    val licenseKey = prefs.getString(KEY_LICENSE_KEY, DEFAULT_LICENSE) ?: DEFAULT_LICENSE
    val apiKey = prefs.getString(KEY_API_KEY, DEFAULT_API_KEY) ?: DEFAULT_API_KEY
    val disclaimerAccepted = prefs.getBoolean(KEY_DISCLAIMER_ACCEPTED, false)
    val extraUsers = prefs.getInt(KEY_EXTRA_USERS, 0)
    return ShaheenConfig(
      username = username,
      licenseKey = licenseKey,
      apiKey = apiKey,
      hasAcceptedDisclaimer = disclaimerAccepted,
      additionalUsersCount = extraUsers
    )
  }

  fun saveConfig(config: ShaheenConfig) {
    prefs.edit()
      .putString(KEY_USERNAME, config.username)
      .putString(KEY_LICENSE_KEY, config.licenseKey)
      .putString(KEY_API_KEY, config.apiKey)
      .putBoolean(KEY_DISCLAIMER_ACCEPTED, config.hasAcceptedDisclaimer)
      .putInt(KEY_EXTRA_USERS, config.additionalUsersCount)
      .apply()
  }

  fun saveUsername(username: String) {
    prefs.edit().putString(KEY_USERNAME, username).apply()
  }

  fun saveLicenseKey(licenseKey: String) {
    prefs.edit().putString(KEY_LICENSE_KEY, licenseKey).apply()
  }

  fun saveApiKey(apiKey: String) {
    prefs.edit().putString(KEY_API_KEY, apiKey).apply()
  }

  fun saveDisclaimerAccepted(accepted: Boolean) {
    prefs.edit().putBoolean(KEY_DISCLAIMER_ACCEPTED, accepted).apply()
  }

  fun saveExtraUsers(count: Int) {
    prefs.edit().putInt(KEY_EXTRA_USERS, count).apply()
  }
}
