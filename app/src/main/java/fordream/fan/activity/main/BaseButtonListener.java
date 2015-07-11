package fordream.fan.activity.main;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fordream.fan.R;

/**
 * Created by forDream on 2015-07-10.
 */
public abstract class BaseButtonListener implements View.OnClickListener {
    protected Activity activity;

    public BaseButtonListener(Activity activity) {
        this.activity = activity;
    }

    protected String getOrderId() {
        return ((EditText) this.activity.findViewById(R.id.txtOrderId)).getText().toString();
    }

    /**
     * 检查OrderId是否合法，并给予提示
     *
     * @return 不合法返回false
     */
    protected boolean checkOrderId() {
        String orderId = this.getOrderId();
        boolean b = false;
        if (orderId != null)
            b = true;
        else
            Toast.makeText(this.activity, R.string.toast_check_order_id, Toast.LENGTH_LONG).show();

        return b;
    }

    protected void showProcessing() {
        ((MainActivity) this.activity).showProcessing();
    }
}
