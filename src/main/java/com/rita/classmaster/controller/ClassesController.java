package com.rita.classmaster.controller;

import com.rita.classmaster.dto.ClassRequest;
import com.rita.classmaster.dto.ClassResponse;
import com.rita.classmaster.service.ClassesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("classes")
public class ClassesController {
    private final static Logger logger = LoggerFactory.getLogger(ClassesController.class);

    private final ClassesService classesService;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @PostMapping
    public List<ClassResponse> createClasses(@RequestBody @Valid ClassRequest classRequest) {
        logger.info("Received request to create classes. classRequest={}", classRequest);
        return classesService.createClasses(classRequest);
    }
}
