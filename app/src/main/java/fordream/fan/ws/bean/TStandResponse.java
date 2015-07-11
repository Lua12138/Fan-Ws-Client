package fordream.fan.ws.bean;

import java.io.Serializable;

/**
 * WebService的标准返回类型，即只有Result/ResultMessage两个参数
 * Created by forDream on 2015-07-07.
 */
public class TStandResponse extends BeanBased implements Serializable {
    private final String FIELD_RESULT = "Result";
    private final String FIELD_RESULT_MESSAGE = "ResultMessage";
    private boolean result;
    private String resultMessage;

    public TStandResponse(Object objParam) {
        if (objParam == null) return;
        this.result = (boolean) this.getField(objParam, FIELD_RESULT, Boolean.class);
        this.resultMessage = (String) this.getField(objParam, FIELD_RESULT_MESSAGE, String.class);
    }

    public boolean isResult() {
        return result;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
