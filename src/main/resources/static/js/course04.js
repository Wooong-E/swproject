document.addEventListener('DOMContentLoaded', () => {
    // --- DOM Elements ---
    const startDateInput = document.getElementById('start-date-input');
    const endDateInput = document.getElementById('end-date-input');
    const startDateButton = document.getElementById('start-date-button');
    const endDateButton = document.getElementById('end-date-button');
    const mainCompleteButton = document.getElementById('main-selection-complete-button');
    const startDateHiddenInput = document.getElementById('startDate-hidden-input');
    const endDateHiddenInput = document.getElementById('endDate-hidden-input');
    const course04Form = document.getElementById('course04-form');

    const popup = document.getElementById('calendar-popup');
    const popupHeader = document.getElementById('popup-header');
    const calendarContainer = document.getElementById('calendar-container');
    const popupConfirmButton = document.getElementById('popup-confirm-button');

    let fpInstance = null; // To hold the current flatpickr instance
    let activeInput = null; // To hold the input element (start/end) that the popup is for

    // --- Helper Functions ---

    // Checks if both dates are selected to enable the main button
    function checkMainButtonState() {
        if (startDateInput.value && endDateInput.value) {
            mainCompleteButton.disabled = false;
        } else {
            mainCompleteButton.disabled = true;
        }
    }

    // Closes the popup
    function closePopup() {
        popup.classList.remove('open');
    }

    // Opens the popup and initializes the calendar
    function openPopup(targetInput, title) {
        activeInput = targetInput;
        popupHeader.textContent = title;
        popupConfirmButton.disabled = true; // Disable on open

        // Destroy previous instance if it exists
        if (fpInstance) {
            fpInstance.destroy();
        }

        const config = {
            inline: true, // Render calendar directly in our container
            dateFormat: "Y-m-d",
            defaultDate: activeInput.value || 'today',
            onChange: function(selectedDates, dateStr, instance) {
                // Enable confirm button only when a date is selected
                popupConfirmButton.disabled = false;
            }
        };

        // Add constraint for end date picker
        if (activeInput.id === 'end-date-input' && startDateInput.value) {
            config.minDate = startDateInput.value;
        }

        fpInstance = flatpickr(calendarContainer, config);
        popup.classList.add('open');
    }

    // --- Event Listeners ---

    // Open popup for "가는 날"
    startDateButton.addEventListener('click', () => {
        openPopup(startDateInput, '가는 날');
    });

    // Open popup for "오는 날"
    endDateButton.addEventListener('click', () => {
        openPopup(endDateInput, '오는 날');
    });

    // Confirm date selection in popup
    popupConfirmButton.addEventListener('click', () => {
        if (fpInstance && activeInput) {
            const selectedDate = fpInstance.selectedDates[0];
            if (selectedDate) {
                const formattedDate = fpInstance.formatDate(selectedDate, "Y-m-d");
                activeInput.value = formattedDate;
                
                // 숨겨진 필드에 날짜 저장
                if (activeInput.id === 'start-date-input') {
                    startDateHiddenInput.value = formattedDate;
                } else if (activeInput.id === 'end-date-input') {
                    endDateHiddenInput.value = formattedDate;
                }
                
                // If start date is changed, clear end date if it's now invalid
                if (activeInput.id === 'start-date-input' && endDateInput.value) {
                    const startDate = new Date(startDateInput.value);
                    const endDate = new Date(endDateInput.value);
                    if (endDate < startDate) {
                        endDateInput.value = '';
                        endDateHiddenInput.value = '';
                    }
                }
            }
            closePopup();
            checkMainButtonState();
        }
    });

    // Main button navigation (폼 제출로 변경)
    mainCompleteButton.addEventListener('click', () => {
        if (!mainCompleteButton.disabled) {
            course04Form.submit(); // 폼 제출
        }
    });

    // Set default start date (제거)
    // startDateInput.value = new Date().toISOString().split('T')[0];
});
