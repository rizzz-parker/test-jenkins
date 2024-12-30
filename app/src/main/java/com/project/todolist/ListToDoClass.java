package com.project.todolist;

public class ListToDoClass {
    int idListToDo;
    String nameListToDo;
    private String date;
    private String time;

    public ListToDoClass() {
        super();
    }

    public ListToDoClass(int mIdListToDo, String mNameListToDo) {
        super();
        this.idListToDo = mIdListToDo;
        this.nameListToDo = mNameListToDo;
    }

    public ListToDoClass(String mNameListToDo) {
        this.nameListToDo = mNameListToDo;
    }

    public ListToDoClass(String nameListToDo, String date, String time) {
        this.nameListToDo = nameListToDo;
        this.date = date;
        this.time = time;
    }

    public int getIdListToDo() {
        return idListToDo;
    }

    public void setIdListToDo(int idListToDo) {
        this.idListToDo = idListToDo;
    }

    public String getNameListToDo() {
        return nameListToDo;
    }

    public void setNameListToDo(String nameListToDo) {
        this.nameListToDo = nameListToDo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

//coba coba wlee
