let DATA = null;

window.addEventListener("load", function(){

    $.ajax({
        type: "GET",
        url: API_URL + "complaints/get",
        headers:
        {
            "Authorization": TOKEN
        },
        success: function(response) {

            // Save the data to be used later
            DATA = response.data;

            let table = $("#tableMain");

            // Populate the complaints table
            for(data of response.data) {

                let template = 
                `<tr>
                    <td>`+ data.complaintNo +`</td>
                    <td>`+ data.user.vehicleRegNo +`</td>
                    <td>`+ data.date +`</td>
                    <td><button onclick="showDetails('`+ data.complaintNo +`')" class="infoBtn">More Details</button></td>
                </tr>`;

                table.append(template);
            }
            
        },
        error: function(eror) {
            window.location.href = "error.html";
        }
    })
});

// Find the data about a specific complaint out of all the complaints in the database by its id
function findById(id) {

    for(data of DATA) {
        if(data.complaintNo == id) return data;
    }
}

// Show the details of a complaint when clicking "more details"
function showDetails(id) {
    $("#container").addClass("modal-active");
    $("#modal").fadeIn();

    let data = findById(id);

    $("#user-reg-no").text(data.user.vehicleRegNo);
    $("#user-name").text(data.user.firstName + " " + data.user.lastName);
    $("#user-contact").text(data.user.contactNo);
    $("#user-nic").text(data.user.nic);

    $("#tp-reg-no").text(data.thirdParty.vehicleRegNo);
    $("#tp-name").text(data.thirdParty.firstName + " " + data.thirdParty.lastName);
    $("#tp-contact").text(data.thirdParty.contactNo);
    $("#tp-nic").text(data.thirdParty.nic);

    $("#accident-date").text(data.date);
    $("#accident-description").text(data.description);
    $("#accident-location").text(data.location);

    $("#accident-recording").attr("src", API_URL + "file/recording/" + data.recordingUrl);

    let imageUrls = data.imageUrl.split(",");

    $("#image-count").text(imageUrls.length);
    $("#accident-images").attr("src", API_URL + "file/image/" + imageUrls[0]);

    // Go to the previous image
    $("#prev-btn").click(function() {
        prevImage(imageUrls);
    });

    // Go to the enxt image
    $("#next-btn").click(function() {
        nextImage(imageUrls);
    });

    if(!data.policeInvolved) {
        $("#police-btn").show();
        // Remove previous event
        $("#police-btn").unbind("click");
        $("#police-btn").click(function(){
            sendToPolice(id);
        });
    }

    else {
        $("#police-btn").hide();
    }

}

function nextImage(urls) {
    let currentImage = $("#current-image");
    let currentImageVal = parseInt(currentImage.text());

    if(currentImageVal < urls.length) {
        $("#accident-images").attr("src", API_URL + "file/image/" + urls[currentImageVal].trim());
        currentImage.text(currentImageVal + 1);
    }

}

function prevImage(urls) {
    let currentImage = $("#current-image");
    let currentImageVal = parseInt(currentImage.text()) - 1;

    if(currentImageVal > 0) {
        $("#accident-images").attr("src", API_URL + "file/image/" + urls[currentImageVal - 1].trim());
        currentImage.text(currentImageVal);
    }
    
}

function sendToPolice(id) {

    $.ajax({
        type: "PUT",
        url: API_URL + "admin/send/" + id,
        headers:
        {
            "Authorization": TOKEN,
        },
        success: function(response) {
            alert(response.message);
            location.reload();
        },
        error: function(error) {
            window.location.href = "error.html";
        }

    })
}