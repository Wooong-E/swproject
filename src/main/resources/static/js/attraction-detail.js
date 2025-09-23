function toggleHeart() {
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
