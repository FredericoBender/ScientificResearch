def Mean(listData,precisao=15):
    if(len(listData)==0):
        return -1
    x=0
    for i in listData:
        x+=i
    x/=len(listData)
    return round(x,precisao)

def StandardDeviation(listData,precisao=4):
    if(len(listData)==0):
        return -1
    mean = Mean(listData)
    x=0
    for i in listData:
        x+=(i-mean)**2
    x=(x/len(listData))**0.5
    return round(x,precisao)

def SortData(name):
    arq = open(name)
    dados = arq.read()
    dados = dados.split("\n")
    arq.close()
    while(dados[-1]==""):
        del dados[-1]
    header = dados[0]
    del dados[0]
    dados.sort()
    dados = "\n".join(dados)
    dados = header + "\n" + dados + "\n" 
    arq2 = open(name,"w")
    arq2.write(dados)
    arq2.close()

def ReadCSV(arq):
    arq = open(arq)
    CSV = arq.read()
    arq.close()
    CSV = re.sub("(.+,){4}-?Infinity.+\n","",CSV) #Remoção de Datasets que apresentam classes desaparecidas
    CSV = re.sub(",(NaN|Infinity)",",-1",CSV) #Padronização de erros
    CSV = CSV.split("\n")
    del CSV[-1]
    for i in range(len(CSV)):
        CSV[i]=CSV[i].split(",")
    for i in range(len(CSV)):
        del CSV[i][1:3]
    for i in range(1,len(CSV)):
        for j in range(1,len(CSV[i])):
            CSV[i][j] = float(CSV[i][j])
    return CSV

def WriteCSV(writeArq,readArq):
    readData = ReadCSV(readArq)
    # for i in readData:
    #     print(i)
    writeData = open(writeArq,"x")

    string = str(readData[0][0])
    for i in range(1,6):
        string+= "," + str(readData[0][i]) + "-Mean,SD"
    string+="\n"
    writeData.write(string)

    del readData[0]

    indices = [0]
    for i in range(len(readData)-1):
        if(readData[i][0]!=readData[i+1][0]):
            indices.append(i+1)
    indices.append(i+2)

    for i in range(len(indices)-1):
        writeData.write(str(readData[indices[i]][0]))
        for measures in range(1,6):
            lista = []
            for dataset in range(indices[i],indices[i+1]):
                if(readData[dataset][measures]!=-1):
                    lista.append(readData[dataset][measures])
            # writeData.write("," + str(Mean(lista)) + "," + str(StandardDeviation(lista)))
            writeData.write("," + "{0:.4f}".format(Mean(lista)) + "," + "{0:.2f}".format(StandardDeviation(lista)))
        writeData.write("\n")
    writeData.close()

import re
resultados = ["resultsOneVsAll.csv","resultsOneVsOne.csv","resultsMultiClass.csv"]

for i in resultados:
    SortData(i)
WriteCSV("Conclusion_OneVsAll.csv","resultsOneVsAll.csv")
WriteCSV("Conclusion_OneVsOne.csv","resultsOneVsOne.csv")