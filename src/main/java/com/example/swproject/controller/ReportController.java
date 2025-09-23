package com.example.swproject.controller;

import com.example.swproject.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "report"; // html
    }

    @PostMapping
    public String submitReport(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               Model model) {
        try {
            reportService.sendReport(title, content, image);
            model.addAttribute("message", "문의가 성공적으로 접수되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "문의 접수 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "report-result"; // templates/report-result.html
    }
}
