package fordream.fan.service;

import java.io.Serializable;

/**
 * Created by forDream on 2015-07-08.<br/>
 * Service响应表述层调用结果通用类
 */
public class WsResponseExtra implements Serializable {
    private int opType;

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }
}
