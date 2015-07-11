package fordream.fan.ws.bean;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by forDream on 2015-07-07.
 */
public class TOrderLandOpt extends TStandResponse {
    private final String FIELD_DATA = "Data";
    private final String FIELD_DATAS = "Datas";
    private TList<TLandChk> landChks;

    public TList<TLandChk> getLandChks() {
        return landChks;
    }

    public TOrderLandOpt(Object objParam) {
        super(objParam);

        SoapObject soapObject = (SoapObject) objParam;
        if (soapObject.hasProperty(FIELD_DATA))
            soapObject = (SoapObject) soapObject.getProperty(FIELD_DATA);
        if (soapObject.hasProperty(FIELD_DATAS))
            this.landChks = new TList<TLandChk>(soapObject.getProperty(FIELD_DATAS), TLandChk.class);
    }
}
