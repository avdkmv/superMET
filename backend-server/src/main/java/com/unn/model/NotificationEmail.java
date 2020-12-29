package com.unn.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    private String recipent;
    private String subject;
    private String doctorName;
    private LocalDateTime meetDate;
}
