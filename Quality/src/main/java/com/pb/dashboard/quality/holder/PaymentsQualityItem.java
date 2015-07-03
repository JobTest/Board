package com.pb.dashboard.quality.holder;

import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.core.model.Year;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.PaymentType;
import com.pb.dashboard.quality.types.ValueType;

public class PaymentsQualityItem {

    private String bank;
    private String date;
    private Month month;
    private Year year;
    private double value;
    private Channel channel; // DIMENSION_3
    private ValueType valueType; // payments cnt, timings etc
    private PaymentType paymentType; // DIMENSION_1
    private String rejectName; // DIMENSION_2_NAME

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = convertToChannel(channel);
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = convertToValueType(valueType);
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = convertToPaymentType(paymentType);
    }

    public String getRejectName() {
        return rejectName;
    }

    public void setRejectName(String rejectName) {
        this.rejectName = rejectName;
    }

    private Channel convertToChannel(String channel) {
        for (Channel currentChannel : Channel.values()) {
            if (currentChannel.getAttrId().equals(channel)) return currentChannel;
        }
        return Channel.UNDEF;
    }

    private ValueType convertToValueType(String valueType) {
        for (ValueType type : ValueType.values()) {
            if (type.toString().equals(valueType)) return type;
        }
        return ValueType.UNDEF;
    }

    private PaymentType convertToPaymentType(String paymentType) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getAttrID().equals(paymentType)) return type;
        }
        return PaymentType.UNDEF;
    }

    public String toString() {
        return String.format("Date: %s, Bank: %s, Channel: %s, Value Type: %s, " +
                "Payment Type: %s, RejectName: %s, Value: %.2f", date, bank,
                channel, valueType, paymentType, rejectName, value);
    }
}
