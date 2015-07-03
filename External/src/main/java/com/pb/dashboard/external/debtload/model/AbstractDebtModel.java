package com.pb.dashboard.external.debtload.model;

import com.pb.dashboard.core.model.Bank;

public class AbstractDebtModel {

    protected Bank bank;
    protected int recipientId;
    protected String recipientName = "";
    protected String branch = "";

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
