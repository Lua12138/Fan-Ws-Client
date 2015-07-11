package fordream.fan.ws.bean;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by forDream on 2015-07-07.
 * 数组类型复合对象
 */
public class TList<T> extends BeanBased implements Serializable {
    private List list;

    public TList(Object objParam, Class targetClass) {
        if (objParam == null) return;
        SoapObject soapObject = null;

        if (objParam instanceof SoapObject)
            soapObject = (SoapObject) objParam;

        if (soapObject == null) return;

        this.list = new ArrayList();
        try {
            for (int i = 0; i < soapObject.getPropertyCount(); i++) {
                /*构造复合对象*/
                this.list.add(targetClass.getConstructor(Object.class).newInstance(soapObject.getProperty(i)));
            }
        } catch (Exception e) {
            //Log.e(this.getClass().getPackage().getName(), e.getMessage());
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.list == null ? -1 : this.list.size();
    }

    public T getItem(int index) {
        return this.list == null ? null : (T) this.list.get(index);
    }

}
