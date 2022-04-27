package com.sampatparas.starprinterutility.searchPrinter;

import android.app.Activity;
import android.os.AsyncTask;

import com.sampatparas.starprinterutility.Utils.Utils;
import com.sampatparas.starprinterutility.interfaces.PrinterListCallBack;
import com.sampatparas.starprinterutility.model.SearchResultInfo;
import com.sampatparas.starprinterutility.printerUtils.PrinterSettingConstant;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.util.ArrayList;
import java.util.List;

public class SearchPrinterUtils {
    Activity activity;
    PrinterListCallBack printerTypeCallBack;
    private List<SearchResultInfo> searchResultArray = new ArrayList<>();

    public SearchPrinterUtils(Activity activity, PrinterListCallBack printerTypeCallBack) {
        this.activity = activity;
        this.printerTypeCallBack = printerTypeCallBack;
    }

    public void startSearch(String name) {
        SearchTask searchTask = new SearchTask();
        searchTask.execute(getConnectionName(name));
    }

    private String getConnectionName(String name) {

        if (name.equalsIgnoreCase("LAN")) {
            return "TCP:";
        } else {
            return "BT:";
        }
    }

    private class SearchTask extends AsyncTask<String, Void, Void> {
        private List<PortInfo> mPortList;

        SearchTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.pDialog(activity);
        }

        @Override
        protected Void doInBackground(String... interfaceType) {
            try {
                mPortList = StarIOPort.searchPrinter(interfaceType[0], activity);
            } catch (StarIOPortException e) {
                mPortList = new ArrayList<>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void doNotUse) {
            Utils.pDialogDismiss();
            for (PortInfo info : mPortList) {
                addItem(info);
            }
            printerTypeCallBack.callback(searchResultArray);
        }
    }

    private void addItem(PortInfo info) {
        try {
            String modelName;
            String portName;
            String macAddress;

            if (info.getPortName().startsWith(PrinterSettingConstant.IF_TYPE_BLUETOOTH)) {
                modelName = info.getPortName().substring(PrinterSettingConstant.IF_TYPE_BLUETOOTH.length());
                portName = PrinterSettingConstant.IF_TYPE_BLUETOOTH + info.getMacAddress();
                macAddress = info.getMacAddress();
            } else {
                modelName = info.getModelName();
                portName = info.getPortName();
                macAddress = info.getMacAddress();
            }

            SearchResultInfo searchResultInfo = new SearchResultInfo();
            searchResultInfo.setModelName(modelName);
            searchResultInfo.setPortNumber(portName);
            searchResultInfo.setMacAddress(macAddress);
            searchResultArray.add(searchResultInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

