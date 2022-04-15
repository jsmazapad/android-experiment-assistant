package com.jsm.exptool.data.mock;

import android.content.Context;

import com.jsm.exptool.R;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MockExamples {

    public static Experiment getFullExperiment() {
        //TODO Código desarrollo
        Experiment experiment = new Experiment();
        experiment.setQuickComments(new ArrayList<String>(){{
            add("Comentario de prueba");
            add("Otro comentario");
            add("Otro comentario más");
            add("Comentario");
            add("Comentario un poco mas largo");
            add("Comentario número 6");
        }});
        experiment.setConfiguration(new ExperimentConfiguration());
        experiment.getConfiguration().setCameraConfig(new CameraConfig());
        experiment.getConfiguration().getCameraConfig().setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        experiment.getConfiguration().setAudioConfig(new AudioConfig());
        experiment.getConfiguration().setSensorConfig(new SensorsGlobalConfig());
        experiment.getConfiguration().getAudioConfig().setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));
        experiment.getConfiguration().getSensorConfig().setSensors(new ArrayList<SensorConfig>() {
            {
                addAll(SensorHandler.getInstance().getSensors());

            }
        });

        return experiment;
    }

    public static Experiment registerExperimentForPerformanceTest() {
        Experiment experiment = new Experiment();
        experiment.setQuickComments(new ArrayList<String>(){{
            add("Comentario de prueba");
            add("Otro comentario");
            add("Otro comentario más");
            add("Comentario");
            add("Comentario un poco mas largo");
            add("Comentario número 6");
        }});
        experiment.setStatus(Experiment.ExperimentStatus.CREATED);
        experiment.setTitle("Experimento " + new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  " + new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig);
        SensorsGlobalConfig sensorsGlobalConfig = new SensorsGlobalConfig();
        sensorsGlobalConfig.setSensors(new ArrayList<SensorConfig>() {{
            add(SensorHandler.getInstance().getSensors().get(0));
            add(SensorHandler.getInstance().getSensors().get(1));
            add(SensorHandler.getInstance().getSensors().get(2));
        }});
        configuration.setSensorConfig(sensorsGlobalConfig);
        AudioConfig audioConfig = new AudioConfig();
        audioConfig.setInterval(1000);
        audioConfig.setRecordingDuration(500);
        audioConfig.setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));
        configuration.setAudioConfig(audioConfig);
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);
        return experiment;
    }

    public static Experiment registerExperimentForSensorVisualizationTest(Context context) {
        Experiment experiment = new Experiment();
        experiment.setQuickComments(new ArrayList<String>(){{
            add("Comentario de prueba");
            add("Otro comentario");
            add("Otro comentario más");
            add("Comentario");
            add("Comentario un poco mas largo");
            add("Comentario número 6");
        }});
        experiment.setStatus(new Random().nextBoolean()? Experiment.ExperimentStatus.INITIATED: Experiment.ExperimentStatus.FINISHED);
        //experiment.setStatus(Experiment.ExperimentStatus.CREATED);
        experiment.setInitDate(new Date());
        if (experiment.getStatus().equals(Experiment.ExperimentStatus.FINISHED)){
            experiment.setEndDate(new Date());
        }
        experiment.setTitle("Experimento " + new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  " + new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig);
        SensorsGlobalConfig sensorsGlobalConfig = new SensorsGlobalConfig();
        sensorsGlobalConfig.setSensors(new ArrayList<SensorConfig>() {{
            add(SensorHandler.getInstance().getSensors().get(0));
            add(SensorHandler.getInstance().getSensors().get(1));
            add(SensorHandler.getInstance().getSensors().get(2));
        }});
        configuration.setSensorConfig(sensorsGlobalConfig);

        AudioConfig audioConfig = new AudioConfig();
        configuration.setAudioConfig(audioConfig);

        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (SensorConfig sensor : configuration.getSensorConfig().getSensors()) {
            for (int i = 0; i <= 40; i++) {
                cal.add(Calendar.HOUR, 1);
                for (String key : sensor.getMeasure().keySet()) {
                    sensor.getMeasure().put(key, new Random().nextFloat());
                }


                SensorsRepository.registerSensorCapture(sensor, "PRUEBA", id, cal.getTime());
            }

        }


        File f = new File(FilePathsProvider.getImagesFilePath(context), "GAtos.jpg");

        try {
            if (!f.exists())
                f.createNewFile();
//id is some like R.drawable.b_image
            InputStream inputStream = context.getResources().openRawResource(R.raw.cat_4001);
            OutputStream out = new FileOutputStream(f);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= 40; i++) {


            ImagesRepository.registerImageCapture(f, id, cal.getTime());
        }

        File f2 = new File(FilePathsProvider.getAudiosFilePath(context), "cat_sound.mp3");

        try {
            if (!f2.exists())
                f2.createNewFile();
//id is some like R.drawable.b_image
            InputStream inputStream = context.getResources().openRawResource(R.raw.cat_sound);
            OutputStream out = new FileOutputStream(f2);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= 40; i++) {


            AudioRepository.registerAudioRecording(f2, id, cal.getTime());
        }


        return experiment;
    }

    public static void createRandomCompletedExperiments(Context context){

        for (int i=0; i<20; i++){
            registerExperimentForSensorVisualizationTest(context);
        }

    }
}


