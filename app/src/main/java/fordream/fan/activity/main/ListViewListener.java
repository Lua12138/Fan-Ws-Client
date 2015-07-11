package fordream.fan.activity.main;

import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import fordream.fan.R;

/**
 * Created by forDream on 2015-07-10.
 */
public class ListViewListener implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String orderId = ((TextView) view.findViewById(R.id.lvTvOrderId)).getText().toString();
        Log.d(this.getClass().getName(), "orderId:" + orderId);
    }

    /**
     * 创建上下文菜单
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final int groupId = 0;
        int itemId = 0;
        menu.add(groupId, itemId, itemId++, R.string.listview_context_OrderLand);
        menu.add(groupId, itemId, itemId++, R.string.listview_context_OrderLandCancel);
        menu.add(groupId, itemId, itemId++, R.string.listview_context_OrderLandExp);
        menu.add(groupId, itemId, itemId++, R.string.listview_context_OrderLandOpt);
        menu.add(groupId, itemId, itemId++, R.string.listview_context_OrderState);
    }
}
