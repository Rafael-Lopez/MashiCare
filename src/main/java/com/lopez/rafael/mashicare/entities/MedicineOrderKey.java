package com.lopez.rafael.mashicare.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//https://www.baeldung.com/jpa-many-to-many#many-to-many-using-a-composite-key
@Embeddable
public class MedicineOrderKey implements Serializable {
    @Column(name = "medicine_id")
    private Integer medicineId;
    @Column(name = "order_id")
    private Integer orderId;

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineOrderKey that = (MedicineOrderKey) o;
        return Objects.equals(medicineId, that.medicineId) && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, orderId);
    }
}
