document.addEventListener('DOMContentLoaded', () => {
    const addressInput = document.getElementById('address-input');
    const searchAddressButton = document.getElementById('search-address-button');
    const nextButton = document.getElementById('next-button');

    // 1. 주소 검색 기능
    if (searchAddressButton) {
        searchAddressButton.addEventListener('click', () => {
            new daum.Postcode({
                oncomplete: function(data) {
                    if (addressInput) {
                        addressInput.value = data.roadAddress; // 도로명 주소 사용
                        
                        // 주소 입력 후 버튼 상태 업데이트를 위해 강제로 input 이벤트 발생
                        const event = new Event('input', { bubbles: true });
                        addressInput.dispatchEvent(event);
                    }
                }
            }).open();
        });
    }

    // 2. 주소 입력에 따른 버튼 활성화/비활성화
    if (addressInput) {
        addressInput.addEventListener('input', () => {
            if (addressInput.value.trim() !== '') {
                nextButton.disabled = false;
            } else {
                nextButton.disabled = true;
            }
        });
    }

    // 3. '코스짜기' 버튼 클릭 시 다음 페이지로 이동
    if (nextButton) {
        nextButton.addEventListener('click', () => {
            if (!nextButton.disabled) {
                window.location.href = '/courses/course02';
            }
        });
    }
});
