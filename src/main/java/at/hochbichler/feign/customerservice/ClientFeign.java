package at.hochbichler.feign.customerservice;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;

import java.io.IOException;

public class ClientFeign {
    public static void main(String[] args) {
        Customer customer = CustomerClient.connect().customer();
        System.out.println("Customer: " + customer);
    }

    interface CustomerClient {

        @RequestLine("GET /customer")
        Customer customer();

        static CustomerClient connect() {
            Decoder decoder = new GsonDecoder();
            return Feign.builder()
                    .decoder(decoder)
                    .errorDecoder(new CustomerErrorDecoder(decoder))
                    .target(CustomerClient.class, "http://localhost:8080");
        }
    }

    static class ClientError extends RuntimeException {
        private String message; // parsed from json

        @Override
        public String getMessage() {
            return message;
        }
    }

    static class CustomerErrorDecoder implements ErrorDecoder {

        final Decoder decoder;
        final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

        CustomerErrorDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                return (Exception) decoder.decode(response, ErrorDecoder.class);
            } catch (IOException fallbackToDefault) {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}
