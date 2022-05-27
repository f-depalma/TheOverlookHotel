# TheHoverlookHotel

<h3>Database</h3>
<h6>GET:</h6>
<p>
    <i>return all the entities</i><br>
    <code>BookingRepository.execute().getAll()</code><br>
    <i>return the entity with the specified ID</i><br>
    <code>BookingRepository.execute().findById(ID)</code>
</p>

<h6>SAVE:</h6>
<p>
    <i>if the entity contain an already existing ID it updates else it adds the entity</i><br>
    <code>BookingRepository.execute().save(new Booking())</code><br>
    <i>it save and then flush</i><br>
    <code>BookingRepository.execute().saveAndFlush(new Booking())</code>
</p>

<h6>DELETE:</h6>
<p>
    <i>delete the entity with the specified ID</i><br>
    <code>BookingRepository.execute().delete(ID)</code><br>
    <i>delete the entity with the specified ID and update the file</i><br>
    <code>BookingRepository.execute().deleteAndFlush(ID)</code>
</p>
  
<h6>OTHERS:</h6>
<p>
    <i>write all the entity changes to the file</i><br>
    <code>BookingRepository.execute().flush()</code><br>
    <i>check if exist an entity with the specified ID</i><br>
    <code>BookingRepository.execute().contains(ID)</code><br>
</p>

<h6>EXAMPLES</h6>
<pre>
<code>
try {
    // create and save roomType
    RoomType villa = new RoomType("k");
    RoomTypeRepository.execute().save(villa);<br>
    // create and save room
    Room r01 = new Room("201", villa, 5);
    RoomRepository.execute().save(r01);<br>
    // create and save guest
    Guest g = new Guest("Paolo", "+455555555");
    GuestRepository.execute().save(g);<br>
    // create and save facilities
    Facility f = new Facility("a", 20.2);
    FacilityRepository.execute().save(f);
    Facility f2 = new Facility("b", 20.2);
    FacilityRepository.execute().save(f2);<br>
    //creating and save booking
    Booking b = new Booking(r01, g, new Date(10, 10, 10), new Date(10, 10, 10));
    b.saveFacility(f);
    b.saveFacility(f2);
    BookingRepository.execute().save(b);<br>
    //delete room and facility
    RoomRepository.execute().delete(1);
    FacilityRepository.execute().delete(1);
} catch (ConstraintException | EntityNotValidException e) {
    e.printStackTrace();
}
</code>
</pre>
