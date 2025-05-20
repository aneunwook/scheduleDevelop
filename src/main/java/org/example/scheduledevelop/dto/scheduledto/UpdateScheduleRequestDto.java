package org.example.scheduledevelop.dto.scheduledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleRequestDto {
    private String title;

    private String contents;

    private Long userId;
}
