package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;

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

    // récupérer toutes les bières des favoris
    public List<Verb> getAllVerbs() {
        List<Verb> allVerbs = realm.where(Verb.class).findAll();

        return allVerbs;
    }
    // ajouter une bière dans les favoris
    public void addVerb(Verb verb) {
//        Verb verb = createFromBeer(verb);

//        newVerb.setFrench(verb.getFrench());
//        newVerb.setInfinitive(verb.getInfinitive());
//        newVerb.setSimplePast(verb.getSimplePast());
//        newVerb.setPastParticiple(verb.getPastParticiple());
//        newVerb.setGrade(verb.getGrade());
        realm.beginTransaction();
//        Verb newVerb = realm.createObject(Verb.class, verb);
        realm.copyToRealmOrUpdate(verb);
        realm.commitTransaction();
    }
    // supprimer une bière des favoris
    public void deleteVerb(Verb verb) {
        realm.beginTransaction();
        verb.deleteFromRealm();
        realm.commitTransaction();
    }
    // la bière est-elle présente dans les favoris
    /*public boolean isPresentInFavorites(Verb verb) {
        Verb verb = getVerb(verb.getId());

        return verb != null;
    }*/

    public void addVerbToVerbListId(Verb verb, String verbListId) {
        realm.beginTransaction();
        VerbList verbList = realm.where(VerbList.class).equalTo("id", verbListId).findFirst();
        verbList.getVerbs().add(verb);
        realm.commitTransaction();
    }

    // récupérer un objet bière par rapport à son ID
    public @Nullable
        Verb getVerb(String id) {
        return realm.where(Verb.class).equalTo("id", id).findFirst();
    }

    // récupérer un objet Verb par rapport à son french
    public @Nullable
    Verb getVerbByFrench(String french) {
        return realm.where(Verb.class).equalTo("french", french, Case.INSENSITIVE).findFirst();
    }

    /*private static Verb createFromBeer(Verb verb) {
        Verb verbDB = new Verb();
        verbDB.setId(verb.getId());
        verbDB.setName(verb.getName());
        verbDB.setNameDisplay(verb.getNameDisplay());
        verbDB.setAbv(verb.getAbv());
        verbDB.setDescription(verb.getDescription());

        return verbDB;
    }*/
}
