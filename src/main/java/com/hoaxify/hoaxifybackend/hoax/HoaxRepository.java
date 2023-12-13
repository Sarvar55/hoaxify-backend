package com.hoaxify.hoaxifybackend.hoax;

import com.hoaxify.hoaxifybackend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Repository
public interface HoaxRepository extends JpaRepository<Hoax, Long>, JpaSpecificationExecutor<Hoax> {

    Page<Hoax> findByUser_UserName(String userName, Pageable pageable);

    Page<Hoax> findByHoaxIdLessThan(Long hoaxId, Pageable pageable);

    Page<Hoax> findByHoaxIdLessThanAndUser(Long hoaxId, User user, Pageable pageable);

    Long countByHoaxIdGreaterThan(Long hoaxId);

    Long countByHoaxIdGreaterThanAndUser(Long hoaxId, User user);

    List<Hoax> findByHoaxIdGreaterThan(Long hoaxId, Sort sort);

    List<Hoax> findByHoaxIdGreaterThanAndUser(Long hoaxId, User user, Sort sort);
}
