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
    <i>if the entity contain an already existing ID it updates else it adds the entity</i>
    <code>BookingRepository.execute().save(new Booking())</code><br>
    <i>use it to save the entities on the file</i><br>
    <code>BookingRepository.execute().flush()</code><br>
    <i>it save and then flush</i><br>
    <code>BookingRepository.execute().saveAndFlush(new Booking())</code>
</p>
  
