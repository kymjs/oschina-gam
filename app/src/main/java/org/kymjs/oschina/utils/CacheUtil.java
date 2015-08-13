package org.kymjs.oschina.utils;

import org.kymjs.kjframe.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象保存
 *
 * @author kymjs (http://www.kymjs.com/) on 6/18/15.
 */
public class CacheUtil {

    public static final void save(File saveFile, Serializable o) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

            fos = new FileOutputStream(saveFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(oos, fos);
        }
    }

    public static final Object read(File saveFile) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        try {
            fis = new FileInputStream(saveFile);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (Exception e) {
            object = null;
        } finally {
            FileUtils.closeIO(ois, fis);
        }
        return object;
    }
}
