package com.cyatophilum.projetmenu.mFaceBook;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cyatophilum.projetmenu.mDataObject.MyPost;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;
import java.util.List;

public class Login {

    SimpleFacebook fb;
    Context c;
    MyPost myPost;

    public Login(SimpleFacebook fb, Context c, MyPost myPost) {
        this.fb = fb;
        this.c = c;
        this.myPost = myPost;
    }

    OnLoginListener loginListener = new OnLoginListener() {
        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
            //Toast.makeText(c, "Logged In", Toast.LENGTH_LONG).show();
            new PostPublisher(c,fb).publishFeed(myPost);
        }

        @Override
        public void onCancel() {
            Toast.makeText(c, "Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onException(Throwable throwable) {
            Log.e("Exception", throwable.getMessage());
        }

        @Override
        public void onFail(String reason) {
            Log.e("Fail", reason);
        }
    };

    public void login() {
        fb.login(loginListener);
    }

}
