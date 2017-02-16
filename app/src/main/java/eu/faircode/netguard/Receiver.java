package eu.faircode.netguard;

/*
    This file is part of NetGuard.

    NetGuard is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    NetGuard is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with NetGuard.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2015-2017 by Marcel Bokhorst (M66B)
*/

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Receiver extends BroadcastReceiver {
    private static final String TAG = "NetGuard.Receiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "Received " + intent);
        Util.logExtras(intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Start service
        try {
            Log.d(TAG, "Boot received. About to start service.");
            if (prefs.getBoolean("enabled", false)) {
                Log.d(TAG, "Boot received. About to start service.");
                ServiceSinkhole.start("receiver", context);
            }
        } catch (Throwable ex) {
            Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
        }

        if (Util.isInteractive(context))
            ServiceSinkhole.reloadStats("receiver", context);
    }
}