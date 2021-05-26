package com.lopez.rafael.mashicare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MedicineOrder {
    // Remove unwanted data when serializing
    @JsonIgnore
    @EmbeddedId
    private MedicineOrderKey id;

    @ManyToOne
    @MapsId("medicineId")
    @JoinColumn(name = "medicineId")
    private Medicine medicine;

    // Remove unwanted data when serializing
    @JsonIgnore
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private Order order;

    private Integer quantity;

    public MedicineOrderKey getId() {
        return id;
    }

    public void setId(MedicineOrderKey id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
