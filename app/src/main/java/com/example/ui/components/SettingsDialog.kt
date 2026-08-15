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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.ShaheenConfig
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
import com.example.ui.theme.InactiveCrimson
import com.example.ui.theme.ShaheenBackground
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenMetallicBorderLight
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.ShaheenSurfaceDark
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun SettingsDialog(
  config: ShaheenConfig,
  onUsernameChange: (String) -> Unit,
  onLicenseKeyChange: (String) -> Unit,
  onApiKeyChange: (String) -> Unit,
  onDismiss: () -> Unit,
  autoSaveActive: Boolean
) {
  var showApiKey by remember { mutableStateOf(false) }
  var showLicenseKey by remember { mutableStateOf(false) }

  val isAyman = config.username.trim().equals("ayman", ignoreCase = true)

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .border(1.dp, ShaheenMetallicBorderLight, RoundedCornerShape(20.dp))
        .testTag("settings_dialog"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceDark)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(FalconBlue.copy(alpha = 0.15f))
                .border(1.dp, FalconBlue, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = FalconBlue,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "SHAHEEN CONFIG",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
              )
              Text(
                text = "Auto-Saved Local Vault",
                style = MaterialTheme.typography.labelSmall,
                color = FalconCyan
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("close_settings_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Auto-save notification pill
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = ShaheenSurfaceCard,
          border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
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
              text = if (autoSaveActive) "Saving modifications to SharedPreferences..." else "Encrypted local state synced (Auto-save)",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = if (autoSaveActive) FalconCyan else TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Username Field
        Text(
          text = "OPERATOR USERNAME",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = config.username,
          onValueChange = onUsernameChange,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("username_input"),
          placeholder = { Text("Enter username (e.g. ayman)", color = TextDim) },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = if (isAyman) ActiveEmerald else InactiveCrimson
            )
          },
          trailingIcon = {
            Text(
              text = if (isAyman) "VALID" else "LOCKED",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              ),
              color = if (isAyman) ActiveEmerald else InactiveCrimson,
              modifier = Modifier.padding(end = 12.dp)
            )
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isAyman) FalconBlue else InactiveCrimson,
            unfocusedBorderColor = ShaheenMetallicBorder,
            focusedContainerColor = ShaheenSurfaceCard,
            unfocusedContainerColor = ShaheenSurfaceCard,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )
        Text(
          text = if (isAyman) "✔ Licensed to authorized operator 'ayman'" else "⚠ Warning: Engine is locked to username 'ayman'",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
          color = if (isAyman) ActiveEmerald else InactiveCrimson,
          modifier = Modifier.padding(top = 4.dp, start = 4.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2. License Key Field
        Text(
          text = "CORE LICENSE KEY",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = config.licenseKey,
          onValueChange = onLicenseKeyChange,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("license_key_input"),
          placeholder = { Text("e.g. SH-9924-SEC-ALPHA-88X", color = TextDim) },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.VpnKey,
              contentDescription = null,
              tint = FalconBlue
            )
          },
          trailingIcon = {
            IconButton(onClick = { showLicenseKey = !showLicenseKey }) {
              Icon(
                imageVector = if (showLicenseKey) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "Toggle license visibility",
                tint = TextMuted
              )
            }
          },
          visualTransformation = if (showLicenseKey) VisualTransformation.None else PasswordVisualTransformation(),
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FalconBlue,
            unfocusedBorderColor = ShaheenMetallicBorder,
            focusedContainerColor = ShaheenSurfaceCard,
            unfocusedContainerColor = ShaheenSurfaceCard,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 3. API Key Field
        Text(
          text = "TRADING API KEY",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = config.apiKey,
          onValueChange = onApiKeyChange,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("api_key_input"),
          placeholder = { Text("e.g. sh_live_k82f990141be297d09873a", color = TextDim) },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = FalconCyan
            )
          },
          trailingIcon = {
            IconButton(onClick = { showApiKey = !showApiKey }) {
              Icon(
                imageVector = if (showApiKey) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "Toggle API key visibility",
                tint = TextMuted
              )
            }
          },
          visualTransformation = if (showApiKey) VisualTransformation.None else PasswordVisualTransformation(),
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FalconBlue,
            unfocusedBorderColor = ShaheenMetallicBorder,
            focusedContainerColor = ShaheenSurfaceCard,
            unfocusedContainerColor = ShaheenSurfaceCard,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Helper quick reset/fill
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(
            onClick = {
              onUsernameChange("ayman")
              onLicenseKeyChange("SH-9924-SEC-ALPHA-88X")
              onApiKeyChange("sh_live_k82f990141be297d09873a")
            },
            modifier = Modifier.testTag("reset_defaults_button")
          ) {
            Text("Reset to 'ayman'", color = FalconBlue, fontSize = 12.sp)
          }

          TextButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("done_settings_button")
          ) {
            Text("Done", color = TextWhite, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
