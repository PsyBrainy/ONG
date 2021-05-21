package com.team32.ong.service.impl;


import com.team32.ong.dto.ContactDTO;
import com.team32.ong.component.Validation;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Contact;
import com.team32.ong.repository.ContactRepository;
import com.team32.ong.service.ContactService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private Validation valid;

    @Override
    public ContactDTO save(ContactDTO contactDTO) {

        if (contactDTO.getEmail().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
        }else if (contactDTO.getName().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }else if (!valid.validateEmail(contactDTO.getEmail())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_EMAIL_INVALID);
        }

        Contact contactSave = contactRepository.save(dtoToModel(contactDTO));
        return modelToDTO(contactSave);
    }
    
    @Override
	public List<ContactDTO> getAll() {
		List<Contact> listFound = contactRepository.findAll();
		return listFound
                .stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
	}

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contactList = contactRepository.findAll();

        return mapContactListToContacDTOList(contactList, ContactDTO.class);
    }

    private List<ContactDTO> mapContactListToContacDTOList(List<Contact> contactList, Class<ContactDTO> contactDTO) {
        ModelMapper mapper = new ModelMapper();
        return contactList
                .stream()
                .map(element -> mapper.map(element, contactDTO))
                .collect(Collectors.toList());
    }

    ContactDTO modelToDTO(Contact contact){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(contact, ContactDTO.class);
    }

    Contact dtoToModel(ContactDTO contactDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(contactDTO, Contact.class);
    }
}
