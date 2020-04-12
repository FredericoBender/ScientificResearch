package FARCHD;

/**
 * <p>Title: Cover</p>
 *
 * <p>Description: This class contains the representation to examples covered</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2007</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcala (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

public class Cover{
  int pos;

  public Cover(int pos) {
    this.pos = pos;
  }

  public Cover clone() {
	return (new Cover(this.pos));
  }

  public int getPos() {
    return (this.pos);
  }
}
