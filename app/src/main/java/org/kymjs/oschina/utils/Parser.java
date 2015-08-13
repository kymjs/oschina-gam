package org.kymjs.oschina.utils;

import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author kymjs (http://www.kymjs.com/) on 8/12/15.
 */
public class Parser {

    public static <T> T xmlToBean(Class<T> type, String xml) {
        T data = null;
        try {
            XStream xStream = new XStream(new DomDriver("UTF-8"));
            xStream.processAnnotations(type);
            data = (T) xStream.fromXML(xml);
        } catch (Exception e) {
            try {
                data = type.newInstance();
            } catch (Exception ee) {
            } finally {
                Log.e("kymjs", "xml解析异常");
            }
        }
        return data;
    }
}
