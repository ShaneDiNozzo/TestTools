package com.shandroid.shanedinozzo.testtools;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RemoveAppListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_app_list);
        _appExist();
    }

    public void _appExist() {
        Button aospBrowserButton = (Button) findViewById(R.id.aosp_browser_button);
        File aospBrowser_0 = new File("/system/app/Browser.apk");
        File aospBrowser_1 = new File("/system/app/Browser/Browser.apk");
        if (aospBrowser_0.exists() || aospBrowser_1.exists()) {
            aospBrowserButton.setText("AOSP Browser");
        } else {
            aospBrowserButton.setEnabled(false);
            aospBrowserButton.setText("AOSP Browser is already removed!");
        }

        Button aospEmailButton = (Button) findViewById(R.id.aosp_email_button);
        File aospEmail_0 = new File("/system/app/Email.apk");
        File aospEmail_1 = new File("/system/app/Email/Email.apk");
        if (aospEmail_0.exists() || aospEmail_1.exists()) {
            aospEmailButton.setText("AOSP Email");
        } else {
            aospEmailButton.setEnabled(false);
            aospEmailButton.setText("AOSP Email is already removed!");
        }

        Button cmFileManagerButton = (Button) findViewById(R.id.cm_filemanager_button);
        File cmFileManager_0 = new File("/system/app/CMFileManager.apk");
        File cmFileManager_1 = new File("/system/app/CMFileManager/CMFileManager.apk");
        if (cmFileManager_0.exists() || cmFileManager_1.exists()) {
            cmFileManagerButton.setText("CM File Manager");
        } else {
            cmFileManagerButton.setEnabled(false);
            cmFileManagerButton.setText("CM File Manager is already removed!");
        }

        Button aospMessagingButton = (Button) findViewById(R.id.aosp_messaging_button);
        File aospMessaging = new File("/system/priv-app/Mms.apk");
        if (aospMessaging.exists()) {
            aospMessagingButton.setText("AOSP Messaging");
        } else {
            aospMessagingButton.setEnabled(false);
            aospMessagingButton.setText("AOSP Messaging is already removed!");
        }

        Button omniSwitchButton = (Button) findViewById(R.id.omniswitch_button);
        File omniSwitch = new File("/system/priv-app/OmniSwitch.apk");
        if (omniSwitch.exists()) {
            omniSwitchButton.setText("Omni Switch");
        } else {
            omniSwitchButton.setEnabled(false);
            omniSwitchButton.setText("Omni Switch is already removed!");
        }

        Button aospSoundRecorderButton = (Button) findViewById(R.id.aosp_soundrecorder_button);
        File aospSoundRecorder = new File("/system/app/SoundRecorder.apk");
        if (aospSoundRecorder.exists()) {
            aospSoundRecorderButton.setText("AOSP Sound Recorder");
        } else {
            aospSoundRecorderButton.setEnabled(false);
            aospSoundRecorderButton.setText("AOSP Sound Recorder is already removed!");
        }

        Button torchButton = (Button) findViewById(R.id.torch_button);
        File torch = new File("/system/app/Torch.apk");
        if (torch.exists()) {
            torchButton.setText("Torch");
        } else {
            torchButton.setEnabled(false);
            torchButton.setText("Torch is already removed!");
        }

        Button printSpoolerButton = (Button) findViewById(R.id.print_spooler_button);
        File printSpooler = new File("/system/app/PrintSpooler.apk");
        if (printSpooler.exists()) {
            printSpoolerButton.setText("Print Spooler");
        } else {
            printSpoolerButton.setEnabled(false);
            printSpoolerButton.setText("Print Spooler is already removed!");
        }

        Button aospCalendarButton = (Button) findViewById(R.id.aosp_calendar_button);
        File aospCalendar = new File("/system/app/Calendar/Calendar.apk");
        if (aospCalendar.exists()) {
            aospCalendarButton.setText("AOSP Calendar");
        } else {
            aospCalendarButton.setEnabled(false);
            aospCalendarButton.setText("AOSP Calendar is already removed!");
        }

        Button aospVoiceDialerButton = (Button) findViewById(R.id.aosp_voice_dialer_button);
        File aospVoiceDialer = new File("/system/priv-app/VoiceDialer/VoiceDialer.apk");
        if (aospVoiceDialer.exists()) {
            aospVoiceDialerButton.setText("AOSP Voice Dialer");
        } else {
            aospVoiceDialerButton.setEnabled(false);
            aospVoiceDialerButton.setText("AOSP Voice Dialer is already removed!");
        }

        Button aospCameraButton = (Button) findViewById(R.id.aosp_camera_button);
        File aospCamera_1 = new File("/system/app/Camera2/Camera2.apk");
        if (aospCamera_1.exists()) {
            aospCameraButton.setText("AOSP Camera");
        } else {
            aospCameraButton.setEnabled(false);
            aospCameraButton.setText("AOSP Camera is already removed!");
        }
    }

    public void _aosp_browser(View view) {
        Button aospBrowserButton = (Button) findViewById(R.id.aosp_browser_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/Browser.apk /system/app/Browser.ap || " +
                            "mv /system/app/Browser/Browser.apk /system/app/Browser/Browser.ap");
            RootTools.getShell(true).add(command);
            aospBrowserButton.setText("AOSP Browser removed successfully");
            aospBrowserButton.setEnabled(false);
        } catch (RootDeniedException | TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    public void _aosp_email(View view) {
        Button aospEmailButton = (Button) findViewById(R.id.aosp_email_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/Email.apk /system/app/Email.ap || " +
                            "mv /system/app/Email/Email.apk /system/app/Email/Email.ap");
            RootTools.getShell(true).add(command);
            aospEmailButton.setText("AOSP Email removed successfully");
            aospEmailButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _cm_filemanager(View view) {
        Button cmFileManagerButton = (Button) findViewById(R.id.cm_filemanager_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/CMFileManager.apk /system/app/CMFileManager.ap || " +
                            "mv /system/app/CMFileManager/CMFileManager.apk " +
                            "/system/app/CMFileManager/CMFileManager.ap");
            RootTools.getShell(true).add(command);
            cmFileManagerButton.setText("CM File Manager removed successfully");
            cmFileManagerButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _aosp_messaging(View view) {
        Button aospMessagingButton = (Button) findViewById(R.id.aosp_messaging_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/priv-app/Mms.apk /system/priv-app/Mms.ap");
            RootTools.getShell(true).add(command);
            aospMessagingButton.setText("AOSP Messaging removed successfully");
            aospMessagingButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _omniswitch(View view) {
        Button omniSwitchButton = (Button) findViewById(R.id.omniswitch_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/priv-app/OmniSwitch.apk /system/priv-app/OmniSwitch.ap");
            RootTools.getShell(true).add(command);
            omniSwitchButton.setText("Omni Switch removed successfully");
            omniSwitchButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _aosp_soundrecorder(View view) {
        Button aospSoundRecorderButton = (Button) findViewById(R.id.aosp_soundrecorder_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/SoundRecorder.apk /system/app/SoundRecorder.ap");
            RootTools.getShell(true).add(command);
            aospSoundRecorderButton.setText("AOSP Sound Recorder removed successfully");
            aospSoundRecorderButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _torch(View view) {
        Button torchButton = (Button) findViewById(R.id.torch_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/Torch.apk /system/app/Torch.ap");
            RootTools.getShell(true).add(command);
            torchButton.setText("Torch removed successfully");
            torchButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _print_spooler(View view) {
        Button printSpoolerButton = (Button)findViewById(R.id.print_spooler_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/PrintSpooler.apk /system/app/PrintSpooler.ap");
            RootTools.getShell(true).add(command);
            printSpoolerButton.setText("Print Spooler removed successfully");
            printSpoolerButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _mountSystem(View view) {
        TextView mountSystem = (TextView) findViewById(R.id.mount_system);
        RootTools.remount("/system/", "rw");
        mountSystem.setText(R.string.mount_system_utana);
    }

    public void _aosp_calendar(View view) {
        Button aospCalendarButton = (Button)findViewById(R.id.aosp_calendar_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/PrintSpooler.apk /system/app/PrintSpooler.ap || " +
            "mv /system/app/Calendar/Calendar.apk /system/app/Calendar/Calendar.ap");
            RootTools.getShell(true).add(command);
            aospCalendarButton.setText("AOSP Calendar removed successfully");
            aospCalendarButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _aosp_voice_dialer(View view) {
        Button aospVoiceDialerButton = (Button)findViewById(R.id.aosp_voice_dialer_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/VoiceDialer.apk /system/app/VoiceDialer.ap || " +
                            "mv /system/priv-app/VoiceDialer/VoiceDialer.apk" +
                            " /system/priv-app/VoiceDialer/VoiceDialer.ap");
            RootTools.getShell(true).add(command);
            aospVoiceDialerButton.setText("AOSP Calendar removed successfully");
            aospVoiceDialerButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _aosp_camera(View view) {
        Button aospCameraButton = (Button)findViewById(R.id.aosp_camera_button);
        try {
            CommandCapture command = new CommandCapture(0,
                    "mv /system/app/Camera2/Camera2.apk /system/app/Camera2/Camera2.ap");
            RootTools.getShell(true).add(command);
            aospCameraButton.setText("AOSP Calendar removed successfully");
            aospCameraButton.setEnabled(false);
        } catch (RootDeniedException | IOException rdeie) {
            rdeie.printStackTrace();
            Toast.makeText(this, rdeie.getMessage(), Toast.LENGTH_LONG).show();
        } catch (TimeoutException te) {
            te.printStackTrace();
            Toast.makeText(this, te.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void _checkForAptoideApk(View view) {
        TextView aptoideDownloadAndInstall = (TextView) findViewById(
                R.id.aptoide_download_and_install);
        File AptoideApk = new File(Environment.getExternalStorageDirectory(),
                "/Download/aptoide5.2.0.2.apk");
        if (AptoideApk.exists()) {
            try {
                CommandCapture command = new CommandCapture(0,
                        "pm install " + AptoideApk);
                RootTools.getShell(true).add(command);
            } catch (RootDeniedException | TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }else {
            aptoideDownloadAndInstall.setText(
                    "The \"aptoide5.2.0.2.apk\" is not in the Download directory!");
        }
    }
}
