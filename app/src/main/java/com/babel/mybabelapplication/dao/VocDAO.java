package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.Collections;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VocDAO {
    private final Realm realm;

    public VocDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer tous les vocs
    public List<Voc> getAllVocs() {
        return realm.where(Voc.class).findAll();
    }

    // récupérer tous les vocs d'une liste
    public List<Voc> getAllVocsOffOneList(String listId) {
        VocList vocList = realm.where(VocList.class).equalTo("id", listId).findFirst();
        return vocList.getVocs();
    }

    // randomise une liste de voc
    public List<Voc> randomAllVocsOffOneList(List<Voc> vocs) {
        realm.beginTransaction();
        Collections.shuffle(vocs);
        realm.commitTransaction();
        return vocs;
    }

    // ajouter un voc
    public void addVoc(Voc voc) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(voc);
        realm.commitTransaction();
    }

    // Update all attributes
    public void updateVocFrench(Voc voc, String french) {
        realm.beginTransaction();
        voc.setFrench(french);
        realm.copyToRealmOrUpdate(voc);
        realm.commitTransaction();
    }
    public void updateVocEnglish(Voc voc, String english) {
        realm.beginTransaction();
        voc.setEnglish(english);
        realm.copyToRealmOrUpdate(voc);
        realm.commitTransaction();
    }

    public void updateVocGrade(Voc voc, Integer grade) {
        realm.beginTransaction();
        voc.setGrade(grade);
        realm.copyToRealmOrUpdate(voc);
        realm.commitTransaction();
    }

    // supprimer un voc
    public void deleteVoc(Voc voc) {
        realm.beginTransaction();
        voc.deleteFromRealm();
        realm.commitTransaction();
    }

    // la bière est-elle présente dans les favoris
    /*public boolean isPresentInFavorites(Voc voc) {
        Voc voc = getVoc(voc.getId());

        return voc != null;
    }*/

    //    Ajouter un voc à une liste
    public void addVocToVocListId(Voc voc, String vocListId) {
        realm.beginTransaction();
        VocList vocList = realm.where(VocList.class).equalTo("id", vocListId).findFirst();
        vocList.getVocs().add(voc);
        realm.commitTransaction();
    }

    // récupérer un objet voc par rapport à son ID
    public @Nullable
    Voc getVoc(String id) {
        return realm.where(Voc.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet Voc par rapport à son french
    public @Nullable
    Voc getVocByFrench(String french) {
        return realm.where(Voc.class).equalTo("french", french, Case.INSENSITIVE).findFirst();
    }

    // récupérer un objet Voc par rapport à son english
    public @Nullable
    Voc getVocByEnglish(String english) {
        return realm.where(Voc.class).equalTo("english", english, Case.INSENSITIVE).findFirst();
    }
}
