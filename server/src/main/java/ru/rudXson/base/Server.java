package ru.rudXson.base;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.rudXson.exceptions.ExitException;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.ErrorResponse;
import ru.rudXson.responses.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Server {
    private final CommandHandler commandHandler;

    private final int sizePerSend = 1024;

    private final DatagramSocket datagramSocket;


    private boolean running = true;

    public Server(InetSocketAddress addr, CommandHandler commandHandler) throws SocketException {
        this.commandHandler = commandHandler;
        this.datagramSocket = new DatagramSocket(addr);
        this.datagramSocket.setReuseAddress(true);
    }

    public void run() throws ExitException {

        while (running) {
            Pair<Byte[], SocketAddress> dataPair;

            try {
                var result = new byte[0];
                SocketAddress addr;


                var data = new byte[sizePerSend];

                var datagramPacket = new DatagramPacket(data, sizePerSend);
                datagramSocket.receive(datagramPacket);

                addr = datagramPacket.getSocketAddress();

                result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));

                dataPair = new ImmutablePair<>(ArrayUtils.toObject(result), addr);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                datagramSocket.disconnect();
                continue;
            }

            var dataFromClient = dataPair.getKey();
            var clientAddr = dataPair.getValue();

            try {
                datagramSocket.connect(clientAddr);
            } catch (Exception e) {
            }

            Request request;
            try {
                request = SerializationUtils.deserialize(ArrayUtils.toPrimitive(dataFromClient));
            } catch (SerializationException e) {
                datagramSocket.disconnect();
                continue;
            }

            Response response = commandHandler.handle(request);

            if (response == null) response = new ErrorResponse(request.name);

            var data = SerializationUtils.serialize(response);

            try {
                sendResponse(data, clientAddr);
            } catch (Exception e) {
                System.out.println("idk");
            }

            datagramSocket.disconnect();
        }

        datagramSocket.close();
    }

    public void sendResponse(byte[] data, SocketAddress addr) throws IOException {
        int dataSizePerSend = sizePerSend - 1;
        byte[][] responseSplit = new byte[(int) Math.ceil(data.length / (double) dataSizePerSend)][dataSizePerSend];

        int currPos = 0;
        for (int i = 0; i < responseSplit.length; i++) {
            responseSplit[i] = Arrays.copyOfRange(data, currPos, currPos + dataSizePerSend);
            currPos += dataSizePerSend;
        }

        for (int currChunkNum = 0; currChunkNum < responseSplit.length; currChunkNum++) {
            var currChunk = responseSplit[currChunkNum];
            if (currChunkNum == responseSplit.length - 1) {
                var lastCurrChunk = Bytes.concat(currChunk, new byte[]{1});
                var datagramPacket = new DatagramPacket(lastCurrChunk, sizePerSend, addr);
                datagramSocket.send(datagramPacket);
            } else {
                var datagramPacket = new DatagramPacket(ByteBuffer.allocate(sizePerSend).put(currChunk).array(), sizePerSend, addr);
                datagramSocket.send(datagramPacket);
            }
        }

    }
}



