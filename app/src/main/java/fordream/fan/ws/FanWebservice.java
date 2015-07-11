package fordream.fan.ws;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.HashMap;
import java.util.Map;

import fordream.fan.ws.bean.TOrderExceptionState;
import fordream.fan.ws.bean.TOrderLandOpt;
import fordream.fan.ws.bean.TResultOrder;
import fordream.fan.ws.bean.TResultState;
import fordream.fan.ws.bean.TStandResponse;

/**
 * Created by forDream on 2015-07-06.<br/>
 * dancing.koding.io：WebService客户代理类<br/>
 * 该类代理方法返回值恒不为null，若某个字段转换失败，仅仅该字段为null（或默认值），其他字段不受影响
 */
public class FanWebservice extends WebserviceClientSupport {
    //WebService命名空间
    protected final static String nameSpace = "http://dancing.koding.io/";
    //wsdl地址
    protected final static String wsdlUri = "http://dancing.koding.io/ServiceFanApp.asmx?wsdl=0";
    private static FanWebservice wsInstance; //单例实体
    protected final String endPoint = "http://dancing.koding.io/ServiceFanApp.asmx";
    protected final String token = "4C82C67D87BA194C8E";

    protected FanWebservice() {
        super(nameSpace, wsdlUri, true);
        this.setSoapEnvelopeLevel(SoapEnvelope.VER12);
    }

    /**
     * @return 获得一个FanWebservice对象实例
     */
    public static FanWebservice getInstance() {
        if (wsInstance == null)
            wsInstance = new FanWebservice();
        return wsInstance;
    }

    /**
     * @return 获取带有令牌的参数列表
     */
    protected Map<String, String> getParamMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Token", token);
        return map;
    }

    /**
     * 调试方法，生成环境不使用
     *
     * @param soap
     */
    protected void analysis(SoapObject soap) {
        if (soap.getPropertyCount() == 1) {
            Log.i("输出结果", soap.getPropertyAsString(0));
            return;
        }
        for (int i = 0; i < soap.getPropertyCount(); i++) {
            analysis((SoapObject) soap.getProperty(i));
        }
    }

    /**
     * 创建参数对象
     *
     * @param name  参数名
     * @param type  参数类型
     * @param value 参数值
     * @return
     */
    protected PropertyInfo getPropertyInfo(String name, Class type, Object value) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.namespace = this.nameSpace;
        propertyInfo.name = name;
        propertyInfo.type = type;
        propertyInfo.setValue(value != null ? value : SoapPrimitive.NullSkip);
        return propertyInfo;
    }

    /**
     * @return 获得一个带有token标记的PropertyInfo
     */
    protected PropertyInfo getTokenPropertyInfo() {
        return this.getPropertyInfo("Token", String.class, this.token);
    }

    /**
     * 自己添加的属性应从下标1开始，下标0恒为token
     *
     * @param parametersCount 集合数量（包括token标记在内的数量）
     * @return 获得一个带有token标记的属性集合
     */
    protected PropertyInfo[] getStandPropertyInfos(int parametersCount) {
        PropertyInfo[] propertyInfos = new PropertyInfo[parametersCount];
        propertyInfos[0] = getTokenPropertyInfo();
        return propertyInfos;
    }

    /**
     * @return 查询前十条未到货订单信息
     */
    public TResultOrder getOrderTop10() {
        final int paramCount = 1; //参数数量
        final String methodName = "OrderTop10"; //调用的远程方法名

        final String returnTypeString = "ServiceResultOrder"; //返回的根节点元素，即服务端的类型名称

        PropertyInfo[] propertyInfos = this.getStandPropertyInfos(paramCount);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        //对象转换
        return new TResultOrder(response);
    }

    /**
     * 查询订单信息
     *
     * @param orderId 待查询的订单号
     * @return 调用失败返回null
     */
    public TResultOrder getOrderInf(String orderId) {
        final int paramCount = 2; //参数数量
        final String methodName = "OrderInf";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = this.getPropertyInfo("OrderId", String.class, orderId);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TResultOrder(response);
    }

    /**
     * 查询指定订单当前状态
     *
     * @param orderId 订单号
     * @return 调用失败返回null
     */
    public TResultState getOrderState(String orderId) {
        final int paramCount = 2; //参数数量
        final String methodName = "OrderState";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = this.getPropertyInfo("OrderId", String.class, orderId);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TResultState(response);
    }

    /**
     * 到货确认
     *
     * @param orderId 订单号
     * @return 调用失败返回null
     */
    public TStandResponse confirmOrder(String orderId) {
        final int paramCount = 2; //参数数量
        final String methodName = "OrderLand";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = this.getPropertyInfo("OrderId", String.class, orderId);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TStandResponse(response);
    }

    /**
     * 取消到货确认
     *
     * @param orderId 订单号
     * @return 调用失败返回null
     */
    public TStandResponse cancleOrder(String orderId) {
        final int paramCount = 2; //参数数量
        final String methodName = "OrderLandCancel";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = this.getPropertyInfo("OrderId", String.class, orderId);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TStandResponse(response);
    }

    /**
     * 到货异常类型字段
     *
     * @return
     */
    public TOrderExceptionState getOrderExpState() {
        final int paramCount = 1; //参数数量
        final String methodName = "OrderExpState";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TOrderExceptionState(response);
    }

    /**
     * 到货异常确认
     *
     * @param orderId 订单号
     * @param reason  到货异常类型（可以从OrderExpState中获取到）
     * @param memo    异常备注信息
     * @return
     */
    public TStandResponse confirmOrderLandExp(String orderId, String reason, String memo) {
        final int paramCount = 4; //参数数量
        final String methodName = "OrderLandExp";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = getPropertyInfo("OrderId", String.class, orderId);
        propertyInfos[2] = getPropertyInfo("Reason", String.class, reason);
        propertyInfos[3] = getPropertyInfo("Memo", String.class, memo);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TStandResponse(response);
    }

    /**
     * 到货确认流水账
     *
     * @param orderId 订单号
     * @return
     */
    public TOrderLandOpt confirmOrderLandOpt(String orderId) {
        final int paramCount = 2; //参数数量
        final String methodName = "OrderLandOpt";

        PropertyInfo[] propertyInfos = getStandPropertyInfos(paramCount);
        propertyInfos[1] = this.getPropertyInfo("OrderId", String.class, orderId);

        SoapObject response = this.doMethod(methodName, propertyInfos);

        return new TOrderLandOpt(response);
    }
}
