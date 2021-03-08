package com.example.busfinder.Common;

import com.example.busfinder.Remote.IGoogleAPI;
import com.example.busfinder.Remote.RetrofitClient;

public class Common {
    public static final String baseURL = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI(){
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
