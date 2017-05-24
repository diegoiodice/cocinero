package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Address;
import com.cocinero.infrastructure.repository.schemas.MongoAddress;
import com.cocinero.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private MongoAddressRepository mongoAddressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Address create(Address address) {
        MongoAddress mongoAddress = objectMapper.convertValue(address, MongoAddress.class);
        mongoAddress = mongoAddressRepository.save(mongoAddress);
        return objectMapper.convertValue(mongoAddress, Address.class);
    }

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public Collection<Address> find() {
        return null;
    }

    @Override
    public Address findById(String id) {
        return null;
    }
}
