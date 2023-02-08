package pe.mil.microservices.components.wsdl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapMutualConfiguration implements Serializable {
    private static final long serialVersionUID = -8613151655476666066L;
    private Boolean mutualEnable;
    private String clientKeyStorePath;
    private String clientKeyStorePassword;
    private String serverTrustStorePath;
    private String serverTrustStorePassword;
}
