package food.system.service.impl;


import food.system.dto.AdminDto;
import food.system.dto.UserDto;
import food.system.entity.Admin;
import food.system.entity.Image;
import food.system.entity.User;
import food.system.mapper.AdminMapper;
import food.system.repository.AdminRepository;
import food.system.repository.ImageRepository;
import food.system.role.Role;
import food.system.service.main.AdminService;
import food.system.util.ImageUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static food.system.helper.ResponseEntityHelper.NOT_FOUND;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    public static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder encoder;
    private final ImageRepository imageRepository;

    @PostConstruct
    public void init() {
        if(adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .user(
                            User.builder()
                                    .email("admin@gmail.com")
                                    .password(encoder.encode("123456"))
                                    .role(Role.ADMIN).build()
                    )
                    .firstName("Ism")
                    .lastName("Familiya")
                    .build();
            adminRepository.save(admin);
        }
    }

    @Override
    public ResponseEntity<?> updateAdmin(AdminDto adminDto, MultipartFile file) {
        try {
            Optional<Admin> optional = adminRepository.findById(adminDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();

            Admin admin = optional.get();
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());

            if(file != null)
                updateAdminPhoto(admin, file);
            updateAdminUser(adminDto, admin);

            adminRepository.save(admin);

            return ResponseEntity.ok(adminDto);
        }catch (Exception e){
            logger.error("Error while updating a admin".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }




    @Override
    public ResponseEntity<?> findAdminById(Integer id) {
        Optional<Admin> optional = adminRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(adminMapper.toDto(optional.get()));
        return NOT_FOUND();
    }



    private void updateAdminUser(AdminDto adminDto, Admin admin){
        User user = admin.getUser();
        UserDto userDto = adminDto.getUser();
        user.setEmail(adminDto.getUser().getEmail());
        if(userDto.getPassword() != null)
            user.setPassword(encoder.encode(userDto.getPassword()));
    }


    private void updateAdminPhoto(Admin admin, MultipartFile file) throws IOException {
        Image oldImage = admin.getImage();
        imageRepository.deleteById(oldImage.getId());
        Files.delete(Path.of(oldImage.getPath()));
        admin.setImage(buildImage(file));
    }


    private Image buildImage(MultipartFile file) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .ext(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                .path(ImageUtil.uploadImage(file))
                .build();

    }

}

