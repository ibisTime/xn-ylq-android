package com.chengdai.ehealthproject.model.healthstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/19.
 */

public class PayCarListModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 3
     * totalPage : 1
     * list : [{"code":"GW20170619103022832259","userId":"U201706161048120928135","productCode":"CP2017061614173859925599","productSpecsCode":"PS2017061810580102855831","quantity":12,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","product":{"code":"CP2017061614173859925599","storeCode":"SYS_USER_JKEG","name":"来健身啊","advPic":"th (1)_1497593893907.jpg"},"productSpecs":{"code":"PS2017061810580102855831","name":"颜色1","productCode":"CP2017061614173859925599","originalPrice":20000000,"price1":10,"quantity":20,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}},{"code":"GW2017061910262118022732","userId":"U201706161048120928135","productCode":"CP2017061614173859925599","productSpecsCode":"PS201706181058010252679","quantity":19,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","product":{"code":"CP2017061614173859925599","storeCode":"SYS_USER_JKEG","name":"来健身啊","advPic":"th (1)_1497593893907.jpg"},"productSpecs":{"code":"PS201706181058010252679","name":"颜色3","productCode":"CP2017061614173859925599","originalPrice":20000000,"price1":30,"quantity":20,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}},{"code":"GW2017061910222894662083","userId":"U201706161048120928135","productCode":"CP2017061614173859925599","productSpecsCode":"PS2017061810580102734735","quantity":1,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","product":{"code":"CP2017061614173859925599","storeCode":"SYS_USER_JKEG","name":"来健身啊","advPic":"th (1)_1497593893907.jpg"},"productSpecs":{"code":"PS2017061810580102734735","name":"颜色2","productCode":"CP2017061614173859925599","originalPrice":10000000,"price1":20,"quantity":20,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}}]
     */

    private int pageNO;
    private int start;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * code : GW20170619103022832259
         * userId : U201706161048120928135
         * productCode : CP2017061614173859925599
         * productSpecsCode : PS2017061810580102855831
         * quantity : 12
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * product : {"code":"CP2017061614173859925599","storeCode":"SYS_USER_JKEG","name":"来健身啊","advPic":"th (1)_1497593893907.jpg"}
         * productSpecs : {"code":"PS2017061810580102855831","name":"颜色1","productCode":"CP2017061614173859925599","originalPrice":20000000,"price1":10,"quantity":20,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}
         */

        private String code;
        private String userId;
        private String productCode;
        private String productSpecsCode;
        private int quantity;
        private String companyCode;
        private String systemCode;
        private ProductBean product;
        private ProductSpecsBean productSpecs;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductSpecsCode() {
            return productSpecsCode;
        }

        public void setProductSpecsCode(String productSpecsCode) {
            this.productSpecsCode = productSpecsCode;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
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

        public ProductSpecsBean getProductSpecs() {
            return productSpecs;
        }

        public void setProductSpecs(ProductSpecsBean productSpecs) {
            this.productSpecs = productSpecs;
        }

        public static class ProductBean implements Parcelable {
            /**
             * code : CP2017061614173859925599
             * storeCode : SYS_USER_JKEG
             * name : 来健身啊
             * advPic : th (1)_1497593893907.jpg
             */

            private String code;
            private String storeCode;
            private String name;
            private String advPic;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAdvPic() {
                return advPic;
            }

            public void setAdvPic(String advPic) {
                this.advPic = advPic;
            }
            public String getSplitAdvPic() {

                List<String> s= StringUtils.splitAsList(advPic,"\\|\\|");
                if(s .size()>1)
                {
                    return s.get(0);
                }

                return advPic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.storeCode);
                dest.writeString(this.name);
                dest.writeString(this.advPic);
            }

            public ProductBean() {
            }

            protected ProductBean(Parcel in) {
                this.code = in.readString();
                this.storeCode = in.readString();
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

        public static class ProductSpecsBean implements Parcelable {
            /**
             * code : PS2017061810580102855831
             * name : 颜色1
             * productCode : CP2017061614173859925599
             * originalPrice : 20000000
             * price1 : 10
             * quantity : 20
             * companyCode : CD-JKEG000011
             * systemCode : CD-JKEG000011
             */

            private String code;
            private String name;
            private String productCode;
            private BigDecimal originalPrice;
            private BigDecimal price1;
            private int quantity;
            private String companyCode;
            private String systemCode;

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

            public ProductSpecsBean() {
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
                dest.writeString(this.companyCode);
                dest.writeString(this.systemCode);
            }

            protected ProductSpecsBean(Parcel in) {
                this.code = in.readString();
                this.name = in.readString();
                this.productCode = in.readString();
                this.originalPrice = (BigDecimal) in.readSerializable();
                this.price1 = (BigDecimal) in.readSerializable();
                this.quantity = in.readInt();
                this.companyCode = in.readString();
                this.systemCode = in.readString();
            }

            public static final Creator<ProductSpecsBean> CREATOR = new Creator<ProductSpecsBean>() {
                @Override
                public ProductSpecsBean createFromParcel(Parcel source) {
                    return new ProductSpecsBean(source);
                }

                @Override
                public ProductSpecsBean[] newArray(int size) {
                    return new ProductSpecsBean[size];
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
            dest.writeString(this.userId);
            dest.writeString(this.productCode);
            dest.writeString(this.productSpecsCode);
            dest.writeInt(this.quantity);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeParcelable(this.product, flags);
            dest.writeParcelable(this.productSpecs, flags);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.userId = in.readString();
            this.productCode = in.readString();
            this.productSpecsCode = in.readString();
            this.quantity = in.readInt();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.product = in.readParcelable(ProductBean.class.getClassLoader());
            this.productSpecs = in.readParcelable(ProductSpecsBean.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageNO);
        dest.writeInt(this.start);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.totalPage);
        dest.writeList(this.list);
    }

    public PayCarListModel() {
    }

    protected PayCarListModel(Parcel in) {
        this.pageNO = in.readInt();
        this.start = in.readInt();
        this.pageSize = in.readInt();
        this.totalCount = in.readInt();
        this.totalPage = in.readInt();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PayCarListModel> CREATOR = new Parcelable.Creator<PayCarListModel>() {
        @Override
        public PayCarListModel createFromParcel(Parcel source) {
            return new PayCarListModel(source);
        }

        @Override
        public PayCarListModel[] newArray(int size) {
            return new PayCarListModel[size];
        }
    };
}
