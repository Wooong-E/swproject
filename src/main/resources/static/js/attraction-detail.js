function toggleHeart() {
    // Placeholder: replace with actual login check
    const isLoggedIn = false;

    if (!isLoggedIn) {
        if (confirm("로그인 시 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")) {
            window.location.href = '/login';
        }
        return; // Stop further execution if not logged in
    }

    const heartIcon = document.getElementById('heartIcon');
    if (heartIcon) {
        const currentSrc = heartIcon.src;
        if (currentSrc.includes('tabler_heart_filled.svg')) {
            heartIcon.src = '/images/tabler_heart.svg';
        } else {
            heartIcon.src = '/images/tabler_heart_filled.svg';
        }
        // In a real application, you would also send an AJAX request here
        // to update the like status on the server.
    }
}
