var now = new Date();

$('#from').attr('min', now.getFullYear() + "-"
    + ("0" + (now.getMonth() + 1)).slice(-2) + "-"
    + ("0" + now.getDate()).slice(-2));

$('#from').change(function () {
    $('#to').attr('min', this.value);
    $("#search").prop("disabled", $('#from').val() === "" || $('#to').val() === "")
});
$('#to').change(function () {
    $('#from').attr('max', this.value);
    $("#search").prop("disabled", $('#from').val() === "" || $('#to').val() === "")
});
var rooms;

$.get("../manager/src/main/resources/com/toh/data/room.json", function (data, status) {
    rooms = data;
    $.ajax({
        async: false,
        type: 'GET',
        url: '../manager/src/main/resources/com/toh/data/room_type.json',
        success: function (types) {
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
    $.ajax({
        async: false,
        type: 'GET',
        url: '../manager/src/main/resources/com/toh/data/booking.json',
        success: function (bookings) {
            if ($('#from').val() != "" && $('#to').val() != "") {
                let from = new Date($('#from').val())
                let to = new Date($('#to').val())

                let unavailableRooms = bookings.filter(booking => {
                    let s = booking.arrive.split("/");
                    let arrive = new Date(s[2], s[1] - 1, s[0]);
                    s = booking.departure.split("/")
                    let departure = new Date(s[2], s[1] - 1, s[0]);

                    console.log(booking.room, arrive, departure)

                    return !(arrive.getTime() <= from.getTime() && departure.getTime() <= from.getTime()
                        || arrive.getTime() >= to.getTime() && departure.getTime() >= to.getTime());
                }).map(b => b.room);

                rooms.map(room => unavailableRooms.includes(room.id) ? room["available"] = false : room["available"] = true);
                console.log(unavailableRooms)
                $("#room-container").empty()

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
                        '<span class="name">Beds:</span>' +
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
