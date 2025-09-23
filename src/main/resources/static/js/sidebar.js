document.addEventListener('DOMContentLoaded', () => {
  const menuButton = document.getElementById('menu-button');
  const sidebar = document.getElementById('sidebar');
  const closeButton = document.getElementById('close-button');

  console.log(menuButton, sidebar, closeButton); // 확인

  if (!menuButton || !sidebar || !closeButton) return;

  menuButton.addEventListener('click', () => sidebar.classList.add('open'));
  closeButton.addEventListener('click', () => sidebar.classList.remove('open'));

  document.addEventListener('click', (e) => {
    if (!sidebar.contains(e.target) && !menuButton.contains(e.target)) {
      sidebar.classList.remove('open');
    }
  });

  const likeButtons = document.querySelectorAll('.img-2');

  likeButtons.forEach(button => {
    button.addEventListener('click', (event) => {
      event.stopPropagation(); // Stop the click from bubbling up to the <a> tag
      const img = button.querySelector('img');
      const isLiked = img.src.includes('tabler_heart_filled.svg');

      if (isLiked) {
        img.src = '/images/tabler_heart.svg';
        button.setAttribute('aria-pressed', 'false');
        const attractionName = button.closest('.frame-15, .frame-17').querySelector('.text-wrapper-3').textContent;
        button.setAttribute('aria-label', `${attractionName} 찜하기`);
      } else {
        img.src = '/images/tabler_heart_filled.svg';
        button.setAttribute('aria-pressed', 'true');
        const attractionName = button.closest('.frame-15, .frame-17').querySelector('.text-wrapper-3').textContent;
        button.setAttribute('aria-label', `${attractionName} 찜 해제하기`);
      }
    });
  });
});
