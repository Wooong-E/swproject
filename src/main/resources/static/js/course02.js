document.addEventListener('DOMContentLoaded', () => {
    const moodButtonsContainer = document.querySelector('.mood-buttons-container');
    const moodButtons = document.querySelectorAll('.mood-button');
    const completeButton = document.getElementById('selection-complete-button');

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

            // '선택 완료' 버튼 활성화
            completeButton.disabled = false;
        });

        // 2. '선택 완료' 버튼 클릭 시 페이지 이동
        completeButton.addEventListener('click', () => {
            if (!completeButton.disabled) {
                // 선택된 분위기 값을 다음 페이지로 넘길 수 있습니다 (예: 쿼리 파라미터).
                // const selectedMood = document.querySelector('.mood-button:not(.inactive)').dataset.mood;
                // window.location.href = `/courses/course03.html?mood=${selectedMood}`;
                
                // 지금은 단순히 페이지 이동만 구현합니다.
                window.location.href = '/courses/course03';
            }
        });
    }
});
