package com.brightcove.player.samples.cast.basic;

import java.util.ArrayList;
import java.util.List;

public class CastVideoInfo {
    private CatalogData catalogData;
    private String policyKey;
    private String accountId;
    private List js = new ArrayList();
    private List css = new ArrayList();
    private boolean debug;

    private CastVideoInfo(){} //For GSON
    public CastVideoInfo(CatalogData catalogData, String accountId, String policyKey) {
        this.catalogData = catalogData;
        this.policyKey = policyKey;
        this.accountId = accountId;
    }
}
