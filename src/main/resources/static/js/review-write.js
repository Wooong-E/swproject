document.addEventListener('DOMContentLoaded', () => {
    // --- Element Selections ---
    const form = document.getElementById('review-form');
    const titleInput = document.getElementById('review-title');
    const gradeInput = document.getElementById('review-grade');
    const starContainer = document.getElementById('star-rating-container');
    const stars = starContainer.querySelectorAll('.star-image');
    const photoContainer = document.getElementById('photo-attachment-container');
    const photoUploadButton = document.getElementById('photo-upload-button');
    const imageUploadInput = document.getElementById('image-upload-input');
    const contentInput = document.getElementById('review-content');
    const fhashGroup = document.getElementById('fhash-group');
    const shashGroup = document.getElementById('shash-group');
    const fhashInput = document.getElementById('fhash-input');
    const shashInput = document.getElementById('shash-input');
    const submitButton = document.getElementById('submit-review-button');

    let uploadedFiles = [];

    // --- Validation Checker ---
    const checkSubmitButtonState = () => {
        const isTitleValid = titleInput.value.length >= 5;
        const isGradeValid = parseInt(gradeInput.value, 10) > 0;
        const isPhotoValid = uploadedFiles.length >= 1;
        const isContentValid = contentInput.value.length >= 5;
        const isFhashValid = fhashInput.value !== '';
        const isShashValid = shashInput.value !== '';

        if (isTitleValid && isGradeValid && isPhotoValid && isContentValid && isFhashValid && isShashValid) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    };

    // --- Event Listeners for Validation ---
    titleInput.addEventListener('input', checkSubmitButtonState);
    contentInput.addEventListener('input', checkSubmitButtonState);

    // --- Star Rating Logic ---
    starContainer.addEventListener('click', (e) => {
        if (e.target.classList.contains('star-image')) {
            const rating = e.target.dataset.value;
            gradeInput.value = rating;
            stars.forEach((star, index) => {
                if (index < rating) {
                    star.src = '/images/star_filled.svg';
                } else {
                    star.src = '/images/star_empty.svg';
                }
            });
            checkSubmitButtonState();
        }
    });

    // --- Photo Upload Logic ---
    photoUploadButton.addEventListener('click', () => imageUploadInput.click());

    imageUploadInput.addEventListener('change', (event) => {
        const files = Array.from(event.target.files);
        if (uploadedFiles.length + files.length > 5) {
            alert('사진은 최대 5장까지 첨부할 수 있습니다.');
            return;
        }

        files.forEach(file => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const previewWrapper = document.createElement('div');
                previewWrapper.classList.add('photo-preview');
                
                const img = document.createElement('img');
                img.src = e.target.result;
                
                const removeBtn = document.createElement('button');
                removeBtn.type = 'button';
                removeBtn.classList.add('remove-photo-button');
                removeBtn.textContent = 'X';
                removeBtn.onclick = () => {
                    uploadedFiles = uploadedFiles.filter(f => f !== file);
                    previewWrapper.remove();
                    checkSubmitButtonState();
                };

                previewWrapper.appendChild(img);
                previewWrapper.appendChild(removeBtn);
                photoContainer.insertBefore(previewWrapper, photoUploadButton);
            };
            reader.readAsDataURL(file);
            uploadedFiles.push(file);
        });
        checkSubmitButtonState();
        // Clear the input value to allow selecting the same file again
        event.target.value = '';
    });

    // --- Hashtag Logic ---
    function handleHashtagSelection(groupElement, hiddenInput) {
        groupElement.addEventListener('click', (e) => {
            if (e.target.classList.contains('hashtag-button')) {
                const selectedBtn = e.target;
                // Deselect previous button in the same group
                const currentlySelected = groupElement.querySelector('.selected');
                if (currentlySelected) {
                    currentlySelected.classList.remove('selected');
                }
                // Select the new button
                selectedBtn.classList.add('selected');
                hiddenInput.value = selectedBtn.dataset.value;
                checkSubmitButtonState();
            }
        });
    }
    handleHashtagSelection(fhashGroup, fhashInput);
    handleHashtagSelection(shashGroup, shashInput);

    // --- Form Submission ---
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        if (submitButton.disabled) return;

        const formData = new FormData(form);
        // Since file inputs are not part of form data by default when handled like this,
        // we append them manually.
        uploadedFiles.forEach((file, index) => {
            formData.append('images', file);
        });

        console.log('--- Form Data to be Sent ---');
        for (let [key, value] of formData.entries()) {
            console.log(`${key}:`, value);
        }

        alert('리뷰가 등록되었습니다.');
        window.location.href = '/';
    });
});
