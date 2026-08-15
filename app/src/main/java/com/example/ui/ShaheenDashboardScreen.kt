package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.AccessDeniedDialog
import com.example.ui.components.AuditReportDialog
import com.example.ui.components.LegalDisclaimerDialog
import com.example.ui.components.MetricsGrid
import com.example.ui.components.PaymentHubDialog
import com.example.ui.components.SettingsDialog
import com.example.ui.components.ShaheenGlobalInnovationHub
import com.example.ui.components.TelemetryConsole
import com.example.ui.components.VpnToggleButton
import com.example.ui.components.WebPortalBlueprintDialog
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.ConsoleYellow
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
import com.example.ui.theme.InactiveCrimson
import com.example.ui.theme.ShaheenBackground
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenMetallicBorderLight
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.ShaheenSurfaceDark
import com.example.ui.theme.ShaheenSurfaceElevated
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun ShaheenDashboardScreen(
  viewModel: ShaheenViewModel
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val isAyman = uiState.config.username.trim().equals("ayman", ignoreCase = true)

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(ShaheenBackground),
    containerColor = ShaheenBackground,
    contentWindowInsets = WindowInsets.safeDrawing
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              ShaheenSurfaceDark,
              ShaheenBackground,
              ShaheenBackground
            )
          )
        )
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp)
      ) {
        // 1. Top Tactical Bar with Official Falcon Logo
        item {
          TopTacticalBar(
            username = uiState.config.username,
            isAyman = isAyman,
            isRunning = uiState.engineStatus.isRunning,
            onOpenSettings = { viewModel.openSettingsDialog() },
            onOpenAudit = { viewModel.openAuditReportDialog() }
          )
        }

        // 2. Beta Testing Countdown & Commercial Readiness Ribbon
        item {
          TestingCountdownBanner(
            remainingSeconds = uiState.engineStatus.testRemainingSeconds,
            onOpenAudit = { viewModel.openAuditReportDialog() }
          )
        }

        // 3. Identity Status Chip & Operator Binding
        item {
          IdentityStatusBanner(
            username = uiState.config.username,
            isAyman = isAyman,
            extraSeats = uiState.config.additionalUsersCount,
            hasAcceptedDisclaimer = uiState.config.hasAcceptedDisclaimer,
            onConfigure = { viewModel.openSettingsDialog() },
            onOpenDisclaimer = { viewModel.openDisclaimerDialog() }
          )
        }

        // 4. Central VPN Toggle Button
        item {
          VpnToggleButton(
            isRunning = uiState.engineStatus.isRunning,
            onToggle = { viewModel.toggleVpnEngine() },
            modifier = Modifier.fillMaxWidth()
          )
        }

        // 5. Commercial Licensing & Crypto Payment Hub Card
        item {
          CommercialLicensingBanner(
            extraUsers = uiState.config.additionalUsersCount,
            onOpenPaymentHub = { viewModel.openPaymentHubDialog() }
          )
        }

        // 6. Metrics Grid
        item {
          Spacer(modifier = Modifier.height(10.dp))
          MetricsGrid(
            engineStatus = uiState.engineStatus,
            modifier = Modifier.fillMaxWidth()
          )
        }

        // 7. Global Innovation Hub (Bet23 Behavioral Lock, Pre-Emptive Shield & Ecosystem Roadmap)
        item {
          Spacer(modifier = Modifier.height(10.dp))
          ShaheenGlobalInnovationHub(
            engineStatus = uiState.engineStatus,
            preEmptiveActive = uiState.config.preEmptiveShieldActive,
            bet23LockActive = uiState.config.bet23BehavioralLock,
            onTriggerEmergencyKillSwitch = { viewModel.triggerEmergencyKillSwitch() },
            onOpenPortalWeb = { viewModel.openWebPortalDialog() },
            modifier = Modifier.fillMaxWidth()
          )
        }

        // 8. Live Telemetry Console
        item {
          Spacer(modifier = Modifier.height(12.dp))
          TelemetryConsole(
            logs = uiState.logs,
            isRunning = uiState.engineStatus.isRunning,
            onClearLogs = { viewModel.clearLogs() }
          )
        }

        // 9. Legal Disclaimer Footer Link & Attribution
        item {
          Spacer(modifier = Modifier.height(10.dp))
          LegalFooterBar(
            hasAccepted = uiState.config.hasAcceptedDisclaimer,
            onOpenDisclaimer = { viewModel.openDisclaimerDialog() }
          )
        }
      }

      // Auto-save Floating Toast
      AnimatedVisibility(
        visible = uiState.autoSaveToastVisible,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 },
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 16.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = ShaheenSurfaceCard,
          border = androidx.compose.foundation.BorderStroke(1.dp, FalconBlue),
          shadowElevation = 8.dp
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = ActiveEmerald,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "تم الحفظ التلقائي في الخزينة المحلية المشفرة",
              style = MaterialTheme.typography.labelSmall,
              color = TextWhite
            )
          }
        }
      }
    }
  }

  // Legal Disclaimer Dialog
  if (uiState.showDisclaimerDialog) {
    LegalDisclaimerDialog(
      isAccepted = uiState.config.hasAcceptedDisclaimer,
      onAccept = {
        viewModel.acceptDisclaimer()
      },
      onDismiss = { viewModel.dismissDisclaimerDialog() }
    )
  }

  // Payment & Multi-User Hub Dialog
  if (uiState.showPaymentHubDialog) {
    PaymentHubDialog(
      extraUsersCount = uiState.config.additionalUsersCount,
      onExtraUsersChange = { viewModel.updateAdditionalUsersCount(it) },
      onDismiss = { viewModel.dismissPaymentHubDialog() }
    )
  }

  // Audit Report Dialog
  if (uiState.showAuditReportDialog) {
    AuditReportDialog(
      config = uiState.config,
      engineStatus = uiState.engineStatus,
      onDismiss = { viewModel.dismissAuditReportDialog() }
    )
  }

  // Access Denied Dialog
  if (uiState.showAccessDeniedDialog) {
    AccessDeniedDialog(
      currentUsername = uiState.config.username,
      reason = uiState.accessDeniedReason,
      onDismiss = { viewModel.dismissAccessDeniedDialog() },
      onQuickFixToAyman = {
        viewModel.updateUsername("ayman")
      }
    )
  }

  // Web Portal Blueprint Dialog
  if (uiState.showWebPortalDialog) {
    WebPortalBlueprintDialog(
      onDismiss = { viewModel.dismissWebPortalDialog() }
    )
  }

  // Settings Dialog
  if (uiState.showSettingsDialog) {
    SettingsDialog(
      config = uiState.config,
      onUsernameChange = { viewModel.updateUsername(it) },
      onLicenseKeyChange = { viewModel.updateLicenseKey(it) },
      onApiKeyChange = { viewModel.updateApiKey(it) },
      onDismiss = { viewModel.dismissSettingsDialog() },
      autoSaveActive = uiState.autoSaveToastVisible
    )
  }
}

@Composable
private fun TopTacticalBar(
  username: String,
  isAyman: Boolean,
  isRunning: Boolean,
  onOpenSettings: () -> Unit,
  onOpenAudit: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Official Falcon Logo & Title
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(ShaheenSurfaceElevated)
          .border(1.5.dp, FalconCyan.copy(alpha = 0.8f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.shaheen_logo),
          contentDescription = "SHAHEEN APEX AI Falcon",
          modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(10.dp)),
          contentScale = ContentScale.Crop
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "SHAHEEN",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              letterSpacing = 2.sp
            ),
            color = TextWhite
          )
          Spacer(modifier = Modifier.width(6.dp))
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = FalconBlue.copy(alpha = 0.25f),
            border = androidx.compose.foundation.BorderStroke(1.dp, FalconCyan)
          ) {
            Text(
              text = "APEX AI",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              ),
              color = FalconCyan,
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
          }
        }

        Text(
          text = "Autonomous Intelligence • Sovereign Invention",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
          color = TextMuted
        )
      }
    }

    // Header Actions
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
      IconButton(
        onClick = onOpenAudit,
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(ShaheenSurfaceCard)
          .border(1.dp, ShaheenMetallicBorder, CircleShape)
          .testTag("open_audit_top_button")
      ) {
        Icon(
          imageVector = Icons.Default.Assessment,
          contentDescription = "Audit Report",
          tint = FalconCyan,
          modifier = Modifier.size(20.dp)
        )
      }

      IconButton(
        onClick = onOpenSettings,
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(ShaheenSurfaceCard)
          .border(1.dp, ShaheenMetallicBorder, CircleShape)
          .testTag("settings_button")
      ) {
        Icon(
          imageVector = Icons.Default.Settings,
          contentDescription = "Settings",
          tint = FalconBlue,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@Composable
private fun TestingCountdownBanner(
  remainingSeconds: Long,
  onOpenAudit: () -> Unit
) {
  val formatted = formatCountdownShort(remainingSeconds)

  Surface(
    shape = RoundedCornerShape(12.dp),
    color = ShaheenSurfaceDark,
    border = androidx.compose.foundation.BorderStroke(1.dp, FalconBlue.copy(alpha = 0.4f)),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp)
      .clickable(onClick = onOpenAudit)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.HourglassTop,
          contentDescription = null,
          tint = ConsoleYellow,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "انتهاء مرحلة الاختبار التجريبي:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = TextWhite
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = formatted,
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black
          ),
          color = FalconCyan
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "تقرير الفحص ↗",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = FalconBlue
        )
      }
    }
  }
}

@Composable
private fun IdentityStatusBanner(
  username: String,
  isAyman: Boolean,
  extraSeats: Int,
  hasAcceptedDisclaimer: Boolean,
  onConfigure: () -> Unit,
  onOpenDisclaimer: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = ShaheenSurfaceCard,
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isAyman) ShaheenMetallicBorder else InactiveCrimson.copy(alpha = 0.5f)
    ),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.clickable(onClick = onConfigure),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = if (isAyman) Icons.Default.Fingerprint else Icons.Default.Lock,
          contentDescription = null,
          tint = if (isAyman) ActiveEmerald else InactiveCrimson,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = "المشغل المرخص: ${if (username.isBlank()) "[غير محدد]" else username}",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TextWhite
          )
          Text(
            text = if (isAyman) "مرخص للمطور أيمن العرايشي • (1+$extraSeats يوزر)" else "مقفل أمنياً للمشغل [ayman]",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (isAyman) ActiveEmerald else InactiveCrimson
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(6.dp),
        color = (if (hasAcceptedDisclaimer) ActiveEmerald else FalconBlue).copy(alpha = 0.15f),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          (if (hasAcceptedDisclaimer) ActiveEmerald else FalconBlue).copy(alpha = 0.4f)
        ),
        modifier = Modifier.clickable(onClick = onOpenDisclaimer)
      ) {
        Text(
          text = if (hasAcceptedDisclaimer) "إخلاء المسؤولية ✔" else "مطلوب الإقرار ⚠",
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp
          ),
          color = if (hasAcceptedDisclaimer) ActiveEmerald else FalconCyan,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
      }
    }
  }
}

@Composable
private fun CommercialLicensingBanner(
  extraUsers: Int,
  onOpenPaymentHub: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = ShaheenSurfaceCard,
    border = androidx.compose.foundation.BorderStroke(1.dp, FalconBlue.copy(alpha = 0.5f)),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .clickable(onClick = onOpenPaymentHub)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(FalconBlue.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CurrencyExchange,
            contentDescription = null,
            tint = FalconCyan,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "الترخيص والاشتراكات (100 USDT)",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TextWhite
          )
          Text(
            text = "دفع كريبتو: باينانس / MEXC • يوزر إضافي: 50 USDT",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = FalconCyan
          )
        }
      }

      Button(
        onClick = onOpenPaymentHub,
        colors = ButtonDefaults.buttonColors(containerColor = FalconBlue),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.height(32.dp)
      ) {
        Text(text = "ترقية / دفع", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
      }
    }
  }
}

@Composable
private fun LegalFooterBar(
  hasAccepted: Boolean,
  onOpenDisclaimer: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.clickable(onClick = onOpenDisclaimer)
    ) {
      Icon(
        imageVector = Icons.Default.Gavel,
        contentDescription = null,
        tint = FalconCyan,
        modifier = Modifier.size(14.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "عرض بنود إخلاء المسؤولية وإقرار الاستخدام القانوني الكامل",
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        ),
        color = FalconCyan
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "SHAHEEN APEX AI © • المطور: أيمن العرايشي (Ayman Al-Araishi)",
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        color = TextDim
      ),
      textAlign = TextAlign.Center
    )
  }
}

private fun formatCountdownShort(seconds: Long): String {
  val days = seconds / 86400
  val hours = (seconds % 86400) / 3600
  val minutes = (seconds % 3600) / 60
  val secs = seconds % 60
  return String.format(Locale.US, "%dd %02dh:%02dm:%02ds", days, hours, minutes, secs)
}
