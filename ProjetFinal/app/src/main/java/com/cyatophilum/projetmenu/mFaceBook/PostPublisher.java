package com.cyatophilum.projetmenu.mFaceBook;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cyatophilum.projetmenu.mDataObject.MyPost;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;


public class PostPublisher {

    Context c;
    SimpleFacebook fb;

    public PostPublisher(Context c, SimpleFacebook fb) {
        this.c = c;
        this.fb=fb;
    }

    OnPublishListener publishListener = new OnPublishListener() {
        @Override
        public void onComplete(String response) {
            super.onComplete(response);
            Toast.makeText(c, "Successfully published", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
            Log.e("Exception", throwable.getMessage());
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            Log.e("Exception", reason);
        }

        @Override
        public void onThinking() {
            super.onThinking();
            Log.i("Exception", "Thinking");
        }
    };

    public void publishFeed(MyPost mypost){
        Feed feed=new Feed.Builder()
                .setName(mypost.getName())
                .setMessage(mypost.getMessage())
                .setDescription(mypost.getDescription())
                .setLink(mypost.getLing())
                .build();

        fb.publish(feed,publishListener);
    }
}
