package ru.nook_of_madness.transaction;

import ru.nook_of_madness.account.Account;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.nook_of_madness.utils.BigDecimalUtil.MINUS_ONE;

public class Transaction {

    private UUID transactionUUID;
    private Account from;
    private Account to;
    private BigDecimal value;
    private ClientStatus clientStatus;

    public Transaction(Account from, Account to, BigDecimal value) {
        this.transactionUUID = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.value = value;
        this.clientStatus = ClientStatus.STARTED;
    }

    public UUID getTransactionUUID() {
        return transactionUUID;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public Transaction setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
        return this;
    }

    public void checkFromBalance() {
        if (from.getAccountBalance().compareTo(value) < 0) {
            setClientStatus(ClientStatus.NOT_ENOUGH_MONEY);
        } else {
            setClientStatus(ClientStatus.PROCESSING);
        }
    }

    public void execute() {
        if (clientStatus != ClientStatus.PROCESSING) {
            throw new IllegalStateException("Требуется статус только PROCESSING, остальные не проходят");
        } else {
            from.handleTransactionFromAccount(this);
            to.handleTransactionToAccount(this);
            if (clientStatus == ClientStatus.PROCESSING) {
                from.changeAccountBalance(value -> value.add(this.value.multiply(MINUS_ONE)), "Транзакция " + transactionUUID);
                to.changeAccountBalance(value -> value.add(this.value), "Транзакция " + transactionUUID);
                setClientStatus(ClientStatus.COMPLETED);
            }
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionUUID=" + transactionUUID +
                ", from=" + from +
                ", to=" + to +
                ", value=" + value +
                ", clientStatus=" + clientStatus +
                '}';
    }
}
