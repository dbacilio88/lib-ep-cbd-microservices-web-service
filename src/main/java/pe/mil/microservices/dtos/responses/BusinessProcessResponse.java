package pe.mil.microservices.dtos.responses;

import lombok.Builder;
import lombok.Data;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.dtos.base.BaseBusinessResponseDto;

import java.util.Objects;

@Data
@Builder
public class BusinessProcessResponse {

    ResponseCode responseCode;
    private boolean successfullyResponse;
    private boolean emptySuccessfullyResponse;
    private BaseBusinessResponseDto businessResponse;
    private BusinessProcessResult businessProcessResult;

    public static BusinessProcessResponse setEntitySuccessfullyResponse(BaseBusinessResponseDto businessResponse) {
        return BusinessProcessResponse.builder().businessProcessResult(BusinessProcessResult.BUSINESS_PROCESS_SUCCESSFULLY).responseCode(ResponseCode.PROCESS_OK).successfullyResponse(true).emptySuccessfullyResponse(false).businessResponse(businessResponse).build();
    }

    public static BusinessProcessResponse setBusinessErrorResponse(BaseBusinessResponseDto businessResponse, ResponseCode responseCode) {
        return builder().businessProcessResult(BusinessProcessResult.BUSINESS_PROCESS_SUCCESSFULLY).responseCode(responseCode).successfullyResponse(true).emptySuccessfullyResponse(false).businessResponse(businessResponse).build();
    }

    public static BusinessProcessResponse setEmptySuccessfullyResponse() {
        return BusinessProcessResponse.builder().businessProcessResult(BusinessProcessResult.BUSINESS_PROCESS_SUCCESSFULLY).responseCode(ResponseCode.PROCESS_OK).successfullyResponse(true).emptySuccessfullyResponse(true).build();
    }

    public static BusinessProcessResponse setBusinessProcessError(ResponseCode responseCode) {
        return BusinessProcessResponse.builder().businessProcessResult(BusinessProcessResult.BUSINESS_PROCESS_ERROR).successfullyResponse(false).responseCode(responseCode).build();
    }

    public boolean isEmptySuccessfullyResponse() {
        return this.emptySuccessfullyResponse;
    }

    public boolean isSuccessfullyResponse() {
        return this.successfullyResponse;
    }

    public boolean isErrorProcessResponse() {
        return !this.successfullyResponse;
    }

    public boolean existBusinessResponse() {
        return !Objects.isNull(this.businessResponse);
    }

    public enum BusinessProcessResult {
        BUSINESS_PROCESS_SUCCESSFULLY, BUSINESS_PROCESS_ERROR
    }
}
