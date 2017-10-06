package com.example.loadmorerecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<MstStudents> students;
    private StudentAdapter studentAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        students = new ArrayList<>();

        final DatabaseHandler db = new DatabaseHandler(this);

        //Adding studentData to DB.
        for (int i = 0; i < 100; i++) {
            MstStudents mstStudent = new MstStudents();
            mstStudent.setStudname("Name"+(i+1));
            mstStudent.setRollno(i+1);
            db.addStudent(mstStudent);
            //students.add(mstStudent);
        }

        List<MstStudents> student = db.getStudents(0);
        for (MstStudents stu : student) {
            String log = "Id: " +stu.getId() + " ,Name: " + stu.getStudname() + " ,R.No.: " +stu.getRollno();
            Log.w(TAG, "student is : " +log);
            students.add(stu);

        }


        //find view by id and attaching adapter for the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(recyclerView, students, this);
        recyclerView.setAdapter(studentAdapter);

        //set load more listener for the RecyclerView adapter
        studentAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (students.size() <= 90) {
                    students.add(null);
                    studentAdapter.notifyItemInserted(students.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            students.remove(students.size() - 1);
                            studentAdapter.notifyItemRemoved(students.size());

                            //Generating more data
                            int index = students.size();
                            int end = index + 10;

                            List<MstStudents> student = db.getStudents(end);
                            for (MstStudents stu : student) {
                                String log = "Id: " +stu.getId() + " ,Name: " + stu.getStudname() + " ,R.No.: " +stu.getRollno();
                                Log.w(TAG, "student is : " +log);
                                students.add(stu);
                            }

                            studentAdapter.notifyDataSetChanged();
                            studentAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(MainActivity.this, "Completed Loading", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}