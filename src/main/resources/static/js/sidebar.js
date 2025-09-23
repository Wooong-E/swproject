document.addEventListener('DOMContentLoaded', () => {
  const menuButton = document.getElementById('menu-button');
  const sidebar = document.getElementById('sidebar');
  const closeButton = document.getElementById('close-button');
  const loginRegisterButton = document.querySelector('.login-button');

  console.log(menuButton, sidebar, closeButton, loginRegisterButton); // 확인

  if (!menuButton || !sidebar || !closeButton) return;

  menuButton.addEventListener('click', () => sidebar.classList.add('open'));
  closeButton.addEventListener('click', () => sidebar.classList.remove('open'));

  document.addEventListener('click', (e) => {
    if (!sidebar.contains(e.target) && !menuButton.contains(e.target)) {
      sidebar.classList.remove('open');
    }
  });

  // Handle login/register button click
  if (loginRegisterButton) {
    loginRegisterButton.addEventListener('click', () => {
      // For now, redirect to login page. In a real app, this might open a modal or offer both login/signup.
      window.location.href = '/users/login';
    });
  }

  // Links that require login
  const protectedLinks = document.querySelectorAll(
    '#sidebar .frame a[href="#"]', 
  );

  protectedLinks.forEach(link => {
    link.addEventListener('click', (event) => {
      event.preventDefault(); // Prevent default navigation

      if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
        window.location.href = '/users/login';
      }
    });
  });

  const likeButtons = document.querySelectorAll('.img-2');

  likeButtons.forEach(button => {
    button.addEventListener('click', (event) => {
      event.stopPropagation(); // Stop the click from bubbling up to the <a> tag

      // Check if user is logged in (for now, always prompt)
      // In a real application, you would check a session variable or a token
      const isLoggedIn = false; // Placeholder: replace with actual login check

      if (!isLoggedIn) {
        if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
          window.location.href = '/users/login';
        }
        return; // Stop further execution if not logged in
      }

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
