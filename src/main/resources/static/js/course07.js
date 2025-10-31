document.addEventListener('DOMContentLoaded', function() {
    // Find the form on the page
    const form = document.getElementById('course07-form');

    // Redirect after 5 seconds by submitting the form

    setTimeout(function() {
        if (form) {
            form.submit();
        }
    }, 5000); // 5000 milliseconds = 5 seconds

});
