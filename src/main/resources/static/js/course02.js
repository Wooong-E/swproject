document.addEventListener('DOMContentLoaded', () => {
    const moodButtonsContainer = document.querySelector('.mood-buttons-container');
    const moodButtons = document.querySelectorAll('.mood-button');
    const completeButton = document.getElementById('selection-complete-button');
    const fhashInput = document.getElementById('fhash-input');
    const course02Form = document.getElementById('course02-form');

    if (moodButtonsContainer && completeButton) {
        // 1. 분위기 버튼 선택 로직
        moodButtonsContainer.addEventListener('click', (event) => {
            const clickedButton = event.target.closest('.mood-button');

            if (!clickedButton) return; // 버튼이나 버튼의 자식요소가 아니면 무시

            // 모든 버튼의 비활성 스타일 초기화
            moodButtons.forEach(button => {
                button.classList.remove('inactive');
            });

            // 클릭된 버튼을 제외한 나머지 버튼에 비활성 스타일 적용
            moodButtons.forEach(button => {
                if (button !== clickedButton) {
                    button.classList.add('inactive');
                }
            });

            // 선택된 분위기 값을 숨겨진 필드에 저장
            fhashInput.value = clickedButton.dataset.mood;

            // '선택 완료' 버튼 활성화
            completeButton.disabled = false;
        });

        // 2. '선택 완료' 버튼 클릭 시 페이지 이동 (폼 제출로 변경)
        completeButton.addEventListener('click', () => {
            if (!completeButton.disabled) {
                course02Form.submit(); // 폼 제출
            }
        });
    }
});
