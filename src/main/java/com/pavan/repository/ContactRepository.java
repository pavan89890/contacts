package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
	public List<Contact> findByMobile(long mobile);
}
