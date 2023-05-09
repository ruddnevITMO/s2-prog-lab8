package ru.rudXson.responses;

public class AddResponse extends Response {
  public final int newId;

  public AddResponse(int newId, String error) {
    super("add", error);
    this.newId = newId;
  }
}
