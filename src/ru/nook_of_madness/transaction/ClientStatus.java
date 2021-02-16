package ru.nook_of_madness.transaction;

public enum ClientStatus {
    STARTED,
    PROCESSING,
    NOT_ENOUGH_MONEY,
    ERROR,
    FRAUD_ERROR,
    SHADY_ERROR,
    DEPOSIT_TIME_IS_NOT_EXPIRED,
    COMPLETED,
    REFUND;
}
