package com.example.swproject.service;

import com.example.swproject.domain.Report;
import com.example.swproject.domain.ReportsPost;
import com.example.swproject.domain.User;
import com.example.swproject.repository.ReportRepository;
import com.example.swproject.repository.ReportsPostRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    private final JavaMailSender mailSender;
    private final ReportRepository reportRepository;
    private final ReportsPostRepository reportsPostRepository;
    private final String adminEmail;
    private final String uploadPath;

    public ReportService(JavaMailSender mailSender, ReportRepository reportRepository, ReportsPostRepository reportsPostRepository, @Value("${admin.email}") String adminEmail, @Value("${upload.path}") String uploadPath) {
        this.mailSender = mailSender;
        this.reportRepository = reportRepository;
        this.reportsPostRepository = reportsPostRepository;
        this.adminEmail = adminEmail;
        this.uploadPath = uploadPath;
    }

    @Transactional
    public void sendReport(String title, String content, List<MultipartFile> images, User user) {
        // 1. Report 객체 생성 및 DB 저장
        Report report = new Report();
        report.setUser(user);
        report.setTitle(title);
        report.setContent(content);
        Report savedReport = reportRepository.save(report);

        try {
            // 2. 이미지 파일 영구 저장 및 DB에 메타데이터 저장
            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    if (image.isEmpty()) continue;

                    String originalFileName = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                    Path destination = Paths.get(uploadPath, storedFileName);
                    Files.createDirectories(destination.getParent());
                    image.transferTo(destination);

                    ReportsPost reportsPost = new ReportsPost();
                    reportsPost.setReport(savedReport);
                    reportsPost.setPostImageUrl(storedFileName);
                    reportsPostRepository.save(reportsPost);
                }
            }

            // 3. DB에서 이미지 정보 불러오기
            List<ReportsPost> fetchedImagePosts = reportsPostRepository.findByReportsId(savedReport.getId());

            // 4. 파라미터와 불러온 정보를 기반으로 이메일 생성 및 발송
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(adminEmail);
            helper.setSubject("[문의] " + title + " (from " + user.getName() + ")");

            String emailContent = "<h1>문의 내용</h1>" +
                "<hr>" +
                "<p><b>작성자:</b> " + user.getName() + " (" + user.getUsername() + ")</p>" +
                "<p><b>회신할 이메일:</b> " + user.getEmail() + "</p>" +
                "<hr>" +
                "<p><b>제목:</b> " + title + "</p>" +
                "<p><b>내용:</b> " + content + "</p>";
            helper.setText(emailContent, true);

            // 5. DB에서 불러온 이미지 경로를 사용하여 파일 첨부
            for (ReportsPost imagePost : fetchedImagePosts) {
                Path filePath = Paths.get(uploadPath, imagePost.getPostImageUrl());
                File file = filePath.toFile();
                if (file.exists()) {
                    helper.addAttachment(file.getName(), file);
                }
            }

            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException("메일 발송 또는 파일 처리 중 오류가 발생했습니다.", e);
        }
    }
}
