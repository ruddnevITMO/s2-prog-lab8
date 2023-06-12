package ru.rudXson.base;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final CommandExecutor commandExecutor;
    private final DatagramSocket datagramSocket;
    private final ExecutorService fixedReceiveService = Executors.newFixedThreadPool(5);
    private final ExecutorService fixedHandleService = Executors.newFixedThreadPool(5);
    private final ExecutorService cachedRespondService = Executors.newCachedThreadPool();


    public Server(InetSocketAddress addr, CommandExecutor commandExecutor) throws SocketException {
        this.commandExecutor = commandExecutor;
        this.datagramSocket = new DatagramSocket(addr);
    }

    public void go() {
        fixedReceiveService.submit(() -> {
            while (true) {
                Pair<Byte[], SocketAddress> requestPacket = getRequest();
                datagramSocket.connect(requestPacket.getValue());

                Request request = SerializationUtils.deserialize(ArrayUtils.toPrimitive(requestPacket.getKey()));

                fixedHandleService.submit(() -> {
                    Response response = commandExecutor.execute(request);
                    var data = SerializationUtils.serialize(response);

                    cachedRespondService.submit(() -> {
                        try {
                            sendResponse(data, requestPacket.getValue());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
                datagramSocket.disconnect();
            }
        });
    }

    public Pair<Byte[], SocketAddress> getRequest() throws IOException {
        byte[] result = new byte[0];
        SocketAddress addr;

        byte[] data;
        do {
            data = new byte[1024];
            var datagramPacket = new DatagramPacket(data, 1024);
            datagramSocket.receive(datagramPacket);
            addr = datagramPacket.getSocketAddress();

            result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
        } while (data[data.length - 1] != 1);

        return new ImmutablePair<>(ArrayUtils.toObject(result), addr);
    }

    public void sendResponse(byte[] data, SocketAddress addr) throws IOException {
        byte[][] packetStorage = new byte[(int) Math.ceil(data.length / (double) 1023)][1023];

        for (int i = 0; i < packetStorage.length; i++) packetStorage[i] = Arrays.copyOfRange(data, 1023 * i, 1023 * (i + 1));

        for (byte[] packet : packetStorage) {
            var currPacket = Bytes.concat(packet, new byte[]{1});
            var datagramPacket = new DatagramPacket(currPacket, 1024, addr);
            datagramSocket.send(datagramPacket);
        }
        var datagramPacket = new DatagramPacket(ByteBuffer.allocate(1024).put(packetStorage[packetStorage.length - 1]).array(), 1024, addr);
        datagramSocket.send(datagramPacket);
    }
}
