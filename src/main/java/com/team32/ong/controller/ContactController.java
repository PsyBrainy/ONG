package com.team32.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.team32.ong.dto.ContactDTO;
import com.team32.ong.service.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts")
	public ResponseEntity<List<ContactDTO>> getAll(){
		return new ResponseEntity<List<ContactDTO>>(contactService.getAll(),HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<ContactDTO> create(ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.save(contactDTO), HttpStatus.CREATED);
    }
}
