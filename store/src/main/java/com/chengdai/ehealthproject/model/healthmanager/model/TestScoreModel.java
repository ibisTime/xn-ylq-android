package com.chengdai.ehealthproject.model.healthmanager.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/24.
 */

public class TestScoreModel {

    /**
     * pageNO : 1
     * start : 0
     * pageSize : 1
     * totalCount : 2
     * totalPage : 2
     * list : [{"code":"CR20170617508071580","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 8:07:15 PM","title":"健康评分","content":"亚健康","score":60,"wjKind":"questionare_kind_1","wjCode":"WJ20170617110201878"}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * code : CR20170617508071580
         * userId : U201706161048120928135
         * createDatetime : Jun 24, 2017 8:07:15 PM
         * title : 健康评分
         * content : 亚健康
         * score : 60
         * wjKind : questionare_kind_1
         * wjCode : WJ20170617110201878
         */

        private String code;
        private String userId;
        private String createDatetime;
        private String title;
        private String content;
        private int score;
        private String wjKind;
        private String wjCode;

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

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getWjKind() {
            return wjKind;
        }

        public void setWjKind(String wjKind) {
            this.wjKind = wjKind;
        }

        public String getWjCode() {
            return wjCode;
        }

        public void setWjCode(String wjCode) {
            this.wjCode = wjCode;
        }
    }
}
