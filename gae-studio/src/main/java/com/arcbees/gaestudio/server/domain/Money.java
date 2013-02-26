/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
