// 
// Decompiled by Procyon v0.5.36
// 

package Complexity_Metrics;

import keel.Dataset.Attributes;
import java.util.Vector;
import keel.Dataset.InstanceSet;

public class Statistics
{
    InstanceSet dSet;
    private Vector classValues;
    private int[] numInstancesPerClass;
    private double[][] mean;
    private double[][] variance;
    private double[][] maximum;
    private double[][] minimum;
    private int numberOfClasses;
    private int numberOfAttributes;
    
    Statistics(final InstanceSet _dSet, final int _numberOfClasses) {
        this.dSet = _dSet;
        this.numberOfAttributes = Attributes.getNumAttributes() - 1;
        this.numberOfClasses = _numberOfClasses;
        this.classValues = new Vector();
        this.mean = new double[this.numberOfAttributes][this.numberOfClasses];
        this.numInstancesPerClass = new int[this.numberOfClasses];
        this.variance = new double[this.numberOfAttributes][this.numberOfClasses];
        this.maximum = new double[this.numberOfAttributes][this.numberOfClasses];
        this.minimum = new double[this.numberOfAttributes][this.numberOfClasses];
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            for (int j = 0; j < this.numberOfClasses; ++j) {
                this.mean[i][j] = 0.0;
                this.variance[i][j] = 0.0;
                this.maximum[i][j] = Double.MIN_VALUE;
                this.minimum[i][j] = Double.MAX_VALUE;
            }
        }
        for (int i = 0; i < this.numberOfClasses; ++i) {
            this.numInstancesPerClass[i] = 0;
        }
    }
    
    public void run(final double[][] example, final int[] classOfExample, final int numberOfExamples, final int numberOfAttributes) {
        this.runClassValues(example, classOfExample, numberOfExamples, numberOfAttributes);
        this.runMinMax(example, classOfExample, numberOfExamples, numberOfAttributes);
        this.runMeanComputation(example, classOfExample, numberOfExamples, numberOfAttributes);
        this.runVarianceComputation(example, classOfExample, numberOfExamples, numberOfAttributes);
    }
    
    private void runMeanComputation(final double[][] example, final int[] classOfExample, final int numberOfExamples, final int numberOfAttributes) {
        for (int i = 0; i < numberOfExamples; ++i) {
            for (int j = 0; j < numberOfAttributes; ++j) {
                final double[] array = this.mean[j];
                final int n = classOfExample[i];
                array[n] += example[i][j];
            }
        }
        for (int i = 0; i < numberOfAttributes; ++i) {
            for (int j = 0; j < this.numberOfClasses; ++j) {
                final double[] array2 = this.mean[i];
                final int n2 = j;
                array2[n2] /= this.numInstancesPerClass[j];
            }
        }
    }
    
    private void runClassValues(final double[][] example, final int[] classOfExample, final int numberOfExamples, final int numberOfAttributes) {
        for (int i = 0; i < this.dSet.getNumInstances(); ++i) {
            final String classValue = this.dSet.getInstance(i).getOutputNominalValues(0);
            if (!this.classValues.contains(classValue)) {
                this.classValues.add(classValue);
            }
        }
        for (int i = 0; i < numberOfExamples; ++i) {
            final int[] numInstancesPerClass = this.numInstancesPerClass;
            final int n = classOfExample[i];
            ++numInstancesPerClass[n];
        }
    }
    
    private void runMinMax(final double[][] example, final int[] classOfExample, final int numberOfExamples, final int numberOfAttributes) {
        for (int j = 0; j < numberOfAttributes; ++j) {
            for (int i = 0; i < this.numberOfClasses; ++i) {
                this.maximum[j][i] = Double.MIN_VALUE;
                this.minimum[j][i] = Double.MAX_VALUE;
            }
        }
        for (int i = 0; i < numberOfExamples; ++i) {
            for (int j = 0; j < numberOfAttributes; ++j) {
                this.maximum[j][classOfExample[i]] = Math.max(this.maximum[j][classOfExample[i]], example[i][j]);
                this.minimum[j][classOfExample[i]] = Math.min(this.minimum[j][classOfExample[i]], example[i][j]);
            }
        }
    }
    
    private void runVarianceComputation(final double[][] example, final int[] classOfExample, final int numberOfExamples, final int numberOfAttributes) {
        for (int i = 0; i < numberOfExamples; ++i) {
            for (int j = 0; j < numberOfAttributes; ++j) {
                final double[] array = this.variance[j];
                final int n = classOfExample[i];
                array[n] += Math.pow(example[i][j] - this.mean[j][classOfExample[i]], 2.0);
            }
        }
        for (int i = 0; i < numberOfAttributes; ++i) {
            for (int j = 0; j < this.numberOfClasses; ++j) {
                final double[] array2 = this.variance[i];
                final int n2 = j;
                array2[n2] /= this.numInstancesPerClass[j] - 1;
            }
        }
    }
    
    public double getVariance(final int whichAttribute, final int whichClass) {
        return this.variance[whichAttribute][whichClass];
    }
    
    public double getMean(final int whichAttribute, final int whichClass) {
        return this.mean[whichAttribute][whichClass];
    }
    
    public double getMax(final int whichAttribute, final int whichClass) {
        return this.maximum[whichAttribute][whichClass];
    }
    
    public double getMin(final int whichAttribute, final int whichClass) {
        return this.minimum[whichAttribute][whichClass];
    }
}
