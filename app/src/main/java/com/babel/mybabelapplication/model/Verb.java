package com.babel.mybabelapplication.model;

/**
 * Created by Lucas on 13/12/2016.
 */

public class Verb {
    private String id;
    private String infinitive;
    private String simplePast;
    private String pastParticiple;

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
}
