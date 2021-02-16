package ru.nook_of_madness;

import ru.nook_of_madness.account.CreditAccount;
import ru.nook_of_madness.account.DebetAccount;
import ru.nook_of_madness.account.DepositAccount;
import ru.nook_of_madness.bank.Bank;
import ru.nook_of_madness.transaction.Transaction;
import ru.nook_of_madness.user.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank("BAN");
        User user = User.builder("a", "b").build();
        DebetAccount account = new DebetAccount(UUID.randomUUID(), BigDecimal.TEN, user, bank);
        DepositAccount account2 = new DepositAccount(UUID.randomUUID(), 36, BigDecimal.TEN, user, bank);
        System.out.println(account2);
        System.out.println(account);
        System.out.println(user.toString());
        System.out.println(bank.toString());

        Transaction transaction = bank.createTransaction(account, account2, BigDecimal.TEN);
        System.out.println(transaction);
        bank.executeTransaction(transaction);
        System.out.println(transaction);

        Transaction secondTransaction = bank.createTransaction(account2, account, BigDecimal.TEN);
        System.out.println(secondTransaction);
        bank.executeTransaction(secondTransaction);
        System.out.println(secondTransaction);

        bank.cancelTransaction(transaction);
        //bank.cancelTransaction(secondTransaction); <-- IllegalStateExcepiton

        for (int i = 0; i < 62; i++) {
            account.onDay();
            account2.onDay();
        }
    }
}
