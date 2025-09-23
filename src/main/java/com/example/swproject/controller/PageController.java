package com.example.SWFRONT;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.example.SWFRONT.MenuDto;
import com.example.SWFRONT.RestaurantDto;
import com.example.SWFRONT.CafeDto;

@Controller
public class PageController {

    @GetMapping("/attractions")
    public String showAttractionsPage() {
        return "attractions";
    }

    @GetMapping("/restaurants")
    public String showRestaurantsPage() {
        return "restaurants";
    }

    @GetMapping("/cafes")
    public String showCafesPage() {
        return "cafes";
    }

    @GetMapping("/attractions/{id}")
    public String showAttractionDetail(@PathVariable Long id, Model model) {
        // 임시 데이터 (실제로는 서비스 계층에서 DB 또는 다른 소스에서 데이터를 가져옵니다)
        List<ReviewDto> reviews = List.of(
                new ReviewDto(1L, "/images/review_placeholder_1.png", "너무 좋았어요!", 5),
                new ReviewDto(2L, "/images/review_placeholder_2.png", "경치가 아름다워요.", 4),
                new ReviewDto(3L, "/images/review_placeholder_3.png", "가족과 함께하기 좋아요.", 5),
                new ReviewDto(4L, "/images/review_placeholder_4.png", "다음에 또 올게요!", 4),
                new ReviewDto(5L, "/images/review_placeholder_5.png", "사진 찍기 좋은 곳!", 5)
        );

        AttractionDto attraction = new AttractionDto(
                id,
                "남매지",
                "남매지는 경상북도 경산시 동부동에 위치한 아름다운 저수지입니다.",
                "/images/attraction_nammaeji.jpg", // 임시 이미지 URL
                "무료",
                "무료 주차장, 도보 20분",
                false, // 초기에는 찜하지 않은 상태
                reviews
        );

        // ID에 따라 다른 데이터를 보여주기 위한 간단한 로직 (실제로는 DB에서 가져옴)
        if (id == 2L) {
            attraction = new AttractionDto(
                    id,
                    "경산 자연마당",
                    "경산 자연마당은 도심 속에서 자연을 느낄 수 있는 생태공원입니다.",
                    "/images/attraction_jayeonmadang.jpg", // 임시 이미지 URL
                    "무료",
                    "주차 가능, 버스 정류장 인접",
                    true, // 초기에는 찜한 상태
                    reviews
            );
        }
        // 다른 ID에 대한 데이터는 필요에 따라 추가

        model.addAttribute("attraction", attraction);
        return "attraction-detail";
    }

    @GetMapping("/restaurants/{id}")
    public String showRestaurantDetail(@PathVariable Long id, Model model) {
        // Dummy Menu Data
        List<MenuDto> menuList = List.of(
                new MenuDto("/images/menu_bibimbap.jpg", "비빔밥", "8,000원"),
                new MenuDto("/images/menu_bulgogi.jpg", "불고기", "12,000원"),
                new MenuDto("/images/menu_kimchijjigae.jpg", "김치찌개", "7,500원"),
                new MenuDto("/images/menu_samgyeopsal.jpg", "삼겹살", "13,000원"),
                new MenuDto("/images/menu_naengmyeon.jpg", "냉면", "7,000원")
        );

        // Dummy Review Data (reusing from AttractionDto example)
        List<ReviewDto> reviews = List.of(
                new ReviewDto(1L, "/images/review_placeholder_1.png", "음식이 정말 맛있어요!", 5),
                new ReviewDto(2L, "/images/review_placeholder_2.png", "분위기가 좋아요.", 4),
                new ReviewDto(3L, "/images/review_placeholder_3.png", "가성비 최고입니다.", 5)
        );

        // Dummy Restaurant Data
        RestaurantDto restaurant = new RestaurantDto(
                id,
                "전통 한정식",
                "정갈하고 맛있는 한정식을 맛볼 수 있는 곳입니다. 신선한 재료로 만든 다양한 반찬과 메인 요리가 일품입니다. 가족 외식이나 중요한 모임에 적합합니다.",
                "/images/restaurant_main.jpg", // Main image for the restaurant
                "경상북도 경산시 대학로 123",
                "버스 정류장 도보 5분, 주차 가능",
                false, // 초기에는 찜하지 않은 상태
                reviews,
                menuList
        );

        // Simple logic to show different data based on ID (for demonstration)
        if (id == 2L) {
            menuList = List.of(
                    new MenuDto("/images/menu_pasta.jpg", "크림 파스타", "15,000원"),
                    new MenuDto("/images/menu_pizza.jpg", "마르게리따 피자", "18,000원"),
                    new MenuDto("/images/menu_salad.jpg", "리코타 치즈 샐러드", "10,000원")
            );
            restaurant = new RestaurantDto(
                    id,
                    "이탈리안 비스트로",
                    "현대적인 분위기에서 즐기는 정통 이탈리안 요리. 신선한 해산물 파스타와 화덕 피자가 인기 메뉴입니다. 데이트 코스로 추천합니다.",
                    "/images/restaurant_italian.jpg",
                    "경상북도 경산시 중앙로 456",
                    "지하철역 도보 10분, 발렛 파킹 가능",
                    true,
                    reviews,
                    menuList
            );
        }

        model.addAttribute("restaurant", restaurant);
        return "restaurant-detail";
    }

    @GetMapping("/cafes/{id}")
    public String showCafeDetail(@PathVariable Long id, Model model) {
        // Dummy Menu Data for Cafe
        List<MenuDto> menuList = List.of(
                new MenuDto("/images/menu_americano.jpg", "아메리카노", "4,500원"),
                new MenuDto("/images/menu_latte.jpg", "카페 라떼", "5,000원"),
                new MenuDto("/images/menu_cheesecake.jpg", "치즈 케이크", "7,000원"),
                new MenuDto("/images/menu_croissant.jpg", "크루아상", "3,500원"),
                new MenuDto("/images/menu_juice.jpg", "생과일 주스", "6,000원")
        );

        // Dummy Review Data (reusing from AttractionDto example)
        List<ReviewDto> reviews = List.of(
                new ReviewDto(1L, "/images/review_placeholder_1.png", "커피 맛이 좋아요!", 5),
                new ReviewDto(2L, "/images/review_placeholder_2.png", "분위기가 아늑해요.", 4),
                new ReviewDto(3L, "/images/review_placeholder_3.png", "디저트가 맛있어요.", 5)
        );

        // Dummy Cafe Data
        CafeDto cafe = new CafeDto(
                id,
                "모던 브루",
                "현대적인 인테리어와 고품질 원두로 내린 커피를 즐길 수 있는 카페입니다. 다양한 디저트와 함께 편안한 시간을 보내세요. 스터디나 작업하기에도 좋습니다.",
                "/images/cafe_main.jpg", // Main image for the cafe
                "경상북도 경산시 중앙로 789",
                "지하철역 도보 3분, 주차 공간 협소",
                false, // 초기에는 찜하지 않은 상태
                reviews,
                menuList
        );

        // Simple logic to show different data based on ID (for demonstration)
        if (id == 2L) {
            menuList = List.of(
                    new MenuDto("/images/menu_espresso.jpg", "에스프레소", "4,000원"),
                    new MenuDto("/images/menu_muffin.jpg", "초코 머핀", "4,000원"),
                    new MenuDto("/images/menu_tea.jpg", "얼그레이 티", "5,500원")
            );
            cafe = new CafeDto(
                    id,
                    "빈티지 다방",
                    "레트로 감성이 가득한 아늑한 다방입니다. 직접 로스팅한 원두로 내린 핸드드립 커피와 전통차를 맛볼 수 있습니다. 조용히 책 읽기 좋은 곳입니다.",
                    "/images/cafe_vintage.jpg",
                    "경상북도 경산시 골목길 10",
                    "버스 정류장 인접, 주차 불가",
                    true,
                    reviews,
                    menuList
            );
        }

        model.addAttribute("cafe", cafe);
        return "cafe-detail";
    }

    @GetMapping("/report")
    public String showReportPage() {
        return "report";
    }

    @GetMapping("/suggest")
    public String showSuggestPage() {
        return "suggest";
    }
}
