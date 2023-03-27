package net.gsantner.markor.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TermuxUtils {
    public static boolean runCommand(final Context context, final String command, final String workdir) {
        String welcome = command.isEmpty() ? "echo" : "echo \"$ " + command + "\"";
        String bash = command.isEmpty() ? "bash -il" : " ; read -s -n1 -p [exit]";

        Intent intent = new Intent();
        intent.setClassName("com.termux", "com.termux.app.RunCommandService");
        intent.setAction("com.termux.RUN_COMMAND");
        intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/login");
        intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"-c", welcome + " ; " + command + bash});
        intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", workdir);
        intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intent.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
