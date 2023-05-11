package ru.rudXson.requests;

import ru.rudXson.datatype.Flat;

import java.util.UUID;

public class UpdateRequest extends Request {
  public final UUID id;
  public final Flat newFlat;

  public UpdateRequest(UUID id, Flat newFlat) {
    super("update");
    this.id = id;
    this.newFlat = newFlat;
  }
}
