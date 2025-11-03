document.addEventListener('DOMContentLoaded', () => {
    const restaurantListContainer = document.getElementById('restaurant-list');
    const mainCategoryButtons = document.querySelectorAll('.main-category-button');
    const subCategoryContainers = {
        taste: document.getElementById('taste-sub-categories'),
        mood: document.getElementById('mood-sub-categories')
    };

    let allRestaurants = []; // Will be populated after fetching and merging
    let currentMainCategory = 'taste';
    let currentTasteSubCategory = '로컬';
    let currentMoodSubCategory = '';
    let likedPlaceIds = new Set();

    const createRatingHTML = (grade) => {
        if (grade === null || grade === undefined) return '';
        const gradeNum = Number(grade);
        let starsHTML = '';
        for (let i = 1; i <= 5; i++) {
            const emptyStar = '<img src="/images/star_empty.svg" alt="Empty Star"/>';
            let filledStar = '';
            if (gradeNum >= i) {
                filledStar = '<img src="/images/star_filled.svg" alt="Filled Star"/>';
            } else if (gradeNum > i - 1) {
                const percentage = (gradeNum - (i - 1)) * 100;
                const clipPercentage = 100 - percentage;
                filledStar = `<img src="/images/star_filled.svg" alt="Filled Star" style="clip-path: inset(0 ${clipPercentage}% 0 0);"/>`;
            }
            starsHTML += `<div class="star-container">${emptyStar}${filledStar}</div>`;
        }
        return `
            <div class="rating-display">
                <span class="rating-score">${gradeNum.toFixed(1)}</span>
                <div class="stars-wrapper">${starsHTML}</div>
            </div>
        `;
    }



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
            contentWrapper.href = `/restaurants/${place.id - 6}`;
        }

        const isLiked = likedPlaceIds.has(String(place.id));
        const heartIconSrc = isLiked ? '/images/tabler_heart_filled.svg' : '/images/tabler_heart.svg';

        contentWrapper.innerHTML = `
            <img src="${place.imageUrl}" alt="${place.name}" class="place-image">
            <button class="img-2 like-button" type="button" aria-label="${place.name} 찜하기">
                <img src="${heartIconSrc}" alt="" />
            </button>
        `;

        const textContent = document.createElement('div');
        textContent.className = 'frame-16';
        textContent.innerHTML = `
            <h3 class="text-wrapper-3">${place.name}</h3>
            <p class="text-wrapper-12">${place.address}</p>
            ${createRatingHTML(place.averageGrade)}
        `;

        article.appendChild(contentWrapper);
        article.appendChild(textContent);

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

    const renderRestaurants = () => {
        restaurantListContainer.innerHTML = '';
        let filteredRestaurants = allRestaurants.filter(place => place.category === '맛집'); // Ensure we only process restaurants

        if (currentMainCategory === 'taste' && currentTasteSubCategory) {
            filteredRestaurants = filteredRestaurants.filter(r => r.tasteCategory === currentTasteSubCategory);
        } else if (currentMainCategory === 'mood' && currentMoodSubCategory) {
            filteredRestaurants = filteredRestaurants.filter(r => r.moodCategory === currentMoodSubCategory);
        }

        if (filteredRestaurants.length === 0) {
            restaurantListContainer.innerHTML = '<p style="text-align: center; width: 100%;">해당 카테고리의 맛집이 없습니다.</p>';
            return;
        }

        for (let i = 0; i < filteredRestaurants.length; i += 2) {
            const row = document.createElement('div');
            row.className = 'frame-14';
            const restaurant1 = filteredRestaurants[i];
            if (restaurant1) row.appendChild(createPlaceCard(restaurant1));
            const restaurant2 = filteredRestaurants[i + 1];
            if (restaurant2) row.appendChild(createPlaceCard(restaurant2));
            restaurantListContainer.appendChild(row);
        }
    };

    const fetchAndPrepareData = () => {
        restaurantListContainer.innerHTML = '<p>불러오는 중...</p>';
        const mockData = [
            { id: 7, name: '왕이모네식당', address: '경북 경산시 진량읍 대구대로 60길 5-10 포시즌', tasteCategory: '가성비', moodCategory: '모임/회식', imageUrl: '/images/restaurants/restaurant_1.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 8, name: '자인식육식당', address: '경북 경산시 자인면 일연로 56', tasteCategory: '로컬', moodCategory: '모임/회식', imageUrl: '/images/restaurants/restaurant_2.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 9, name: '돈우리', address: '경북 경산시 청운로 37 봉명빌', tasteCategory: '가성비', moodCategory: '모임/회식', imageUrl: '/images/restaurant_fusion.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 10, name: '장군제육', address: '경북 경산시 청운 1로 6-1 라온빌', tasteCategory: '로컬', moodCategory: '모임/회식', imageUrl: '/images/restaurant_japanese.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 11, name: '듬성 경산하양 본점', address: '경북 경산시 하양읍 대학로295길 6-9', tasteCategory: '트렌디', moodCategory: '데이트', imageUrl: '/images/restaurant_chinese.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 12, name: '마당닭갈비', address: '경북 경산시 남산면 하대2길 32-17', tasteCategory: '로컬', moodCategory: '모임/회식', imageUrl: '/images/restaurant_vietnamese.jpg', hasDetailPage: true, category: '맛집', averageGrade: 0.0 },
            { id: 301, name: '남산식육식당', address: '경북 경산시 남산면 남산1로 100', tasteCategory: '로컬', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 302, name: '대보식육식당', address: '경북 경산시 경안로 100', tasteCategory: '로컬', moodCategory: '모임/회식', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 303, name: '촌순두부청국장전문', address: '경북 경산시 대학로 100', tasteCategory: '로컬', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 304, name: '꽃돼지식당 경산점', address: '경북 경산시 대학로 100-1', tasteCategory: '트렌디', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 305, name: '김숙성 경산점', address: '경북 경산시 대학로 100-2', tasteCategory: '트렌디', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 306, name: '분보남보 경산', address: '경북 경산시 대학로 100-3', tasteCategory: '트렌디', moodCategory: '모임/회식', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 307, name: '핸즈커피 하양점', address: '경북 경산시 하양읍 하양로 100-1', tasteCategory: '트렌디', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 308, name: '비비비 영대점', address: '경북 경산시 대학로 100-4', tasteCategory: '트렌디', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 309, name: '오늘김해뒷고기 영남대점', address: '경북 경산시 대학로 100-5', tasteCategory: '가성비', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 310, name: '함바 소갈비 경산점', address: '경북 경산시 대학로 100-6', tasteCategory: '가성비', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 311, name: '백자산식육식당', address: '경북 경산시 삼성현로 100', tasteCategory: '가성비', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 312, name: '자인면옥', address: '경북 경산시 자인면 자인로 100', tasteCategory: '가성비', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 313, name: '용성어탕', address: '경북 경산시 용성면 용성로 100', tasteCategory: '특산물', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 314, name: '큰큰이황태집 본점', address: '경북 경산시 하양읍 하양로 100-2', tasteCategory: '특산물', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 315, name: '옹심이칼국수 하양점', address: '경북 경산시 하양읍 하양로 100-3', tasteCategory: '특산물', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 316, name: '마당넓은집', address: '경북 경산시 남천면 남천로 100-1', tasteCategory: '특산물', moodCategory: '데이트', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 317, name: '행운식당', address: '경북 경산시 남천면 남천로 100-2', tasteCategory: '특산물', moodCategory: '비지니스', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 },
            { id: 318, name: '경산돌짜장', address: '경북 경산시 대학로 100-7', tasteCategory: '특산물', moodCategory: '혼밥', imageUrl: 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Restaurant+Image', category: '맛집', averageGrade: 5.0 }
        ];

        fetch('/api/places/summary')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(apiPlaces => {
                const apiPlacesMap = new Map(apiPlaces.map(p => [p.id, p]));
                
                allRestaurants = mockData.map(mockPlace => {
                    const apiPlace = apiPlacesMap.get(mockPlace.id);
                    return {
                        ...mockPlace,
                        averageGrade: apiPlace ? apiPlace.averageGrade : mockPlace.averageGrade
                    };
                });

                renderRestaurants();
            })
            .catch(error => {
                console.error('Error fetching restaurants:', error);
                restaurantListContainer.innerHTML = '<p style="text-align: center; width: 100%; color: red;">데이터를 불러오는 중 오류가 발생했습니다.</p>';
            });
    };

    // Event Listeners (no changes needed)
    mainCategoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            mainCategoryButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            currentMainCategory = button.dataset.mainCategory;
            if (currentMainCategory === 'taste') {
                subCategoryContainers.taste.classList.add('active');
                subCategoryContainers.mood.classList.remove('active');
                currentMoodSubCategory = '';
                if (!currentTasteSubCategory) {
                    const defaultBtn = subCategoryContainers.taste.querySelector('[data-sub-category="로컬"]');
                    if(defaultBtn) defaultBtn.classList.add('active');
                    currentTasteSubCategory = '로컬';
                }
            } else { 
                subCategoryContainers.mood.classList.add('active');
                subCategoryContainers.taste.classList.remove('active');
                currentTasteSubCategory = '';
                if (!currentMoodSubCategory) {
                    const defaultBtn = subCategoryContainers.mood.querySelector('[data-sub-category="데이트"]');
                    if(defaultBtn) defaultBtn.classList.add('active');
                    currentMoodSubCategory = '데이트';
                }
            }
            renderRestaurants();
        });
    });

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
            renderRestaurants();
        });
    });

    fetchAndPrepareData();
    // Initial render
    fetchLikedPlaces().then(() => {
        renderRestaurants();
    });
});
