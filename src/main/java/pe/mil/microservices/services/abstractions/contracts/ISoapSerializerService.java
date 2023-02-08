package pe.mil.microservices.services.abstractions.contracts;

import java.lang.reflect.Type;

public interface ISoapSerializerService {

    void loadCustomJsonDeserializer(ICustomJsonDeserializer customJsonDeserializer);

    String toJson(Object src);

    String toJson(Object src, Type typeOfSrc);

    <T> T fromJson(String json, Type typeOfT);

    <T> T fromObject(Object object, Type typeOfT);
}
