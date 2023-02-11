package pe.mil.microservices.components.factories.implementations;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.helpers.SoapKeyStoreHelper;
import pe.mil.microservices.components.helpers.SoapServiceHelper;
import pe.mil.microservices.components.wsdl.SoapConfiguration;
import pe.mil.microservices.components.wsdl.SoapMutualConfiguration;
import pe.mil.microservices.services.abstractions.implementations.SoapGatewayService;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@Log4j2
@Component
public class SoapServiceGatewayFactory {

    public Mono<SoapGatewayService> doOnFactory(final SoapConfiguration soapConfiguration) {

        return Mono.just(new SoapGatewayService())
            .flatMap(currentSoapGatewayService -> {
                final Jaxb2Marshaller jaxb2Marshaller = SoapServiceHelper.buildJaxb2Marshaller(soapConfiguration.getServiceGeneratorWsdlPath());
                currentSoapGatewayService.setDefaultUri(soapConfiguration.getServiceUri());
                currentSoapGatewayService.setMarshaller(jaxb2Marshaller);
                currentSoapGatewayService.setUnmarshaller(jaxb2Marshaller);
                return Mono.just(currentSoapGatewayService);
            })
            .doOnSuccess(success ->
                log.debug("process factory SoapGatewayService successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process factory SoapGatewayService, error: {}", throwable.getMessage())
            );
    }

    public Mono<SoapGatewayService> doOnFactoryByMutual(
        final SoapConfiguration soapConfiguration,
        final SoapMutualConfiguration soapMutualConfiguration) {

        return Mono.just(new SoapGatewayService())
            .flatMap(currentSoapGatewayService -> {
                final Jaxb2Marshaller jaxb2Marshaller = SoapServiceHelper.buildJaxb2Marshaller(soapConfiguration.getServiceGeneratorWsdlPath());
                currentSoapGatewayService.setWebServiceTemplate(factoryWebServiceTemplate(soapMutualConfiguration));
                currentSoapGatewayService.setDefaultUri(soapConfiguration.getServiceUri());
                currentSoapGatewayService.setMarshaller(jaxb2Marshaller);
                currentSoapGatewayService.setUnmarshaller(jaxb2Marshaller);
                return Mono.just(currentSoapGatewayService);
            })
            .doOnSuccess(success ->
                log.debug("process factory SoapGatewayService by mutual tls successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process factory SoapGatewayService by mutual tls, error: {}", throwable.getMessage())
            );
    }

    public SoapGatewayService factoryByMutual(final SoapConfiguration soapConfiguration, final SoapMutualConfiguration soapMutualConfiguration) {
        final SoapGatewayService soapGatewayService = new SoapGatewayService();
        final Jaxb2Marshaller jaxb2Marshaller = SoapServiceHelper.buildJaxb2Marshaller(soapConfiguration.getServiceGeneratorWsdlPath());
        soapGatewayService.setWebServiceTemplate(factoryWebServiceTemplate(soapMutualConfiguration));
        soapGatewayService.setDefaultUri(soapConfiguration.getServiceUri());
        soapGatewayService.setMarshaller(jaxb2Marshaller);
        soapGatewayService.setUnmarshaller(jaxb2Marshaller);
        return soapGatewayService;
    }

    public SoapGatewayService factory(final SoapConfiguration soapConfiguration) {
        final SoapGatewayService soapGatewayService = new SoapGatewayService();
        final Jaxb2Marshaller jaxb2Marshaller = SoapServiceHelper.buildJaxb2Marshaller(soapConfiguration.getServiceGeneratorWsdlPath());
        soapGatewayService.setDefaultUri(soapConfiguration.getServiceUri());
        soapGatewayService.setMarshaller(jaxb2Marshaller);
        soapGatewayService.setUnmarshaller(jaxb2Marshaller);
        return soapGatewayService;
    }

    private WebServiceTemplate factoryWebServiceTemplate(final SoapMutualConfiguration soapMutualConfiguration) {

        try {

            final SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(
                    SoapKeyStoreHelper.createKeyStore(soapMutualConfiguration.getClientKeyStorePath(), soapMutualConfiguration.getClientKeyStorePassword()),
                    soapMutualConfiguration.getClientKeyStorePassword().toCharArray()
                )
                .loadTrustMaterial(
                    SoapKeyStoreHelper.createKeyStore(soapMutualConfiguration.getServerTrustStorePath(), soapMutualConfiguration.getClientKeyStorePassword()),
                    (TrustStrategy) (chain, authType) -> false
                ).build();

            final SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);


            final HttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .build();


            final HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
            httpComponentsMessageSender.setHttpClient(httpClient);

            final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
            webServiceTemplate.setMessageSender(httpComponentsMessageSender);
            return webServiceTemplate;
        } catch (NoSuchAlgorithmException e) {
            log.error("error in process create sslContext, NoSuchAlgorithmException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException(e.getMessage(), ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (KeyStoreException e) {
            log.error("error in process create sslContext, KeyStoreException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException(e.getMessage(), ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (UnrecoverableKeyException e) {
            log.error("error in process create sslContext, UnrecoverableKeyException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException(e.getMessage(), ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (KeyManagementException e) {
            log.error("error in process create sslContext, KeyManagementException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException(e.getMessage(), ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
