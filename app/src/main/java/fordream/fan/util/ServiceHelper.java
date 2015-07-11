package fordream.fan.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import fordream.fan.service.WsRequestExtra;
import fordream.fan.service.WsService;
import fordream.fan.ws.bean.TStandResponse;

/**
 * Activity与Service交互辅助类
 */
public class ServiceHelper {
    /**
     * 启动服务
     *
     * @param from         启动服务的组件
     * @param to           准备启动的服务
     * @param requestExtra 传递给Service的附加信息
     */
    public static void toService(Context from, Class to, WsRequestExtra requestExtra) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        Bundle bundle = new Bundle();

        bundle.putSerializable(requestExtra.getClass().getName(), requestExtra);

        intent.putExtras(bundle);

        from.startService(intent);
    }

    /**
     * 启动服务（WsService）
     *
     * @param from         启动服务的组件
     * @param requestExtra 传递给Service的附加信息
     */
    public static void toService(Context from, WsRequestExtra requestExtra) {
        toService(from, WsService.class, requestExtra);
    }

    /**
     * 停止WsService服务
     *
     * @param from 停止服务的组件
     */
    public static void stopService(Context from) {
        Intent intent = new Intent();
        intent.setClass(from, WsService.class);
        from.stopService(intent);
    }

    /**
     * Service调用，用于接收附加数据（标准类型）<br>
     * 对于非标准类型，该方法不能接收
     *
     * @param intent 接收到的Intent对象
     */
    public static WsRequestExtra serviceReceiveExtras(Intent intent) {
        Bundle bundle = intent.getExtras();
        Object preExtra = bundle.get(WsRequestExtra.class.getName());

        WsRequestExtra requestExtra = null;

        if (preExtra != null && preExtra instanceof WsRequestExtra)
            requestExtra = (WsRequestExtra) preExtra;

        return requestExtra;
    }

    /**
     * Activity调用，返回附加数据中指定的对象
     *
     * @param intent      Activity接收到的Intent对象
     * @param targetClass 欲获取的目标类型
     * @return 返回目标类型
     */
    public static TStandResponse activityReceiveExtras(Intent intent, Class targetClass) {
        Bundle bundle = intent.getExtras();
        return (TStandResponse) bundle.get(targetClass.getName());
    }

    /**
     * Service反馈Activity方法
     *
     * @param from     Service自身指针
     * @param to       目标Activity
     * @param response 附加数据
     */
    public static void toActivity(Context from, Class to, Object response) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(response.getClass().getName(), (Serializable) response);

        toActivity(from, to, bundle);
    }

    /**
     * Service反馈Activity方法
     *
     * @param from   Service自身指针
     * @param to     目标Activity
     * @param bundle 附加数据
     */
    public static void toActivity(Context from, Class to, Bundle bundle) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(from, to);

        intent.putExtras(bundle);

        from.startActivity(intent);
    }
}
