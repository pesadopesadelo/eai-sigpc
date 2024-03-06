package eaismart.webapps.sigpc.dto;

import eaismart.webapps.sigpc.util.constants.Status;

/**
 * @author Iekiny Marcel Feb 16, 2021
 */
public class ClassifierDto {

    private Long id;
    private String name;
    private String code;
    private String description;
    private String type;
    private long selfId;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSelfId() {
        return selfId;
    }

    public void setSelfId(long selfId) {
        this.selfId = selfId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassifierDto [id=" + id + ", name=" + name + ", code=" + code + ", description=" + description
                + ", type=" + type + ", selfId=" + selfId + ", status=" + status + "]";
    }
}
