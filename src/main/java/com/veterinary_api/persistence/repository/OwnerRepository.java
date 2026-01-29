package com.veterinary_api.persistence.repository;

import com.veterinary_api.persistence.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {
}
