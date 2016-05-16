package com.giaquino.sample.common.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class PicassoImageLoader implements ImageLoader {

    private final Picasso picasso;

    public PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void downloadImageInto(String url, ImageView imageView) {
        picasso.load(url)
               .fit()
               .centerCrop()
               .into(imageView);
    }
}
