package org.example.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Floor {

  private final int floorNumber;
  private final List<Integer> waitingPassengers;
  private final boolean canGoUp;
  private final boolean canGoDown;
  private int amountOfEnteredPeople = 0;
  private boolean isElevatorHere = false;

  public void addAmountOfEnteredPeople(int newPeople) {
    amountOfEnteredPeople = amountOfEnteredPeople + newPeople;
  }

  public Floor(int floorNumber, List<Integer> waitingPassengers) {
    this.floorNumber = floorNumber;
    this.waitingPassengers = waitingPassengers;
    canGoUp = true;
    canGoDown = true;
  }

  public Floor(int floorNumber, List<Integer> waitingPassengers, boolean canGoUp, boolean canGoDown) {
    this.floorNumber = floorNumber;
    this.waitingPassengers = waitingPassengers;
    this.canGoUp = canGoUp;
    this.canGoDown = canGoDown;
  }

  public boolean isCanGoUp() {
    return canGoUp;
  }

  public boolean isCanGoDown() {
    return canGoDown;
  }

  public void printFloor() {
    if (isElevatorHere) {
      if (floorNumber > 9) {
        System.out.printf("%d %d|^    ^| ", floorNumber, amountOfEnteredPeople);
        waitingPassengers.forEach(i -> System.out.print(i + " "));
        System.out.println();
      } else {
        System.out.printf("%d  %d|^    ^| ", floorNumber, amountOfEnteredPeople);
        waitingPassengers.forEach(i -> System.out.print(i + " "));
        System.out.println();
      }
    } else {
      if (floorNumber > 9) {
        System.out.printf("%d %d|      | ", floorNumber, amountOfEnteredPeople);
        waitingPassengers.forEach(i -> System.out.print(i + " "));
        System.out.println();
      } else {
        System.out.printf("%d  %d|      | ", floorNumber, amountOfEnteredPeople);
        waitingPassengers.forEach(i -> System.out.print(i + " "));
        System.out.println();
      }
    }
  }

}
