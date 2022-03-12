package com.jsm.exptool.providers;

import static com.jsm.exptool.config.ConfigConstants.IMAGE_THUMBNAIL_HEIGHT;
import static com.jsm.exptool.config.ConfigConstants.IMAGE_THUMBNAIL_WIDTH;

import android.widget.ImageView;

import com.jsm.exptool.core.utils.ImageUtils;


/**
 * Provee de méétodos auxiliares para el tratamiento de imágenes
 */
public class ImagesProvider {

    private static final int  PLACEHOLDER_RES_ID = R.drawable.beer_logo;

    /**
     * Carga una imagen de manera perezosa
     * @param url url donde está la imagen
     * @param imageViewTarget ImageView
     * @param isThumbnail Indica si la imagen se mostrará en una vista previa en listado
     */
    public static void loadImageForListOrDetail(String url, ImageView imageViewTarget, boolean isThumbnail) {

        try {
            int width = -1;
            int height = -1;
            if(isThumbnail)
            {
                //TODO REVISAR TAMAÑO THUMBNAILS
                width = IMAGE_THUMBNAIL_WIDTH;
                height = IMAGE_THUMBNAIL_HEIGHT;

            }

            ImageUtils.lazyLoadFromUrl(url, imageViewTarget, PLACEHOLDER_RES_ID, width, height);
        } catch (Exception e) {
            imageViewTarget.setImageResource(PLACEHOLDER_RES_ID);
        }
    }
}
