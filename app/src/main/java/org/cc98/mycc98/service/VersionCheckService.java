package org.cc98.mycc98.service;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.allenliu.versionchecklib.core.AVersionService;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.google.gson.Gson;

import org.cc98.mycc98.R;

public class VersionCheckService extends AVersionService {
    public static final String TAG = "VersionCheckService";


    public VersionCheckService() {
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        Log.i(TAG, response);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            int currentVersionCode = pInfo.versionCode;
            UpdateInfomation newupdateInfo = jsonToClass(response, UpdateInfomation.class);
            if (newupdateInfo.getLatestVersionCode() > currentVersionCode) {
                //need to update;
                showVersionDialog(newupdateInfo.getUrl(),
                        newupdateInfo.getReleaseNotes().getTitle(),
                        newupdateInfo.getReleaseNotes().getMsgcontent());
            }

        } catch (Exception e) {
            Log.e(TAG, "Package Error", e);
        }


    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public static void initVersionCheckerService(Application application) {

        VersionParams.Builder builder = new VersionParams.Builder()
                .setRequestUrl(application.getString(R.string.service_update_versionchecker_url))
                .setDownloadAPKPath(application.getCacheDir().getAbsolutePath()+"/")
                .setPauseRequestTime(300*1000) //every 300seconds retry pull update info.
                .setService(VersionCheckService.class);

        AllenChecker.startVersionCheck(application, builder.build());
    }

    public static <T> T jsonToClass(String jsonData, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonData, cls);
        return t;
    }

}


