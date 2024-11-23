package com.java.repositories;

import com.java.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNameContaining(String name);

    List<Member> findByEmailContaining(String email);

    List<Member> findByPhoneContaining(String phone);

    List<Member> findByStreetContaining(String street);

    List<Member> findByCityContaining(String city);

    List<Member> findByStateContaining(String state);

    List<Member> findByZipCodeContaining(String zipCode);
}