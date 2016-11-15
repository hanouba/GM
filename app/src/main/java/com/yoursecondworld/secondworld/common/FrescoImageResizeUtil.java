package com.yoursecondworld.secondworld.common;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by cxj on 2016/7/28.
 */
public class FrescoImageResizeUtil {

    public static int imageMaxWidth = 1000;
    public static int imageMaxheight = 1000;

    public static void setResizeControl(SimpleDraweeView sdv, Uri uri) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(imageMaxWidth, imageMaxheight))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        sdv.setController(controller);
    }

}
