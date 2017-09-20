package com.chengdai.ehealthproject.model.healthcircle.models;

import java.util.List;

/**
 * Created by 李先俊 on 2017/7/12.
 */

public class ArticleDetailsModel {

    /**
     * code : TZ20170718810583132
     * title : 标题
     * content : 卖米嘞
     * pic : iOS_1499439506959192_1242_2208.jpg
     * status : B
     * address : 浙江省.杭州市.西湖区
     * publisher : U20170707225400064247
     * photo : IOS_1499439257182751_960_960.jpg
     * nickname : 小海哥
     * publishDatetime : Jul 7, 2017 10:58:31 PM
     * location : 1
     * orderNo : 0
     * sumComment : 1
     * sumLike : 3
     * isDZ : 0
     * isSC : 0
     * commentList : [{"code":"PL20170718811271493","content":"��","parentCode":"TZ20170718810583132","status":"B","commer":"U201707072142255578821","photo":"IOS_1499434974402131_350_311.jpg","nickname":"小妮子","commDatetime":"Jul 7, 2017 11:27:14 PM","postCode":"TZ20170718810583132"}]
     * likeList : [{"code":"TZJH20170719312423738","type":"1","postCode":"TZ20170718810583132","talker":"U201707121054372292667","nickname":"72292667","talkDatetime":"Jul 12, 2017 12:42:37 PM"},{"code":"TZJH20170719202353932","type":"1","postCode":"TZ20170718810583132","talker":"U20170707225400064247","photo":"IOS_1499439257182751_960_960.jpg","nickname":"小海哥","talkDatetime":"Jul 11, 2017 2:35:39 PM"},{"code":"TZJH20170718811002954","type":"1","postCode":"TZ20170718810583132","talker":"U201707072142255578821","photo":"IOS_1499434974402131_350_311.jpg","nickname":"小妮子","talkDatetime":"Jul 7, 2017 11:00:29 PM"}]
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
    private String isDZ;
    private String isSC;
    private List<CommentListBean> commentList;
    private List<LikeListBean> likeList;

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

    public String getIsDZ() {
        return isDZ;
    }

    public void setIsDZ(String isDZ) {
        this.isDZ = isDZ;
    }

    public String getIsSC() {
        return isSC;
    }

    public void setIsSC(String isSC) {
        this.isSC = isSC;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public List<LikeListBean> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<LikeListBean> likeList) {
        this.likeList = likeList;
    }

    public static class CommentListBean {
        /**
         * code : PL20170718811271493
         * content : ��
         * parentCode : TZ20170718810583132
         * status : B
         * commer : U201707072142255578821
         * photo : IOS_1499434974402131_350_311.jpg
         * nickname : 小妮子
         * commDatetime : Jul 7, 2017 11:27:14 PM
         * postCode : TZ20170718810583132
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
    }

    public static class LikeListBean {
        /**
         * code : TZJH20170719312423738
         * type : 1
         * postCode : TZ20170718810583132
         * talker : U201707121054372292667
         * nickname : 72292667
         * talkDatetime : Jul 12, 2017 12:42:37 PM
         * photo : IOS_1499439257182751_960_960.jpg
         */

        private String code;
        private String type;
        private String postCode;
        private String talker;
        private String nickname;
        private String talkDatetime;
        private String photo;

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

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getTalker() {
            return talker;
        }

        public void setTalker(String talker) {
            this.talker = talker;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTalkDatetime() {
            return talkDatetime;
        }

        public void setTalkDatetime(String talkDatetime) {
            this.talkDatetime = talkDatetime;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
