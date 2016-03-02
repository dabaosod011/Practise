package net.sourceforge.simcpux.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = WXEntryActivity.class.getSimpleName();
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx335b9226947437e8", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.d(TAG, "onResp: BaseResp.ErrCode.ERR_OK");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.d(TAG, "onResp: BaseResp.ErrCode.ERR_USER_CANCEL");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.d(TAG, "onResp: BaseResp.ErrCode.ERR_AUTH_DENIED");
                break;
            default:
                Log.d(TAG, "onResp: default");
                break;
        }
    }
}