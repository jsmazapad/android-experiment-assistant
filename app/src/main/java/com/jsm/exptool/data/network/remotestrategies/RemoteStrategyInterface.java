package com.jsm.exptool.data.network.remotestrategies;

import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;

import java.io.File;
import java.util.List;

import retrofit2.Call;

public interface RemoteStrategyInterface {


    Call<NetworkElementResponse<LoginResponse>> login(NetworkElementResponseCallback<LoginResponse> callback, boolean returnCall);

    void syncExperiment(NetworkElementResponseCallback<RemoteSyncResponse> callback, Experiment experiment);

    void syncImageFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file);

    void syncAudioFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file);

    void syncImageRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<ImageRegister> registers);

    void syncAudioRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<AudioRegister> registers);

    void syncSensorRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<SensorRegister> registers);

    void syncCommentRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<CommentRegister> registers);

}
