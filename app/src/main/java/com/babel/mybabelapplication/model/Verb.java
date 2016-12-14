package com.babel.mybabelapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Lucas on 13/12/2016.
 */

public class Verb extends RealmObject {
    @PrimaryKey @Required
    private String id;

    @Required
    private String infinitive;
    @Required
    private String simplePast;
    @Required
    private String pastParticiple;
    @Required
    private String french;
    private Integer grade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getSimplePast() {
        return simplePast;
    }

    public void setSimplePast(String simplePast) {
        this.simplePast = simplePast;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public void setPastParticiple(String pastParticiple) { this.pastParticiple = pastParticiple; }

    public String getFrench() { return french; }

    public void setFrench(String french) { this.french = french; }

    public Integer getGrade() { return grade; }

    public void setGrade(Integer grade) { this.grade = grade; }
}
