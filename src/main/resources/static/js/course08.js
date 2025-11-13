document.addEventListener('DOMContentLoaded', function() {
    const placeListContainer = document.getElementById('place-list-container');

    placeListContainer.addEventListener('click', function(event) {
        // Check if a delete button was clicked
        if (event.target.classList.contains('delete-button')) {
            // Find the parent <li> element and remove it
            const placeItem = event.target.closest('.place-item');
            if (placeItem) {
                placeItem.remove();
            }
        }
    });

    // Optional: Add logic for the final submit button, e.g., validation
    const confirmForm = document.getElementById('course-confirm-form');
    const completeButton = document.getElementById('selection-complete-button');

    confirmForm.addEventListener('submit', function(event) {
        const courseNameInput = document.getElementById('course-name-input');
        if (courseNameInput.value.trim() === '') {
            alert('코스 이름을 입력해주세요.');
            event.preventDefault(); // Stop form submission
            return;
        }

        const remainingPlaces = document.querySelectorAll('input[name="placeIds"]');
        if (remainingPlaces.length === 0) {
            alert('코스에 포함된 장소가 없습니다. 장소를 1개 이상 포함해주세요.');
            event.preventDefault(); // Stop form submission
            window.location.href = '/'; // Redirect to main page
        }
    });
});
