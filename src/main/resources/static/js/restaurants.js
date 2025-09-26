document.addEventListener('DOMContentLoaded', () => {
  // =========================
  // 초기 찜 상태 업데이트 기능
  // =========================
  const initializeLikeStatus = () => {
    // 로그인 상태가 아니면 함수 종료 (isLoggedIn 변수 필요)
    if (!window.isLoggedIn) return;

    fetch('/api/likes/mine')
        .then(response => {
          if (!response.ok) throw new Error('서버 응답 오류');
          return response.json();
        })
        .then(likedPlaceIds => {
          if (likedPlaceIds.length === 0) return;

          const likedIdSet = new Set(likedPlaceIds.map(String)); // 비교를 위해 문자열 Set으로 변환

          const attractions = document.querySelectorAll('.frame-15, .frame-17');
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

  // =========================
  // 카테고리 버튼 기능
  // =========================
  const categoryButtons = document.querySelectorAll('.category-button');
  categoryButtons.forEach(button => {
    button.addEventListener('click', () => {
      categoryButtons.forEach(btn => btn.classList.remove('active'));
      button.classList.add('active');
    });
  });

  // =========================
  // 찜하기 버튼 기능 (이벤트 위임 방식)
  // =========================
  const attractionsList = document.querySelector('.attractions-list');
  if (!attractionsList) return;

  const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
  const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

  attractionsList.addEventListener('click', (event) => {
    const button = event.target.closest('.img-2');
    if (!button) return; // 클릭된 요소가 '찜하기' 버튼이 아니면 무시

    event.preventDefault();
    event.stopPropagation();

    if (!window.isLoggedIn) {
      if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
        window.location.href = '/users/login';
      }
      return;
    }

    const placeContainer = button.closest('.frame-15, .frame-17');
    if (!placeContainer) return;

    const placeId = placeContainer.getAttribute('data-place-id');
    if (!placeId) {
      console.error('장소 ID를 찾을 수 없습니다.');
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

  // 페이지 로드 시 초기 찜 상태 업데이트 함수 호출
  initializeLikeStatus();
});