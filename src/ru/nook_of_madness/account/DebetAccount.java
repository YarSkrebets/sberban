package ru.nook_of_madness.account;

import ru.nook_of_madness.bank.Bank;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public class DebetAccount extends Account {
    private BigDecimal monthlyProfit = BigDecimal.ZERO;
    private int days;

    public DebetAccount(UUID accountUUID, BigDecimal accountBalance, User user, Bank bank) {
        super(accountUUID, accountBalance, user, bank);
    }

    @Override
    public void handleTransactionFromAccount(Transaction transaction) {
        transaction.checkFromBalance();
    }

    @Override
    public void onDay() {
        monthlyProfit = monthlyProfit.add(getAccountBalance().multiply(getBank().calculateDebetPercentage(getAccountBalance())));
        days++;
        if (days == 30) {
            changeAccountBalance(account -> account.add(monthlyProfit), "Месячные проценты");
            monthlyProfit = BigDecimal.ZERO;
            days = 0;
        }
    }
}
