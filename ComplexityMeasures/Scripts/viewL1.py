from dependencias.mst import getDataFrom
from matplotlib import pyplot as plt
from statistics import mean 
import math
import matplotlib.colors as mcolors
import pandas as pd

def m_da_reta(x1,x2,y1,y2):
    return (y2 - y1)/(x2 - x1)

def achar_equacao(x,y,m):
    b = -m*x + y 
    # "y = " + str(m) + "*x +" + str(b),
    return  m, b

def Euclidean_Distance(x0, y0, x1, y1):
    return (((x0 - x1)**2) + ((y0 - y1)**2))**0.5

if __name__ == "__main__":
    datasets = ["aggregation","compound","pathbased","spiral","D31","R15","jain","flame"]
    rows, columns = 2, 4
    
    cores = ["red", "green", "blue", "yellow","pink","purple", "orange","olive","cyan","gray","darkslategray","violet","lawngreen","tomato","goldenrod","darkcyan","indigo"]*2
    plt.figure(figsize=(columns*11, rows*11))
    for row in range(rows): #[0,1]
        for col in range(columns):#[0,1,2]
            if row*columns + col >= len(datasets):
                break
            nodes = getDataFrom("../JavaCode/data/Shape Datasets/" + datasets[row*columns + col] + ".csv")
            data = pd.read_csv("../JavaCode/data/Shape Datasets/" + datasets[row*columns + col] + ".csv") 
            plt.subplot(rows, columns, row*columns + col + 1)
            plt.axis("equal")
            plt.grid(True, zorder=0)
            meanX, meanY = [], []
            minimoX = minimoY = maximoX = maximoY = None
            breaker=0
            for name, group in data.groupby("species"):
                breaker += 1
                if breaker>2:
                    break
                if minimoX == None:
                    minimoX, minimoY = min(group["x"]), min(group["y"])
                    maximoX, maximoY = max(group["x"]), max(group["y"])
                else:
                    if min(group["x"])< minimoX:
                        minimoX = min(group["x"])
                    if min(group["y"])< minimoY:
                        minimoY = min(group["y"])
                    if max(group["x"])> maximoX:
                        maximoX = max(group["x"])
                    if max(group["y"])> maximoY:
                        maximoY = max(group["y"])
                plt.plot(group["x"], group["y"], marker="o", linestyle="", label=name,color=cores[name], ms=2, zorder=10) #ms = marker size        
                plt.scatter([group["x"].mean()], group["y"].mean(), edgecolors='black',color="black", s=6,zorder=30)  #media
                meanX.append(group["x"].mean())
                meanY.append(group["y"].mean())

            x = mean(meanX) #ponto medio
            y = mean(meanY)
            # plt.scatter([x], [y], edgecolors='black',color="black", s=6,zorder=30)  #media
            m = m_da_reta(meanX[0],meanX[1],meanY[0],meanY[1])
            # print("m: ", m)
            angulo = math.degrees(math.atan(m_da_reta(meanX[0],meanX[1],meanY[0],meanY[1])))
            # print("angulo", angulo)
            new_m = math.tan(math.radians(angulo+90))
            # print("new_m", new_m)

            m , b = achar_equacao(x,y,new_m)

            minimoX, minimoY, maximoX, maximoY = int(minimoX), int(minimoY), int(maximoX), int(maximoY)
            x, y= [0]*2, [0]*2
            plus = 0.1
            x[0] = ((minimoY-plus)-b)/m
            x[1] = ((maximoY+plus)-b)/m

            y[0] = m*(minimoX-plus) + b
            y[1] = m*(maximoX+plus) + b

            if Euclidean_Distance(x[0],minimoY-plus,x[1],maximoY+plus) < Euclidean_Distance(y[0],minimoX-plus,y[1],maximoX+plus):
                plt.plot(x, [minimoY-plus, maximoY+plus], color="black")
            else:
                plt.plot([minimoX-plus, maximoX+plus], y, color="black")

            # "y = " + str(m) + "*x +" + str(b)
                     
            plt.title(datasets[row*columns + col], fontsize=25)
            plt.legend(loc="center right", bbox_to_anchor=(1.15, 0.5), prop={'size': 16})

    # plt.show()
    plt.savefig("viewL1_generated.pdf", bbox_inches='tight')