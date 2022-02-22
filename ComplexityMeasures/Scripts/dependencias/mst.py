import csv
import numpy as np

def Euclidean_Distance(x0, y0, x1, y1):
    return (((x0 - x1)**2) + ((y0 - y1)**2))**0.5

def getDataFrom(arq):
    values = list(csv.reader(open(arq), delimiter=","))
    del values[0]
    values = np.array(values).astype("float")
    return values

def getEdgesFrom(values):
    edges = []
    for i in range(len(values)):
        for j in range(len(values)):
            if i!=j:
                edges.append([i, j, Euclidean_Distance(values[i][0], values[i][1], values[j][0], values[j][1])])
    return edges

def kruskal(edges):
    edges.sort(key=lambda x: x[2]) #Ordena pelo 2 elemento

    currentTrees = [{edges[0][0], edges[0][1]}] #Conjunto de sub arvores atuais
    minimumSpanningTree = [[edges[0][0], edges[0][1]]] #Resultado

    for i in edges: #i=[no1,no2,distancia]
        newTree = True
        counter = 0
        indices = []
        for index, j in enumerate(currentTrees): #j=set(elements used)
            if (i[0] in j) and (i[1] in j): #Ambos nós já em sub arvore, só ignora o caso
                newTree = False
                break
            if (i[0] in j) or (i[1] in j): 
                newTree = False
                indices.append(index)
                counter += 1
                if counter == 2: #Nós em sub arvores diferentes
                    minimumSpanningTree.append([i[0], i[1]])
                    currentTrees[indices[0]].update(currentTrees[indices[1]])
                    currentTrees.pop(indices[1])
                    break
            if (index == len(currentTrees) - 1) and (counter == 1): #Quando vazio ou 1 nó existente
                newTree = False
                currentTrees[indices[0]].add(i[0])
                currentTrees[indices[0]].add(i[1])
                minimumSpanningTree.append([i[0], i[1]])
        if newTree:
            currentTrees.append(set())
            currentTrees[-1].add(i[0])
            currentTrees[-1].add(i[1])
            minimumSpanningTree.append([i[0], i[1]])
    return minimumSpanningTree



if __name__ == "__main__":
    nodes = getDataFrom("flame.csv")
    edges = getEdgesFrom(nodes)
    minimumSpanningTree = kruskal(edges)