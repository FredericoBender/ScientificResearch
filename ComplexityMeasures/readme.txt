This project is part of my undergraduate work in computer engineer at Federal University of Rio Grande




ORGANIZATION OF THE FOLDERS:
    JavaCode >>> Contains the scripts in Java that calculate the expected results from all the complexity measures, using the datasets loaded in the folder "JavaCode/data" and putting the results in csvs;
    Resultados das medidas >>> Contain the different kind of results obtained for the shape datasets, organized by subfiles of dataset, measures, or visual examples with numeric results;
    Scripts >>> This folder contains scripts that make the pre_processing of datasets and decomposition of the data in binary datasets, scripts to calculate de mean and standard deviation of the results generated by the "JavaCode" project, for all the binary datasets generated. And off course scripts that return visual information about the Complexity Measures for the given datasets (in this work, the Shape Datasets named: aggregation, compound, pathbased, spiral, D31, R15, jain, flame)
    
    
    
    
INSTALLING THE DEPENDENCIES:
    1 - Download and install Java : https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR
    2 - Download and install Java SE Development Kit : https://www.oracle.com/java/technologies/downloads/
    3 - Download and install Python : https://www.python.org/downloads/
    4 - >>> Press "Windows button"
        >>> write "cmd"
        >>> Right mouse click
        >>> execute as admin
        >>> run "pip install matplotlib && pip install pandas && pip install numpy"



SCRIPTS (in order of execution):
    1 - Scripts/preProcessing.py >>> This script is responsible per execute a pre-processing algorithm over the datasets in the folder: "JavaCode/data/Shape Datasets/", and make the decomposition of them using One Vs One and One Vs All heuristics to prepare the datasets for a execution.
    2 - JavaCode/build/classes/Complexity_Metrics.Run >>> This script is responsible per calculate the results for each measure and for each decomposed dataset, generating a csv file that have all results from all datasets and measures.
    3 - Scripts/compileResults.py >>> This script is responsible per take the csv generated by the java code : "JavaCode/build/classes/Complexity_Metrics.Run". It generate a file with de mean and standard deviation results of each dataset and measure.
    
    GET VISUALIZATION OF MEASURES FROM THE GIVEN DATASETS
        4 - Scripts/viewF1.py >>> This script is responsible per generate a PDF with a visual information about de F1 measure based in the dataset files at this folder: "JavaCode/data/Shape Datasets/"
        5 - Scripts/viewF2.py >>> This script is responsible per generate a PDF with a visual information about de F2 measure based in the dataset files at this folder: "JavaCode/data/Shape Datasets/"
        6 - Scripts/viewL1.py >>> This script is responsible per generate a PDF with a visual information about de L1 measure based in the dataset files at this folder: "JavaCode/data/Shape Datasets/"
        7 - Scripts/viewN1.py >>> This script is responsible per generate a PDF with a visual information about de N1 measure based in the dataset files at this folder: "JavaCode/data/Shape Datasets/"
        8 - Scripts/viewN2.py >>> This script is responsible per generate a PDF with a visual information about de N2 measure based in the dataset files at this folder: "JavaCode/data/Shape Datasets/"
        extra - Scripts/viewDatasets.py >>> This script is responsible per generate a PDF with a visual information about the dataset files at this folder: "JavaCode/data/Shape Datasets/"



RUNNING THE CODE:
    PRE-PROCESSING:
        1  >>> Press "Windows button"
        2  >>> write "cmd"
        3  >>> Right mouse click
        4  >>> execute as admin
        5  >>> navigate to the following path where you save the project : "ComplexityMeasures/Scripts"
            example1: run "cd C:\Users\Frederico Bender\Documents\Algoritmos\ScientificResearch\ComplexityMeasures\Scripts"
            example2: run "cd C:\ScientificResearch\ComplexityMeasures\Scripts"
        6  >>> run "python preProcessing.py"

    GET RESULTS:
        1  >>> Press "Windows button"
        2  >>> write "cmd"
        3  >>> Right mouse click
        4  >>> execute as admin
        5  >>> navigate to the following path where you save the project : "/ComplexityMeasures/JavaCode"
            example1: run "cd C:\Users\Frederico Bender\Documents\Algoritmos\ScientificResearch\ComplexityMeasures\JavaCode"
            example2: run "cd C:\ScientificResearch\ComplexityMeasures\JavaCode"
        6  >>> run decomposed 'one vs one' with the following command : "for a in {appendicitis,balance,banana,bands,bupa,cleveland,contraceptive,ecoli,glass,haberman,hayes-roth,ionosphere,iris,led7digit,magic,newthyroid,page-blocks,penbased,phoneme,pima,ring,saheart,satimage,segment,shuttle,sonar,spectfheart,titanic,twonorm,vehicle,wine,wisconsin,yeast};do for ((i=1;i<=10;++i)); do for ((j=i;j<=10;++j)); do java -cp build/classes Complexity_Metrics.Run param.txt $a $i $j;done;done;done;"
        7  >>> run decomposed 'one vs all' with the following command : "for a in {appendicitis,balance,banana,bands,bupa,cleveland,contraceptive,ecoli,glass,haberman,hayes-roth,ionosphere,iris,led7digit,magic,newthyroid,page-blocks,penbased,phoneme,pima,ring,saheart,satimage,segment,shuttle,sonar,spectfheart,titanic,twonorm,vehicle,wine,wisconsin,yeast};do for b in {1..10};do java -cp build/classes Complexity_Metrics.Run param.txt $a $b all;done;done;"
        8  >>> run 'MultiClass' with the following command : "for a in {appendicitis,balance,banana,bands,bupa,cleveland,contraceptive,ecoli,glass,haberman,hayes-roth,ionosphere,iris,led7digit,magic,newthyroid,page-blocks,penbased,phoneme,pima,ring,saheart,satimage,segment,shuttle,sonar,spectfheart,titanic,twonorm,vehicle,wine,wisconsin,yeast};do java -cp build/classes Complexity_Metrics.Run param.txt $a;done;"
    
    GET FILE WITH MEAN AND STANDARD DEVIATION OF EACH DATASET AND MEASURE
        1  >>> Press "Windows button"
        2  >>> write "cmd"
        3  >>> Right mouse click
        4  >>> execute as admin
        5  >>> navigate to the following path where you save the project : "ComplexityMeasures/Scripts"
            example1: run "cd C:\Users\Frederico Bender\Documents\Algoritmos\ScientificResearch\ComplexityMeasures\Scripts"
            example2: run "cd C:\ScientificResearch\ComplexityMeasures\Scripts"
        6  >>> run "python compileResults.py"
    
    GENERATE VISUALIZATION FROM DATASETS AND MEASURES:
        1  >>> Press "Windows button"
        2  >>> write "cmd"
        3  >>> Right mouse click
        4  >>> execute as admin
        5  >>> navigate to the following path where you save the project : "ComplexityMeasures/Scripts"
            example1: run "cd C:\Users\Frederico Bender\Documents\Algoritmos\ScientificResearch\ComplexityMeasures\Scripts"
            example2: run "cd C:\ScientificResearch\ComplexityMeasures\Scripts"
        6  >>> run "python viewF1.py"
        7  >>> run "python viewF2.py"
        8  >>> run "python viewL1.py"
        9  >>> run "python viewN1.py"
        10 >>> run "python viewN2.py"



CONTACT:
    Student Developer:
        Name: Frederico Bender Tiggemann
        Email: frederico.bender99@gmail.com
        Current Profession: Computer Engineer (03/2017 -> 03/2022)
        University: FURG - Universidade Federal do Rio Grande

    Advisors:
        Giancarlo Lucca  - giancarlo.lucca@furg.br
        Helida Salles    - helida@furg.br
        Eduardo Borges   - eduardoborges@furg.br
