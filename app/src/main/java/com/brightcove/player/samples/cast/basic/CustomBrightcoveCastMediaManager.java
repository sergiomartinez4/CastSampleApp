package com.brightcove.player.samples.cast.basic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.brightcove.cast.controller.BrightcoveCastMediaManager;
import com.brightcove.cast.util.CastMediaUtil;
import com.brightcove.player.event.Event;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventListener;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.Source;
import com.brightcove.player.model.Video;
import com.google.android.gms.cast.MediaInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomBrightcoveCastMediaManager extends BrightcoveCastMediaManager {
    private static final Gson GSON_PARSER = new GsonBuilder()
            .create();

    private static final String TAG = CustomBrightcoveCastMediaManager.class.getSimpleName();
    private Video currentVideo;
    private Source currentSource;
    private String account;
    private String policyKey;

    CustomBrightcoveCastMediaManager(Context context, EventEmitter eventEmitter, String account, String policyKey) {
        super(context, eventEmitter);
        this.account = account;
        this.policyKey = policyKey;

        addListener(EventType.SET_SOURCE, new OnSetSourceListener());
    }

    protected void loadMediaInfo() {
        if (!isSessionAvailable()) {
            return;
        }

        MediaInfo mediaInfo = createMediaInfo();
        if (mediaInfo != null) {
            updateBrightcoveMediaController(true);
            loadMediaInfo(mediaInfo);
        } else {
            Log.e(TAG, "Media Queue Item is null");
        }
    }

    private @Nullable
    MediaInfo createMediaInfo() {
        MediaInfo mediaInfo = null;
        if (currentVideo != null && currentSource != null) {
            JSONObject customData = null;
            try {
                customData = createBrightcoveCustomData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mediaInfo = CastMediaUtil.toMediaInfo(currentVideo, currentSource, null, customData);
        }
        return mediaInfo;
    }

    private class OnSetSourceListener implements EventListener {
        @Override
        public void processEvent(Event event) {
            currentVideo = (Video) event.properties.get(Event.VIDEO);
            currentSource = (Source) event.properties.get(Event.SOURCE);
            if(isSessionAvailable()) {
                event.preventDefault();
            }
        }
    }

    private JSONObject createBrightcoveCustomData() throws JSONException {
        CatalogData catalogData = new CatalogData(currentVideo.getId(), "", "");
        CastVideoInfo castVideoInfo = new CastVideoInfo(catalogData, account, policyKey
        );
        String jsonString = GSON_PARSER.toJson(castVideoInfo);
        return new JSONObject(jsonString);

    }
}
