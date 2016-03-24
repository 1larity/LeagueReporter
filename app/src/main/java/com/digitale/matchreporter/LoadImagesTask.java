package com.digitale.matchreporter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Load player portraits in background
 */
public class LoadImagesTask extends AsyncTask<String, Void, Void> {
    public MainActivity activity;

    public LoadImagesTask(MainActivity activity)
    {
        this.activity = activity;
    }
    @Override
    protected Void doInBackground(String... params) {
        Resources resources =activity.getResources();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inSampleSize = 4;
        Bitmap drawable;
        for(int i=1;i<19;i++){
            int resourceId = resources.getIdentifier("bplayer" + i, "drawable",
                    activity.getPackageName());
            drawable = BitmapFactory.decodeResource(resources, resourceId,opts);
            MainActivity.mPlayerPortraits.add(new PlayerPortrait(drawable, "bplayer" + i));
        }
        for(int i=1;i<19;i++){
            int resourceId = resources.getIdentifier("eplayer" + i, "drawable",
                    activity.getPackageName());

            drawable = BitmapFactory.decodeResource(resources, resourceId,opts);
            MainActivity.mPlayerPortraits.add(new PlayerPortrait(drawable, "eplayer" + i));
        }
        return null;
    }

}