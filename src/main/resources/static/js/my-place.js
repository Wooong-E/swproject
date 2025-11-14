document.addEventListener('DOMContentLoaded', () => {
    const likesList = document.getElementById('likes-list');
    const categoryButtons = document.querySelector('.category-buttons');
    let allLikedPlaces = {};

    const fetchLikedPlaces = () => {
        fetch('/api/likes/categorized')
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버에서 찜 목록을 가져오는 데 실패했습니다.');
                }
                return response.json();
            })
            .then(data => {
                allLikedPlaces = data;
                // 초기에 '명소' 카테고리 표시
                renderLikes('attraction');
            })
            .catch(error => {
                console.error('찜 목록 로딩 오류:', error);
                likesList.innerHTML = '<p>찜한 장소를 불러오는 데 실패했습니다.</p>';
            });
    };

    const renderLikes = (category) => {
        likesList.innerHTML = ''; // 기존 목록 초기화
        const places = allLikedPlaces[category] || [];

        if (places.length === 0) {
            likesList.innerHTML = '<p>찜한 장소가 없습니다.</p>';
            return;
        }

        places.forEach(place => {
            const item = document.createElement('div');
            item.classList.add('like-item');
            item.setAttribute('data-place-id', place.id);

            const detailUrl = `/${category}s/${place.id}`;

            let imageUrl = `/images/restaurants/local/${place.id}.jpg`;
            let placeName = place.name;

            // temporary
            if (place.id === 8) {
                imageUrl = '/images/restaurants/detail/1/1.svg';
                placeName = '자인식육식당';
            } else if (place.id === 10) {
                imageUrl = '/images/restaurants/detail/2/2.svg';
                placeName = '장군제육';
            } else if (place.id === 12) {
                imageUrl = '/images/restaurants/detail/3/3.svg';
                placeName = '마당닭갈비';
            }

            // 이미지, 정보, 하트 버튼을 포함하는 HTML 구조 생성
            item.innerHTML = `
                <a href="${detailUrl}" class="like-item-link">
                    <img src="${imageUrl}" alt="${placeName}" class="like-item-image">
                    <div class="like-item-info">
                        <h3 class="like-item-title">${placeName}</h3>
                        <p class="like-item-address">${place.address}</p>
                    </div>
                </a>
                <button class="heart-button" type="button" aria-label="찜 해제하기">
                    <img src="/images/tabler_heart_filled.svg" alt="찜한 상태">
                </button>
            `;
            likesList.appendChild(item);
        });
    };

    categoryButtons.addEventListener('click', (event) => {
        const target = event.target;
        if (target.matches('.category-button')) {
            // 모든 버튼에서 'active' 클래스 제거
            categoryButtons.querySelectorAll('.category-button').forEach(btn => {
                btn.classList.remove('active');
            });
            // 클릭된 버튼에 'active' 클래스 추가
            target.classList.add('active');

            const category = target.dataset.category;
            renderLikes(category);
        }
    });

    likesList.addEventListener('click', (event) => {
        const heartButton = event.target.closest('.heart-button');
        if (!heartButton) return;

        event.preventDefault(); // 링크 이동 방지

        const item = heartButton.closest('.like-item');
        const placeId = item.dataset.placeId;

        const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

        fetch(`/api/likes/${placeId}/toggle`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        })
        .then(response => response.json())
        .then(data => {
            if (!data.liked) {
                // UI에서 아이템 제거
                item.remove();
                // allLikedPlaces에서도 해당 아이템 제거
                Object.keys(allLikedPlaces).forEach(category => {
                    allLikedPlaces[category] = allLikedPlaces[category].filter(p => p.id != placeId);
                });
            }
        })
        .catch(error => console.error('찜 해제 처리 중 오류 발생:', error));
    });

    // 로그인 상태일 때만 찜 목록을 가져옴
    if (window.isLoggedIn) {
        fetchLikedPlaces();
    } else {
        likesList.innerHTML = '<p>로그인 후 찜한 장소를 확인해보세요.</p>';
    }
});
