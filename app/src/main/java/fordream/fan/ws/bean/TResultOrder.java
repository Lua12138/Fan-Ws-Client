package fordream.fan.ws.bean;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

/**
 * Created by forDream on 2015-07-07.
 */
public class TResultOrder extends TStandResponse implements Serializable {
    private final String FIELD_DATA = "Data";
    private final String FIELD_DATAS = "Datas";

    private TList<TData> data; //复合类型

    public TResultOrder(Object objParam) {
        super(objParam);

        SoapObject soapObject = (SoapObject) objParam;
        if (soapObject.hasProperty(FIELD_DATA))
            soapObject = (SoapObject) soapObject.getProperty(FIELD_DATA);
        if (soapObject.hasProperty(FIELD_DATAS))
            this.data = new TList<TData>(soapObject.getProperty(FIELD_DATAS), TData.class);
    }

    public void setData(TList<TData> data) {
        this.data = data;
    }

    public TList<TData> getData() {
        //if(this.data==null)
        //    this.data=new TList<TData>(null,null);
        return data;
    }
}
