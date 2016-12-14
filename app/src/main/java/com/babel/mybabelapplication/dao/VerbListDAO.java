package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.VerbList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lucas on 14/12/2016.
 */

public class VerbListDAO {
    private final Realm realm;

    public VerbListDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer toutes les listVerb
    public List<VerbList> getAllVerbLists() {
        return realm.where(VerbList.class).findAll();
    }
    // ajouter une verbList
    public void addVerbList(VerbList verbList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(verbList);
        realm.commitTransaction();
    }
    // supprimer une verbList
    public void deleteVerbList(VerbList verbList) {
        realm.beginTransaction();
        verbList.deleteFromRealm();
        realm.commitTransaction();
    }
    // la bière est-elle présente dans les favoris
    /*public boolean isPresentInFavorites(Verb verb) {
        Verb verb = getVerb(verb.getId());

        return verb != null;
    }*/

    // récupérer un objet verbList par rapport à son ID
    public @Nullable
    VerbList getVerbList(String id) {
        return realm.where(VerbList.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet Verb par rapport à son name
    public @Nullable
    VerbList getVerbListByName(String name) {
        return realm.where(VerbList.class).equalTo("name", name).findFirst();
    }
}