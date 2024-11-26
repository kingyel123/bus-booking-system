package com.java.busbookingsystem.bus.businfo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;
    private String busNo;
    private Long seatsAvailability;
    private String via;

    private Time departure;


    public Bus(Long id, String busNo, Long seatsAvailability, String via, Time departure){
        this.id =id;
        this.busNo = busNo;
        this.seatsAvailability = seatsAvailability;
        this.via = via;
        this.departure = departure;

    };

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }


    public String getBusNo(){
        return busNo;
    }

    public  void setBusNo(String busNo){
        this.busNo = busNo;
    }



    public Long getSeatsAvailability(){
        return seatsAvailability;
    }
    public void setSeatsAvailability(Long seatsAvailability){
        this.seatsAvailability = seatsAvailability;
    }

    public String getVia(){
        return via;
    }
    public  void  setVia(String via){
        this.via = via;
    }


    public Time getDeparture(){
        return departure;
    }

    public  void setDeparture(Time departure){
        this.departure = departure;
    }
}


