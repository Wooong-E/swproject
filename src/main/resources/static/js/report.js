document.addEventListener('DOMContentLoaded', () => {
    const reportForm = document.getElementById('report-form');
    if (!reportForm) return;

    // --- Character Counters ---
    const titleInput = document.getElementById('title');
    const titleCharCount = document.getElementById('title-char-count');
    const contentInput = document.getElementById('content');
    const contentCharCount = document.getElementById('content-char-count');

    if (titleInput) {
        titleInput.addEventListener('input', () => {
            if(titleCharCount) titleCharCount.textContent = titleInput.value.length;
        });
    }

    if (contentInput) {
        contentInput.addEventListener('input', () => {
            if(contentCharCount) contentCharCount.textContent = contentInput.value.length;
        });
    }

    // --- Image Upload and Preview ---
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

    // --- Form Submission ---
    reportForm.addEventListener('submit', (event) => {
        event.preventDefault(); // 기본 HTML 폼 전송을 막음

        const formData = new FormData();
        formData.append('title', titleInput.value);
        formData.append('content', contentInput.value);

        const csrfToken = document.querySelector('input[name="_csrf"]');
        if (csrfToken) {
            formData.append(csrfToken.name, csrfToken.value);
        }

        uploadedFiles.forEach(fileObject => {
            formData.append('images', fileObject.file);
        });

        fetch('/report', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                // response.text()를 먼저 호출하여 응답 본문을 얻음
                return response.text().then(text => ({
                    ok: response.ok,
                    status: response.status,
                    text: text
                }));
            })
            .then(response => {
                if (response.ok) {
                    // 성공 시, 서버가 보낸 메시지를 팝업으로 띄움
                    alert(response.text || '문의가 성공적으로 접수되었습니다.');
                    window.location.href = '/'; // 홈으로 리다이렉트
                } else {
                    // 실패 시, 서버가 보낸 에러 메시지를 팝업으로 띄움
                    throw new Error(response.text || `서버 에러: ${response.status}`);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                // 최종적으로 에러 메시지를 팝업으로 표시
                alert(error.message);
            });
    });
});
