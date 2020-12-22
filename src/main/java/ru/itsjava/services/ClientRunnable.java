package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@RequiredArgsConstructor

public class ClientRunnable implements Runnable, Observer{
    private final Socket socket;
    private final ServerService serverService;

   @SneakyThrows
    @Override
    public void run() {
        System.out.println("Client connected");
        serverService.addObserver(this);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String messageFromClient;
        while ((messageFromClient = bufferedReader.readLine()) != null) {
            System.out.println(messageFromClient);
            serverService.notifyObserver(messageFromClient);
        }

        PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
        clientWriter.println("Hi from server!");
        clientWriter.flush();
    }

    @SneakyThrows
    @Override
    public void notifyMe(String message) {
        PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
        clientWriter.println(message);
        clientWriter.flush();
    }
}
