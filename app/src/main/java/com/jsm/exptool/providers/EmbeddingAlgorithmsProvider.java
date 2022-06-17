package com.jsm.exptool.providers;

import com.jsm.exptool.config.EmbeddingConfig;
import com.jsm.exptool.entities.embedding.EmbeddingAlgorithm;

import java.util.List;

public class EmbeddingAlgorithmsProvider {

    public static List<EmbeddingAlgorithm> getEmbeddingAlgorithms(){
        return EmbeddingConfig.EMBEDDING_ALGORITHMS;
    }
}
