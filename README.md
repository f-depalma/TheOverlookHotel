# TheHoverlookHotel

DATASOURCE

How to use:
  GET:
  // return all the entities in the file
  BookingRepository.get().getAll()
  
  // return the entity with the specified ID
  BookingRepository.get().findById(ID)
  
  SAVE:
  // if the entity contain an already existing ID it update the entity else it add
  BookingRepositori.get().save(new Booking())
  
  Remenber to call the repository save method in order to update the edit on the file.
  
