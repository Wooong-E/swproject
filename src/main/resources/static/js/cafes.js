document.addEventListener('DOMContentLoaded', () => {
    const allCafes = [
        // Existing Cafes (with detail pages)
        { id: 13, name: '섬섬밀밀', address: '경북 경산시 진량읍 대구대로 346', tasteCategory: '디저트', moodCategory: '힐링', imageUrl: '/images/cafes/cafe_1.jpg', hasDetailPage: true },
        { id: 14, name: '파라글로우', address: '경북 경산시 진량읍 공단 7로3 2층', tasteCategory: '브런치', moodCategory: '친구/모임', imageUrl: '/images/cafes/cafe_2.jpg', hasDetailPage: true },
        { id: 15, name: '브라우니', address: '경북 경산시 청운로 14-3', tasteCategory: '디저트', moodCategory: '데이트', imageUrl: '/images/cafe_book.jpg', hasDetailPage: true },
        { id: 16, name: '플라플로', address: '경북 경산시 대학로 281-1 7층', tasteCategory: '디저트', moodCategory: '혼공', imageUrl: '/images/cafe_dessert.jpg', hasDetailPage: true },
        { id: 17, name: '하말', address: '경북 경산시 와촌면 대한길 13', tasteCategory: '디저트', moodCategory: '데이트', imageUrl: '/images/cafe_rooftop.jpg', hasDetailPage: true },
        { id: 18, name: '우주주택', address: '경북 경산시 하양읍 가마실길 26', tasteCategory: '테마', moodCategory: '친구/모임', imageUrl: '/images/cafe_pet.jpg', hasDetailPage: true },

        // New Dummy Cafes (no detail pages)
        { id: 201, name: '마고 포레스트', address: '경북 경산시 와촌면 계당길 100', tasteCategory: '감성', moodCategory: '힐링', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 202, name: '안포레', address: '경북 경산시 와촌면 계당길 100-10', tasteCategory: '감성', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 203, name: '서상카페', address: '경북 경산시 서상길 10-1', tasteCategory: '감성', moodCategory: '혼공', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 204, name: '카페 코잔타', address: '경북 경산시 와촌면 계당길 100-20', tasteCategory: '감성', moodCategory: '친구/모임', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 205, name: '인필름', address: '경북 경산시 하양읍 하양로 100-1', tasteCategory: '감성', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 206, name: '월든', address: '경북 경산시 남천면 남천로 100-2', tasteCategory: '감성', moodCategory: '힐링', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 207, name: '스페이스임원', address: '경북 경산시 임당동 100-1', tasteCategory: '브런치', moodCategory: '혼공', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 208, name: '더웨이투커피', address: '경북 경산시 진량읍 봉회길 100-2', tasteCategory: '브런치', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 209, name: '그린에이지', address: '경북 경산시 중방동 100-3', tasteCategory: '브런치', moodCategory: '친구/모임', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 210, name: '브리즈온', address: '경북 경산시 하양읍 하양로 100-3', tasteCategory: '브런치', moodCategory: '힐링', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 211, name: '인바이트', address: '경북 경산시 남매로 100-4', tasteCategory: '브런치', moodCategory: '혼공', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 212, name: '하양송송제빵소', address: '경북 경산시 하양읍 하양로 100-5', tasteCategory: '디저트', moodCategory: '친구/모임', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 213, name: '브리프저니', address: '경북 경산시 진량읍 봉회길 100-4', tasteCategory: '디저트', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 214, name: '커피명가 포레', address: '경북 경산시 와촌면 계당길 100-6', tasteCategory: '테마', moodCategory: '힐링', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 215, name: '테이스티가든', address: '경북 경산시 와촌면 계당길 100-7', tasteCategory: '테마', moodCategory: '친구/모임', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 216, name: '커피키친한일', address: '경북 경산시 하양읍 하양로 100-8', tasteCategory: '테마', moodCategory: '혼공', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 217, name: '쉬다가게', address: '경북 경산시 남천면 남천로 100-9', tasteCategory: '테마', moodCategory: '혼공', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' },
        { id: 218, name: '호미호시', address: '경북 경산시 남천면 남천로 100-10', tasteCategory: '테마', moodCategory: '힐링', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Cafe+Image' }
    ];

    const cafeListContainer = document.getElementById('cafe-list');
    const mainCategoryButtons = document.querySelectorAll('.main-category-button');
    const subCategoryContainers = {
        taste: document.getElementById('taste-sub-categories'),
        mood: document.getElementById('mood-sub-categories')
    };

    let currentMainCategory = 'taste';
    let currentTasteSubCategory = '감성';
    let currentMoodSubCategory = ''; // Initially empty, will be set if mood is selected
    let likedPlaceIds = new Set();

    const fetchLikedPlaces = () => {
        if (!isLoggedIn) return Promise.resolve();
        return fetch('/api/likes/mine')
            .then(response => response.json())
            .then(data => {
                likedPlaceIds = new Set(data.map(String));
            })
            .catch(error => {
                console.error('Error fetching liked places:', error);
            });
    };

    const createPlaceCard = (place) => {
        const article = document.createElement('article');
        article.className = 'frame-15';
        article.setAttribute('data-place-id', place.id);

        const contentWrapper = document.createElement(place.hasDetailPage ? 'a' : 'div');
        contentWrapper.className = 'img-wrapper';
        if (place.hasDetailPage) {
            contentWrapper.href = `/cafes/${place.id - 12}`; // Adjust ID for detail page links (13 -> 1, 14 -> 2, etc.)
        }

        const isLiked = likedPlaceIds.has(String(place.id));
        const heartIconSrc = isLiked ? '/images/tabler_heart_filled.svg' : '/images/tabler_heart.svg';

        contentWrapper.innerHTML = `
            <img src="${place.imageUrl}" alt="${place.name}" class="place-image">
            <button class="img-2 like-button" type="button" aria-label="${place.name} 찜하기">
                <img src="${heartIconSrc}" alt="" />
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

        const likeButton = article.querySelector('.like-button');
        likeButton.addEventListener('click', (event) => {
            event.preventDefault();
            event.stopPropagation();

            if (!isLoggedIn) {
                window.location.href = '/users/login';
                return;
            }

            const placeId = place.id;
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            fetch(`/api/likes/${placeId}/toggle`, {
                method: 'POST',
                headers: {
                    [csrfHeader]: csrfToken
                }
            })
            .then(response => response.json())
            .then(data => {
                const icon = likeButton.querySelector('img');
                if (data.liked) {
                    icon.src = '/images/tabler_heart_filled.svg';
                    likedPlaceIds.add(String(placeId));
                } else {
                    icon.src = '/images/tabler_heart.svg';
                    likedPlaceIds.delete(String(placeId));
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });

        return article;
    };

    const renderCafes = () => {
        cafeListContainer.innerHTML = '';
        let filteredCafes = allCafes;

        if (currentMainCategory === 'taste' && currentTasteSubCategory) {
            filteredCafes = filteredCafes.filter(cafe => cafe.tasteCategory === currentTasteSubCategory);
        } else if (currentMainCategory === 'mood' && currentMoodSubCategory) {
            filteredCafes = filteredCafes.filter(cafe => cafe.moodCategory === currentMoodSubCategory);
        }

        if (filteredCafes.length === 0) {
            cafeListContainer.innerHTML = '<p style="text-align: center; width: 100%;">해당 카테고리의 카페가 없습니다.</p>';
            return;
        }

        for (let i = 0; i < filteredCafes.length; i += 2) {
            const row = document.createElement('div');
            row.className = 'frame-14';

            const cafe1 = filteredCafes[i];
            if (cafe1) {
                row.appendChild(createPlaceCard(cafe1));
            }

            const cafe2 = filteredCafes[i + 1];
            if (cafe2) {
                row.appendChild(createPlaceCard(cafe2));
            }

            cafeListContainer.appendChild(row);
        }
    };

    // Event Listeners for Main Category Buttons
    mainCategoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            mainCategoryButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            currentMainCategory = button.dataset.mainCategory;

            // Show/hide sub-category groups
            if (currentMainCategory === 'taste') {
                subCategoryContainers.taste.classList.add('active');
                subCategoryContainers.mood.classList.remove('active');
                // Reset mood sub-category selection
                subCategoryContainers.mood.querySelectorAll('.sub-category-button').forEach(btn => btn.classList.remove('active'));
                currentMoodSubCategory = '';
                // Ensure a taste sub-category is active
                if (!currentTasteSubCategory) {
                    subCategoryContainers.taste.querySelector('[data-sub-category="감성"]').classList.add('active');
                    currentTasteSubCategory = '감성';
                }
            } else { // mood
                subCategoryContainers.mood.classList.add('active');
                subCategoryContainers.taste.classList.remove('active');
                // Reset taste sub-category selection
                subCategoryContainers.taste.querySelectorAll('.sub-category-button').forEach(btn => btn.classList.remove('active'));
                currentTasteSubCategory = '';
                // Ensure a mood sub-category is active (default to '데이트' for example)
                if (!currentMoodSubCategory) {
                    subCategoryContainers.mood.querySelector('[data-sub-category="데이트"]').classList.add('active');
                    currentMoodSubCategory = '데이트';
                }
            }
            renderCafes();
        });
    });

    // Event Listeners for Sub-Category Buttons
    Object.values(subCategoryContainers).forEach(container => {
        container.addEventListener('click', (event) => {
            const clickedButton = event.target.closest('.sub-category-button');
            if (!clickedButton) return;

            container.querySelectorAll('.sub-category-button').forEach(btn => btn.classList.remove('active'));
            clickedButton.classList.add('active');

            if (container.id === 'taste-sub-categories') {
                currentTasteSubCategory = clickedButton.dataset.subCategory;
            } else {
                currentMoodSubCategory = clickedButton.dataset.subCategory;
            }
            renderCafes();
        });
    });

    // Initial render
    fetchLikedPlaces().then(() => {
        renderCafes();
    });
});