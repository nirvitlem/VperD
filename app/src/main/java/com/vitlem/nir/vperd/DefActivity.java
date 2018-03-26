package com.vitlem.nir.vperd;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DefActivity extends AppCompatActivity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String PREFS_NAME = "com.vitlem.nir.vperd.DefActivity";
    private static final String PREF_PREFIX_KEY = "DefActivity_";
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();
    private static ListView lv;
    private int itemPosition;
    private String itemValue;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    //private static final int REQUEST_COARSE_LOCATION=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def);
        ((CheckBox)findViewById(R.id.radioButton)).setChecked(false);
        findViewById(R.id.editText_X).setEnabled(false);
        findViewById(R.id.editText_Y).setEnabled(false);
        findViewById(R.id.DisFromPT).setEnabled(false);
        findViewById(R.id.radioButton).setOnClickListener(mOnClickFListener);
        findViewById(R.id.A_button).setOnClickListener(mOnClickAddListener);
        findViewById(R.id.S_button).setOnClickListener(mOnClickSListener);
        findViewById(R.id.D_button).setOnClickListener(mOnClickDListener);
        lv = (ListView)findViewById(R.id.L_VIEW);
        lv.setOnItemClickListener(mOnClicklListener);
        adapter = new ArrayAdapter<String>(DefActivity.this,android.R.layout.simple_expandable_list_item_1,listItems);
        lv.setAdapter(adapter);
       /* if (!loadTitlePref(DefActivity.this ,1).isEmpty())
        {
            adapter.addAll(loadTitlePref(DefActivity.this ,1));
        }
*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!loadTitlePref(DefActivity.this ,1).isEmpty())
        {
            adapter.addAll(loadTitlePref(DefActivity.this ,1));
        }
    }

    View.OnClickListener mOnClickFListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DefActivity.this;

            CheckBox rb = (CheckBox)findViewById(R.id.radioButton);

            if (!rb.isChecked()) {

                findViewById(R.id.editText_X).setEnabled(false);
                findViewById(R.id.editText_Y).setEnabled(false);
                findViewById(R.id.DisFromPT).setEnabled(false);
                rb.setChecked(false);
            }
            else
            {
                findViewById(R.id.editText_X).setEnabled(true);
                findViewById(R.id.editText_Y).setEnabled(true);
                findViewById(R.id.DisFromPT).setEnabled(true);
                rb.setChecked(true);
            }
            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MainAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);


        }
    };

    View.OnClickListener mOnClickAddListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DefActivity.this;

            TextView tX = (TextView)findViewById(R.id.editText_X);
            TextView tY = (TextView)findViewById(R.id.editText_Y);
            TextView tT = (TextView)findViewById(R.id.editText_T);
            TextView tD = (TextView)findViewById(R.id.DisFromPT);



            if (tX.isEnabled())
            {
                listItems.add(tT.getText() + "#" + tX.getText() + "#" + tY.getText() + "#" + tD.getText());
                //adapter.addAll(listItems);
                adapter.notifyDataSetChanged();
            }
            else
            {
                listItems.add(tT.getText() + "#0#0#0");
                //adapter.addAll(listItems);
                adapter.notifyDataSetChanged();
            }

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MainAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);


        }
    };

    View.OnClickListener mOnClickSListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DefActivity.this;

            saveTitlePref(DefActivity.this,1,listItems);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("Saved Data");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            DefActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MainAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);


        }
    };

    View.OnClickListener mOnClickDListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = DefActivity.this;

            adapter.remove(itemValue);
            adapter.notifyDataSetChanged();

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MainAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);


        }
    };

     AdapterView.OnItemClickListener mOnClicklListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {


            // ListView Clicked item index
            itemPosition = position;

            // ListView Clicked item value
            itemValue = (String) lv.getItemAtPosition(position);
            parent.setSelection(position);
            adapter.notifyDataSetChanged();

        }
    };

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, ArrayList<String> l_view) {

        List<String>  tasksList = new ArrayList<String>();
        Set<String> tasksSet = new HashSet<String>(l_view);
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putStringSet(PREF_PREFIX_KEY + appWidgetId, tasksSet);
        prefs.apply();


    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static List<String> loadTitlePref(Context context, int appWidgetId) {
        List<String> tasksList = new ArrayList<String>();
        try {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Set<String> tasksSet = prefs.getStringSet(PREF_PREFIX_KEY + appWidgetId, new HashSet<String>());
            tasksList = new ArrayList<String>(tasksSet);
            editor.commit();
            return tasksList;
        }
        catch (Exception e)
        {
            Log.d("loadTitlePref",e.getMessage());
        }
        return tasksList;
    }


}
