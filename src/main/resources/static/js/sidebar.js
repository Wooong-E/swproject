document.addEventListener('DOMContentLoaded', () => {
  const menuButton = document.getElementById('menu-button');
  const sidebar = document.getElementById('sidebar');
  const closeButton = document.getElementById('close-button');
  const loginRegisterButton = document.querySelector('.login-button');
  const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
  const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

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

  /*// Links that require login in the sidebar
  const protectedLinks = document.querySelectorAll(
    '#sidebar .frame a[href="#"]',
  );*/

  protectedLinks.forEach(link => {
    link.addEventListener('click', (event) => {
      event.preventDefault(); // Prevent default navigation

      if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
        window.location.href = '/users/login';
      }
    });
  });

  // Handle the main course planning link in the top nav
  const coursePlanningLink = document.getElementById('course-planning-link');
  if (coursePlanningLink) {
      coursePlanningLink.addEventListener('click', (event) => {
          // The global isLoggedIn variable is set in the HTML template
          if (!window.isLoggedIn) {
              event.preventDefault(); // Stop navigation
              if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
                  window.location.href = '/users/login';
              }
          }
      });
  }

});

