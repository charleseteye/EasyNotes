package com.example.charl.easynotes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.charl.easynotes.database.AppRepository;
import com.example.charl.easynotes.database.NoteEntity;
import com.example.charl.easynotes.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes=mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
