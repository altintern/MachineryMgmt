package com.machinarymgmt.service.api.config.dto;

public enum MessageType {
  ERROR("ERROR"), WARNING("WARNING"), INFO("INFO");

  private String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
