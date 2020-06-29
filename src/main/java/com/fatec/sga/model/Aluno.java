package com.fatec.sga.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "CPF não pode ser nulo")
	@Size(min = 11, max = 11, message = "O CPF deve conter 11 numeros")
	private String CPF;

	@NotNull(message = "Nome não pode ser nulo")
	@Size(min = 10, max = 50, message = "O nome precisa ter no mínimo 10 caracteres e no máximo 50")
	private String name;

	@NotNull(message = "Data de matricula não pode ser nulo")
	private LocalDate enrollmentDate;

	@NotNull(message = "Atestado médico não pode ser nulo")
	private boolean medicalCertificate;

	@NotNull(message = "Pagamento não pode ser nulo")
	private boolean paid;

	public Aluno() {
	}

	public Aluno(String CPF, String name, LocalDate enrollmentDate, boolean medicalCertificate, boolean paid) {
		this.CPF = CPF;
		this.name = name;
		this.enrollmentDate = enrollmentDate;
		this.medicalCertificate = medicalCertificate;
		this.paid = paid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@Type(type = "yes_no")
	public boolean isMedicalCertificate() {
		return medicalCertificate;
	}

	public void setMedicalCertificate(boolean medicalCertificate) {
		this.medicalCertificate = medicalCertificate;
	}

	@Type(type = "yes_no")
	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (CPF == null) {
			if (other.CPF != null)
				return false;
		} else if (!CPF.equals(other.CPF))
			return false;
		if (enrollmentDate == null) {
			if (other.enrollmentDate != null)
				return false;
		} else if (!enrollmentDate.equals(other.enrollmentDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalCertificate != other.medicalCertificate)
			return false;
		if (paid != other.paid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", CPF=" + CPF + ", name=" + name + ", enrollmentDate=" + enrollmentDate
				+ ", medicalCertificate=" + medicalCertificate + ", paid=" + paid + "]";
	}
}
