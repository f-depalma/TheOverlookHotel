var now = new Date();
// from min = today
$('#from').attr('min', now.getFullYear() + "-"
    + ("0" + (now.getMonth() + 1)).slice(-2) + "-"
    + ("0" + now.getDate()).slice(-2));

// to min = from max
$('#from').change(function () {
    $('#to').attr('min', this.value);
    $("#search").prop("disabled", $('#from').val() === "" || $('#to').val() === "")
});

//from max = to min 
$('#to').change(function () {
    $('#from').attr('max', this.value);
    $("#search").prop("disabled", $('#from').val() === "" || $('#to').val() === "")
});
var rooms;

// get the rooms
$.get("../manager/src/main/java/com/toh/database/core/data/room.json", function (data, status) {
    rooms = data;

    // get the room types
    $.ajax({
        async: false,
        type: 'GET',
        url: '../manager/src/main/java/com/toh/database/core/data/room_type.json',
        success: function (types) {

            // add type nama and image name to the room
            types.forEach(type => {
                rooms.filter(r => r.roomType === type.id)
                    .map(r => {
                        r.roomType = type.name;
                        r["imageName"] = type.imageName
                    })
            })
        }
    });
})

$("#search").click(function () {
    // get the booking
    $.ajax({
        async: false,
        type: 'GET',
        url: '../manager/src/main/java/com/toh/database/core/data/booking.json',
        success: function (bookings) {
            if ($('#from').val() != "" && $('#to').val() != "") {
                let from = new Date($('#from').val())
                let to = new Date($('#to').val())

                // get the unavailable room
                let unavailableRooms = bookings.filter(booking => {
                    let s = booking.arrive.split("/");
                    let arrive = new Date(s[2], s[1] - 1, s[0]);
                    s = booking.departure.split("/")
                    let departure = new Date(s[2], s[1] - 1, s[0]);

                    return !((arrive.getTime() < from.getTime() && departure.getTime() <= from.getTime())
                        || (arrive.getTime() >= to.getTime() && departure.getTime() > to.getTime()));
                }).map(b => b.room);

                // set the availability to the rooms
                rooms.map(room => unavailableRooms.includes(room.id) ? room["available"] = false : room["available"] = true);
                $("#room-container").empty()

                // add the rooms to the document
                rooms.forEach(room => {
                    let item = '<div class="room-card col-xxl-3 col-xl-4 col-md-6 col-12">' +
                        '<img alt="" src="images/' + room.imageName + '">' +
                        '<div class="room-descr">' +
                        '<div class="line">' +
                        '<span class="name">Availability:</span>' +
                        '<span class="bullet ' + (room.available ? "free" : "booked") + '">•</span>' +
                        '<span class="value">' + (room.available ? "Free" : "Booked") + '</span></div>' +
                        '<div class="line">' +
                        '<span class="name">Category:</span>' +
                        '<span class="value">' + room.roomType + '</span></div>' +
                        '<div class="line">' +
                        '<span class="name">Beds n.:</span>' +
                        '<span class="value">' + room.beds + '</span></div>' +
                        '<div class="line">' +
                        '<span class="name">Price:</span>' +
                        '<span class="value">$' + room.price + '</span></div>' +
                        '<div class="line">' +
                        '<span class="name">Room n.:</span>' +
                        '<span class="value">' + room.number + '</span></div></div></div>';

                    $("#room-container").append(item);

                })
            }
        }
    });
})
