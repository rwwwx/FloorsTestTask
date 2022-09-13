package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Building {

  private Building(){}

  private static final Random random = new Random();
  private List<Floor> floors;

  public Floor getCurrentFloor() {
    return floors
        .stream()
        .filter(Floor::isElevatorHere)
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }

  public void dropPassengers(List<Integer> deleteList) {
    getCurrentFloor().getWaitingPassengers().removeAll(deleteList);
  }

  public List<Floor> getFloors() {
    return floors;
  }

  public void printBuilding() {
    floors.stream().sorted(Comparator.comparingInt(Floor::getFloorNumber).reversed()).toList().forEach(Floor::printFloor);
  }

  public static Building initBuildingWithFloors() {
    Building building = new Building();
    int amountOfFloors = random.nextInt(5,21);
    building.floors = new ArrayList<>();

    for (int i = 0; i < amountOfFloors; i++) {
      List<Integer> passengers = generateListOfPassengers(i, random.nextInt(11), amountOfFloors);
      if (i == 0) {
        building.floors.add(new Floor(i, passengers, true, false));
      } else if (i == amountOfFloors - 1) {
        building.floors.add(new Floor(i, passengers, false, true));
      } else {
        building.floors.add(new Floor(i, passengers));
      }
    }

    building.floors.get(0).setElevatorHere(true);
    return building;
  }

  private static List<Integer> generateListOfPassengers(int numberOfFloor, int amountOfPassengers,
      int amountOfBuilding) {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < amountOfPassengers; i++) {
      int tmp = random.nextInt(amountOfBuilding);
      while (tmp == numberOfFloor) {
        tmp = random.nextInt(amountOfBuilding);
      }
      result.add(tmp);
    }
    return result;
  }

}
