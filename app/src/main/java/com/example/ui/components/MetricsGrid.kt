package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EngineStatus
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun MetricsGrid(
  engineStatus: EngineStatus,
  modifier: Modifier = Modifier
) {
  val uptimeText = formatUptime(engineStatus.uptimeSeconds)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      MetricCard(
        title = "UPTIME",
        value = if (engineStatus.isRunning) uptimeText else "00:00:00",
        subValue = if (engineStatus.isRunning) "3000ms loop active" else "Engine standby",
        icon = Icons.Default.AvTimer,
        accentColor = if (engineStatus.isRunning) ActiveEmerald else TextMuted,
        modifier = Modifier.weight(1f).testTag("metric_uptime")
      )

      MetricCard(
        title = "MONITOR CYCLES",
        value = "${engineStatus.totalCycles}",
        subValue = if (engineStatus.isRunning) "Non-blocking async" else "0 cycles",
        icon = Icons.Default.Loop,
        accentColor = FalconBlue,
        modifier = Modifier.weight(1f).testTag("metric_cycles")
      )
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      MetricCard(
        title = "CLUSTER LATENCY",
        value = if (engineStatus.isRunning) "${engineStatus.latencyMs} ms" else "-- ms",
        subValue = if (engineStatus.isRunning) "Ultra-low latency" else "Disconnected",
        icon = Icons.Default.NetworkCheck,
        accentColor = FalconCyan,
        modifier = Modifier.weight(1f).testTag("metric_latency")
      )

      MetricCard(
        title = "PAIR / INDEX",
        value = if (engineStatus.isRunning) engineStatus.currentPair else "BTC/USDT",
        subValue = if (engineStatus.isRunning) "$${String.format(Locale.US, "%,.1f", engineStatus.priceIndex)}" else "Standby",
        icon = Icons.Default.CurrencyBitcoin,
        accentColor = ActiveEmerald,
        modifier = Modifier.weight(1f).testTag("metric_pair")
      )
    }
  }
}

@Composable
private fun MetricCard(
  title: String,
  value: String,
  subValue: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(ShaheenSurfaceCard)
      .border(1.dp, ShaheenMetallicBorder, RoundedCornerShape(14.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp,
            fontSize = 10.sp
          ),
          color = TextMuted
        )

        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(accentColor.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium.copy(
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        ),
        color = TextWhite
      )

      Text(
        text = subValue,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = TextDim,
        maxLines = 1
      )
    }
  }
}

private fun formatUptime(seconds: Long): String {
  val hours = seconds / 3600
  val minutes = (seconds % 3600) / 60
  val secs = seconds % 60
  return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, secs)
}
