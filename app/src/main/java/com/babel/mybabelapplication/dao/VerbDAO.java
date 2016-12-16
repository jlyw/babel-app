package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.List;
import java.util.UUID;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lucas on 14/12/2016.
 */

public class VerbDAO {
    private final Realm realm;

    public VerbDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer tous les verbs
    public List<Verb> getAllVerbs() {

        return realm.where(Verb.class).findAll();
    }

    // récupérer tous les verbs d'une liste
    public List<Verb> getAllVerbsOffOneList(String listId) {
        VerbList verbList = realm.where(VerbList.class).equalTo("id", listId).findFirst();
        return verbList.getVerbs();
    }

    // ajouter un verb
    public void addVerb(Verb verb) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }

    // Update all attributes
    public void updateVerbInfinitive(Verb verb, String infinitive) {
        realm.beginTransaction();
        verb.setInfinitive(infinitive);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void updateVerbFrench(Verb verb, String french) {
        realm.beginTransaction();
        verb.setFrench(french);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void updateVerbSimplePast(Verb verb, String simplePast) {
        realm.beginTransaction();
        verb.setSimplePast(simplePast);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void updateVerbPastParticiple(Verb verb, String pastParticiple) {
        realm.beginTransaction();
        verb.setPastParticiple(pastParticiple);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void updateVerbGrade(Verb verb, Integer grade) {
        realm.beginTransaction();
        verb.setGrade(grade);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void upVerbGrade(Verb verb) {
        realm.beginTransaction();
        if(verb.getGrade() < 2) {
            verb.setGrade(verb.getGrade()+1);
        }
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    public void downVerbGrade(Verb verb) {
        realm.beginTransaction();
        if(verb.getGrade() > 0) {
            verb.setGrade(verb.getGrade()-1);
        }
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }

    // supprimer un verb
    public void deleteVerb(Verb verb) {
        realm.beginTransaction();
        verb.deleteFromRealm();
        realm.commitTransaction();
    }

//    Ajouter un verb à une liste
    public void addVerbToVerbListId(Verb verb, String verbListId) {
        realm.beginTransaction();
        VerbList verbList = realm.where(VerbList.class).equalTo("id", verbListId).findFirst();
        verbList.getVerbs().add(verb);
        realm.commitTransaction();
    }

    // récupérer un objet verb par rapport à son ID
    public @Nullable
        Verb getVerb(String id) {
        return realm.where(Verb.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet Verb par rapport à son french
    public @Nullable
    Verb getVerbByFrench(String french) {
        return realm.where(Verb.class).equalTo("french", french, Case.INSENSITIVE).findFirst();
    }

    // récupérer un objet Verb par rapport à son infinitive
    public @Nullable
    Verb getVerbByInfinitive(String infinitive) {
        return realm.where(Verb.class).equalTo("infinitive", infinitive, Case.INSENSITIVE).findFirst();
    }
}
