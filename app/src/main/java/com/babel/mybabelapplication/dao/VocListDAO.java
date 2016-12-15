package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.VocList;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VocListDAO {
    private final Realm realm;

    public VocListDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer toutes les VocList
    public List<VocList> getAllVocLists() {
        return realm.where(VocList.class).findAllSorted("name");
    }

    // ajouter une vocList
    public void addVocList(VocList vocList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(vocList);
        realm.commitTransaction();
    }

    // Update name attributes
    public void updateVocListName(VocList vocList, String name) {
        realm.beginTransaction();
        vocList.setName(name);
        realm.copyToRealmOrUpdate(vocList);
        realm.commitTransaction();
    }

    // supprimer une vocList
    public void deleteVocList(VocList vocList) {
        realm.beginTransaction();
        vocList.deleteFromRealm();
        realm.commitTransaction();
    }

    // récupérer un objet vocList par rapport à son ID
    public @Nullable
    VocList getVocList(String id) {
        return realm.where(VocList.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet VocList par rapport à son name
    public @Nullable
    VocList getVocListByName(String name) {
        return realm.where(VocList.class).equalTo("name", name, Case.INSENSITIVE).findFirst();
    }
}
