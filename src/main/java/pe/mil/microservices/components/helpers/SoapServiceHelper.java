package pe.mil.microservices.components.helpers;

import lombok.experimental.UtilityClass;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@UtilityClass
public class SoapServiceHelper {

    public static Jaxb2Marshaller buildJaxb2Marshaller(final String serviceContextPath) {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(serviceContextPath);
        return marshaller;
    }
}
