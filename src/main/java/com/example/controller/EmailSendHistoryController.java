package com.example.controller;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.ProfileRole;
import com.example.service.EmailSendHistoryService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/email_send_history")

public class EmailSendHistoryController {
    @Autowired
    private EmailSendHistoryService emailSendHistoryService;

    @GetMapping("/{email}")
    public ResponseEntity<List<EmailSendHistoryDTO>> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(emailSendHistoryService.getByEmail(email));
    }
    @GetMapping("/byDate")
    public ResponseEntity<List<EmailSendHistoryDTO>> getByDate(@RequestParam("date") LocalDate date){
        return ResponseEntity.ok(emailSendHistoryService.getByDate(date));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<EmailSendHistoryDTO>> pagination(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size" , defaultValue = "1") Integer size,
                                                           HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(emailSendHistoryService.pagination(page, size));
    }


}
