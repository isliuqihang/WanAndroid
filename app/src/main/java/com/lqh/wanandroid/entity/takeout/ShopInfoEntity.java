package com.lqh.wanandroid.entity.takeout;

import java.util.List;

/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20210810
 *     update: 0810
 *     version:1.0
 * </pre>
 */

public class ShopInfoEntity {

    private int canteen_id;
    private int freightType;

    /**
     * 打印机打印份数
     */
    private int storePrintTimes = 1;

    public int getStorePrintTimes() {
        return storePrintTimes;
    }

    public void setStorePrintTimes(int storePrintTimes) {
        this.storePrintTimes = storePrintTimes;
    }

    private int pay_channel_type;
    private String printSn;
    private int school_id;
    private int showType;

    private String storeAddress;
    private int storeAgentId;
    private int storeAuditState;
    /**
     * 店铺配送方式
     * 0---商家配送
     * 1---平台配送
     */
    private int storeAutoDispatch = 0;
    /**
     * 店铺接单模式
     */
    private int storeAutoReceipt;
    private int storeBasicFreight;
    private int storeCatchOnStoreSelf;
    private String storeCity;
    private String storeCounty;
    private int storeCurrentOpenState;
    private int storeEnterpriseAuth;
    private int storeFreeFreightAmount;
    private long storeGct;
    private long storeGmt;
    private int storeId = -1;
    private int storeIsKillOpen;
    private int storeLeve;
    private double storeLocalLat;
    private double storeLocalLon;
    private String storeLoginNumber;
    private String storeLogo;
    private int storeMaxDeliveryDistance;
    private int storeMinGoodsAmount;
    private String storeName = "";
    private String storeNotice = "";
    private int storeOpenState;
    private long storeOpenTime;
    private long storeOverdueTime;
    private int storePercentage;
    private int storePersonalAuth;
    private String storePhone;
    private String storePics;
    private int storePower;
    private String storePrinterNumber;
    private String storeProvince;
    private int storeReject;
    private int storeSaleCount;
    private int storeSettlementDelay;
    private int storeShowType;
    private int storeStationId;
    private int storeTotalCommentCount;
    private int storeTotalOrderCount;
    private double storeTotalScore;
    private String storeTypeName;
    private int storeWeightScore;
    private int store_Separate;
    private String store_code;
    private String store_delivery_status;
    private int store_invite_status;
    private int store_sweep_code;
    private String sxf_account_id;
    private String sxf_mch_id;
    private String sxf_shop_id;


    /**
     * 骑手配送时间
     */
    private int riderDeliveryTime = 15;
    /**
     * 可预约日期
     * 0、今天 1、明天 2、后天
     */
    private String acrossDays = "{\"delivery\":\"0\",\"selfAccess\":\"0\"}";
    /**
     * 备餐时间
     */
    private int mealPreparationTime = 15;

    /**
     * 商家配送时间
     */
    private int selfDeliveryTime = 15;

    /**
     * 订单配送类型（0、尽快送达 1、预订单 2、尽快送达和预订单）
     */
    private int deliveryType = 0;

    public int getSelfAccessType() {
        return selfAccessType;
    }

    public void setSelfAccessType(int selfAccessType) {
        this.selfAccessType = selfAccessType;
    }

    /**
     * 自取的类型
     * 自取类型（-1、关闭0、立即自取 1、预约自取2、立即自取和预约自取）
     */
    private int selfAccessType = 0;

    public int getRiderDeliveryTime() {
        return riderDeliveryTime;
    }

    public void setRiderDeliveryTime(int riderDeliveryTime) {
        this.riderDeliveryTime = riderDeliveryTime;
    }

    public String getAcrossDays() {

        if ("0".equals(acrossDays)) {
            return "{\"delivery\":\"0\",\"selfAccess\":\"0\"}";
        }
        return acrossDays;
    }

    public void setAcrossDays(String acrossDays) {
        this.acrossDays = acrossDays;
    }

    public int getMealPreparationTime() {
        return mealPreparationTime;
    }

    public void setMealPreparationTime(int mealPreparationTime) {
        this.mealPreparationTime = mealPreparationTime;
    }

    public int getSelfDeliveryTime() {
        return selfDeliveryTime;
    }

    public void setSelfDeliveryTime(int selfDeliveryTime) {
        this.selfDeliveryTime = selfDeliveryTime;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    private String storeType;

    private List<OpeninfosBean> openinfos;

    public int getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(int canteen_id) {
        this.canteen_id = canteen_id;
    }

    public int getFreightType() {
        return freightType;
    }

    public void setFreightType(int freightType) {
        this.freightType = freightType;
    }

    public int getPay_channel_type() {
        return pay_channel_type;
    }

    public void setPay_channel_type(int pay_channel_type) {
        this.pay_channel_type = pay_channel_type;
    }

    public String getPrintSn() {
        return printSn;
    }

    public void setPrintSn(String printSn) {
        this.printSn = printSn;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public int getStoreAgentId() {
        return storeAgentId;
    }

    public void setStoreAgentId(int storeAgentId) {
        this.storeAgentId = storeAgentId;
    }

    public int getStoreAuditState() {
        return storeAuditState;
    }

    public void setStoreAuditState(int storeAuditState) {
        this.storeAuditState = storeAuditState;
    }

    public int getStoreAutoDispatch() {
        return storeAutoDispatch;
    }

    public void setStoreAutoDispatch(int storeAutoDispatch) {
        this.storeAutoDispatch = storeAutoDispatch;
    }

    public int getStoreAutoReceipt() {
        return storeAutoReceipt;
    }

    public void setStoreAutoReceipt(int storeAutoReceipt) {
        this.storeAutoReceipt = storeAutoReceipt;
    }

    public int getStoreBasicFreight() {
        return storeBasicFreight;
    }

    public void setStoreBasicFreight(int storeBasicFreight) {
        this.storeBasicFreight = storeBasicFreight;
    }

    public int getStoreCatchOnStoreSelf() {
        return storeCatchOnStoreSelf;
    }

    public void setStoreCatchOnStoreSelf(int storeCatchOnStoreSelf) {
        this.storeCatchOnStoreSelf = storeCatchOnStoreSelf;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreCounty() {
        return storeCounty;
    }

    public void setStoreCounty(String storeCounty) {
        this.storeCounty = storeCounty;
    }

    public int getStoreCurrentOpenState() {
        return storeCurrentOpenState;
    }

    public void setStoreCurrentOpenState(int storeCurrentOpenState) {
        this.storeCurrentOpenState = storeCurrentOpenState;
    }

    public int getStoreEnterpriseAuth() {
        return storeEnterpriseAuth;
    }

    public void setStoreEnterpriseAuth(int storeEnterpriseAuth) {
        this.storeEnterpriseAuth = storeEnterpriseAuth;
    }

    public int getStoreFreeFreightAmount() {
        return storeFreeFreightAmount;
    }

    public void setStoreFreeFreightAmount(int storeFreeFreightAmount) {
        this.storeFreeFreightAmount = storeFreeFreightAmount;
    }

    public long getStoreGct() {
        return storeGct;
    }

    public void setStoreGct(long storeGct) {
        this.storeGct = storeGct;
    }

    public long getStoreGmt() {
        return storeGmt;
    }

    public void setStoreGmt(long storeGmt) {
        this.storeGmt = storeGmt;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getStoreIsKillOpen() {
        return storeIsKillOpen;
    }

    public void setStoreIsKillOpen(int storeIsKillOpen) {
        this.storeIsKillOpen = storeIsKillOpen;
    }

    public int getStoreLeve() {
        return storeLeve;
    }

    public void setStoreLeve(int storeLeve) {
        this.storeLeve = storeLeve;
    }

    public double getStoreLocalLat() {
        return storeLocalLat;
    }

    public void setStoreLocalLat(double storeLocalLat) {
        this.storeLocalLat = storeLocalLat;
    }

    public double getStoreLocalLon() {
        return storeLocalLon;
    }

    public void setStoreLocalLon(double storeLocalLon) {
        this.storeLocalLon = storeLocalLon;
    }

    public String getStoreLoginNumber() {
        return storeLoginNumber;
    }

    public void setStoreLoginNumber(String storeLoginNumber) {
        this.storeLoginNumber = storeLoginNumber;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public int getStoreMaxDeliveryDistance() {
        return storeMaxDeliveryDistance;
    }

    public void setStoreMaxDeliveryDistance(int storeMaxDeliveryDistance) {
        this.storeMaxDeliveryDistance = storeMaxDeliveryDistance;
    }

    public int getStoreMinGoodsAmount() {
        return storeMinGoodsAmount;
    }

    public void setStoreMinGoodsAmount(int storeMinGoodsAmount) {
        this.storeMinGoodsAmount = storeMinGoodsAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNotice() {
        return storeNotice;
    }

    public void setStoreNotice(String storeNotice) {
        this.storeNotice = storeNotice;
    }

    public int getStoreOpenState() {
        return storeOpenState;
    }

    public void setStoreOpenState(int storeOpenState) {
        this.storeOpenState = storeOpenState;
    }

    public long getStoreOpenTime() {
        return storeOpenTime;
    }

    public void setStoreOpenTime(long storeOpenTime) {
        this.storeOpenTime = storeOpenTime;
    }

    public long getStoreOverdueTime() {
        return storeOverdueTime;
    }

    public void setStoreOverdueTime(long storeOverdueTime) {
        this.storeOverdueTime = storeOverdueTime;
    }

    public int getStorePercentage() {
        return storePercentage;
    }

    public void setStorePercentage(int storePercentage) {
        this.storePercentage = storePercentage;
    }

    public int getStorePersonalAuth() {
        return storePersonalAuth;
    }

    public void setStorePersonalAuth(int storePersonalAuth) {
        this.storePersonalAuth = storePersonalAuth;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStorePics() {
        return storePics;
    }

    public void setStorePics(String storePics) {
        this.storePics = storePics;
    }

    public int getStorePower() {
        return storePower;
    }

    public void setStorePower(int storePower) {
        this.storePower = storePower;
    }

    public String getStorePrinterNumber() {
        return storePrinterNumber;
    }

    public void setStorePrinterNumber(String storePrinterNumber) {
        this.storePrinterNumber = storePrinterNumber;
    }

    public String getStoreProvince() {
        return storeProvince;
    }

    public void setStoreProvince(String storeProvince) {
        this.storeProvince = storeProvince;
    }

    public int getStoreReject() {
        return storeReject;
    }

    public void setStoreReject(int storeReject) {
        this.storeReject = storeReject;
    }

    public int getStoreSaleCount() {
        return storeSaleCount;
    }

    public void setStoreSaleCount(int storeSaleCount) {
        this.storeSaleCount = storeSaleCount;
    }

    public int getStoreSettlementDelay() {
        return storeSettlementDelay;
    }

    public void setStoreSettlementDelay(int storeSettlementDelay) {
        this.storeSettlementDelay = storeSettlementDelay;
    }

    public int getStoreShowType() {
        return storeShowType;
    }

    public void setStoreShowType(int storeShowType) {
        this.storeShowType = storeShowType;
    }

    public int getStoreStationId() {
        return storeStationId;
    }

    public void setStoreStationId(int storeStationId) {
        this.storeStationId = storeStationId;
    }

    public int getStoreTotalCommentCount() {
        return storeTotalCommentCount;
    }

    public void setStoreTotalCommentCount(int storeTotalCommentCount) {
        this.storeTotalCommentCount = storeTotalCommentCount;
    }

    public int getStoreTotalOrderCount() {
        return storeTotalOrderCount;
    }

    public void setStoreTotalOrderCount(int storeTotalOrderCount) {
        this.storeTotalOrderCount = storeTotalOrderCount;
    }

    public double getStoreTotalScore() {
        return storeTotalScore;
    }

    public void setStoreTotalScore(double storeTotalScore) {
        this.storeTotalScore = storeTotalScore;
    }

    public String getStoreTypeName() {
        return storeTypeName;
    }

    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public int getStoreWeightScore() {
        return storeWeightScore;
    }

    public void setStoreWeightScore(int storeWeightScore) {
        this.storeWeightScore = storeWeightScore;
    }

    public int getStore_Separate() {
        return store_Separate;
    }

    public void setStore_Separate(int store_Separate) {
        this.store_Separate = store_Separate;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_delivery_status() {
        return store_delivery_status;
    }

    public void setStore_delivery_status(String store_delivery_status) {
        this.store_delivery_status = store_delivery_status;
    }

    public int getStore_invite_status() {
        return store_invite_status;
    }

    public void setStore_invite_status(int store_invite_status) {
        this.store_invite_status = store_invite_status;
    }

    public int getStore_sweep_code() {
        return store_sweep_code;
    }

    public void setStore_sweep_code(int store_sweep_code) {
        this.store_sweep_code = store_sweep_code;
    }

    public String getSxf_account_id() {
        return sxf_account_id;
    }

    public void setSxf_account_id(String sxf_account_id) {
        this.sxf_account_id = sxf_account_id;
    }

    public String getSxf_mch_id() {
        return sxf_mch_id;
    }

    public void setSxf_mch_id(String sxf_mch_id) {
        this.sxf_mch_id = sxf_mch_id;
    }

    public String getSxf_shop_id() {
        return sxf_shop_id;
    }

    public void setSxf_shop_id(String sxf_shop_id) {
        this.sxf_shop_id = sxf_shop_id;
    }

    public List<OpeninfosBean> getOpeninfos() {
        return openinfos;
    }

    public void setOpeninfos(List<OpeninfosBean> openinfos) {
        this.openinfos = openinfos;
    }

    @Override
    public String toString() {
        return "ShopInfoModle{" +
                "canteen_id=" + canteen_id +
                ", freightType=" + freightType +
                ", pay_channel_type=" + pay_channel_type +
                ", printSn='" + printSn + '\'' +
                ", school_id=" + school_id +
                ", showType=" + showType +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeAgentId=" + storeAgentId +
                ", storeAuditState=" + storeAuditState +
                ", storeAutoDispatch=" + storeAutoDispatch +
                ", storeAutoReceipt=" + storeAutoReceipt +
                ", storeBasicFreight=" + storeBasicFreight +
                ", storeCatchOnStoreSelf=" + storeCatchOnStoreSelf +
                ", storeCity='" + storeCity + '\'' +
                ", storeCounty='" + storeCounty + '\'' +
                ", storeCurrentOpenState=" + storeCurrentOpenState +
                ", storeEnterpriseAuth=" + storeEnterpriseAuth +
                ", storeFreeFreightAmount=" + storeFreeFreightAmount +
                ", storeGct=" + storeGct +
                ", storeGmt=" + storeGmt +
                ", storeId=" + storeId +
                ", storeIsKillOpen=" + storeIsKillOpen +
                ", storeLeve=" + storeLeve +
                ", storeLocalLat=" + storeLocalLat +
                ", storeLocalLon=" + storeLocalLon +
                ", storeLoginNumber='" + storeLoginNumber + '\'' +
                ", storeLogo='" + storeLogo + '\'' +
                ", storeMaxDeliveryDistance=" + storeMaxDeliveryDistance +
                ", storeMinGoodsAmount=" + storeMinGoodsAmount +
                ", storeName='" + storeName + '\'' +
                ", storeNotice='" + storeNotice + '\'' +
                ", storeOpenState=" + storeOpenState +
                ", storeOpenTime=" + storeOpenTime +
                ", storeOverdueTime=" + storeOverdueTime +
                ", storePercentage=" + storePercentage +
                ", storePersonalAuth=" + storePersonalAuth +
                ", storePhone='" + storePhone + '\'' +
                ", storePics='" + storePics + '\'' +
                ", storePower=" + storePower +
                ", storePrinterNumber='" + storePrinterNumber + '\'' +
                ", storeProvince='" + storeProvince + '\'' +
                ", storeReject=" + storeReject +
                ", storeSaleCount=" + storeSaleCount +
                ", storeSettlementDelay=" + storeSettlementDelay +
                ", storeShowType=" + storeShowType +
                ", storeStationId=" + storeStationId +
                ", storeTotalCommentCount=" + storeTotalCommentCount +
                ", storeTotalOrderCount=" + storeTotalOrderCount +
                ", storeTotalScore=" + storeTotalScore +
                ", storeTypeName='" + storeTypeName + '\'' +
                ", storeWeightScore=" + storeWeightScore +
                ", store_Separate=" + store_Separate +
                ", store_code='" + store_code + '\'' +
                ", store_delivery_status='" + store_delivery_status + '\'' +
                ", store_invite_status=" + store_invite_status +
                ", store_sweep_code=" + store_sweep_code +
                ", sxf_account_id='" + sxf_account_id + '\'' +
                ", sxf_mch_id='" + sxf_mch_id + '\'' +
                ", sxf_shop_id='" + sxf_shop_id + '\'' +
                ", openinfos=" + openinfos +
                '}';
    }

    public static class OpeninfosBean {
        /**
         * openEffect : 1
         * openEndTime : 2355
         * openGct : 1585211512000
         * openGmt : 1585211512000
         * openId : 2112
         * openStartTime : 2
         * storeId : 12257
         */

        private String openEndTime;
        private int openId;
        private String openStartTime;

        public String getOpenEndTime() {
            return openEndTime;
        }

        public void setOpenEndTime(String openEndTime) {
            this.openEndTime = openEndTime;
        }

        public int getOpenId() {
            return openId;
        }

        public void setOpenId(int openId) {
            this.openId = openId;
        }

        public String getOpenStartTime() {
            return openStartTime;
        }

        public void setOpenStartTime(String openStartTime) {
            this.openStartTime = openStartTime;
        }
    }
} 
 