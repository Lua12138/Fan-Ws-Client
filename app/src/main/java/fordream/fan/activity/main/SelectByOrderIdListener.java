package fordream.fan.activity.main;

import android.app.Activity;
import android.view.View;

import fordream.fan.service.WsRequestExtra;
import fordream.fan.util.ServiceHelper;

/**
 * Created by forDream on 2015-07-10.
 */
public class SelectByOrderIdListener extends BaseButtonListener implements View.OnClickListener {

    public SelectByOrderIdListener(Activity activity) {
        super(activity);
    }

    @Override
    public void onClick(View v) {
        if (this.checkOrderId()) {
            WsRequestExtra requestExtra = new WsRequestExtra();
            requestExtra.setOrderId(this.getOrderId());
            requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderInf);

            ServiceHelper.toService(this.activity, requestExtra);
            //TODO 应采用AOP方式进行修改
            this.showProcessing();
        }
    }
}
