package cn.com.basic.dto;

import java.math.BigDecimal;

/**
 * Created by zhaijiayi on 2016/6/29.
 */
public class BusinessReceiptsDTO {
    private String bizDate;  //yyyyMM
    //钱包端
    private BigDecimal agencyFee;/*居间服务费收入*/
    private BigDecimal withdrawFee;/*取现费手续费收入*/
    private BigDecimal managementFee;/*账户管理费收入*/
    private BigDecimal depositFee;/*充值费收入*/
    private BigDecimal transFee;/*转让费收入*/
    private BigDecimal advancePenaltyInterest;/*提前还款罚息收入*/
    private BigDecimal overdueFee;/*逾期滞纳金收入*/
    private BigDecimal dunManagementFee;/*催收管理费收入*/
    private BigDecimal membershipFee;/*会员费（每年）收入*/

    private BigDecimal depositExpenses;/*充值费支出*/
    private BigDecimal withdrawExpenses;/*取现费支出*/
    private BigDecimal verifyExpenses;/*认证费支出*/
    private BigDecimal otherExpenses;/*其他支出*/
    //理财
    private BigDecimal marketingInterestExpenses; /* 营销加息支出*/
    private BigDecimal marketingOffsetExpenses;     /*营销抵值支出*/
    private BigDecimal investorDeposit;          /* 充值*/
    private BigDecimal invest;                    /*投资*/
    private BigDecimal investorExpenses;        /* 支出*/
    private BigDecimal investorReceipts;          /* 收入*/
    private BigDecimal totalReceipts;        /*总收入*/

    //贷款
    private BigDecimal loanAmount;    /* 借款金额*/
    private BigDecimal feeExpenses;     /*费用支出*/
    private BigDecimal interestExpenses;  /* 利息支出*/
    private BigDecimal totalExpenses;      /* 总支出*/

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public BigDecimal getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(BigDecimal agencyFee) {
        this.agencyFee = agencyFee;
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public BigDecimal getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(BigDecimal depositFee) {
        this.depositFee = depositFee;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public BigDecimal getAdvancePenaltyInterest() {
        return advancePenaltyInterest;
    }

    public void setAdvancePenaltyInterest(BigDecimal advancePenaltyInterest) {
        this.advancePenaltyInterest = advancePenaltyInterest;
    }

    public BigDecimal getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee) {
        this.overdueFee = overdueFee;
    }

    public BigDecimal getDunManagementFee() {
        return dunManagementFee;
    }

    public void setDunManagementFee(BigDecimal dunManagementFee) {
        this.dunManagementFee = dunManagementFee;
    }

    public BigDecimal getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(BigDecimal membershipFee) {
        this.membershipFee = membershipFee;
    }

    public BigDecimal getDepositExpenses() {
        return depositExpenses;
    }

    public void setDepositExpenses(BigDecimal depositExpenses) {
        this.depositExpenses = depositExpenses;
    }

    public BigDecimal getWithdrawExpenses() {
        return withdrawExpenses;
    }

    public void setWithdrawExpenses(BigDecimal withdrawExpenses) {
        this.withdrawExpenses = withdrawExpenses;
    }

    public BigDecimal getVerifyExpenses() {
        return verifyExpenses;
    }

    public void setVerifyExpenses(BigDecimal verifyExpenses) {
        this.verifyExpenses = verifyExpenses;
    }

    public BigDecimal getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(BigDecimal otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public BigDecimal getMarketingInterestExpenses() {
        return marketingInterestExpenses;
    }

    public void setMarketingInterestExpenses(BigDecimal marketingInterestExpenses) {
        this.marketingInterestExpenses = marketingInterestExpenses;
    }

    public BigDecimal getMarketingOffsetExpenses() {
        return marketingOffsetExpenses;
    }

    public void setMarketingOffsetExpenses(BigDecimal marketingOffsetExpenses) {
        this.marketingOffsetExpenses = marketingOffsetExpenses;
    }

    public BigDecimal getInvestorDeposit() {
        return investorDeposit;
    }

    public void setInvestorDeposit(BigDecimal investorDeposit) {
        this.investorDeposit = investorDeposit;
    }

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getInvestorExpenses() {
        return investorExpenses;
    }

    public void setInvestorExpenses(BigDecimal investorExpenses) {
        this.investorExpenses = investorExpenses;
    }

    public BigDecimal getInvestorReceipts() {
        return investorReceipts;
    }

    public void setInvestorReceipts(BigDecimal investorReceipts) {
        this.investorReceipts = investorReceipts;
    }

    public BigDecimal getTotalReceipts() {
        return totalReceipts;
    }

    public void setTotalReceipts(BigDecimal totalReceipts) {
        this.totalReceipts = totalReceipts;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getFeeExpenses() {
        return feeExpenses;
    }

    public void setFeeExpenses(BigDecimal feeExpenses) {
        this.feeExpenses = feeExpenses;
    }

    public BigDecimal getInterestExpenses() {
        return interestExpenses;
    }

    public void setInterestExpenses(BigDecimal interestExpenses) {
        this.interestExpenses = interestExpenses;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
