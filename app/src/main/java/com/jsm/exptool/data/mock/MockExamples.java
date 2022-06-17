package com.jsm.exptool.data.mock;

import static com.jsm.exptool.config.SensorConfigConstants.TYPE_GPS;
import static com.jsm.exptool.config.SensorConfigConstants.TYPE_GPS_SENSOR_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.ALTITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LATITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LONGITUDE;

import android.content.Context;
import android.util.Log;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.CommentRepository;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.repositories.SensorRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MockExamples {

    public static Experiment registerFullExperiment(boolean register) {
        //TODO Código desarrollo
        Experiment experiment = new Experiment();
        experiment.setTitle("Experimento generado"+ DateProvider.dateToDisplayStringWithTime(new Date()));
        experiment.setDescription("Descripción de experimento generado mediante MockExamples, este experimento tiene un conjunto de funcionalidades completo y listo para probar");
        experiment.setStatus(Experiment.ExperimentStatus.CREATED);

        experiment.setConfiguration(new ExperimentConfiguration());
        experiment.getConfiguration().setQuickComments(Arrays.asList(
            "Comentario de prueba",
            "Otro comentario",
            "Otro comentario más",
            "Comentario",
            "Comentario un poco mas largo",
            "Comentario número 6"
        ));
        experiment.getConfiguration().setCameraConfig(new CameraConfig(FrequencyConstants.DEFAULT_CAMERA_FREQ, FrequencyConstants.MIN_CAMERA_INTERVAL_MILLIS, FrequencyConstants.MAX_CAMERA_INTERVAL_MILLIS ));
        experiment.getConfiguration().getCameraConfig().setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        experiment.getConfiguration().setAudioConfig(new AudioConfig(FrequencyConstants.DEFAULT_AUDIO_FREQ, FrequencyConstants.MIN_AUDIO_INTERVAL_MILLIS, FrequencyConstants.MAX_AUDIO_INTERVAL_MILLIS));
        experiment.getConfiguration().setLocationConfig(new LocationConfig(FrequencyConstants.DEFAULT_LOCATION_FREQ, FrequencyConstants.MIN_LOCATION_INTERVAL_MILLIS, FrequencyConstants.MAX_LOCATION_INTERVAL_MILLIS));
        experiment.getConfiguration().getLocationConfig().setLocationOption(LocationProvider.getLocationOptions().get(0));
        experiment.getConfiguration().setSensorConfig(new SensorsGlobalConfig(FrequencyConstants.DEFAULT_SENSOR_FREQ, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        experiment.getConfiguration().getAudioConfig().setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));

        experiment.getConfiguration().getSensorConfig().setSensors(new ArrayList<>(SensorHandler.getInstance().getSensors()));


        if(register) {
            long id = ExperimentsRepository.registerExperiment(experiment);
            experiment.setInternalId(id);
        }

        return experiment;
    }

    public static Experiment registerExperimentForLocationTest() {
        Experiment experiment = new Experiment();

        experiment.setStatus(Experiment.ExperimentStatus.CREATED);
        experiment.setTitle("Experimento ubica " + new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  " + new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        LocationConfig locationConfig = new LocationConfig(FrequencyConstants.DEFAULT_LOCATION_FREQ, FrequencyConstants.MIN_LOCATION_INTERVAL_MILLIS, FrequencyConstants.MAX_LOCATION_INTERVAL_MILLIS);
        locationConfig.setInterval(500);
        locationConfig.setLocationOption(LocationProvider.getLocationOptions().get(0));
        configuration.setLocationConfig(locationConfig);
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);
        return experiment;

    }



    public static Experiment registerExperimentForSensorVisualizationTest(Context context, Experiment.ExperimentStatus status) {
        Experiment experiment = registerFullExperiment(true);
        ExperimentConfiguration configuration = experiment.getConfiguration();
        long id = experiment.getInternalId();

        if(status != null){
            experiment.setStatus(status);
        }else{
            experiment.setStatus(Experiment.ExperimentStatus.FINISHED);
        }

        experiment.setInitDate(new Date());
        if (experiment.getStatus().equals(Experiment.ExperimentStatus.FINISHED)){
            experiment.setEndDate(new Date());
        }

        ExperimentsRepository.updateExperiment(experiment);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Random randomGenerator = new Random();
        //Sensores y comentarios
        for (SensorConfig sensor : configuration.getSensorConfig().getSensors()) {
            for (int i = 0; i <= 40; i++) {
                cal.add(Calendar.MILLISECOND, 10);
                for (String key : sensor.getSensorReader().getMeasure().keySet()) {
                    sensor.getSensorReader().getMeasure().put(key, randomGenerator.nextFloat());
                }
                SensorRepository.registerSensorCapture(sensor, "PRUEBA", id, cal.getTime());
                CommentRepository.registerComment(new CommentRegister(id,cal.getTime(), false, "Comentario de prueba "+ cal.getTime()));
            }

        }

        //UBICACION
        double latitude = 37.347978;
        double longitude = -5.982095;
        double altitude = 56.5;
        float accuracy = 1;
        for(int i=0; i<60; i++){

            SensorRegister sensorRegister = new SensorRegister(id,  new Date(), false,  latitude, LATITUDE,
                    longitude,LONGITUDE, altitude, ALTITUDE, TYPE_GPS_SENSOR_NAME, TYPE_GPS, R.string.location, accuracy);
            SensorRepository.registerSensorCapture(sensorRegister);
            latitude += 0.0000001 * i;
        }

        //Imagenes
        File f = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.IMAGES), "GAtos.jpg");

        try {
            if (!f.exists())
                f.createNewFile();
            InputStream inputStream = context.getResources().openRawResource(R.raw.cat_4001);
            OutputStream out = new FileOutputStream(f);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (Exception e) {
            Log.w(MockExamples.class.getSimpleName(), e.getMessage(), e);
        }

        for (int i = 0; i <= 40; i++) {
            ImageRepository.registerImageCapture(f, id, cal.getTime());
        }

        File f2 = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.AUDIO), "cat_sound.mp3");
        //AUDIO
        try {
            if (!f2.exists())
                f2.createNewFile();
            InputStream inputStream = context.getResources().openRawResource(R.raw.cat_sound);
            OutputStream out = new FileOutputStream(f2);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            inputStream.close();
        } catch (Exception e) {
            Log.w(MockExamples.class.getSimpleName(), e.getMessage(), e);
        }

        for (int i = 0; i <= 40; i++) {
            AudioRepository.registerAudioRecording(f2, id, cal.getTime());
        }


        return experiment;
    }

    public static void createRandomCompletedExperiments(Context context){
        Random randomGenerator = new Random();
        for (int i=0; i<20; i++){
            registerExperimentForSensorVisualizationTest(context, randomGenerator.nextBoolean()? Experiment.ExperimentStatus.INITIATED: Experiment.ExperimentStatus.FINISHED);
        }

    }
}


