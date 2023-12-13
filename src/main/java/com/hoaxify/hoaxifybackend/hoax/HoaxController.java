package com.hoaxify.hoaxifybackend.hoax;

import com.hoaxify.hoaxifybackend.Shared.CurrentUser;
import com.hoaxify.hoaxifybackend.hoax.vm.HoaxSubmitVm;
import com.hoaxify.hoaxifybackend.hoax.vm.HoaxVm;
import com.hoaxify.hoaxifybackend.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@RestController
@RequestMapping("/api/1.0")
@Slf4j
public class HoaxController {
    private final HoaxService hoaxService;

    public HoaxController(HoaxService hoaxService) {
        this.hoaxService = hoaxService;
    }

    @PostMapping("/hoaxes")
    public ResponseEntity<?> saveHoax(@Valid @RequestBody HoaxSubmitVm hoaxSubmitVm, @CurrentUser User user) {
        log.info("buraya kadar gelmis");
        hoaxService.saveHoax(hoaxSubmitVm, user);
        return ResponseEntity.ok().body("Hoax is saved");
    }

    @GetMapping("/hoaxes")
    public ResponseEntity<Page<HoaxVm>> getAllHoax(@PageableDefault(sort = "hoaxId", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(hoaxService.getAllHoax(pageable).map(HoaxVm::new));
    }

    @GetMapping({"/hoaxes/{hoaxId:[0-9]+}", "/users/{username}/hoaxes/{hoaxId:[0-9]+}"})
    public ResponseEntity<?> getAllHoaxRelative(
            @PathVariable(required = false) String username,
            @PageableDefault(sort = "hoaxId", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long hoaxId,
            @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
            @RequestParam(name = "direction", defaultValue = "before") String direction) {

        if (count) {
            long newCount = hoaxService.getNewHoaxesCount(hoaxId, username);
            log.info("buraya girdim:");
            Map<String, Long> response = new HashMap<>();
            response.put("count", newCount);
            return ResponseEntity.ok(response);
        } else if (direction.equals("after")) {
            List<HoaxVm> newHoaxes = hoaxService.getNewHoaxes(hoaxId, username, pageable.getSort())
                    .stream()
                    .map(HoaxVm::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(newHoaxes);
        }
        return ResponseEntity.ok(hoaxService.getOldHoaxes(pageable, username, hoaxId).map(HoaxVm::new));
    }


    @GetMapping("/users/{username}/hoaxes")
    public ResponseEntity<Page<HoaxVm>> getHoaxByUsername(@PageableDefault(sort = "hoaxId", direction = Sort.Direction.DESC) Pageable pageable,
                                                          @PathVariable String username) {
        return ResponseEntity.ok(hoaxService.getHoaxByUserName(username, pageable)
                .map(HoaxVm::new));
    }

    //    @GetMapping("/users/{username}/hoaxes/{hoaxId:[0-9]+}")
//    public ResponseEntity<?> getHoaxByUsernameRelative(@PageableDefault(sort = "hoaxId", direction = Sort.Direction.DESC) Pageable pageable,
//                                                       @PathVariable String username, @PathVariable Long hoaxId,
//                                                       @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
//                                                       @RequestParam(name = "direction", required = false, defaultValue = "before") String direction) {
//        if (count) {
//            log.info("buraya da girdim ");
//            Long newHoaxCount = hoaxService.getByUserNewHoaxCount(hoaxId, username);
//            Map<String, Long> response = new HashMap<>();
//            log.info("newHoaxCount:{}", newHoaxCount);
//            response.put("count", newHoaxCount);
//            return ResponseEntity.ok(response);
//        }
//        if (direction.equals("after")) {
//            List<HoaxVm> newHoaxesForUser = hoaxService.getNewHoaxesForUser(hoaxId, username, pageable.getSort())
//                    .stream().map(HoaxVm::new)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(newHoaxesForUser);
//        }
//        return ResponseEntity.ok(hoaxService.getOldHoaxByUserName(hoaxId, username, pageable)
//                .map(HoaxVm::new));
//    }
    @DeleteMapping("/hoaxes/{hoaxId:[0-9]+}")
    @PreAuthorize("@hoaxSecurityService.isAllowedToDelete(#hoaxId,principal)")
    public ResponseEntity<String> deleteHoax(@PathVariable Long hoaxId) {
        hoaxService.deleteHoax(hoaxId);
        return ResponseEntity.ok("Hoax Succesfully deleted");
    }
}
