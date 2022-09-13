package org.example.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.example.model.Building;

public class ElevatorOperator {

  private final int elevatorCapacity = 5;
  private List<Integer> currentPassengers;
  private Building building;

  public ElevatorOperator(Building building) {
    this.building = building;
  }

  public void start() {
    int iteration = 0;
    fillElevator();
    while (building.isPassengersWait()) {
      if (getNumberOfRequiredFloor() > building.getCurrentFloor().getFloorNumber()) {
        goUp();
      } else if (getNumberOfRequiredFloor() < building.getCurrentFloor().getFloorNumber()) {
        goDown();
      } else if (getNumberOfRequiredFloor() == -1) {
        goUp();
      } else {
        throw new RuntimeException("something go wrong");
      }
      iteration++;
      print(iteration);
    }

  }

  private void goDown() {
    if (building.getCurrentFloor().isCanGoDown()) {
      int floorNumber = building.getCurrentFloor().getFloorNumber();
      building.getFloors().get(floorNumber - 1).setElevatorHere(true);
      building.getFloors().get(floorNumber).setElevatorHere(false);
      dropPassengers();
      if (getNumberOfFreeSeats() != 0) {fillElevator();}
    } else {
      throw new RuntimeException("it cannot go down");
    }
  }

  public void goUp() {
    if (building.getCurrentFloor().isCanGoUp()) {
      int floorNumber = building.getCurrentFloor().getFloorNumber();
      building.getFloors().get(floorNumber + 1).setElevatorHere(true);
      building.getFloors().get(floorNumber).setElevatorHere(false);
      dropPassengers();
      if (getNumberOfFreeSeats() != 0) {fillElevator();}
    } else {
      throw new RuntimeException("it cannot go up");
    }
  }

  private void dropPassengers() {
    int tmp = (int) currentPassengers
        .stream()
        .filter(integer -> integer == building.getCurrentFloor().getFloorNumber())
        .count();
    currentPassengers
        .removeIf(passenger -> passenger == building.getCurrentFloor().getFloorNumber());
    building.getCurrentFloor().addAmountOfEnteredPeople(tmp);
  }

  private void fillElevator() {
    List<Integer> waitingPassengers = building.getCurrentFloor().getWaitingPassengers();
    if (waitingPassengers.size() > elevatorCapacity) {
      currentPassengers = waitingPassengers.stream().limit(elevatorCapacity)
          .collect(Collectors.toList());
    }
    if (waitingPassengers.size() < elevatorCapacity && !waitingPassengers.isEmpty()) {
      currentPassengers.addAll(waitingPassengers);
    }
  }

  private int getNumberOfRequiredFloor() {
    return currentPassengers.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).max().orElse(-1);
  }

  private int getNumberOfFreeSeats() {
    return 5 - currentPassengers.size();
  }

  public void print(int iteration) {
    System.out.println("Step " + iteration);
    building.printBuilding();
  }

}
