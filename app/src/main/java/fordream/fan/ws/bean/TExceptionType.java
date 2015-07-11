package fordream.fan.ws.bean;

/**
 * 货物异常类型
 * Created by forDream on 2015-07-07.
 */
public class TExceptionType extends BeanBased {
    private final String FIELD_CODE = "code";
    private final String FIELD_DESC = "desc";
    private final String FIELD_ORD = "ord";

    private String code;
    private String desc;
    private String ord;

    public TExceptionType(Object objParam) {
        this.code = (String) this.getField(objParam, FIELD_CODE, String.class);
        this.desc = (String) this.getField(objParam, FIELD_DESC, String.class);
        this.ord = (String) this.getField(objParam, FIELD_ORD, String.class);
    }

    @Override
    public String toString() {
        return this.desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getOrd() {
        return ord;
    }
}
