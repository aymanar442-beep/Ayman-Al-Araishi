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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.example.ui.theme.WarningAmber

@Composable
fun LegalDisclaimerDialog(
  isAccepted: Boolean,
  onAccept: () -> Unit,
  onDismiss: () -> Unit
) {
  var agreeChecked by remember { mutableStateOf(isAccepted) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp)
        .border(1.5.dp, FalconBlue.copy(alpha = 0.8f), RoundedCornerShape(20.dp))
        .testTag("legal_disclaimer_dialog"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = ShaheenSurfaceDark)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            brush = Brush.verticalGradient(
              listOf(
                ShaheenSurfaceDark,
                ShaheenBackground
              )
            )
          )
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header with Falcon Legal Seal
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(FalconBlue.copy(alpha = 0.15f))
                .border(1.dp, FalconCyan, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Gavel,
                contentDescription = null,
                tint = FalconCyan,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "إقرار الاستخدام وإخلاء المسؤولية",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                ),
                color = TextWhite
              )
              Text(
                text = "SHAHEEN APEX AI • Legal Compliance (Pro)",
                style = MaterialTheme.typography.labelSmall,
                color = FalconCyan
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp).testTag("close_disclaimer_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Critical Warning Banner
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = InactiveCrimson.copy(alpha = 0.12f),
          border = androidx.compose.foundation.BorderStroke(1.dp, InactiveCrimson.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = InactiveCrimson,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "تنبيه قانوني هام ومُلزم:",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = InactiveCrimson
              )
              Text(
                text = "إن الضغط على زر \"تشغيل\" أو البدء في استخدام البرمجية يُعد إقراراً قانونياً ونهائياً منك بالاطلاع والموافقة التامة على البنود والاشتراطات التالية دون قيد أو شرط.",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 18.sp),
                color = TextWhite
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5 Legal Clauses Formatted
        DisclaimerClauseItem(
          number = "١",
          title = "طبيعة الخدمة (Service Classification)",
          content = "يُصنف Shaheen Shield Core كأداة برمجية ذكية (Automated Tool) تهدف للمساعدة في الحماية من تقلبات السوق. هو نظام \"تجريبي متطور\" (Beta System) يعتمد على خوارزميات معقدة، ولكنه لا يضمن الربح ولا يمنع الخسارة بشكل قطعي في جميع ظروف السوق."
        )

        DisclaimerClauseItem(
          number = "٢",
          title = "مخاطر التداول وتقلبات الأسواق (Market Risks)",
          content = "يقر المستخدم بأن أسواق العملات الرقمية شديدة التقلب. إن مخاطر الانزلاق السعري (Slippage)، الفجوات السعرية المفاجئة، ضعف السيولة، أو تأخر استجابة سيرفرات المنصات (Exchange Latency) هي مخاطر جوهرية قائمة وموجودة في السوق، ولا تدخل بأي شكل ضمن نطاق سيطرة البرمجية أو مطورها."
        )

        DisclaimerClauseItem(
          number = "٣",
          title = "الحدود التقنية والاتصال (Technical Limitations)",
          content = "شأنها شأن كبرى الأنظمة العالمية المتقدمة (مثل ChatGPT وجوجل)، فإن هذه البرمجية عرضة للأعطال التقنية غير المتوقعة، انقطاع الاتصال (API Failure)، أو الأخطاء الناتجة عن تحديثات خارجية لمنصات التداول. استخدامك لها يكون على مسؤوليتك التقنية والمالية الكاملة."
        )

        DisclaimerClauseItem(
          number = "٤",
          title = "إخلاء المسؤولية القانونية التامة (Legal Disclaimer)",
          content = "يقر المستخدم إقراراً نهائياً غير قابل للطعن بأن المطور (أيمن العرايشي - Ayman Al-Araishi) وكيانه البرمجي (SHAHEEN APEX AI) غير مسؤولين إطلاقاً عن أي خسائر مالية، أضرار مادية أو معنوية، أو قرارات تداول ناتجة عن استخدام هذه البرمجية. إدارة رأس المال وتحديد حجم المخاطرة هو قرارك الشخصي والمنفرد."
        )

        DisclaimerClauseItem(
          number = "٥",
          title = "الإقرار بالمعرفة والأهلية (User Acknowledgment)",
          content = "يؤكد المستخدم أنه يمتلك المعرفة الكافية بمبادئ التداول وإدارة المخاطر وكيفية عمل مفاتيح الربط البرمجي (API Keys)، وأنه اختار استخدام هذه الأداة بكامل إرادته وأهليته المعتبرة قانوناً لغرض المساعدة التقنية فقط."
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Checkbox Agreement
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = ShaheenSurfaceCard,
          border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = agreeChecked,
              onCheckedChange = { agreeChecked = it },
              colors = CheckboxDefaults.colors(
                checkedColor = ActiveEmerald,
                uncheckedColor = TextMuted,
                checkmarkColor = ShaheenBackground
              ),
              modifier = Modifier.testTag("agree_checkbox")
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "قرأت كافة البنود القانونية وأوافق عليها بصفتي المستخدم المفوض والمتحمل لكافة النتائج.",
              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),
              color = if (agreeChecked) TextWhite else TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.weight(1f).testTag("cancel_disclaimer_button"),
            border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorder),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(text = "إغلاق", color = TextMuted)
          }

          Button(
            onClick = {
              if (agreeChecked) {
                onAccept()
                onDismiss()
              }
            },
            enabled = agreeChecked,
            modifier = Modifier.weight(1.5f).testTag("confirm_disclaimer_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = FalconBlue,
              disabledContainerColor = ShaheenMetallicBorder,
              contentColor = TextWhite
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.VerifiedUser,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "إقرار وموافقة قانونية", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}

@Composable
private fun DisclaimerClauseItem(
  number: String,
  title: String,
  content: String
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(ShaheenSurfaceCard)
      .border(1.dp, ShaheenMetallicBorder, RoundedCornerShape(10.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(FalconBlue.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = number,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = FalconCyan
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = TextWhite
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 18.sp),
        color = TextMuted
      )
    }
  }
}
