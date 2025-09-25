package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.ReportService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String submitReport(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes redirectAttributes) {
        try {
            // 서비스 호출 시 User 객체와 이미지 목록을 함께 전달
            reportService.sendReport(title, content, images, user);
            redirectAttributes.addFlashAttribute("message", "문의가 성공적으로 접수되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "문의 접수 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/"; // 성공/실패 여부와 관계없이 홈으로 리다이렉트
    }
}
