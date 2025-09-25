document.addEventListener('DOMContentLoaded', () => {
  // =========================
  // CSRF 토큰 설정
  // =========================
  const csrfToken = document.querySelector('meta[name="_csrf"]').content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

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

      const placeId = button.closest('.img-wrapper').dataset.placeId;
      if (!placeId) {
        console.error('Place ID not found');
        return;
      }

      fetch(`/api/likes/${placeId}/toggle`, {
        method: 'POST',
        headers: {
          [csrfHeader]: csrfToken
        },
      })
      .then(response => {
        if (response.status === 401) { // 로그인이 필요한 경우
          if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
            window.location.href = '/users/login';
          }
          return Promise.reject('로그인 필요');
        }
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        const heartImage = button.querySelector('img');
        if (data.liked) {
          heartImage.src = '/images/tabler_heart_filled.svg';
        } else {
          heartImage.src = '/images/tabler_heart.svg';
        }
      })
      .catch(error => {
        if (error !== '로그인 필요') {
          console.error('Error:', error);
          alert('오류가 발생했습니다. 다시 시도해주세요.');
        }
      });
    });
  });
});