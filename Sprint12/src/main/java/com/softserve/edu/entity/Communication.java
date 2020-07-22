package com.softserve.edu.entity;

public class Communication {
    private final int idStudent;
    private final int idMentor;

    public Communication(int idStudent, int idMentor) {
        this.idStudent = idStudent;
        this.idMentor = idMentor;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdMentor() {
        return idMentor;
    }
}
