document.addEventListener('DOMContentLoaded', function () {
  const course05Form = document.getElementById('course05-form');
  const likedPlacesListContainer = document.getElementById('liked-places-dynamic-list');
  const selectedPlaceIdsInput = document.getElementById('selectedPlaceIds-input');
  if (!likedPlacesListContainer) return; // 컨테이너 안전가드

  let selectedPlaceIds = new Set();

  function getCsrfToken() {
    return document.querySelector('meta[name="_csrf"]')?.content || '';
  }
  function getCsrfHeader() {
    return document.querySelector('meta[name="_csrf_header"]')?.content || '';
  }

  // (A) 하트 버튼: 먼저 처리 + 즉시 전파 차단
  likedPlacesListContainer.addEventListener('click', function (event) {
    const heartButton = event.target.closest('.heart-button');
    if (!heartButton) return;

    event.preventDefault();
    event.stopImmediatePropagation(); // 같은 노드의 다른 click 리스너로 전파 차단

    // 버튼 자체나 가장 가까운 data-place-id 소유 엘리먼트에서 id 확보
    const card = heartButton.closest('[data-place-id]');
    const placeIdToUnlike =
      heartButton.dataset.placeId ||
      heartButton.getAttribute('data-place-id') ||
      card?.dataset.placeId;

    if (!placeIdToUnlike) {
      console.error('placeId를 찾을 수 없습니다. data-place-id 부착 여부 확인');
      return;
    }

    const csrfToken = getCsrfToken();
    const csrfHeader = getCsrfHeader();

    fetch(`/api/likes/${placeIdToUnlike}/toggle`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
      }
    })
      .then((res) => {
        if (!res.ok) throw new Error('찜 해제 실패');
        return res.json();
      })
      .then((data) => {
        if (!data.liked) {
          const unlikedCard = likedPlacesListContainer.querySelector(
            `[data-place-id="${placeIdToUnlike}"]`
          );
          unlikedCard?.remove();
          selectedPlaceIds.delete(String(placeIdToUnlike));
        }
      })
      .catch((err) => {
        console.error('Error unliking place:', err);
        alert('찜 해제 중 오류가 발생했습니다.');
      });
  });

  // (B) 카드 선택: 하트 클릭이 아닌 경우에만 동작
  likedPlacesListContainer.addEventListener('click', function (event) {
    if (event.target.closest('.heart-button')) return; // 하트면 무시
    const placeCard = event.target.closest('.place-card');
    if (!placeCard) return;

    const checkbox = placeCard.querySelector('.place-checkbox');
    if (!checkbox) return;

    checkbox.checked = !checkbox.checked;
    placeCard.classList.toggle('selected', checkbox.checked);

    const placeId = placeCard.dataset.placeId;
    if (!placeId) return;
    if (checkbox.checked) selectedPlaceIds.add(String(placeId));
    else selectedPlaceIds.delete(String(placeId));
  });

  // (C) 폼 전송 시 선택 id들 주입
  course05Form?.addEventListener('submit', function () {
    selectedPlaceIdsInput.value = Array.from(selectedPlaceIds).join(',');
  });
});
