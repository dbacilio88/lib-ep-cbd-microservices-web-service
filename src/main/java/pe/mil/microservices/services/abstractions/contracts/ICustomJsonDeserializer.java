package pe.mil.microservices.services.abstractions.contracts;

import com.google.gson.Gson;

public interface ICustomJsonDeserializer {

    Gson getCustomDeserializer();
}
