package com.jsm.exptool.config;

import com.jsm.exptool.R;
import com.jsm.exptool.model.embedding.EmbeddingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmbeddingConfig {
    //    public static final List<EmbeddingAlgorithm> EMBEDDING_ALGORITHMS = new ArrayList<EmbeddingAlgorithm>(){{
//        add(new EmbeddingAlgorithm("Inception V3", "inception-v3", R.string.inception_v3_description, 299, 299, 100));
//        add(new EmbeddingAlgorithm("Painters", "painters", R.string.painters_description, 256, 256, 20));
//        add(new EmbeddingAlgorithm("DeepLoc", "deeploc", R.string.deeploc_description, 64, 64, 20));
//        add(new EmbeddingAlgorithm("VGG 16", "vgg16", R.string.vgg16_description, 224, 224, 15));
//        add(new EmbeddingAlgorithm("VGG-19", "vgg19", R.string.vgg19_description, 224, 224, 15));
//        add(new EmbeddingAlgorithm("Openface", "openface", R.string.openface_description, 256, 256, 20));
//    }};
    public static final List<EmbeddingAlgorithm> EMBEDDING_ALGORITHMS = Arrays.asList(
            new EmbeddingAlgorithm("Inception V3", "inception-v3", R.string.inception_v3_description, 299, 299, 100),
            new EmbeddingAlgorithm("Painters", "painters", R.string.painters_description, 256, 256, 20),
            new EmbeddingAlgorithm("DeepLoc", "deeploc", R.string.deeploc_description, 64, 64, 20),
            new EmbeddingAlgorithm("VGG 16", "vgg16", R.string.vgg16_description, 224, 224, 15),
            new EmbeddingAlgorithm("VGG-19", "vgg19", R.string.vgg19_description, 224, 224, 15),
            new EmbeddingAlgorithm("Openface", "openface", R.string.openface_description, 256, 256, 20)
    );
}
