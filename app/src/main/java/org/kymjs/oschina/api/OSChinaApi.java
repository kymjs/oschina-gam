package org.kymjs.oschina.api;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.oschina.AppConfig;

/**
 * @author kymjs (http://www.kymjs.com/) on 8/17/15.
 */
public class OSChinaApi {
    static KJHttp kjh = new KJHttp();

    public static void getFriendGroupList(int page, HttpCallBack handler) {
        HttpParams params = new HttpParams();
        params.put("uid", AppConfig.INSTANCE$.getUser().getId());
        params.put("catalog", 1);
        params.put("pageSize", 20);
        params.put("pageIndex", page);
        kjh.get("http://www.oschina.net/action/api/active_list", params, handler);
    }
}
