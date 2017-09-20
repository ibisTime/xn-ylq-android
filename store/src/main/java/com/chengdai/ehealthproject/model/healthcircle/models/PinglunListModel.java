package com.chengdai.ehealthproject.model.healthcircle.models;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/21.
 */

public class PinglunListModel  {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 5
     * totalPage : 1
     * list : [{"code":"PL20170617211235018","content":"哈哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:50 AM","postCode":"TZ20170617211053911","post":{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}},{"code":"PL20170617211234373","content":"哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:43 AM","postCode":"TZ20170617211053911","post":{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}},{"code":"PL20170617211233587","content":"哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:35 AM","postCode":"TZ20170617211053911","post":{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}},{"code":"PL20170617202520562","content":"x\u2006f\u2006g","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 2:52:05 PM","postCode":"TZ20170617211053911","post":{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}},{"code":"PL2017061720228310","content":"习大大","parentCode":"TZ20170617211053911","status":"D","commer":"U201706190959487793595","nickname":"87793595","commDatetime":"Jun 21, 2017 2:28:31 PM","approver":"jkeg","approveDatetime":"Jun 21, 2017 2:52:33 PM","approveNote":"11","postCode":"TZ20170617211053911","post":{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}}]
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
         * code : PL20170617211235018
         * content : 哈哈哈哈
         * parentCode : TZ20170617211053911
         * status : B
         * commer : U201706161050240444339
         * photo : IOS_1498014255280108_1024_576.jpg
         * nickname : qqq
         * commDatetime : Jun 21, 2017 11:23:50 AM
         * postCode : TZ20170617211053911
         * post : {"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":5,"sumLike":0}
         * approver : jkeg
         * approveDatetime : Jun 21, 2017 2:52:33 PM
         * approveNote : 11
         */

        private String code;
        private String content;
        private String parentCode;
        private String status;
        private String commer;
        private String photo;
        private String nickname;
        private String commDatetime;
        private String postCode;
        private PostBean post;
        private String approver;
        private String approveDatetime;
        private String approveNote;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCommer() {
            return commer;
        }

        public void setCommer(String commer) {
            this.commer = commer;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCommDatetime() {
            return commDatetime;
        }

        public void setCommDatetime(String commDatetime) {
            this.commDatetime = commDatetime;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public PostBean getPost() {
            return post;
        }

        public void setPost(PostBean post) {
            this.post = post;
        }

        public String getApprover() {
            return approver;
        }

        public void setApprover(String approver) {
            this.approver = approver;
        }

        public String getApproveDatetime() {
            return approveDatetime;
        }

        public void setApproveDatetime(String approveDatetime) {
            this.approveDatetime = approveDatetime;
        }

        public String getApproveNote() {
            return approveNote;
        }

        public void setApproveNote(String approveNote) {
            this.approveNote = approveNote;
        }

        public static class PostBean {
            /**
             * code : TZ20170617211053911
             * title : 标题
             * content : 出来吧，神龙！
             * pic : iOS_1498014377031708_852_640.jpg
             * status : B
             * address : 浙江省.杭州市.余杭区
             * publisher : U201706161050240444339
             * photo : IOS_1498014255280108_1024_576.jpg
             * nickname : qqq
             * publishDatetime : Jun 21, 2017 11:05:39 AM
             * location : 1
             * orderNo : 0
             * sumComment : 5
             * sumLike : 0
             */

            private String code;
            private String title;
            private String content;
            private String pic;
            private String status;
            private String address;
            private String publisher;
            private String photo;
            private String nickname;
            private String publishDatetime;
            private String location;
            private int orderNo;
            private int sumComment;
            private int sumLike;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPublishDatetime() {
                return publishDatetime;
            }

            public void setPublishDatetime(String publishDatetime) {
                this.publishDatetime = publishDatetime;
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

            public int getSumComment() {
                return sumComment;
            }

            public void setSumComment(int sumComment) {
                this.sumComment = sumComment;
            }

            public int getSumLike() {
                return sumLike;
            }

            public void setSumLike(int sumLike) {
                this.sumLike = sumLike;
            }
        }
    }
}
