let DATA;

window.addEventListener("load", function(){

    // Load the users
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/admin/get",
        headers:
        {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function(response) {

            DATA = response.data;

            let table = $("#tableMain");

            // Populate the users table
            for(data of response.data) {

                let template = 
                `<tr>
                    <td>`+ data.vehicleRegNo +`</td>
                    <td>`+ data.firstName +` `+ data.lastName +`</td>
                    <td>`+ data.contactNo +`</td>
                    <td>
                        <button onclick="showDetails('`+ data.vehicleRegNo +`')">More Details</button>
                        <button onclick="deleteAccount('`+ data.vehicleRegNo +`')">Delete</button>
                    </td>
                    
                </tr>`;

                table.append(template);

            }
            
        },
        error: function() {
            // Redirect to error page if an error occurs
            window.location.href = "error.html";
        }
    })
});

// Show teh details of the users when clicking on "more info"
function showDetails(id) {

    for(data of DATA) {
        if(data.vehicleRegNo == id) {
            // Open the pop up modal
            openModal();

            $("#user-reg-no").text(data.vehicleRegNo);
            $("#user-name").text(data.firstName + " " + data.lastName);
            $("#user-contact").text(data.contactNo);
            $("#user-nic").text(data.nic);
            $("#user-address").text(data.address);
            $("#user-email").text(data.email);

            $("#policy-no").text(data.policyNo);
            $("#policy-type").text(data.insuranceType);
            $("#exp-date").text(data.expiryDate);

        }
    }
}

function deleteAccount(id) {

    let data = 
    {
        "vehicleRegNo": id
    }

    if(confirm("Are you sure you want to delete this user?")) {
        $.ajax({
            type: "DELETE",
            url: API_URL + "admin/delete",
            headers: 
            {
                "Authorization": TOKEN,
                "Content-Type": "application/json"
            },
            data: JSON.stringify(data),
            success: function(response) {
                alert(response.message);

                // If the user was successfully deleted, refresh the page
                if(response.status == 1) location.reload();
            },
            error: function() {
                // Redirect to error page if an error occurs
                window.location.href = "error.html";
            }
        })
    }
}