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
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static food.system.helper.ResponseEntityHelper.NOT_FOUND;
import static food.system.util.ImageUtil.buildImage;


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
                                    .username("admin")
                                    .password(encoder.encode("123456"))
                                    .role(Role.ADMIN).build()
                    )
                    .name("Name")
                    .build();
            adminRepository.save(admin);
        }
    }

    @Override
    public ResponseEntity<AdminDto> updateAdmin(AdminDto adminDto, MultipartFile file) {
        try {
            Optional<Admin> optional = adminRepository.findById(adminDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND(null);

            Admin admin = optional.get();
            admin.setName(adminDto.getName());

            if(file != null)
                updateAdminPhoto(admin, file);
            updateAdminUser(adminDto, admin);

            adminRepository.save(admin);
            adminDto = adminMapper.toDto(admin);
            return ResponseEntity.ok(adminDto);
        }catch (Exception e){
            logger.error("Error while updating an admin: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }




    @Override
    public ResponseEntity<AdminDto> findAdminById(Integer id) {
        Optional<Admin> optional = adminRepository.findById(id);
        return optional.map(admin -> ResponseEntity.ok(adminMapper.toDto(admin)))
                .orElseGet(() -> NOT_FOUND(null));
    }



    private void updateAdminUser(AdminDto adminDto, Admin admin){
        User user = admin.getUser();
        UserDto userDto = adminDto.getUser();
        user.setUsername(adminDto.getUser().getUsername());
        if(userDto.getPassword() != null)
            user.setPassword(encoder.encode(userDto.getPassword()));
    }


    private void updateAdminPhoto(Admin admin, MultipartFile file) throws IOException {
        if(admin.getImage() != null) {
            Image oldImage = admin.getImage();
            imageRepository.deleteById(oldImage.getId());
            Files.delete(Path.of("uploads/" + oldImage.getPath()));
        }
        admin.setImage(buildImage(file));
    }


}

