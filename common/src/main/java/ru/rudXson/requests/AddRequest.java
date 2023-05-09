package ru.rudXson.requests;

import ru.rudXson.datatype.Flat;

public class AddRequest extends Request {
  public final Flat flat;

  public AddRequest(Flat flat) {
    super("add");
    this.flat = flat;
  }
}
