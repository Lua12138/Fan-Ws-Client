package fordream.fan.ws.bean;

/**
 * Created by forDream on 2015-07-07.
 */
public class TResultState extends TStandResponse {
    //private final String FIELD_RESULT = "Result";
    //private final String FIELD_RESULT_MESSAGE = "ResultMessage";
    private final String FIELD_STATE = "State";

    //private boolean result;
    //private String resultMessage;
    private String state;

    public TResultState(Object objParam) {
        super(objParam);
        //this.result = (boolean) this.getField(objParam, FIELD_RESULT, Boolean.class);
        //this.resultMessage = (String) this.getField(objParam, FIELD_RESULT_MESSAGE, String.class);
        this.state = (String) this.getField(objParam, FIELD_STATE, String.class);
    }
/*
    public boolean isResult() {
        return result;
    }

    public String getResultMessage() {
        return resultMessage;
    }
*/
    public String getState() {
        return state;
    }
}
