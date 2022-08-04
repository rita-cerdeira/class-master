package com.rita.classmaster.mapper;

import com.rita.classmaster.dto.ClassResponse;
import com.rita.classmaster.model.Class;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassResponse classToClassResponse(Class source);
}
