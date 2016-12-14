package com.babel.mybabelapplication.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class VocList extends RealmObject {
    @PrimaryKey
    private String id;

    @Required
    private String name;
    private RealmList<Voc> vocs;

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

    public RealmList<Voc> getVocs() {
        return vocs;
    }

    public void setVocs(RealmList<Voc> vocs) {
        this.vocs = vocs;
    }
}
