package com.ms.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {


    private UUID id;
    private String emailTo;
    private String subject;
    private String text;
}
