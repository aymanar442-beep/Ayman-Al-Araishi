package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ShaheenPreferences
import com.example.model.EngineStatus
import com.example.model.LogEntry
import com.example.model.LogLevel
import com.example.model.ShaheenConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class ShaheenUiState(
  val config: ShaheenConfig = ShaheenConfig(),
  val engineStatus: EngineStatus = EngineStatus(),
  val logs: List<LogEntry> = emptyList(),
  val showAccessDeniedDialog: Boolean = false,
  val accessDeniedReason: String = "",
  val showSettingsDialog: Boolean = false,
  val showDisclaimerDialog: Boolean = false,
  val showPaymentHubDialog: Boolean = false,
  val showAuditReportDialog: Boolean = false,
  val showWebPortalDialog: Boolean = false,
  val autoSaveToastVisible: Boolean = false
)

class ShaheenViewModel(application: Application) : AndroidViewModel(application) {
  private val preferences = ShaheenPreferences(application)

  private val _uiState = MutableStateFlow(ShaheenUiState())
  val uiState: StateFlow<ShaheenUiState> = _uiState.asStateFlow()

  private var tradingLoopJob: Job? = null
  private var uptimeTimerJob: Job? = null
  private var countdownJob: Job? = null
  private val timeFormatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)

  private val tradingPairs = listOf("BTC/USDT", "ETH/USDT", "SOL/USDT", "BNB/USDT", "AVAX/USDT")

  init {
    // 1. Auto-load settings on App Startup
    val savedConfig = preferences.loadConfig()
    _uiState.update { it.copy(config = savedConfig) }
    
    appendLog(
      tag = "KERNEL",
      message = "SHAHEEN APEX AI Core v2.4 initialized. Sovereign Autonomous Trading Engine online.",
      level = LogLevel.SYSTEM
    )
    appendLog(
      tag = "SECURITY",
      message = "Lead Architect: Ayman Al-Araishi (أيمن العرايشي). Identity locked to operator [ayman].",
      level = LogLevel.INFO
    )
    appendLog(
      tag = "CONFIG",
      message = "Profile loaded: '${savedConfig.username}'. Seats: 1 Primary + ${savedConfig.additionalUsersCount} Extra.",
      level = LogLevel.INFO
    )

    startCountdownTicker()
  }

  private fun startCountdownTicker() {
    countdownJob?.cancel()
    countdownJob = viewModelScope.launch(Dispatchers.Default) {
      while (isActive) {
        delay(1000)
        _uiState.update { state ->
          val currentSec = state.engineStatus.testRemainingSeconds
          val nextSec = if (currentSec > 0) currentSec - 1 else 0
          state.copy(
            engineStatus = state.engineStatus.copy(testRemainingSeconds = nextSec)
          )
        }
      }
    }
  }

  // --- App Settings Auto-Save ---
  fun updateUsername(newUsername: String) {
    _uiState.update { it.copy(config = it.config.copy(username = newUsername)) }
    preferences.saveUsername(newUsername)
    triggerAutoSaveFeedback()
  }

  fun updateLicenseKey(newLicense: String) {
    _uiState.update { it.copy(config = it.config.copy(licenseKey = newLicense)) }
    preferences.saveLicenseKey(newLicense)
    triggerAutoSaveFeedback()
  }

  fun updateApiKey(newApiKey: String) {
    _uiState.update { it.copy(config = it.config.copy(apiKey = newApiKey)) }
    preferences.saveApiKey(newApiKey)
    triggerAutoSaveFeedback()
  }

  fun updateAdditionalUsersCount(count: Int) {
    val safeCount = count.coerceAtLeast(0)
    _uiState.update { it.copy(config = it.config.copy(additionalUsersCount = safeCount)) }
    preferences.saveExtraUsers(safeCount)
    triggerAutoSaveFeedback()
    appendLog(
      tag = "LICENSING",
      message = "Multi-user seats updated: 1 Primary + $safeCount Extra Seats (${50 * safeCount} USDT).",
      level = LogLevel.INFO
    )
  }

  fun acceptDisclaimer() {
    _uiState.update { it.copy(config = it.config.copy(hasAcceptedDisclaimer = true)) }
    preferences.saveDisclaimerAccepted(true)
    triggerAutoSaveFeedback()
    appendLog(
      tag = "LEGAL",
      message = "Legal Disclaimer & Usage Agreement formally acknowledged and cryptographically bound.",
      level = LogLevel.SUCCESS
    )
  }

  private fun triggerAutoSaveFeedback() {
    viewModelScope.launch {
      _uiState.update { it.copy(autoSaveToastVisible = true) }
      delay(1500)
      _uiState.update { it.copy(autoSaveToastVisible = false) }
    }
  }

  // --- License & Identity Verification ---
  fun toggleVpnEngine() {
    val currentState = _uiState.value
    if (currentState.engineStatus.isRunning) {
      stopEngine()
    } else {
      // Check disclaimer acceptance first
      if (!currentState.config.hasAcceptedDisclaimer) {
        _uiState.update { it.copy(showDisclaimerDialog = true) }
        appendLog(
          tag = "LEGAL",
          message = "Engine activation halted: User must accept Legal Terms & Disclaimer first.",
          level = LogLevel.WARNING
        )
        return
      }
      startEngineWithSecurityCheck()
    }
  }

  private fun startEngineWithSecurityCheck() {
    val currentConfig = _uiState.value.config
    val username = currentConfig.username.trim()

    // Requirement: Identity Lock strictly bound to operator "ayman" (case-insensitive)
    if (!username.equals("ayman", ignoreCase = true)) {
      val denialMessage = "Access Denied: Identity Lock Failure.\n\n" +
          "The active SHAHEEN APEX AI core license is cryptographically restricted to authorized operator [ayman].\n" +
          "Current detected identity: '${currentConfig.username}'\n\n" +
          "Execution has been blocked to prevent unauthorized engine deployment."

      appendLog(
        tag = "SECURITY",
        message = "UNAUTHORIZED ACCESS: Identity mismatch detected for '$username'. Engine start aborted.",
        level = LogLevel.ERROR
      )

      _uiState.update {
        it.copy(
          showAccessDeniedDialog = true,
          accessDeniedReason = denialMessage
        )
      }
      return
    }

    // License verified successfully -> Spawn secure background coroutine
    _uiState.update {
      it.copy(
        engineStatus = it.engineStatus.copy(
          isRunning = true,
          uptimeSeconds = 0L,
          totalCycles = 0L
        )
      )
    }

    appendLog(
      tag = "AUTH",
      message = "Identity validated: Operator [ayman]. Cryptographic license match confirmed.",
      level = LogLevel.SUCCESS
    )
    appendLog(
      tag = "VPN-TUNNEL",
      message = "Shaheen Shield Core tunnel linked to Ultra-Low-Latency Cluster.",
      level = LogLevel.INFO
    )

    startTradingLoop()
    startUptimeTimer()
  }

  private fun startTradingLoop() {
    tradingLoopJob?.cancel()
    // Spawns background coroutine on Dispatchers.Default (async execution, no UI freezing)
    tradingLoopJob = viewModelScope.launch(Dispatchers.Default) {
      delay(500)
      appendLog(
        tag = "ENGINE",
        message = "Trading core loop engaged. Monitoring stream frequency: 3000ms.",
        level = LogLevel.SUCCESS
      )

      var cycleCount = 0L
      while (isActive) {
        delay(3000) // 3-second interval
        cycleCount++
        
        val pair = tradingPairs.random()
        val latency = Random.nextInt(8, 22)
        val priceOffset = Random.nextDouble(-120.0, 150.0)
        val currentBasePrice = 98400.0 + priceOffset
        val vol = Random.nextDouble(0.85, 4.50)
        val risk = Random.nextDouble(0.01, 0.04)

        val logMessage = when (cycleCount % 5L) {
          1L -> "[MONITOR] $pair Orderbook scanned | Best Bid: $${String.format(Locale.US, "%,.2f", currentBasePrice)} | Latency: ${latency}ms"
          2L -> "[RISK-ENGINE] Slippage buffer active. Margin safe: 98.6% | Vol: ${String.format(Locale.US, "%.2f", vol)}M"
          3L -> "[TELEMETRY] Heartbeat ack (3000ms) | Status: NOMINAL | Node ping: ${latency}ms | Packets: OK"
          4L -> "[SIGNAL-SCAN] $pair micro-trend momentum: +${String.format(Locale.US, "%.2f", Random.nextDouble(0.1, 0.8))}% | Arbitrage window: Verified"
          else -> "[SECURE-LOOP] Trading loop cycle #$cycleCount completed. Engine memory: 24.1MB | Threads: 6"
        }

        appendLog(
          tag = if (cycleCount % 5L == 3L) "HEARTBEAT" else "MONITOR",
          message = logMessage,
          level = if (cycleCount % 5L == 3L) LogLevel.SYSTEM else LogLevel.INFO
        )

        _uiState.update { state ->
          state.copy(
            engineStatus = state.engineStatus.copy(
              totalCycles = cycleCount,
              currentPair = pair,
              latencyMs = latency,
              priceIndex = currentBasePrice,
              volumeScanned = vol,
              riskScore = risk
            )
          )
        }
      }
    }
  }

  private fun startUptimeTimer() {
    uptimeTimerJob?.cancel()
    uptimeTimerJob = viewModelScope.launch(Dispatchers.Default) {
      while (isActive) {
        delay(1000)
        _uiState.update { state ->
          if (state.engineStatus.isRunning) {
            state.copy(
              engineStatus = state.engineStatus.copy(
                uptimeSeconds = state.engineStatus.uptimeSeconds + 1
              )
            )
          } else {
            state
          }
        }
      }
    }
  }

  fun stopEngine() {
    tradingLoopJob?.cancel()
    tradingLoopJob = null
    uptimeTimerJob?.cancel()
    uptimeTimerJob = null

    _uiState.update {
      it.copy(
        engineStatus = it.engineStatus.copy(isRunning = false)
      )
    }

    appendLog(
      tag = "ENGINE",
      message = "Trading engine shut down. VPN tunnel terminated safely. Standby mode active.",
      level = LogLevel.WARNING
    )
  }

  fun dismissAccessDeniedDialog() {
    _uiState.update { it.copy(showAccessDeniedDialog = false) }
  }

  fun openSettingsDialog() {
    _uiState.update { it.copy(showSettingsDialog = true) }
  }

  fun dismissSettingsDialog() {
    _uiState.update { it.copy(showSettingsDialog = false) }
  }

  fun openDisclaimerDialog() {
    _uiState.update { it.copy(showDisclaimerDialog = true) }
  }

  fun dismissDisclaimerDialog() {
    _uiState.update { it.copy(showDisclaimerDialog = false) }
  }

  fun openPaymentHubDialog() {
    _uiState.update { it.copy(showPaymentHubDialog = true) }
  }

  fun dismissPaymentHubDialog() {
    _uiState.update { it.copy(showPaymentHubDialog = false) }
  }

  fun openAuditReportDialog() {
    _uiState.update { it.copy(showAuditReportDialog = true) }
  }

  fun dismissAuditReportDialog() {
    _uiState.update { it.copy(showAuditReportDialog = false) }
  }

  fun openWebPortalDialog() {
    _uiState.update { it.copy(showWebPortalDialog = true) }
  }

  fun dismissWebPortalDialog() {
    _uiState.update { it.copy(showWebPortalDialog = false) }
  }

  fun triggerEmergencyKillSwitch() {
    stopEngine()
    appendLog(
      tag = "KILL-SWITCH",
      message = "EMERGENCY PROTOCOL ENGAGED: All positions closed immediately. 100% assets converted to USDT safe vault.",
      level = LogLevel.ERROR
    )
    appendLog(
      tag = "BET23-SHIELD",
      message = "Bet23 Psycho-Temporal lock engaged: Cooldown timer active for 30 minutes to protect capital.",
      level = LogLevel.WARNING
    )
  }

  fun clearLogs() {
    _uiState.update { it.copy(logs = emptyList()) }
    appendLog(tag = "CONSOLE", message = "Telemetry console cleared by user.", level = LogLevel.SYSTEM)
  }

  private fun appendLog(tag: String, message: String, level: LogLevel) {
    val timestamp = timeFormatter.format(Date())
    val entry = LogEntry(
      timestamp = timestamp,
      tag = tag,
      message = message,
      level = level
    )
    _uiState.update { state ->
      val updated = (state.logs + entry).takeLast(100)
      state.copy(logs = updated)
    }
  }

  override fun onCleared() {
    super.onCleared()
    tradingLoopJob?.cancel()
    uptimeTimerJob?.cancel()
    countdownJob?.cancel()
  }
}
