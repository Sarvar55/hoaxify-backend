package com.hoaxify.hoaxifybackend.hoax;

import com.hoaxify.hoaxifybackend.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
public class HoaxSecurityService {

    private final HoaxRepository hoaxRepository;

    public HoaxSecurityService(HoaxRepository hoaxRepository) {
        this.hoaxRepository = hoaxRepository;
    }

    public boolean isAllowedToDelete(Long hoaxId, User user) {
        Optional<Hoax> hoaxOpt = hoaxRepository.findById(hoaxId);
        if (!hoaxOpt.isPresent())
            return false;

        if (hoaxOpt.get().getUser().getUserId() != user.getUserId())
            return false;

        return true;
    }
}
