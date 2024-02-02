package com.example.service;

import com.example.dto.*;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.enums.SmsStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.repository.SmsServerRepository;
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
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private SmsHistoryService smsHistoryService;

    private String text;




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
        if (dto.getPhone().length()!=13){
            throw new AppBadException("phone number is wrong");
        }
        // check
//        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
//        if (optional.isPresent()) {
//            LocalDateTime from = LocalDateTime.now().minusMinutes(1);
//            LocalDateTime to = LocalDateTime.now();
//            if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
//                throw new AppBadException("To many attempt. Please try after 1 minute.");
//            }
//            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
//               sendEmailMessage(dto, optional.get());
//                return true;
//            } else {
//                throw new AppBadException("Email exists");
//            }
     //   }
         Optional<ProfileEntity> optionalPhone = profileRepository.findByPhone(dto.getPhone());
        if (optionalPhone.isPresent()){
            if (optionalPhone.get().getStatus().equals(ProfileStatus.REGISTRATION)){
                LocalDateTime from = LocalDateTime.now().minusMinutes(1);
                LocalDateTime to = LocalDateTime.now();
                if (smsHistoryRepository.countSendPhone(dto.getPhone(), from, to) >= 2) {
                    throw new AppBadException("To many attempt. Please try after 1 minute.");
                }
                sendSms(dto);
            }
            else {
                throw new AppBadException("phone exists");
            }
        }

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

        //sms history
        sendSms(dto);
        //gmail
      //  sendEmailMessage(dto, entity);
         return true;
    }

    private void sendSms(RegistrationDTO dto) {
        String code = RandomUtil.getRandomSmsCode();
        smsServerService.send(dto.getPhone(),"Tasdiqlash kodi: \n", code);



        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setMessage(code);
        smsHistoryEntity.setStatus(SmsStatus.NEW);
        smsHistoryEntity.setPhone(dto.getPhone());
        smsHistoryEntity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(smsHistoryEntity);
    }

    private void sendEmailMessage(RegistrationDTO dto, ProfileEntity entity) {
         String jwt = JWTUtil.encodeForEmail(entity.getId());
         text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        mailSender.sendEmail(dto.getEmail(), "Complete registration", text);

        EmailSendHistoryDTO emailSendHistoryDTO = new EmailSendHistoryDTO();
        emailSendHistoryDTO.setMessage(text);
        emailSendHistoryDTO.setEmail(dto.getEmail());
        emailSendHistoryDTO.setProfile(entity);
        emailSendHistoryDTO.setStatus(entity.getStatus());
        emailSendHistoryService.saveEmailHistory(emailSendHistoryDTO);

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
    public String smsVerification(String code) {
        try {
            Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByMessage(code);
            if (optional.isEmpty()) {
                throw new AppBadException("code is wrong");
            }
            Optional<ProfileEntity> entity = profileRepository.findByPhone(optional.get().getPhone());
            if (!entity.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            int effectRows = profileRepository.updateStatus(entity.get().getId(), ProfileStatus.ACTIVE);
            if (effectRows == 1) {
                smsHistoryRepository.updateStatus(optional.get().getId(),SmsStatus.USED,LocalDateTime.now());
            }

        } catch (JwtException e) {
            e.printStackTrace();
            throw new AppBadException("Please tyre again.");
        }
        return "success";
    }


    public String resentEmail(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()){
            throw new AppBadException("You are not registered. Please register first! ");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)){
            throw new AppBadException("you are not allowed");
        }
            LocalDateTime from = LocalDateTime.now().minusMinutes(1);
            LocalDateTime to = LocalDateTime.now();
            if (emailSendHistoryRepository.countSendEmail(email, from, to) >= 3) {
                throw new AppBadException("To many attempt. Please try after 1 minute.");
            }

            String code = RandomUtil.getRandomSmsCode();
            boolean isSend = mailSender.sendEmail(email, "Resent password", "Please do not give the code to anyone! \n" + code);
            if (!isSend) {
                return "error";
            }
            EmailSendHistoryDTO emailSendHistoryDTO = new EmailSendHistoryDTO();
            emailSendHistoryDTO.setMessage(code);
            emailSendHistoryDTO.setEmail(email);
            emailSendHistoryDTO.setProfile(optional.get());
            emailSendHistoryDTO.setStatus(optional.get().getStatus());
            emailSendHistoryService.saveEmailHistory(emailSendHistoryDTO);
            return "Success";
        }

    public String resentPhone(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhone(phone);
        if (optional.isEmpty()){
            throw new AppBadException("You are not registered. Please register first! ");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)){
            throw new AppBadException("you are not allowed");
        }
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (smsHistoryRepository.countSendPhone(phone, from, to) > 1) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }

        String code = RandomUtil.getRandomSmsCode();
        smsServerService.send(phone, "<#> Resent password", " Please do not give the code to anyone! \n code: " + code);

        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setMessage(code);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPhone(phone);
        entity.setStatus(SmsStatus.NEW);
        smsHistoryRepository.save(entity);

        return "Success";

    }
}
