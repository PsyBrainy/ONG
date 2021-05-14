package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) {

        if(testimonialDto.getName().isEmpty() || testimonialDto.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(testimonialDto.getContent().isEmpty() || testimonialDto.getContent().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }

        if(testimonialDto.getImage().isEmpty() || testimonialDto.getImage().length() == 0){

            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }

        if(stringIsOnlyDigits(testimonialDto.getName())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_NOT_NUMBER);
        }

        if(stringIsOnlyDigits(testimonialDto.getContent())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_NOT_NUMBER);
        }

        Testimonial testimonialToCreate = this.dtoToModel(testimonialDto);

        testimonialToCreate.setDeleted(false);
        Testimonial testimonialCreated = testimonialRepository.save(testimonialToCreate);

        return modelToDto(testimonialCreated);
    }

    @Override
    public TestimonialDto updateById(TestimonialDto testimonialDtoToUpdate, Long id) throws NotFoundException {

        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException( ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }

        if(testimonialDtoToUpdate.getName().isEmpty() || testimonialDtoToUpdate.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getContent().isEmpty() || testimonialDtoToUpdate.getContent().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getImage().isEmpty() || testimonialDtoToUpdate.getImage().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }

        Optional<Testimonial> testimonials = testimonialRepository.findById(id);

        Testimonial testimonialToUpdate = testimonials.get();
        testimonialToUpdate.setName(testimonialDtoToUpdate.getName());
        testimonialToUpdate.setImage(testimonialDtoToUpdate.getImage());
        testimonialToUpdate.setContent(testimonialDtoToUpdate.getContent());

        testimonialRepository.save(testimonialToUpdate);

        return modelToDto(testimonialToUpdate);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        testimonialRepository.deleteById(id);
    }

    private Boolean stringIsOnlyDigits(String str){
        String regex = "[0-9]+";

        Pattern p = Pattern.compile(regex);

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    private TestimonialDto modelToDto(Testimonial testimonial){

        ModelMapper mapper = new ModelMapper();
        TestimonialDto map = mapper.map(testimonial, TestimonialDto.class);

        return map;
    }


    private Testimonial dtoToModel(TestimonialDto testimonialDto){

        ModelMapper mapper = new ModelMapper();
        Testimonial map = mapper.map(testimonialDto, Testimonial.class);

        return map;
    }
}
