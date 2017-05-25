package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Address;
import com.cocinero.infrastructure.repository.mongo.MongoAddressRepository;
import com.cocinero.infrastructure.repository.schemas.MongoAddress;
import com.cocinero.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl extends AbstractRepositoryImpl<Address,MongoAddress> implements AddressRepository {

    @Autowired
    private MongoAddressRepository mongoAddressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Address save(Address address) {
        MongoAddress mongoAddress = objectMapper.convertValue(address, MongoAddress.class);
        mongoAddress = mongoAddressRepository.save(mongoAddress);
        return objectMapper.convertValue(mongoAddress, Address.class);
    }

    @Override
    protected Class<Address> getType() {
        return Address.class;
    }

    @Override
    protected MongoRepository<MongoAddress, String> getMongoRepository() {
        return mongoAddressRepository;
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
