package com.jsm.exptool.data.mock;

import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.SensorsConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MockExamples {

    public static Experiment getFullExperiment(){
        //TODO Código desarrollo
        Experiment experiment = new Experiment();
        experiment.setConfiguration(new ExperimentConfiguration());
        experiment.getConfiguration().setCameraConfig(new CameraConfig());
        experiment.getConfiguration().getCameraConfig().setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        experiment.getConfiguration().setAudioConfig(new AudioConfig());
        experiment.getConfiguration().getAudioConfig().setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));
        experiment.setSensors(new ArrayList<MySensor>() {
            {
                addAll(SensorHandler.getInstance().getSensors());

            }
        });

        return experiment;
    }

    public static Experiment registerExperimentForPerformanceTest(){
        Experiment experiment = new Experiment();
        experiment.setTitle("Experimento "+ new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  "+ new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig );
        SensorsConfig sensorsConfig = new SensorsConfig();
        sensorsConfig.setSensors(new ArrayList<MySensor>(){{
            add(SensorHandler.getInstance().getSensors().get(0));
            add(SensorHandler.getInstance().getSensors().get(1));
            add(SensorHandler.getInstance().getSensors().get(2));
        }});
        configuration.setSensorConfig(sensorsConfig);
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);
        return experiment;
    }

    public static Experiment registerExperimentForSensorVisualizationTest(){
        Experiment experiment = new Experiment();
        experiment.setTitle("Experimento "+ new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  "+ new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig );
        SensorsConfig sensorsConfig = new SensorsConfig();
        sensorsConfig.setSensors(new ArrayList<MySensor>(){{
            add(SensorHandler.getInstance().getSensors().get(0));
            add(SensorHandler.getInstance().getSensors().get(1));
            add(SensorHandler.getInstance().getSensors().get(2));
        }});
        configuration.setSensorConfig(sensorsConfig);
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (MySensor sensor:configuration.getSensorConfig().getSensors()) {
            for (int i= 0; i<= 400; i++){
                cal.add(Calendar.HOUR, 1);
                for (String key:sensor.getMeasure().keySet()) {
                    sensor.getMeasure().put(key, new Random().nextFloat());
                }


                SensorsRepository.registerSensorCapture(sensor, "PRUEBA", id, cal.getTime());
            }

        }



        return experiment;
    }
}
