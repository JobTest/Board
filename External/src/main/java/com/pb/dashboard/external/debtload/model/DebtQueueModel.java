package com.pb.dashboard.external.debtload.model;

public class DebtQueueModel extends AbstractDebtModel {

    private String fileDate;
    private int loadFull;
    private int loadPart;
    private int loadMulti;
    private int loadMultiPart;
    private int loadTest;
    private int ftpFull;
    private int ftpPart;
    private int ftpMulti;
    private int ftpTest;

    private boolean isSigned = true;
    private boolean dbPathOk = true;
    private boolean inList = true;

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public int getLoadFull() {
        return loadFull;
    }

    public void setLoadFull(int loadFull) {
        this.loadFull = loadFull;
    }

    public int getLoadPart() {
        return loadPart;
    }

    public void setLoadPart(int loadPart) {
        this.loadPart = loadPart;
    }

    public int getLoadMulti() {
        return loadMulti;
    }

    public void setLoadMulti(int loadMulti) {
        this.loadMulti = loadMulti;
    }

    public int getLoadMultiPart() {
        return loadMultiPart;
    }

    public void setLoadMultiPart(int loadMultiPart) {
        this.loadMultiPart = loadMultiPart;
    }

    public int getLoadTest() {
        return loadTest;
    }

    public void setLoadTest(int loadTest) {
        this.loadTest = loadTest;
    }

    public int getFtpFull() {
        return ftpFull;
    }

    public void setFtpFull(int ftpFull) {
        this.ftpFull = ftpFull;
    }

    public int getFtpPart() {
        return ftpPart;
    }

    public void setFtpPart(int ftpPart) {
        this.ftpPart = ftpPart;
    }

    public int getFtpMulti() {
        return ftpMulti;
    }

    public void setFtpMulti(int ftpMulti) {
        this.ftpMulti = ftpMulti;
    }

    public int getFtpTest() {
        return ftpTest;
    }

    public void setFtpTest(int ftpTest) {
        this.ftpTest = ftpTest;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public boolean isDbPathOk() {
        return dbPathOk;
    }

    public void setDbPathOk(boolean dbPathOk) {
        this.dbPathOk = dbPathOk;
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebtQueueModel that = (DebtQueueModel) o;

        if (recipientId != that.recipientId) return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null) return false;
        if (ftpFull != that.ftpFull) return false;
        if (ftpMulti != that.ftpMulti) return false;
        if (ftpPart != that.ftpPart) return false;
        if (ftpTest != that.ftpTest) return false;
        if (loadFull != that.loadFull) return false;
        if (loadMulti != that.loadMulti) return false;
        if (loadMultiPart != that.loadMultiPart) return false;
        if (loadPart != that.loadPart) return false;
        if (loadTest != that.loadTest) return false;
        if (fileDate != null ? !fileDate.equals(that.fileDate) : that.fileDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileDate != null ? fileDate.hashCode() : 0;
        result = 31 * result + recipientId;
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + loadFull;
        result = 31 * result + loadPart;
        result = 31 * result + loadMulti;
        result = 31 * result + loadMultiPart;
        result = 31 * result + loadTest;
        result = 31 * result + ftpFull;
        result = 31 * result + ftpPart;
        result = 31 * result + ftpMulti;
        result = 31 * result + ftpTest;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Recipient ID: %d%n Date: %s, Recipient name: %s, Country: %s%n Load Full: %d, Load Part: %d, " +
                "Load Multi: %d, Load Multi-Part: %d, Load Test: %d%n Ftp Full: %d, Ftp Part: %d, Ftp Multi: %d, Ftp Test: %d, " +
                "Branch: %s%n Is Signed: %b, DB Path OK: %b, In List: %b%n",
                recipientId, fileDate, recipientName, bank, loadFull, loadPart, loadMulti, loadMultiPart, loadTest,
                ftpFull, ftpPart, ftpMulti, ftpTest, branch, isSigned, dbPathOk, inList);
    }

}
