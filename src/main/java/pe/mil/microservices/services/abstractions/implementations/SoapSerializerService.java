package pe.mil.microservices.services.abstractions.implementations;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.services.abstractions.contracts.ICustomJsonDeserializer;
import pe.mil.microservices.services.abstractions.contracts.ISoapSerializerService;

import java.lang.reflect.Type;
import java.util.Objects;

import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_SERIALIZER_SERVICE_NAME;

@Log4j2
public class SoapSerializerService extends SoapBaseService implements ISoapSerializerService {

    private Gson gson = new Gson();

    public SoapSerializerService() {
        super(SOAP_SERIALIZER_SERVICE_NAME);
    }

    @Override
    public void loadCustomJsonDeserializer(ICustomJsonDeserializer customJsonDeserializer) {
        if (Objects.isNull(customJsonDeserializer)) {
            throw new SoapBusinessProcessException("ICustomJsonDeserializer not found");
        }
        gson = customJsonDeserializer.getCustomDeserializer();

    }

    @Override
    public String toJson(Object src) {
        return Objects.isNull(src) ? null : gson.toJson(src);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return Objects.isNull(src) ? null : gson.toJson(src, typeOfSrc);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        return Objects.isNull(json) ? null : gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromObject(Object object, Type typeOfT) {
        return this.fromJson(this.toJson(object), typeOfT);
    }
}
