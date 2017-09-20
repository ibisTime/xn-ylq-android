package com.chengdai.ehealthproject.model.tabmy.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/27.
 */

public class MyTestHistoryListModel  {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 10
     * totalPage : 1
     * list : [{"code":"CR2017061780134121","userId":"U201706161048120928135","createDatetime":"Jun 27, 2017 1:34:12 PM","title":"生素D缺乏症的风险","content":"结果极好","score":100,"wjKind":"questionare_kind_2","wjCode":"WJ20170617409563281"},{"code":"CR20170617505394645","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:39:46 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"},{"code":"CR20170617505332259","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:33:22 PM","title":"生素D缺乏症的风险","content":"结果良好","score":55,"wjKind":"questionare_kind_2","wjCode":"WJ20170617409563281"},{"code":"CR20170617505331873","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:33:18 PM","title":"生素D缺乏症的风险","content":"结果较差","score":40,"wjKind":"questionare_kind_2","wjCode":"WJ20170617409563281"},{"code":"CR20170617505325897","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:32:58 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"},{"code":"CR20170617505324853","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:32:48 PM","title":"生素D缺乏症的风险","content":"结果良好","score":55,"wjKind":"questionare_kind_2","wjCode":"WJ20170617409563281"},{"code":"CR20170617505320011","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:32:00 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"},{"code":"CR2017061750531541","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:31:54 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"},{"code":"CR20170617505264394","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:26:43 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"},{"code":"CR20170617505225324","userId":"U201706161048120928135","createDatetime":"Jun 24, 2017 5:22:53 PM","title":"花千骨中谁与你最配","content":"1","score":45,"wjKind":"questionare_kind_2","wjCode":"WJ20170617008324690"}]
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
         * code : CR2017061780134121
         * userId : U201706161048120928135
         * createDatetime : Jun 27, 2017 1:34:12 PM
         * title : 生素D缺乏症的风险
         * content : 结果极好
         * score : 100
         * wjKind : questionare_kind_2
         * wjCode : WJ20170617409563281
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
