package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer>, PagingAndSortingRepository<ProfileEntity,Integer> {

    Optional<ProfileEntity> findByEmail(String email);
    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByEmailAndPassword(String email,String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name =:name, surname =:surname, phone=:phone, password=:password, email=:email, status=:status,role=:role, updatedDate = :updatedDate  where id=:id")
    int update(@Param("name") String name,
               @Param("surname") String surname,
               @Param("phone") String phone,
               @Param("password") String password,
               @Param("email") String email,
               @Param("status") ProfileStatus status,
               @Param("role") ProfileRole role,
               @Param("updatedDate") LocalDateTime updatedDate,
               @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set status =?2 where id = ?1")
    int  updateStatus(Integer id, ProfileStatus active);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set role =?2, updatedDate=?3 where id = ?1")
    int  updateRole(Integer id, ProfileRole role,LocalDateTime updatedDate);

    Optional<ProfileEntity> findByPhoneOrEmail(String phone,String email);
}
