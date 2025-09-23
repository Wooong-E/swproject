document.addEventListener('DOMContentLoaded', () => {
    const suggestForm = document.getElementById('suggest-form');
    if (!suggestForm) return;

    // --- Section Elements ---
    const categorySection = document.getElementById('category-section');
    const atmosphereSection = document.getElementById('atmosphere-section');
    const featuresSection = document.getElementById('features-section');

    // --- Button Group Elements ---
    const placeTypeButtons = document.getElementById('place-type-buttons');
    const attractionCategories = document.getElementById('attraction-categories');
    const restaurantCategories = document.getElementById('restaurant-categories');
    const cafeCategories = document.getElementById('cafe-categories');

    const atmosphereButtons = atmosphereSection.querySelectorAll('.choice-button');
    const featureButtons = featuresSection.querySelectorAll('.choice-button');

    // --- Main Logic: Place Type Selection ---
    placeTypeButtons.addEventListener('click', (e) => {
        if (!e.target.matches('.choice-button')) return;

        const selectedType = e.target.dataset.type;

        // 1. Handle active state for place type buttons
        handleSingleSelection(placeTypeButtons, e.target);

        // 2. Show main conditional sections
        categorySection.classList.remove('hidden');
        atmosphereSection.classList.remove('hidden');
        featuresSection.classList.remove('hidden');

        // 3. Show the correct category group
        attractionCategories.classList.toggle('hidden', selectedType !== 'attraction');
        restaurantCategories.classList.toggle('hidden', selectedType !== 'restaurant');
        cafeCategories.classList.toggle('hidden', selectedType !== 'cafe');

        // 4. Enable/disable atmosphere and feature buttons
        const isRestaurantOrCafe = (selectedType === 'restaurant' || selectedType === 'cafe');
        atmosphereButtons.forEach(btn => btn.disabled = !isRestaurantOrCafe);
        featureButtons.forEach(btn => btn.disabled = !isRestaurantOrCafe);
        
        // 5. Reset selections in conditional fields if type changes
        clearSelections(atmosphereButtons);
        clearSelections(featureButtons);
    });

    // --- Logic for single selection within other groups ---
    [attractionCategories, restaurantCategories, cafeCategories, atmosphereSection, featuresSection].forEach(group => {
        group.addEventListener('click', (e) => {
            if (e.target.matches('.choice-button:not(:disabled)')) {
                handleSingleSelection(group, e.target);
            }
        });
    });

    // --- Helper Functions ---
    function handleSingleSelection(group, selectedButton) {
        const buttons = group.querySelectorAll('.choice-button');
        buttons.forEach(btn => btn.classList.remove('active'));
        selectedButton.classList.add('active');
    }

    function clearSelections(buttonNodeList) {
        buttonNodeList.forEach(btn => btn.classList.remove('active'));
    }

    // --- Image Upload and Preview (Copied from report.js) ---
    const imageUpload = document.getElementById('image-upload');
    const previewContainer = document.getElementById('image-preview-container');
    const MAX_IMAGES = 3;
    let uploadedFiles = [];

    imageUpload.addEventListener('change', (event) => {
        const files = Array.from(event.target.files);
        
        if (uploadedFiles.length + files.length > MAX_IMAGES) {
            alert(`이미지는 최대 ${MAX_IMAGES}장까지 첨부할 수 있습니다.`);
            return;
        }

        files.forEach(file => {
            if (!file.type.startsWith('image/')) return;
            
            const reader = new FileReader();
            reader.onload = (e) => {
                const fileId = Date.now() + '-' + Math.random();
                const imgSrc = e.target.result;
                
                const previewWrapper = document.createElement('div');
                previewWrapper.classList.add('preview-image-wrapper');
                previewWrapper.dataset.fileId = fileId;

                const img = document.createElement('img');
                img.src = imgSrc;
                img.classList.add('preview-image');

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('delete-image-button');
                deleteButton.textContent = 'X';
                deleteButton.type = 'button';
                deleteButton.addEventListener('click', () => removeImage(fileId));

                previewWrapper.appendChild(img);
                previewWrapper.appendChild(deleteButton);
                previewContainer.appendChild(previewWrapper);

                uploadedFiles.push({ id: fileId, file: file });
            };
            reader.readAsDataURL(file);
        });

        event.target.value = '';
    });

    function removeImage(fileId) {
        uploadedFiles = uploadedFiles.filter(f => f.id != fileId);
        const wrapperToRemove = previewContainer.querySelector(`[data-file-id='${fileId}']`);
        if (wrapperToRemove) {
            previewContainer.removeChild(wrapperToRemove);
        }
    }

    // --- Form Submission ---
    suggestForm.addEventListener('submit', (event) => {
        event.preventDefault();
        alert('"장소 제보하기" 버튼이 클릭되었습니다. 실제 서버 전송 로직은 여기에 구현해야 합니다.');
        // Logic to gather all selected data and submit via fetch would go here
    });
});
