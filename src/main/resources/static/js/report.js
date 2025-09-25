document.addEventListener('DOMContentLoaded', () => {
    const reportForm = document.getElementById('report-form');
    if (!reportForm) return;

    // --- Character Counters ---
    const titleInput = document.getElementById('title');
    const titleCharCount = document.getElementById('title-char-count');
    const contentInput = document.getElementById('content');
    const contentCharCount = document.getElementById('content-char-count');

    titleInput.addEventListener('input', () => {
        titleCharCount.textContent = titleInput.value.length;
    });

    contentInput.addEventListener('input', () => {
        contentCharCount.textContent = contentInput.value.length;
    });

    // --- Image Upload and Preview ---
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
                deleteButton.type = 'button'; // Prevent form submission
                deleteButton.addEventListener('click', () => {
                    removeImage(fileId);
                });

                previewWrapper.appendChild(img);
                previewWrapper.appendChild(deleteButton);
                previewContainer.appendChild(previewWrapper);

                uploadedFiles.push({ id: fileId, file: file });
            };
            reader.readAsDataURL(file);
        });

        // Clear the file input for next selection
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
    reportForm.addEventListener('submit', (event) => {
        event.preventDefault(); // Prevent default HTML form submission

        const formData = new FormData();
        formData.append('title', titleInput.value);
        formData.append('content', contentInput.value);
        
        uploadedFiles.forEach(fileObject => {
            formData.append('images', fileObject.file);
        });

        fetch('/report', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                // The form was submitted successfully.
                // The controller redirects, and the browser should follow it.
                // We'll show a success message and redirect to home page as a final action.
                alert('문의가 성공적으로 접수되었습니다.');
                window.location.href = '/';
            } else {
                // Handle HTTP errors
                alert('문의 접수 중 오류가 발생했습니다. 다시 시도해주세요.');
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('네트워크 오류가 발생했습니다. 다시 시도해주세요.');
        });
    });
});
