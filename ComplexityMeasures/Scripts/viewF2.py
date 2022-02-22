from dependencias.mst import getDataFrom
from matplotlib import pyplot as plt 
import csv
import numpy as np
import pandas as pd

def Connect(p1, p2, color, coordenada):
    global iterationsCounter, constante
    x = [p1[0], p2[0]]
    y = [p1[1], p2[1]]
    if coordenada == "x":
        plt.plot(x, [min(y) + iterationsCounter, min(y) + iterationsCounter], color=color)
        if min(y)==y[0]:
            plt.plot([x[1], x[1]], [y[0] + iterationsCounter, y[1]], ":", color="gray")
            plt.plot([x[0], x[0]], [y[0] + iterationsCounter, y[0]], ":", color="gray")
        else:
            plt.plot([x[0], x[0]], [y[0], y[1] + iterationsCounter], ":", color="gray")
            plt.plot([x[1], x[1]], [y[1], y[1] + iterationsCounter], ":", color="gray")
    elif coordenada == "y":
        plt.plot([min(x) + iterationsCounter, min(x) + iterationsCounter], [y[1],y[0]], color=color)    
        if min(x)==x[0]:
            plt.plot([x[0] + iterationsCounter, x[1]], [y[1], y[1]], ":", color="gray")
            plt.plot([x[0] + iterationsCounter, x[0]], [y[0], y[0]], ":", color="gray")
        else:
            plt.plot([x[0], x[1] + iterationsCounter], [y[0], y[0]], ":", color="gray")
            plt.plot([x[1], x[1] + iterationsCounter], [y[1], y[1]], ":", color="gray")
    iterationsCounter += constante

def getF2(nodes, size=2):
    dicionario = dict()
    indices = []
    for i in nodes:
        if dicionario.get(i[2],False):
            dicionario[i[2]] += [[i[0], i[1]]]
        else:
            dicionario[i[2]] = [[i[0], i[1]]]
            indices.append(i[2])
    indices.sort()
    if size == "all":
        size = len(indices)
    if type(size) == int: 
        if size > len(indices):
            size = len(indices)
    F2connectionsX, F2connectionsY = [], []
    index = 0
    for i in range(size):
        for j in range(i + 1, size):
            if index >= size*(size - 1)/2:
                return F2connectionsX, F2connectionsY
            fi1, fi2 = "dicionario[indices[i]]", "dicionario[indices[j]]"
            F2connectionsX.append([min(max(eval(fi1), key=lambda x: x[0]), max(eval(fi2), key=lambda x: x[0]), key=lambda x: x[0]) , max(min(eval(fi1), key=lambda x: x[0]), min(eval(fi2), key=lambda x: x[0]), key=lambda x: x[0])]) #Em X
            F2connectionsX.append([max(max(eval(fi1), key=lambda x: x[0]), max(eval(fi2), key=lambda x: x[0]), key=lambda x: x[0]) , min(min(eval(fi1), key=lambda x: x[0]), min(eval(fi2), key=lambda x: x[0]), key=lambda x: x[0])]) #Em X
            F2connectionsY.append([min(max(eval(fi1), key=lambda x: x[1]), max(eval(fi2), key=lambda x: x[1]), key=lambda x: x[1] ) , max(min(eval(fi1), key=lambda x: x[1]), min(eval(fi2), key=lambda x: x[1]), key=lambda x: x[1])]) #Em Y
            F2connectionsY.append([max(max(eval(fi1), key=lambda x: x[1]), max(eval(fi2), key=lambda x: x[1]), key=lambda x: x[1] ) , min(min(eval(fi1), key=lambda x: x[1]), min(eval(fi2), key=lambda x: x[1]), key=lambda x: x[1])]) #Em Y        
            index += 1
    return F2connectionsX, F2connectionsY



if __name__ == "__main__":
    datasets = ["aggregation","compound","pathbased","spiral","D31","R15","jain","flame"]
    rows, columns = 2, 4
    size = 2 #Número máximo de classes a comparar(2,3,...,"all")
    constante = 0.05 #grau de separação das linhas

    colors = ["red", "green", "blue", "yellow", "black"]*100
    plt.figure(figsize=(columns*11, rows*11))
    for row in range(rows): #[0,1]
        for col in range(columns):#[0,1,2]
            if row*columns + col >= len(datasets):
                break
            nodes = getDataFrom("../JavaCode/data/Shape Datasets/" + datasets[row*columns + col] + ".csv")
            minMaxX, minMaxY = getF2(nodes, size)
            data = pd.read_csv("../JavaCode/data/Shape Datasets/" + datasets[row*columns + col] + ".csv") 
            plt.subplot(rows, columns, row*columns + col + 1)
            plt.axis("equal")
            iterationsCounter = 0
            for name, group in data.groupby("species"):
                plt.plot(group["x"], group["y"], marker="o", linestyle="", label=name, ms=2) #ms = marker size
            
            for color, i in enumerate(minMaxX):
                if color%2==0:
                    currentColor = colors[color//2]
                Connect(i[0], i[1], currentColor, "x")
                print("Dataset:",datasets[row*columns + col],"| x |",i[0][0], i[1][0])
                #Dataset: flame | y | (24 - 20,5) / (27,8 - 14,45)
            for color, i in enumerate(minMaxY):
                if color%2==0:
                    currentColor = colors[color//2]
                Connect(i[0], i[1], currentColor, "y")
                print("Dataset:",datasets[row*columns + col],"| y |",i[0][1], i[1][1])
            print("\n")
            plt.grid(True)
            plt.title(datasets[row*columns + col], fontsize=25)
            plt.legend(loc="center right", bbox_to_anchor=(1.15, 0.5), prop={'size': 16})
    # plt.show()
    plt.savefig("viewF2_generated.pdf", bbox_inches='tight')