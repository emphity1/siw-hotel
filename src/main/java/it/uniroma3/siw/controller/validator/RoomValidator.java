package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Room;
import it.uniroma3.siw.repository.RoomRepository;

@Component
public class RoomValidator implements Validator{


    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void validate(Object o, Errors errors) {

        Room room = (Room) o;
        if(room.getName().isEmpty())
            errors.rejectValue("name", "required");
        else if(roomRepository.findByName(room.getName()) != null)
            errors.rejectValue("name", "duplicate");

        if(room.getCapacity() == null)
            errors.rejectValue("capacity", "required");
        else if(room.getCapacity() < 1)
            errors.rejectValue("capacity", "invalid");

        if(room.getPrice() == null)
            errors.rejectValue("price", "required");
        else if(room.getPrice() < 1)
            errors.rejectValue("price", "invalid");

        if(room.getDesc().isEmpty())
            errors.rejectValue("desc", "required");
        else if(room.getDesc().length() < 10)
            errors.rejectValue("desc", "invalid");

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Room.class.equals(clazz);
    }

    
}
