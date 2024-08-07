package com.tobeto.hotelReservationSystem.core.utils.exceptions.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetails {

    private String title;
    private String type;
    private String detail;
}
