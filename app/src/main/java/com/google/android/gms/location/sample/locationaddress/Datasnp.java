package com.google.android.gms.location.sample.locationaddress;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Datasnp {
    public String loc;
    public String url;

    public Datasnp() {
    }

    public Datasnp(String title, String content) {
        this.loc = title;
        this.url = content;
    }
}
