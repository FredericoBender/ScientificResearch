package FARCHD;

/**
 * <p>Title: Item</p>
 *
 * <p>Description: This class contains the representation of a item</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2007</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Jesus Alcal� (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

import java.util.*;


public class Item implements Comparable {
  int variable, value;

  public Item() {
  }

  public Item(int variable, int value) {
	  this.variable = variable;
	  this.value = value;
  }

  public void setValues (int variable, int value) {
	  this.variable = variable;
	  this.value = value;
  }

  public int getVariable () {
	  return (this.variable);
  }

  public int getValue () {
	  return (this.value);
  }

  public Item clone(){
    Item d = new Item();
    d.variable = this.variable;
    d.value = this.value;

	return d;
  }

  public boolean isEqual(Item a) {
	  if ((this.variable == a.variable) && (this.value == a.value))  return (true);
	  else  return (false);
  }


  public int compareTo(Object a) {
    if (((Item) a).variable > this.variable) {
      return -1;
    }
    else if (((Item) a).variable < this.variable) {
      return 1;
    }
    else if (((Item) a).value > this.value) {
      return -1;
    }
    else if (((Item) a).value < this.value) {
      return 1;
    }

    return 0;
  }

}
