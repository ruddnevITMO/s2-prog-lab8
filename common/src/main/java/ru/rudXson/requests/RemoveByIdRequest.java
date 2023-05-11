package ru.rudXson.requests;


import java.util.UUID;

public class RemoveByIdRequest extends Request {
  public final UUID id;

  public RemoveByIdRequest(UUID id) {
    super("remove_by_id");
    this.id = id;
  }
}
