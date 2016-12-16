package com.babel.mybabelapplication.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class VerbList extends RealmObject {
    @PrimaryKey
    private String id;

    @Required
    private String name;
    private RealmList<Verb> verbs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Verb> getVerbs() {
        return verbs;
    }

    public void setVerbs(RealmList<Verb> verbs) {
        this.verbs = verbs;
    }
}
