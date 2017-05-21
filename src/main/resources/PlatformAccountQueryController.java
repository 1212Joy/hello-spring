package cn.com.fero.backend.payment.controller;

import cn.com.fero.backend.auth.common.AuthCode;
import cn.com.fero.backend.payment.configuration.ApplicationMessage;
import cn.com.fero.backend.payment.dto.*;
import cn.com.fero.backend.payment.service.PlatformAccountService;
import cn.com.fero.backend.payment.utils.RecordUtils;
import cn.com.fero.backend.payment.validator.PlatformAccountQueryValidator;
import cn.com.fero.backend.payment.vo.PlatformAccountRecordVO;
import cn.com.fero.commons.entity.accounts.*;
import cn.com.fero.commons.enums.accounts.RealAccountType;
import cn.com.fero.commons.enums.accounts.VirtualAccountType;
import cn.com.fero.framework.page.pagehelper.PageHelper;
import cn.com.fero.framework.page.pagehelper.PageInfo;
import cn.com.fero.framework.utils.DateUtils;
import cn.com.fero.framework.utils.StringUtils;
import cn.com.fero.framework.web.controller.BaseController;
import cn.com.fero.framework.web.response.JsonResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sp on 2016/2/24.
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "平台交易流水", description = "平台交易相关操作")
public class PlatformAccountQueryController extends BaseController {
    @Autowired
    private PlatformAccountService platformAccountService;

    // 如果保留此方法时间字段类型会被拦截，所以暂时先去掉自动校验方法，改为手动校验 参考@AuthCode(code = "010913")
/*    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new PlatformAccountQueryValidator());
    }*/

    @AuthCode(codes = "010901", required = true)
    @RequestMapping(value = "/accounts/bindCardList/get", method = RequestMethod.GET)
    public JsonResponse getBingCardsByInfoId(@RequestParam Integer userInfoId) {
        if (userInfoId == null) {
            throw new IllegalArgumentException(ApplicationMessage.parameterError("用户ID"));
        }
        Boolean success = true;
        String message = "";
        List<BankAccount> bankAccountList = platformAccountService.getBingCardsByUserInfoId(userInfoId);
        if (bankAccountList.size() < 1) {
            success = false;
            message = ApplicationMessage.queryFailure();
            return new JsonResponse(success, message);
        }
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, bankAccountList);
    }

    @AuthCode(codes = "010902", required = true)
    @RequestMapping(value = "/accounts/tmbbanks/getAll", method = RequestMethod.GET)
    public JsonResponse getTmbBanks() {
        return jsonData(platformAccountService.getTmbBanks());
    }

    @AuthCode(codes = "010903", required = true)
    @RequestMapping(value = "/accounts/userAccount/getById", method = RequestMethod.GET)
    public JsonResponse getUserByInfoId(@RequestParam Integer userInfoId) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        UserAccount userAccount = platformAccountService.getUserAccountsByUserInfoId(userInfoId);
        if (userAccount == null) {
            success = false;
            message = "该用户未开通钱包！";
            return new JsonResponse(success, message, userAccount);
        }
        userAccount.setPayPassword(null);
        userAccount.setPayPasswordSalt(null);
        return new JsonResponse(success, message, userAccount);
    }

    @AuthCode(codes = "010932", required = false)
    @RequestMapping(value = "/accounts/userAccount/info", method = RequestMethod.GET)
    public JsonResponse getUserAccountInfo(@RequestParam Integer userInfoId) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        UserAccount userAccount = platformAccountService.getInfo(userInfoId);
        if (userAccount == null) {
            success = false;
            message = "该用户不存在绑卡信息！";
            return new JsonResponse(success, message, userAccount);
        }
        userAccount.setPayPassword(null);
        userAccount.setPayPasswordSalt(null);
        return new JsonResponse(success, message, userAccount);
    }
    // 此方法被下面的方法替换，增加了查询字段的拓展，注释掉的方法可删
    /*@AuthCode(code = "010904", required = true)
    @RequestMapping(value = "/payment/userAccount/getAll", method = RequestMethod.GET)
    public JsonResponse getAllUserAccount(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "") String createdBy) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        PageHelper.startPage(pageNum, 10);
        List<UserAccountDTO> userAccountList = new ArrayList();
        if (createdBy != null && !StringUtils.isEmpty(createdBy)) {
            UserAccountDTO userAccountDTO = platformAccountService.getUserAccountByCreatedBy(createdBy);
            if (userAccountDTO != null) {
                userAccountList.add(userAccountDTO);
            }
        } else
            userAccountList = platformAccountService.getUserAccountsList();
        PageInfo<UserAccountDTO> userAccountDTOPageInfo = new PageInfo(userAccountList);
        return new JsonResponse(success, message, userAccountDTOPageInfo);
    }*/
    //资金账户-会员账户分账户列表
    @AuthCode(codes = "010904", required = true)
    @RequestMapping(value = "/accounts/userAccount/getAll", method = RequestMethod.GET)
    public JsonResponse getAllUserAccount(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          UserAccountDTO userAccountDTO) {
        return new JsonResponse(ApplicationMessage.querySuccess()
                , platformAccountService.getUserAccountByCriteria(pageNum, userAccountDTO));
    }

    @AuthCode(codes = "010905", required = true)
    @RequestMapping(value = "/accounts/majorAccount/get", method = RequestMethod.GET)
    public JsonResponse getMajorAccount() {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<PlatformMajorAccount> platformMajorAccountList = platformAccountService.getPlatformMajorAccount();
        if (platformMajorAccountList.size() < 1) {
            success = false;
            message = "平台主账号不存在！";
            return new JsonResponse(success, message);
        }

        PlatformMajorAccount platformMajorAccount = platformMajorAccountList.get(0);
        Map platformMajorAccountAmounts = convertPlatformMajorAccount(platformMajorAccount);

        return new JsonResponse(success, message, platformMajorAccountAmounts);
    }

    @AuthCode(codes = "010906", required = true)
    @RequestMapping(value = "/accounts/subAccount/get", method = RequestMethod.GET)
    public JsonResponse getSubAccount(@RequestParam(required = true, defaultValue = "") String subAccountTableName, @RequestParam(required = true, defaultValue = "") String realAccountType) throws ParseException {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<PlatformMajorAccount> platformMajorAccountList = platformAccountService.getPlatformMajorAccount();
        if (platformMajorAccountList.size() < 1) {
            success = false;
            message = "平台主账号不存在！";
            return new JsonResponse(success, message);
        }
        PlatformMajorAccount platformMajorAccount = platformMajorAccountList.get(0);
        Map retMap = convertPlatformMajorAccount(platformMajorAccount);

        BigDecimal incomeAmount = BigDecimal.ZERO;
        BigDecimal payoutAmount = BigDecimal.ZERO;
        if (StringUtils.isBlank(subAccountTableName) && StringUtils.isBlank(realAccountType)) {
            throw new IllegalArgumentException("subAccountTableName参数有误");
        } else if (!StringUtils.isBlank(subAccountTableName)) {
            incomeAmount = platformAccountService.getAccountIncomeAmount(subAccountTableName, null, null);
            payoutAmount = platformAccountService.getAccountPayoutAmount(subAccountTableName, null, null);
        } else if (!StringUtils.isBlank(realAccountType)) {
            String type = RealAccountType.getKey(realAccountType);
            incomeAmount = platformAccountService.getAccountIncomeAmount(null, null, type);
            payoutAmount = platformAccountService.getAccountPayoutAmount(null, null, type);
        }

        retMap.put("incomeAmount", incomeAmount);
        retMap.put("payoutAmount", payoutAmount);
        return new JsonResponse(success, message, retMap);
    }

    @AuthCode(codes = "010907", required = true)
    @RequestMapping(value = "/accounts/userAccount/get", method = RequestMethod.GET)
    public JsonResponse getUserAccount(@RequestParam(required = true, defaultValue = "") String subAccountTableName,
                                       @RequestParam(required = true, defaultValue = "") String subAccountId) throws ParseException {
        if (StringUtils.isBlank(subAccountTableName) || subAccountTableName.length() < 1) {
            throw new IllegalArgumentException("subAccountTableName参数有误");
        }
        if (StringUtils.isBlank(subAccountId) || subAccountId.length() < 1) {
            throw new IllegalArgumentException("subAccountId参数有误");
        }

        Boolean success = true;
        String message = ApplicationMessage.querySuccess();

        BigDecimal incomeAmount = platformAccountService.getAccountIncomeAmount(subAccountTableName, subAccountId, null);
        BigDecimal payoutAmount = platformAccountService.getAccountPayoutAmount(subAccountTableName, subAccountId, null);

        Map detailAmounts = new HashMap();
        detailAmounts.put("incomeAmount", incomeAmount);
        detailAmounts.put("payoutAmount", payoutAmount);
        return new JsonResponse(success, message, detailAmounts);
    }

    /**
     * 资金账户交易明细查询
     *
     * @param pageNum
     * @param subAccountTableName 资金账户类型表
     * @param subAccountId        子账户id
     * @param tradeType           交易类型
     * @param tradeResultCode     交易结果类型
     * @param realAccountType     实体账户类型
     * @param startDateStr        交易开始时间
     * @param endDateStr          交易结束时间
     * @param tradeDate           交易时间 没用参数
     * @return
     * @throws ParseException
     *
     * 资金账户 - 明细表
     * 实体、虚拟账目详情
     * 会员分账户
     */
    @AuthCode(codes = "010908", required = true)
    @RequestMapping(value = "/accounts/record/get", method = RequestMethod.GET)
    public JsonResponse getAccountRecordByCriteria(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "") String subAccountTableName,
                                                   @RequestParam(required = false, defaultValue = "") Integer subAccountId,
                                                   @RequestParam(required = false, defaultValue = "") String tradeType,
                                                   @RequestParam(required = false, defaultValue = "") String tradeResultCode,
                                                   @RequestParam(required = false, defaultValue = "") String realAccountType,
                                                   @RequestParam(required = false, defaultValue = "") String startDateStr,
                                                   @RequestParam(required = false, defaultValue = "") String endDateStr,
                                                   @RequestParam(required = false, defaultValue = "") String tradeDate) throws ParseException {
        Boolean success;
        String message;
        Date startDate;
        Date endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PlatformAccountsRecord platformAccountsRecord = new PlatformAccountsRecord();
        platformAccountsRecord.setResultCode("0000");
        if (subAccountTableName != null && !StringUtils.isEmpty(subAccountTableName))
            platformAccountsRecord.setSubAccountTableName(subAccountTableName);
        if (subAccountId != null && subAccountId > 0)
            platformAccountsRecord.setPlatformSubAccountId(subAccountId);
        if (tradeType != null && !StringUtils.isEmpty(tradeType))
            platformAccountsRecord.setTradeType(tradeType);
        if (tradeResultCode != null && !StringUtils.isEmpty(tradeResultCode))
            platformAccountsRecord.setResultCode(tradeResultCode);
        if (tradeDate != null && StringUtils.isNotBlank(tradeDate)) {
            Date date = sdf.parse(tradeDate);
            platformAccountsRecord.setTradeTime(date);
        }
        if (realAccountType != null && !StringUtils.isEmpty(realAccountType)) {
            platformAccountsRecord.setRealAccountType(RealAccountType.getKey(realAccountType));
        }
        if (startDateStr != null && !StringUtils.isEmpty(startDateStr)) {
            startDate = sdf.parse(startDateStr);

        } else {
            startDate = null;
        }
        if (endDateStr != null && !StringUtils.isEmpty(endDateStr)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(endDateStr));
            cal.add(Calendar.DATE, 1);
            endDate = cal.getTime();
        } else {
            endDate = null;
        }
        PageHelper.startPage(pageNum, 10);
        List platformAccountsRecordList = platformAccountService.getAccountRecordByCriteria(platformAccountsRecord, startDate, endDate);
        PageInfo platformAccountsRecordPageInfo = new PageInfo(platformAccountsRecordList);


        List<PlatformAccountRecordVO> platformAccountRecordVOList = convertPlatformRecord(platformAccountsRecordList);
        platformAccountsRecordPageInfo.setList(platformAccountRecordVOList);
        success = true;
        message = ApplicationMessage.querySuccess();

        return new JsonResponse(success, message, platformAccountsRecordPageInfo);
    }

    @AuthCode(codes = "010909", required = true)
    @RequestMapping(value = "/accounts/daily/get", method = RequestMethod.GET)
    public JsonResponse getRealAccountDailyAmount(@RequestParam(required = false) String recordDate,
                                                  @RequestParam(required = true) String realAccountType) {
        Boolean success;
        String message;
        String recordDateTem = StringUtils.isEmpty(recordDate) ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)):recordDate;
        if (StringUtils.isBlank(realAccountType) || realAccountType.length() < 1) {
            throw new IllegalArgumentException("realAccountType参数有误");
        }
        RealAccountsDailySettlement realAccountsDailySettlement = platformAccountService.getRealAccountDailyAmount(recordDateTem, RealAccountType.getKey(realAccountType));
        if (realAccountsDailySettlement == null) {
            success = false;
            message = ApplicationMessage.queryFailure();
            return new JsonResponse(success, message);
        }
        success = true;
        message = ApplicationMessage.querySuccess();
        Map detailAmounts = new HashMap();
        detailAmounts.put("dayBalance", realAccountsDailySettlement.getDayBalance());
        detailAmounts.put("dayIncome", realAccountsDailySettlement.getDayIncome());
        detailAmounts.put("dayPayout", realAccountsDailySettlement.getDayPayout());
        return new JsonResponse(success, message, detailAmounts);
    }

    @AuthCode(codes = "010911", required = true)
    @RequestMapping(value = "/accounts/month/get", method = RequestMethod.GET)
    public JsonResponse getRealAccountMonthAmount(@RequestParam(required = false) String recordDate,
                                                  @RequestParam(required = true) String realAccountType) {
        Boolean success;
        String message;
        String DATE_FORMAT_REGX = "^\\w{4}-\\w{1,2}$";
        if (!recordDate.trim().matches(DATE_FORMAT_REGX)) {
            throw new IllegalArgumentException("时间格式不正确");
        }
        if (StringUtils.isBlank(realAccountType) || realAccountType.length() < 1) {
            throw new IllegalArgumentException("realAccountType参数有误");
        }
        String recordDateTem = StringUtils.isEmpty(recordDate) ? new SimpleDateFormat("yyyy-MM").format(DateUtils.now()):recordDate;

        List<RealAccountsDailySettlement> realAccountsDailySettlementList = platformAccountService.getRealAccountMouthAmount(recordDateTem,  RealAccountType.getKey(realAccountType));
        if (realAccountsDailySettlementList == null) {
            success = false;
            message = ApplicationMessage.queryFailure();
            return new JsonResponse(success, message);
        }
        success = true;
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, realAccountsDailySettlementList);
    }
    //资金账户 - 实体账户日结详情
    @AuthCode(codes = "010910", required = true)
    @RequestMapping(value = "/accounts/daily/record/get", method = RequestMethod.GET)
    public JsonResponse getRealAccountDailyRecord(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(required = false) String recordDate,
                                                  @RequestParam(required = true) String realAccountType) throws ParseException {

        Boolean success;
        String message;
        if (StringUtils.isBlank(realAccountType) || realAccountType.length() < 1) {
            throw new IllegalArgumentException("realAccountType参数有误");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //默认查询当前日期前一天的记录
        String recordDateTem = StringUtils.isEmpty(recordDate) ? simpleDateFormat.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000): recordDate;
        Date endDate = simpleDateFormat.parse(recordDateTem);
        String startDateStr = recordDateTem;
        String endDateStr = simpleDateFormat.format(new Date(endDate.getTime() + 1 * 24 * 60 * 60 * 1000));
        PageHelper.startPage(pageNum, 10);
        List platformAccountsRecordList = platformAccountService.getDailyRecordByCriteria(startDateStr, endDateStr, RealAccountType.getKey(realAccountType));
        PageInfo platformAccountRecordVOPageInfo = new PageInfo(platformAccountsRecordList);
       /*
        if (platformAccountsRecordList.size() < 1) {
            success = false;
            message = ApplicationMessage.queryFailure();
            return new JsonResponse(success, message);
        }
        */

        List<PlatformAccountRecordVO> platformAccountRecordVOList = convertPlatformRecord(platformAccountsRecordList);
        platformAccountRecordVOPageInfo.setList(platformAccountRecordVOList);

        success = true;
        message = ApplicationMessage.querySuccess();

        return new JsonResponse(success, message, platformAccountRecordVOPageInfo);
    }

    @AuthCode(codes = "010913", required = true)
    @RequestMapping(value = "/accounts/person/record", method = RequestMethod.GET)
    public JsonResponse getAccountRecordByCriteria(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                   PlatformAccountRecordVO recordVO) throws ParseException {
        PlatformAccountQueryValidator.validatePersonRecord(recordVO);
        return JsonResponse.newInstance().setData(platformAccountService.getPersonRecord(pageNum, recordVO));
    }

    @AuthCode(codes = "010914", required = true)
    @RequestMapping(value = "/accounts/person/record/sum", method = RequestMethod.GET)
    public JsonResponse getRecordsAmountSum(PlatformAccountRecordVO recordVO) throws ParseException {
        PlatformAccountQueryValidator.validatePersonRecord(recordVO);
        return JsonResponse.newInstance().setData(platformAccountService.getRecordsAmountSum(recordVO));
    }

    @AuthCode(codes = "010915", required = true)
    @RequestMapping(value = "/accounts/static/virtualAccount/income", method = RequestMethod.GET)
    public JsonResponse getVAccountIncomeStatistic(@RequestParam(required = true) String subAccountTableName) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<AccountIncomeStatisticDTO> accountIncomeStatisticLst = platformAccountService.getAccountIncomeStatistic(subAccountTableName, null);
        return new JsonResponse(success, message, accountIncomeStatisticLst);
    }

    @AuthCode(codes = "010916", required = true)
    @RequestMapping(value = "/accounts/static/realAccount/income", method = RequestMethod.GET)
    public JsonResponse getRAccountIncomeStatistic(@RequestParam(required = true) String realAccountType) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<AccountIncomeStatisticDTO> accountIncomeStatisticLst = platformAccountService.getAccountIncomeStatistic(null, RealAccountType.getKey(realAccountType));
        return new JsonResponse(success, message, accountIncomeStatisticLst);
    }

    @AuthCode(codes = "010917", required = true)
    @RequestMapping(value = "/accounts/static/virtualAccount/payout", method = RequestMethod.GET)
    public JsonResponse getVAccountPayoutStatistic(@RequestParam(required = true) String subAccountTableName) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<AccountIncomeStatisticDTO> accountIncomeStatisticLst = platformAccountService.getAccountPayoutStatistic(subAccountTableName, null);
        return new JsonResponse(success, message, accountIncomeStatisticLst);
    }

    @AuthCode(codes = "010918", required = true)
    @RequestMapping(value = "/accounts/static/realAccount/payout", method = RequestMethod.GET)
    public JsonResponse getRAccountPayoutStatistic(@RequestParam(required = true) String realAccountType) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<AccountIncomeStatisticDTO> accountIncomeStatisticLst = platformAccountService.getAccountPayoutStatistic(null, RealAccountType.getKey(realAccountType));
        return new JsonResponse(success, message, accountIncomeStatisticLst);
    }

    //资金账户明细表 -  会员账户池列表
    @AuthCode(codes = "010919", required = false)
    @RequestMapping(value = "/accounts/record/get/userAccount", method = RequestMethod.GET)
    public JsonResponse getUserAccountRecords(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false) String startDateStr,
                                              @RequestParam(required = false) String endDateStr) throws ParseException {
        Boolean success;
        String message;
        Date startDate;
        Date endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PlatformAccountsRecord platformAccountsRecord = new PlatformAccountsRecord();
        platformAccountsRecord.setResultCode("0000");
        platformAccountsRecord.setSubAccountTableName("user_accounts");
        if (startDateStr != null && !StringUtils.isEmpty(startDateStr)) {
            startDate = sdf.parse(startDateStr);

        } else {
            startDate = null;
        }
        if (endDateStr != null && !StringUtils.isEmpty(endDateStr)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(endDateStr));
            cal.add(Calendar.DATE, 1);
            endDate = cal.getTime();
        } else {
            endDate = null;
        }
        PageHelper.startPage(pageNum, 10);
        List platformAccountsRecordList = platformAccountService.getUserAccountsDetails(platformAccountsRecord, startDate, endDate);
        PageInfo platformAccountsRecordPageInfo = new PageInfo(platformAccountsRecordList);

        List<PlatformAccountRecordVO> platformAccountRecordVOList = convertPlatformRecordDTO(platformAccountsRecordList, Boolean.TRUE);
        platformAccountsRecordPageInfo.setList(platformAccountRecordVOList);
        success = true;
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, platformAccountsRecordPageInfo);
    }

    @AuthCode(codes = "010920", required = false)
    @RequestMapping(value = "/accounts/userAccount/getByCreatedBy", method = RequestMethod.GET)
    public JsonResponse getUserAccountByCreatedBy(String createdBy) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, platformAccountService.getUserAccountByCreatedBy(createdBy));
    }
    @AuthCode(codes = "010921", required = false)
    @RequestMapping(value = "/accounts/pad/statistic/IOAmount", method = RequestMethod.GET)
    public JsonResponse getIOAmount4Pad(@RequestParam(required = false) String recordDate) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();

        RealAccountsDailySettlement realAccountsDailySettlement = platformAccountService.getIOAmount();
        if (realAccountsDailySettlement == null) {
            success = false;
            message = ApplicationMessage.queryFailure();
            return new JsonResponse(success, message);
        }

        Map retMap = new HashMap();
        retMap.put("dayPayout", realAccountsDailySettlement.getDayPayout());
        retMap.put("dayIncome", realAccountsDailySettlement.getDayIncome());
        retMap.put("netIncome", realAccountsDailySettlement.getDayIncome().add(realAccountsDailySettlement.getDayPayout()));

        return new JsonResponse(success, message, retMap);
    }
    @AuthCode(codes = "010922", required = false)
    @RequestMapping(value = "/accounts/pad/balance", method = RequestMethod.GET)
    public JsonResponse getPadBalance() {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<PlatformMajorAccount> platformMajorAccountList = platformAccountService.getPlatformMajorAccount();
        if (platformMajorAccountList.size() < 1) {
            success = false;
            message = "平台主账号不存在！";
            return new JsonResponse(success, message);
        }

        PlatformMajorAccount platformMajorAccount = platformMajorAccountList.get(0);
        Map platformMajorAccountAmounts = convertPlatformMajorAccount(platformMajorAccount);

        return new JsonResponse(success, message, platformMajorAccountAmounts);
    }
    @AuthCode(codes = "010923", required = false)
    @RequestMapping(value = "/accounts/pad/statistic/byDay", method = RequestMethod.GET)
    public JsonResponse getPadStatisticByDay(@RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        PageHelper.startPage(pageNum, 30);
        List<PlatformStatisticDTO> platformStatisticDTOs = platformAccountService.getStatisticByDay();
        PageInfo<PlatformStatisticDTO> platformStatisticDTOsPageInfo = new PageInfo(platformStatisticDTOs);
        if (platformStatisticDTOsPageInfo.getSize() < 1) {
            success = false;
            message = "暂无记录！";
            return new JsonResponse(success, message);
        }

        return new JsonResponse(success, message, platformStatisticDTOsPageInfo);
    }
    @AuthCode(codes = "010924", required = false)
    @RequestMapping(value = "/accounts/pad/statistic/byMonth", method = RequestMethod.GET)
    public JsonResponse getPadStatisticByMonth() {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        List<PlatformStatisticDTO> platformStatisticDTOs = platformAccountService.getStatisticByMonth();
        if (platformStatisticDTOs.size() < 1) {
            success = false;
            message = "暂无记录！";
            return new JsonResponse(success, message);
        }

        return new JsonResponse(success, message, platformStatisticDTOs);
    }
    @AuthCode(codes = "010925", required = true)
    @RequestMapping(value = "/accounts/getDifference", method = RequestMethod.GET)
    public JsonResponse getDifference() {
        return JsonResponse.newInstance().setData(platformAccountService.getDifference());
    }

    @AuthCode(codes = "010926", required = true)
    @RequestMapping(value = "/accounts/getThirdSummary", method = RequestMethod.GET)
    public JsonResponse getThirdSummary(@RequestParam(required = false, defaultValue = "1") Integer pageNum, @RequestParam(required = false) String startDateStr,
                                        @RequestParam(required = false) String endDateStr) throws Exception {
        Date stratDate = null;
        Date endDate = null;
        try {
            if (!StringUtils.isBlank(startDateStr)) {
                stratDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
            }

            if (!StringUtils.isBlank(endDateStr)) {
                endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
                endDate = DateUtils.addHours(endDate, 23);
                endDate = DateUtils.addMinutes(endDate, 59);
                endDate = DateUtils.addSeconds(endDate, 59);
            }
        } catch (Exception e) {
            log.info("[PlatformAccountQueryController]-[getThirdSummary] 异常 : {}",e);
            throw new IllegalArgumentException("日期格式不正确");
        }

        return JsonResponse.newInstance().setData(platformAccountService.getThirdSummary(pageNum, stratDate, endDate));
    }

    @AuthCode(codes = "010927", required = true)
    @RequestMapping(value = "/accounts/record/get/depositOrWithdrawRecords", method = RequestMethod.GET)
    public JsonResponse getUserRecordsByTradeType(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(required = true) String tradeType,
                                                  @RequestParam(required = false, defaultValue = "") String status,
                                                  @RequestParam(required = false) String startDateStr,
                                                  @RequestParam(required = false) String endDateStr) throws ParseException {
        Boolean success;
        String message;
        Date startDate;
        Date endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (startDateStr != null && !StringUtils.isEmpty(startDateStr)) {
            startDate = sdf.parse(startDateStr);
        } else {
            startDate = null;
        }
        if (endDateStr != null && !StringUtils.isEmpty(endDateStr)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(endDateStr));
            cal.add(Calendar.DATE, 1);
            endDate = cal.getTime();
        } else {
            endDate = null;
        }
        String statusTem = StringUtils.isEmpty(status) ? null :status;
        PageHelper.startPage(pageNum, 10);
        List<PlatformAccountsRecordDTO> platformAccountsRecordList = platformAccountService.getRecordsByTradeType(tradeType, "user_accounts", statusTem, startDate, endDate);
        PageInfo<PlatformAccountsRecordDTO> platformAccountsRecordPageInfo = new PageInfo(platformAccountsRecordList);
        platformAccountsRecordPageInfo.setList(platformAccountsRecordList);
        success = true;
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, platformAccountsRecordPageInfo);
    }

    @AuthCode(codes = "010928", required = false)
    @RequestMapping(value = "/finacing/accounts/list", method = RequestMethod.GET)
    public JsonResponse getFinacingAcctList(@RequestParam(required = true) String projectIds) {
        return jsonData(platformAccountService.getFinacingAcctListByProjectID(projectIds));
    }

    @RequestMapping(value = "/accounts/noValidation/finacing/list", method = RequestMethod.GET)
    public JsonResponse getFinacingAcctListJob(@RequestParam(required = true) String projectIds) {
        return jsonData(platformAccountService.getFinacingAcctListByProjectID(projectIds));
    }

    //平台报表 - 正常到期收入明细表（显示）
    @AuthCode(codes = "010929", required = true)
    @RequestMapping(value = "/accounts/dueProfitDetails", method = RequestMethod.GET)
    public JsonResponse getDueProfitRecords(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "") String dateStr) throws Exception {
        Boolean success;
        String message;
        String dateStrTem = StringUtils.isEmpty(dateStr) ? null:new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyy-MM").parse(dateStr));
        PageHelper.startPage(pageNum, 10);
        List platformAccountsRecordList = platformAccountService.dueProfitDetails(dateStrTem);
        PageInfo platformAccountsRecordPageInfo = new PageInfo(platformAccountsRecordList);

        List<PlatformAccountRecordVO> platformAccountRecordVOList = convertPlatformRecordDTO(platformAccountsRecordList, Boolean.FALSE);
        platformAccountsRecordPageInfo.setList(platformAccountRecordVOList);
        success = true;
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, platformAccountsRecordPageInfo);
    }

    //平台报表 - 业务收益表（显示）
    @AuthCode(codes = "010930", required = true)
    @RequestMapping(value = "/accounts/businessReceipts", method = RequestMethod.GET)
    public JsonResponse getBusinessReceipts(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = true) String token) throws Exception {
        Boolean success;
        String message;
        List<BusinessReceiptsDTO> businessReceiptsDTOList = platformAccountService.getBusinessReceipts(token);
        success = true;
        message = ApplicationMessage.querySuccess();
        return new JsonResponse(success, message, businessReceiptsDTOList);
    }

    @AuthCode(codes = "010931", required = false)
    @RequestMapping(value = "/accounts/platformRecords", method = RequestMethod.GET)
    public JsonResponse getAllPlatformRecords(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = true) String tradeType,
                                              @RequestParam(required = false) String startDateStr,
                                              @RequestParam(required = false) String endDateStr) throws ParseException {
        Boolean success = true;
        String message = ApplicationMessage.querySuccess();
        Date startDate;
        Date endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (startDateStr != null && !StringUtils.isEmpty(startDateStr)) {
            startDate = sdf.parse(startDateStr);
        } else {
            startDate = null;
        }
        if (endDateStr != null && !StringUtils.isEmpty(endDateStr)) {
            endDate = sdf.parse(endDateStr);
        } else {
            endDate = null;
        }
        PageHelper.startPage(pageNum, 10);
        List<PlatformAccountsRecordDTO> platformAccountsRecordList = platformAccountService.getRecordsByTradeType(tradeType, "user_accounts", null, startDate, endDate);
        PageInfo<UserAccountDTO> recordsInfo = new PageInfo(platformAccountsRecordList);
        return new JsonResponse(success, message, recordsInfo);
    }

    @RequestMapping(value = "/accounts/info/LoanAccountInfo", method = RequestMethod.GET)
    public JsonResponse getLoanAcct(@RequestParam(required = true) Integer projectId) {
        if(projectId == null || projectId<0){
            throw new IllegalArgumentException("projectId参数有误");
        }
        return jsonData(platformAccountService.getLoanAccountInfo(projectId));
    }


    private Map convertPlatformMajorAccount(PlatformMajorAccount platformMajorAccount) {
        Map platformMajorAccountAmounts = new HashMap();
        platformMajorAccountAmounts.put(VirtualAccountType.USER_ACCOUNT.getValue(),
                platformMajorAccount.getUserAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.LOAN_ACCOUNT.getValue(),
                platformMajorAccount.getLoanAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.FINANCING_ACCOUNT.getValue(),
                platformMajorAccount.getFinancingAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.COMPENSATE_DEBT_ACCOUNT.getValue(),
                platformMajorAccount.getCompensateDebtAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.COMPENSATORY_ACCOUNT.getValue(),
                platformMajorAccount.getCompensatoryAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.PETTY_LOAN_ACCOUNT.getValue(),
                platformMajorAccount.getPettyLoanAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.DEBT_ACCOUNT.getValue(),
                platformMajorAccount.getDebtAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.PROFIT_ACCOUNT.getValue(),
                platformMajorAccount.getProfitAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.MARKETING_ACCOUNT.getValue(),
                platformMajorAccount.getMarketingAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.FINANCING_MARKETING_ACCOUNT.getValue(),
                platformMajorAccount.getFinancingMarketingAccountAmount());
        platformMajorAccountAmounts.put(VirtualAccountType.PLEDGE_ACCOUNT_AMOUNT.getValue(),
                platformMajorAccount.getPledgeAccountAmount());

        // 计算实体账户金额
        platformMajorAccountAmounts.put(RealAccountType.DEPOSITORY_ACCOUNT.getValue(),
                platformMajorAccount.getUserAccountAmount().add(platformMajorAccount.getFinancingAccountAmount()
                        .add(platformMajorAccount.getLoanAccountAmount()).add(platformMajorAccount.getPledgeAccountAmount())));
        platformMajorAccountAmounts.put(RealAccountType.COMPENSATORY_ACCOUNT.getValue(),
                platformMajorAccount.getCompensateDebtAccountAmount().add(platformMajorAccount.getCompensatoryAccountAmount()));
        platformMajorAccountAmounts.put(RealAccountType.PETTY_LOAN_ACCOUNT.getValue(),
                platformMajorAccount.getDebtAccountAmount().add(platformMajorAccount.getPettyLoanAmount()));
        platformMajorAccountAmounts.put(RealAccountType.PLATFORM_ACCOUNT.getValue(),
                platformMajorAccount.getProfitAccountAmount().add(platformMajorAccount.getMarketingAccountAmount())
                        .add(platformMajorAccount.getFinancingMarketingAccountAmount()));
        return platformMajorAccountAmounts;
    }


    public List<PlatformAccountRecordVO> convertPlatformRecord(List<PlatformAccountsRecord> platformAccountsRecordList) {
        return RecordUtils.convertPlatformRecord(platformAccountsRecordList);
    }

    /**
     * 处理交易记录涉及的用户,理财标的,贷款编号信息
     * 个人理财相关返回会员姓名 , 贷款相关返回 贷款主体
     *
     * @param platformAccountsRecordList 交易记录
     * @param isUserAcct                 true - 会员交易记录
     * @return 处理后的记录
     */
    private List<PlatformAccountRecordVO> convertPlatformRecordDTO(List<PlatformAccountsRecordDTO> platformAccountsRecordList, Boolean isUserAcct) {
        return RecordUtils.convertPlatformRecordDTO(platformAccountsRecordList, isUserAcct);
    }
}
