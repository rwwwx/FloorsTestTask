package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.exception.CannotGoDownException;
import org.example.exception.CannotGoUpException;
import org.example.model.Building;

public class ElevatorOperator {

  private static final int ELEVATOR_CAPACITY = 5;
  private final List<Integer> currentPassengers = new ArrayList<>();
  private final Building building;

  public ElevatorOperator(Building building) {
    this.building = building;
  }

  public void start() {
    int iteration = 0;
    fillElevatorForFirstTime();
    while (building.isPassengersInBuildingWait()) {
      if (getNumberOfRequiredFloor() > building.getCurrentFloor().getFloorNumber()) {
        goUp();
      } else if (getNumberOfRequiredFloor() < building.getCurrentFloor().getFloorNumber()){
        goDown();
      } else {
        throw new RuntimeException("something went wrong");
      }
      iteration++;
      print(iteration);
    }

  }

  private void fillElevatorForFirstTime() {
    if (!building.getCurrentFloor().getWaitingPassengers().isEmpty()) {
      fillElevator(building.getCurrentFloor().getWaitingPassengers().size());
    } else {
      while (currentPassengers.isEmpty()) {
        goUp();
      }
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
      throw new CannotGoDownException();
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
      throw new CannotGoUpException();
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
  }

  private void fillElevator(int limit) {
    List<Integer> waitingPassengers = building.getCurrentFloor().getWaitingPassengers();
    if (waitingPassengers.isEmpty()) {
      return;
    }
    if (waitingPassengers.size() < limit){
      limit = waitingPassengers.size();
    }
    ArrayList<Integer> deleteList = new ArrayList<>();
    for (int i = 0; i < limit; i++) {
      currentPassengers.add(waitingPassengers.get(i));
      deleteList.add(waitingPassengers.get(i));
    }
    building.dropPassengers(deleteList);
  }

  private int getNumberOfRequiredFloor() {
    return currentPassengers.stream().mapToInt(Integer::intValue).max().orElse(-1);
  }

  private int getNumberOfFreeSeats() {
    return ELEVATOR_CAPACITY - currentPassengers.size();
  }

  public void print(int iteration) {
    System.out.println("Step " + iteration + currentPassengers);
    building.printBuilding();
  }

}
