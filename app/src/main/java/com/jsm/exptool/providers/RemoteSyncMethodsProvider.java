package com.jsm.exptool.providers;

import android.content.Context;

import androidx.annotation.StringRes;

import com.jsm.exptool.R;
import com.jsm.exptool.data.network.remotestrategies.FireBaseStrategy;
import com.jsm.exptool.data.network.remotestrategies.RemoteServerStrategy;
import com.jsm.exptool.data.network.remotestrategies.RemoteStrategyInterface;

import java.util.Arrays;
import java.util.List;

public class RemoteSyncMethodsProvider {

    public static final String REMOTE_METHOD_NONE = "REMOTE_METHOD_NONE";
    public static final String REMOTE_METHOD_API = "REMOTE_METHOD_API";
    public static final String REMOTE_METHOD_FIREBASE = "REMOTE_METHOD_FIREBASE";

    public enum RemoteConfigMethods {

        NONE(R.string.configuration_remote_method_none, R.string.configuration_remote_method_none_desc, REMOTE_METHOD_NONE),
        API(R.string.configuration_remote_method_api, R.string.configuration_remote_method_api_desc, REMOTE_METHOD_API ),
        FIREBASE(R.string.configuration_remote_method_firebase, R.string.configuration_remote_method_firebase_desc, REMOTE_METHOD_FIREBASE);

        private @StringRes
        int titleStringRes;
        int descriptionStringRes;
        private String internalId;

        RemoteConfigMethods(@StringRes int titleStringRes, @StringRes int descriptionStringRes, String internalId) {
            this.titleStringRes = titleStringRes;
            this.descriptionStringRes = descriptionStringRes;
            this.internalId = internalId;
        }

        public int getTitleStringRes() {
            return titleStringRes;
        }

        public int getDescriptionStringRes() {
            return descriptionStringRes;
        }

        public String getInternalId() {
            return internalId;
        }
    }

    public static List<RemoteConfigMethods> getRemoteConfigMethodsAsList(){
        return Arrays.asList(RemoteConfigMethods.values());
    }

    public static RemoteConfigMethods getRemoteConfigMethodFromStringRes(@StringRes int stringRes) {
        RemoteSyncMethodsProvider.RemoteConfigMethods returnValue = null;
        for (RemoteSyncMethodsProvider.RemoteConfigMethods method : RemoteSyncMethodsProvider.RemoteConfigMethods.values()) {
            if (method.getTitleStringRes() == stringRes) {
                returnValue = method;
                break;
            }

        }
        return returnValue;
    }

    public static RemoteConfigMethods getRemoteConfigMethodFromInternalId(String internalId) {
        RemoteSyncMethodsProvider.RemoteConfigMethods returnValue = null;
        for (RemoteSyncMethodsProvider.RemoteConfigMethods method : RemoteSyncMethodsProvider.RemoteConfigMethods.values()) {
            if (method.getInternalId().equals(internalId)) {
                returnValue = method;
                break;
            }

        }
        return returnValue;
    }

    public static RemoteConfigMethods getRemoteConfigMethodFromInternalIdString(String methodString) {
        RemoteSyncMethodsProvider.RemoteConfigMethods returnValue = null;
        for (RemoteSyncMethodsProvider.RemoteConfigMethods method : RemoteSyncMethodsProvider.RemoteConfigMethods.values()) {
            if (method.getInternalId().equals(methodString)) {
                returnValue = method;
                break;
            }

        }
        return returnValue;
    }

    public static String getStringFromRemoteConfigMethod(Context context, RemoteConfigMethods method) {
        return context.getString(method.getTitleStringRes());
    }

    public static RemoteStrategyInterface getRemoteStrategy(){
        RemoteStrategyInterface strategy = null;
        String methodStoredAsString = PreferencesProvider.getRemoteSyncMethod();
        RemoteConfigMethods method = getRemoteConfigMethodFromInternalIdString(methodStoredAsString);
        if(method != null){
            switch (method){
                case API:
                    strategy = new RemoteServerStrategy();
                    break;
                case FIREBASE:
                    strategy = new FireBaseStrategy();
                    break;
            }
        }

            return strategy;
    }


}
