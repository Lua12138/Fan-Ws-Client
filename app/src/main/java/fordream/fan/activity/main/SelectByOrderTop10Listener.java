package fordream.fan.activity.main;

import android.app.Activity;
import android.view.View;

import fordream.fan.service.WsRequestExtra;
import fordream.fan.util.ServiceHelper;

/**
 * Created by forDream on 2015-07-10.
 */
public class SelectByOrderTop10Listener extends BaseButtonListener implements View.OnClickListener {
    public SelectByOrderTop10Listener(Activity activity) {
        super(activity);
    }

    @Override
    public void onClick(View v) {
        if (this.checkOrderId()) {
            WsRequestExtra requestExtra = new WsRequestExtra();
            requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderTop10);

            ServiceHelper.toService(this.activity, requestExtra);

            this.showProcessing();
        }
    }
}
