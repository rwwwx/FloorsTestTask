package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.model.Building;

public class ElevatorOperator {

  private final int elevatorCapacity = 5;
  private List<Integer> currentPassengers = new ArrayList<>();
  private Building building;

  public ElevatorOperator(Building building) {
    this.building = building;
  }

  public void start() {
    int iteration = 0;
    //if 1 floor is empty start with 2
    fillElevatorForFirstTime();
    while (building.isPassengersInBuildingWait()) {
      if (building.getCurrentFloor().getWaitingPassengers().isEmpty()
          || getNumberOfRequiredFloor() > building.getCurrentFloor().getFloorNumber()) {
        goUp();
      } else {
        goDown();
      }
      iteration++;
      print(iteration);
    }

  }

  private void fillElevatorForFirstTime() {
    if (!building.getCurrentFloor().getWaitingPassengers().isEmpty()) {
      fillElevator(5);
    } else {
      goUp();
    }
  }

  private void goDown() {
    if (building.getCurrentFloor().isCanGoDown()) {
      int floorNumber = building.getCurrentFloor().getFloorNumber();
      building.getFloors().get(floorNumber - 1).setElevatorHere(true);
      building.getFloors().get(floorNumber).setElevatorHere(false);
      dropPassengers();
      if (getNumberOfFreeSeats() != 0) {
        fillElevator(getNumberOfFreeSeats());
      }
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
      if (getNumberOfFreeSeats() != 0) {
        fillElevator(getNumberOfFreeSeats());
      }
    } else {
      throw new RuntimeException("it cannot go up");
    }
  }

  private void dropPassengers() {
    if (currentPassengers.isEmpty()) {
      return;
    }
    List<Integer> deleteList = currentPassengers.stream()
        .filter(passenger -> passenger == building.getCurrentFloor().getFloorNumber())
        .collect(Collectors.toList());
    building.getCurrentFloor().addAmountOfEnteredPeople(deleteList.size());
    currentPassengers.removeAll(deleteList);
    building.dropPassengers(deleteList);
  }

  private void fillElevator(int limit) {
    List<Integer> waitingPassengers = building.getCurrentFloor().getWaitingPassengers();
    if (waitingPassengers.isEmpty()) {
      return;
    }
    for (int i = 0; i < limit; i++) {
      currentPassengers.add(waitingPassengers.get(i));
      building.dropPassengers(waitingPassengers.get(i));
    }
  }

  private int getNumberOfRequiredFloor() {
    return currentPassengers.stream().mapToInt(Integer::intValue).max().orElse(-1);
  }

  private int getNumberOfFreeSeats() {
    return 5 - currentPassengers.size();
  }

  public void print(int iteration) {
    System.out.println("Step " + iteration + currentPassengers);
    building.printBuilding();
  }

}
