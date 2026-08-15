package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.ActiveEmeraldDark
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
import com.example.ui.theme.InactiveCrimson
import com.example.ui.theme.InactiveCrimsonDark
import com.example.ui.theme.ShaheenBackground
import com.example.ui.theme.ShaheenMetallicBorder
import com.example.ui.theme.ShaheenSurfaceCard
import com.example.ui.theme.ShaheenSurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun VpnToggleButton(
  isRunning: Boolean,
  onToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "vpnPulse")

  // Radar wave animations when active
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.35f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "pulseScale"
  )

  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 0.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "pulseAlpha"
  )

  val glowRotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(12000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "glowRotation"
  )

  // Animated colors
  val primaryGlowColor by animateColorAsState(
    targetValue = if (isRunning) ActiveEmerald else InactiveCrimson,
    animationSpec = tween(500),
    label = "glowColor"
  )

  val darkGlowColor by animateColorAsState(
    targetValue = if (isRunning) ActiveEmeraldDark else InactiveCrimsonDark,
    animationSpec = tween(500),
    label = "darkGlowColor"
  )

  val buttonScale by animateFloatAsState(
    targetValue = if (isRunning) 1.02f else 1.0f,
    animationSpec = spring(dampingRatio = 0.6f),
    label = "scale"
  )

  val interactionSource = remember { MutableInteractionSource() }

  Column(
    modifier = modifier.padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Outer Container with Radar Waves
    Box(
      modifier = Modifier
        .size(230.dp),
      contentAlignment = Alignment.Center
    ) {
      // Pulsing Radar Rings when running
      if (isRunning) {
        Canvas(
          modifier = Modifier
            .size(230.dp)
            .scale(pulseScale)
        ) {
          drawCircle(
            color = ActiveEmerald.copy(alpha = pulseAlpha * 0.5f),
            radius = size.minDimension / 2f,
            style = Stroke(width = 4.dp.toPx())
          )
          drawCircle(
            color = FalconCyan.copy(alpha = pulseAlpha * 0.3f),
            radius = size.minDimension / 2.6f,
            style = Stroke(width = 2.dp.toPx())
          )
        }
      }

      // Outer Static Cyber Rim
      Canvas(
        modifier = Modifier.size(200.dp)
      ) {
        val strokeWidth = 3.dp.toPx()
        // Dark base ring
        drawCircle(
          color = ShaheenMetallicBorder,
          radius = (size.minDimension - strokeWidth) / 2f,
          style = Stroke(width = strokeWidth)
        )
        // Active glowing arc indicator
        val sweep = if (isRunning) 360f else 90f
        val start = if (isRunning) glowRotation else 225f
        drawArc(
          brush = Brush.sweepGradient(
            listOf(primaryGlowColor.copy(alpha = 0.2f), primaryGlowColor, primaryGlowColor.copy(alpha = 0.2f))
          ),
          startAngle = start,
          sweepAngle = sweep,
          useCenter = false,
          style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
      }

      // The Interactive VPN Button
      Box(
        modifier = Modifier
          .size(164.dp)
          .scale(buttonScale)
          .shadow(
            elevation = if (isRunning) 24.dp else 12.dp,
            shape = CircleShape,
            ambientColor = primaryGlowColor.copy(alpha = 0.5f),
            spotColor = primaryGlowColor
          )
          .clip(CircleShape)
          .background(
            brush = Brush.radialGradient(
              colors = listOf(
                ShaheenSurfaceDark,
                ShaheenBackground,
                darkGlowColor.copy(alpha = 0.35f)
              ),
              center = Offset.Unspecified
            )
          )
          .border(
            width = 2.5.dp,
            brush = Brush.verticalGradient(
              colors = listOf(
                primaryGlowColor,
                ShaheenMetallicBorder
              )
            ),
            shape = CircleShape
          )
          .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onToggle
          )
          .testTag("vpn_toggle_button"),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.padding(12.dp)
        ) {
          // Icon with subtle glow
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(primaryGlowColor.copy(alpha = 0.15f))
              .border(1.dp, primaryGlowColor.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isRunning) Icons.Default.Shield else Icons.Default.PowerSettingsNew,
              contentDescription = if (isRunning) "Active VPN" else "Inactive VPN",
              tint = primaryGlowColor,
              modifier = Modifier.size(26.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Primary Status Text - strictly following prompt requirements
          Text(
            text = if (isRunning) "ON" else "OFF",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              letterSpacing = 1.5.sp
            ),
            color = primaryGlowColor,
            textAlign = TextAlign.Center
          )

          // Subtitle instruction
          Text(
            text = if (isRunning) "(Running Securely)" else "(Click to Start)",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              fontWeight = FontWeight.SemiBold
            ),
            color = TextWhite.copy(alpha = 0.85f),
            textAlign = TextAlign.Center,
            maxLines = 1
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // State Badge
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = ShaheenSurfaceCard,
      border = androidx.compose.foundation.BorderStroke(1.dp, primaryGlowColor.copy(alpha = 0.3f)),
      modifier = Modifier.padding(top = 4.dp)
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(primaryGlowColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (isRunning) "ENCRYPTED VPN TUNNEL ACTIVE" else "STANDBY • TUNNEL DISCONNECTED",
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
          ),
          color = if (isRunning) ActiveEmerald else TextMuted
        )
      }
    }
  }
}
