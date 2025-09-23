package com.example.swproject.service;

import com.example.swproject.domain.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${upload.path}")
    private String uploadPath;

    public void sendReport(String title, String content, MultipartFile image, User user) { // User 객체를 파라미터로 추가
        File tempFile = null;
        try {
            // 1. 이미지 파일이 있으면 임시 저장
            if (image != null && !image.isEmpty()) {
                String originalFileName = image.getOriginalFilename();
                String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                Path destination = Paths.get(uploadPath, storedFileName);
                Files.createDirectories(destination.getParent());
                image.transferTo(destination);
                tempFile = destination.toFile();
            }

            // 2. 이메일 메시지 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(adminEmail);
            helper.setSubject("[문의] " + title + " (from " + user.getName() + ")"); // 제목에 사용자 이름 추가

            // 이메일 본문에 사용자 정보 추가
            String emailContent = "<h1>문의 내용</h1>" +
                                  "<hr>" +
                                  "<p><b>작성자:</b> " + user.getName() + " (" + user.getUsername() + ")</p>" +
                                  "<p><b>회신할 이메일:</b> " + user.getEmail() + "</p>" +
                                  "<hr>" +
                                  "<p><b>제목:</b> " + title + "</p>" +
                                  "<p><b>내용:</b> " + content + "</p>";
            helper.setText(emailContent, true);

            // 3. 첨부파일 추가
            if (tempFile != null) {
                helper.addAttachment(tempFile.getName(), tempFile);
            }

            // 4. 이메일 발송
            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("메일 발송 또는 파일 처리 중 오류가 발생했습니다.", e);
        } finally {
            // 5. 임시 파일 삭제
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }
}
