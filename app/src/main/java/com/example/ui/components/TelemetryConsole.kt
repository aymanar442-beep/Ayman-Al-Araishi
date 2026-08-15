package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LogEntry
import com.example.model.LogLevel
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.ConsoleCyan
import com.example.ui.theme.ConsoleGreen
import com.example.ui.theme.ConsolePurple
import com.example.ui.theme.ConsoleYellow
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
import com.example.ui.theme.InactiveCrimson
import com.example.ui.theme.ShaheenBackground
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.ShaheenSurfaceDark
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun TelemetryConsole(
  logs: List<LogEntry>,
  isRunning: Boolean,
  onClearLogs: () -> Unit,
  modifier: Modifier = Modifier
) {
  val listState = rememberLazyListState()
  val context = LocalContext.current

  // Auto-scroll when new logs arrive (instant, non-blocking to prevent UI lag)
  LaunchedEffect(logs.size) {
    if (logs.isNotEmpty()) {
      listState.scrollToItem(logs.size - 1)
    }
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(ShaheenSurfaceDark)
      .border(1.dp, ShaheenMetallicBorder, RoundedCornerShape(16.dp))
      .testTag("telemetry_console")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)
    ) {
      // Console Header
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(ShaheenSurfaceCard)
          .border(
            width = 1.dp,
            color = ShaheenMetallicBorder,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
          )
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Terminal indicator dot
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(if (isRunning) ActiveEmerald else InactiveCrimson)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            imageVector = Icons.Default.Terminal,
            contentDescription = null,
            tint = FalconBlue,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "LIVE TELEMETRY CONSOLE",
            style = MaterialTheme.typography.labelSmall.copy(
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            ),
            color = TextWhite
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          // Logs Count
          Text(
            text = "${logs.size} lines",
            style = MaterialTheme.typography.labelSmall.copy(
              fontFamily = FontFamily.Monospace,
              fontSize = 10.sp
            ),
            color = TextMuted,
            modifier = Modifier.padding(end = 4.dp)
          )

          // Copy logs action
          IconButton(
            onClick = {
              val allLogs = logs.joinToString("\n") { "[${it.timestamp}] [${it.tag}] ${it.message}" }
              val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              val clip = ClipData.newPlainText("Shaheen Telemetry Logs", allLogs)
              clipboard.setPrimaryClip(clip)
              Toast.makeText(context, "Telemetry logs copied to clipboard", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.size(32.dp).testTag("copy_logs_button")
          ) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Copy logs",
              tint = TextMuted,
              modifier = Modifier.size(16.dp)
            )
          }

          // Clear logs action
          IconButton(
            onClick = onClearLogs,
            modifier = Modifier.size(32.dp).testTag("clear_logs_button")
          ) {
            Icon(
              imageVector = Icons.Default.DeleteSweep,
              contentDescription = "Clear logs",
              tint = TextMuted,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      // Console Body
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(ShaheenBackground)
          .padding(8.dp)
      ) {
        if (logs.isEmpty()) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Console idle. Click VPN switch to start monitoring loop.",
              style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
              color = TextDim
            )
          }
        } else {
          LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            items(logs, key = { it.id }) { log ->
              LogLine(log = log)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun LogLine(log: LogEntry) {
  val (tagColor, textColor) = when (log.level) {
    LogLevel.SUCCESS -> ActiveEmerald to ConsoleGreen
    LogLevel.ERROR -> InactiveCrimson to InactiveCrimson
    LogLevel.WARNING -> ConsoleYellow to ConsoleYellow
    LogLevel.SYSTEM -> ConsolePurple to TextWhite
    LogLevel.INFO -> FalconCyan to TextWhite
  }

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    Text(
      text = log.timestamp,
      style = MaterialTheme.typography.labelSmall.copy(
        fontFamily = FontFamily.Monospace,
        fontSize = 10.sp
      ),
      color = TextDim,
      modifier = Modifier.padding(end = 6.dp)
    )

    Text(
      text = "[${log.tag}]",
      style = MaterialTheme.typography.labelSmall.copy(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
      ),
      color = tagColor,
      modifier = Modifier.padding(end = 6.dp)
    )

    Text(
      text = log.message,
      style = MaterialTheme.typography.labelSmall.copy(
        fontFamily = FontFamily.Monospace,
        fontSize = 10.sp,
        lineHeight = 14.sp
      ),
      color = textColor
    )
  }
}
