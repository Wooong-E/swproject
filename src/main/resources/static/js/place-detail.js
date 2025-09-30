document.addEventListener('DOMContentLoaded', () => {
    // =========================
    // 요소 가져오기
    // =========================
    const mainElement = document.querySelector('.attraction-detail-main');
    const likeButton = document.getElementById('likeButton');
    const heartIcon = document.getElementById('heartIcon');

    if (!mainElement || !likeButton || !heartIcon) {
        console.error('필수 요소를 찾을 수 없습니다.');
        return;
    }

    const placeId = mainElement.getAttribute('data-place-id');
    if (!placeId) {
        console.error('장소 ID를 찾을 수 없습니다.');
        return;
    }

    // =========================
    // 초기 찜 상태 업데이트 기능
    // =========================
    const initializeLikeStatus = () => {
        if (!window.isLoggedIn) return;

        fetch('/api/likes/mine')
            .then(response => {
                if (!response.ok) throw new Error('서버 응답 오류');
                return response.json();
            })
            .then(likedPlaceIds => {
                const likedIdSet = new Set(likedPlaceIds.map(String));
                const attractionTitle = document.querySelector('.attraction-title').textContent;

                if (likedIdSet.has(placeId)) {
                    heartIcon.src = '/images/tabler_heart_filled.svg';
                    likeButton.setAttribute('aria-pressed', 'true');
                    likeButton.setAttribute('aria-label', `${attractionTitle} 찜 해제하기`);
                } else {
                    heartIcon.src = '/images/tabler_heart.svg';
                    likeButton.setAttribute('aria-pressed', 'false');
                    likeButton.setAttribute('aria-label', `${attractionTitle} 찜하기`);
                }
            })
            .catch(error => console.error('찜 상태 초기화 실패:', error));
    };

    // =========================
    // 찜하기 버튼 클릭 이벤트
    // =========================
    likeButton.addEventListener('click', () => {
        if (!window.isLoggedIn) {
            if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
                window.location.href = '/users/login';
            }
            return;
        }

        const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
        const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

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
                const attractionTitle = document.querySelector('.attraction-title').textContent;
                if (data.liked) {
                    heartIcon.src = '/images/tabler_heart_filled.svg';
                    likeButton.setAttribute('aria-pressed', 'true');
                    likeButton.setAttribute('aria-label', `${attractionTitle} 찜 해제하기`);
                } else {
                    heartIcon.src = '/images/tabler_heart.svg';
                    likeButton.setAttribute('aria-pressed', 'false');
                    likeButton.setAttribute('aria-label', `${attractionTitle} 찜하기`);
                }
            })
            .catch(error => {
                console.error('찜하기 요청 실패:', error);
                alert('찜하기 기능에 오류가 발생했습니다. 다시 시도해주세요.');
            });
    });

    // 페이지 로드 시 초기 찜 상태 업데이트 함수 호출
    initializeLikeStatus();

    // =========================
    // 리뷰 작성 버튼 클릭 이벤트 (로그인 체크)
    // =========================
    const writeReviewLink = document.getElementById('write-review-link');
    if (writeReviewLink) {
        writeReviewLink.addEventListener('click', (event) => {
            if (!window.isLoggedIn) {
                event.preventDefault(); // Prevent default navigation
                if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
                    window.location.href = '/users/login';
                }
                return;
            }
        });
    }
});