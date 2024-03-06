package eaismart.webapps.sigpc.entity;
/**
 * @author Iekiny Marcel
 * Feb 14, 2021
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eaismart.entity.BaseEntity;
import eaismart.webapps.sigpc.util.constants.Status;

        
@Entity(name = "sigpcDomain")
@Table(name="tbl_domain")
public class Domain extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Long id;

	@Column(nullable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String dominio;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status estado;
	
	@Column(nullable = false)
	private String significado;

	public Domain() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDominio() {
		return this.dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public Status getEstado() {
		return this.estado;
	}

	public void setEstado(Status estado) {
		this.estado = estado;
	}

	public String getSignificado() {
		return this.significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	@Override
	public String toString() {
		return "DomainSigpc [id=" + id + ", codigo=" + codigo + ", dominio=" + dominio + ", estado=" + estado
				+ ", significado=" + significado + "]";
	}
	
}