package ru.rudXson.requests;

import ru.rudXson.datatype.Flat;

public class UpdateRequest extends Request {
  public final int id;
  public final Flat newFlat;

  public UpdateRequest(int id, Flat newFlat) {
    super("update");
    this.id = id;
    this.newFlat = newFlat;
  }
}
