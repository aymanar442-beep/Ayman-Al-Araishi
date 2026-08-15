package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiTetheringOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EngineStatus
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

@Composable
fun ShaheenGlobalInnovationHub(
  engineStatus: EngineStatus,
  preEmptiveActive: Boolean,
  bet23LockActive: Boolean,
  onTriggerEmergencyKillSwitch: () -> Unit,
  onOpenPortalWeb: () -> Unit,
  modifier: Modifier = Modifier
) {
  var expandedSection by remember { mutableStateOf<String?>("ecosystem") }

  Column(
    modifier = modifier
      .padding(horizontal = 14.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(ShaheenSurfaceElevated)
      .border(BorderStroke(1.dp, Brush.horizontalGradient(listOf(FalconCyan, FalconBlue, ShaheenMetallicBorderLight))), RoundedCornerShape(16.dp))
      .padding(14.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(FalconBlue.copy(alpha = 0.2f))
            .border(BorderStroke(1.dp, FalconCyan), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = FalconCyan,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "منظومة شاهين للأمان السيادي والابتكار",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = TextWhite
          )
          Text(
            text = "SHAHEEN GLOBAL SOVEREIGN ECOSYSTEM & BET23 CORE",
            style = MaterialTheme.typography.labelSmall,
            color = FalconCyan,
            fontFamily = FontFamily.Monospace,
            fontSize = 9.sp
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(6.dp),
        color = ActiveEmerald.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, ActiveEmerald.copy(alpha = 0.5f))
      ) {
        Text(
          text = "PATENT PENDING",
          style = MaterialTheme.typography.labelSmall,
          color = ActiveEmerald,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          fontSize = 9.sp,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 1. Bet23 & Pre-Emptive Exchange Shield Live Status
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // Box 1: Pre-Emptive Laddering Protocol
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceDark),
        border = BorderStroke(1.dp, if (preEmptiveActive) ActiveEmerald.copy(alpha = 0.6f) else ShaheenMetallicBorder),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.WifiTetheringOff,
              contentDescription = null,
              tint = if (preEmptiveActive) ActiveEmerald else TextMuted,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "الدرع الاستباقي للزمن",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = TextWhite,
              fontSize = 11.sp
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "حصانة عند انقطاع الإنترنت عبر أوامر OCO مسبقة على سيرفر البورصة",
            style = MaterialTheme.typography.labelSmall,
            color = TextDim,
            fontSize = 10.sp,
            lineHeight = 13.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(ActiveEmerald)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
              text = "${engineStatus.preEmptiveOrdersArmed} أوامر OCO نشطة على السيرفر",
              style = MaterialTheme.typography.labelSmall,
              fontFamily = FontFamily.Monospace,
              color = FalconCyan,
              fontSize = 9.sp
            )
          }
        }
      }

      // Box 2: Bet23 Psycho-Temporal Lock
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceDark),
        border = BorderStroke(1.dp, if (bet23LockActive) FalconCyan.copy(alpha = 0.6f) else ShaheenMetallicBorder),
        modifier = Modifier.weight(1f)
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = if (bet23LockActive) FalconCyan else TextMuted,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "خوارزمية Bet23 السلوكية",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = TextWhite,
              fontSize = 11.sp
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "تجميد التداول آلياً ومنع التداول العاطفي والانتقامي عند اضطراب النبض",
            style = MaterialTheme.typography.labelSmall,
            color = TextDim,
            fontSize = 10.sp,
            lineHeight = 13.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(ActiveEmerald)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
              text = "استقرار نفسي: ${engineStatus.behavioralStability}%",
              style = MaterialTheme.typography.labelSmall,
              fontFamily = FontFamily.Monospace,
              color = ActiveEmerald,
              fontSize = 9.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Emergency Kill Switch Button
    Button(
      onClick = onTriggerEmergencyKillSwitch,
      shape = RoundedCornerShape(10.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = InactiveCrimson.copy(alpha = 0.2f),
        contentColor = InactiveCrimson
      ),
      border = BorderStroke(1.dp, InactiveCrimson.copy(alpha = 0.8f)),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("emergency_kill_switch_btn")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = InactiveCrimson,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "مفتاح التسييل الفوري للطوارئ (Emergency Liquidation Kill-Switch)",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Section 2: Shaheen Autonomous Ecosystem Products (Smart Tracker, Care, AI Cloud)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(ShaheenSurfaceDark)
        .border(BorderStroke(1.dp, ShaheenMetallicBorder), RoundedCornerShape(12.dp))
        .padding(10.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            expandedSection = if (expandedSection == "ecosystem") null else "ecosystem"
          },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.DeviceHub,
            contentDescription = null,
            tint = FalconCyan,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "منتجات منظومة شاهين الشاملة للأمان (Ecosystem)",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = TextWhite
          )
        }
        Icon(
          imageVector = if (expandedSection == "ecosystem") Icons.Default.ExpandLess else Icons.Default.ExpandMore,
          contentDescription = null,
          tint = TextMuted
        )
      }

      AnimatedVisibility(visible = expandedSection == "ecosystem") {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          EcosystemProductRow(
            title = "Shaheen Smart Tracker (نظام تتبع واستغاثة الأطفال والعائلة)",
            desc = "خوارزمية القراءة السلوكية الاستباقية، تتبع ذكي بدون إشارة، وزر استغاثة SOS فوري للأجهزة القابلة للارتداء والسيارات.",
            status = "خارطة الإطلاق Q4",
            color = 0xFF00E5FF,
            icon = Icons.Default.TrackChanges
          )

          Spacer(modifier = Modifier.height(8.dp))

          EcosystemProductRow(
            title = "Shaheen Shield Core (درع التداول والسيولة الذاتية)",
            desc = "حماية الأصول المالية، مكافحة الانزلاق، وتوليد الأرباح بالذكاء السيادي للمطور أيمن العرايشي.",
            status = "النسخة النشطة حالياً v2.4",
            color = 0xFF00E676,
            icon = Icons.Default.Shield
          )

          Spacer(modifier = Modifier.height(8.dp))

          EcosystemProductRow(
            title = "Shaheen Sovereign Cloud & Web Portal",
            desc = "منصة الويب العالمية لعرض التراخيص، بيع المنتجات بالكريبتو، واستخراج بطاقات الدفع العالمية.",
            status = "جاهز للربط المجاني",
            color = 0xFFFFD600,
            icon = Icons.Default.Language
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = onOpenPortalWeb,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = FalconBlue,
              contentColor = TextWhite
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("open_portal_web_btn")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Language, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "عرض بوابة المنصة العالمية وخطة التوسع (Global Web Portal)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun EcosystemProductRow(
  title: String,
  desc: String,
  status: String,
  color: Long,
  icon: ImageVector
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(ShaheenSurfaceCard)
      .border(BorderStroke(1.dp, Color(color).copy(alpha = 0.3f)), RoundedCornerShape(8.dp))
      .padding(10.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(32.dp)
        .clip(CircleShape)
        .background(Color(color).copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color(color),
        modifier = Modifier.size(18.dp)
      )
    }

    Spacer(modifier = Modifier.width(10.dp))

    Column(modifier = Modifier.weight(1f)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = TextWhite,
          modifier = Modifier.weight(1f)
        )
        Surface(
          shape = RoundedCornerShape(4.dp),
          color = Color(color).copy(alpha = 0.2f)
        ) {
          Text(
            text = status,
            style = MaterialTheme.typography.labelSmall,
            color = Color(color),
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = desc,
        style = MaterialTheme.typography.labelSmall,
        color = TextDim,
        fontSize = 10.sp,
        lineHeight = 13.sp
      )
    }
  }
}
