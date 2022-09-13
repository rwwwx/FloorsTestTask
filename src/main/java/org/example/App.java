package org.example;

import org.example.model.Building;
import org.example.service.ElevatorOperator;

public class App {

  public static void main(String[] args) {
    Building building = Building.initBuildingWithFloors();
    ElevatorOperator elevatorOperator = new ElevatorOperator(building);
    building.printBuilding();
    System.out.println("=====================================");
    elevatorOperator.start();
  }

}