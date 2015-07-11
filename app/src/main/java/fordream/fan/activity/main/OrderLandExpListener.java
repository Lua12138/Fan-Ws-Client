package fordream.fan.activity.main;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import fordream.fan.R;
import fordream.fan.service.WsRequestExtra;
import fordream.fan.util.ServiceHelper;
import fordream.fan.ws.bean.TExceptionType;

/**
 * Created by forDream on 2015-07-10.
 */
public class OrderLandExpListener extends BaseButtonListener implements View.OnClickListener {
    private View parent;

    public OrderLandExpListener(Activity activity, View view) {
        super(activity);
        this.parent = view;
    }

    @Override
    public void onClick(View v) {
        String orderId = ((TextView) parent.findViewById(R.id.expTvOrderId)).getText().toString();
        Spinner spinner = (Spinner) parent.findViewById(R.id.expCbbReason);

        TExceptionType exceptionType = (TExceptionType) spinner.getSelectedItem();

        String memo = ((EditText) parent.findViewById(R.id.expTxtMemo)).getText().toString();

        WsRequestExtra requestExtra = new WsRequestExtra();
        requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderLandExp);
        requestExtra.setOrderId(orderId);
        requestExtra.setReason(exceptionType.getCode());
        requestExtra.setMemo(memo);

        ServiceHelper.toService(this.activity, requestExtra);

        this.showProcessing();
    }
}
