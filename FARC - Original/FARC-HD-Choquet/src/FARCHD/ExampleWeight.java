package FARCHD;

/**
 * <p>Title: ExampleWeight</p>
 *
 * <p>Description: This class contains the representation to select rules</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2007</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcala (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

public class ExampleWeight{
  double weight;
  int count, K;

  public ExampleWeight(int K) {
	this.K = K;
    this.count = 0;
    this.weight = 1.0;
  }


  public void incCount() {
    this.count++;
	if (this.count >= this.K)  this.weight = 0.0;
	else  this.weight = 1.0 / (count + 1.0);
  }

  public int getCount() {
    return (this.count);
  }

  public boolean isActive() {
    return (this.count < this.K);
  }

  public double getWeight() {
    return (this.weight);
  }
}
