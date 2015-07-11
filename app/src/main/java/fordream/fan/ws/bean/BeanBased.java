package fordream.fan.ws.bean;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by forDream on 2015-07-06.
 * 可被序列化的基类，对不使用的方法提供空实现
 */
public abstract class BeanBased extends AttributeContainer implements KvmSerializable {
    protected Object getField(Object objParam, final String fieldName, Class targetClass) {
        if (objParam == null) return null;
        if (!(objParam instanceof SoapObject)) return null;
        SoapObject soapObject = (SoapObject) objParam;
        //依次取属性
        if (soapObject.hasProperty(fieldName)) {
            java.lang.Object obj = soapObject.getProperty(fieldName);
            //针对时间类型单独设置
            if (obj != null && Date.class.isAssignableFrom(targetClass)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.toString().replace('T', ' '));
                    return date;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (obj != null && obj.getClass().equals(SoapPrimitive.class)) {
                //针对一般类型
                SoapPrimitive j = (SoapPrimitive) obj;
                if (j.toString() != null) {
                    //this.result = Boolean.getBoolean(j.toString());
                    try {
                        return targetClass.getConstructor(String.class).newInstance(j.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            } else if (obj != null && obj.getClass().isAssignableFrom(targetClass)) {
                try {
                    return targetClass.getConstructor(String.class).newInstance(obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }


    @Override
    public void setProperty(int arg0, java.lang.Object arg1) {
    }

    @Override
    public String getInnerText() {
        return null;
    }

    @Override
    public void setInnerText(String s) {

    }

    /*以下三个暂时用不到，因此提供空实现*/
    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
