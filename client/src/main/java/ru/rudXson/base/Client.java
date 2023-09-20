package ru.rudXson.base;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.SerializationUtils;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;


public class Client {
    private final int PACKET_SIZE = 1024;

    private final DatagramChannel client;
    private final InetSocketAddress addr;

    private String username = null;
    private String password = null;

    public Client(InetAddress address, int port) throws IOException {
        this.addr = new InetSocketAddress(address, port);
        this.client = DatagramChannel.open().bind(null).connect(addr);
        this.client.configureBlocking(false);
    }

    public Response sendRequestGetResponse(Request request) {
        request.setCreds(username, password);
        try {
            sendRequest(SerializationUtils.serialize(request));
            return SerializationUtils.deserialize(getResponse());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCreds(String[] creds) {
        this.username = creds[0];
        this.password = creds[1];
    }

    public void setCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    private void sendRequest(byte[] data) throws IOException {
        int DATA_SIZE = PACKET_SIZE - 1;
        byte[][] ret = new byte[(int) Math.ceil(data.length / (double) DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }


        for (int i = 0; i < ret.length; i++) {
            var chunk = ret[i];
            if (i == ret.length - 1) {
                var lastChunk = Bytes.concat(chunk, new byte[]{1});
                client.send(ByteBuffer.wrap(lastChunk), addr);
            } else {
                var answer = Bytes.concat(chunk, new byte[]{0});
                client.send(ByteBuffer.wrap(answer), addr);
            }
        }

    }

    private byte[] getResponse() throws IOException {
        var received = false;
        var result = new byte[0];

        while (!received) {
            var buffer = ByteBuffer.allocate(PACKET_SIZE);
            SocketAddress address = null;
            while (address == null) {
                address = client.receive(buffer);
            }
            var data = buffer.array();

            if (data[data.length - 1] == 1) {
                received = true;
            }
            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        }

        return result;
    }

}
