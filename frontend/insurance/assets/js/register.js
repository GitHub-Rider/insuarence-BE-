document.getElementById("registration-form").addEventListener("submit", function(e) {
    // Prevent the default form submit event
    e.preventDefault();
    
   let data = 
   {
        "vehicleRegNo": document.getElementById("reg-no").value,
        "firstName": document.getElementById("first-name").value,
        "lastName": document.getElementById("last-name").value,
        "contactNo": document.getElementById("contact-no").value,
        "nic": document.getElementById("nic").value,
        "email": document.getElementById("email").value,
        "address": document.getElementById("address").value,
        "policyNo": document.getElementById("policy-no").value,
        "expiryDate": getExpDate(),
        "insuranceType": document.getElementById("insurance-type").value
   }

   $.ajax({
       type: "POST",
       url: API_URL + "admin/register",
       headers:
       {
           "Authorization": TOKEN,
           "Content-Type": "application/json"
       },
       data: JSON.stringify(data),
       success: function(response) {

        alert(response.message);

        // If the user was successfully added, refresh the page
        if(response.status == 1) window.location.href = "dashboard.html";

       },
       error: function() {
           // Redirect to error page if an error occurs
          window.location.href = "error.html";
       }
   })

});

// Generate the policy expiry date, 1 year from the current date
function getExpDate() {

    const date = new Date();
    date.setFullYear(date.getFullYear() + 1);

    // convert the date from dd/mm/yyyy format to yyyy-mm-dd format
    return date.toLocaleDateString("en-GB").split('/').reverse().join('-');

}