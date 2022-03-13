package com.jsm.exptool.core.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.net.MalformedURLException;

public class ImageUtils {

    /**
     * Carga de manera perzosa una imagen desde una url
     *
     * @param url                   url de la imagen
     * @param imageViewTarget       ImageView donde se carga la imagen
     * @param placeHolderResourceId drawable que se usa como placeholder hasta que se carga la imagen o falla la carga
     * @param width                 Ancho al que se redimensiona, si se le pasa -1 no redimensiona
     * @param height                Altura a la que se redimensiona, si se le pasa -1 no redimensiona
     * @throws MalformedURLException
     */
    public static void lazyLoadFromUrl(String url, ImageView imageViewTarget, int placeHolderResourceId, int width, int height) throws MalformedURLException {


        Uri uri = Uri.parse(url);
        if (uri != null) {
           RequestCreator request =  Picasso.get().load(uri).placeholder(placeHolderResourceId);

                   if(width > 0 && height > 0) {
                    request.resize(width, height)
                               .centerInside();
                   }

                   request.into(imageViewTarget);
        } else {
            throw new MalformedURLException();
        }
    }

    /**
     * Carga de manera perzosa una imagen desde el sistema de archivos
     *
     * @param imagePath             path de la imagen
     * @param imageViewTarget       ImageView donde se carga la imagen
     * @param placeHolderResourceId drawable que se usa como placeholder hasta que se carga la imagen o falla la carga
     * @param width                 Ancho al que se redimensiona, si se le pasa -1 no redimensiona
     * @param height                Altura a la que se redimensiona, si se le pasa -1 no redimensiona
     * @throws MalformedURLException
     */

    public static void lazyLoadFromDisk(String imagePath, ImageView imageViewTarget, int placeHolderResourceId, int width, int height) throws Exception {


            RequestCreator request =  Picasso.get().load(imagePath).placeholder(placeHolderResourceId);

            if(width > 0 && height > 0) {
                request.resize(width, height)
                        .centerInside();
            }

            request.into(imageViewTarget);
        }
}
