package com.liaoxb.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * @Description 获取资源配置文件内容
 * @Author liaoxb
 * @Date 2017/9/27 13:58:58
 */
public class propertiesUtil {

    private static Log log = LogFactory.getLog(propertiesUtil.class);

    /**
     * 根据key 获取Properties配置文件中对应的值<br/>
     * <span style="color:red;">
     *     注意：1、通过ResourceBundle方式，得到的值会出现中文乱码<br/>
     *           2、getBundle（String baseName）中的baseName为不加后缀的配置文件名称
     *     </span>
     * @param key
     * @Date 2017/9/27 14:03
     */
    public static String getValue(String key){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        return resourceBundle.getString(key);
    }

    public static String getPropertiesValue(String key){
        Properties prop = new Properties();
        InputStream in = propertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(new InputStreamReader(in, "UTF-8"));
            String value = prop.get(key).toString().trim();
            return value;
        } catch (IOException e) {
            log.error(e.getMessage()+" 获取配置文件信息异常");
            e.printStackTrace();
        }
        return null;
    }

}
