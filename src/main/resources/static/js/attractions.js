document.addEventListener('DOMContentLoaded', () => {
    const allAttractions = [
        // Existing Attractions (with detail pages)
        { id: 1, name: '자인 계정숲', address: '경북 경산시 자인면 계정길 5', category: '자연', imageUrl: '/images/attractions/attraction_1.jpg', hasDetailPage: true },
        { id: 2, name: '대구대학교 늘푸른테마 공원', address: '경북 경산시 진량읍 대구대로 201', category: '자연', imageUrl: '/images/attractions/attraction_2.jpg', hasDetailPage: true },
        { id: 3, name: '경산 스타필드', address: '경북 경산시 하양읍 대경로 1590', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image', hasDetailPage: true },
        { id: 4, name: '남매지', address: '경북 경산시 계양동', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image', hasDetailPage: true },
        { id: 5, name: '삼성현역사문화공원', address: '경북 경산시 남산면 삼성현로 915-1', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image', hasDetailPage: true },
        { id: 6, name: '대부잠수교', address: '경북 경산시 남천면 대부리', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image', hasDetailPage: true },

        // New Dummy Attractions (no detail pages)
        { id: 101, name: '팔공산 국립공원 (갓바위)', address: '경북 경산시 와촌면 갓바위로 100', category: '자연', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 102, name: '삼성산', address: '경북 경산시 남산면 삼성산길 100', category: '자연', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 103, name: '백자산', address: '경북 경산시 남산면 백자산길 100', category: '자연', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 104, name: '환성산', address: '경북 경산시 와촌면 환성산길 100', category: '자연', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 105, name: '경산시립박물관', address: '경북 경산시 박물관로 100', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 106, name: '임당유적', address: '경북 경산시 임당동 100', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 107, name: '경산향교', address: '경북 경산시 향교길 100', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 108, name: '영남대 민속촌', address: '경북 경산시 대학로 100', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 109, name: '선본사', address: '경북 경산시 와촌면 갓바위로 100-1', category: '역사문화', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 110, name: '하양 유채꽃 단지', address: '경북 경산시 하양읍 하양로 100', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 111, name: '대구대 보리밭', address: '경북 경산시 진량읍 대구대로 100', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 112, name: '반곡지', address: '경북 경산시 남천면 반곡지길 28', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 113, name: '한실마을', address: '경북 경산시 남산면 한실길 100', category: '포토스팟', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 114, name: '중산지', address: '경북 경산시 중산동 100', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 115, name: '영대교', address: '경북 경산시 대학로 100-1', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 116, name: '반곡지', address: '경북 경산시 남천면 반곡지길 100', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 117, name: '남매지', address: '경북 경산시 계양동 100', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 118, name: '팔공산 갓바위', address: '경북 경산시 와촌면 갓바위로 81-1', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 119, name: '문천지', address: '경북 경산시 남천면 문천길 100', category: '야경·전망', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 120, name: '어린이 물놀이장 (남매근린공원)', address: '경북 경산시 계양동 100-1', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 121, name: '대추 테마파크', address: '경북 경산시 남산면 대추로 100', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 122, name: '도화지', address: '경북 경산시 와촌면 계당길 100-2', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 123, name: '경산향교', address: '경북 경산시 교동 90-2', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 124, name: '안포레', address: '경북 경산시 자인면 계정길 2', category: '테마파크·체험', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 125, name: '경산 전통시장', address: '경북 경산시 경안로 31길 19', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 126, name: '하양 꿈바우시장', address: '경북 경산시 하양읍 하양로 100-4', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 127, name: '진량시장', address: '경북 경산시 진량읍 봉회길 100-1', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 128, name: '압량시장', address: '경북 경산시 압량읍 압독로 100', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 129, name: '와촌시장', address: '경북 경산시 와촌면 와촌로 100', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' },
        { id: 130, name: '용성시장', address: '경북 경산시 용성면 용성로 100-1', category: '전통시장·거리', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Attraction+Image' }
    ];

    const attractionListContainer = document.getElementById('attraction-list');
    const subCategoryButtons = document.querySelectorAll('#attraction-sub-categories .sub-category-button');

    let currentSubCategory = '자연'; // Default for attractions

    const createPlaceCard = (place) => {
        const article = document.createElement('article');
        article.className = 'frame-15';
        article.setAttribute('data-place-id', place.id);

        const contentWrapper = document.createElement(place.hasDetailPage ? 'a' : 'div');
        contentWrapper.className = 'img-wrapper';
        if (place.hasDetailPage) {
            contentWrapper.href = `/attractions/${place.id}`; // Existing attractions use their ID directly
        }

        contentWrapper.innerHTML = `
            <img src="${place.imageUrl}" alt="${place.name}" class="place-image">
            <button class="img-2" type="button" aria-label="${place.name} 찜하기">
                <img src="/images/tabler_heart.svg" alt="" />
            </button>
        `;

        const textContent = `
            <div class="frame-16">
                <h3 class="text-wrapper-3">${place.name}</h3>
                <p class="text-wrapper-12">${place.address}</p>
            </div>
        `;

        article.appendChild(contentWrapper);
        article.innerHTML += textContent;

        return article;
    };

    const renderAttractions = () => {
        attractionListContainer.innerHTML = '';
        let filteredAttractions = allAttractions;

        if (currentSubCategory) {
            filteredAttractions = filteredAttractions.filter(attraction => attraction.category === currentSubCategory);
        }

        if (filteredAttractions.length === 0) {
            attractionListContainer.innerHTML = '<p style="text-align: center; width: 100%;">해당 카테고리의 명소가 없습니다.</p>';
            return;
        }

        for (let i = 0; i < filteredAttractions.length; i += 2) {
            const row = document.createElement('div');
            row.className = 'frame-14';

            const attraction1 = filteredAttractions[i];
            if (attraction1) {
                row.appendChild(createPlaceCard(attraction1));
            }

            const attraction2 = filteredAttractions[i + 1];
            if (attraction2) {
                row.appendChild(createPlaceCard(attraction2));
            }

            attractionListContainer.appendChild(row);
        }
    };

    // Event Listeners for Sub-Category Buttons
    subCategoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            subCategoryButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            currentSubCategory = button.dataset.subCategory;
            renderAttractions();
        });
    });

    // Initial render
    renderAttractions();
});
