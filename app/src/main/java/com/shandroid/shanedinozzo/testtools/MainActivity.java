package com.shandroid.shanedinozzo.testtools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity {
    String[] cpuFreqs = {
            "300 MHz",
            "422 MHz",
            "652 MHz",
            "729 MHz",
            "883 MHz",
            "960 MHz",
            "1.037 GHz",
            "1.19 GHz",
            "1.267 GHz",
            "1.498 GHz",
            "1.574 GHz",
            "1.728 GHz",
            "1.958 GHz",
            "2.266 GHz"};

    Spinner cpuMaxSpinner;
    Spinner cpuMinSpinner;
    int checkmax = 0;
    int checkmin = 0;
    int selectedMaxFreq, selectedMinFreq, convertedMaxFreq, convertedMinFreq;
    String result, convertedMinFreqString, convertedMaxFreqString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _firstAlert();
        _cpuFreqTextViewUpdate();
        _setMaxCpuFreq();
        _setMinCpuFreq();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public void _checkRoot(View view) {
        TextView gotRoot = (TextView) findViewById(R.id.got_root);

        if (RootTools.isRootAvailable()) {
            gotRoot.setText("Your device is rooted!\n");
        } else {
            gotRoot.setText("Your device is not rooted!\n");
        }
    }

    public void _removeApps(View v) {
        Intent removeApps = new Intent(this, RemoveAppListActivity.class);
        this.startActivity(removeApps);
    }

    public void _rebootDevice(View view) {
        Toast toast = Toast.makeText(this, "This function is not yet implemented!\n" +
                        "It's coming soon!",
                Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void _powerOff(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you really want to power off your device?\n" +
        "All unsaved data will be lost!");
        builder1.setTitle("POWER OFF");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            @SuppressWarnings("UnusedDeclaration") // <-- Remove IntelliJ warning
                                    Process powerOff = Runtime.getRuntime().exec(
                                    new String[]{"su", "-c", "reboot", "-p"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void _checkBusybox(View view) {
        TextView gotBusybox = (TextView) findViewById(R.id.got_busybox);

        if (RootTools.isBusyboxAvailable()) {
            gotBusybox.setText("You've got busybox installed\n");
        } else {
            gotBusybox.setText("You haven't got busybox installed\n");
        }
    }

    public void _firstAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("To use this app, your device must be rooted!" +
                " Otherwise you can't access its functions!");
        builder.setTitle("WARNING!");
        builder.setCancelable(true);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public String _readCpuFreq(){
        ProcessBuilder cmd;
        result = "";

        try{
            String[] currentCpuFreq = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
            cmd = new ProcessBuilder(currentCpuFreq);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] read = new byte[1024];
            while(in.read(read) != -1){
                result = result + new String(read);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public void _cpuFreqTextViewUpdate(){
        final TextView cpuClockSpeed = (TextView) findViewById(R.id.cpu_clock_speed);
        Thread cpuFreqUpdate = new Thread(){
            @Override
            public void run(){
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        Thread.sleep(600);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String str = _readCpuFreq();
                                String strok = str.trim();
                                cpuClockSpeed.setText(strok + " Khz");
                            } //run()
                        }); //MainActivity.this
                    } //WHILE
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } //try-catch
            } //run()
        }; //thread
        cpuFreqUpdate.start();
    } //cpuFreqTextViewUpdate

    public int _setMaxCpuFreq() {
        cpuMaxSpinner = (Spinner) findViewById(R.id.cpu_max_freq);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.rowlayout, cpuFreqs);
        cpuMaxSpinner.setAdapter(adapter);
        cpuMaxSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        checkmax = checkmax + 1;
                        if (checkmax > 1) {
                            selectedMaxFreq = cpuMaxSpinner.getSelectedItemPosition();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        onVisibleBehindCanceled();
                    }
                });
        return selectedMaxFreq;
    }

    public int _setMinCpuFreq() {
        cpuMinSpinner = (Spinner) findViewById(R.id.cpu_min_freq);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.rowlayout, cpuFreqs);
        cpuMinSpinner.setAdapter(adapter2);
        cpuMinSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        checkmin = checkmin + 1;
                        if(checkmin > 1) {
                            selectedMinFreq = cpuMinSpinner.getSelectedItemPosition();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        onVisibleBehindCanceled();
                    }
                });
        return selectedMinFreq;
    }

    public void _setSelectedMaxFreqs(View view) {
        Context context = getApplicationContext();
        _convertMaxToKhz();
        Toast toast = Toast.makeText(context, "echo \"" + convertedMaxFreqString +
                        "\" > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq",
                Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(
                android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void _setSelectedMinFreqs(View view) {
        Context context = getApplicationContext();
        _convertMinToKhz();
        Toast toast = Toast.makeText(context, "echo \"" + convertedMinFreqString  +
                        "\" > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq",
                Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(
                android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public String _convertMinToKhz() {
        switch (cpuFreqs[+selectedMinFreq]) {
            case "300 MHz":
                convertedMinFreq = 300000;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "422 MHz":
                convertedMinFreq = 422400;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "652 MHz":
                convertedMinFreq = 652800;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "729 MHz":
                convertedMinFreq = 729600;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "883 MHz":
                convertedMinFreq = 883200;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "960 MHz":
                convertedMinFreq = 960000;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.037 GHz":
                convertedMinFreq = 1036800;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.19 GHz":
                convertedMinFreq = 1190400;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.267 GHz":
                convertedMinFreq = 1267200;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.498 GHz":
                convertedMinFreq = 1497600;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.574 GHz":
                convertedMinFreq = 1574400;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.728 GHz":
                convertedMinFreq = 1728000;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "1.958 GHz":
                convertedMinFreq = 1958400;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
            case "2.266 GHz":
                convertedMinFreq = 2265600;
                convertedMinFreqString = String.valueOf(convertedMinFreq);
                break;
        }
        return convertedMinFreqString;
    }

    public String _convertMaxToKhz() {
        switch (cpuFreqs[+selectedMaxFreq]) {
            case "300 MHz":
                convertedMaxFreq = 300000;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "422 MHz":
                convertedMaxFreq = 422400;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "652 MHz":
                convertedMaxFreq = 652800;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "729 MHz":
                convertedMaxFreq = 729600;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "883 MHz":
                convertedMaxFreq = 883200;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "960 MHz":
                convertedMaxFreq = 960000;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.037 GHz":
                convertedMaxFreq = 1036800;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.19 GHz":
                convertedMaxFreq = 1190400;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.267 GHz":
                convertedMaxFreq = 1267200;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.498 GHz":
                convertedMaxFreq = 1497600;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.574 GHz":
                convertedMaxFreq = 1574400;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.728 GHz":
                convertedMaxFreq = 1728000;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "1.958 GHz":
                convertedMaxFreq = 1958400;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
            case "2.266 GHz":
                convertedMaxFreq = 2265600;
                convertedMaxFreqString = String.valueOf(convertedMaxFreq);
                break;
        }
        return convertedMaxFreqString;
    }
}
