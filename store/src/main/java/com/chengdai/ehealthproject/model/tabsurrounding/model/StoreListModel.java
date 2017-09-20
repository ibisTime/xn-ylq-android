package com.chengdai.ehealthproject.model.tabsurrounding.model;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.util.List;

/**商户列表list model
 * Created by 李先俊 on 2017/6/12.
 */

public class StoreListModel {
    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"SJ201612172029042837","name":"满意小炒update","level":"1","type":"1","slogan":"200积分抵用50元","advPic":"店铺缩略图URL","pic":"www.baidu.com","description":"满意小炒，炒到你满意","province":"浙江","city":"金华","area":"金东区","address":"金东区大堰河街4号","longitude":"1","latitude":"1","bookMobile":"15268501481","smsMobile":"","pdf":"","status":"1","updateDatetime":"Dec 18, 2016 3:05:57 PM","remark":"上架","owner":"店铺主人ID","legalPersonName":"法人姓名","userReferee":"推荐人","rate1":0.1,"rate2":0.15,"totalJfNum":0,"totalDzNum":0,"totalScNum":0,"distance":0,"companyCode":"公司编号","systemCode":"系统编号"}]
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

    public static class ListBean {
        /**
         * code : SJ201612172029042837
         * name : 满意小炒update
         * level : 1
         * type : 1
         * slogan : 200积分抵用50元
         * advPic : 店铺缩略图URL
         * pic : www.baidu.com
         * description : 满意小炒，炒到你满意
         * province : 浙江
         * city : 金华
         * area : 金东区
         * address : 金东区大堰河街4号
         * longitude : 1
         * latitude : 1
         * bookMobile : 15268501481
         * smsMobile :
         * pdf :
         * status : 1
         * updateDatetime : Dec 18, 2016 3:05:57 PM
         * remark : 上架
         * owner : 店铺主人ID
         * legalPersonName : 法人姓名
         * userReferee : 推荐人
         * rate1 : 0.1
         * rate2 : 0.15
         * totalJfNum : 0
         * totalDzNum : 0
         * totalScNum : 0
         * distance : 0
         * companyCode : 公司编号
         * systemCode : 系统编号
         * isDZ  是否点赞
         */

        private String code;
        private String name;
        private String level;
        private String type;
        private String slogan;
        private String advPic;
        private String pic;
        private String description;
        private String province;
        private String city;
        private String area;
        private String address;
        private String longitude;
        private String latitude;
        private String bookMobile;
        private String smsMobile;
        private String pdf;
        private String status;
        private String updateDatetime;
        private String remark;
        private String owner;
        private String legalPersonName;
        private String userReferee;
        private double rate1;
        private double rate2;
        private int totalJfNum;
        private int totalDzNum;
        private int totalScNum;
        private int distance;
        private String companyCode;
        private String systemCode;

        private boolean isDZ;

        public boolean isDZ() {
            return isDZ;
        }

        public void setDZ(boolean DZ) {
            isDZ = DZ;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getSplitAdvPic(){

            List<String> list= StringUtils.splitAsList(advPic,"\\|\\|");

            if(list.size()>1){
                return list.get(0);
            }

            return advPic;

        }
        public void setAdvPic(String advPic) {
            this.advPic = advPic;
        }

        public String getPic() {
            return pic;
        }

        public String getSplitPic(){

            List<String> list= StringUtils.splitAsList(pic,"\\|\\|");

            if(list.size()>1){
                return list.get(0);
            }

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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getBookMobile() {
            return bookMobile;
        }

        public void setBookMobile(String bookMobile) {
            this.bookMobile = bookMobile;
        }

        public String getSmsMobile() {
            return smsMobile;
        }

        public void setSmsMobile(String smsMobile) {
            this.smsMobile = smsMobile;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getLegalPersonName() {
            return legalPersonName;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }

        public String getUserReferee() {
            return userReferee;
        }

        public void setUserReferee(String userReferee) {
            this.userReferee = userReferee;
        }

        public double getRate1() {
            return rate1;
        }

        public void setRate1(double rate1) {
            this.rate1 = rate1;
        }

        public double getRate2() {
            return rate2;
        }

        public void setRate2(double rate2) {
            this.rate2 = rate2;
        }

        public int getTotalJfNum() {
            return totalJfNum;
        }

        public void setTotalJfNum(int totalJfNum) {
            this.totalJfNum = totalJfNum;
        }

        public int getTotalDzNum() {
            return totalDzNum;
        }

        public void setTotalDzNum(int totalDzNum) {
            this.totalDzNum = totalDzNum;
        }

        public int getTotalScNum() {
            return totalScNum;
        }

        public void setTotalScNum(int totalScNum) {
            this.totalScNum = totalScNum;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
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
    }


    /**/


}
