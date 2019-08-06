package ru.zferma.zebrascanner;

public class AccountingAreaModel {
    public String OperationType ;
    public String GuideOperationType;
    public String AccountingArea ;
    public String GuideAccountingArea;

    public AccountingAreaModel(String operationType, String guideOperationType, String accountingArea, String guideAccountingArea) {
        this.OperationType = operationType;
        this.GuideOperationType = guideOperationType;
        this.AccountingArea = accountingArea;
        this.GuideAccountingArea = guideAccountingArea;
    }
}
