document.addEventListener('DOMContentLoaded', function () {
    const bookmarkButton = document.querySelector('.bookmark-button');
    if (bookmarkButton) {
        bookmarkButton.addEventListener('click', function () {
            const courseName = this.dataset.courseName;
            const startDate = this.dataset.startDate;
            const endDate = this.dataset.endDate;
            const placeIds = this.dataset.placeIds;
            const nth = this.dataset.nth;
            let isSaved = this.dataset.saved === 'true';

            const url = isSaved ? '/courses/delete-recommended' : '/courses/save-recommended';
            const method = 'POST';

            const formData = new FormData();
            if (isSaved) {
                formData.append('nth', nth);
            } else {
                formData.append('courseName', courseName);
                formData.append('startDate', startDate);
                formData.append('endDate', endDate);
                // Spring Boot will automatically convert comma-separated string to List<Long>
                formData.append('placeIds', placeIds);
            }

            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            fetch(url, {
                method: method,
                headers: {
                    [csrfHeader]: csrfToken
                },
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    isSaved = !isSaved;
                    this.dataset.saved = isSaved;
                    const icon = this.querySelector('.book-icon');
                    icon.src = isSaved ? '/images/book_filled.png' : '/images/book.png';
                    if (isSaved) {
                        return response.text();
                    }
                    return null;
                }
                throw new Error('Network response was not ok.');
            })
            .then(newNth => {
                if (newNth) {
                    this.dataset.nth = newNth;
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }
});