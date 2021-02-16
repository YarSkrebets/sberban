package ru.nook_of_madness.bank;

import ru.nook_of_madness.account.Account;
import ru.nook_of_madness.transaction.ClientStatus;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.utils.BigDecimalUtil;

import java.math.BigDecimal;

public class Bank {
    private String name;

    public Bank(String name) {
        this.name = name;
    }

    public BigDecimal calculateDepositPercentage(BigDecimal decimal) {
        return BigDecimal.valueOf(0.01);
    }

    public BigDecimal calculateDebetPercentage(BigDecimal value) {
        return BigDecimal.valueOf(0.001);
    }

    public BigDecimal calculateCreditComission() {
        return BigDecimal.valueOf(10);
    }

    public Transaction createTransaction(Account from, Account to, BigDecimal value) {
        Transaction transaction = new Transaction(from, to, value);
        return transaction;
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction.getClientStatus() != ClientStatus.STARTED) {
            throw new IllegalStateException("Чтобы банк мог обработать транзакцию она должна быть в состании STARTED");
        }
        transaction.setClientStatus(ClientStatus.PROCESSING);
        transaction.execute();
    }

    public void cancelTransaction(Transaction transaction) {
        if (transaction.getClientStatus() == ClientStatus.COMPLETED) {
            transaction.getTo().changeAccountBalance(value -> value.add(transaction.getValue().multiply(BigDecimalUtil.MINUS_ONE)), "Отмена транзакции " + transaction.getTransactionUUID());
            transaction.getFrom().changeAccountBalance(value -> value.add(transaction.getValue()), "Отмена транзакции " + transaction.getTransactionUUID());
            transaction.setClientStatus(ClientStatus.REFUND);
        } else {
            throw new IllegalStateException("Нельзя отменить транзакцию если она не в статусе COMPLETED");
        }
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                '}';
    }
}
