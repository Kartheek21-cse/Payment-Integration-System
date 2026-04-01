package com.stripe_provider_service.dto;

public class StatusUpdateRequestDTO {

    private String sessionId;
    private String status;

    public StatusUpdateRequestDTO(){}

    public StatusUpdateRequestDTO(String sessionId,String status){
        this.sessionId=sessionId;
        this.status=status;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}