package fordream.fan.ws.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by forDream on 2015-07-07.
 */
public class TLandChk extends BeanBased implements Serializable {
    private final String FIELD_ORDER_ID = "orderid";
    private final String FIELD_STATE = "state";
    private final String FIELD_REASON_ = "reason";
    private final String FIELD_MEMO = "memo";
    private final String FIELD_CREATE_DATE_TIME = "create_date_time";

    private String orderid;
    private String state;
    private String reason;
    private String memo;
    private Date create_date_time;

    public TLandChk(Object objParam) {
        this.orderid = (String) this.getField(objParam, FIELD_ORDER_ID, String.class);
        this.state = (String) this.getField(objParam, FIELD_STATE, String.class);
        this.reason = (String) this.getField(objParam, FIELD_REASON_, String.class);
        this.memo = (String) this.getField(objParam, FIELD_MEMO, String.class);
        this.create_date_time = (Date) this.getField(objParam, FIELD_CREATE_DATE_TIME, Date.class);
    }

    public String getOrderid() {
        return orderid;
    }

    public String getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public String getMemo() {
        return memo;
    }

    public Date getCreate_date_time() {
        return create_date_time;
    }
}
