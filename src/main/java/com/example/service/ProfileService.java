package com.example.service;

import com.example.dto.*;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(ProfileCRUDDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()){
            throw new AppBadException("this email is already exist");
        }
        if (dto.getRole().equals(ProfileRole.USER)){
            throw new AppBadException("you cannot create a user");
        }
        if (dto.getName().length()<3 || dto.getSurname().length()<3){
            throw new AppBadException("name or surname wrong");
        }
        boolean isTrue = checkPhone(dto.getPhone());
        if (!isTrue){
            throw new AppBadException("phone is wrong !");
        }
        if (dto.getPhone().length() != 13 ){
            throw new AppBadException("phone is wrong");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setCreatedDate(LocalDateTime.now());

        profileRepository.save(entity);
        return toDTO(entity);
    }


    public Boolean update(Integer id, ProfileCRUDDTO dto) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()){
            throw new AppBadException("profile not found");
        }
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()){
            throw new AppBadException("this email is already exist");
        }

        if (dto.getName().length()<3 || dto.getSurname().length()<3){
            throw new AppBadException("name or surname wrong");
        }
        if (dto.getPhone().length() != 13 ){
            throw new AppBadException("phone is wrong");
        }

        int effectRows = profileRepository.update(dto.getName(), dto.getSurname(), dto.getPhone(), dto.getPassword(), dto.getEmail(), dto.getStatus(),dto.getRole(),LocalDateTime.now(),id);
        if (effectRows != 1){
            throw new AppBadException(" unknown error ");
        }
        return true;

    }
    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setVisible(entity.getVisible());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    private   boolean checkPhone(String str) {
        // Define a regex pattern that matches only digits
        String regex = "\\d+";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(str);

        // Check if the entire string matches the pattern
        return matcher.matches();
    }

    public Boolean updateOwn(Integer id, ProfileCRUDDTO dto) {
        ProfileEntity profile = get(id);

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()){
            throw new AppBadException("this email is already exist");
        }
        if (!dto.getRole().equals(profile.getRole())){
            throw new AppBadException("you cannot update another role");
        }
        if (dto.getName().length()<3 || dto.getSurname().length()<3){
            throw new AppBadException("name or surname wrong");
        }
        boolean isTrue = checkPhone(dto.getPhone());
        if (!isTrue){
            throw new AppBadException("phone is wrong !");
        }
        if (dto.getPhone().length() != 12 ){
            throw new AppBadException("phone is wrong");
        }

        int effectRows = profileRepository.update(dto.getName(), dto.getSurname(), dto.getPhone(), dto.getPassword(), dto.getEmail(), dto.getStatus(),dto.getRole(),LocalDateTime.now(),id);
        if (effectRows != 1){
            throw new AppBadException(" unknown error ");
        }
        return true;

    }

    public PageImpl<ProfileDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable paging =  PageRequest.of(page-1,size,sort);

        Page<ProfileEntity> studentPage=profileRepository.findAll(paging);
        List<ProfileEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,paging,totalElements);
    }

    public Boolean deleteById(Integer id) {
        get(id);

        int effectRows = profileRepository.delete(id);
        if (effectRows != 1){
            throw new AppBadException("unknown error ");
        }
        return true;

    }

    private ProfileEntity get(Integer id){
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()){
            throw new AppBadException("profile not found");
        }
        return profile.get();
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filter, Integer page, Integer size) {
        PaginationResultDTO<ProfileEntity> paginationResult = profileCustomRepository.filter(filter, page, size);

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }

        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, paginationResult.getTotalSize());

    }

    public Boolean updateRole(Integer id, ProfileRole role) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadException("profile not found");
        }
        int n = profileRepository.updateRole(id, role,LocalDateTime.now());
        return n == 1;
    }
}
