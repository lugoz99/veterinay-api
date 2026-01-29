package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One relationship: Many transactions can belong to one invoice
    // This allows multiple payment attempts, partial payments, or refunds for a single invoice
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    // Stripe Integration Fields
    @Column(name = "stripe_payment_intent_id", unique = true, length = 255)
    private String stripePaymentIntentId; // pi_xxxxx - Unique identifier from Stripe

    @Column(name = "stripe_charge_id", length = 255)
    private String stripeChargeId; // ch_xxxxx - Charge ID when payment is captured

    @Column(name = "stripe_customer_id", length = 255)
    private String stripeCustomerId; // cus_xxxxx - Stripe customer ID for recurring payments

    // Payment Details
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency = "COP"; // ISO currency code: COP, USD, EUR

    @Column(name = "payment_method", length = 50, nullable = false)
    private String paymentMethod; // CARD, PSE, NEQUI, BANK_TRANSFER, CASH

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 30, nullable = false)
    private PaymentStatus paymentStatus;

    // Card Information (if payment method is CARD)
    @Column(name = "card_brand", length = 20)
    private String cardBrand; // visa, mastercard, amex, etc.

    @Column(name = "card_last4", length = 4)
    private String cardLast4; // Last 4 digits of card for reference

    @Column(name = "receipt_url", columnDefinition = "TEXT")
    private String receiptUrl; // Stripe receipt URL for customer

    // Timestamps
    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDateTime transactionDate;

    @Column(name = "completed_date")
    private LocalDateTime completedDate; // When payment was successfully completed

    // Error Handling
    @Column(name = "error_code", length = 50)
    private String errorCode; // Stripe error code if payment failed

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage; // Human-readable error message

    // Webhook Management
    @Column(name = "webhook_received")
    private Boolean webhookReceived = false; // Flag to track if webhook was processed

    @Column(name = "webhook_data", columnDefinition = "JSON")
    private String webhookData; // Full Stripe webhook payload for debugging

    // Refund Information
    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount; // Amount refunded (for partial refunds)

    @Column(name = "refund_reason", columnDefinition = "TEXT")
    private String refundReason;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    // Payment Status Enum
    public enum PaymentStatus {
        PENDING,              // Payment intent created, waiting for confirmation
        PROCESSING,           // Payment is being processed
        REQUIRES_ACTION,      // Additional authentication required (3D Secure)
        SUCCEEDED,            // Payment completed successfully
        FAILED,               // Payment failed
        CANCELLED,            // Payment cancelled by user or system
        REFUNDED,             // Full refund issued
        PARTIALLY_REFUNDED    // Partial refund issued
    }

    // Lifecycle Callbacks
    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        // Automatically set completed date when status changes to SUCCEEDED
        if (paymentStatus == PaymentStatus.SUCCEEDED && completedDate == null) {
            completedDate = LocalDateTime.now();
        }
    }

    // Convenience Methods
    public boolean isSuccessful() {
        return paymentStatus == PaymentStatus.SUCCEEDED;
    }

    public boolean isPending() {
        return paymentStatus == PaymentStatus.PENDING ||
                paymentStatus == PaymentStatus.PROCESSING;
    }

    public boolean isFailed() {
        return paymentStatus == PaymentStatus.FAILED ||
                paymentStatus == PaymentStatus.CANCELLED;
    }

    public boolean isRefunded() {
        return paymentStatus == PaymentStatus.REFUNDED ||
                paymentStatus == PaymentStatus.PARTIALLY_REFUNDED;
    }

    // Business Logic Methods
    public void markAsSucceeded(String stripeChargeId, String receiptUrl) {
        this.paymentStatus = PaymentStatus.SUCCEEDED;
        this.stripeChargeId = stripeChargeId;
        this.receiptUrl = receiptUrl;
        this.completedDate = LocalDateTime.now();
        this.webhookReceived = true;
    }

    public void markAsFailed(String errorCode, String errorMessage) {
        this.paymentStatus = PaymentStatus.FAILED;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.webhookReceived = true;
    }

    public void markAsRefunded(BigDecimal refundAmount, String reason) {
        if (refundAmount.compareTo(this.amount) >= 0) {
            this.paymentStatus = PaymentStatus.REFUNDED;
        } else {
            this.paymentStatus = PaymentStatus.PARTIALLY_REFUNDED;
        }
        this.refundAmount = refundAmount;
        this.refundReason = reason;
        this.refundedAt = LocalDateTime.now();
    }
}