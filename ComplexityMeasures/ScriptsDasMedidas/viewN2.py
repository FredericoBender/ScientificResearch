#Example: https://matplotlib.org/3.1.1/gallery/lines_bars_and_markers/nan_test.html#sphx-glr-gallery-lines-bars-and-markers-nan-test-py
from mst import *
from matplotlib import pyplot as plt 
import pandas as pd

def Connect(x, y, connectionType="intraClass"):
    global medida
    if medida=="n2":
        colorSelected = "blue"
        if connectionType == "interClass":
            colorSelected = "orange"
        plt.plot(x, y, color=colorSelected)
    else:
        colorSelected = "gray"
        if connectionType == "interClass":
            colorSelected = "black"
        plt.plot(x, y, color=colorSelected)



datasets = ["aggregation","compound","pathbased","spiral","D31","R15","jain","flame"]
rows, columns = 1, 4
medida = "n2" #"n1" = arvore, "n2" = medida n2


plt.figure(figsize=(columns*11, rows*11))
for row in range(rows): #[0,1]
    for col in range(columns):#[0,1,2]
        if row*columns + col >= len(datasets):
            break
        nodes = getDataFrom("../JavaCode/data/Shape Datasets/" + datasets[row*columns + col] + ".csv")
        edges = getEdgesFrom(nodes)
        edges.sort()
        
        minimumSpanningTree = kruskal(edges)
        
        data = pd.read_csv("ComplexityMeasures/data/Shape Datasets/" + datasets[row*columns + col] + ".csv") 
        plt.subplot(rows, columns, row*columns + col + 1)
        plt.axis("equal")
        edges.sort()
            
        if medida=="n1":
            for i in minimumSpanningTree:
                typeLigation = "intraClass"
                if nodes[i[0]][2] != nodes[i[1]][2]:
                    typeLigation = "interClass"
                Connect([nodes[i[0]][0], nodes[i[1]][0]], [nodes[i[0]][1], nodes[i[1]][1]], typeLigation)
        
        elif medida=="n2": 
            menorIntra, menorInter, atual = [0,0,10000000], [0,0,10000000], 0
            for num,i in enumerate(edges): #Plot N2
                typeLigation = "intraClass"
                if nodes[i[0]][2] != nodes[i[1]][2]:
                    typeLigation = "interClass"
        
                if i[2]<menorIntra[2] and typeLigation=="intraClass":
                    menorIntra = i
                elif i[2]<menorInter[2] and typeLigation=="interClass":
                    menorInter = i
                    
                try:
                    if edges[num][0]!=edges[num+1][0]:
                                        #[X1,x2], [y1,y2]
                        Connect([nodes[menorInter[0]][0], nodes[menorInter[1]][0]], [nodes[menorInter[0]][1], nodes[menorInter[1]][1]], "interClass")
                        Connect([nodes[menorIntra[0]][0], nodes[menorIntra[1]][0]], [nodes[menorIntra[0]][1], nodes[menorIntra[1]][1]], "intraClass")
                        menorIntra, menorInter = [0,0,10000000], [0,0,10000000]
                except:
                    Connect([nodes[menorInter[0]][0], nodes[menorInter[1]][0]], [nodes[menorInter[0]][1], nodes[menorInter[1]][1]], "interClass")
                    Connect([nodes[menorIntra[0]][0], nodes[menorIntra[1]][0]], [nodes[menorIntra[0]][1], nodes[menorIntra[1]][1]], "intraClass")

        for name, group in data.groupby("species"):
            plt.plot(group["x"], group["y"], marker="o", linestyle="", label=name, ms=2) #ms = marker size
        plt.grid(True)
        # plt.grid(False)
        plt.title(datasets[row*columns + col], fontsize=25)
        plt.legend(loc="center right", bbox_to_anchor=(1.15, 0.5), prop={'size': 16})

# plt.show()
plt.savefig("N1_1.pdf", bbox_inches='tight')