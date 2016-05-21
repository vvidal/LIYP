package com.cyatophilum.projetmenu.mFaceBook;


import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

/**
 * Created by a on 15/05/2016.
 */
public class MyConfiguration {

    Permission[] permissions = new Permission[]{Permission.EMAIL};
    static final String APP_ID="1623029774681128";

    public SimpleFacebookConfiguration getMyConfig(){
        SimpleFacebookConfiguration configs=new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace("testons")
                .setPermissions(permissions)
                .build();

        return configs;
    }
}
