document.getElementById("login-form").addEventListener("submit", function(e){
    e.preventDefault();

    let username = document.getElementById("username").value;
    
    let data = 
    {
        "username": username,
        "password": document.getElementById("password").value
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/login",
        headers:
        {
            "Content-Type": "application/json"
        },
        data: JSON.stringify(data),
        success: function(response) {

            // If login was successful, store the JWT token in the local storage
            if(response.status == 1 && response.data.role == "[ROLE_ADMIN]") {
                localStorage.setItem("token", response.data.token);
                window.location.href = "dashboard.html";
                
            }

            // Username or password is incorrect. Display warning
            else if(response.status == 0 || response.data.role != "[ROLE_ADMIN]") {
                $("#login-error").show();
            }

            else {
                window.location.href = "error.html";
            }
            
        },
        error: function() {
            window.location.href = "error.html";
        }
    })

});

window.addEventListener("load", function(){
    localStorage.clear();
});