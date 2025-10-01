document.addEventListener('DOMContentLoaded', () => {

    const createPlaceCard = (place) => {
        const article = document.createElement('article');
        article.className = 'frame-15'; // Use a consistent class
        if (place.place_code) {
            article.setAttribute('data-place-id', place.place_code);
        }

        const imageSrc = place.main_image ? place.main_image : 'https://via.placeholder.com/400x250/cccccc/ffffff?text=Image';

        const contentWrapper = document.createElement(place.place_code ? 'a' : 'div');
        contentWrapper.className = 'img-wrapper';
        if (place.place_code) {
            // Thymeleaf is not used here, so construct URL directly
            contentWrapper.href = `/attractions/${place.place_code}`;
        }

        contentWrapper.innerHTML = `
            <img src="${imageSrc}" alt="${place.name}" class="place-image">
            <button class="img-2" type="button" aria-label="${place.name} 찜하기">
                <img src="/images/tabler_heart.svg" alt="" />
            </button>
        `;

        const textContent = `
            <div class="frame-16">
                <h3 class="text-wrapper-3">${place.name}</h3>
                <p class="text-wrapper-12">${place.location}</p>
            </div>
        `;

        article.appendChild(contentWrapper);
        article.innerHTML += textContent; // Append the text content HTML

        return article;
    };

    const fetchAndRenderPlaces = async (category) => {
        try {
            const response = await fetch(`/api/places?category=${category}`);
            if (!response.ok) throw new Error('서버 응답 오류');
            const places = await response.json();

            const container = document.querySelector('.attractions-list');
            container.innerHTML = '';

            for (let i = 0; i < places.length; i += 2) {
                const row = document.createElement('div');
                row.className = 'frame-14';

                const place1 = places[i];
                if (place1) {
                    row.appendChild(createPlaceCard(place1));
                }

                const place2 = places[i + 1];
                if (place2) {
                    row.appendChild(createPlaceCard(place2));
                }

                container.appendChild(row);
            }
        } catch (error) {
            console.error('장소 로딩 실패:', error);
            const container = document.querySelector('.attractions-list');
            container.innerHTML = '<p style="text-align: center; width: 100%;">장소를 불러오는 데 실패했습니다.</p>';
        }
    };

    const initializeLikeStatus = () => {
        if (!window.isLoggedIn) return;

        fetch('/api/likes/mine')
            .then(response => {
                if (!response.ok) throw new Error('서버 응답 오류');
                return response.json();
            })
            .then(likedPlaceIds => {
                if (likedPlaceIds.length === 0) return;
                const likedIdSet = new Set(likedPlaceIds.map(String));

                const attractions = document.querySelectorAll('[data-place-id]');
                attractions.forEach(attraction => {
                    const placeId = attraction.getAttribute('data-place-id');
                    if (likedIdSet.has(placeId)) {
                        const button = attraction.querySelector('.img-2');
                        const img = button?.querySelector('img');
                        if (img) {
                            img.src = '/images/tabler_heart_filled.svg';
                            const attractionName = attraction.querySelector('.text-wrapper-3').textContent;
                            button.setAttribute('aria-pressed', 'true');
                            button.setAttribute('aria-label', `${attractionName} 찜 해제하기`);
                        }
                    }
                });
            })
            .catch(error => console.error('찜 상태 초기화 실패:', error));
    };

    const categoryButtons = document.querySelectorAll('.category-button');
    categoryButtons.forEach(button => {
        button.addEventListener('click', async () => {
            categoryButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            const category = button.dataset.category;
            await fetchAndRenderPlaces(category);
            initializeLikeStatus();
        });
    });

    const attractionsList = document.querySelector('.attractions-list');
    if (!attractionsList) return;

    const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

    attractionsList.addEventListener('click', (event) => {
        const button = event.target.closest('.img-2');
        if (!button) return;

        event.preventDefault();
        event.stopPropagation();

        if (!window.isLoggedIn) {
            if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
                window.location.href = '/login';
            }
            return;
        }

        const placeContainer = button.closest('[data-place-id]');
        if (!placeContainer) return;

        const placeId = placeContainer.getAttribute('data-place-id');
        if (!placeId) {
            // This case is for placeholders which don't have a placeId and shouldn't be likable.
            return;
        }

        if (!token || !header) {
            console.error('CSRF 토큰을 찾을 수 없습니다.');
            alert('오류가 발생했습니다. 페이지를 새로고침 해주세요.');
            return;
        }

        fetch(`/api/likes/${placeId}/toggle`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        })
        .then(response => {
            if (!response.ok) throw new Error('서버 응답 오류: ' + response.status);
            return response.json();
        })
        .then(data => {
            const img = button.querySelector('img');
            if (!img) return;

            const attractionName = placeContainer.querySelector('.text-wrapper-3').textContent;

            if (data.liked) {
                img.src = '/images/tabler_heart_filled.svg';
                button.setAttribute('aria-pressed', 'true');
                button.setAttribute('aria-label', `${attractionName} 찜 해제하기`);
            } else {
                img.src = '/images/tabler_heart.svg';
                button.setAttribute('aria-pressed', 'false');
                button.setAttribute('aria-label', `${attractionName} 찜하기`);
            }
        })
        .catch(error => {
            console.error('찜하기 요청 실패:', error);
            alert('찜하기 기능에 오류가 발생했습니다. 다시 시도해주세요.');
        });
    });

    const initialLoad = async () => {
        await fetchAndRenderPlaces('nature');
        initializeLikeStatus();
    };

    initialLoad();
});
