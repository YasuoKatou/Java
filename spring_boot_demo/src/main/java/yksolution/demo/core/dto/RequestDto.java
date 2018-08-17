package yksolution.demo.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import yksolution.demo.entity.UserMasterEntity;

@Data
@Builder
@ToString
public class RequestDto {
  private String requestUri;
  private UserMasterEntity userMaster;
}