package org.cc98.mycc98.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.cc98.mycc98.service.base.BaseService;

public class VersionCheckService extends BaseService {
    public VersionCheckService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
