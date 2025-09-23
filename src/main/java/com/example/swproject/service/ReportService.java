package com.example.swproject.service;

import com.example.swproject.domain.Report;
import com.example.swproject.domain.ReportsPost;
import com.example.swproject.domain.User;
import com.example.swproject.repository.ReportRepository;
import com.example.swproject.repository.ReportsPostRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportsPostRepository reportsPostRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 프론트엔드에서 제출된 문의 데이터를 처리하고, 데이터베이스에 저장하며, 관리자에게 이메일을 발송합니다.
     * @param title 문의 제목
     * @param content 문의 내용
     * @param image 첨부 이미지 파일 (선택 사항)
     * @param user 현재 로그인한 사용자 정보
     */
    @Transactional // 이 어노테이션 추가
    public void sendReport(String title, String content, MultipartFile image, User user) {
        // 1. Report 객체 생성 및 DB 저장
        Report report = new Report();
        report.setUser(user);
        report.setTitle(title);
        report.setContent(content);
        Report savedReport = reportRepository.save(report); // DB에 저장하고 저장된 객체를 반환받음

        String storedFileName = null;
        File tempFile = null;
        try {
            // 2. 이미지 파일이 있으면 로컬에 저장하고 ReportsPost 객체 생성 및 DB 저장
            if (image != null && !image.isEmpty()) {
                String originalFileName = image.getOriginalFilename();
                storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                Path destination = Paths.get(uploadPath, storedFileName);
                Files.createDirectories(destination.getParent());
                image.transferTo(destination);
                tempFile = destination.toFile();

                // ReportsPost 객체 생성 및 DB 저장
                ReportsPost reportsPost = new ReportsPost();
                reportsPost.setReport(savedReport); // 위에서 저장된 Report 객체와 연결
                reportsPost.setPostImageUrl(storedFileName); // 파일 이름 또는 경로 저장
                reportsPostRepository.save(reportsPost);
            }

            // 3. 이메일 메시지 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(adminEmail);
            helper.setSubject("[문의] " + title + " (from " + user.getName() + ")");

            // 이메일 본문에 사용자 정보 추가
            String emailContent = "<h1>문의 내용</h1>" +
                                  "<hr>" +
                                  "<p><b>작성자:</b> " + user.getName() + " (" + user.getUsername() + ")</p>" +
                                  "<p><b>회신할 이메일:</b> " + user.getEmail() + "</p>" +
                                  "<hr>" +
                                  "<p><b>제목:</b> " + title + "</p>" +
                                  "<p><b>내용:</b> " + content + "</p>";
            helper.setText(emailContent, true);

            // 4. 첨부파일 추가
            if (tempFile != null) {
                helper.addAttachment(tempFile.getName(), tempFile);
            }

            // 5. 이메일 발송
            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("메일 발송 또는 파일 처리 중 오류가 발생했습니다.", e);
        }
    }
}
