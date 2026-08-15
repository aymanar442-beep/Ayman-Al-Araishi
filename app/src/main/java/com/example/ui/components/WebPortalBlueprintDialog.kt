package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.ConsoleYellow
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
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
fun WebPortalBlueprintDialog(
  onDismiss: () -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  var copiedToast by remember { mutableStateOf(false) }

  val portalUrl = "https://shaheen-apex.ai"
  val portalBlueprintSummary = """
    === SHAHEEN APEX AI • GLOBAL WEB PORTAL BLUEPRINT ===
    Domain: https://shaheen-apex.ai (Ready for Free Deploy on Vercel/Cloudflare)
    Lead Architect: Ayman Al-Araishi (أيمن العرايشي)
    
    1. ZERO-COST CLOUD HOSTING:
    - Frontend: Next.js / Tailwind CSS hosted on Vercel / GitHub Pages (100% Free Forever)
    - SSL Certificate: Free Cloudflare SSL & DDoS Shield
    
    2. DIRECT CRYPTO COMMERCE (NO BANK ACCOUNT REQUIRED):
    - BNB Smart Chain (BEP20) Native Web3 Gateway
    - Binance Pay Merchant Direct Link + MEXC Direct Deposit
    - Card-to-Crypto (Fiat On-Ramp) via MoonPay/Binance Connect
    
    3. GLOBAL ECOSYSTEM ROADMAP:
    - Shaheen Shield Core v2.4 (Active Commercial App: 100 USDT)
    - Shaheen Smart Tracker & Family Care (Pre-Order)
    - Bet23 Autonomous Psycho-Temporal Risk Shield
    
    4. INTERNATIONAL MERCHANT COMPLIANCE:
    - Official Terms of Service & 5-Clause Legal Disclaimer
    - Ready for Visa/Mastercard Global Onboarding
  """.trimIndent()

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .clip(RoundedCornerShape(18.dp))
        .border(
          BorderStroke(1.dp, Brush.horizontalGradient(listOf(FalconCyan, FalconBlue, ShaheenMetallicBorderLight))),
          RoundedCornerShape(18.dp)
        ),
      color = ShaheenSurfaceDark,
      shadowElevation = 24.dp
    ) {
      Column(
        modifier = Modifier
          .padding(18.dp)
          .verticalScroll(rememberScrollState())
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
                .size(40.dp)
                .clip(CircleShape)
                .background(FalconBlue.copy(alpha = 0.25f))
                .border(BorderStroke(1.dp, FalconCyan), CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Public,
                contentDescription = null,
                tint = FalconCyan,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "بوابة منصة شاهين العالمية (Web Portal)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
              )
              Text(
                text = "SHAHEEN APEX AI • GLOBAL SOVEREIGN PLATFORM",
                style = MaterialTheme.typography.labelSmall,
                color = FalconCyan,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp
              )
            }
          }

          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Web Domain & Hosting Banner
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceCard),
          border = BorderStroke(1.dp, FalconBlue.copy(alpha = 0.5f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = ActiveEmerald, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "رابط المنصة المقترح:", style = MaterialTheme.typography.labelSmall, color = TextMuted)
              }
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = ActiveEmerald.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "استضافة مجانية 100%",
                  style = MaterialTheme.typography.labelSmall,
                  color = ActiveEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "https://shaheen-apex.ai",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = FalconCyan,
              fontFamily = FontFamily.Monospace
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Strategy Breakdown
        SectionHeader(title = "1. خطة إطلاق المنصة مجاناً وبدون بطاقات بنكية", icon = Icons.Default.CreditCard)
        BlueprintItem(
          bullet = "أ",
          title = "استضافة سحابية مجانية مدى الحياة (Vercel / Cloudflare):",
          desc = "رفع الواجهة الرسمية لشاهين بدون أي تكلفة استضافة أو خوادم مدفوعة، مع حماية أمنية وتشفير SSL تلقائي."
        )
        BlueprintItem(
          bullet = "ب",
          title = "استلام الأموال كريبتو مباشرة لمحفظتيك على BSC:",
          desc = "العميل يشتري التطبيق (100 USDT) أو يشترك سنوياً (25 USDT) وتتحول الدفعة مباشرة لمحفظة باينانس أو MEXC بدون وسيط أو قيود بنكية."
        )
        BlueprintItem(
          bullet = "ج",
          title = "الدفع بالفيزا والماستركارد وجوجل باي:",
          desc = "ربط أداة Fiat On-Ramp (مثل MoonPay / Binance Pay Link) تتيح للمشتري الشراء ببطاقته وتصلك أنت فوراً كـ USDT على محفظة الـ BNB Smart Chain."
        )

        Spacer(modifier = Modifier.height(10.dp))

        SectionHeader(title = "2. حزمة منتجات الأمان في المنصة (Ecosystem)", icon = Icons.Default.Shield)
        BlueprintItem(
          bullet = "1",
          title = "Shaheen Shield Core v2.4 (الأمان المالي والتداول):",
          desc = "التطبيق التجاري الحالي بسعر 100 USDT لمرة واحدة + 25 USDT اشتراك سنوي + 50 USDT لكل مستخدم إضافي."
        )
        BlueprintItem(
          bullet = "2",
          title = "Shaheen Smart Tracker & Family Care (تتبع الأمان):",
          desc = "نظام الاستغاثة والتتبع الاستباقي للأطفال والأصول بنظام العمل بدون إشارة هاتفية."
        )
        BlueprintItem(
          bullet = "3",
          title = "خوارزمية Bet23 والدرع المناعي الاستباقي:",
          desc = "براءة اختراع حصرية مسجلة باسم المطور أيمن العرايشي لمنع الخسائر والانزلاق والتداول العاطفي."
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Copy Blueprint / Summary Button
        Button(
          onClick = {
            clipboardManager.setText(AnnotatedString(portalBlueprintSummary))
            copiedToast = true
          },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = FalconBlue),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("copy_portal_blueprint_btn")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (copiedToast) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
              contentDescription = null,
              tint = if (copiedToast) ActiveEmerald else TextWhite,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (copiedToast) "تم نسخ خطة المنصة ومواصفات الإطلاق بنجاح!" else "نسخ المخطط الشامل للمنصة والحسابات (Copy Blueprint)",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(vertical = 6.dp)
  ) {
    Icon(imageVector = icon, contentDescription = null, tint = FalconCyan, modifier = Modifier.size(18.dp))
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
  }
}

@Composable
private fun BlueprintItem(bullet: String, title: String, desc: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.Top
  ) {
    Surface(
      shape = CircleShape,
      color = FalconBlue.copy(alpha = 0.3f),
      border = BorderStroke(1.dp, FalconCyan.copy(alpha = 0.6f)),
      modifier = Modifier.size(22.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(text = bullet, style = MaterialTheme.typography.labelSmall, color = FalconCyan, fontWeight = FontWeight.Bold, fontSize = 10.sp)
      }
    }
    Spacer(modifier = Modifier.width(8.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 12.sp)
      Text(text = desc, style = MaterialTheme.typography.labelSmall, color = TextDim, fontSize = 11.sp, lineHeight = 14.sp)
    }
  }
}
