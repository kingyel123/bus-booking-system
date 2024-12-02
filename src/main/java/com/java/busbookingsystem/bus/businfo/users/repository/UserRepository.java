package com.java.busbookingsystem.bus.businfo.users.repository;



import com.java.busbookingsystem.bus.businfo.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
        }