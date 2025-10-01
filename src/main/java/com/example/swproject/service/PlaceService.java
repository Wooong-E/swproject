package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.dto.AttractionDto;
import com.example.swproject.repository.PlaceRepository;
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

    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }

    private final List<AttractionDto> attractions = new ArrayList<>();

    @jakarta.annotation.PostConstruct
    public void initAttractionsData() {
        // 실제 장소 데이터
        attractions.add(createAttractionDto(1L, "자인 계정숲", "경북 경산시 자인면 계정길 5", "nature", "/images/attractions/attraction_1.jpg"));
        attractions.add(createAttractionDto(2L, "대구대학교 늘푸른테마 공원", "경북 경산시 진량읍 대구대로 201", "nature", "/images/attractions/attraction_2.jpg"));
        attractions.add(createAttractionDto(3L, "경산 스타필드", "경북 경산시 하양읍 대경로 1590", "experience", null));
        attractions.add(createAttractionDto(4L, "남매지", "경북 경산시 계양동", "photospot", null));
        attractions.add(createAttractionDto(5L, "삼성현역사문화공원", "경북 경산시 남산면 삼성현로 915-1", "history", null));
        attractions.add(createAttractionDto(6L, "대부잠수교", "경북 경산시 남천면 대부리", "photospot", null));

        // 임시 데이터
        for (int i = 3; i <= 6; i++) {
            attractions.add(createPlaceholderDto("자연" + i, "자연" + i + "주소", "nature"));
        }
        for (int i = 3; i <= 6; i++) {
            attractions.add(createPlaceholderDto("포토스팟" + i, "포토스팟" + i + "주소", "photospot"));
        }
        for (int i = 2; i <= 6; i++) {
            attractions.add(createPlaceholderDto("테마파크·체험" + i, "테마파크·체험" + i + "주소", "experience"));
        }
        for (int i = 2; i <= 6; i++) {
            attractions.add(createPlaceholderDto("역사문화" + i, "역사문화" + i + "주소", "history"));
        }
        for (int i = 1; i <= 6; i++) {
            attractions.add(createPlaceholderDto("야경·전망" + i, "야경·전망" + i + "주소", "nightview"));
        }
        for (int i = 1; i <= 6; i++) {
            attractions.add(createPlaceholderDto("전통시장·거리" + i, "전통시장·거리" + i + "주소", "market"));
        }
    }

    public List<AttractionDto> getAttractionsByCategory(String category) {
        if (category == null || category.isEmpty() || "all".equalsIgnoreCase(category)) {
            return attractions.stream()
                .filter(place -> "nature".equals(place.getCategory()))
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
        return place;
    }

    private AttractionDto createPlaceholderDto(String name, String location, String category) {
        AttractionDto placeholder = new AttractionDto();
        placeholder.setName(name);
        placeholder.setLocation(location);
        placeholder.setCategory(category);
        placeholder.setMain_image("https://via.placeholder.com/400x250/cccccc/ffffff?text=Image");
        return placeholder;
    }

}

