package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.PriorityQueue;

public class ShowResponse extends Response {
  public final PriorityQueue<Flat> flats;

  public ShowResponse(PriorityQueue<Flat> flats, String error) {
    super("show", error);
    this.flats = flats;
  }
}
