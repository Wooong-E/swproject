package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.dto.AttractionDto;
import com.example.swproject.repository.PlaceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewService reviewService;

    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }

    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    private final List<AttractionDto> attractions = new ArrayList<>();

    @PostConstruct
    public void initAttractionsData() {
        // 카테고리명을 한글로 수정
        attractions.add(createAttractionDto(1L, "자인 계정숲", "경북 경산시 자인면 계정길 5", "자연", "/images/attractions/attraction_1.jpg"));
        attractions.add(createAttractionDto(2L, "대구대학교 늘푸른테마 공원", "경북 경산시 진량읍 대구대로 201", "자연", "/images/attractions/attraction_2.jpg"));
        attractions.add(createAttractionDto(3L, "경산 스타필드", "경북 경산시 하양읍 대경로 1590", "테마파크·체험", null));
        attractions.add(createAttractionDto(4L, "남매지", "경북 경산시 계양동", "포토스팟", null));
        attractions.add(createAttractionDto(5L, "삼성현역사문화공원", "경북 경산시 남산면 삼성현로 915-1", "역사문화", null));
        attractions.add(createAttractionDto(6L, "대부잠수교", "경북 경산시 남천면 대부리", "포토스팟", null));
        // TODO: Add other real places for restaurants and cafes

        // 임시 데이터 카테고리명도 한글로 수정
        for (int i = 3; i <= 6; i++) {
            attractions.add(createPlaceholderDto("자연" + i, "자연" + i + "주소", "자연"));
        }
        for (int i = 3; i <= 6; i++) {
            attractions.add(createPlaceholderDto("포토스팟" + i, "포토스팟" + i + "주소", "포토스팟"));
        }
        for (int i = 2; i <= 6; i++) {
            attractions.add(createPlaceholderDto("테마파크·체험" + i, "테마파크·체험" + i + "주소", "테마파크·체험"));
        }
        for (int i = 2; i <= 6; i++) {
            attractions.add(createPlaceholderDto("역사문화" + i, "역사문화" + i + "주소", "역사문화"));
        }
        for (int i = 1; i <= 6; i++) {
            attractions.add(createPlaceholderDto("야경·전망" + i, "야경·전망" + i + "주소", "야경·전망"));
        }
        for (int i = 1; i <= 6; i++) {
            attractions.add(createPlaceholderDto("전통시장·거리" + i, "전통시장·거리" + i + "주소", "전통시장·거리"));
        }
    }

    public List<AttractionDto> getAttractionsByCategory(String category) {
        // 기본 필터링 로직 수정
        if (category == null || category.isEmpty() || "all".equalsIgnoreCase(category)) {
            return attractions.stream()
                .filter(place -> "자연".equals(place.getCategory()))
                .collect(Collectors.toList());
        }
        return attractions.stream()
                .filter(place -> category.equals(place.getCategory()))
                .collect(Collectors.toList());
    }

    private AttractionDto createAttractionDto(Long id, String name, String location, String category, String image) {
        AttractionDto place = new AttractionDto();
        place.setPlace_code(id);
        place.setName(name);
        place.setLocation(location);
        place.setCategory(category);
        if (image != null) {
            place.setMain_image(image);
        } else {
            place.setMain_image("https://via.placeholder.com/400x250/cccccc/ffffff?text=Image");
        }

        Double averageGrade = reviewService.getAverageGrade(id);

        return place;
    }

    private AttractionDto createPlaceholderDto(String name, String location, String category) {
        AttractionDto placeholder = new AttractionDto();
        placeholder.setName(name);
        placeholder.setLocation(location);
        placeholder.setCategory(category);
        placeholder.setMain_image("https://via.placeholder.com/400x250/cccccc/ffffff?text=Image");
        placeholder.setAverageGrade(5.0);
        return placeholder;
    }
}
