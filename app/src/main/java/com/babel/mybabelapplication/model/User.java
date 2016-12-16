package com.babel.mybabelapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lucas on 16/12/2016.
 */

public class User extends RealmObject {
    @PrimaryKey @Required
    private String id;

    @Required
    private Integer createdList;
    @Required
    private Integer exerciceDone;
    @Required
    private Integer goodAnswer;
    @Required
    private Integer allAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreatedList() {
        return createdList;
    }

    public void setCreatedList(Integer createdList) {
        this.createdList = createdList;
    }

    public Integer getExerciceDone() {
        return exerciceDone;
    }

    public void setExerciceDone(Integer exerciceDone) {
        this.exerciceDone = exerciceDone;
    }

    public Integer getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(Integer goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public Integer getAllAnswer() {
        return allAnswer;
    }

    public void setAllAnswer(Integer allAnswer) {
        this.allAnswer = allAnswer;
    }
}
