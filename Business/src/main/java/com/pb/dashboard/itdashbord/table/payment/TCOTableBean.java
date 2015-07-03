package com.pb.dashboard.itdashbord.table.payment;

import com.pb.dashboard.itdashbord.table.model.PaymentTCO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class TCOTableBean extends TableBeanAbs implements Serializable {

    private static final long serialVersionUID = 3766138570700847066L;
    private List<PaymentTCO> paymentTCO;
    private Map<String, List<Object[]>> tcoHolder = new LinkedHashMap<String, List<Object[]>>();
    private String tco = "";
    private String myPayments = "";
    private String tcoAll = "";
    private String myPaymentsAll = "";
    private String date = null;

    public TCOTableBean() {
        fillLists();
    }

    public List<Object[]> getPaymentsTCO() {
        fillLists();
        paymentTCO = dm.paymentTCO;
        return populatePayments();
    }

    private List<Object[]> populatePayments() {
        List<Object[]> payments = new ArrayList<Object[]>();
        for (PaymentTCO payment : paymentTCO) {
            String dateTemp = createStamp(payment.getDate());
            if (date == null) {
                date = dateTemp;
            }
            if (dateTemp.equals(date)) {
                buildPayment(payments, payment);
                continue;
            } else {
                tcoHolder.put(date, payments);
                date = dateTemp;
                payments = new ArrayList<Object[]>();
                buildPayment(payments, payment);
            }
        }
        tcoHolder.put(date, payments);
        return getPaymentsCurrentData();
    }

    public void buildPayment(List<Object[]> payments, PaymentTCO payment) {
        payments.add(new Object[]{payment.getDate(),
                payment.getDayOfWeek(), payment.getTco(),
                payment.getMyPayment(),
                devide(payment.getMyPayment(), payment.getTco())});
    }

    private List<Object[]> getPaymentsCurrentData() {
        tco = "0";
        myPayments = "0";
        SimpleDateFormat formatter = new SimpleDateFormat("MM.yyyy");
        List<Object[]> objects = tcoHolder.get(formatter.format(new Date()));
        if (objects != null) {
            for (Object[] payment : objects) {
                tco = multyply(tco, payment[2].toString());
                myPayments = multyply(myPayments, payment[3].toString());
            }
            return objects;
        } else {
            return new ArrayList<Object[]>();
        }
    }

    public List<Object[]> getPayments() {
        tcoAll = "0";
        myPaymentsAll = "0";
        List<Object[]> payments = new ArrayList<Object[]>();
        for (PaymentTCO payment : paymentTCO) {
            buildPayment(payments, payment);
            tcoAll = multyply(tcoAll, payment.getTco());
            myPaymentsAll = multyply(myPaymentsAll, payment.getMyPayment());
        }
        return payments;
    }

    public Object[] getSum() {
        return new Object[]{"Всего", "", pbformat.format(Double.valueOf(tco)), pbformat.format(Double.valueOf(myPayments)),
                pbformat.format(Double.valueOf(devide(myPayments, tco)))};
    }

    public Object[] getSumAll() {
        return new Object[]{"Всего", "", pbformat.format(Double.valueOf(tcoAll)), pbformat.format(Double.valueOf(myPaymentsAll)),
                pbformat.format(Double.valueOf(devide(myPaymentsAll, tcoAll)))};
    }

    private String createStamp(String date) {
        String[] data = date.split("\\.");
        return data[1] + "." + data[2];
    }

    public Map<String, List<Object[]>> getTcoHolder() {
        return tcoHolder;
    }
}