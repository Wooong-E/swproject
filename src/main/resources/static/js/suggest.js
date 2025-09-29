document.addEventListener('DOMContentLoaded', () => {
    const suggestForm = document.getElementById('suggest-form');
    if (!suggestForm) return;

    // --- Input & Section Elements ---
    const placeTypeHiddenInput = document.getElementById('placeType');
    const placeNameInput = document.getElementById('name');
    const addressInput = document.getElementById('address-input');
    
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

    // --- Helper: Button Group Click Handler ---
    function handleSingleSelection(group, selectedButton) {
        const buttons = group.querySelectorAll('.choice-button');
        buttons.forEach(btn => btn.classList.remove('active'));
        selectedButton.classList.add('active');
    }

    // --- 장소 유형(대분류) 선택 로직 ---
    placeTypeButtons.addEventListener('click', (e) => {
        if (!e.target.matches('.choice-button')) return;

        const selectedType = e.target.dataset.type; // 'attraction', 'restaurant', 'cafe'

        // 대분류 버튼 UI 업데이트
        handleSingleSelection(placeTypeButtons, e.target);

        // 숨겨진 필드에 대분류 값 저장
        if (placeTypeHiddenInput) {
            placeTypeHiddenInput.value = e.target.textContent.trim();
        }

        // 모든 세부 섹션 표시
        [categorySection, atmosphereSection, featuresSection].forEach(sec => sec.classList.remove('hidden'));

        // 대분류에 맞는 소분류 카테고리 그룹 표시
        attractionCategories.classList.toggle('hidden', selectedType !== 'attraction');
        restaurantCategories.classList.toggle('hidden', selectedType !== 'restaurant');
        cafeCategories.classList.toggle('hidden', selectedType !== 'cafe');

        // 식당/카페 전용 버튼 활성화/비활성화
        const isRestaurantOrCafe = (selectedType === 'restaurant' || selectedType === 'cafe');
        atmosphereButtons.forEach(btn => btn.disabled = !isRestaurantOrCafe);
        featureButtons.forEach(btn => btn.disabled = !isRestaurantOrCafe);
    });

    // --- 세부 항목(소분류) 선택 로직 ---
    [attractionCategories, restaurantCategories, cafeCategories, atmosphereSection, featuresSection].forEach(group => {
        group.addEventListener('click', (e) => {
            if (e.target.matches('.choice-button:not(:disabled)')) {
                handleSingleSelection(group, e.target);
            }
        });
    });

    // --- 카카오 주소 검색 API 연동 ---
    const searchAddressButton = document.getElementById('search-address-button');
    if (searchAddressButton) {
        searchAddressButton.addEventListener('click', () => {
            new daum.Postcode({
                oncomplete: function(data) {
                    if (addressInput) {
                        addressInput.value = data.roadAddress;
                    }
                }
            }).open();
        });
    }

    // --- 이미지 업로드 로직 (기존과 동일) ---
    const imageUpload = document.getElementById('image-upload');
    const previewContainer = document.getElementById('image-preview-container');
    const MAX_IMAGES = 3;
    let uploadedFiles = [];

    if (imageUpload) {
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
                    const previewWrapper = document.createElement('div');
                    previewWrapper.classList.add('preview-image-wrapper');
                    previewWrapper.dataset.fileId = fileId;
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.classList.add('preview-image');
                    const deleteButton = document.createElement('button');
                    deleteButton.type = 'button';
                    deleteButton.classList.add('delete-image-button');
                    deleteButton.textContent = 'X';
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
    }

    function removeImage(fileId) {
        uploadedFiles = uploadedFiles.filter(f => f.id != fileId);
        const wrapperToRemove = previewContainer.querySelector(`[data-file-id='${fileId}']`);
        if (wrapperToRemove) {
            previewContainer.removeChild(wrapperToRemove);
        }
    }

    // --- 폼 전송 로직 ---
    suggestForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = new FormData();
        
        // 기본 정보 추가
        formData.append('placeName', placeNameInput.value);
        formData.append('address', addressInput.value);
        formData.append('placeType', placeTypeHiddenInput.value);

        // 선택된 세부 항목 정보 추가
        const activeDetailCategory = document.querySelector('#category-section .category-group:not(.hidden) .choice-button.active');
        if (activeDetailCategory) {
            formData.append('detailCategory', activeDetailCategory.textContent.trim());
        }

        const activeAtmosphere = document.querySelector('#atmosphere-section .choice-button.active');
        if (activeAtmosphere) {
            formData.append('atmosphere', activeAtmosphere.textContent.trim());
        }

        const activeFeature = document.querySelector('#features-section .choice-button.active');
        if (activeFeature) {
            formData.append('features', activeFeature.textContent.trim());
        }

        // 이미지 추가
        uploadedFiles.forEach(fileObject => {
            formData.append('images', fileObject.file);
        });

        // CSRF 토큰
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
        const headers = {};
        if (csrfToken && csrfHeader) {
            headers[csrfHeader] = csrfToken;
        }

        // 서버로 전송
        fetch('/suggest', {
            method: 'POST',
            headers: headers,
            body: formData
        })
        .then(response => response.text().then(text => ({ ok: response.ok, status: response.status, text: text })))
        .then(response => {
            if (response.ok) {
                alert(response.text || '장소가 성공적으로 제보되었습니다.');
                window.location.href = '/';
            } else {
                throw new Error(response.text || `서버 에러: ${response.status}`);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert(error.message);
        });
    });
});
