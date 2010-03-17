/**
 * Interface implemented by classes representing non-static units in the game world.
 * @author andreas
 *
 */
public interface IUnit {
	/**
	 * Sets the health of the unit.
	 * @param health the integer value giving the new health.
	 */
	public void setHealth(int health);
	/**
	 * Returns the integer value representing the current health
	 * of the unit.
	 * @return the current health of the unit
	 */
	public int getHealth();
	/**
	 * Renders the unit inactive and invulnerable.
	 */
	public void kill();
	/**
	 * Restores the unit to an operating mode.
	 */
	public void revive();

	/**
	 * Sets the damage that the weapon of the unit will inflict.
	 * @param damage the integer value representing the damage.
	 */
	public void setWeaponDamage(int damage);
	/**
	 * Returns the current damage of the units weapon.
	 * @return the integer value representing the damage.
	 */
	public int getWeaponDamage();
}
