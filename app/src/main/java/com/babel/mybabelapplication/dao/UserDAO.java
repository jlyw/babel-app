package com.babel.mybabelapplication.dao;

import android.support.annotation.Nullable;

import com.babel.mybabelapplication.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UserDAO {
    private final Realm realm;
    private User user;

    public UserDAO() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    // récupérer tous les users
    public List<User> getAllUsers() {
        List<User> allUsers = realm.where(User.class).findAll();

        return allUsers;
    }

    // ajouter un user
    public void addUser(User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    // Update createdList
    public void updateUserCreatedList(User user, Integer createdList) {
        realm.beginTransaction();
        user.setCreatedList(createdList);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    public void upUserCreatedList() {
        user = getUser();
        realm.beginTransaction();
        user.setCreatedList(user.getCreatedList()+1);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    // Update exerciceDone
    public void updateUserExerciceDone(User user, Integer exerciceDone) {
        realm.beginTransaction();
        user.setExerciceDone(exerciceDone);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    public void upUserExerciceDone() {
        user = getUser();
        realm.beginTransaction();
        user.setExerciceDone(user.getExerciceDone()+1);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    // Update Answers (all & good)
    public void updateUserGoodAnswer(User user, Integer goodAnswer) {
        realm.beginTransaction();
        user.setGoodAnswer(goodAnswer);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    public void updateUserAllAnswer(User user, Integer allAnswer) {
        realm.beginTransaction();
        user.setAllAnswer(allAnswer);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    public void userGoodAnswer() {
        user = getUser();
        realm.beginTransaction();
        user.setGoodAnswer(user.getGoodAnswer()+1);
        user.setAllAnswer(user.getAllAnswer()+1);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    public void userBadAnswer() {
        user = getUser();
        realm.beginTransaction();
        user.setAllAnswer(user.getAllAnswer()+1);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    // supprimer un user
    public void deleteUser(User user) {
        realm.beginTransaction();
        user.deleteFromRealm();
        realm.commitTransaction();
    }

    // Un user est-il présent ?
    public boolean userIsPresentInRealm() {
        return getAllUsers().size() > 0;
    }

    // récupérer le user
    public @Nullable
    User getUser() {
        return realm.where(User.class).equalTo("id", "USER_ID").findFirst();
    }
}
