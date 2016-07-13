package cn.com.basic.dto;

/**
 * Created by zhaijiayi on 2016/7/4.
 */
public class PlatformRecordReservedField {
    //理财标的
    private String subjectId;
    private String subjectCode;
    private String subjectDesc;

    //贷款
    private String projectId;
    private String projectCode;
    //用户
    private String userInfoID;
    private String userRealName;
    //提现
    private String subRecordId;
    private String requestIp;
    private String canWithdrawAmount;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getUserInfoID() {
        return userInfoID;
    }

    public void setUserInfoID(String userInfoID) {
        this.userInfoID = userInfoID;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getSubRecordId() {
        return subRecordId;
    }

    public void setSubRecordId(String subRecordId) {
        this.subRecordId = subRecordId;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getCanWithdrawAmount() {
        return canWithdrawAmount;
    }

    public void setCanWithdrawAmount(String canWithdrawAmount) {
        this.canWithdrawAmount = canWithdrawAmount;
    }
}
