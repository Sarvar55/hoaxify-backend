package com.hoaxify.hoaxifybackend.hoax;

import com.hoaxify.hoaxifybackend.file.FileAttachment;
import com.hoaxify.hoaxifybackend.file.FileAttachmentRepository;
import com.hoaxify.hoaxifybackend.file.FileService;
import com.hoaxify.hoaxifybackend.hoax.vm.HoaxSubmitVm;
import com.hoaxify.hoaxifybackend.user.User;
import com.hoaxify.hoaxifybackend.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
@Slf4j
public class HoaxService {
    private final HoaxRepository hoaxRepository;
    private final UserService userService;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final FileService fileService;

    public HoaxService(HoaxRepository hoaxRepository, UserService userService, FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
    }


    public void saveHoax(HoaxSubmitVm hoaxSubmitVm, User user) {
        Hoax hoax = new Hoax();
        hoax.setUser(user);
        hoax.setContent(hoaxSubmitVm.getContent());

        hoaxRepository.save(hoax);
        /*buradaki sorun hoax tarafinda biz mappedBy="hoax" yaptik ve ilk olarak fileAtacRepo.save(hoax) yaptigi zaman
        @OneToOne olgudu icin bide fileAttacment tarafında bakilir diger tafada o hoax yoksa o zaman save edilir ve hoax tablosunda da
        bir tane kayit olusur sonra ise gelir bizim yalisn baslar 2 tane kayit olusur hoaxda
        onun da seebi bu sefer yine hoaxRep.save(hoax) gelir ama bu sefer bu hoax olusur ve id si de atanir
        ve o zaman da onceki hoaxdan sadece if sartina girecek olanda fileAttachment biligisi gozukur cunki burada ekleme islemi
        iliskili bir sekilde olur
        veri tabaninda da baktıgım zama o sekilde oluyor eger if sartina girerse gider o bakar eger bu fileAttachmen ile alakali bir hoax yoksa (gider setHoax(hoax) dedigin hoaxın hoaxId sinne)
        bakar eger orda id yoksa o zaman kendisi olustur ve ona forengy key olarak baglanır
        o zaman o hoaxı gider o hoax tablosun ekler ve iliskiyi kurar
        biz sonra yine hoaxRepo.save(hoax) dedigimizde yine farkli id li bir hoax olusur ve olusan hoaxlarda sadecee bir fileAtachme ile alakali olur
        o da if sartinda olan ile olusan olur yani sonrada save olan nin fileAtachmenti null olmus olur
        ama ilk basta hoaxRepo.save(hoax) desek o zaman da yeni bir id ile hoax olusur sonra ise o hoaxın id si ile kendine forengy key olusturur
        !!! tama olarak bu sekilde calisrir tum her seyi debug edim hangi asamalardan gectigine baktım
        */
        if (hoaxSubmitVm.getAttachmentId() != null) {
            Optional<FileAttachment> fileAttachmentOptional = fileAttachmentRepository.findById(hoaxSubmitVm.getAttachmentId());
            FileAttachment fileAttachment = fileAttachmentOptional.get();
            fileAttachment.setHoax(hoax);
            fileAttachmentRepository.save(fileAttachment);
        }


    }

    public Page<Hoax> getAllHoax(Pageable pageable) {
//        Pageable sortedByCreatedDesc =
//                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        return hoaxRepository.findAll(pageable);
    }

    public Page<Hoax> getHoaxByUserName(String username, Pageable pageable) {
        User user = userService.getUser(username).get();
        return hoaxRepository.findByUser_UserName(user.getUsername(), pageable);
    }

    public Page<Hoax> getOldHoaxes(Pageable pageable, String username, Long hoaxId) {
        Specification<Hoax> idLess = idLessThan(hoaxId);
        if (username != null) {
            Specification<Hoax> isUser = userIs(username);
            return hoaxRepository.findAll(idLess.and(isUser), pageable);
        }
        return hoaxRepository.findAll(idLess, pageable);
    }


    public long getNewHoaxesCount(Long hoaxId, String username) {
        Specification<Hoax> specification = idGreatherThan(hoaxId);
        if (username != null) {
            specification = specification.and(userIs(username));
            //return hoaxRepository.countByHoaxIdGreaterThanAndUser(hoaxId, user);
        }
        return hoaxRepository.count(specification);
    }


    public List<Hoax> getNewHoaxes(Long hoaxId, String username, Sort sort) {
        System.out.println(hoaxId + "hoaxId");
        Specification<Hoax> idGraetger = idGreatherThan(hoaxId);
        if (username != null) {
            return hoaxRepository.findAll(idGraetger.and(userIs(username)), sort);
            //return hoaxRepository.findByHoaxIdGreaterThanAndUser(hoaxId, userService.getUser(username).get(), sort);
        }
        //hoaxRepository.findAll(idGraetger, sort).stream().forEach((h) -> System.out.println(h.getAttachment().getFileType()));
        return hoaxRepository.findAll(idGraetger, sort);
        //return hoaxRepository.findByHoaxIdGreaterThan(hoaxId, sort);
    }

    Specification<Hoax> idLessThan(Long hoaxId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("hoaxId"), hoaxId);
    }

    Specification<Hoax> userIs(String username) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("userName"), username);
    }

    Specification<Hoax> idGreatherThan(Long hoaxId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("hoaxId"), hoaxId);
    }

    public void deleteHoax(Long hoaxId) {
        Hoax inDb = hoaxRepository.getOne(hoaxId);
        if (inDb.getAttachment() != null) {
            String fileName = inDb.getAttachment().getFileName();
            fileService.deleteFileAttachment(fileName);
        }
        hoaxRepository.deleteById(hoaxId);
    }

//    public void deleteHoaxesOfUser(String username) {
//        User inDB = userService.getUser(username).get();
//        Specification<Hoax> userOwned = userIs(username);
//        List<Hoax> hoaxesToBeRemove = hoaxRepository.findAll(userOwned);
//        hoaxRepository.deleteAll(hoaxesToBeRemove);
//    }
}
