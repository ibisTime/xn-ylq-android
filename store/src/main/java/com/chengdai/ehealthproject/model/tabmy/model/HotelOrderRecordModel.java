package com.chengdai.ehealthproject.model.tabmy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class HotelOrderRecordModel  {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"SD201706142137536021853","productCode":"FW2017061413484119738604","category":"0","type":"0","storeCode":"SJ201706131808285787504","storeUser":"U201706131808284246899","startDate":"Jun 18, 2017 12:00:00 AM","endDate":"Jun 19, 2017 12:00:00 AM","reName":"1123456","reMobile":"2322222","applyUser":"U201706091555347931953","applyNote":"555555","applyDatetime":"Jun 14, 2017 9:37:53 PM","amount1":499000,"amount2":0,"amount3":0,"payAmount1":499000,"payAmount2":0,"payAmount3":0,"status":"1","payType":"1","payGroup":"PG2017061421375938722185","payDatetime":"Jun 14, 2017 9:37:59 PM","remark":"订单已成功支付","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011","product":{"code":"FW2017061413484119738604","name":"欧洲风情馆","category":"0","type":"0","storeCode":"SJ201706131808285787504","storeUser":"U201706131808284246899","slogan":"欧洲风味 体验高端生活","advPic":"165375_20130429223859988378_1_1497419320059.jpg||timg_1497426808416.jpg","pic":"55f68581N53093f55_1497419327448.jpg||u=400392644,289454318&fm=214&gp=0_1497426811775.jpg","description":"<p>欧洲风味 体验高端生活<\/p><p><img src=\"http://or4e1nykg.bkt.clouddn.com/73bOOOPIC1d_1024_1497426820599.jpg\" style=\"max-width:100%;\"><\/p><p>欧洲风味 <\/p><p>info<span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><\/p><p><br><\/p>","price":499000,"status":"2","totalNum":30,"remainNum":34,"location":"0","orderNo":2,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}}]
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
         * code : SD201706142137536021853
         * productCode : FW2017061413484119738604
         * category : 0
         * type : 0
         * storeCode : SJ201706131808285787504
         * storeUser : U201706131808284246899
         * startDate : Jun 18, 2017 12:00:00 AM
         * endDate : Jun 19, 2017 12:00:00 AM
         * reName : 1123456
         * reMobile : 2322222
         * applyUser : U201706091555347931953
         * applyNote : 555555
         * applyDatetime : Jun 14, 2017 9:37:53 PM
         * amount1 : 499000
         * amount2 : 0
         * amount3 : 0
         * payAmount1 : 499000
         * payAmount2 : 0
         * payAmount3 : 0
         * status : 1
         * payType : 1
         * payGroup : PG2017061421375938722185
         * payDatetime : Jun 14, 2017 9:37:59 PM
         * remark : 订单已成功支付
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         * product : {"code":"FW2017061413484119738604","name":"欧洲风情馆","category":"0","type":"0","storeCode":"SJ201706131808285787504","storeUser":"U201706131808284246899","slogan":"欧洲风味 体验高端生活","advPic":"165375_20130429223859988378_1_1497419320059.jpg||timg_1497426808416.jpg","pic":"55f68581N53093f55_1497419327448.jpg||u=400392644,289454318&fm=214&gp=0_1497426811775.jpg","description":"<p>欧洲风味 体验高端生活<\/p><p><img src=\"http://or4e1nykg.bkt.clouddn.com/73bOOOPIC1d_1024_1497426820599.jpg\" style=\"max-width:100%;\"><\/p><p>欧洲风味 <\/p><p>info<span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><span style=\"display: inline !important;\">info<\/span><\/p><p><br><\/p>","price":499000,"status":"2","totalNum":30,"remainNum":34,"location":"0","orderNo":2,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}
         */

        private String code;
        private String productCode;
        private String category;
        private String type;
        private String storeCode;
        private String storeUser;
        private String startDate;
        private String endDate;
        private String reName;
        private String reMobile;
        private String applyUser;
        private String applyNote;
        private String applyDatetime;
        private int amount1;
        private int amount2;
        private int amount3;
        private int payAmount1;
        private int payAmount2;
        private int payAmount3;
        private String status;
        private String payType;
        private String payGroup;
        private String payDatetime;
        private String remark;
        private String companyCode;
        private String systemCode;
        private ProductBean product;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
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

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getStoreUser() {
            return storeUser;
        }

        public void setStoreUser(String storeUser) {
            this.storeUser = storeUser;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getReName() {
            return reName;
        }

        public void setReName(String reName) {
            this.reName = reName;
        }

        public String getReMobile() {
            return reMobile;
        }

        public void setReMobile(String reMobile) {
            this.reMobile = reMobile;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayGroup() {
            return payGroup;
        }

        public void setPayGroup(String payGroup) {
            this.payGroup = payGroup;
        }

        public String getPayDatetime() {
            return payDatetime;
        }

        public void setPayDatetime(String payDatetime) {
            this.payDatetime = payDatetime;
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

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductBean implements Parcelable {
            /**
             * code : FW2017061413484119738604
             * name : 欧洲风情馆
             * category : 0
             * type : 0
             * storeCode : SJ201706131808285787504
             * storeUser : U201706131808284246899
             * slogan : 欧洲风味 体验高端生活
             * advPic : 165375_20130429223859988378_1_1497419320059.jpg||timg_1497426808416.jpg
             * pic : 55f68581N53093f55_1497419327448.jpg||u=400392644,289454318&fm=214&gp=0_1497426811775.jpg
             * description : <p>欧洲风味 体验高端生活</p><p><img src="http://or4e1nykg.bkt.clouddn.com/73bOOOPIC1d_1024_1497426820599.jpg" style="max-width:100%;"></p><p>欧洲风味 </p><p>info<span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span><span style="display: inline !important;">info</span></p><p><br></p>
             * price : 499000
             * status : 2
             * totalNum : 30
             * remainNum : 34
             * location : 0
             * orderNo : 2
             * companyCode : CD-JKEG000011
             * systemCode : CD-JKEG000011
             */

            private String code;
            private String name;
            private String category;
            private String type;
            private String storeCode;
            private String storeUser;
            private String slogan;
            private String advPic;
            private String pic;
            private String description;
            private BigDecimal price;
            private String status;
            private int totalNum;
            private int remainNum;
            private String location;
            private int orderNo;
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

            public String getStoreCode() {
                return storeCode;
            }

            public void setStoreCode(String storeCode) {
                this.storeCode = storeCode;
            }

            public String getStoreUser() {
                return storeUser;
            }

            public void setStoreUser(String storeUser) {
                this.storeUser = storeUser;
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

            public String getSplitAdvPic() {
                List<String> s= StringUtils.splitAsList(advPic,"\\|\\|");
                if(s.size() > 1){
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

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public int getRemainNum() {
                return remainNum;
            }

            public void setRemainNum(int remainNum) {
                this.remainNum = remainNum;
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

            public ProductBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.name);
                dest.writeString(this.category);
                dest.writeString(this.type);
                dest.writeString(this.storeCode);
                dest.writeString(this.storeUser);
                dest.writeString(this.slogan);
                dest.writeString(this.advPic);
                dest.writeString(this.pic);
                dest.writeString(this.description);
                dest.writeSerializable(this.price);
                dest.writeString(this.status);
                dest.writeInt(this.totalNum);
                dest.writeInt(this.remainNum);
                dest.writeString(this.location);
                dest.writeInt(this.orderNo);
                dest.writeString(this.companyCode);
                dest.writeString(this.systemCode);
            }

            protected ProductBean(Parcel in) {
                this.code = in.readString();
                this.name = in.readString();
                this.category = in.readString();
                this.type = in.readString();
                this.storeCode = in.readString();
                this.storeUser = in.readString();
                this.slogan = in.readString();
                this.advPic = in.readString();
                this.pic = in.readString();
                this.description = in.readString();
                this.price = (BigDecimal) in.readSerializable();
                this.status = in.readString();
                this.totalNum = in.readInt();
                this.remainNum = in.readInt();
                this.location = in.readString();
                this.orderNo = in.readInt();
                this.companyCode = in.readString();
                this.systemCode = in.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.productCode);
            dest.writeString(this.category);
            dest.writeString(this.type);
            dest.writeString(this.storeCode);
            dest.writeString(this.storeUser);
            dest.writeString(this.startDate);
            dest.writeString(this.endDate);
            dest.writeString(this.reName);
            dest.writeString(this.reMobile);
            dest.writeString(this.applyUser);
            dest.writeString(this.applyNote);
            dest.writeString(this.applyDatetime);
            dest.writeInt(this.amount1);
            dest.writeInt(this.amount2);
            dest.writeInt(this.amount3);
            dest.writeInt(this.payAmount1);
            dest.writeInt(this.payAmount2);
            dest.writeInt(this.payAmount3);
            dest.writeString(this.status);
            dest.writeString(this.payType);
            dest.writeString(this.payGroup);
            dest.writeString(this.payDatetime);
            dest.writeString(this.remark);
            dest.writeString(this.companyCode);
            dest.writeString(this.systemCode);
            dest.writeParcelable(this.product, flags);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.productCode = in.readString();
            this.category = in.readString();
            this.type = in.readString();
            this.storeCode = in.readString();
            this.storeUser = in.readString();
            this.startDate = in.readString();
            this.endDate = in.readString();
            this.reName = in.readString();
            this.reMobile = in.readString();
            this.applyUser = in.readString();
            this.applyNote = in.readString();
            this.applyDatetime = in.readString();
            this.amount1 = in.readInt();
            this.amount2 = in.readInt();
            this.amount3 = in.readInt();
            this.payAmount1 = in.readInt();
            this.payAmount2 = in.readInt();
            this.payAmount3 = in.readInt();
            this.status = in.readString();
            this.payType = in.readString();
            this.payGroup = in.readString();
            this.payDatetime = in.readString();
            this.remark = in.readString();
            this.companyCode = in.readString();
            this.systemCode = in.readString();
            this.product = in.readParcelable(ProductBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
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
}
