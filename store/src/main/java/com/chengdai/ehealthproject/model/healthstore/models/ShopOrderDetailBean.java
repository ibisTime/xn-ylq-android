package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/18.
 */

public class ShopOrderDetailBean implements Parcelable {


    /**
     * code : DD2017061811510503971779
     * type : 1
     * toUser : SYS_USER_JKEG
     * receiver : 李险峻
     * reMobile : 13765051712
     * reAddress : 浙江省 杭州市 余杭区 餐前
     * applyUser : U201706161048120928135
     * applyNote :
     * applyDatetime : Jun 18, 2017 11:51:05 AM
     * amount1 : 30
     * amount2 : 0
     * amount3 : 0
     * yunfei : 0
     * status : 1
     * payAmount1 : 0
     * payAmount2 : 0
     * payAmount3 : 0
     * promptTimes : 0
     * updater : U201706161048120928135
     * updateDatetime : Jun 18, 2017 11:51:05 AM
     * remark : 订单新提交，待支付
     * companyCode : CD-JKEG000011
     * systemCode : CD-JKEG000011
     * productOrderList : [{"code":"CD2017061811510503996711","orderCode":"DD2017061811510503971779","productCode":"CP2017061614173859925599","productSpecsName":"颜色3","quantity":1,"price1":30,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","product":{"name":"来健身啊","advPic":"th (1)_1497593893907.jpg"}}]
     */

    private String code;
    private String type;
    private String toUser;
    private String receiver;
    private String reMobile;
    private String reAddress;
    private String applyUser;
    private String applyNote;
    private String applyDatetime;
    private int amount1;
    private int amount2;
    private int amount3;
    private int yunfei;
    private String status;
    private int payAmount1;
    private int payAmount2;
    private int payAmount3;
    private int promptTimes;
    private String updater;
    private String updateDatetime;
    private String remark;
    private String companyCode;
    private String systemCode;
    private String logisticsCode;
    private String logisticsCompany;

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    private List<ProductOrderListBean> productOrderList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReMobile() {
        return reMobile;
    }

    public void setReMobile(String reMobile) {
        this.reMobile = reMobile;
    }

    public String getReAddress() {
        return reAddress;
    }

    public void setReAddress(String reAddress) {
        this.reAddress = reAddress;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyNote() {
        return applyNote;
    }

    public void setApplyNote(String applyNote) {
        this.applyNote = applyNote;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public int getAmount3() {
        return amount3;
    }

    public void setAmount3(int amount3) {
        this.amount3 = amount3;
    }

    public int getYunfei() {
        return yunfei;
    }

    public void setYunfei(int yunfei) {
        this.yunfei = yunfei;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPayAmount1() {
        return payAmount1;
    }

    public void setPayAmount1(int payAmount1) {
        this.payAmount1 = payAmount1;
    }

    public int getPayAmount2() {
        return payAmount2;
    }

    public void setPayAmount2(int payAmount2) {
        this.payAmount2 = payAmount2;
    }

    public int getPayAmount3() {
        return payAmount3;
    }

    public void setPayAmount3(int payAmount3) {
        this.payAmount3 = payAmount3;
    }

    public int getPromptTimes() {
        return promptTimes;
    }

    public void setPromptTimes(int promptTimes) {
        this.promptTimes = promptTimes;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public List<ProductOrderListBean> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrderListBean> productOrderList) {
        this.productOrderList = productOrderList;
    }

    public static class ProductOrderListBean implements Parcelable {
        /**
         * code : CD2017061811510503996711
         * orderCode : DD2017061811510503971779
         * productCode : CP2017061614173859925599
         * productSpecsName : 颜色3
         * quantity : 1
         * price1 : 30
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * product : {"name":"来健身啊","advPic":"th (1)_1497593893907.jpg"}
         */

        private String code;
        private String orderCode;
        private String productCode;
        private String productSpecsName;
        private int quantity;
        private BigDecimal price1;
        private String companyCode;
        private String systemCode;
        private ProductBean product;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductSpecsName() {
            return productSpecsName;
        }

        public void setProductSpecsName(String productSpecsName) {
            this.productSpecsName = productSpecsName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice1() {
            return price1;
        }

        public void setPrice1(BigDecimal price1) {
            this.price1 = price1;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductBean implements Parcelable {
            /**
             * name : 来健身啊
             * advPic : th (1)_1497593893907.jpg
             */

            private String name;
            private String advPic;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAdvPic() {
                return advPic;
            }

            public String getSplitAdvPic() {

                List<String> s= StringUtils.splitBannerList(advPic);
                if(s .size()>1)
                {
                    return s.get(0);
                }

                return advPic;
            }

            public void setAdvPic(String advPic) {
                this.advPic = advPic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeString(this.advPic);
            }

            public ProductBean() {
            }

            protected ProductBean(Parcel in) {
                this.name = in.readString();
                this.advPic = in.readString();
            }

            public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
                @Override
                public ProductBean createFromParcel(Parcel source) {
                    return new ProductBean(source);
                }

                @Override
                public ProductBean[] newArray(int size) {
                    return new ProductBean[size];
                }
            };
        }

        public ProductOrderListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.orderCode);
            dest.writeString(this.productCode);
            dest.writeString(this.productSpecsName);
            dest.writeInt(this.quantity);
            dest.writeSerializable(this.price1);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeParcelable(this.product, flags);
        }

        protected ProductOrderListBean(Parcel in) {
            this.code = in.readString();
            this.orderCode = in.readString();
            this.productCode = in.readString();
            this.productSpecsName = in.readString();
            this.quantity = in.readInt();
            this.price1 = (BigDecimal) in.readSerializable();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.product = in.readParcelable(ProductBean.class.getClassLoader());
        }

        public static final Creator<ProductOrderListBean> CREATOR = new Creator<ProductOrderListBean>() {
            @Override
            public ProductOrderListBean createFromParcel(Parcel source) {
                return new ProductOrderListBean(source);
            }

            @Override
            public ProductOrderListBean[] newArray(int size) {
                return new ProductOrderListBean[size];
            }
        };
    }

    public ShopOrderDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.type);
        dest.writeString(this.toUser);
        dest.writeString(this.receiver);
        dest.writeString(this.reMobile);
        dest.writeString(this.reAddress);
        dest.writeString(this.applyUser);
        dest.writeString(this.applyNote);
        dest.writeString(this.applyDatetime);
        dest.writeInt(this.amount1);
        dest.writeInt(this.amount2);
        dest.writeInt(this.amount3);
        dest.writeInt(this.yunfei);
        dest.writeString(this.status);
        dest.writeInt(this.payAmount1);
        dest.writeInt(this.payAmount2);
        dest.writeInt(this.payAmount3);
        dest.writeInt(this.promptTimes);
        dest.writeString(this.updater);
        dest.writeString(this.updateDatetime);
        dest.writeString(this.remark);
        dest.writeString(this.companyCode);
        dest.writeString(this.systemCode);
        dest.writeString(this.logisticsCode);
        dest.writeString(this.logisticsCompany);
        dest.writeTypedList(this.productOrderList);
    }

    protected ShopOrderDetailBean(Parcel in) {
        this.code = in.readString();
        this.type = in.readString();
        this.toUser = in.readString();
        this.receiver = in.readString();
        this.reMobile = in.readString();
        this.reAddress = in.readString();
        this.applyUser = in.readString();
        this.applyNote = in.readString();
        this.applyDatetime = in.readString();
        this.amount1 = in.readInt();
        this.amount2 = in.readInt();
        this.amount3 = in.readInt();
        this.yunfei = in.readInt();
        this.status = in.readString();
        this.payAmount1 = in.readInt();
        this.payAmount2 = in.readInt();
        this.payAmount3 = in.readInt();
        this.promptTimes = in.readInt();
        this.updater = in.readString();
        this.updateDatetime = in.readString();
        this.remark = in.readString();
        this.companyCode = in.readString();
        this.systemCode = in.readString();
        this.logisticsCode = in.readString();
        this.logisticsCompany = in.readString();
        this.productOrderList = in.createTypedArrayList(ProductOrderListBean.CREATOR);
    }

    public static final Creator<ShopOrderDetailBean> CREATOR = new Creator<ShopOrderDetailBean>() {
        @Override
        public ShopOrderDetailBean createFromParcel(Parcel source) {
            return new ShopOrderDetailBean(source);
        }

        @Override
        public ShopOrderDetailBean[] newArray(int size) {
            return new ShopOrderDetailBean[size];
        }
    };
}
