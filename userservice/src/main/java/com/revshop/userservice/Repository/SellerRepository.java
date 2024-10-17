package com.revshop.userservice.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.userservice.entity.Seller;
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{
	Seller findByEmailAndPassword(String email,String password);
	Optional<Seller> findByEmail(String email);
}