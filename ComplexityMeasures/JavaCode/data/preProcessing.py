#encoding=utf-8
import re

def ReadArchive(name):
    """Retorna os dados de um arquivo lido"""
    arq = open(name) #archive original só abre para leitura
    archive = arq.read()
    arq.close()
    return archive

def CreateArchive(name, mode ="w"):
    """Retorna um novo arquivo, não esqueça de fechá-lo"""
    arq = open(name,mode)
    return arq

def FindClasses():
    """Retorna uma lista com todas as classes do Dataset"""
    classes = re.findall("(?<={).+(?=})",archive)[-1]
    regexClass = "{"+ classes + "}"
    classes = re.sub(" ","",classes) #Elimina os espaços
    classes = classes.split(",")
    return classes,regexClass

def SubstituteMaxMin(newData):
    """Corrige os dados de Min e Max do Dataset"""
    newData = newData.split("@data")

    maxMinList = re.findall("(?<=\[).+(?=\])",newData[0]) #Busca os ANTIGOS valores de Min e Max
    for i in range(len(maxMinList)):
        maxMinList[i]= maxMinList[i].split(",")

    search = newData[1].split("\n") #Coloca as instâncias do Dataset em uma lista
    del search[0]
    while(search[-1]==""):
        del search[-1]
    for i in range(len(search)):
        search[i] = search[i].split(",")
    for i in range(len(search[0])-1):    
        for j in range(len(search)):
            try:
                search[j][i] = float(search[j][i])
            except:
                break

    newMaxMinList = [] #Busca os NOVOS valores de Min e Max
    for i in range(len(search[0])-1):
        maximum, minimum = search[0][i], search[0][i]
        for j in range(len(search)):
            if(isinstance(search[j][i], str)):
                break
            if(search[j][i]<minimum):
                minimum = float(search[j][i])
            if(search[j][i]>maximum):
                maximum = float(search[j][i])
        if(not isinstance(search[j][i], str)):
            newMaxMinList.append([minimum,maximum])

    # print("Max and Min: " +str(maxMinList))
    # print("New Max and Min:" + str(newMaxMinList))

    for i in range(len(maxMinList)): #Substitui os valores
        newData[0] = re.sub("\["+ str(maxMinList[i][0]) + "," + str(maxMinList[i][1]) +"\](?!FREDERICO.BENDER)", "["+ str(newMaxMinList[i][0]) + "," + str(newMaxMinList[i][1]) +"]FREDERICO.BENDER", newData[0], 1)
    newData[0] = re.sub("FREDERICO.BENDER","",newData[0])
    newData = str(newData[0]) + "@data" + str(newData[1])
    return newData

def RemoveAtributosInvariaveis(newData):
    """Faz a remoção de atributos inuteís"""
    newData = newData.split("@data")
    cabecalho = re.findall("@attribute.+\n",newData[0],flags=re.I)
    del cabecalho[-1]

    deletar = [] #Obtém as colunas que devem ser apagadas, e excluí o atributo referente no cabecalho
    for i in range(len(cabecalho)):
        valores = re.findall("(?<=\[).+(?=\])",cabecalho[i])
        if(valores): #Só vai pegar se tiver algum valor
            valores = valores[0].split(",")
            if(float(valores[0])==float(valores[1])):
                deletar.append(i)
                newData[0] = re.sub("@.+\[" + str(valores[0]) + "," + str(valores[1]) + "\]\n","",newData[0])
    deletar.reverse()

    search = re.findall("(?<=@inputs).+",newData[0]) #apaga valores do input
    search = search[0].split(",")
    for i in deletar:
        del search[i]
    search = ",".join(search)
    search = "@inputs" + search
    newData[0] = re.sub("@inputs.+",search,newData[0])


    search = newData[1].split("\n") #Apaga as colunas desnecessárias e remonta o dataset
    del search[0]
    while(search[-1]==""):
        del search[-1]
    for i in range(len(search)):
        search[i] = search[i].split(",")
    for i in range(len(search)):
        for j in deletar:
            del search[i][j]
    for i in range(len(search)):
        search[i]=",".join(search[i])
    newData[1]="\n".join(search)
    newData[1]+="\n"
    newData = str(newData[0]) + "@data\n" + str(newData[1])          
    return newData

def RunScript1():
    """Gera Datasets One Vs All: class 1 vs all, 2 vs all, 3 vs all..."""
    for i in range(len(classesList)):
        newData = archive
        newData = re.sub(regexClasses, "{"+ str(classesList[i])+",all}",newData) #Substitui as classes
      

        classesList2 = classesList.copy() #Monta Regex para substituir os dados
        classesList2.remove(classesList[i])
        regex="(?:(?<=, )|(?<=,))(" + str(classesList2[0])
        for j in range(1,len(classesList2)): 
            regex =str(regex) + "|" + str(classesList2[j])
        regex+=")(?: |)$"

        #Example (?:(?<=, )|(?<=,))(Class1|Class2|ClassN)(?: |)$
        newData = re.sub(regex,"all",newData,flags=re.M) #Substitui os dados que nao sao daquela iteracao
        
        newData = SubstituteMaxMin(newData)
        newData = RemoveAtributosInvariaveis(newData)

        ArchiveCreated = CreateArchive(str(inputData)+"_"+str(i+1)+"_vs_all.dat") #Salva os dados no arquivo
        ArchiveCreated.write(newData)
        ArchiveCreated.close()

def RunScript2():
    """Gera Datasets One Vs One: class 1 vs 2, 1 vs 3, 2 vs 3... OBS: Combinações"""
    for i in range(len(classesList)):
        for j in range(i+1,len(classesList)):
            newData = archive
            newData = re.sub(regexClasses, "{" + str(classesList[i])+","+str(classesList[j]) + "}",newData) #Substitui as classes
            

            classesList2 = classesList.copy()   
            classesList2.remove(classesList[i])
            classesList2.remove(classesList[j])
            regex=".+(?:(?<=, )|(?<=,))(" + str(classesList2[0])
            for k in range(1,len(classesList2)):
                regex =str(regex) + "|" + str(classesList2[k])
            regex+=")(?: |)$\n"

            #Example .+(?:(?<=, )|(?<=,))(Class1|Class2|ClassN)(?: |)$\n
            newData = re.sub(regex,"",newData,flags=re.M)

            newData = SubstituteMaxMin(newData)
            newData = RemoveAtributosInvariaveis(newData)

            ArchiveCreated = CreateArchive(str(inputData)+"_"+str(i+1)+"_vs_"+str(j+1)+".dat")
            ArchiveCreated.write(newData)
            ArchiveCreated.close()
 
#ALGORITMO RODA APENAS SE FOR UM DATASET COM MAIS DE 2 CLASSES, CASO TENHA SÓ 2 CLASSE, ELE SÓ COPIA OS DATASETS, CRIANDO 3 ARQUIVOS IGUAIS

inputs = ["appendicitis","balance","banana","bands","bupa","cleveland","contraceptive","ecoli","glass","haberman","hayes-roth","ionosphere","iris","led7digit","magic","newthyroid","page-blocks","penbased","phoneme","pima","ring","saheart","satimage","segment","shuttle","sonar","spectfheart","titanic","twonorm","vehicle","wine","wisconsin","yeast"]
#inputs=["aggregation","compound","pathbased","spiral","D31","R15","jain","flame"] #http://cs.joensuu.fi/sipu/datasets/
inputs=["saheart","segment","ecoli"]
for inputData in inputs[:]:
    archive = ReadArchive(str(inputData)+".dat")
    classesList,regexClasses = FindClasses()
    print(inputData, "classes = "+str(len(classesList)))
    
    archive = SubstituteMaxMin(archive)
    archive = RemoveAtributosInvariaveis(archive)

    newData = CreateArchive(str(inputData)+".dat")
    newData.write(archive)
    newData.close()

    if(len(classesList)>2):
        RunScript1() #n x ALL
        RunScript2() #COMBINATIONS
    else:
        current=["_1_vs_2.dat","_1_vs_all.dat"]
        for i in range(2):
            newData = CreateArchive(str(inputData)+str(current[i]))
            newData.write(archive)
            newData.close()