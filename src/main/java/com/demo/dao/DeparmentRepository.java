package com.demo.dao;

import com.demo.bean.Deparment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeparmentRepository extends JpaRepository<Deparment,Long> {
}
