package com.tobeto.hotelReservationSystem.services.dtos.responses.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReplyResponse {

    private int id;

    private int userId;

    private int feedbackId;

    private LocalDateTime date;
}
