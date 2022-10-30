package com.spinyowl.booking.application.storage;

import com.spinyowl.booking.application.model.Table;
import java.util.List;

public interface TableStorage {

  /** Create table. */
  Table create(Table table);

  /** Update table. */
  Table update(Table table);

  /** Delete table. */
  void delete(long id);

  /** Find table by id. */
  Table findById(long id);

  /** Find all tables. */
  List<Table> findAll();

  /** Find all tables by seats. */
  List<Table> findBySeats(int seats);

  /** Find all tables where available at least specified number of seats. */
  List<Table> findByMinSeats(int seats);
}
