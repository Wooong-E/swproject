function swapImage(thumbnailElement) {
    // Get the main image element
    const mainImage = document.getElementById('main-review-image');

    if (!mainImage) {
        console.error('Main image element not found!');
        return;
    }

    // Get current sources
    const mainImageSrc = mainImage.src;
    const thumbnailSrc = thumbnailElement.src;

    // Swap the sources
    mainImage.src = thumbnailSrc;
    thumbnailElement.src = mainImageSrc;
}
