package com.shandroid.shanedinozzo.testtools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _firstAlert();
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
}
