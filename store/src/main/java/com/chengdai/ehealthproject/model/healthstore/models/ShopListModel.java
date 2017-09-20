package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopListModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 3
     * totalPage : 1
     * list : [{"code":"CP2017061614173859925599","storeCode":"SYS_USER_JKEG","kind":"1","category":"FL2017061611323544926138","type":"FL2017061612233181756042","name":"来健身啊","slogan":"*广告语:*广告语:*广告语:*广告语:","advPic":"th (1)_1497593893907.jpg","pic":"th (2)_1497593897971.jpg","description":"*商品详述:*商品详述:*商品详述:*商品详述:","location":"1","orderNo":2,"status":"3","updater":"jkeg","updateDatetime":"Jun 17, 2017 10:59:10 AM","boughtCount":1,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","productSpecsList":[{"code":"PS2017061618003802618928","name":"型号","productCode":"CP2017061614173859925599","originalPrice":20000000,"price1":10,"quantity":20,"orderNo":2,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]},{"code":"CP2017061614030396682349","storeCode":"SYS_USER_JKEG","kind":"1","category":"FL2017061611260999346746","type":"FL2017061612201688937222","name":"这是新增的商品","slogan":"*广告语:*广告语:*广告语:","advPic":"th (3)_1497593042380.jpg","pic":"th (2)_1497593046390.jpg","description":"2222","location":"0","orderNo":2,"status":"3","updater":"jkeg","updateDatetime":"Jun 16, 2017 2:19:38 PM","boughtCount":0,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","productSpecsList":[{"code":"PS2017061614191901350658","name":"型号","productCode":"CP2017061614030396682349","originalPrice":2000000,"price1":2000000,"quantity":20,"orderNo":1,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"code":"PS2017061614191901496451","name":"颜色","productCode":"CP2017061614030396682349","originalPrice":10000000,"price1":10000000,"quantity":10,"orderNo":20,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]},{"code":"CP2017061613020166722208","storeCode":"SJ2017061611211296942716","kind":"1","category":"FL2017061611320646922221","type":"FL2017061612221012761076","name":"氨基酸","slogan":"*广告语:*广告语:*广告语:","advPic":"下载_1497589373877.jpg","pic":"th (2)_1497589382457.jpg","description":"*商品详述:*商品详述:*商品详述:","location":"1","orderNo":1,"status":"3","updater":"jkeg","updateDatetime":"Jun 16, 2017 1:05:54 PM","boughtCount":0,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","productSpecsList":[{"code":"PS201706161305240212555","name":"颜色","productCode":"CP2017061613020166722208","originalPrice":10000000,"price1":10000000,"price2":0,"price3":0,"quantity":10000,"orderNo":1,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]}]
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
         * code : CP2017061614173859925599
         * storeCode : SYS_USER_JKEG
         * kind : 1
         * category : FL2017061611323544926138
         * type : FL2017061612233181756042
         * name : 来健身啊
         * slogan : *广告语:*广告语:*广告语:*广告语:
         * advPic : th (1)_1497593893907.jpg
         * pic : th (2)_1497593897971.jpg
         * description : *商品详述:*商品详述:*商品详述:*商品详述:
         * location : 1
         * orderNo : 2
         * status : 3
         * updater : jkeg
         * updateDatetime : Jun 17, 2017 10:59:10 AM
         * boughtCount : 1
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * productSpecsList : [{"code":"PS2017061618003802618928","name":"型号","productCode":"CP2017061614173859925599","originalPrice":20000000,"price1":10,"quantity":20,"orderNo":2,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]
         */

        private String code;
        private String storeCode;
        private String kind;
        private String category;
        private String type;
        private String name;
        private String slogan;
        private String advPic;
        private String pic;
        private String description;
        private String location;
        private int orderNo;
        private String status;
        private String updater;
        private String updateDatetime;
        private int boughtCount;
        private String companyCode;
        private String systemCode;
        private List<ProductSpecsListBean> productSpecsList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getAdvPic() {
            return advPic;
        }

        public String getSplitPic() {

            List<String> s= StringUtils.splitAsList(pic,"\\|\\|");
            if(s .size()>1)
            {
                return s.get(0);
            }

            return pic;
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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public int getBoughtCount() {
            return boughtCount;
        }

        public void setBoughtCount(int boughtCount) {
            this.boughtCount = boughtCount;
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

        public List<ProductSpecsListBean> getProductSpecsList() {
            return productSpecsList;
        }

        public void setProductSpecsList(List<ProductSpecsListBean> productSpecsList) {
            this.productSpecsList = productSpecsList;
        }

        public static class ProductSpecsListBean implements Parcelable {
            /**
             * code : PS2017061618003802618928
             * name : 型号
             * productCode : CP2017061614173859925599
             * originalPrice : 20000000
             * price1 : 10
             * quantity : 20
             * orderNo : 2
             * companyCode : CD-JKEG000011
             * systemCode : CD-JKEG000011
             */

            private String code;
            private String name;
            private String productCode;
            private BigDecimal originalPrice;
            private BigDecimal price1;
            private int quantity;
            private int orderNo;
            private String companyCode;
            private String systemCode;

            private int mBuyNum=0;//自定义属性 用于保存用户选择数量

            public int getmBuyNum() {
                return mBuyNum;
            }

            public void setmBuyNum(int mBuyNum) {
                this.mBuyNum = mBuyNum;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public BigDecimal getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(BigDecimal originalPrice) {
                this.originalPrice = originalPrice;
            }

            public BigDecimal getPrice1() {
                return price1;
            }

            public void setPrice1(BigDecimal price1) {
                this.price1 = price1;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(int orderNo) {
                this.orderNo = orderNo;
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

            public ProductSpecsListBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.name);
                dest.writeString(this.productCode);
                dest.writeSerializable(this.originalPrice);
                dest.writeSerializable(this.price1);
                dest.writeInt(this.quantity);
                dest.writeInt(this.orderNo);
                dest.writeString(this.companyCode);
                dest.writeString(this.systemCode);
                dest.writeInt(this.mBuyNum);
            }

            protected ProductSpecsListBean(Parcel in) {
                this.code = in.readString();
                this.name = in.readString();
                this.productCode = in.readString();
                this.originalPrice = (BigDecimal) in.readSerializable();
                this.price1 = (BigDecimal) in.readSerializable();
                this.quantity = in.readInt();
                this.orderNo = in.readInt();
                this.companyCode = in.readString();
                this.systemCode = in.readString();
                this.mBuyNum = in.readInt();
            }

            public static final Creator<ProductSpecsListBean> CREATOR = new Creator<ProductSpecsListBean>() {
                @Override
                public ProductSpecsListBean createFromParcel(Parcel source) {
                    return new ProductSpecsListBean(source);
                }

                @Override
                public ProductSpecsListBean[] newArray(int size) {
                    return new ProductSpecsListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.storeCode);
            dest.writeString(this.kind);
            dest.writeString(this.category);
            dest.writeString(this.type);
            dest.writeString(this.name);
            dest.writeString(this.slogan);
            dest.writeString(this.advPic);
            dest.writeString(this.pic);
            dest.writeString(this.description);
            dest.writeString(this.location);
            dest.writeInt(this.orderNo);
            dest.writeString(this.status);
            dest.writeString(this.updater);
            dest.writeString(this.updateDatetime);
            dest.writeInt(this.boughtCount);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeList(this.productSpecsList);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.storeCode = in.readString();
            this.kind = in.readString();
            this.category = in.readString();
            this.type = in.readString();
            this.name = in.readString();
            this.slogan = in.readString();
            this.advPic = in.readString();
            this.pic = in.readString();
            this.description = in.readString();
            this.location = in.readString();
            this.orderNo = in.readInt();
            this.status = in.readString();
            this.updater = in.readString();
            this.updateDatetime = in.readString();
            this.boughtCount = in.readInt();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.productSpecsList = new ArrayList<ProductSpecsListBean>();
            in.readList(this.productSpecsList, ProductSpecsListBean.class.getClassLoader());
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

    public ShopListModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected ShopListModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<ShopListModel> CREATOR = new Creator<ShopListModel>() {
        @Override
        public ShopListModel createFromParcel(Parcel source) {
            return new ShopListModel(source);
        }

        @Override
        public ShopListModel[] newArray(int size) {
            return new ShopListModel[size];
        }
    };
}
