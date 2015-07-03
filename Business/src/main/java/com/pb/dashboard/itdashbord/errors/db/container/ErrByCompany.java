package com.pb.dashboard.itdashbord.errors.db.container;

public class ErrByCompany {
    private String errorCode;
    private String numberOfErrors;
    private String errorType;
    private String decode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(String numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public String[] errByCompanyAsStringArr(){
        return new String[]{errorCode, numberOfErrors, errorType, decode};
    }
}
