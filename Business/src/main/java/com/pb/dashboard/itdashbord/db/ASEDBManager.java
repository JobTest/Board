package com.pb.dashboard.itdashbord.db;

import com.pb.dashboard.itdashbord.table.model.PaymentTCO;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ASEDBManager {

    private static final Logger LOG = Logger.getLogger(ASEDBManager.class);
    private static ASEDBManager instance;
    private List<String> messages = new ArrayList<String>();
    public List<PaymentTCO> paymentTCO;

    private ASEDBManager() {
    }

    public static ASEDBManager getInstance() {
        if (instance == null) {
            return instance = new ASEDBManager();
        }
        return instance;
    }

    public void updateData() {
        paymentTCO = createDemoData();
        LOG.info("Data Update");
    }

    private List<PaymentTCO> createDemoData() {
        List<PaymentTCO> paymentTCOs = new ArrayList<PaymentTCO>();
        Object[] holder = DataHolder.getTcoPayments();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            paymentTCOs.add(new PaymentTCO(item[0].toString(), item[3]
                    .toString(), item[1].toString(), item[2].toString()));
        }
        return paymentTCOs;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String msg) {
        messages.add(msg);
    }

    public void clearMsg() {
        messages.clear();
    }
}