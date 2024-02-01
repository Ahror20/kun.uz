package com.example.service;

import com.example.dto.*;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MDUtil;
import com.example.util.RandomUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private EmailSendHistoryService emailSendHistoryService;
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;
    @Autowired
    private SmsServerService smsServerService;
    private String text;
    private int countSms = 0;



    public ProfileDTO auth(AuthDTO profile) { // login
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(profile.getEmail(),
                MDUtil.encode(profile.getPassword()));

        if (optional.isEmpty()) {
            throw new AppBadException("Email or Password is wrong");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("profile not active");
        }

        ProfileEntity entity = optional.get();
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        return dto;
    }

    public Boolean registration(RegistrationDTO dto) {
        // validation
        if (dto.getName().length() < 2 || dto.getSurname().length() < 2) {
            throw new AppBadException("name or surname is wrong");
        }
        if (dto.getPhone().length() != 12) {
            throw new AppBadException("phone is not correct");
        }
//        // check
//        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
//        if (optional.isPresent()) {
//            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
//                Optional<EmailSendHistoryEntity> emailSendHistoryEntity = emailSendHistoryRepository.findTop1ByEmailOrderByCreatedDateDesc(dto.getEmail());
//                if (emailSendHistoryEntity.isPresent()) {
//                    LocalDateTime createdDate = emailSendHistoryEntity.get().getCreatedDate();
//                    if (countSms < 4 || LocalDateTime.now().isAfter(createdDate.plusMinutes(1))) {
//                        if (countSms == 4 && LocalDateTime.now().isAfter(createdDate.plusMinutes(1))) {
//                            countSms = 0;
//                        }
//                        sendEmailMessage(dto, optional.get());
//                        return true;
//                    }
//                    throw new AppBadException("You have reached the limit of sending sms. Try again 1 min later");
//                }
//                sendEmailMessage(dto, optional.get());
//                return true;
//            } else {
//                throw new AppBadException("Email exists");
//            }
//        }
//
//        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
//        LocalDateTime to = LocalDateTime.now();
//
//        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
//            throw new AppBadException("To many attempt. Please try after 1 minute.");
//        }
        // create
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.USER);
        profileRepository.save(entity);
        //send verification code (email/sms)

        String code = RandomUtil.getRandomSmsCode();
        smsServerService.send(dto.getPhone(),"Kun.uz Test verification code: ", code);
//
//        sendEmailMessage(dto, entity);
        return true;

    }

    private void sendEmailMessage(RegistrationDTO dto, ProfileEntity entity) {
        String jwt = JWTUtil.encodeForEmail(entity.getId());
        text = "Hello. \n To complete registration please link to the following link\n"
                + "http://localhost:8080/auth/verification/email/" + jwt;

        EmailSendHistoryDTO emailSendHistoryDTO = new EmailSendHistoryDTO();
        emailSendHistoryDTO.setMessage(text);
        emailSendHistoryDTO.setEmail(dto.getEmail());
        emailSendHistoryDTO.setProfile(entity);
        emailSendHistoryDTO.setStatus(entity.getStatus());
        emailSendHistoryService.saveEmailHistory(emailSendHistoryDTO);

        boolean isSending = mailSender.sendEmail(dto.getEmail(), "Complete registration", text);
        if (isSending) {
            countSms++;
        }
    }
    public String emailVerification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decode(jwt);
            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (optional.isEmpty()) {
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            int effectRows = profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
            if (effectRows == 1) {
                EmailSendHistoryDTO emailSendHistoryDTO = new EmailSendHistoryDTO();
                emailSendHistoryDTO.setMessage(text);
                emailSendHistoryDTO.setEmail(entity.getEmail());
                emailSendHistoryDTO.setProfile(entity);
                emailSendHistoryDTO.setStatus(ProfileStatus.ACTIVE);
                emailSendHistoryService.saveEmailHistory(emailSendHistoryDTO);
            }

        } catch (JwtException e) {
            e.printStackTrace();
            throw new AppBadException("Please tyre again.");
        }
        return "success";
    }


}
