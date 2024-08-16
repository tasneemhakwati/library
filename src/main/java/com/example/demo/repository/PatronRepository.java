package com.example.demo.repository;

import com.example.demo.entities.Patron;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {




}
