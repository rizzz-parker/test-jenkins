package com.project.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListTodoAdapter.KegiatanListener {
    private ImageButton mbtnTambah;
    private RecyclerView rvList;

    DataBaseHandler db;
    private List<ListToDoClass> todolist;
    private ListTodoAdapter listTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        mbtnTambah = findViewById(R.id.btnTambah);
        mbtnTambah.setOnClickListener(v -> showForm(new ListToDoClass(), null));

        db = new DataBaseHandler(this);

        loadData();
    }

    private void showForm(ListToDoClass listToDoClass, String existingNote) {
        AlertDialog.Builder formBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.form_note, null);
        formBuilder.setView(view);

        AlertDialog popup = formBuilder.create();
        popup.show();

        EditText etNote = view.findViewById(R.id.etNote);
        EditText etDate = view.findViewById(R.id.etDate);
        EditText etTime = view.findViewById(R.id.etTime);
        Button btnSave = view.findViewById(R.id.save_data);

        Calendar calendar = Calendar.getInstance();

        if (listToDoClass != null) {
            // Edit mode
            etNote.setText(listToDoClass.getNameListToDo());
            etDate.setText(listToDoClass.getDate());
            etTime.setText(listToDoClass.getTime());
        } else {
            // Create mode
            etNote.setText(""); // Clear EditText for create mode
            etDate.setText("Select Date");
            etTime.setText("Select Time");
        }

        etDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                etDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        etTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view12, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String selectedTime = hourOfDay + ":" + (minute < 10 ? "0" + minute : minute);
                etTime.setText(selectedTime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            String noteText = etNote.getText().toString();
            String dateText = etDate.getText().toString();
            String timeText = etTime.getText().toString();

            if (noteText.isEmpty() || dateText.equals("Select Date") || timeText.equals("Select Time")) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            } else {
                if (existingNote != null) {
                    // Edit mode
                    assert listToDoClass != null;
                    listToDoClass.setNameListToDo(noteText);
                    listToDoClass.setDate(dateText);
                    listToDoClass.setTime(timeText);
                    db.EditListToDo(listToDoClass); // Update with new date and time
                } else {
                    // Create mode
                    ListToDoClass newTodo = new ListToDoClass(noteText, dateText, timeText);
                    db.AddListToDo(newTodo);
                }
                loadData(); // Reload data after saving
                popup.dismiss();
            }
        });

    }

    private void loadData() {
        todolist = db.getAllListToDo();  // Get updated data from database
        listTodoAdapter = new ListTodoAdapter(todolist, this, this);
        rvList.setAdapter(listTodoAdapter);
    }


    @Override
    public void onHapusKegiatan(int position) {
        ListToDoClass selectedHapus = todolist.get(position);
        db.DeleteListToDo(selectedHapus);
        Toast.makeText(this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
        loadData();
    }

    @Override
    public void onUbahKegiatan(int position) {
        ListToDoClass selectedEdit = todolist.get(position);
        String ubahNote = selectedEdit.getNameListToDo();
        showForm(selectedEdit, ubahNote);
    }
}
