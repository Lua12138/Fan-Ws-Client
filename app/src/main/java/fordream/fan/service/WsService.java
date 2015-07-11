package fordream.fan.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fordream.fan.activity.main.MainActivity;
import fordream.fan.util.ServiceHelper;
import fordream.fan.ws.FanWebservice;
import fordream.fan.ws.bean.TExceptionType;
import fordream.fan.ws.bean.TList;
import fordream.fan.ws.bean.TOrderExceptionState;
import fordream.fan.ws.bean.TOrderLandOpt;
import fordream.fan.ws.bean.TResultOrder;
import fordream.fan.ws.bean.TResultState;
import fordream.fan.ws.bean.TStandResponse;

/**
 * Created by forDream on 2015-07-07.
 * app调用WebService代理，所使用的Service对象
 */
public class WsService extends IntentService {

    //TODO 不应设置为public，为编码方便暂时设置
    public static TList<TExceptionType> exceptionTypeList; //异常类型字段
    private static Map<Integer, Class> opTarget; //WebService对应于activity
    private static Map<Integer, Class> opResultTarget; //WebService对应的返回值类型

    public WsService() {
        super("fordream-Service-ws");
        //this.binder = new WsServiceBinder();
        if (opTarget == null) {
            opTarget = new HashMap<Integer, Class>();

            opTarget.put(WsRequestExtra.OP_TYPE_OrderInf, MainActivity.class);
            opTarget.put(WsRequestExtra.OP_TYPE_OrderState, MainActivity.class);

            opTarget.put(WsRequestExtra.OP_TYPE_OrderTop10, MainActivity.class);

            opTarget.put(WsRequestExtra.OP_TYPE_OrderLand, MainActivity.class);
            opTarget.put(WsRequestExtra.OP_TYPE_OrderLandCancel, MainActivity.class);
            opTarget.put(WsRequestExtra.OP_TYPE_OrderLandOpt, MainActivity.class);

            opTarget.put(WsRequestExtra.OP_TYPE_OrderLandExp, MainActivity.class);
        }
        if (opResultTarget == null) {
            opResultTarget = new HashMap<Integer, Class>();
            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderInf, TResultOrder.class);
            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderState, TResultState.class);

            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderTop10, TResultOrder.class);

            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderLand, TStandResponse.class);
            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderLandCancel, TStandResponse.class);
            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderLandOpt, TOrderLandOpt.class);

            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderLandExp, TStandResponse.class);

            opResultTarget.put(WsRequestExtra.OP_TYPE_OrderExpState, TOrderExceptionState.class);
        }
    }

    /**
     * 获取异常状态列表总数
     *
     * @return 列表不存在返回-1
     */
    public static int getExceptionListCount() {
        if (exceptionTypeList == null) return -1;
        return exceptionTypeList.getItemCount();
    }

    /**
     * 获取一条异常状态信息
     *
     * @param index 下标
     * @return 列表不存在或下标越界返回null
     */
    public static TExceptionType getExceptionType(int index) {
        if (exceptionTypeList == null) return null;
        return exceptionTypeList.getItem(index);
    }

    /**
     * 获得操作类型对应的本地包装类型返回值
     *
     * @param opType 操作类型
     * @return 本地包装类型Class对象
     */
    public static Class getWsResultType(int opType) {
        if (opResultTarget == null) return null;
        return opResultTarget.get(opType);
    }

    /**
     * 。。。。简单的包装
     *
     * @param bundle
     * @param obj
     */
    protected void littleMethod(Bundle bundle, Object obj) {
        bundle.putSerializable(obj.getClass().getName(), (Serializable) obj);
    }

    @Override
    protected void onHandleIntent(Intent requestIntent) {
        //不能放在构造函数能，构造函数将在UI线程执行
        if (exceptionTypeList == null) {
            FanWebservice ws = FanWebservice.getInstance();
            TOrderExceptionState state = ws.getOrderExpState();
            if (state.isResult()) {
                exceptionTypeList = state.getExceptionTypes();
            }
        }

        final String TAG = this.getClass().getPackage().getName();
        Log.v(TAG, "onHandleIntent-Action:" + requestIntent.getAction());

        WsRequestExtra requestExtra = ServiceHelper.serviceReceiveExtras(requestIntent);

        if (requestExtra == null) {
            Log.w(TAG, "Bundle Type Mission.");
            return;
        }

        FanWebservice ws = FanWebservice.getInstance();
        Bundle responseBundle = new Bundle();
        Object responseObj = null;

        Log.d(TAG, "操作类型:" + requestExtra.getOpType());
        //选择操作
        switch (requestExtra.getOpType()) {
            case WsRequestExtra.OP_TYPE_OrderInf:
                //responseBundle.putSerializable(TResultOrder.class.getPackage().getName(), ws.getOrderInf(requestExtra.getOrderId()));
                //littleMethod(responseBundle, ws.getOrderInf(requestExtra.getOrderId()));
                responseObj = ws.getOrderInf(requestExtra.getOrderId());
                break;
            case WsRequestExtra.OP_TYPE_OrderTop10:
                //ws.getOrderTop10();
                //littleMethod(responseBundle, ws.getOrderTop10());
                responseObj = ws.getOrderTop10();
                break;
            case WsRequestExtra.OP_TYPE_OrderState:
                //ws.getOrderState(requestExtra.getOrderId());
                //littleMethod(responseBundle, ws.getOrderState(requestExtra.getOrderId()));
                responseObj = ws.getOrderState(requestExtra.getOrderId());
                break;
            case WsRequestExtra.OP_TYPE_OrderLand:
                //ws.confirmOrder(requestExtra.getOrderId());
                //littleMethod(responseBundle, ws.confirmOrder(requestExtra.getOrderId()));
                responseObj = ws.confirmOrder(requestExtra.getOrderId());
                break;
            case WsRequestExtra.OP_TYPE_OrderLandCancel:
                //ws.cancleOrder(requestExtra.getOrderId());
                //littleMethod(responseBundle, ws.cancleOrder(requestExtra.getOrderId()));
                responseObj = ws.cancleOrder(requestExtra.getOrderId());
                break;
            case WsRequestExtra.OP_TYPE_OrderLandExp:
                //ws.confirmOrderLandExp(requestExtra.getOrderId(), requestExtra.getReason(), requestExtra.getMemo());
                //littleMethod(responseBundle, ws.confirmOrderLandExp(requestExtra.getOrderId(), requestExtra.getReason(), requestExtra.getMemo()));
                responseObj = ws.confirmOrderLandExp(requestExtra.getOrderId(), requestExtra.getReason(), requestExtra.getMemo());
                break;
            case WsRequestExtra.OP_TYPE_OrderLandOpt:
                //ws.confirmOrderLandOpt(requestExtra.getOrderId());
                //littleMethod(responseBundle, ws.confirmOrderLandOpt(requestExtra.getOrderId()));
                responseObj = ws.confirmOrderLandOpt(requestExtra.getOrderId());
                break;
            case WsRequestExtra.OP_TYPE_OrderExpState:
                ws.getOrderExpState();
                break;
            default:
                Log.w(TAG, "unknow op-type:" + requestExtra.getOpType());
                break;
        }

        //添加WS结果
        responseBundle.putSerializable(responseObj.getClass().getName(), (Serializable) responseObj);
        responseBundle.putInt("opType", requestExtra.getOpType()); //附加操作类型
        ServiceHelper.toActivity(this, this.opTarget.get(requestExtra.getOpType()), responseBundle);
        /*
        //返回结果
        Intent responseIntent = new Intent();
        responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //指定目标地址
        responseIntent.setClass(this, this.opTarget.get(requestExtra.getOpType()));

        WsResponseExtra responseExtra = new WsResponseExtra();
        responseExtra.setOpType(requestExtra.getOpType()); //返回具体操作类型
        responseBundle.putSerializable(WsResponseExtra.class.getName(), responseExtra);

        responseIntent.putExtras(responseBundle);

        this.startActivity(responseIntent);
*/
        //this.stopSelf();
    }
}