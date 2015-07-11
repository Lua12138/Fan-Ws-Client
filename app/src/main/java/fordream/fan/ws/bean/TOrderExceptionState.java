package fordream.fan.ws.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * 对应于OrderExpState方法的数据类型
 * Created by forDream on 2015-07-07.
 */
public class TOrderExceptionState extends TStandResponse {
    private final String FIELD_DATA = "Data";
    private final String FIELD_DATAS = "Datas";
    private TList<TExceptionType> exceptionTypes;

    public TList<TExceptionType> getExceptionTypes() {
        return exceptionTypes;
    }

    public TOrderExceptionState(Object objParam) {
        super(objParam);

        SoapObject soapObject = (SoapObject) objParam;
        if (soapObject.hasProperty(FIELD_DATA))
            soapObject = (SoapObject) soapObject.getProperty(FIELD_DATA);
        if (soapObject.hasProperty(FIELD_DATAS))
            this.exceptionTypes = new TList<TExceptionType>(soapObject.getProperty(FIELD_DATAS), TExceptionType.class);
    }
}
