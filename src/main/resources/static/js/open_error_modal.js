document.addEventListener('DOMContentLoaded', function() {
    openErrorModal();
});

function openErrorModal() {
    let errorModal = document.getElementById("add-etf-error-modal");
    let errorModalInstance = M.Modal.getInstance(errorModal);
    errorModalInstance.open();
}