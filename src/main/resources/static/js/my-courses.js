document.addEventListener('DOMContentLoaded', function() {
    const deleteForms = document.querySelectorAll('.delete-form');

    deleteForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            const confirmed = confirm('정말로 이 코스를 삭제하시겠습니까?');
            if (!confirmed) {
                event.preventDefault(); // Stop form submission if not confirmed
            }
        });
    });
});
