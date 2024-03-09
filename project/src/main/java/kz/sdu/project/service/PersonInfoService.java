package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.PersonInfo;
import kz.sdu.project.repository.PersonInfoRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonInfoService {

    private final PersonInfoRepo personInfoRepo;

    @Autowired
    public PersonInfoService(PersonInfoRepo personInfoRepo) {
        this.personInfoRepo = personInfoRepo;
    }

    public Optional<PersonInfo> findById(Integer id) {
        return personInfoRepo.findById(id);
    }

    public Optional<PersonInfo> findByPersonId(Integer personId) {
        return personInfoRepo.findByPersonId(personId);
    }

    public List<PersonInfo> findAll() {
        return personInfoRepo.findAll();
    }

    public PersonInfo save(PersonInfo personInfo) {
        return personInfoRepo.save(personInfo);
    }

    public void delete(PersonInfo personInfo) {
        personInfoRepo.delete(personInfo);
    }

    public void deleteById(Integer id) {
        personInfoRepo.deleteById(id);
    }

    public String uploadUserImage(Integer userId, MultipartFile imageFile) throws IOException {
        Optional<PersonInfo> personInfoOptional = personInfoRepo.findById(userId);
        if (personInfoOptional.isEmpty()) {
            throw new IllegalStateException("PersonInfo with ID " + userId + " does not exist.");
        }

        PersonInfo personInfo = personInfoOptional.get();
        byte[] imageBytes = imageFile.getBytes();
        // personInfo.setImage(imageBytes);
        personInfoRepo.save(personInfo);

        return "Image uploaded successfully for PersonInfo with ID " + userId;
    }

//     public byte[] getUserImage(Integer userId) {
//         Optional<PersonInfo> personInfoOptional = personInfoRepo.findById(userId);
//         if (personInfoOptional.isPresent() && personInfoOptional.get().getImage() != null) {
//             return personInfoOptional.get().getImage();
//         } else {
//             throw new IllegalStateException("Image data not found for PersonInfo with ID " + userId);
//         }
//     }
}
