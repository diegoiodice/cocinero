package com.cocinero.infrastructure.repository;

import com.cocinero.domain.Address;
import com.cocinero.domain.User;
import com.cocinero.domain.UserAddress;
import com.cocinero.domain.UserProfile;
import com.cocinero.infrastructure.SpringIntegrationTest;
import com.cocinero.repository.AddressRepository;
import com.cocinero.repository.UserProfileRepository;
import com.cocinero.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserProfileRepositoryImplITest extends SpringIntegrationTest{

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() throws Exception {

        User user = userRepository.save(User.builder()
                .email("pepeto@pachanga.com")
                .password("123456")
                .build());

        Address address = addressRepository.save(Address.builder()
                .addressLine1("94 Parkway")
                .postCode("NW1 7AN")
                .city("London")
                .country("UK")
                .build());

        UserAddress userAddress = UserAddress.builder()
                .address(address)
                .alias("Home")
                .telephone("07453815909")
                .build();

        UserProfile userProfile = UserProfile.builder()
                .id(user.getId())
                .names("Pepeto")
                .lastNames("Pachanga")
                .dateOfBirth(Date.from(LocalDate.parse("2016-04-17").atStartOfDay().toInstant(ZoneOffset.UTC)))
                .build();

        userProfile.getAddresses().add(userAddress);

        userProfileRepository.save(userProfile);

        assertThat(userProfileRepository.findById(user.getId())).isNotNull();
    }

}