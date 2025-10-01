document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('course06-form');
    const placeCards = document.querySelectorAll('.place-card');
    const selectedRecommendedPlacesContainer = document.getElementById('selected-recommended-places');
    const nextButton = document.getElementById('selection-complete-button');

    // Get initial selections passed from course05
    const initialSelections = document.querySelectorAll('input[name="placeIds"]');

    // Set to store NEW selected place IDs from this page
    const newSelectedPlaceIds = new Set();

    function updateButtonState() {
        // Enable the button if there are initial selections OR new selections
        if (initialSelections.length > 0 || newSelectedPlaceIds.size > 0) {
            nextButton.disabled = false;
        } else {
            nextButton.disabled = true;
        }
    }

    placeCards.forEach(card => {
        card.addEventListener('click', function () {
            const placeId = this.dataset.placeId;

            // Toggle 'selected' class for visual feedback
            this.classList.toggle('selected');

            if (newSelectedPlaceIds.has(placeId)) {
                // If already selected, remove it from the new selections
                newSelectedPlaceIds.delete(placeId);
            } else {
                // If not selected, add it to the new selections
                newSelectedPlaceIds.add(placeId);
            }

            // Update button state after selection changes
            updateButtonState();
        });
    });

    form.addEventListener('submit', function (event) {
        // Prevent default form submission
        event.preventDefault();

        // Clear previous hidden inputs for recommended places
        selectedRecommendedPlacesContainer.innerHTML = '';

        // Create a new hidden input for each newly selected recommended place ID
        newSelectedPlaceIds.forEach(placeId => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'placeIds'; // This will be added to the existing 'placeIds' list
            input.value = placeId;
            selectedRecommendedPlacesContainer.appendChild(input);
        });

        // Now, submit the form
        form.submit();
    });

    // Initial button state check on page load
    updateButtonState();
});