package com.sprc.tema2.ids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdsServiceImpl implements  IdsService{

    @Autowired
    private IdsRepository idsRepository;

    @Override
    public Integer getNextId(String collectionName) {
        Integer nextId = 0;
        Ids searchedId = idsRepository.findByCollectionName(collectionName);
        if (searchedId == null)
            idsRepository.save(new Ids(collectionName, 0));
        else {
            nextId = searchedId.getCurrentId() + 1;
            searchedId.setCurrentId(nextId);
            idsRepository.save(searchedId);
        }
        return nextId;
    }
}
