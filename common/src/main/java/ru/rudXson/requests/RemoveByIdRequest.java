package ru.rudXson.requests;


public class RemoveByIdRequest extends Request {
  public final int id;

  public RemoveByIdRequest(int id) {
    super("remove_by_id");
    this.id = id;
  }
}
