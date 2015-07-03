package com.pb.dashboard.itdashbord.table.authorize;

import com.pb.dashboard.itdashbord.table.payment.TableBeanAbs;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuthorizeBean extends TableBeanAbs implements Serializable {
    private static final long serialVersionUID = 4956681506128661342L;
    private Map<String, List<Object[]>> tcoHolder = new LinkedHashMap<String, List<Object[]>>();
    private Object[] holder;
    private String date = null;
    private String operationTCO = "0";
    private String authQR = "0";
    private String noAuth = "0";
    private String onCart = "0";
    private String onPhone = "0";

    private String operationTCOAll = "0";
    private String authQRAll = "0";
    private String noAuthAll = "0";
    private String onCartAll = "0";
    private String onPhoneAll = "0";

    public AuthorizeBean(Object[] holder) {
        super();
        this.holder = holder;
        populatePayments();
    }

    public void setHolder() {

    }

    private List<Object[]> populatePayments() {
        List<Object[]> payments = new ArrayList<Object[]>();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            String dateTemp = createStamp(item[0].toString());
            if (date == null) {
                date = dateTemp;
            }
            if (dateTemp.equals(date)) {
                buildPayment(payments, item);
                continue;
            } else {
                tcoHolder.put(date, payments);
                date = dateTemp;
                payments = new ArrayList<Object[]>();
                buildPayment(payments, item);
            }
        }
        tcoHolder.put(date, payments);
        return getPaymentsCurrentData();
    }

    public void buildPayment(List<Object[]> payments, Object[] item) {
        payments.add(new Object[]{item[0].toString(), item[1].toString(), item[2].toString(),
                item[3].toString(), item[4].toString(), item[5].toString(),
                item[6].toString(), item[7].toString()});
    }

    public List<Object[]> getPaymentsCurrentData() {
        clearData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM");
        List<Object[]> objects = tcoHolder.get(formatter.format(new Date()));
        if (objects == null) {
            return new ArrayList<Object[]>();
        }
        for (Object[] payment : objects) {
            operationTCO = multyply(operationTCO, payment[1].toString());
            authQR = multyply(authQR, payment[2].toString());
            noAuth = multyply(noAuth, payment[3].toString());
            onCart = multyply(onCart, payment[5].toString());
            onPhone = multyply(onCart, payment[6].toString());
        }
        return objects;
    }

    public List<Object[]> getPayments() {
        clearData();
        List<Object[]> payments = new ArrayList<Object[]>();
        for (int i = 0; i < holder.length; i++) {
            Object[] item = (Object[]) holder[i];
            buildPayment(payments, item);
            operationTCOAll = multyply(operationTCOAll, item[1].toString());
            authQRAll = multyply(authQRAll, item[2].toString());
            noAuthAll = multyply(noAuthAll, item[3].toString());
            onCartAll = multyply(onCartAll, item[5].toString());
            onPhoneAll = multyply(onCartAll, item[6].toString());
        }
        return payments;
    }

    public Object[] getSum() {
        return new Object[]{"Всего",
                pbformat.format(Double.valueOf(operationTCO)),
                pbformat.format(Double.valueOf(authQR)),
                pbformat.format(Double.valueOf(noAuth)),
                pbformat.format(Double.valueOf(devide(noAuth, operationTCO))),
                pbformat.format(Double.valueOf(onCart)),
                pbformat.format(Double.valueOf(onPhone)),
                pbformat.format(Double.valueOf(devide(onPhone, operationTCO)))};
    }

    public Object[] getSumAll() {
        return new Object[]{"Всего",
                pbformat.format(Double.valueOf(operationTCOAll)),
                pbformat.format(Double.valueOf(authQRAll)),
                pbformat.format(Double.valueOf(noAuthAll)),
                pbformat.format(Double.valueOf(devide(noAuthAll, operationTCOAll))),
                pbformat.format(Double.valueOf(onCartAll)),
                pbformat.format(Double.valueOf(onPhoneAll)),
                pbformat.format(Double.valueOf(devide(onPhoneAll, operationTCOAll)))};
    }

    private String createStamp(String date) {
        String[] data = date.split("\\.");
        return data[0] + "." + data[1];
    }

    private void clearData() {
        operationTCO = "0";
        authQR = "0";
        noAuth = "0";
        onCart = "0";
        onPhone = "0";
        operationTCOAll = "0";
        authQRAll = "0";
        noAuthAll = "0";
        onCartAll = "0";
        onPhoneAll = "0";
    }
}