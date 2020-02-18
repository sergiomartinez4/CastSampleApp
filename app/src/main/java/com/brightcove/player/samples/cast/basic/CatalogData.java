package com.brightcove.player.samples.cast.basic;

public class CatalogData {
    private String videoId;
    private String playlistId;
    private String adConfigId;

    private CatalogData() {} //For GSON

    public CatalogData(String videoId, String playlistId, String adConfigId) {
        this.videoId = videoId;
        this.playlistId = playlistId;
        this.adConfigId = adConfigId;
    }
}
