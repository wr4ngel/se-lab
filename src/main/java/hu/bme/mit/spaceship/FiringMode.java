package hu.bme.mit.spaceship;

/**
* Weapon firing mode enumeration
*
*/
public enum FiringMode {
  SINGLE, ALL;

/**
* Tries to fire the torpedo stores of the ship.
*
* @param gt4500 
 * @return whether at least one torpedo was fired successfully
*/
public boolean fireTorpedo(GT4500 gt4500) {

  boolean firingSuccess = false;

  switch (this) {
    case SINGLE:
      if (gt4500.wasPrimaryFiredLast) {
        // try to fire the secondary first
        if (! gt4500.secondaryTorpedoStore.isEmpty()) {
          firingSuccess = gt4500.secondaryTorpedoStore.fire(1);
          gt4500.wasPrimaryFiredLast = false;
        }
        else {
          // although primary was fired last time, but the secondary is empty
          // thus try to fire primary again
          if (! gt4500.primaryTorpedoStore.isEmpty()) {
            firingSuccess = gt4500.primaryTorpedoStore.fire(1);
            gt4500.wasPrimaryFiredLast = true;
          }

          // if both of the stores are empty, nothing can be done, return failure
        }
      }
      else {
        // try to fire the primary first
        if (! gt4500.primaryTorpedoStore.isEmpty()) {
          firingSuccess = gt4500.primaryTorpedoStore.fire(1);
          gt4500.wasPrimaryFiredLast = true;
        }
        else {
          // although secondary was fired last time, but primary is empty
          // thus try to fire secondary again
          if (! gt4500.secondaryTorpedoStore.isEmpty()) {
            firingSuccess = gt4500.secondaryTorpedoStore.fire(1);
            gt4500.wasPrimaryFiredLast = false;
          }

          // if both of the stores are empty, nothing can be done, return failure
        }
      }
      break;

    case ALL:
      // try to fire both of the torpedo stores
      if((!gt4500.primaryTorpedoStore.isEmpty())&&(!gt4500.secondaryTorpedoStore.isEmpty())){
        firingSuccess=(gt4500.primaryTorpedoStore.fire(1))||(gt4500.secondaryTorpedoStore.fire(1));
        gt4500.wasPrimaryFiredLast=true;
      }else{
        firingSuccess=false;
      }
      break;
  }

  return firingSuccess;
}
}
