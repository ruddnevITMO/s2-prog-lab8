package ru.rudXson.responses;

public class InfoResponse extends Response {
  public final String infoMessage;

  public InfoResponse(String infoMessage) {
    super("info", null);
    this.infoMessage = infoMessage;
  }
}
