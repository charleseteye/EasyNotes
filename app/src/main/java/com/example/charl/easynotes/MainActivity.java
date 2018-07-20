package com.example.charl.easynotes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.charl.easynotes.database.NoteEntity;
import com.example.charl.easynotes.ui.NotesAdapter;
import com.example.charl.easynotes.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //using the butterknife library to create  view

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler(){
        Intent intent= new Intent(this, EditorActivity.class);
        startActivity(intent);
    }
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;

    private List<NoteEntity> notesData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerview();
        initViewModel();




    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> noteObserver =
                new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                        notesData.clear();
                        notesData.addAll(noteEntities);

                        if (mAdapter == null) {
                            mAdapter=new NotesAdapter(notesData,MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        }else {
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                };
        mViewModel= ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mViewModel.mNotes.observe(this,noteObserver);
    }

    private void initRecyclerview() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {

            addSampleData();
            return true;
        }else if(id == R.id.action_delete){

            deleteAllNotes();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}
