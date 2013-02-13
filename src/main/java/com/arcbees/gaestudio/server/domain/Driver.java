package com.arcbees.gaestudio.server.domain;

import java.util.Date;

import javax.persistence.Embedded;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Driver {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Date renewalDate;
    @Embedded
    private Money accountBalance;

    public Driver() {
    }

    public Driver(String firstName, String lastName, Date renewalDate, Money accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.renewalDate = renewalDate;
        this.accountBalance = accountBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Money getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Money accountBalance) {
        this.accountBalance = accountBalance;
    }

}
