package com.hoaxify.hoaxifybackend.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String user);


    Page<User> findByUserNameNot(String userName, Pageable pageable);
    //burada artik spring data bizim icin bir qery retemiyor
    // JPQL
//    @Query(value = "select u from User u")
//    Page<UserProjection>  getAllUserProjection(Pageable pageable);//burada findAll() diyemedik cunki findAll belli o sadece User tipinde olan veriyi donecek

//    @Transactional
//    void deleteByUserName(String userName);

}
