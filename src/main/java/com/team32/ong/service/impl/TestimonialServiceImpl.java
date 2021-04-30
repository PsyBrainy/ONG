package com.team32.ong.service.impl;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.EmptyInputException;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;



    @Override
    public Testimonial save(Testimonial testimonial) {
        if(testimonial.getName().isEmpty() || testimonial.getName().length() == 0 ||
                testimonial.getImage().length() == 0 || testimonial.getImage().isEmpty()){
            throw new EmptyInputException("601", "Input Fields are empty");
        }

        testimonial.setDeleted(false);
        Testimonial testimonialSaved = testimonialRepository.save(testimonial);

        return testimonialSaved;
    }
}
