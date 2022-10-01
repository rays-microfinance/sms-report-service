package com.sahay.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

    @Id
    @JsonIgnore()
    private long id;
    private String message;
    @JsonIgnore
    private String phone;



}
