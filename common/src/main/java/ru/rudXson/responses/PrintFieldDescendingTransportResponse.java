package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;
import ru.rudXson.datatype.Transport;

import java.util.List;


public class PrintFieldDescendingTransportResponse extends Response {
    public final List<Transport> transports;

    public PrintFieldDescendingTransportResponse(List<Transport> transports, String error) {
        super("print_field_descending_transport", error);
        this.transports = transports;
    }
}
