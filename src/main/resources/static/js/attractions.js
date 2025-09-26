document.addEventListener('DOMContentLoaded', () => {
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
  // 찜하기 버튼 기능
  // =========================
  const heartButtons = document.querySelectorAll('.img-2');

  heartButtons.forEach(button => {
    button.addEventListener('click', (event) => {
      event.preventDefault();   // a 태그 이동 막기
      event.stopPropagation();  // 부모 a 이벤트 전파 막기

      if (!window.isLoggedIn) {
        if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
          window.location.href = '/users/login';
        }
        return; // Stop further execution if not logged in
      }

      console.log("찜하기 버튼 클릭됨!");

      // 장소 ID 가져오기 (HTML에서 data-place-id 속성으로 설정해야 함)
      const placeId = button.closest('.frame-15, .frame-17').getAttribute('data-place-id');
      
      if (!placeId) {
        console.error('장소 ID를 찾을 수 없습니다.');
        return;
      }

      // 서버에 찜하기 토글 요청
      fetch(`/api/likes/${placeId}/toggle`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('서버 응답 오류: ' + response.status);
        }
        return response.json();
      })
      .then(data => {
        // 서버 응답에 따라 UI 업데이트
        const img = button.querySelector('img');
        const attractionName = button.closest('.frame-15, .frame-17').querySelector('.text-wrapper-3').textContent;
        
        if (data.liked) {
          img.src = '/images/tabler_heart_filled.svg';
          button.setAttribute('aria-pressed', 'true');
          button.setAttribute('aria-label', `${attractionName} 찜 해제하기`);
        } else {
          img.src = '/images/tabler_heart.svg';
          button.setAttribute('aria-pressed', 'false');
          button.setAttribute('aria-label', `${attractionName} 찜하기`);
        }
        
        console.log('찜하기 상태 업데이트:', data.liked);
      })
      .catch(error => {
        console.error('찜하기 요청 실패:', error);
        alert('찜하기 기능에 오류가 발생했습니다. 다시 시도해주세요.');
      });
    });
  });
});
