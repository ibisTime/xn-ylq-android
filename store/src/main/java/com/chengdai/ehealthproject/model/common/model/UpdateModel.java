package com.chengdai.ehealthproject.model.common.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/8/4.
 */

public class UpdateModel {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 5
     * totalPage : 1
     * list : [{"id":87,"type":"3","ckey":"androidDownload","cvalue":"http://a.app.qq.com/o/simple.jsp?pkgname=com.chengdai.ehealthproject","note":"android下载地址","updater":"admin","updateDatetime":"Aug 4, 2017 11:17:06 AM","remark":"android下载地址","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"id":86,"type":"3","ckey":"iosDownload","cvalue":"https://itunes.apple.com/cn/app/健康e购/id1256905137?mt=8","note":"iOS下载地址","updater":"admin","updateDatetime":"Aug 4, 2017 11:17:06 AM","remark":"IOS下载地址","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"id":85,"type":"3","ckey":"note","cvalue":"更新说明","note":"更新说明","updater":"admin","updateDatetime":"Aug 4, 2017 11:17:06 AM","remark":"更新说明","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"id":84,"type":"3","ckey":"forceUpdate","cvalue":"1","note":"是否强制更新","updater":"admin","updateDatetime":"Aug 4, 2017 11:17:06 AM","remark":"是否强制更新","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"},{"id":83,"type":"3","ckey":"version","cvalue":"1.1.3","note":"最新版本号","updater":"admin","updateDatetime":"Aug 4, 2017 11:17:06 AM","remark":"最新版本号","companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]
     */

    private List<IntroductionInfoModel> list;

    public List<IntroductionInfoModel> getList() {
        return list;
    }

    public void setList(List<IntroductionInfoModel> list) {
        this.list = list;
    }
}
