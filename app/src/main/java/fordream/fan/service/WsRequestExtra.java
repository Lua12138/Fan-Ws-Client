package fordream.fan.service;

import java.io.Serializable;

/**
 * Created by forDream on 2015-07-08.<br/>
 * 表述层调用Service通用类
 */
public class WsRequestExtra implements Serializable {
    private static final int BASE = 2046;
    /**
     * 获取订单信息
     */
    public static final int OP_TYPE_OrderInf = BASE + 1;
    /**
     * 前10订单信息
     */
    public static final int OP_TYPE_OrderTop10 = BASE + 2;
    /**
     * 订单状态
     */
    public static final int OP_TYPE_OrderState = BASE + 3;
    /**
     * 订单到货确认上传
     */
    public static final int OP_TYPE_OrderLand = BASE + 4;
    /**
     * 取消订单到货确认
     */
    public static final int OP_TYPE_OrderLandCancel = BASE + 5;
    /**
     * 订单到货异常确认
     */
    public static final int OP_TYPE_OrderLandExp = BASE + 6;
    /**
     * 订单到货流水查询
     */
    public static final int OP_TYPE_OrderLandOpt = BASE + 7;
    /**
     * 订单异常状态字典
     */
    public static final int OP_TYPE_OrderExpState = BASE + 8;
    private int opType; //操作类型
    private String orderId; //订单号
    private String reason;
    private String memo;
    private Object sender;//Service的调用者

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
