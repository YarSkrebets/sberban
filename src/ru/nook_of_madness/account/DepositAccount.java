package ru.nook_of_madness.account;

import ru.nook_of_madness.bank.Bank;
import ru.nook_of_madness.transaction.ClientStatus;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.user.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class DepositAccount extends Account {
    private boolean depositEnded;
    private int daysDueToEnding;
    private BigDecimal monthlyProfit = BigDecimal.ZERO;
    private int days;

    public DepositAccount(UUID accountUUID, int daysDueToEnding, BigDecimal accountBalance, User user, Bank bank) {
        super(accountUUID, accountBalance, user, bank);
        this.daysDueToEnding = daysDueToEnding;
    }

    @Override
    public void handleTransactionFromAccount(Transaction transaction) {
        transaction.checkFromBalance();
        if (!depositEnded) {
            transaction.setClientStatus(ClientStatus.DEPOSIT_TIME_IS_NOT_EXPIRED);
        }

    }

    @Override
    public void onDay() {
        if (daysDueToEnding == 0) {
            depositEnded = true;
            if (monthlyProfit != null) {
                changeAccountBalance(account -> account.add(monthlyProfit), "Месячные проценты(Последний неполный месяц)");
                monthlyProfit = null;
            }
        } else {
            if (daysDueToEnding > 0) {
                daysDueToEnding--;
            }
            monthlyProfit = monthlyProfit.add(getAccountBalance().multiply(getBank().calculateDepositPercentage(getAccountBalance())));
            days++;
            if (days == 30) {
                changeAccountBalance(account -> account.add(monthlyProfit), "Месячные проценты");
                monthlyProfit = BigDecimal.ZERO;
                days = 0;
            }
        }
    }
}
