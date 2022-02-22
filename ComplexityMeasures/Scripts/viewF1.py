from dependencias.mst import getDataFrom
from matplotlib import pyplot as plt 
import matplotlib.colors as mcolors
import pandas as pd

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
            minimoX = minimoY = maximoX = maximoY = None
            for name, group in data.groupby("species"):
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
                plt.boxplot(group["y"],widths = 0.8,showfliers=False, zorder=20, vert=True, positions=[group["x"].mean()], flierprops= dict(color=cores[name]), boxprops = dict(color=cores[name]),medianprops = dict(color=cores[name]),capprops= dict(color=cores[name]),whiskerprops= dict(color=cores[name])) #boxplot   
                plt.boxplot(group["x"],widths = 0.8,showfliers=False, zorder=20, vert=False, positions=[group["y"].mean()], flierprops= dict(color=cores[name]), boxprops = dict(color=cores[name]),medianprops = dict(color=cores[name]),capprops= dict(color=cores[name]),whiskerprops= dict(color=cores[name])) #boxplot
                print("Dataset:",datasets[row*columns + col],"| Class: ",name,"| Xmean: {:.5f}".format(group["x"].mean()), "| Ymean: {:.5f}".format(group["y"].mean()))
                print("Dataset:",datasets[row*columns + col],"| Class: ",name,"| Xvar: {:.5f}".format(group["x"].var()), "| Yvar: {:.5f}".format(group["y"].var()),"\n") #variancia, ou desvio padrao ao quadrado
            
            minimoX, minimoY, maximoX, maximoY = int(minimoX), int(minimoY), int(maximoX), int(maximoY)
            plt.xticks(range(minimoX-6,maximoX+6,3), range(minimoX-6,maximoX+6,3))
            plt.yticks(range(minimoY-6,maximoY+6,3), range(minimoY-6,maximoY+6,3))
                
            plt.title(datasets[row*columns + col], fontsize=25)
            plt.legend(loc="center right", bbox_to_anchor=(1.15, 0.5), prop={'size': 16})

    # plt.show()
    plt.savefig("viewF1_generated.pdf",bbox_inches='tight')