package com.chengdai.ehealthproject.model.tabmy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/27.
 */

public class JfDetailsListModel implements Parcelable {

    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 6
     * totalPage : 1
     * list : [{"code":"AJ20170627180101567584023748","refNo":"健康评分测试","accountNumber":"A201706271754103417568793684","transAmount":2000,"userId":"U201706271754103317047","realName":"15268501481","type":"C","currency":"JF","bizType":"JKEG_JKPFCS","bizNote":"健康评分测试","preAmount":7000,"postAmount":9000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 6:01:01 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"},{"code":"AJ201706271801015641412681764","refNo":"健康评分测试","accountNumber":"JKEGA2016100000000000001","transAmount":-2000,"userId":"SYS_USER_JKEG","realName":"平台积分账户","type":"P","currency":"JF","bizType":"JKEG_JKPFCS","bizNote":"健康评分测试","preAmount":-7000,"postAmount":-9000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 6:01:01 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"},{"code":"AJ201706271756039219542491090","refNo":"发帖","accountNumber":"A201706271754103417568793684","transAmount":5000,"userId":"U201706271754103317047","realName":"15268501481","type":"C","currency":"JF","bizType":"JKEG_FT","bizNote":"发帖","preAmount":2000,"postAmount":7000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 5:56:03 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"},{"code":"AJ201706271756039179616709440","refNo":"发帖","accountNumber":"JKEGA2016100000000000001","transAmount":-5000,"userId":"SYS_USER_JKEG","realName":"平台积分账户","type":"P","currency":"JF","bizType":"JKEG_FT","bizNote":"发帖","preAmount":-2000,"postAmount":-7000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 5:56:03 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"},{"code":"AJ201706271755132416737484468","refNo":"健康评分测试","accountNumber":"A201706271754103417568793684","transAmount":2000,"userId":"U201706271754103317047","realName":"15268501481","type":"C","currency":"JF","bizType":"JKEG_JKPFCS","bizNote":"健康评分测试","preAmount":0,"postAmount":2000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 5:55:13 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"},{"code":"AJ201706271755132389078798319","refNo":"健康评分测试","accountNumber":"JKEGA2016100000000000001","transAmount":-2000,"userId":"SYS_USER_JKEG","realName":"平台积分账户","type":"P","currency":"JF","bizType":"JKEG_JKPFCS","bizNote":"健康评分测试","preAmount":0,"postAmount":-2000,"status":"1","remark":"记得对账哦","createDatetime":"Jun 27, 2017 5:55:13 PM","workDate":"20170627","channelType":"0","systemCode":"CD-JKEG000011","companyCode":"CD-JKEG000011"}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * code : AJ20170627180101567584023748
         * refNo : 健康评分测试
         * accountNumber : A201706271754103417568793684
         * transAmount : 2000
         * userId : U201706271754103317047
         * realName : 15268501481
         * type : C
         * currency : JF
         * bizType : JKEG_JKPFCS
         * bizNote : 健康评分测试
         * preAmount : 7000
         * postAmount : 9000
         * status : 1
         * remark : 记得对账哦
         * createDatetime : Jun 27, 2017 6:01:01 PM
         * workDate : 20170627
         * channelType : 0
         * systemCode : CD-JKEG000011
         * companyCode : CD-JKEG000011
         */

        private String code;
        private String refNo;
        private String accountNumber;
        private BigDecimal transAmount;
        private String userId;
        private String realName;
        private String type;
        private String currency;
        private String bizType;
        private String bizNote;
        private int preAmount;
        private int postAmount;
        private String status;
        private String remark;
        private String createDatetime;
        private String workDate;
        private String channelType;
        private String systemCode;
        private String companyCode;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public BigDecimal getTransAmount() {
            return transAmount;
        }

        public void setTransAmount(BigDecimal transAmount) {
            this.transAmount = transAmount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getBizNote() {
            return bizNote;
        }

        public void setBizNote(String bizNote) {
            this.bizNote = bizNote;
        }

        public int getPreAmount() {
            return preAmount;
        }

        public void setPreAmount(int preAmount) {
            this.preAmount = preAmount;
        }

        public int getPostAmount() {
            return postAmount;
        }

        public void setPostAmount(int postAmount) {
            this.postAmount = postAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getWorkDate() {
            return workDate;
        }

        public void setWorkDate(String workDate) {
            this.workDate = workDate;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.refNo);
            dest.writeString(this.accountNumber);
            dest.writeSerializable(this.transAmount);
            dest.writeString(this.userId);
            dest.writeString(this.realName);
            dest.writeString(this.type);
            dest.writeString(this.currency);
            dest.writeString(this.bizType);
            dest.writeString(this.bizNote);
            dest.writeInt(this.preAmount);
            dest.writeInt(this.postAmount);
            dest.writeString(this.status);
            dest.writeString(this.remark);
            dest.writeString(this.createDatetime);
            dest.writeString(this.workDate);
            dest.writeString(this.channelType);
            dest.writeString(this.systemCode);
            dest.writeString(this.companyCode);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.refNo = in.readString();
            this.accountNumber = in.readString();
            this.transAmount = (BigDecimal) in.readSerializable();
            this.userId = in.readString();
            this.realName = in.readString();
            this.type = in.readString();
            this.currency = in.readString();
            this.bizType = in.readString();
            this.bizNote = in.readString();
            this.preAmount = in.readInt();
            this.postAmount = in.readInt();
            this.status = in.readString();
            this.remark = in.readString();
            this.createDatetime = in.readString();
            this.workDate = in.readString();
            this.channelType = in.readString();
            this.systemCode = in.readString();
            this.companyCode = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    public JfDetailsListModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected JfDetailsListModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<JfDetailsListModel> CREATOR = new Creator<JfDetailsListModel>() {
        @Override
        public JfDetailsListModel createFromParcel(Parcel source) {
            return new JfDetailsListModel(source);
        }

        @Override
        public JfDetailsListModel[] newArray(int size) {
            return new JfDetailsListModel[size];
        }
    };
}
