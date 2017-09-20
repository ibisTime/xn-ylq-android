package com.chengdai.ehealthproject.model.healthcircle.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/21.
 */

public class ArticleModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 2
     * totalPage : 1
     * list : [{"code":"TZ20170617211053911","title":"标题","content":"出来吧，神龙！","pic":"iOS_1498014377031708_852_640.jpg","status":"B","address":"浙江省.杭州市.余杭区","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 11:05:39 AM","location":"1","orderNo":0,"sumComment":3,"sumLike":1,"isDZ":"1","isSC":"0","commentList":[{"code":"PL20170617211235018","content":"哈哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:50 AM","postCode":"TZ20170617211053911"},{"code":"PL20170617211234373","content":"哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:43 AM","postCode":"TZ20170617211053911"},{"code":"PL20170617211233587","content":"哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:35 AM","postCode":"TZ20170617211053911"}],"likeList":[{"code":"TZJH20170617201163187","type":"1","postCode":"TZ20170617211053911","talker":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","talkDatetime":"Jun 21, 2017 1:16:31 PM"}]},{"code":"TZ20170617201184524","title":"标题","content":"哈哈","pic":"iOS_1498022352539626_2448_3264.jpg","status":"B","publisher":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","publishDatetime":"Jun 21, 2017 1:18:45 PM","location":"1","orderNo":0,"sumComment":0,"sumLike":0,"isDZ":"0","isSC":"0","commentList":[],"likeList":[]}]
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
         * sumComment : 3
         * sumLike : 1
         * isDZ : 1
         * isSC : 0
         * commentList : [{"code":"PL20170617211235018","content":"哈哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:50 AM","postCode":"TZ20170617211053911"},{"code":"PL20170617211234373","content":"哈哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:43 AM","postCode":"TZ20170617211053911"},{"code":"PL20170617211233587","content":"哈哈","parentCode":"TZ20170617211053911","status":"B","commer":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","commDatetime":"Jun 21, 2017 11:23:35 AM","postCode":"TZ20170617211053911"}]
         * likeList : [{"code":"TZJH20170617201163187","type":"1","postCode":"TZ20170617211053911","talker":"U201706161050240444339","photo":"IOS_1498014255280108_1024_576.jpg","nickname":"qqq","talkDatetime":"Jun 21, 2017 1:16:31 PM"}]
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

        public static class CommentListBean implements Parcelable {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.content);
                dest.writeString(this.parentCode);
                dest.writeString(this.status);
                dest.writeString(this.commer);
                dest.writeString(this.photo);
                dest.writeString(this.nickname);
                dest.writeString(this.commDatetime);
                dest.writeString(this.postCode);
            }

            public CommentListBean() {
            }

            protected CommentListBean(Parcel in) {
                this.code = in.readString();
                this.content = in.readString();
                this.parentCode = in.readString();
                this.status = in.readString();
                this.commer = in.readString();
                this.photo = in.readString();
                this.nickname = in.readString();
                this.commDatetime = in.readString();
                this.postCode = in.readString();
            }

            public static final Creator<CommentListBean> CREATOR = new Creator<CommentListBean>() {
                @Override
                public CommentListBean createFromParcel(Parcel source) {
                    return new CommentListBean(source);
                }

                @Override
                public CommentListBean[] newArray(int size) {
                    return new CommentListBean[size];
                }
            };
        }

        public static class LikeListBean implements Parcelable {
            /**
             * code : TZJH20170617201163187
             * type : 1
             * postCode : TZ20170617211053911
             * talker : U201706161050240444339
             * photo : IOS_1498014255280108_1024_576.jpg
             * nickname : qqq
             * talkDatetime : Jun 21, 2017 1:16:31 PM
             */

            private String code;
            private String type;
            private String postCode;
            private String talker;
            private String photo;
            private String nickname;
            private String talkDatetime;

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

            public String getTalkDatetime() {
                return talkDatetime;
            }

            public void setTalkDatetime(String talkDatetime) {
                this.talkDatetime = talkDatetime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.type);
                dest.writeString(this.postCode);
                dest.writeString(this.talker);
                dest.writeString(this.photo);
                dest.writeString(this.nickname);
                dest.writeString(this.talkDatetime);
            }

            public LikeListBean() {
            }

            protected LikeListBean(Parcel in) {
                this.code = in.readString();
                this.type = in.readString();
                this.postCode = in.readString();
                this.talker = in.readString();
                this.photo = in.readString();
                this.nickname = in.readString();
                this.talkDatetime = in.readString();
            }

            public static final Creator<LikeListBean> CREATOR = new Creator<LikeListBean>() {
                @Override
                public LikeListBean createFromParcel(Parcel source) {
                    return new LikeListBean(source);
                }

                @Override
                public LikeListBean[] newArray(int size) {
                    return new LikeListBean[size];
                }
            };
        }

        public ListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.pic);
            dest.writeString(this.status);
            dest.writeString(this.address);
            dest.writeString(this.publisher);
            dest.writeString(this.photo);
            dest.writeString(this.nickname);
            dest.writeString(this.publishDatetime);
            dest.writeString(this.location);
            dest.writeInt(this.orderNo);
            dest.writeInt(this.sumComment);
            dest.writeInt(this.sumLike);
            dest.writeString(this.isDZ);
            dest.writeString(this.isSC);
            dest.writeTypedList(this.commentList);
            dest.writeTypedList(this.likeList);
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.pic = in.readString();
            this.status = in.readString();
            this.address = in.readString();
            this.publisher = in.readString();
            this.photo = in.readString();
            this.nickname = in.readString();
            this.publishDatetime = in.readString();
            this.location = in.readString();
            this.orderNo = in.readInt();
            this.sumComment = in.readInt();
            this.sumLike = in.readInt();
            this.isDZ = in.readString();
            this.isSC = in.readString();
            this.commentList = in.createTypedArrayList(CommentListBean.CREATOR);
            this.likeList = in.createTypedArrayList(LikeListBean.CREATOR);
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
        dest.writeTypedList(this.list);
    }

    public ArticleModel() {
    }

    protected ArticleModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<ArticleModel> CREATOR = new Parcelable.Creator<ArticleModel>() {
        @Override
        public ArticleModel createFromParcel(Parcel source) {
            return new ArticleModel(source);
        }

        @Override
        public ArticleModel[] newArray(int size) {
            return new ArticleModel[size];
        }
    };
}
