package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String showReportForm() {
        return "report"; // report.html
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> submitReport(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                               @AuthenticationPrincipal User user) {
        // 1. 비로그인 사용자 차단
        if (user == null) {
            return new ResponseEntity<>("문의를 보내려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }

        try {
            // 2. 서비스 호출
            reportService.sendReport(title, content, images, user);
            // 3. 성공 응답 반환
            return new ResponseEntity<>("문의가 성공적으로 접수되었습니다.", HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            // 4. 실패 응답 반환
            e.printStackTrace(); // 서버 로그에 에러 기록
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
