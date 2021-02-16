package ru.nook_of_madness.account;

import ru.nook_of_madness.bank.Bank;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.user.User;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

public abstract class Account implements Tickable {
    private UUID accountUUID;
    private BigDecimal accountBalance;
    private User user;
    private Bank bank;

    public Account(UUID accountUUID, BigDecimal accountBalance, User user, Bank bank) {
        this.accountUUID = accountUUID;
        this.accountBalance = accountBalance;
        this.user = user;
        this.bank = bank;
    }


    public UUID getAccountUUID() {
        return accountUUID;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }

    public void changeAccountBalance(Function<BigDecimal, BigDecimal> function, String operationType) {
        System.out.println("Исполнение операции " + operationType);
        accountBalance = function.apply(accountBalance);
    }



    public void handleTransactionFromAccount(Transaction transaction) {

    }

    public void handleTransactionToAccount(Transaction transaction) {

    }

    @Override
    public String toString() {
        return "Account{" +
                "accountUUID=" + accountUUID +
                ", accountBalance=" + accountBalance +
                ", user=" + user +
                ", bank=" + bank +
                '}';
    }
}
