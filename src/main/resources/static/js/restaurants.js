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

      // Placeholder: replace with actual login check
      const isLoggedIn = false;

      if (!isLoggedIn) {
        if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
          window.location.href = '/login';
        }
        return; // Stop further execution if not logged in
      }

      console.log("찜하기 버튼 클릭됨!");

      // 예: 찜하기 상태 토글 (UI 변화)
      button.classList.toggle("active");

      // TODO: 여기서 서버로 요청 보내거나 상태 저장 로직 추가 가능
      // fetch('/wishlist', { method: 'POST', body: JSON.stringify({ id: 5 }) })
    });
  });
});
