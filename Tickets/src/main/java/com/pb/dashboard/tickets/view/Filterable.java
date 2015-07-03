package com.pb.dashboard.tickets.view;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.tickets.entype.TicketType;

public interface Filterable {

    void filterUpdated(TicketType type, Bank bank, int year, Month month);

}
