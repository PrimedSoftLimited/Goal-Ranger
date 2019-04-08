package com.example.goalranger.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goalranger.Models.Data;
import com.example.goalranger.Models.Goal;
import com.example.goalranger.Network.ApiInterface;
import com.example.goalranger.Network.RetrofitInstance;
import com.example.goalranger.R;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGoals extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePickerDialog datePicker;
    AppCompatEditText goalNameEdit, goalDescriptionEdit, beginDateEdit, dueDateEdit;
    Spinner goalLevelspinner;
    String goalName, goalDescription, beginDate, dueDate, spinner;
   AppCompatButton doneButton, addTaskButton, nextButton;
    String token, token2;
    TextView titleEdit;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);
        token = getIntent().getStringExtra("editToken");
        token2 = getIntent().getStringExtra("fabToken");
        id = getIntent().getIntExtra("id", 0);
        titleEdit = findViewById(R.id.title_edit);

        goalNameEdit = findViewById(R.id.goal_name);
        goalDescriptionEdit = findViewById(R.id.goal_description);
        beginDateEdit = findViewById(R.id.start_date_edit);
        dueDateEdit = findViewById(R.id.due_date_edit);
        goalLevelspinner = findViewById(R.id.spinner);
doneButton = findViewById(R.id.done_button);
            if (token != null) {
            titleEdit.setText("Edit Goal");
            getGoal();
        }
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token != null)
                    editGoal();
                else if (token2 != null)
                    createGoal();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.goal_level_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        goalLevelspinner.setAdapter(adapter);
        goalLevelspinner.setOnItemSelectedListener(this);
        beginDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddGoals.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                beginDateEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        dueDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(AddGoals.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dueDateEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        addTaskButton = findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.add_tasks);
                createTask();
            }
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createTask() {
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        Goal goal = new Goal(goalName, goalDescription, beginDate, dueDate, spinner);

        Call<Data> call = service.aGoal(
                token,
                String.valueOf(id)
        );
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Toast.makeText(AddGoals.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
    private void getGoal() {
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        Goal goal = new Goal(goalName, goalDescription, beginDate, dueDate, spinner);

        Call<Data> call = service.aGoal(
                token,
                String.valueOf(id)
        );
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                goalName = response.body().getData().getGoalObject().getTitle();
                goalDescription = response.body().getData().getGoalObject().getDescription();
                dueDate = response.body().getData().getGoalObject().getDue_date();
                beginDate = response.body().getData().getGoalObject().getBegin_date();
                spinner = response.body().getData().getGoalObject().getLevel();
                goalNameEdit.setText(goalName);
                goalDescriptionEdit.setText(goalDescription);
                beginDateEdit.setText(beginDate);
                dueDateEdit.setText(dueDate);
                goalLevelspinner.setPrompt(spinner);
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
    private void createGoal() {


        goalName = Objects.requireNonNull(goalNameEdit.getText()).toString().trim();
        goalDescription = Objects.requireNonNull(goalDescriptionEdit.getText()).toString().trim();
        beginDate = Objects.requireNonNull(beginDateEdit.getText()).toString().trim();
        dueDate = Objects.requireNonNull(dueDateEdit.getText()).toString().trim();
        spinner = goalLevelspinner.getSelectedItem().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Just a sec...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        //Defining the user object as we need to pass it with the call
        Goal goal = new Goal(goalName, goalDescription, beginDate, dueDate, spinner);

        Call<Data> call = service.addGoal(
                token2,
                goal.getTitle(),
                goal.getDescription(),
                goal.getBegin_date(),
                goal.getDue_date(),
                goal.getLevel()
        );

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                progressDialog.dismiss();
                response.raw().body().toString();
                Log.e("create goal-------", response.raw().body().toString());
                Toast.makeText(AddGoals.this, response.raw().body().toString(), Toast.LENGTH_SHORT).show();
                if (response.code() == 201) {
                    Toast.makeText(AddGoals.this, response.raw().body().toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddGoals.this, Goals.class);
                    intent.putExtra("token2", token2);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void editGoal() {
        String goalName2 = Objects.requireNonNull(goalNameEdit.getText()).toString().trim();
        String goalDescription2 = Objects.requireNonNull(goalDescriptionEdit.getText()).toString().trim();
        String beginDate2 = Objects.requireNonNull(beginDateEdit.getText()).toString().trim();
        String dueDate2 = Objects.requireNonNull(dueDateEdit.getText()).toString().trim();
        String spinner2 = goalLevelspinner.getSelectedItem().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Just a sec...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Goal goal = new Goal(goalName2, goalDescription2, beginDate2, dueDate2, spinner2);
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        Call<Data> call = service.updateGoals(
                token,
                String.valueOf(id),
                goal.getTitle(),
                goal.getDescription(),
                goal.getBegin_date(),
                goal.getDue_date(),
                goal.getLevel()

        );
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                progressDialog.dismiss();
                Intent intent = new Intent(AddGoals.this, Goals.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });


    }
}

