package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class representing a Firestation entity
 * with address and station number.
 */
@Data
@AllArgsConstructor
public class Firestation {
    private String address;
    private int station;
}
