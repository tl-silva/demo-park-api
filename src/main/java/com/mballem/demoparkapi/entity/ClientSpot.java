package com.mballem.demoparkapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "clients_have_spots")
@EntityListeners(AuditingEntityListener.class)
public class ClientSpot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "receipt_number", nullable = false, unique = true, length = 15)
	private String receipt;
	
	@Column(name = "license_plate", nullable = false, length = 8)
	private String licensePlate;
	
	@Column(name = "brand", nullable = false, length = 45)
	private String brand;
	
	@Column(name = "model", nullable = false, length = 45)
	private String model;
	
	@Column(name = "color", nullable = false, length = 45)
	private String color;
	
	@Column(name = "entry_date", nullable = false)
	private LocalDateTime entryDate;
	
	@Column(name = "exit_date")
	private LocalDateTime exitDate;
	
	@Column(name = "fee", columnDefinition = "decimal(7,2)")
	private BigDecimal fee;
	
	@Column(name = "discount", columnDefinition = "decimal(7,2)")
	private BigDecimal discount;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "spot_id", nullable = false)
	private Spot spot;
	
	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate; 
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientSpot other = (ClientSpot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
