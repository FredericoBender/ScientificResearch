def Mean(listData):
    x=0
    for i in listData:
        x+=i
    x/=len(listData)
    return x

def StandarDeviation(listData):
    mean = Mean(listData)
    x=0
    for i in listData:
        x+=(i-mean)**2
    x=(x/len(listData))**0.5
    return round(x,2)

def ReadCSV(arq):
    import re
    arq = open(arq)
    CSV = arq.read().split("\n")
    arq.close()
    del CSV[-1]
    for i in range(len(CSV)):
        CSV[i]=CSV[i].split(",")
    for i in range(1,len(CSV)):
        for j in range(1,len(CSV[i])):
            CSV[i][j] = float(CSV[i][j])
    return CSV

def checkDifferentValues(column,readArq):
    inputData = ReadCSV(readArq)
    lista=[]
    for i in range(1,len(inputData)):
        if(inputData[i][column] not in lista):
            lista.append(inputData[i][column])
    return lista

def footer(arq):
    archive = open(arq,"r")
    CSV = archive.read().split("\n")
    archive.close()
    del CSV[-1]
    for i in range(len(CSV)):
        CSV[i]=CSV[i].split(",")
    for i in range(1,len(CSV)):
        for j in range(1,len(CSV[i])):
            import re
            CSV[i][j] = float(re.search("[0-9]*\.[0-9]*",CSV[i][j]).group())
    del CSV[0]
    archive = open(arq,"a")
    archive.write("Mean") 
    cont=1
    while(cont<len(CSV[0])):
        lista = []
        for i in range(0,len(CSV)):
            lista.append(CSV[i][cont])
        cont+=2   #1 quando utilizar ± 
        #archive.write(","+str(round(Mean(lista),2))+",±"+str(StandarDeviation(lista)))
        archive.write(","+str(round(Mean(lista),2))+","+str(StandarDeviation(lista)))
    archive.close()
    
def WriteCSV(writeArq,readArq="results.csv"):
    """{datasets, fm1-tra, fm1-tes, fm(n)-tra, fm(n)-tes} in order of the FM's"""
    def SubWrite(cont,outputData,crossV):
        training=[]
        testing=[]
        for i in range(crossV):
            training.append(inputData[cont][3])
            testing.append(inputData[cont][4])
            cont+=1
        #outputData.write(str(round(Mean(training),2))+",±"+str(StandarDeviation(training))+","+str(round(Mean(testing),2))+",±"+str(StandarDeviation(testing)))
        outputData.write(str(round(Mean(training),2))+","+str(StandarDeviation(training))+","+str(round(Mean(testing),2))+","+str(StandarDeviation(testing)))
        return cont, outputData

    def cabecalho(outputData):
        def fm(x):
            if x==1:
                return "Cardinalidad"
            elif x==2:
                return "Delta Dirac"
            elif x==3:
                return "Weighted Mean"
            elif x==4:
                return "OWA"
            elif x==50:
                return "Sugeno"
            else:
                return "CardGA"

        FuzzyMeasures = checkDifferentValues(1,readArq)
        outputData.write("dataSet")
        for i in range(len(FuzzyMeasures)):
            outputData.write(","+fm(FuzzyMeasures[i])+"-Training,SD,"+fm(FuzzyMeasures[i])+"-Testing,SD")
        outputData.write("\n")
        return outputData
    
    FuzzyMeasures = len(checkDifferentValues(1,readArq))
    crossV =len(checkDifferentValues(2,readArq))
    outputData = open(writeArq,"w")
    inputData = ReadCSV(readArq)
    outputData = cabecalho(outputData)
    cont=1
    while(cont<len(inputData)):
        for i in range(FuzzyMeasures-1):
            outputData.write(inputData[cont][0]+",")
            cont, outputData = SubWrite(cont,outputData,crossV)
            outputData.write(",")
        cont, outputData = SubWrite(cont,outputData,crossV)
        outputData.write("\n")
    outputData.close()
    footer(writeArq)

WriteCSV("resultsTable.csv")