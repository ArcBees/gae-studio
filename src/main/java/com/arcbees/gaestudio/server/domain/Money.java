package com.arcbees.gaestudio.server.domain;

public class Money {

    private Long amount;
    private Currency currency;

    public Money() {
    }

    public Money(Double amount, Currency currency) {
        setAmount(amount);
        this.currency = currency;
    }

    public Double getAmount() {
        return amount / 100.00;
    }

    public void setAmount(Double amount) {
        this.amount = Math.round(amount * 100);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
