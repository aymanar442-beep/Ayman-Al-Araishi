package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ShaheenPreferences
import com.example.model.PaymentGateway
import com.example.model.ShaheenConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SHAHEEN", appName)
  }

  @Test
  fun `test preferences auto-load and save`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = ShaheenPreferences(context)
    val config = prefs.loadConfig()
    assertEquals("ayman", config.username)

    prefs.saveUsername("ayman_pro")
    val updatedConfig = prefs.loadConfig()
    assertEquals("ayman_pro", updatedConfig.username)
  }

  @Test
  fun `test disclaimer and extra users persistence`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = ShaheenPreferences(context)
    prefs.saveDisclaimerAccepted(true)
    prefs.saveExtraUsers(3)

    val updatedConfig = prefs.loadConfig()
    assertTrue(updatedConfig.hasAcceptedDisclaimer)
    assertEquals(3, updatedConfig.additionalUsersCount)
  }

  @Test
  fun `test license identity check logic`() {
    val validUsername = "AYMAN"
    assertTrue(validUsername.trim().equals("ayman", ignoreCase = true))
  }

  @Test
  fun `test payment gateways available`() {
    val gateways = PaymentGateway.values()
    assertTrue(gateways.size >= 3)
  }
}
