package com.team32.ong.service.impl;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.EmptyInputException;
import com.team32.ong.mapper.TestimonialMapper;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) {

        if(testimonialDto.getName().isEmpty() || testimonialDto.getName().length() == 0 ||
                testimonialDto.getImage().length() == 0 || testimonialDto.getImage().isEmpty()){
            throw new EmptyInputException("601", "Input Fields are empty");
        }

        Testimonial testimonialToSave =testimonialMapper.dtoToEntity(testimonialDto);

        Testimonial testimonial = testimonialRepository.save(testimonialToSave);

        TestimonialDto testimonialDtoSaved = testimonialMapper.modelToDto(testimonial);

        return testimonialDtoSaved;
    }
}
