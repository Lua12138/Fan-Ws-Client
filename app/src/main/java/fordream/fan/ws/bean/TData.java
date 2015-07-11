package fordream.fan.ws.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by forDream on 2015-07-07.
 */
public class TData extends BeanBased implements Serializable {
    private final String FIELD_ORDER_ID = "orderid";
    private final String FIELD_CLIENT_NAME = "client_name";
    private final String FIELD_ADD_ID = "add_id";
    private final String FIELD_ADD_NAME = "add_name";
    private final String FIELD_QUALITY = "quality";
    private final String FIELD_CREATE_DATE_TIME = "create_date_time";

    private String orderid;
    private String client_name;
    private String add_id;
    private String add_name;
    private float quality;
    private Date create_date_time;

    public TData(Object objParam) {
        this.orderid = (String) this.getField(objParam, FIELD_ORDER_ID, String.class);
        this.client_name = (String) this.getField(objParam, FIELD_CLIENT_NAME, String.class);
        this.add_id = (String) this.getField(objParam, FIELD_ADD_ID, String.class);
        this.add_name = (String) this.getField(objParam, FIELD_ADD_NAME, String.class);
        Object obj = this.getField(objParam, FIELD_QUALITY, Float.class); //防止空指针异常
        this.quality = obj == null ? 0 : (float) obj;
        this.create_date_time = (Date) this.getField(objParam, FIELD_CREATE_DATE_TIME, Date.class);
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public Date getCreate_date_time() {
        return create_date_time;
    }

    public void setCreate_date_time(Date create_date_time) {
        this.create_date_time = create_date_time;
    }
}
