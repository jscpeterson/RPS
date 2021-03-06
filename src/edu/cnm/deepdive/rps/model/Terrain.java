package edu.cnm.deepdive.rps.model;

import java.util.Random;

public class Terrain {

  // Grid is assumed to be square
  private RpsBreed[][] grid;
  private Random rng;
  private Neighborhood neighborhood;

  public Terrain(int size, Random rng, Neighborhood neighborhood) {
    grid = new RpsBreed[size][size];
    this.rng = rng;
    this.neighborhood = neighborhood;
  }

  public void reset(){
    for (int row = 0; row < grid.length; row++) {
      RpsBreed[] rowContents = grid[row];
      for (int column = 0; column < rowContents.length; column++) {
        rowContents[column] = RpsBreed.values()[rng.nextInt(RpsBreed.values().length)];
      }
    }
  }

  public void step() {
    Location attackerLocation = new Location(rng.nextInt(grid.length), rng.nextInt(grid.length));
    RpsBreed attacker = grid[attackerLocation.getRow()][attackerLocation.getColumn()];
    Location offset = neighborhood.randomNeighbor(rng);
    Location defenderLocation = normalize(attackerLocation, offset);
    RpsBreed defender = grid[defenderLocation.getRow()][defenderLocation.getColumn()];
    int result = RpsBreed.REFEREE.compare(attacker, defender);
    if (result < 0) {
      grid[attackerLocation.getRow()][attackerLocation.getColumn()] = defender;
    } else if (result > 0) {
      grid[defenderLocation.getRow()][defenderLocation.getColumn()] = attacker;
    }
  }

  public void step(int numSteps) {
    for (int i = 0; i < numSteps; i++) {
      step();
    }
  }

  /**
   * Returns a reference to the terrain contents. <strong>Important!</strong>
   * This is <strong>not</strong> a safe copy.
   *
   * @return
   */
  public RpsBreed[][] getGrid() {
    return grid;
  }

  private Location normalize(Location base, Location offset) {
    //assumes parameters are never smaller than -grid.length
    int row = (base.getRow() + offset.getRow() + grid.length) % grid.length;
    int column = (base.getColumn() + offset.getColumn() + grid.length) % grid.length;
    return new Location(row, column);
  }

}
