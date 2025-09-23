package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String showReportForm() {
        // TODO: 프론트엔드 친구에게: 이 "report"는 src/main/resources/templates/report.html 파일을 의미합니다.
        // TODO: HTML 파일 이름이 다를 경우, 이 부분을 수정하거나 HTML 파일 이름을 맞춰주세요.
        return "report"; // html
    }

    @PostMapping
    public String submitReport(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               @AuthenticationPrincipal User user, // 현재 로그인한 사용자의 User 객체를 직접 주입받음
                               Model model) {
        try {
            // 서비스 호출 시 User 객체를 함께 전달
            reportService.sendReport(title, content, image, user);
            model.addAttribute("message", "문의가 성공적으로 접수되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "문의 접수 중 오류가 발생했습니다: " + e.getMessage());
        }
        // TODO: 프론트엔드 친구에게: 이 "report-result"는 src/main/resources/templates/report-result.html 파일을 의미합니다.
        // TODO: HTML 파일 이름이 다를 경우, 이 부분을 수정하거나 HTML 파일 이름을 맞춰주세요.
        return "report-result"; // templates/report-result.html
    }
}
