document.addEventListener('DOMContentLoaded', function() {
    const likedPlacesDynamicList = document.getElementById('liked-places-dynamic-list');
    const createCourseButton = document.getElementById('create-course-button');
    let selectedPlaceIds = new Set();

    // Function to update button state
    function updateCreateCourseButtonState() {
        if (selectedPlaceIds.size > 0) {
            createCourseButton.disabled = false;
        } else {
            createCourseButton.disabled = true;
        }
    }

    const fetchAndRenderLikedPlaces = () => {
        // Assuming isLoggedIn is available globally or passed via meta tag
        // For now, let's assume user is logged in for fetching
        // You might need to add a meta tag for _csrf token if POST requests are made
        // var isLoggedIn = /*[[${isLoggedIn}]]*/ false; // If passed from Thymeleaf

        // For simplicity, assuming isLoggedIn is true for fetching
        // In a real app, check if user is authenticated
        if (true /* Replace with actual isLoggedIn check */) {
            fetch('/api/likes/categorized')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('서버에서 찜 목록을 가져오는 데 실패했습니다.');
                    }
                    return response.json();
                })
                .then(data => {
                    // Flatten the categorized data into a single list of places
                    let allLikedPlaces = [];
                    Object.keys(data).forEach(category => {
                        allLikedPlaces = allLikedPlaces.concat(data[category]);
                    });

                    renderLikedPlaces(allLikedPlaces);
                })
                .catch(error => {
                    console.error('찜 목록 로딩 오류:', error);
                    likedPlacesDynamicList.innerHTML = '<p style="text-align: center; padding: 20px;">찜한 장소를 불러오는 데 실패했습니다.</p>';
                });
        } else {
            likedPlacesDynamicList.innerHTML = '<p style="text-align: center; padding: 20px;">로그인 후 찜한 장소를 확인해보세요.</p>';
        }
    };

    const renderLikedPlaces = (places) => {
        likedPlacesDynamicList.innerHTML = ''; // Clear loading message

        if (places.length === 0) {
            likedPlacesDynamicList.innerHTML = '<p style="text-align: center; padding: 20px;">찜한 장소가 없습니다.</p>';
            return;
        }

        places.forEach(place => {
            const item = document.createElement('div');
            item.classList.add('liked-place-item');
            item.setAttribute('data-place-id', place.id);

            item.innerHTML = `
                <div class="place-image-wrapper">
                    <img src="/images/attractions/attraction_${place.id}.jpg" alt="${place.name}" class="place-image">
                    <div class="overlay">
                        <img src="/images/check.png" alt="체크" class="check-icon">
                    </div>
                </div>
                <div class="place-details">
                    <span class="place-name">${place.name}</span>
                    <span class="place-address">${place.address}</span>
                </div>
                <div class="like-button">
                    <img src="/images/tabler_heart_filled.svg" alt="찜하기" class="like-icon">
                </div>
            `;
            likedPlacesDynamicList.appendChild(item);

            // Add click listener for each new item
            const overlay = item.querySelector('.overlay');
            item.addEventListener('click', function() {
                overlay.classList.toggle('active');
                if (overlay.classList.contains('active')) {
                    selectedPlaceIds.add(place.id);
                } else {
                    selectedPlaceIds.delete(place.id);
                }
                updateCreateCourseButtonState();
            });
        });
    };

    // Initial fetch and render
    fetchAndRenderLikedPlaces();

    // Initial button state
    updateCreateCourseButtonState();

    // Click listener for the create course button
    createCourseButton.addEventListener('click', function() {
        if (selectedPlaceIds.size > 0) {
            window.location.href = '/courses/course06';
        } else {
            alert('코스를 생성하려면 장소를 선택해주세요.');
        }
    });
});