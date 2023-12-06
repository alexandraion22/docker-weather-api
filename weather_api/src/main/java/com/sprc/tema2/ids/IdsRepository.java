package com.sprc.tema2.ids;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdsRepository extends MongoRepository<Ids,String> {

    Ids findByCollectionName(String collectionName);

}
