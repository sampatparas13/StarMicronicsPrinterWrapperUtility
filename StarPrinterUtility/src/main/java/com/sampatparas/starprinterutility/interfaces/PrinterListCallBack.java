package com.sampatparas.starprinterutility.interfaces;


import com.sampatparas.starprinterutility.model.SearchResultInfo;

import java.util.List;

public interface PrinterListCallBack {
    void callback(List<SearchResultInfo> result);
}
