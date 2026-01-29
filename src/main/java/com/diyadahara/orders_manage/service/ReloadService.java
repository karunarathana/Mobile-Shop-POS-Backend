package com.diyadahara.orders_manage.service;

import com.diyadahara.orders_manage.config.ReloadStatus;
import com.diyadahara.orders_manage.response.BaseReloadResponse;

public interface ReloadService {
    BaseReloadResponse createReload(String desc, double price, String simType, String status);
    BaseReloadResponse viewReload();
    BaseReloadResponse deleteReload(int reloadId);
    BaseReloadResponse viewReloadByDate(String date);
    BaseReloadResponse updateReload(int reloadId,String desc, String price, String simType, String status);//sample 2026-01-29
}
