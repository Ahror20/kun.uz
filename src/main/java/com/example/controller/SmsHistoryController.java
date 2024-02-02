package com.example.controller;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.SmsHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.SmsHistoryService;
import com.example.service.SmsServerService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/sms_history")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    @GetMapping("/byPhone/{phone}")
    public ResponseEntity<List<SmsHistoryDTO>> getByPhone(@PathVariable("phone") String phone){
        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }
    @GetMapping("/byDate")
    public ResponseEntity<List<SmsHistoryDTO>> getByDate(@RequestParam("date") LocalDate date){
        return ResponseEntity.ok(smsHistoryService.getByDate(date));
    }
    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<SmsHistoryDTO>> pagination(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                                    @RequestParam(value = "size" , defaultValue = "1") Integer size,
                                                                    HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(smsHistoryService.pagination(page, size));
    }
}
