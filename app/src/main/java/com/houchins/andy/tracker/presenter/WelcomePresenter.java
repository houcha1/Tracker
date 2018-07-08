package com.houchins.andy.tracker.presenter;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.houchins.andy.tracker.ITrackerApplication;
import com.houchins.andy.tracker.R;
import com.houchins.andy.tracker.data.ObservationRecord;
import com.houchins.andy.tracker.dataconverter.CsvConverter;
import com.houchins.andy.tracker.dataconverter.ObservationConverter;
import com.houchins.andy.tracker.model.IAppModelListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Presenter for the Welcome screen
 */

public class WelcomePresenter implements IPresenter, IAppModelListener {
    private ITrackerApplication trackerApplication;
    private View view;
    private TextView subtitle;

    public WelcomePresenter(ITrackerApplication trackerApplication) {
        this.trackerApplication = trackerApplication;
    }

    @Override
    public View getView(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.welcome, null);
            subtitle = (TextView) view.findViewById(R.id.welcome_subtitle);
        }
        return view;
    }

    public void init() {
        subtitle.setText(R.string.subtitle_loading_text);
        if (trackerApplication != null) {
            trackerApplication.initializeModel(this);
        }
    }


    @Override
    public void onDataLoaded() {
        subtitle.setText(R.string.subtitle_loading_complete_text);
        for (ObservationRecord observationRecord : trackerApplication.getModel().getObservationRecords()) {
            System.out.println("ANDYX-" + observationRecord.getDaysSinceEpoch() + "-" +
                    ObservationConverter.convert(observationRecord).toString());
        }
    }

    public void export_data() {
        if (trackerApplication.getModel().isInitialized()) {
            File f;
            String filePrefix = "temp_";
            String fileExtension = ".csv";
            int i = 0;
            String fileName;
            boolean foundFile = false;
            boolean created = false;
            File directory = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            subtitle.setText(R.string.subtitle_export_complete_text);

            do {
                i++;
                fileName = filePrefix + i + fileExtension;
                f = new File(directory, fileName);
                if (!f.exists()) {
                    foundFile = true;
                    try {
                        if (f.createNewFile()) {
                            FileOutputStream fs = new FileOutputStream(f, true);
                            fs.write(CsvConverter.convertToCsv(trackerApplication.getModel().getObservationRecords()).getBytes());
                            fs.flush();
                            fs.close();
                            created = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } while (!foundFile);

            if (created) {
                subtitle.setText(R.string.subtitle_export_complete_text);
            } else {
                subtitle.setText(R.string.subtitle_export_failed_text);
            }
        }
    }
}
