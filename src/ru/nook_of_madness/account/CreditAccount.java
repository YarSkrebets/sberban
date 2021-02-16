package ru.nook_of_madness.account;

import ru.nook_of_madness.bank.Bank;
import ru.nook_of_madness.transaction.ClientStatus;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditAccount extends Account {
    private static final BigDecimal MINUS_ONE = BigDecimal.valueOf(-1);
    private BigDecimal creditLimit;

    public CreditAccount(UUID accountUUID, BigDecimal accountBalance, BigDecimal creditLimit, User user, Bank bank) {
        super(accountUUID, accountBalance, user, bank);
        this.creditLimit = creditLimit;
    }


    @Override
    public void handleTransactionFromAccount(Transaction transaction) {
        if (getAccountBalance().add(transaction.getValue().multiply(MINUS_ONE)).add(creditLimit).compareTo(BigDecimal.ZERO) < 0) {
            transaction.setClientStatus(ClientStatus.NOT_ENOUGH_MONEY);
        } else {
            transaction.setClientStatus(ClientStatus.PROCESSING);
        }
    }

    @Override
    public void onDay() {
        if (getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
            changeAccountBalance(accountBalance -> accountBalance.add(getBank().calculateCreditComission().multiply(MINUS_ONE)), "Проценты по кредитной карте.");
        }
    }
}
