/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
