package com.sampatparas.starprinterutility.printerUtils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import com.sampatparas.starprinterutility.interfaces.PrintInterface;

public class StarPrinterUtils {
    private static final String TAG = StarPrinterUtils.class.getSimpleName();
    public static final int PRINT_CUSTOMER_COPY = 1;
    public static final int PRINT_KITCHEN_COPY = 2;
    public static void printReceipts(Activity context, View v, OnPrintCompletedListener listener) {
        try {
//            webView.setVisibility(View.GONE);
            v.setDrawingCacheEnabled(true);
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.buildDrawingCache(true);
            Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(false);
            printReceiptFromStarPrinter(context, b, listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printReceiptFromStarPrinter(Activity context, Bitmap b, OnPrintCompletedListener listener) {
        StarPrinterHelper.print(context, 8, PrinterSettingConstant.LANGUAGE_ENGLISH, b, new PrintInterface() {
            @Override
            public void callback(String result) {
                Log.e(TAG, "StarPrinterHelper : " + result);
                if(listener != null){
                    listener.onPrintReceiptStatus(result);
                }
            }
        });

    }
    public interface OnPrintCompletedListener{
        void onPrintReceiptStatus(String result);
    }
}
