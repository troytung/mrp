
package com.petfood.mrp.container;

import java.util.List;

public interface Container<E> {

  void add(E obj);
  
  List<E> toList();
  
  int size();
}
