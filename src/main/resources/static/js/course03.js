document.addEventListener('DOMContentLoaded', () => {
    const selectionButtonsContainer = document.querySelector('.mood-buttons-container');
    const selectionButtons = document.querySelectorAll('.mood-button');
    const completeButton = document.getElementById('selection-complete-button');
    const shashInput = document.getElementById('shash-input');
    const course03Form = document.getElementById('course03-form');

    if (selectionButtonsContainer && completeButton) {
        // 1. 버튼 선택 로직
        selectionButtonsContainer.addEventListener('click', (event) => {
            const clickedButton = event.target.closest('.mood-button');

            if (!clickedButton) return;

            // 모든 버튼의 비활성 스타일 초기화
            selectionButtons.forEach(button => {
                button.classList.remove('inactive');
            });

            // 클릭된 버튼을 제외한 나머지 버튼에 비활성 스타일 적용
            selectionButtons.forEach(button => {
                if (button !== clickedButton) {
                    button.classList.add('inactive');
                }
            });

            // 선택된 동반자 값을 숨겨진 필드에 저장
            shashInput.value = clickedButton.dataset.companion;

            // '선택 완료' 버튼 활성화
            completeButton.disabled = false;
        });

        // 2. '선택 완료' 버튼 클릭 시 페이지 이동 (폼 제출로 변경)
        completeButton.addEventListener('click', () => {
            if (!completeButton.disabled) {
                course03Form.submit(); // 폼 제출
            }
        });
    }
});
