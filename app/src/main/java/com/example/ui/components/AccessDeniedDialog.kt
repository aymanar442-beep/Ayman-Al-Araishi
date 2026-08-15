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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppBad
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.InactiveCrimson
import com.example.ui.theme.ShaheenBackground
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.ShaheenSurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun AccessDeniedDialog(
  currentUsername: String,
  reason: String,
  onDismiss: () -> Unit,
  onQuickFixToAyman: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .border(1.5.dp, InactiveCrimson, RoundedCornerShape(20.dp))
        .testTag("access_denied_dialog"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceDark)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                InactiveCrimson.copy(alpha = 0.15f),
                ShaheenSurfaceDark,
                ShaheenBackground
              )
            )
          )
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Red Glowing Alert Icon
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(InactiveCrimson.copy(alpha = 0.2f))
            .border(2.dp, InactiveCrimson, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.GppBad,
            contentDescription = "Access Denied",
            tint = InactiveCrimson,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "ACCESS DENIED",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            letterSpacing = 1.5.sp
          ),
          color = InactiveCrimson,
          textAlign = TextAlign.Center
        )

        Text(
          text = "SECURITY IDENTITY LOCK ACTIVATED",
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
          ),
          color = TextMuted,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Diagnostic Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ShaheenSurfaceCard)
            .border(1.dp, ShaheenMetallicBorder, RoundedCornerShape(12.dp))
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LockPerson,
                contentDescription = null,
                tint = InactiveCrimson,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Detected Operator: ",
                style = MaterialTheme.typography.labelMedium,
                color = TextMuted
              )
              Text(
                text = if (currentUsername.isBlank()) "[EMPTY]" else currentUsername,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = InactiveCrimson
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Key,
                contentDescription = null,
                tint = FalconBlue,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Authorized Lock: ",
                style = MaterialTheme.typography.labelMedium,
                color = TextMuted
              )
              Text(
                text = "ayman",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = FalconBlue
                )
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "The SHAHEEN Trading Engine is cryptographically locked to operator 'ayman'. Execution has been halted.",
              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
              color = TextWhite.copy(alpha = 0.8f)
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Actions
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              onQuickFixToAyman()
              onDismiss()
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("fix_identity_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = FalconBlue,
              contentColor = TextWhite
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = "Authorize as 'ayman'",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }

          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("dismiss_dialog_button"),
            border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorder),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = "Dismiss & Configure",
              style = MaterialTheme.typography.labelLarge,
              color = TextMuted
            )
          }
        }
      }
    }
  }
}
