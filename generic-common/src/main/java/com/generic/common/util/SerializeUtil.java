package com.generic.common.util;

import java.io.*;

/**
 * @author liuyuzhu
 * @description:  序列化工具类
 * @date 2018/1/20 1:52
 */
public class SerializeUtil {
    private static LogUtil logger = LogUtil.getLogger(SerializeUtil.class);

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream bis = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            bis = new ObjectInputStream(new BufferedInputStream(bais));
            return bis.readObject();

        } catch (ClassNotFoundException e) {
            logger.error("Failed to deserialize object type",e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to deserialize",e.getMessage());
        } finally {
            try {
                bais.close();
                bis.close();
            } catch (IOException e) {
                logger.error("Failed to close serialize", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 序列化
     * @param obj
     * @return
     */
    public static byte[] serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e) {
            logger.error("Failed to serialize", e.getMessage());
        } finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e) {
                logger.error("Failed to close serialize", e.getMessage());
            }
        }
        return baos.toByteArray();
    }
}
