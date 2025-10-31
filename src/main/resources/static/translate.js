// 여기에 새로 발급받은 안전한 API 키를 입력하세요.
const API_KEY = 'AIzaSyA0jj3kkvG4jjpUEleFdJgqaoxexHNAkh8'; // 반드시 새로 발급받은 키로 교체!

// --- 상태 관리를 위한 변수 ---
let isKorean = true; // 현재 언어가 한국어인지 여부
let originalKoreanText = ''; // 원본 한국어 텍스트를 저장할 변수

// --- HTML 요소 가져오기 ---
const sourceTextElement = document.getElementById('source-text');
const translateButton = document.getElementById('translate-button');
const statusDiv = document.getElementById('status');

// --- 이벤트 리스너 설정 ---
document.addEventListener('DOMContentLoaded', () => {
    // 페이지가 로드되면 원본 텍스트를 저장
    if (sourceTextElement) {
        originalKoreanText = sourceTextElement.innerText;
    }

    // 버튼이 클릭되면 handleTranslateToggle 함수를 실행
    if (translateButton) {
        translateButton.addEventListener('click', handleTranslateToggle);
    }
});


// --- 번역 토글 핸들러 ---
async function handleTranslateToggle() {
    if (isKorean) {
        // 현재 한국어 -> 영어로 번역
        await translateApiCall(originalKoreanText, 'en');
    } else {
        // 현재 영어 -> 원래 한국어로 복원 (API 호출 필요 없음)
        sourceTextElement.innerText = originalKoreanText;
        translateButton.innerText = '영어로 번역하기';
        statusDiv.innerText = '한국어로 복원됨';
        isKorean = true;
    }
}


// --- 실제 API를 호출하는 함수 ---
async function translateApiCall(text, targetLanguage) {
    const url = `https://translation.googleapis.com/language/translate/v2?key=${API_KEY}`;

    statusDiv.innerText = '번역 중...';

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                q: text,
                target: targetLanguage,
            }),
        });

        if (!response.ok) {
            const errorBody = await response.json();
            console.error('API Error:', errorBody.error.message);
            throw new Error(`API 오류: ${errorBody.error.message}`);
        }

        const data = await response.json();
        const translatedText = data.data.translations[0].translatedText;

        // 번역된 텍스트로 화면 업데이트
        sourceTextElement.innerText = translatedText;
        translateButton.innerText = '한국어로 번역하기';
        statusDiv.innerText = '영어로 번역됨';
        isKorean = false; // 상태를 영어로 변경

    } catch (error) {
        console.error('번역 중 오류 발생:', error);
        statusDiv.innerText = `오류가 발생했습니다: ${error.message}`;
    }
}