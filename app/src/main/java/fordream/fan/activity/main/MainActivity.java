package fordream.fan.activity.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fordream.fan.R;
import fordream.fan.service.WsRequestExtra;
import fordream.fan.service.WsService;
import fordream.fan.util.ServiceHelper;
import fordream.fan.ws.bean.TExceptionType;
import fordream.fan.ws.bean.TOrderLandOpt;
import fordream.fan.ws.bean.TResultOrder;
import fordream.fan.ws.bean.TResultState;
import fordream.fan.ws.bean.TStandResponse;

public class MainActivity extends AppCompatActivity {
    private String selectedOrderId;
    private int listviewId;

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public String getOrderId() {
        return ((EditText) this.findViewById(R.id.txtOrderId)).getText().toString();
    }

    /**
     * 显示进度条
     */
    public void showProcessing() {
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.clientArea);

        ProgressBar progressBar = new ProgressBar(this);

        progressBar.setIndeterminate(true); //不确定具体进度

        layout.removeAllViews(); //移除之前的View
        layout.addView(progressBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.btnSelectByOrderId).setOnClickListener(new SelectByOrderIdListener(this));
        findViewById(R.id.btnSelectByOrderTop10).setOnClickListener(new SelectByOrderTop10Listener(this));
    }

    protected void miniToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout layout = (LinearLayout) findViewById(R.id.clientArea);

        if (this.getIntent().getExtras() == null)
            return;

        int opType = this.getIntent().getExtras().getInt("opType", -1);
        List<Map<String, Object>> lst;
        ListView lv;
        int[] ids;
        String[] from;
        SimpleAdapter adapter;

        //判断数据类型
        TStandResponse response = ServiceHelper.activityReceiveExtras(this.getIntent(), WsService.getWsResultType(opType));

        switch (opType) {
            case WsRequestExtra.OP_TYPE_OrderInf:
            case WsRequestExtra.OP_TYPE_OrderTop10:
                layout.removeAllViews();
                //准备工作
                ids = new int[]{R.id.lvTvAddress, R.id.lvTvAddressId, R.id.lvTvClientName, R.id.lvTvCreateTime, R.id.lvTvOrderId, R.id.lvTvQuality};
                from = new String[]{"lvTvAddress", "lvTvAddressId", "lvTvClientName", "lvTvCreateTime", "lvTvOrderId", "lvTvQuality"};
                lst = new ArrayList<Map<String, Object>>();

                //填写数据
                TResultOrder resultOrder = (TResultOrder) response;

                if (!resultOrder.isResult()) {
                    miniToast("获取失败");
                    miniToast(resultOrder.getResultMessage());
                    return;
                }

                for (int i = 0; i < resultOrder.getData().getItemCount(); i++) {
                    HashMap map = new HashMap();
                    map.put(from[0], resultOrder.getData().getItem(i).getAdd_name());
                    map.put(from[1], resultOrder.getData().getItem(i).getAdd_id());
                    map.put(from[2], resultOrder.getData().getItem(i).getClient_name());
                    map.put(from[3], new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultOrder.getData().getItem(i).getCreate_date_time()));
                    map.put(from[4], resultOrder.getData().getItem(i).getOrderid());
                    map.put(from[5], resultOrder.getData().getItem(i).getQuality());
                    lst.add(map);
                }
                //ListView布局
                lv = new ListView(this);
                this.listviewId = new Random().nextInt(Integer.MAX_VALUE);
                lv.setId(this.listviewId);

                layout.addView(lv);

                adapter = new ListViewAdapter(this, lst, R.layout.activity_listview, from, ids);
                lv.setAdapter(adapter);
                ListViewListener listener = new fordream.fan.activity.main.ListViewListener();

                lv.setOnItemClickListener(listener); //按键监听
                lv.setOnCreateContextMenuListener(listener); //上下文菜单创建监听
                break;
            case WsRequestExtra.OP_TYPE_OrderState:
                TResultState resultState = (TResultState) response;
                new AlertDialog.Builder(this).setMessage(
                        resultState.isResult() ?
                                "Successfully:" + resultState.getState() :
                                "Error:" + resultState.getResultMessage())
                        .setPositiveButton("确定", null).show();
                break;
            case WsRequestExtra.OP_TYPE_OrderLand:
            case WsRequestExtra.OP_TYPE_OrderLandCancel:
            case WsRequestExtra.OP_TYPE_OrderLandExp:
                new AlertDialog.Builder(this).setMessage(response.getResultMessage()).setPositiveButton("确定", null).show();
                break;
            case WsRequestExtra.OP_TYPE_OrderLandOpt:
                layout.removeAllViews();
                lv = new ListView(this);

                lst = new ArrayList<Map<String, Object>>();
                ids = new int[]{R.id.lvTvOrderId, R.id.lvTvState, R.id.lvTvCreateTime, R.id.lvTvMemo, R.id.lvTvReason};
                from = new String[]{"lvTvOrderId", "lvTvState", "lvTvCreateTime", "lvTvMemo", "lvTvReason"};

                TOrderLandOpt result = (TOrderLandOpt) response;

                if (!result.isResult()) {
                    miniToast("获取失败");
                    miniToast(result.getResultMessage());
                    return;
                }

                for (int i = 0; i < result.getLandChks().getItemCount(); i++) {
                    Map map = new HashMap();
                    map.put(from[0], result.getLandChks().getItem(i).getOrderid());
                    map.put(from[1], result.getLandChks().getItem(i).getState());
                    map.put(from[2], result.getLandChks().getItem(i).getCreate_date_time());
                    map.put(from[3], result.getLandChks().getItem(i).getMemo());
                    map.put(from[4], result.getLandChks().getItem(i).getReason());
                    lst.add(map);
                }

                adapter = new ListViewAdapter(this, lst, R.layout.activity_listview2, from, ids);
                lv.setAdapter(adapter);


                layout.addView(lv);
                break;
            case WsRequestExtra.OP_TYPE_OrderExpState:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //上下文菜单选择判断
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.d(this.getClass().getName(), "上下文菜单选择ID：" + item.getItemId());
        Log.d(this.getClass().getName(), "ListView-Position:" + menuInfo.position);

        String orderId = (String) ((HashMap) ((ListView) this.findViewById(R.id.clientArea).findViewById(this.listviewId)).getItemAtPosition(menuInfo.position)).get("lvTvOrderId");

        Log.d(this.getClass().getName(), "OrderId:" + orderId);

        WsRequestExtra requestExtra = new WsRequestExtra();
        requestExtra.setOrderId(orderId);
        switch (item.getItemId()) {
            case 0://到货确认上传
                requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderLand);
                break;
            case 1://取消订单到货确认
                requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderLandCancel);
                break;
            case 3://订单到货流水查询
                requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderLandOpt);
                break;
            case 4://查询订单状态
                requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderState);
                break;
            case 2://订单到货异常确认
                requestExtra.setOpType(WsRequestExtra.OP_TYPE_OrderLandExp);
                break;
        }

        if (item.getItemId() != 2) {
            ServiceHelper.toService(this, requestExtra);
            this.showProcessing();
        } else {
            //显示异常类型供选择
            LinearLayout layout = (LinearLayout) findViewById(R.id.clientArea);
            layout.removeAllViews();
            View view = getLayoutInflater().inflate(R.layout.activity_order_exp_land, null);//异常输入布局文件
            ((TextView) view.findViewById(R.id.expTvOrderId)).setText(orderId);
            Spinner spinner = (Spinner) view.findViewById(R.id.expCbbReason);

            if (WsService.getExceptionListCount() < 0) {
                miniToast("获得异常类型失败，请检查网络后，重启应用再次尝试");
                return false;
            }
            ArrayAdapter<TExceptionType> adapter = new ArrayAdapter<TExceptionType>(this, android.R.layout.simple_spinner_item);
            TExceptionType[] exceptionTypes = new TExceptionType[WsService.getExceptionListCount()];

            //异常类型添加到列表
            for (int i = 0; i < WsService.getExceptionListCount(); i++)
                adapter.add(WsService.getExceptionType(i));

            spinner.setAdapter(adapter);

            layout.addView(view);

            //确定按钮监听器
            view.findViewById(R.id.expBtnSubmit).setOnClickListener(new OrderLandExpListener(this, view));
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ServiceHelper.stopService(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }
}
