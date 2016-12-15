package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VerbListDAO {
    private final Realm realm;

    public VerbListDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer tous les listVerb
    public List<VerbList> getAllVerbLists() {
        return realm.where(VerbList.class).findAllSorted("name");
    }
    // ajouter une verbList
    public void addVerbList(VerbList verbList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(verbList);
        realm.commitTransaction();
    }

    // Update name attributes
    public void updateVerbListName(VerbList verbList, String name) {
        realm.beginTransaction();
        verbList.setName(name);
        realm.copyToRealmOrUpdate(verbList);
        realm.commitTransaction();
    }

    // supprimer une verbList
    public void deleteVerbList(VerbList verbList) {
        realm.beginTransaction();
        verbList.deleteFromRealm();
        realm.commitTransaction();
    }

    // récupérer un objet verbList par rapport à son ID
    public @Nullable
    VerbList getVerbList(String id) {
        return realm.where(VerbList.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet VerbList par rapport à son name
    public @Nullable
    VerbList getVerbListByName(String name) {
        return realm.where(VerbList.class).equalTo("name", name, Case.INSENSITIVE).findFirst();
    }
}
