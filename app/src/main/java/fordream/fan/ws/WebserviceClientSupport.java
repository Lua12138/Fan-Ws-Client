package fordream.fan.ws;

import android.util.Log;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * 调用WebService的基础提供类<br/>
 * Created by forDream on 2015-07-06.
 */
public abstract class WebserviceClientSupport {
    /**
     * WebService命名空间
     */
    protected final String nameSpace;
    /**
     * WSDL地址
     */
    protected final String wsdlUri;
    /**
     * 是否调用.Net服务
     */
    protected final boolean isDotNet;
    /**
     * 是否输出网络层调试信息
     */
    protected boolean isDebug;
    /**
     * SOAP规范级别
     */
    protected int SoapEnvelopeLevel;

    /**
     * @param nameSpace 命名空间
     * @param wsdlUri   WSDL-URI
     * @param isDotNet  是否为.Net服务
     */
    public WebserviceClientSupport(String nameSpace, String wsdlUri, boolean isDotNet) {
        this.nameSpace = nameSpace;
        this.wsdlUri = wsdlUri;
        this.isDotNet = isDotNet;
        this.isDebug = false;
    }

    /**
     * 设置SOAP规范级别，只能设置一次
     *
     * @param soapEnvelopeLevel
     */
    public void setSoapEnvelopeLevel(int soapEnvelopeLevel) {
        SoapEnvelopeLevel = soapEnvelopeLevel;
    }

    /**
     * 设置是否输出网络层调试信息，默认为false
     *
     * @param debug
     */
    public void isDebug(boolean debug) {
        this.isDebug = debug;
    }

    /**
     * 调用远程方法
     *
     * @param method 远程方法的名称
     * @return 失败返回null
     */
    protected SoapObject doMethod(String method) {
        return this.doMethod(method, null);
    }

    /**
     * 调用远程方法
     *
     * @param method         远程方法的名称
     * @param propertyInfoes 调用方法的参数列表
     * @return 失败返回null
     */
    protected SoapObject doMethod(String method, PropertyInfo[] propertyInfoes) {
        SoapObject requestSoap = new SoapObject(nameSpace, method);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(this.SoapEnvelopeLevel);
        envelope.bodyOut = requestSoap;
        envelope.dotNet = this.isDotNet;

        if (propertyInfoes != null)
            for (PropertyInfo propertyInfo : propertyInfoes)
                requestSoap.addProperty(propertyInfo);

        HttpTransportSE transport = new HttpTransportSE(wsdlUri);
        transport.debug = this.isDebug;

        try {
            List headerList = transport.call(null, envelope, null);

            if (this.isDebug)
                Log.i("调试dump", transport.responseDump);

            SoapObject result = (SoapObject) envelope.getResponse();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }
}
