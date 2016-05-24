package com.giaquino.sample.model.image;

import android.widget.ImageView;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface ImageLoader {

    void downloadImageInto(String url, ImageView imageView);
}
