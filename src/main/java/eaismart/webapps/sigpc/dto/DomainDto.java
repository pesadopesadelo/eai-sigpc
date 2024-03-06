package eaismart.webapps.sigpc.dto;

import eaismart.webapps.sigpc.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Dec 1, 2020
 */
public class DomainDto {
	
	private Long id;

	private String codigo;

	private String dominio;

	private Status estado;

	private String significado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public Status getEstado() {
		return estado;
	}

	public void setEstado(Status estado) {
		this.estado = estado;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}
}
