package com.example.swproject.service;

import com.example.swproject.domain.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SuggestionService {

    private final JavaMailSender mailSender;
    private final String adminEmail;
    private final String uploadPath;

    public SuggestionService(JavaMailSender mailSender, @Value("${admin.email}") String adminEmail, @Value("${upload.path}") String uploadPath) {
        this.mailSender = mailSender;
        this.adminEmail = adminEmail;
        this.uploadPath = uploadPath;
    }

    @Transactional
    public void sendSuggestion(String placeName, String address, String placeType, List<MultipartFile> images, User user) {
        List<String> storedFileNames = new ArrayList<>();
        try {
            // 1. 이미지 파일 영구 저장
            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    if (image.isEmpty()) continue;

                    String originalFileName = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                    Path destination = Paths.get(uploadPath, storedFileName);
                    Files.createDirectories(destination.getParent());
                    image.transferTo(destination);
                    storedFileNames.add(storedFileName);
                }
            }

            // 2. 이메일 생성 및 발송
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(adminEmail);

            // 2-1. 장소 유형에 따라 이메일 제목 설정
            String subject = "[" + placeType + " 제보] " + placeName + " (from " + user.getName() + ")";
            helper.setSubject(subject);

            // 2-2. 이메일 본문 생성
            String emailContent = "<h1>장소 제보 내용</h1>"
                + "<hr>"
                + "<p><b>제보자:</b> " + user.getName() + " (" + user.getUsername() + ")</p>"
                + "<p><b>회신할 이메일:</b> " + user.getEmail() + "</p>"
                + "<hr>"
                + "<p><b>장소 이름:</b> " + placeName + "</p>"
                + "<p><b>주소:</b> " + address + "</p>"
                + "<p><b>장소 유형:</b> " + placeType + "</p>";
            helper.setText(emailContent, true);

            // 3. 저장된 이미지 파일을 이메일에 첨부
            for (String storedFileName : storedFileNames) {
                Path filePath = Paths.get(uploadPath, storedFileName);
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
