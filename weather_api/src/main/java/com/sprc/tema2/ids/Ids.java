package com.sprc.tema2.ids;

import org.springframework.data.annotation.Id;

public class Ids {

    @Id
    String collectionName;
    Integer currentId;

    public Ids(String collectionName, Integer currentId) {
        this.collectionName = collectionName;
        this.currentId = currentId;
    }

    public void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public int getCurrentId() {
        return currentId;
    }


}
