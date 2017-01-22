package com.ttpool.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public abstract class BaseDto {

    @Getter @Setter
    @Id
    private String id;
}
