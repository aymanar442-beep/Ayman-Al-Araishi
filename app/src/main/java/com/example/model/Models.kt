package com.example.model

enum class LogLevel {
  INFO,
  SUCCESS,
  WARNING,
  ERROR,
  SYSTEM
}

data class LogEntry(
  val id: Long = System.currentTimeMillis() + (0..999).random(),
  val timestamp: String,
  val tag: String,
  val message: String,
  val level: LogLevel = LogLevel.INFO
)

data class ShaheenConfig(
  val username: String = "ayman",
  val licenseKey: String = "SH-9924-SEC-ALPHA-88X",
  val apiKey: String = "sh_live_k82f990141be297d09873a",
  val hasAcceptedDisclaimer: Boolean = false,
  val additionalUsersCount: Int = 0,
  val preEmptiveShieldActive: Boolean = true,
  val bet23BehavioralLock: Boolean = true,
  val emergencyKillSwitchReady: Boolean = true
)

data class EngineStatus(
  val isRunning: Boolean = false,
  val uptimeSeconds: Long = 0L,
  val totalCycles: Long = 0L,
  val currentPair: String = "BTC/USDT",
  val latencyMs: Int = 14,
  val priceIndex: Double = 98450.20,
  val volumeScanned: Double = 1.42,
  val riskScore: Double = 0.02,
  val testRemainingSeconds: Long = 86400L * 3L + 14320L, // 3 Days Beta Countdown
  val behavioralStability: Double = 99.8, // Bet23 Psycho-Temporal Stability Index
  val preEmptiveOrdersArmed: Int = 8, // Exchange-Level Pre-Emptive OCO Orders
  val isOfflineImmune: Boolean = true
)

data class EcosystemProduct(
  val id: String,
  val title: String,
  val subtitle: String,
  val category: String,
  val status: String,
  val badgeColor: Long,
  val description: String,
  val features: List<String>
)

enum class PaymentGateway(val title: String, val network: String, val address: String, val description: String) {
  BINANCE_PAY(
    title = "Binance Wallet (BNB Smart Chain)",
    network = "BNB Smart Chain (BEP20)",
    address = "0x48d27EDC1a95AD2484bB6563985e4BDd2F952CcC",
    description = "محفظة باينانس الرسمية للمطور على شبكة BNB Smart Chain"
  ),
  MEXC_PAY(
    title = "MEXC Wallet (BNB Smart Chain)",
    network = "BNB Smart Chain (BEP20)",
    address = "0x7de83792347744c4cf6d7d6d6236ced68cccc56c",
    description = "محفظة MEXC الرسمية للمطور على شبكة BNB Smart Chain"
  ),
  FIAT_ONRAMP(
    title = "شراء وتحويل فوري (بطاقات بنكية / Google Pay)",
    network = "Instant Card & Mobile Fiat to Crypto",
    address = "0x48d27EDC1a95AD2484bB6563985e4BDd2F952CcC",
    description = "يمكن للمشتري الدفع ببطاقته البنكية أو Google Pay عبر منصات الشراء الفوري (MoonPay/Binance Connect) لتصل مباشرة كـ USDT إلى محفظتك"
  )
}
