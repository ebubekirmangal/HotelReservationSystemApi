package com.tobeto.hotelReservationSystem.services.dtos.requests.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAddressRequest {
    private String country;
    private String city;
    private String district;
    private String street;
    private String zipCode;

}

    //TODO:hotelId eklenebilir?
