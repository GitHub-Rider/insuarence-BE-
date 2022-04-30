let API_URL = "http://localhost:8080/";
let TOKEN = "Bearer " + localStorage.getItem("token");

function openModal() {
    $("#container").addClass("modal-active");
    $("#modal").fadeIn();
}

function closeModal() {
    $("#container").removeClass("modal-active");
    $("#modal").fadeOut();
}