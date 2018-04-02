package com.vitlem.nir.vperd;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.List;

/**
 * Created by NirV on 18/09/2017.
 */

public class CustomPhoneStateListener extends PhoneStateListener {

    public static String LOG_TAG = "PhoneStateListener";


    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        Log.i(LOG_TAG, "onCellInfoChanged: " + cellInfo);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        if (incomingNumber == null) incomingNumber = "null";

        if (MainAppWidget.c != null) Log.i("MainAppWidget.c", "!null");
        else Log.i("MainAppWidget.c", "null");
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:

                if (MainAppWidget.c != null) {
                    Log.i("MainAppWidget.c", "CALL_STATE_IDLE!null");
                    View v = RelativeLayout.inflate(MainAppWidget.c, R.layout.main_app_widget, null);
                    if (v!=null) Log.i("MainAppWidget.c", "v!null");
                    MainAppWidget.lv = (ListView) v.findViewById(R.id.lView);
                    if (MainAppWidget.lv!=null) Log.i("MainAppWidget.c", "MainAppWidget.lv!null");
                    MainAppWidget.adapter = new ArrayAdapter<String>(MainAppWidget.c, android.R.layout.simple_list_item_1, MainAppWidget.listItemsLV);
                    if (MainAppWidget.adapter!=null) Log.i("MainAppWidget.c", " MainAppWidget.adapter!null");
                    MainAppWidget.lv.setAdapter(MainAppWidget.adapter);
                    MainAppWidget.listItemsLV.add("onCallStateChanged: CALL_STATE_IDLE " + System.currentTimeMillis());
                   // MainAppWidget.adapter.addAll(MainAppWidget.listItemsLV);
                    MainAppWidget.adapter.notifyDataSetChanged();
                   // MainAppWidget.remoteViews.setTextViewText(R.id.appwidget_text,"MainAppWidget.c != null");
                    ComponentName thisWidget = new ComponentName(MainAppWidget.c, MainAppWidget.class);
                    AppWidgetManager manager = AppWidgetManager.getInstance(MainAppWidget.c);
                    int appWidgetIds[] = manager.getAppWidgetIds(new ComponentName(MainAppWidget.c, MainAppWidget.class));
                    manager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.lView);
                    manager.updateAppWidget(thisWidget, MainAppWidget.remoteViews);
                   Log.i("MainAppWidget.c", " end if");
                }

                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_IDLE");
                MyService.StopPalyPlayer();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_RINGING");
                Log.i(LOG_TAG, "incomingNumber: " + incomingNumber);
                //MyService.runGetVolumep();

                for (String item : MainAppWidget.listItems) {
                   /* if (MainAppWidget.c != null) {
                        View v = RelativeLayout.inflate(MainAppWidget.c, R.layout.main_app_widget, null);
                        MainAppWidget.lv = (ListView) v.findViewById(R.id.lView);
                        MainAppWidget.adapter = new ArrayAdapter<String>(MainAppWidget.c, android.R.layout.simple_list_item_1, MainAppWidget.listItemsLV);
                        MainAppWidget.lv.setAdapter(MainAppWidget.adapter);

                        MainAppWidget.listItemsLV.add(incomingNumber + " N " + System.currentTimeMillis());
                        //adapter.addAll(listItemsLV);
                        MainAppWidget.adapter.notifyDataSetChanged();
                    }*/

                   /* Context context = MainAppWidget.c;
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main_app_widget);
                    ComponentName thisWidget = new ComponentName(context, MainAppWidget.class);
                    remoteViews.setTextViewText(R.id.appwidget_text, incomingNumber + " " + System.currentTimeMillis());
                    appWidgetManager.updateAppWidget(thisWidget, remoteViews);*/

                    if (incomingNumber.equals(item.split("#")[0].toString())) {

                      /*  if (MainAppWidget.c != null) {
                            View v = RelativeLayout.inflate(MainAppWidget.c, R.layout.main_app_widget, null);
                            MainAppWidget.lv = (ListView) v.findViewById(R.id.lView);
                            MainAppWidget.adapter = new ArrayAdapter<String>(MainAppWidget.c, android.R.layout.simple_list_item_1, MainAppWidget.listItemsLV);
                            MainAppWidget.lv.setAdapter(MainAppWidget.adapter);
                            MainAppWidget.listItemsLV.add(incomingNumber + " Y " + System.currentTimeMillis());
                            //adapter.addAll(listItemsLV);
                            MainAppWidget.adapter.notifyDataSetChanged();
                        }*/
                        // remoteViews.setTextViewText(R.id.appwidget_text,"!! " + incomingNumber + " " +System.currentTimeMillis());
                        MyService.runGetVolumep();
                    }
                }

                //if (incomingNumber.equals("0543205519") || incomingNumber.equals("0506406883") || incomingNumber.equals("0522945298") || incomingNumber.equals("089719890")  ) MyService.runGetVolumep();

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(LOG_TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");
                break;
            default:
                Log.i(LOG_TAG, "UNKNOWN_STATE: " + state);
                break;
        }
    }


}
