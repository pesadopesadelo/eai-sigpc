package eaismart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Iekiny Marcel
 * Dec 1, 2020
 */
public class ApplicationDto {
	
	private Long id;
	
	@NotNull
	@Size(max = 20, min = 3)
	private String code;
	
	@NotNull
	@Size(max = 50, min = 5)
	private String name;
	
	@Size(max = 100, min = 5)
	private String description;
	
	private String img_src;
	
	private int status;
	
	private int externo; 
	
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getExterno() {
		return externo;
	}

	public void setExterno(int externo) {
		this.externo = externo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
