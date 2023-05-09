package ru.rudXson.responses;


public class RemoveByIdResponse extends Response {
  public RemoveByIdResponse(String error) {
    super("remove_by_id", error);
  }
}
