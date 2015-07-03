package com.pb.dashboard.tickets.logic;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.tickets.entype.ChannelOutput;
import com.pb.dashboard.tickets.entype.TicketType;

public class FilterModel {

    private TicketType ticketType;
    private Bank bank;
    private int year;
    private Month month;
    private ChannelOutput branchChannel;

    public FilterModel(TicketType ticketType, Bank bank, int year, Month month, ChannelOutput branchChannel) {
        this.ticketType = ticketType;
        this.bank = bank;
        this.year = year;
        this.month = month;
        this.branchChannel = branchChannel;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Bank getBank() {
        return bank;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public ChannelOutput getBranchChannel() {
        return branchChannel;
    }

    public void setBranchChannel(ChannelOutput branchChannel) {
        this.branchChannel = branchChannel;
    }
}
