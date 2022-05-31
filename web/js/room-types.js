// get the room types
$.get("../manager/src/main/java/com/toh/database/core/data/room_type.json", function (data, status) {
    // add the room types to the left bar and to the list
    data.forEach(type => {
        addToLeftBar(type);
        addType(type);
    });

    $("#left-bar .line").last().remove();

})

function addToLeftBar(type) {
    let item = '<div class="left-bar-item"><a href="#'
        + type.name.replace(" ", "-") +
        '"><div>'
        + type.name.toUpperCase() +
        '</div></a></div>'
    $("#left-bar").append(item);
    $("#left-bar").append('<div class="line"></div>');
}

function addType(type) {
    let item = '<div id="' + type.name.replace(" ", "-") + '" class="room-card row mx-2">' +
        '<img alt="" class="image-room col-md-5 px-0" src="images/' + type.imageName + '">' +
        '<div class="image-descr col-md-7 p-4">' +
        '<h1>' + type.name.toUpperCase() + '</h1>' +
        '<p>' + type.description + '</p>' +
        '<div class="row">' +
        '<div class="room-price col-12">' +
        '<span class="dollar">$</span>' +
        type.price + ' per night</div>';

    // get the facilities
    $.get("../manager/src/main/java/com/toh/database/core/data/facility.json", function (data, status) {

        // add the facility list to the item
        if (type['facilityList'])
            for (let id of type.facilityList) {
                let facility = data.find((f) => f.id === id);
                item += '<div class="room-extras col-12">' +
                    facility.name + " $ " + facility.price +
                    '</div>';
            }
        item += '</div></div></div>';
        $("#type-container").append(item);
    });
}