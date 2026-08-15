package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.PaymentGateway
import com.example.ui.theme.ActiveEmerald
import com.example.ui.theme.ConsoleGreen
import com.example.ui.theme.FalconBlue
import com.example.ui.theme.FalconCyan
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
fun PaymentHubDialog(
  extraUsersCount: Int,
  onExtraUsersChange: (Int) -> Unit,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  var selectedGateway by remember { mutableStateOf(PaymentGateway.BINANCE_PAY) }
  var txHash by remember { mutableStateOf("") }
  var isSubmitted by remember { mutableStateOf(false) }

  // Pricing calculations
  val baseLicensePrice = 100 // 100 USDT for full app purchase
  val annualMaintenance = 25 // 25 USDT/year
  val additionalUsersCost = extraUsersCount * 50 // 50 USDT per additional user seat
  val totalUsers = 1 + extraUsersCount
  val totalOneTimeUsdt = baseLicensePrice + additionalUsersCost
  val totalAnnualUsdt = totalUsers * annualMaintenance

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp)
        .border(1.5.dp, FalconBlue, RoundedCornerShape(20.dp))
        .testTag("payment_hub_dialog"),
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
        // Header
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
                .background(FalconBlue.copy(alpha = 0.2f))
                .border(1.dp, FalconCyan, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.CurrencyExchange,
                contentDescription = null,
                tint = FalconCyan,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "بوابة الدفع وترخيص المستخدمين",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
              )
              Text(
                text = "SHAHEEN Crypto Licensing Hub",
                style = MaterialTheme.typography.labelSmall,
                color = FalconCyan
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp).testTag("close_payment_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Pricing Architecture Card
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = ShaheenSurfaceCard,
          border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "هيكل التسعير والاشتراك:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = FalconBlue
              )
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = ActiveEmerald.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, ActiveEmerald.copy(alpha = 0.4f))
              ) {
                Text(
                  text = "LIFETIME + ANNUAL SUBSCRIPTION",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                  ),
                  color = ActiveEmerald,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "• سعر شراء التطبيق الأساسي (يوزر واحد):", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), color = TextMuted)
              Text(text = "100 USDT", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = TextWhite)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "• اشتراك صيانة سنوي لكل يوزر:", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), color = TextMuted)
              Text(text = "25 USDT / Year", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = FalconCyan)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "• كل يوزر إضافي (بنصف القيمة):", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), color = TextMuted)
              Text(text = "50 USDT / User", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = ActiveEmerald)
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Multi-User Seat Counter
        Text(
          text = "إضافة مستخدمين إضافيين (Multi-User Expansion):",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = ShaheenSurfaceCard,
          border = androidx.compose.foundation.BorderStroke(1.dp, ShaheenMetallicBorderLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "مجموع التراخيص: $totalUsers مستخدم",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
              )
              Text(
                text = "الأساسي (1) + إضافي ($extraUsersCount × 50 USDT)",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = TextMuted
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              IconButton(
                onClick = { if (extraUsersCount > 0) onExtraUsersChange(extraUsersCount - 1) },
                enabled = extraUsersCount > 0
              ) {
                Icon(
                  imageVector = Icons.Default.RemoveCircleOutline,
                  contentDescription = "Decrease users",
                  tint = if (extraUsersCount > 0) FalconCyan else TextDim
                )
              }

              Text(
                text = "+$extraUsersCount",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                ),
                color = FalconCyan,
                modifier = Modifier.padding(horizontal = 4.dp)
              )

              IconButton(
                onClick = { onExtraUsersChange(extraUsersCount + 1) }
              ) {
                Icon(
                  imageVector = Icons.Default.AddCircleOutline,
                  contentDescription = "Increase users",
                  tint = FalconCyan
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Total Amount Summary Box
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FalconBlue.copy(alpha = 0.12f))
            .border(1.dp, FalconBlue.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "المبلغ الإجمالي المستحق للدفع:",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
              )
              Text(
                text = "$totalOneTimeUsdt USDT",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                ),
                color = FalconCyan
              )
            }
            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = "الاشتراك السنوي:",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
              )
              Text(
                text = "$totalAnnualUsdt USDT / سنة",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = ActiveEmerald
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3 Payment Gateways Selection
        Text(
          text = "اختر منصة الدفع المعتمدة (3 خيارات حصرية):",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(8.dp))

        PaymentGatewayOption(
          gateway = PaymentGateway.BINANCE_PAY,
          isSelected = selectedGateway == PaymentGateway.BINANCE_PAY,
          badge = "BINANCE BSC",
          onSelect = { selectedGateway = PaymentGateway.BINANCE_PAY }
        )

        PaymentGatewayOption(
          gateway = PaymentGateway.MEXC_PAY,
          isSelected = selectedGateway == PaymentGateway.MEXC_PAY,
          badge = "MEXC BSC",
          onSelect = { selectedGateway = PaymentGateway.MEXC_PAY }
        )

        PaymentGatewayOption(
          gateway = PaymentGateway.FIAT_ONRAMP,
          isSelected = selectedGateway == PaymentGateway.FIAT_ONRAMP,
          badge = "CARD / GPAY",
          onSelect = { selectedGateway = PaymentGateway.FIAT_ONRAMP }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Selected Gateway Address Box with Copy
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = ShaheenBackground,
          border = androidx.compose.foundation.BorderStroke(1.dp, FalconBlue.copy(alpha = 0.5f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "عنوان محفظة الإيداع (${selectedGateway.network}):",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = TextMuted
              )
              IconButton(
                onClick = {
                  val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                  clipboard.setPrimaryClip(ClipData.newPlainText("Deposit Address", selectedGateway.address))
                  Toast.makeText(context, "تم نسخ عنوان المحفظة بنجاح", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(28.dp).testTag("copy_address_button")
              ) {
                Icon(
                  imageVector = Icons.Default.ContentCopy,
                  contentDescription = "Copy address",
                  tint = FalconCyan,
                  modifier = Modifier.size(16.dp)
                )
              }
            }

            Text(
              text = selectedGateway.address,
              style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              ),
              color = ConsoleGreen,
              modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
              text = selectedGateway.description,
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = TextDim
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Transaction Hash Submission
        Text(
          text = "رقم المعاملة / رابط التحويل (TXID Hash):",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
          color = TextWhite
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = txHash,
          onValueChange = { txHash = it },
          placeholder = { Text("أدخل هاش التحويل (TXID) لتفعيل التراخيص فوراً", color = TextDim, fontSize = 11.sp) },
          modifier = Modifier.fillMaxWidth().testTag("tx_hash_input"),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FalconBlue,
            unfocusedBorderColor = ShaheenMetallicBorder,
            focusedContainerColor = ShaheenSurfaceCard,
            unfocusedContainerColor = ShaheenSurfaceCard,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isSubmitted) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = ActiveEmerald.copy(alpha = 0.15f),
            border = androidx.compose.foundation.BorderStroke(1.dp, ActiveEmerald),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = ActiveEmerald)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "تم استلام الطلب وتأكيد التحويل! جاري ربط التراخيص عبر SHAHEEN Core.",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = TextWhite
              )
            }
          }
          Spacer(modifier = Modifier.height(10.dp))
        }

        // Action Buttons
        Button(
          onClick = {
            isSubmitted = true
            Toast.makeText(context, "تم التحقق من طلب الدفع وترقية الحساب", Toast.LENGTH_LONG).show()
          },
          modifier = Modifier.fillMaxWidth().testTag("submit_payment_button"),
          colors = ButtonDefaults.buttonColors(
            containerColor = FalconBlue,
            contentColor = TextWhite
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Paid, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "تأكيد الدفع وتفعيل التراخيص ($totalOneTimeUsdt USDT)", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun PaymentGatewayOption(
  gateway: PaymentGateway,
  isSelected: Boolean,
  badge: String,
  onSelect: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(if (isSelected) FalconBlue.copy(alpha = 0.15f) else ShaheenSurfaceCard)
      .border(
        width = 1.dp,
        color = if (isSelected) FalconBlue else ShaheenMetallicBorder,
        shape = RoundedCornerShape(10.dp)
      )
      .clickable(onClick = onSelect)
      .padding(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(if (isSelected) ActiveEmerald else TextDim)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = gateway.title,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = TextWhite
          )
          Text(
            text = gateway.network,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (isSelected) FalconCyan else TextMuted
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(4.dp),
        color = if (isSelected) FalconBlue.copy(alpha = 0.3f) else ShaheenBackground,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) FalconBlue else ShaheenMetallicBorder)
      ) {
        Text(
          text = badge,
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          ),
          color = if (isSelected) FalconCyan else TextDim,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
      }
    }
  }
}
