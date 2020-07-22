package com.softserve.edu.entity;

public class Solution {
    private final int idStudent;
    private final int idSprint;
    private final int score;

    public Solution(int idStudent, int idSprint, int score) {
        this.idStudent = idStudent;
        this.idSprint = idSprint;
        this.score = score;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdSprint() {
        return idSprint;
    }

    public int getScore() {
        return score;
    }
}
